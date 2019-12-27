package com.xiaoxu.error;

/**
 * Created on 2019/11/17 14:09
 *
 * @Author Xiaoxu
 */
public enum EmBusinessError implements CommonError {
    //如果以0开头在进行json转换时会默认将0去掉
    //定义通用类型错误,
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    //定义用户信息相关错误为20000开头
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_ERROR(20002, "手机号或密码错误"),
    USER_NOT_LOGIN(20003, "未登录"),
    LOGIN_USER_DIFFERENT(20004, "请求用户与登录用户不一致，请重新登录"),

    UPLOAD_FILE_FAILED(30001,"文件上传失败！");

    EmBusinessError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private int errorCode;
    private String errorMsg;

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    @Override
    public String getErrorMsg() {

        return errorMsg;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}
