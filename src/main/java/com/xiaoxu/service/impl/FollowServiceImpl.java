package com.xiaoxu.service.impl;

import com.xiaoxu.dao.FollowedDaoMapper;
import com.xiaoxu.dao.UserDaoMapper;
import com.xiaoxu.dataobject.FollowedDao;
import com.xiaoxu.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 这里忘记用户关注数量和粉丝数量了，后期再加上
 * @author xiaoxu
 */
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowedDaoMapper followedDaoMapper;
    @Autowired
    UserDaoMapper userDaoMapper;
    @Override
    public boolean selectFollowStatus(Integer userId, Integer itemUserId) {

        FollowedDao followedDao = followedDaoMapper.selectByUserIdAndItemUserId(userId, itemUserId);
        return followedDao != null;
    }
    @Transactional(rollbackFor = {})
    @Override
    public void cancelFollow(Integer userId, Integer itemUserId) {
        followedDaoMapper.cancelFollow(userId, itemUserId);
        userDaoMapper.decreaseFollows(userId);
        userDaoMapper.decreaseFans(itemUserId);

    }
    @Transactional(rollbackFor = {})
    @Override
    public void doFollow(Integer userId, Integer itemUserId) {
        FollowedDao followedDao = new FollowedDao();
        followedDao.setUserId(userId);
        followedDao.setFollowedId(itemUserId);
        followedDao.setDatetime(new Date(System.currentTimeMillis()));
        followedDaoMapper.insertSelective(followedDao);
        userDaoMapper.increaseFollows(userId);
        userDaoMapper.increaseFans(itemUserId);
    }

    @Override
    public List<Integer> selectUserId(Integer userId) {
        return followedDaoMapper.selectByUserIdAsList(userId);
    }
}
