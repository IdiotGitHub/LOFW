package com.xiaoxu.service;

import java.util.List;

/**
 * @author xiaoxu
 */
public interface FollowService {
    /**
     * 查询收藏状态
     * @param userId
     * @param itemUserId
     * @return
     */
    boolean selectFollowStatus(Integer userId, Integer itemUserId);

    /**
     * 取消收藏
     * @param user
     * @param itemUserId
     */
    void cancelFollow(Integer user, Integer itemUserId);

    /**
     * 收藏
     * @param userId
     * @param itemUserId
     */
    void doFollow(Integer userId, Integer itemUserId);

    /**
     * 根据发布动态的id查询关注的用户ID
     * @param userId
     * @return
     */
    List<Integer> selectUserId(Integer userId);
}
