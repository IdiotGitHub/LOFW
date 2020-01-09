package com.xiaoxu.service;

import com.xiaoxu.response.ChattingRecordsBean;
import com.xiaoxu.response.UnreadMessageBean;

import java.util.List;


/**
 * @author xiaoxu
 */
public interface ChattingRecordsService {
    ChattingRecordsBean getChattingRecords(Integer loginUserId, Integer recUserId);

    List<UnreadMessageBean> getUnreadMessage(Integer loginUserId);
}
