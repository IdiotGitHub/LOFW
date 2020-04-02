package com.yangyang.service.impl;

import com.yangyang.dao.CommentDaoMapper;
import com.yangyang.dao.UserDaoMapper;
import com.yangyang.dataobject.CommentDao;
import com.yangyang.dataobject.UserDao;
import com.yangyang.error.BusinessException;
import com.yangyang.error.EmBusinessError;
import com.yangyang.service.CommentService;
import com.yangyang.service.model.CommentModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 2019/11/26 15:38
 *
 * @Author Xiaoxu
 * @Version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDaoMapper commentDaoMapper;
    @Autowired
    UserDaoMapper userDaoMapper;
    @Override
    public List<CommentModel> selectByItemId(Integer itemId) {
        List<CommentModel> commentModels = new ArrayList<>();
        commentDaoMapper.selectByItemId(itemId).forEach(commentDao -> {
            try {
                commentModels.add(convertFromCommentDao(commentDao));
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        });
        return commentModels;
    }

    @Override
    public void submitComment(Integer userId, Integer itemId, String comment) {
        CommentDao commentDao = new CommentDao();
        commentDao.setUserId(userId);
        commentDao.setItemId(itemId);
        commentDao.setContext(comment);
        commentDao.setDatetime(new Date(System.currentTimeMillis()));
        commentDaoMapper.insertSelective(commentDao);
    }

    private CommentModel convertFromCommentDao(CommentDao commentDao) throws BusinessException {
        if (commentDao == null){
            return null;
        }
        CommentModel commentModel = new CommentModel();
        BeanUtils.copyProperties(commentDao,commentModel);
        UserDao userDao = userDaoMapper.selectByPrimaryKey(commentDao.getUserId());
        if (userDao == null){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
        }
        commentModel.setUserName(userDao.getName());
        commentModel.setUserImg(userDao.getUserImg());
        commentModel.setDatetime(getFormatTime(commentDao.getDatetime()));
        return commentModel;
    }
    private String getFormatTime(Date date){
        String formatTime = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatTime = dateFormat.format(date);
        return formatTime;
    }
}
