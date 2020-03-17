package com.xiaoxu.service.impl;


import com.xiaoxu.dao.*;
import com.xiaoxu.dataobject.*;
import com.xiaoxu.error.BusinessException;
import com.xiaoxu.error.EmBusinessError;
import com.xiaoxu.response.PageBean;
import com.xiaoxu.service.AdmUserService;
import com.xiaoxu.service.UserService;
import com.xiaoxu.service.model.ItemModel;
import com.xiaoxu.service.model.UserModel;
import com.xiaoxu.validator.ValidationResult;
import com.xiaoxu.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在service层不能直接将查询到的对象传到想要该对象的服务
 * 需要在此层定义一个model层用来处理真正的业务逻辑，而dataobject仅仅作为与数据库的映射
 *
 * @author Administrator
 */
@Service
public class AdmUserServiceImpl implements AdmUserService {
    @Autowired
    private UserDaoMapper userDaoMapper;
    @Autowired
    private AdmUserServiceMapper admUserServiceMapper;
    @Autowired
    private UserPasswordDaoMapper userPasswordDaoMapper;
    @Autowired
    private LikeDaoMapper likeDaoMapper;
    @Autowired
    private FavouriteDaoMapper favouriteDaoMapper;
    @Autowired
    private FollowedDaoMapper followedDaoMapper;
    @Autowired
    private ValidatorImpl validator;



    //    @Cacheable(value = {"my-redis-cache1"})
    @Override
    public Map<String,Object> admLogin(String account,String password) throws BusinessException {
        Map admUser = admUserServiceMapper.selectByAccount(account,password);
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
    public List<Map<String,Object>> selectServiceLog() throws BusinessException {
        List<Map<String,Object>> serviceLog = admUserServiceMapper.selectServiceLog();
        return serviceLog;
    }



    /*@Override
    public PageBean<Map<String,Object>> getItemModeLForPage(String search, Integer currentPage, Integer pageSize, Integer userId) {
        *//*PageDao pageDao = null;
        if (userId == 0){
            pageDao = new PageDao(search, (currentPage - 1) * pageSize, pageSize, null);
        }else{
            pageDao = new PageDao(search, (currentPage - 1) * pageSize, pageSize, userId);
        }*//*
        List<Map<String,Object>> logItemList = new ArrayList<>();
        PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>();
       // int count = admUserServiceMapper.selectLogCount(pageDao);
         List<Map<String,Object>> serviceLog = admUserServiceMapper.selectServiceLog();

        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCounts(10);
        pageBean.setList(serviceLog);
        pageBean.setTotalPages((int) Math.ceil((double) (10) / pageSize));
        return pageBean;
    }*/

}
