package com.xiaoxu.service;

/**
 * @author xiaoxu
 */
public interface LikeService {
    /**
     * 查询点赞状态
     *
     * @param userId 用户id
     * @param itemId 帖子id
     * @return 点赞状态
     */
    boolean selectLikeStatus(Integer userId, Integer itemId);

    /**
     * 取消点赞
     *
     * @param userId 用户id
     * @param itemId 帖子id
     */
    void cancelLike(Integer userId, Integer itemId);

    /**
     * 点赞
     *
     * @param userId 用户id
     * @param itemId 帖子id
     */
    void doLike(Integer userId, Integer itemId);
}
