package com.xiaoxu.controller;


import com.xiaoxu.error.BusinessException;
import com.xiaoxu.error.EmBusinessError;
import com.xiaoxu.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019/11/17 15:03
 *
 * @author Xiaoxu
 */
public class BaseController {
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";
    public static final int USER_STATUS_BAN = 2;
    public static final int USER_STATUS_ALLOW = 1;

    //可以通过面向对象的方式将处理方式封装到一个类中，让controller去继承该类以达到同样的效果
    //定义exceptionhandler解决未被controller层吸收的exception
    //通过捕获异常并将状态码设置为200的方式来骗过浏览器，从而达到设置自定义错误页面的目的
    //但是使用handler的方式仅仅只能返回一个路径，那么加入@ResponseBody注解就可以了

    /**
     * @param ex 异常类型
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(Exception ex) {
        //如果返回null，则页面啥也没有
        //那么可以将异常信息返回出去再做处理
        Map<String, Object> map = new HashMap<>();
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            map.put("errorCode", businessException.getErrorCode());
            map.put("errorMsg", businessException.getErrorMsg());
        } else {
            map.put("errorCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            map.put("errorMsg", EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
        }
        return CommonReturnType.create(map, "fail");
        //下面的方式是处理当异常为BusinessException的时候，那么就应该在前面进行相应的判断
        /*  BusinessException businessException = (BusinessException) ex;
        Map<String,Object> map = new HashMap<>();
        map.put("errorCode",businessException.getErrorCode());
        map.put("errorMsg", businessException.getErrorMsg());
        return  CommontReturnType.create(map,"fail");*/
        /* BusinessException businessException = (BusinessException) ex;
        Map<String,Object> map = new HashMap<>();
        map.put("errorCode",businessException.getErrorCode());
        map.put("errorMsg", businessException.getErrorMsg());
        CommontReturnType commontReturnType = new CommontReturnType();
        commontReturnType.setStatus("fail");

        commontReturnType.setData(map);
        return  commontReturnType;*/
    }
}
