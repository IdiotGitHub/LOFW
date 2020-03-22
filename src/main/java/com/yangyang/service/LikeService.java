package com.yangyang.service;

/**
 * @author xiaoxu
 */
public interface LikeService {
    /**
     * 查询点赞状态
     * @param userId
     * @param itemId
     * @return
     */
    boolean selectLikeStatus(Integer userId, Integer itemId);

    /**
     * 取消点赞
     * @param user
     * @param itemId
     */
    void cancelLike(Integer user, Integer itemId);

    /**
     * 点赞
     * @param userId
     * @param itemId
     */
    void doLike(Integer userId, Integer itemId);
}
