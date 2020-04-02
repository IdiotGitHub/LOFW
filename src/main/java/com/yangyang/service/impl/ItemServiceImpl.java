package com.yangyang.service.impl;

import com.yangyang.dao.ItemDaoMapper;
import com.yangyang.dao.UserDaoMapper;
import com.yangyang.dataobject.ItemDao;
import com.yangyang.dataobject.PageDao;
import com.yangyang.dataobject.UserDao;
import com.yangyang.response.PageBean;
import com.yangyang.service.ItemService;
import com.yangyang.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/11/26 14:55
 *
 * @Author Xiaoxu
 * @Version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDaoMapper itemDaoMapper;
    @Autowired
    private UserDaoMapper userDaoMapper;

    @Override
    public PageBean<ItemModel> getItemModeLForPage(String search, Integer currentPage, Integer pageSize, Integer userId) {
        PageDao pageDao = null;
        if (userId == 0){
            pageDao = new PageDao(search, (currentPage - 1) * pageSize, pageSize, null);
        }else{
            pageDao = new PageDao(search, (currentPage - 1) * pageSize, pageSize, userId);
        }
        List<ItemModel> itemModels = new ArrayList<>();
        PageBean<ItemModel> pageBean = new PageBean<ItemModel>();
        int count = itemDaoMapper.selectCount(pageDao);
        List<ItemDao> itemDaos = itemDaoMapper.getItemModeLForPage(pageDao);
        itemDaos.forEach(item -> {
            itemModels.add(convertItemModel(item));
        });
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
    public PageBean<ItemModel> getUserItemModeLForPage(String search, Integer currentPage, Integer pageSize, Integer userId) {
        PageDao pageDao =  new PageDao(search, (currentPage - 1) * pageSize, pageSize, userId);
        List<ItemModel> itemModels = new ArrayList<>();
        PageBean<ItemModel> pageBean = new PageBean<ItemModel>();
        int count = itemDaoMapper.selectItemCount(pageDao);
        List<ItemDao> itemDaos = itemDaoMapper.getUserItemModeLForPage(pageDao);
        itemDaos.forEach(item -> {
            itemModels.add(convertItemModel(item));
        });
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCounts(count);
        pageBean.setList(itemModels);
        pageBean.setTotalPages((int) Math.ceil((double) (count) / pageSize));
        return pageBean;
    }
    @Override
    public PageBean<ItemModel> getUserItemModeLForPageByFavourite(String search, Integer currentPage, Integer pageSize, Integer userId) {
        PageDao pageDao =  new PageDao(search, (currentPage - 1) * pageSize, pageSize, userId);
        List<ItemModel> itemModels = new ArrayList<>();
        PageBean<ItemModel> pageBean = new PageBean<ItemModel>();
        //int count = itemDaoMapper.selectItemCount(pageDao);
        List<ItemDao> itemDaos = itemDaoMapper.getUserItemModeLForPageByFavourite(pageDao);
        int count = itemDaos.size();
        itemDaos.forEach(item -> {
            itemModels.add(convertItemModel(item));
        });
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCounts(count);
        pageBean.setList(itemModels);
        pageBean.setTotalPages((int) Math.ceil((double) (count) / pageSize));
        return pageBean;
    }

    @Override
    public List<ItemModel> getItems() {
        List<ItemDao> itemDaos = itemDaoMapper.getItems();
        List<ItemModel> itemModels = new ArrayList<>();
        itemDaos.forEach(itemDao -> {
            itemModels.add(convertItemModel(itemDao));
        });
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
        BeanUtils.copyProperties(itemModel,itemDao);
        return itemDao;
    }
}
