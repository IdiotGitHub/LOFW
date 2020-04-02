package com.yangyang.controller;

import com.yangyang.controller.view.UserView;
import com.yangyang.dataobject.UserDao;
import com.yangyang.error.BusinessException;
import com.yangyang.error.EmBusinessError;
import com.yangyang.response.CommontReturnType;
import com.yangyang.service.AdmUserService;
import com.yangyang.service.CommentService;
import com.yangyang.service.UserService;
import com.yangyang.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Base64.Encoder;

/**
 * Created on 2019/11/26 9:38
 *
 * @Author Xiaoxu
 */
@Controller
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping("/user")
public class UserController /*extends BaseController*/ {
    /**
     *
     */
    @Autowired
    private AdmUserService admUserService;
    @Autowired
    private CommentService commentService;
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";
    public static final int USER_STATUS_BAN = 2;
    public static final int USER_STATUS_ALLOW = 1;
    StringWriter SEM = new StringWriter();
    PrintWriter PEM = new PrintWriter(SEM);
    @Autowired
    private UserService userService;
    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/isLogin", method = {RequestMethod.POST})
    @ResponseBody
    public CommontReturnType isLogin() throws BusinessException {
        try {
            HttpSession session = httpServletRequest.getSession();
            Boolean bool = (Boolean) session.getAttribute("isLogin");
            UserModel userModel = null;
            if (bool != null && bool) {
                userModel = (UserModel) session.getAttribute("loginUser");
            }

            return CommontReturnType.create(convertFromUserModel(userModel));
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommontReturnType.create(e, "0");
        }
    }

