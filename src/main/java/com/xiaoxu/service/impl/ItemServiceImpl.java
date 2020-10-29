package com.xiaoxu.service.impl;

import com.xiaoxu.dao.ItemDaoMapper;
import com.xiaoxu.dao.UserDaoMapper;
import com.xiaoxu.dataobject.ItemDao;
import com.xiaoxu.dataobject.PageDao;
import com.xiaoxu.dataobject.UserDao;
import com.xiaoxu.response.PageBean;
import com.xiaoxu.service.ItemService;
import com.xiaoxu.service.model.ItemModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/11/26 14:55
 *
 * @author xiaoxu
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    private ItemDaoMapper itemDaoMapper;
    @Resource
    private UserDaoMapper userDaoMapper;

    @Override
    public PageBean<ItemModel> getItemModelForPage(String search, Integer currentPage, Integer pageSize, Integer userId) {
        PageDao pageDao;
        if (userId == 0) {
            pageDao = new PageDao(search, (currentPage - 1) * pageSize, pageSize, null);
        } else {
            pageDao = new PageDao(search, (currentPage - 1) * pageSize, pageSize, userId);
        }
        List<ItemModel> itemModels = new ArrayList<>();
        PageBean<ItemModel> pageBean = new PageBean<>();
        int count = itemDaoMapper.selectCount(pageDao);
        List<ItemDao> itemDaos = itemDaoMapper.getItemModeLForPage(pageDao);
        return getItemModelPageBean(currentPage, pageSize, itemModels, pageBean, count, itemDaos);
    }

    @NotNull
    private PageBean<ItemModel> getItemModelPageBean(Integer currentPage, Integer pageSize, List<ItemModel> itemModels, PageBean<ItemModel> pageBean, int count, List<ItemDao> itemDaos) {
        itemDaos.forEach(item -> itemModels.add(convertItemModel(item)));
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCounts(count);
        pageBean.setList(itemModels);
        pageBean.setTotalPages((int) Math.ceil((double) (count) / pageSize));
        return pageBean;
    }

    @Override
    public void createItem(ItemModel itemModel) {
        itemDaoMapper.insertSelective(convertItemDao(itemModel));
    }

    @Override
    public PageBean<ItemModel> getUserItemModelForPage(String search, Integer currentPage, Integer pageSize, Integer userId) {
        PageDao pageDao = new PageDao(search, (currentPage - 1) * pageSize, pageSize, userId);
        List<ItemModel> itemModels = new ArrayList<>();
        PageBean<ItemModel> pageBean = new PageBean<>();
        int count = itemDaoMapper.selectItemCount(pageDao);
        List<ItemDao> itemDaos = itemDaoMapper.getUserItemModeLForPage(pageDao);
        return getItemModelPageBean(currentPage, pageSize, itemModels, pageBean, count, itemDaos);
    }

    @Override
    public PageBean<ItemModel> getUserItemModelForPageByFavourite(String search, Integer currentPage, Integer pageSize, Integer userId) {
        PageDao pageDao = new PageDao(search, (currentPage - 1) * pageSize, pageSize, userId);
        List<ItemModel> itemModels = new ArrayList<>();
        PageBean<ItemModel> pageBean = new PageBean<>();
        List<ItemDao> itemDaos = itemDaoMapper.getUserItemModeLForPageByFavourite(pageDao);
        int count = itemDaos.size();
        return getItemModelPageBean(currentPage, pageSize, itemModels, pageBean, count, itemDaos);
    }

    @Override
    public List<ItemModel> getItems() {
        List<ItemDao> itemDaos = itemDaoMapper.getItems();
        List<ItemModel> itemModels = new ArrayList<>();
        itemDaos.forEach(itemDao -> itemModels.add(convertItemModel(itemDao)));
        return itemModels;
    }

    @Override
    public int deleteItemById(Integer itemId) {
        return itemDaoMapper.deleteByPrimaryKey(itemId);
    }

    @Override
    public void transmit(Integer itemId, Integer userId) {
        ItemDao itemDao = itemDaoMapper.selectByPrimaryKey(itemId);
        itemDao.setId(null);
        itemDao.setUserId(userId);
        itemDaoMapper.insertSelective(itemDao);
    }

    private ItemModel convertItemModel(ItemDao itemDao) {
        if (itemDao == null) {
            return null;
        }
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDao, itemModel);
        UserDao userDao = userDaoMapper.selectByPrimaryKey(itemDao.getUserId());
        itemModel.setUserName(userDao.getName());
        itemModel.setUserImg(userDao.getUserImg());
        return itemModel;
    }

    private ItemDao convertItemDao(ItemModel itemModel) {
        ItemDao itemDao = new ItemDao();
        BeanUtils.copyProperties(itemModel, itemDao);
        return itemDao;
    }
}
