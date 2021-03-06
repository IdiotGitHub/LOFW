package com.xiaoxu.dao;

import com.xiaoxu.dataobject.UserDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("com.yangyang.dao.UserDaoMapper")
public interface UserDaoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    int insert(UserDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    int insertSelective(UserDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    UserDao selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    int updateByPrimaryKeySelective(UserDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    int updateByPrimaryKey(UserDao record);

    UserDao selectByTelphone(String telephone);

    /**
     * 增加关注数量
     * @param itemUserId
     * @return
     */
    int increaseFollows(Integer itemUserId);

    /**
     * 减少关注数量
     * @param itemUserId
     * @return
     */
    int decreaseFollows(Integer itemUserId);

    /**
     * 增加粉丝数
     * @param itemUserId
     * @return
     */
    int decreaseFans(Integer itemUserId);

    /**
     * 减少粉丝数
     * @param itemUserId
     * @return
     */
    int increaseFans(Integer itemUserId);

    /**
     * 更新用户登陆状态
     * @param userId
     */
    void userOnline(Integer userId);
    /**
     * 更新用户登陆状态
     * @param userId
     */
    void userOffline(Integer userId);

    List<UserDao> selectAll();

    int updateStatus2ByUserId(Integer userId);
    int updateStatus1ByUserId(Integer userId);

    public Map<String,Object> getUserByPhone(String phone);

}