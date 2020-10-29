package com.xiaoxu.service.impl;


import com.xiaoxu.dao.*;
import com.xiaoxu.error.BusinessException;
import com.xiaoxu.error.EmBusinessError;
import com.xiaoxu.service.AdmUserService;
import com.xiaoxu.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 在service层不能直接将查询到的对象传到想要该对象的服务
 * 需要在此层定义一个model层用来处理真正的业务逻辑，而dataobject仅仅作为与数据库的映射
 *
 * @author Administrator
 */
@Service
public class AdmUserServiceImpl implements AdmUserService {

    @Resource
    private AdmUserServiceMapper admUserServiceMapper;

    @Override
    public Map<String,Object> admLogin(String account,String password) throws BusinessException {
        Map<String, Object> admUser = admUserServiceMapper.selectByAccount(account, password);
        //判空，如果userDO为null，抛错
        if (admUser == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_ERROR);
        }
        int status = (int)admUser.getOrDefault("status","0");
        if(status!=1){
            throw new BusinessException(EmBusinessError.LOGIN_ADM_DIFFERENT);

        }

        admUserServiceMapper.amdUserOnline((int)admUser.get("id"));
        return admUser;
    }
    @Override
    public List<Map<String,Object>> selectServiceLog() {
        return admUserServiceMapper.selectServiceLog();
    }


    @Override
    public void insertErrorsLog(String exception){
        Map<String,Object> error  = new HashMap<>();
        error.put("errors_info",exception);
        error.put("create_time",new Date(System.currentTimeMillis()));
        admUserServiceMapper.insertErrorsLog(error);
    }

    @Override
    public List<Map<String,Object>> selectErrorsLog() {
        return admUserServiceMapper.selectErrorsLog();
    }


    @Override
    public List<Map<String,Object>> getReportList() {
        return admUserServiceMapper.getReportList();
    }

}
