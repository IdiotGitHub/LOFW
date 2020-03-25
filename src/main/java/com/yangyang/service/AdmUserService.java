package com.yangyang.service;

import com.yangyang.error.BusinessException;

import java.util.List;
import java.util.Map;

public interface AdmUserService {
    Map<String,Object> admLogin(String account, String password) throws BusinessException;
    List<Map<String,Object>> selectServiceLog() throws BusinessException;
    void insertErrorsLog(String exception) throws BusinessException;
    List<Map<String,Object>> selectErrorsLog() throws BusinessException;
    List<Map<String,Object>> getReportList() throws BusinessException;

}