    @RequestMapping(value = "/updatePassword", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommontReturnType updatePassword(@RequestParam("telphone") String telphone,
                                            @RequestParam("oldPassword") String oldPassword,
                                            @RequestParam("newPassword") String newPassword
    ) throws BusinessException, NoSuchAlgorithmException {
        userService.updatePassword(telphone, enCodeByMd5(oldPassword), enCodeByMd5(newPassword));
        httpServletRequest.getSession().removeAttribute("loginUser");
        insertServiceLog("更新密码", Thread.currentThread().getStackTrace()[1].getMethodName());
        return CommontReturnType.create(null);
    }

    @RequestMapping("/login")
    @ResponseBody
    public CommontReturnType login(@RequestParam("telphone") String telphone,
                                   @RequestParam("password") String password)
            throws BusinessException, NoSuchAlgorithmException {
        try {
            //拿到手机号密码，调用service层方法进行登录
            //对密码进行加密在进行比对
            UserModel u = userService.login(telphone, enCodeByMd5(password));
            if (u.getStatus() == USER_STATUS_BAN) {
                throw new BusinessException(EmBusinessError.USER_LOGIN_ERROR, "您的账户被禁用，请联系管理员");
            }
            if (u.getStatus() == USER_STATUS_ALLOW) {
                //使用session记录用户登录信息
                httpServletRequest.getSession().setAttribute("isLogin", true);
                httpServletRequest.getSession().setAttribute("loginUser", u);

            }
            UserView userView = convertFromUserModel(u);
            insertServiceLog("登录", Thread.currentThread().getStackTrace()[1].getMethodName());
            return CommontReturnType.create(userView);
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommontReturnType.create(e, "0");
        }
    }

    /**
     * RequestMapping中的produces参数可以指定返回值的格式类型，其实就是response.setContentType()的作用
     * consumes参数与之对应，指定接收的数据格式：
     * post的两种格式：
     * application/x-www-form-urlencoded
     * multipart/form-data
     *
     * @param telphone 用户手机号
     * @param optCode  验证码
     * @param name     姓名
     * @param gender   性别
     * @param age      年龄
     * @param password 密码
     * @return 返回通用对象
     * @throws BusinessException        自定义异常
     * @throws NoSuchAlgorithmException 编码异常
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommontReturnType register(@RequestParam("telphone") String telphone,
                                      @RequestParam("otpCode") String optCode,
                                      @RequestParam("name") String name,
                                      @RequestParam("gender") Byte gender,
                                      @RequestParam("age") Integer age,
                                      @RequestParam("password") String password
    ) throws BusinessException, NoSuchAlgorithmException {
        try {
            //验证手机号与对应的otp相结合
            //在生成otpCode的时候将code存入了session中，此时将其拿出来就可以啦
            //需要注意的是在处理Ajax跨域请求时，session是不可以进行共享的，需要对前后端同时进行Ajax跨域授信

            /*String inSessionOtpCode = (String) httpServletRequest.getSession().getAttribute(telphone);
            if (!com.alibaba.druid.util.StringUtils.equals(optCode, inSessionOtpCode)) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不正确");
            }*/
            //用户注册流程
            //在userService中添加注册流程方法
            UserModel userModel = new UserModel();
            userModel.setName(name);
            userModel.setAccount(telphone);
            userModel.setAge(age);
            userModel.setGender(gender);
            userModel.setTelephone(telphone);
            userModel.setUserPassword(this.enCodeByMd5(password));
            userModel.setUserImg("./img/touxiang/touxiang1.jpg");
            userModel.setStatus((byte) 1);
            userService.register(userModel);
            return CommontReturnType.create(null);
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommontReturnType.create(e, "0");
        }
    }

    /**
     * java普通md5实现的方式实现的结果只支持16位的md5
     * 因此需要处理一下
     */
    public String enCodeByMd5(String str) throws NoSuchAlgorithmException {
        //确定计算方法
        //使用MessageDigest类获取md5的实例
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //使用BASE64Encoder
        Encoder base64Encoder = Base64.getEncoder();
        //加密字符串
        return base64Encoder.encodeToString(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    }

    @RequestMapping(value = "/getOtp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommontReturnType getOtp(@RequestParam("telphone") String telphone) {
        //生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        String otpCode = String.valueOf(randomInt + 10000);

        //将otp验证码同对应用户的手机号关联
        //可以使用redis，此处使用session的方式
        httpServletRequest.getSession().setAttribute(telphone, otpCode);
        //将otp验证码通过短信通道发送给用户
        System.out.println("telphone = " + telphone + "&otpCode = " + otpCode);
        return CommontReturnType.create(null);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommontReturnType logout() throws BusinessException {
        try {
            boolean isLogin = (boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (!isLogin) {
                return CommontReturnType.create(null);
            }
            UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("loginUser");
            int userId = userModel.getId();
            insertServiceLog("登出", Thread.currentThread().getStackTrace()[1].getMethodName());
            httpServletRequest.getSession().removeAttribute("isLogin");
            httpServletRequest.getSession().removeAttribute("loginUser");
            userService.logout(userId);
            return CommontReturnType.create(null);
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommontReturnType.create(e, "0");
        }
    }

    /**
     * 获取关注人列表
     *
     * @param id 已登陆用户ID
     * @return 返回通用对象
     * @throws BusinessException 自定义异常
     */
    @RequestMapping("/getFans")
    @ResponseBody
    public CommontReturnType getFans(@RequestParam(name = "id") Integer id) throws BusinessException {
        try {
            //先判断用户是否已经登录，未登录不允许通过访问
            Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (isLogin == null || !isLogin) {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
            //调用service服务获取已登陆用户的关注人列表并返回给前端
            List<UserModel> userModels = userService.getFans(id);
            List<UserView> userViewList = new ArrayList<>();
            //将核心领域模型用户对象转化为可供UI使用的viewobject
            userModels.forEach(userModel -> userViewList.add(convertFromUserModel(userModel)));
            insertServiceLog("查看粉丝", Thread.currentThread().getStackTrace()[1].getMethodName());
            return CommontReturnType.create(userViewList);
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommontReturnType.create(e, "0");
        }
    }

    @RequestMapping("/submitComment")
    @ResponseBody
    public CommontReturnType submitComment(@RequestParam(name = "userId") Integer userId,
                                           @RequestParam(name = "itemId") Integer itemId,
                                           @RequestParam(name = "comment") String comment) {
        commentService.submitComment(userId, itemId, comment);
        return CommontReturnType.create(null);
    }

    @RequestMapping("/getFollows")
    @ResponseBody
    public CommontReturnType getFollows(@RequestParam(name = "id") Integer id) throws BusinessException {
        try {
            //先判断用户是否已经登录，未登录不允许通过访问
            Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (isLogin == null || !isLogin) {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
            //调用service服务获取已登陆用户的关注人列表并返回给前端
            List<UserModel> userModels = userService.getFollows(id);
            List<UserView> userViewList = new ArrayList<>();
            //将核心领域模型用户对象转化为可供UI使用的viewobject
            userModels.forEach(userModel -> userViewList.add(convertFromUserModel(userModel)));
            insertServiceLog("查看关注", Thread.currentThread().getStackTrace()[1].getMethodName());
            return CommontReturnType.create(userViewList);
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommontReturnType.create(e, "0");
        }
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public CommontReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        try {
            //先判断用户是否已经登录，未登录不允许通过访问
            Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (isLogin == null || !isLogin) {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
            UserModel loginUser = (UserModel) httpServletRequest.getSession().getAttribute("loginUser");
            //调用service服务获取对应id的用户对象并返回给前端
            UserModel user = userService.getUser(id);
            //若获取的对应用户信息不存在
            if (user == null) {
                throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
            }
            //判断请求id是否为当前登录用户
            if (!user.getId().equals(loginUser.getId())) {
                throw new BusinessException(EmBusinessError.USER_LOGIN_ERROR, "请求信息错误，请重新登录！");
            }
            //将核心领域模型用户对象转化为可供UI使用的viewobject
            UserView userView = convertFromUserModel(user);
            insertServiceLog("获取用户", Thread.currentThread().getStackTrace()[1].getMethodName());
            return CommontReturnType.create(userView);
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommontReturnType.create(e, "0");
        }
    }

    @RequestMapping("/getOffLineUser")
    @ResponseBody
    public CommontReturnType getOffLineUser(@RequestParam(name = "userId") Integer userId) throws BusinessException {
        try {
            //调用service服务获取对应id的用户对象并返回给前端
            UserModel user = userService.getUser(userId);
            //若获取的对应用户信息不存在
            if (user == null) {
                throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
            }
            //将核心领域模型用户对象转化为可供UI使用的viewobject
            UserView userView = convertFromUserModel(user);
            insertServiceLog("获取用户信息", Thread.currentThread().getStackTrace()[1].getMethodName());
            return CommontReturnType.create(userView);
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommontReturnType.create(e, "0");
        }
    }

    private UserView convertFromUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserView userView = new UserView();
        BeanUtils.copyProperties(userModel, userView);
        return userView;
    }

    public void insertServiceLog(String service_name, String service_url) throws BusinessException {
        try {
            String clazz = this.getClass().getName() + ".";
            UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("loginUser");
            userService.insertServiceLog(userModel, service_name, clazz + service_url);
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
        }
    }


    @RequestMapping("/doReport")
    @ResponseBody
    public CommontReturnType doReport(@RequestParam("userId") Integer userId, @RequestParam("info") String info, @RequestParam("status") String status) throws BusinessException {
        try {
            //先判断用户是否已经登录，未登录不允许通过访问
            Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (isLogin == null || !isLogin) {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
            Map<String, Object> rp = new HashMap<>();
            rp.put("user_id", userId);
            rp.put("info", info);
            rp.put("status", status);
            rp.put("create_time", new Date(System.currentTimeMillis()));
            userService.doReport(rp);
            insertServiceLog("举报用户", Thread.currentThread().getStackTrace()[1].getMethodName());
            return CommontReturnType.create("操作成功");
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommontReturnType.create(e, "0");
        }
    }

    @RequestMapping("/doReport2")
    @ResponseBody
    public CommontReturnType doReport2(@RequestParam("phone") String phone, @RequestParam("info") String info, @RequestParam("status") String status) throws BusinessException {
        try {
            UserDao userInfo = userService.getUserByPhone(phone);
            if (userInfo == null) {
                throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
            }
            Map<String, Object> rp = new HashMap<>();
            rp.put("user_id", userInfo.getId());
            rp.put("info", info);
            rp.put("status", status);
            rp.put("create_time", new Date(System.currentTimeMillis()));
            userService.doReport(rp);
            insertServiceLog("举报用户", Thread.currentThread().getStackTrace()[1].getMethodName());
            return CommontReturnType.create("操作成功");
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommontReturnType.create(e, "0");
        }
    }

}