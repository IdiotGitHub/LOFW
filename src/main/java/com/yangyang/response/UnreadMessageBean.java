package com.yangyang.response;

import com.yangyang.dataobject.UserDao;
import com.yangyang.service.model.ChattingRecordsModel;

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
