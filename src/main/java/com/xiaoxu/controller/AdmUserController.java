package com.xiaoxu.controller;

import com.xiaoxu.controller.view.ItemView;
import com.xiaoxu.controller.view.UserView;
import com.xiaoxu.dataobject.UserDao;
import com.xiaoxu.error.BusinessException;
import com.xiaoxu.error.EmBusinessError;
import com.xiaoxu.response.CommontReturnType;
import com.xiaoxu.service.AdmUserService;
import com.xiaoxu.service.ItemService;
import com.xiaoxu.service.UserService;
import com.xiaoxu.service.model.ItemModel;
import com.xiaoxu.service.model.UserModel;
import com.xiaoxu.utils.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Base64.Encoder;

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
    public CommontReturnType isLogin() {
        HttpSession session = httpServletRequest.getSession();
        Boolean bool = (Boolean) session.getAttribute("isLogin");
        UserModel userModel = null;
        if (bool != null && bool) {
            userModel = (UserModel) session.getAttribute("loginUser");
            return CommontReturnType.create(convertFromUserModel(userModel));
        }else {
            return CommontReturnType.create(1,"fail");
        }

    }

    @RequestMapping("/login")
    @ResponseBody
    public CommontReturnType login(@RequestParam("telphone") String telphone,
                                   @RequestParam("password") String password)
            throws BusinessException, NoSuchAlgorithmException {
        //拿到手机号密码，调用service层方法进行登录
        //对密码进行加密在进行比对
        Map<String, Object> u = admUserService.admLogin(telphone, enCodeByMd5(password));
        httpServletRequest.getSession().setAttribute("isLogin", true);
        httpServletRequest.getSession().setAttribute("loginUser", u);

        //UserView userView = convertFromUserModel(u);
        return CommontReturnType.create(u);
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
    public CommontReturnType logout() throws BusinessException {
        boolean isLogin = (boolean) httpServletRequest.getSession().getAttribute("isLogin");
        if (!isLogin) {
            return CommontReturnType.create(null);
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
        return CommontReturnType.create(null);
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public CommontReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
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
        return CommontReturnType.create(userView);
    }
    @RequestMapping("/getUsers")
    @ResponseBody
    public CommontReturnType getUsers() {
        List<UserModel> users = userService.getUsers();
        List<UserView> userViewList = new ArrayList<>();
        users.forEach(userModel -> {
            userViewList.add(convertFromUserModel(userModel));
        });
        return CommontReturnType.create(userViewList);
    }
    @RequestMapping("/getItems")
    @ResponseBody
    public CommontReturnType getItems() {
        List<ItemModel> itemModels = itemService.getItems();
        List<ItemView> itemViews = new ArrayList<>();
        itemModels.forEach(itemModel -> {
            itemViews.add(convertItemViewFromItemModel(itemModel));
        });
        return CommontReturnType.create(itemViews);
    }
    @RequestMapping("/deleteItem")
    @ResponseBody
    public CommontReturnType deleteItems(@RequestParam(name = "itemId") Integer itemId) {
        int status = itemService.deleteItemById(itemId);
        if (status == 1) {
            return CommontReturnType.create(null);
        }else {
        return CommontReturnType.create(1,"fail");
    }
    }
    @RequestMapping("/fengHao")
    @ResponseBody
    public CommontReturnType fengHao(@RequestParam(name = "userId" ) Integer userId) {
        int status = userService.fengHao(userId);
        if (status == 1) {
            return CommontReturnType.create(null);
        }else {
            return CommontReturnType.create(1,"fail");
        }
    }
    @RequestMapping("/jieFeng")
    @ResponseBody
    public CommontReturnType jieFeng(@RequestParam(name = "userId" ) Integer userId) {
        int status = userService.jieFeng(userId);
        if (status == 1) {
            return CommontReturnType.create(null);
        }else {
            return CommontReturnType.create(1,"fail");
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
    public CommontReturnType getServiceLog() throws BusinessException {
        //先判断用户是否已经登录，未登录不允许通过访问
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
        if (isLogin == null || !isLogin) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        //调用service服务获取已登陆用户的关注人列表并返回给前端
        List<Map<String, Object>> userModels = admUserService.selectServiceLog();

        return CommontReturnType.create(userModels);
    }

}
