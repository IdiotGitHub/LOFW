package com.yangyang.service;

import com.yangyang.dataobject.UserDao;
import com.yangyang.error.BusinessException;
import com.yangyang.service.model.UserModel;

import java.util.List;
import java.util.Map;

/**
 * Created on 2019/11/26 9:07
 *
 * @Author Xiaoxu
 * @Version 1.0
 */
public interface UserService {
    /**
     * 用户登录
     * @param accountOrTelephone
     * @param password
     * @return
     */
    UserModel login(String accountOrTelephone, String password) throws BusinessException;

    void logout(Integer userId);
    /**
     * 通过用户id获取用户数据
     * @param id
     * @return
     */
    UserModel getUser(Integer id);

    /**
     * 用户注册
     * @param userModel
     */
    void register(UserModel userModel) throws BusinessException;
    /**
     * 修改密码
     * @param telphone
     * @param oldPassword
     * @param newPassword
     */
    void updatePassword(String telphone, String oldPassword, String newPassword) throws BusinessException;

    /**
     * 查询用户是否点赞，收藏，关注
     * @param loginUserId
     * @param itemId
     * @param itemUserId
     * @return
     */
    Map<String, Object> selectAction(Integer loginUserId, Integer itemId, Integer itemUserId);

    /**
     * 获取关注列表
     * @param id
     * @return
     */
    List<UserModel> getFollows(Integer id);

    /**
     * 获取粉丝列表
     * @param id
     * @return
     */
    List<UserModel> getFans(Integer id);


    void insertServiceLog(UserModel user_info,String service_name,String service_url) throws BusinessException;

    List<UserModel> getUsers();

    int fengHao(Integer userId);

    int jieFeng(Integer userId);

    void doReport(Map rp) throws BusinessException;

    UserDao getUserByPhone(String phone);
}
