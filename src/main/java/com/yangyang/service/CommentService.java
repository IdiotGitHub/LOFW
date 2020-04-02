package com.yangyang.service;

import com.yangyang.service.model.CommentModel;

import java.util.List;

/**
 * Created on 2019/11/26 15:37
 *
 * @Author Xiaoxu
 * @Version 1.0
 */
public interface CommentService {
    /**
     * 根据帖子id查询所有的评论
     * @param itemId
     * @return
     */
    List<CommentModel> selectByItemId(Integer itemId);

    void submitComment(Integer userId, Integer itemId, String comment);
}
