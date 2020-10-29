package com.xiaoxu.error;

/**
 * Created on 2019/11/17 14:20
 *
 * @Author Xiaoxu
 * @Version 1.0
 */
//包装器业务异常类实现
public class BusinessException extends Exception implements CommonError {
    private CommonError commonError;
    //直接接收EmBusinessError的传参，用于构造业务异常
    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }
    //接受自定义errorMsg的方式构造业务异常
    public BusinessException(CommonError commonError,String errorMsg) {
        super();
        this.commonError = commonError;
        this.commonError.setErrorMsg(errorMsg);
    }


    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.commonError.setErrorMsg(errorMsg);
        return this;
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }
}
