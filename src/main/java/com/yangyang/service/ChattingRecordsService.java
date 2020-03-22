package com.yangyang.service;

import com.yangyang.response.ChattingRecordsBean;
import com.yangyang.response.UnreadMessageBean;

import java.util.List;


/**
 * @author xiaoxu
 */
public interface ChattingRecordsService {
    ChattingRecordsBean getChattingRecords(Integer loginUserId, Integer recUserId);

    List<UnreadMessageBean> getUnreadMessage(Integer loginUserId);
}
