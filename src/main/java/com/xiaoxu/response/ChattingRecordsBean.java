package com.xiaoxu.response;

import com.xiaoxu.dataobject.ChattingRecordsDao;
import com.xiaoxu.dataobject.UserDao;

import java.util.List;

/**
 * @author xiaoxu
 */
public class ChattingRecordsBean {
    private List<ChattingRecordsDao> list;
    private UserDao userDao;

    public List<ChattingRecordsDao> getList() {
        return list;
    }

    public void setList(List<ChattingRecordsDao> list) {
        this.list = list;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
