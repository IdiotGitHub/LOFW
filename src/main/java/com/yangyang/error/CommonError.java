package com.yangyang.error;

/**
 * Created by XiaoXu on 2019/11/17 14:04
 *
 * @author Administrator
 */
public interface CommonError {
    /**
     * 设置错误信息
     *
     * @param errorMsg
     * @return
     */
    public CommonError setErrorMsg(String errorMsg);

    /**
     * 获取错误信息
     *
     * @return
     */
    public String getErrorMsg();

    /**
     * 获取错误码
     *
     * @return
     */
    public int getErrorCode();

}
