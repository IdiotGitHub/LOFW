package com.xiaoxu.service;

import com.xiaoxu.service.model.CommentModel;

import java.util.List;

/**
 * Created on 2019/11/26 15:37
 *
 * @author xiaoxu
 */
public interface CommentService {
    /**
     * 根据帖子id查询所有的评论
     *
     * @param itemId 帖子id
     * @return 评论列表
     */
    List<CommentModel> selectByItemId(Integer itemId);

    /**
     * 用户评论
     *
     * @param userId  用户id
     * @param itemId  帖子id
     * @param comment 评论内容
     */
    void submitComment(Integer userId, Integer itemId, String comment);
}
