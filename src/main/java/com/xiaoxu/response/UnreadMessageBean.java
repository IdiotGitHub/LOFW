package com.xiaoxu.response;

import com.xiaoxu.dataobject.ChattingRecordsDao;
import com.xiaoxu.dataobject.UserDao;
import com.xiaoxu.service.model.ChattingRecordsModel;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxu
 */
public class UnreadMessageBean {

    private UserDao sendUser;
    private ChattingRecordsModel dao;


    public UserDao getSendUser() {
        return sendUser;
    }

    public void setSendUser(UserDao sendUser) {
        this.sendUser = sendUser;
    }

    public ChattingRecordsModel getDao() {
        return dao;
    }

    public void setDao(ChattingRecordsModel dao) {
        this.dao = dao;
    }
}
