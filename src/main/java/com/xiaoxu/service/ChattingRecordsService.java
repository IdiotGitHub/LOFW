package com.xiaoxu.service;

import com.xiaoxu.response.ChattingRecordsBean;
import com.xiaoxu.response.UnreadMessageBean;

import java.util.List;


/**
 * @author xiaoxu
 */
public interface ChattingRecordsService {
    /**
     *  获取聊天记录
     * @param loginUserId  当前登录用户id
     * @param recUserId 消息接收方id
     * @return 聊天记录
     */
    ChattingRecordsBean getChattingRecords(Integer loginUserId, Integer recUserId);

    /**
     * 获取未读消息
     * @param loginUserId 登录用户id
     * @return 未读消息列表
     */
    List<UnreadMessageBean> getUnreadMessage(Integer loginUserId);
}
