package com.xiaoxu.service;

/**
 * @author xiaoxu
 */
public interface FavouriteService {
    /**
     * 查询收藏状态
     *
     * @param userId 用户id
     * @param itemId 帖子id
     * @return 收藏状态
     */
    boolean selectFavouriteStatus(Integer userId, Integer itemId);

    /**
     * 取消收藏
     *
     * @param userId 用户id
     * @param itemId 帖子id
     */
    void cancelFavourite(Integer userId, Integer itemId);

    /**
     * 收藏
     *
     * @param userId 用户id
     * @param itemId 帖子id
     */
    void doFavourite(Integer userId, Integer itemId);
}
