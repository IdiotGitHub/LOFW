package com.xiaoxu.dao;

import java.util.List;
import java.util.Map;

public interface AdmUserServiceMapper {
    Map<String,Object> selectByAccount(String account,String password);

    void amdUserOnline(Integer userId);

    void amdUserOffline(Integer userId);

    int insertServiceLog(Map record);

    int selectLogCount(Map record);

    List<Map<String,Object>> selectServiceLog();

}
