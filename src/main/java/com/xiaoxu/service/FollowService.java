package com.xiaoxu.service;

import java.util.List;

/**
 * @author xiaoxu
 */
public interface FollowService {
    /**
     * 查询关注状态
     *
     * @param userId     用户id
     * @param itemUserId 发表该贴用户id
     * @return 关注状态
     */
    boolean selectFollowStatus(Integer userId, Integer itemUserId);

    /**
     * 取消关注
     *
     * @param userId     用户id
     * @param itemUserId 发表该贴用户id
     */
    void cancelFollow(Integer userId, Integer itemUserId);

    /**
     * 关注
     *
     * @param userId     用户id
     * @param itemUserId 发表该贴用户
     */
    void doFollow(Integer userId, Integer itemUserId);

    /**
     * 根据发布动态的id查询关注的用户ID
     *
     * @param userId 用户id
     * @return 关注用户id列表
     */
    List<Integer> selectUserId(Integer userId);
}
