package com.xiaoxu.controller;

import com.xiaoxu.controller.view.ItemView;
import com.xiaoxu.controller.view.UserView;
import com.xiaoxu.error.BusinessException;
import com.xiaoxu.error.EmBusinessError;
import com.xiaoxu.response.CommonReturnType;
import com.xiaoxu.service.AdmUserService;
import com.xiaoxu.service.ItemService;
import com.xiaoxu.service.UserService;
import com.xiaoxu.service.model.ItemModel;
import com.xiaoxu.service.model.UserModel;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created on 2019/11/26 9:38
 *
 * @Author Xiaoxu
 */
@Controller
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping("/admUser")
public class AdmUserController /*extends BaseController*/ {
    /**
     *
     */
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";
    public static final int USER_STATUS_BAN = 2;
    public static final int USER_STATUS_ALLOW = 1;
    StringWriter SEM = new StringWriter();
    PrintWriter PEM = new PrintWriter(SEM);
    @Autowired
    private UserService userService;
    @Autowired
    private AdmUserService admUserService;
    @Autowired
    private ItemService itemService;
    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/isLogin", method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType isLogin() throws BusinessException  {
        try {
            HttpSession session = httpServletRequest.getSession();
            Boolean bool = (Boolean) session.getAttribute("isLogin");
            UserModel userModel = null;
            if (bool != null && bool) {
                userModel = (UserModel) session.getAttribute("loginUser");
                return CommonReturnType.create(convertFromUserModel(userModel));
            } else {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
        }catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
        }

    }

    @RequestMapping("/login")
    @ResponseBody
    public CommonReturnType login(@RequestParam("telphone") String telphone,
                                  @RequestParam("password") String password)
            throws BusinessException, NoSuchAlgorithmException {
        try {
            //拿到手机号密码，调用service层方法进行登录
            //对密码进行加密在进行比对
            Map<String, Object> u = admUserService.admLogin(telphone, enCodeByMd5(password));
            httpServletRequest.getSession().setAttribute("isLogin", true);
            httpServletRequest.getSession().setAttribute("loginUser", u);

            //UserView userView = convertFromUserModel(u);
            return CommonReturnType.create(u);
        }catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
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
        Base64.Encoder base64Encoder = Base64.getEncoder();
        //加密字符串
        return base64Encoder.encodeToString(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType logout() throws BusinessException {
        try {
            boolean isLogin = (boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (!isLogin) {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
            UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("loginUser");
            int userId = userModel.getId();
            httpServletRequest.getSession().removeAttribute("isLogin");
            httpServletRequest.getSession().removeAttribute("loginUser");
        /*UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("loginUser");
        if (userModel != null){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }*/
            userService.logout(userId);
            return CommonReturnType.create(null);
        }catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
        }
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
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
                throw new BusinessException(EmBusinessError.LOGIN_USER_DIFFERENT);
            }
            //将核心领域模型用户对象转化为可供UI使用的viewobject
            UserView userView = convertFromUserModel(user);
            return CommonReturnType.create(userView);
        }catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
        }
    }
    @RequestMapping("/getUsers")
    @ResponseBody
    public CommonReturnType getUsers()  throws BusinessException {
        try {
            //先判断用户是否已经登录，未登录不允许通过访问
            Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (isLogin == null || !isLogin) {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
            List<UserModel> users = userService.getUsers();
            List<UserView> userViewList = new ArrayList<>();
            users.forEach(userModel -> {
                userViewList.add(convertFromUserModel(userModel));
            });
            return CommonReturnType.create(userViewList);
        }catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
        }
    }
    @RequestMapping("/getItems")
    @ResponseBody
    public CommonReturnType getItems()  throws BusinessException {
        try {
            //先判断用户是否已经登录，未登录不允许通过访问
            Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (isLogin == null || !isLogin) {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
            List<ItemModel> itemModels = itemService.getItems();
            List<ItemView> itemViews = new ArrayList<>();
            itemModels.forEach(itemModel -> {
                itemViews.add(convertItemViewFromItemModel(itemModel));
            });
            return CommonReturnType.create(itemViews);
        }catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
        }
    }
    @RequestMapping("/deleteItem")
    @ResponseBody
    public CommonReturnType deleteItems(@RequestParam(name = "itemId") Integer itemId)  throws BusinessException {
        try {
            int status = itemService.deleteItemById(itemId);
            if (status == 1) {
                return CommonReturnType.create(null);
            } else {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
        }catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
        }
    }
    @RequestMapping("/fengHao")
    @ResponseBody
    public CommonReturnType fengHao(@RequestParam(name = "userId" ) Integer userId) throws BusinessException  {
        try {
            int status = userService.recoverAccount(userId);
            if (status == 1) {
                return CommonReturnType.create(null);
            } else {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
        }catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
        }
    }
    @RequestMapping("/jieFeng")
    @ResponseBody
    public CommonReturnType jieFeng(@RequestParam(name = "userId" ) Integer userId) throws BusinessException  {
        try {
            int status = userService.banAccount(userId);
            if (status == 1) {
                return CommonReturnType.create(null);
            } else {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
        }catch (Exception e) {
        e.printStackTrace(PEM);
        String exception = SEM.toString();
        admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
    }
    }
    private ItemView convertItemViewFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemView itemView = new ItemView();
        BeanUtils.copyProperties(itemModel, itemView);
        //如果不进行时间转换传递到前端会发生格式错误
        itemView.setDatetime(getFormatTime(itemModel.getDatetime()));
        return itemView;
    }
    private String getFormatTime(Date date) {
        String formatTime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatTime = dateFormat.format(date);
        return formatTime;
    }

    private UserView convertFromUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserView userView = new UserView();
        BeanUtils.copyProperties(userModel, userView);
        return userView;
    }

    @RequestMapping("/getServiceLog")
    @ResponseBody
    public CommonReturnType getServiceLog() throws BusinessException {
        try {
            //先判断用户是否已经登录，未登录不允许通过访问
            Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (isLogin == null || !isLogin) {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
            //调用service服务获取已登陆用户的关注人列表并返回给前端
            List<Map<String, Object>> userModels = admUserService.selectServiceLog();

            return CommonReturnType.create(userModels);
        }catch (Exception e){
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
        }
    }


    @RequestMapping("/getErrorsLog")
    @ResponseBody
    public CommonReturnType getErrorsLog() throws BusinessException {
        try {
            //先判断用户是否已经登录，未登录不允许通过访问
            Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (isLogin == null || !isLogin) {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
            List<Map<String, Object>> ErrorsLog = admUserService.selectErrorsLog();

            return CommonReturnType.create(ErrorsLog);
        } catch (Exception e) {
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
        }
    }


    @RequestMapping("/getReportList")
    @ResponseBody
    public CommonReturnType getReportList() throws BusinessException {
        try {
            //先判断用户是否已经登录，未登录不允许通过访问
            Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
            if (isLogin == null || !isLogin) {
                throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            }
            //调用service服务获取已登陆用户的关注人列表并返回给前端
            List<Map<String, Object>> userModels = admUserService.getReportList();

            return CommonReturnType.create(userModels);
        }catch (Exception e){
            e.printStackTrace(PEM);
            String exception = SEM.toString();
            admUserService.insertErrorsLog(exception);
            return CommonReturnType.create(e,"0");
        }
    }
}
