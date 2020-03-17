package com.xiaoxu.service.impl;


import com.xiaoxu.dao.*;
import com.xiaoxu.dataobject.*;
import com.xiaoxu.error.BusinessException;
import com.xiaoxu.error.EmBusinessError;
import com.xiaoxu.service.UserService;
import com.xiaoxu.service.model.UserModel;
import com.xiaoxu.validator.ValidationResult;
import com.xiaoxu.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 在service层不能直接将查询到的对象传到想要该对象的服务
 * 需要在此层定义一个model层用来处理真正的业务逻辑，而dataobject仅仅作为与数据库的映射
 *
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDaoMapper userDaoMapper;
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
    @Autowired
    private AdmUserServiceMapper admUserServiceMapper;
    @Override
    public UserModel getUser(Integer id) {
        //调用userdomapper获取到对应的用户dataobject
        UserDao userDao = userDaoMapper.selectByPrimaryKey(id);
        //如果userDao == null则用户不存在
        if (userDao == null) {
            return null;
        }
        UserPasswordDao userPasswordDao = userPasswordDaoMapper.selectByUserId(userDao.getId());
        String aaa = this.getClass().getName();
        return convertFromDataObject(userDao, userPasswordDao);
    }

    /**
     * 从controller层接收一个User Model类型的对象，但是一个UserModel对象对应的
     * 是一个UserDO和一个UserPasswordDO，因此要将其分开处理
     *
     * @param userModel 用户模型
     */
    @Transactional(rollbackFor = {})
    @Override
    public void register(UserModel userModel) throws BusinessException {
        //首先判空，必要操作
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //使用自定义的validator进行校验
        ValidationResult validate = validator.validate(userModel);
        if (validate.isHasError()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, validate.getErrorMsg());
        }
        //将userModel分离
        UserDao userDao = convertUserDaoFromUserModel(userModel);
        //将数据写入数据库，因为此处牵扯到两张表的数据插入，因此需要使用事务进行处理
        //因为数据库中设置了手机号码为unique，因此同一个手机号重复注册时会抛出异常，该异常会被spring所捕获，为了更好的用户体验，应该在此处进行捕获，给出具体的错误原因
        try {
            userDaoMapper.insertSelective(userDao);
        } catch (DuplicateKeyException ex) {
//            DuplicateKeyException,该异常是唯一索引引起的异常,
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "该手机号已经被注册请勿重复注册");
        }
        //因为user_info表的id是自增键，因此需要将其取出来赋值给userModel，但此处如果不在mybatis中进行相应的设置的话是无法直接取出的
        //因此要在Mapper中进行相应的设置，指定keyProperty为id，指定为自增列userGeneratedKeys
        userModel.setId(userDao.getId());
        UserPasswordDao userPasswordDao = convertPasswordFromUserModel(userModel);
        userPasswordDaoMapper.insertSelective(userPasswordDao);
    }

    //    @Cacheable(value = {"my-redis-cache1"})
    @Override
    public UserModel login(String telephone, String password) throws BusinessException {
        UserDao userDao = userDaoMapper.selectByTelphone(telephone);
        //判空，如果userDO为null，抛错
        if (userDao == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_ERROR);
        }
        UserPasswordDao userPasswordDa = userPasswordDaoMapper.selectByUserId(userDao.getId());
        UserModel userModel = convertFromDataObject(userDao, userPasswordDa);
        if (!com.alibaba.druid.util.StringUtils.equals(userModel.getUserPassword(), password)) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_ERROR);
        }
        userDaoMapper.userOnline(userModel.getId());
        return userModel;
    }

    @Override
    public void logout(Integer userId) {
        userDaoMapper.userOffline(userId);
    }

    @Override
    public void updatePassword(String telphone, String oldPassword, String newPassword) throws BusinessException {
        UserDao userDao = userDaoMapper.selectByTelphone(telphone);
        if (userDao == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号不存在");
        }
        UserPasswordDao userPasswordDao = userPasswordDaoMapper.selectByUserId(userDao.getId());
        if (!com.alibaba.druid.util.StringUtils.equals(oldPassword, userPasswordDao.getUserPassword())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号或原密码不正确");
        }
        userPasswordDaoMapper.updateByUserIdSelective(userDao.getId(), newPassword);
    }

    @Override
    public Map<String, Object> selectAction(Integer loginUserId, Integer itemId, Integer itemUserId) {
        Map<String, Object> status = new HashMap<>(3);
        status.put("isLike", likeDaoMapper.selectByUserIdAndItemId(loginUserId, itemId));
        status.put("isFavourite", favouriteDaoMapper.selectByUserIdAndItemId(loginUserId, itemId));
        status.put("isFollowed", followedDaoMapper.selectByUserIdAndItemUserId(loginUserId, itemUserId));
        return status;
    }

    @Override
    public List<UserModel> getFollows(Integer userId) {
        List<FollowedDao> followedDaoList = followedDaoMapper.selectByUserId(userId);
        List<UserModel> userModelList = new ArrayList<>();
        followedDaoList.forEach(followedDao ->
                userModelList.add(convertUserModelFromUserDao(userDaoMapper.selectByPrimaryKey(followedDao.getFollowedId()), userId)));
        return userModelList;
    }

    @Override
    public List<UserModel> getFans(Integer userId) {
        List<FollowedDao> followedDaoList = followedDaoMapper.selectByFollowedId(userId);
        List<UserModel> userModelList = new ArrayList<>();
        followedDaoList.forEach(followedDao -> userModelList.add(convertUserModelFromUserDao(userDaoMapper.selectByPrimaryKey(followedDao.getUserId()), userId)));
        return userModelList;
    }

    private UserModel convertUserModelFromUserDao(UserDao userDao, Integer userId) {
        if (userDao == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDao, userModel);
        userModel.setFollowed(followedDaoMapper.selectByUserIdAndItemUserId(userDao.getId(), userId) != null);
        return userModel;
    }

    private UserDao convertUserDaoFromUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDao userDao = new UserDao();
        BeanUtils.copyProperties(userModel, userDao);
        return userDao;
    }

    private UserPasswordDao convertPasswordFromUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDao userPasswordDao = new UserPasswordDao();
        userPasswordDao.setUserPassword(userModel.getUserPassword());
        userPasswordDao.setUserId(userModel.getId());
        return userPasswordDao;
    }

    private UserModel convertFromDataObject(UserDao userDao, UserPasswordDao userPasswordDao) {
        if (userDao == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDao, userModel);
        if (userPasswordDao != null) {
            userModel.setUserPassword(userPasswordDao.getUserPassword());
        }
        return userModel;
    }

    public void insertServiceLog(UserModel user_info,String service_name,String service_url){
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("user_id",user_info.getId());
        logMap.put("user_telephone",user_info.getTelephone());
        logMap.put("req_info","请求");
        logMap.put("result_desc","成功");
        logMap.put("service_url",service_url);
        logMap.put("service_name",service_name);
        logMap.put("create_time",new Date(System.currentTimeMillis()));

        admUserServiceMapper.insertServiceLog(logMap);
    }
}
