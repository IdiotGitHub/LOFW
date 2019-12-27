package com.xiaoxu.service;

import com.xiaoxu.response.PageBean;
import com.xiaoxu.service.model.ItemModel;

/**
 * Created on 2019/11/26 14:53
 *
 * @Author Xiaoxu
 * @Version 1.0
 */
public interface ItemService {
    /**
     * 通过分页的形式查询帖子
     * @param search
     * @param currentPage
     * @param pageSize
     * @param userId
     * @return
     */
    PageBean<ItemModel> getItemModeLForPage(String search, Integer currentPage, Integer pageSize,Integer userId);

    /**
     * 创建一个帖子
     * @param itemModel
     */
    void createItem(ItemModel itemModel);

    /**
     * 根据用户id查询用户发布的帖子
     * @param search
     * @param currentPage
     * @param pageSize
     * @param userId
     * @return
     */
    PageBean<ItemModel> getUserItemModeLForPage(String search, Integer currentPage, Integer pageSize, Integer userId);
}
