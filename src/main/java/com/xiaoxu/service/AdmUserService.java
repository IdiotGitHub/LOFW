package com.xiaoxu.service;

import com.xiaoxu.error.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * @author xmj65
 */
public interface AdmUserService {
    Map<String, Object> admLogin(String account, String password) throws BusinessException;

    List<Map<String, Object>> selectServiceLog();

    void insertErrorsLog(String exception);

    List<Map<String, Object>> selectErrorsLog();

    List<Map<String, Object>> getReportList();

}
