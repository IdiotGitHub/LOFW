package com.yangyang.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019/11/20 16:15
 *
 * @Author Xiaoxu
 * @Version 1.0
 */
public class ValidationResult {
    /**
     * 校验是否有错
     * 为了防止不存在错误时产生空指针异常，在这里进行赋值
     */
    private boolean hasError = false;

    private Map<String, String> errorMsgMap = new HashMap<>();

    public String getErrorMsg() {
        return StringUtils.join(this.errorMsgMap.values().toArray(), "");
    }

    public boolean isHasError() {
        return hasError; }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }
}
