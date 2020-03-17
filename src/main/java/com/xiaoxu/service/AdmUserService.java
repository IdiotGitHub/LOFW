package com.xiaoxu.service;

import com.xiaoxu.error.BusinessException;
import com.xiaoxu.service.model.UserModel;

import java.util.List;
import java.util.Map;

public interface AdmUserService {
    Map<String,Object> admLogin(String account, String password) throws BusinessException;
    List<Map<String,Object>> selectServiceLog() throws BusinessException;

}
