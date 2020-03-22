package com.yangyang.service.impl;

import com.yangyang.dao.ItemDaoMapper;
import com.yangyang.dao.LikeDaoMapper;
import com.yangyang.dataobject.LikeDao;
import com.yangyang.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author xiaoxu
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    LikeDaoMapper likeDaoMapper;
    @Autowired
    ItemDaoMapper itemDaoMapper;
    @Override
    public boolean selectLikeStatus(Integer userId, Integer itemId) {

        LikeDao likeDao = likeDaoMapper.selectByUserIdAndItemId(userId, itemId);
        return likeDao != null;
    }
    @Transactional(rollbackFor = {})
    @Override
    public void cancelLike(Integer userId, Integer itemId) {

        likeDaoMapper.cancelLike(userId, itemId);
        itemDaoMapper.decreaseLike(itemId);
    }
    @Transactional(rollbackFor = {})
    @Override
    public void doLike(Integer userId, Integer itemId) {
        LikeDao likeDao = new LikeDao();
        likeDao.setUserId(userId);
        likeDao.setItemId(itemId);
        likeDao.setDatetime(new Date(System.currentTimeMillis()));
        likeDaoMapper.insertSelective(likeDao);
        itemDaoMapper.increaseLike(itemId);
    }
}
