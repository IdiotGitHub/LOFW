package com.xiaoxu.service.impl;

import com.xiaoxu.dao.FavouriteDaoMapper;
import com.xiaoxu.dao.ItemDaoMapper;
import com.xiaoxu.dao.LikeDaoMapper;
import com.xiaoxu.dataobject.FavouriteDao;
import com.xiaoxu.dataobject.LikeDao;
import com.xiaoxu.service.FavouriteService;
import com.xiaoxu.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author xiaoxu
 */
@Service
public class FavouriteServiceImpl implements FavouriteService {
    @Autowired
    FavouriteDaoMapper favouriteDaoMapper;
    @Autowired
    ItemDaoMapper itemDaoMapper;
    @Override
    public boolean selectFavouriteStatus(Integer userId, Integer itemId) {

        FavouriteDao favouriteDao = favouriteDaoMapper.selectByUserIdAndItemId(userId, itemId);
        return favouriteDao != null;
    }
    @Transactional(rollbackFor = {})
    @Override
    public void cancelFavourite(Integer userId, Integer itemId) {

        favouriteDaoMapper.cancelFavourite(userId, itemId);
        itemDaoMapper.decreaseFavourite(itemId);
    }
    @Transactional(rollbackFor = {})
    @Override
    public void doFavourite(Integer userId, Integer itemId) {
        FavouriteDao favouriteDao = new FavouriteDao();
        favouriteDao.setUserId(userId);
        favouriteDao.setItemId(itemId);
        favouriteDao.setDatetime(new Date(System.currentTimeMillis()));
        favouriteDaoMapper.insertSelective(favouriteDao);
        itemDaoMapper.increaseFavourite(itemId);
    }
}
