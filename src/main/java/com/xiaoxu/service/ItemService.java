package com.xiaoxu.service;

import com.xiaoxu.response.PageBean;
import com.xiaoxu.service.model.ItemModel;

import java.util.List;

/**
 * Created on 2019/11/26 14:53
 *
 * @author Xiaoxu
 */
public interface ItemService {
    /**
     * 通过分页的形式查询帖子
     *
     * @param search      搜索内容
     * @param currentPage 当前页码
     * @param pageSize    每页内容长度
     * @param userId      用户id
     * @return 分页数据
     */
    PageBean<ItemModel> getItemModelForPage(String search, Integer currentPage, Integer pageSize, Integer userId);

    /**
     * 创建一个帖子
     *
     * @param itemModel
     */
    void createItem(ItemModel itemModel);

    /**
     * 根据用户id查询用户发布的帖子
     *
     * @param search      搜索内容
     * @param currentPage 当前页码
     * @param pageSize    每页内容长度
     * @param userId      用户id
     * @return 分页数据
     */
    PageBean<ItemModel> getUserItemModelForPage(String search, Integer currentPage, Integer pageSize, Integer userId);


    /**
     * 查询用户收藏的帖子
     *
     * @param search      搜索内容
     * @param currentPage 当前页码
     * @param pageSize    每页内容长度
     * @param userId      用户id
     * @return 分页数据
     */
    PageBean<ItemModel> getUserItemModelForPageByFavourite(String search, Integer currentPage, Integer pageSize, Integer userId);

    List<ItemModel> getItems();

    int deleteItemById(Integer itemId);

    void transmit(Integer itemId, Integer userId);
}
