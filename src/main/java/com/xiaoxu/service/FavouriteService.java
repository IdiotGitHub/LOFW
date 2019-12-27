package com.xiaoxu.service;

/**
 * @author xiaoxu
 */
public interface FavouriteService {
    /**
     * 查询收藏状态
     * @param userId
     * @param itemId
     * @return
     */
    boolean selectFavouriteStatus(Integer userId, Integer itemId);

    /**
     * 取消收藏
     * @param user
     * @param itemId
     */
    void cancelFavourite(Integer user, Integer itemId);

    /**
     * 收藏
     * @param userId
     * @param itemId
     */
    void doFavourite(Integer userId, Integer itemId);
}
