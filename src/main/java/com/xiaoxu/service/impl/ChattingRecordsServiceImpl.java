package com.xiaoxu.service.impl;

import com.xiaoxu.dao.ChattingRecordsDaoMapper;
import com.xiaoxu.dao.UserDaoMapper;
import com.xiaoxu.response.ChattingRecordsBean;
import com.xiaoxu.dataobject.ChattingRecordsDao;
import com.xiaoxu.dataobject.UserDao;
import com.xiaoxu.response.UnreadMessageBean;
import com.xiaoxu.service.ChattingRecordsService;
import com.xiaoxu.service.model.ChattingRecordsModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoxu
 */
@Service
public class ChattingRecordsServiceImpl implements ChattingRecordsService {
    @Resource
    private ChattingRecordsDaoMapper recordsDaoMapper;
    @Resource
    private UserDaoMapper userDaoMapper;

    /**
     * 这里没有必要每条记录都查询接收方的用户信息，等有时间要优化一下，只需要查询一次接收方用户信息就可以了，可以用一个对象包含所有的聊天记录
     * 外加一个接收方用户信息即可
     * 啊！！还有一个问题，数据库查询，这种方式只是查了发送的数据，如何查询发送和接收到的数据呢，查询两次数据库，将两次的查询结果根据datetime进行排序
     *
     * @param loginUserId 登陆用户ID
     * @param recUserId   接收方用户ID
     * @return 返回聊天记录model
     */
    @Override
    public ChattingRecordsBean getChattingRecords(Integer loginUserId, Integer recUserId) {
        List<ChattingRecordsDao> daoList = new ArrayList<>();
        daoList.addAll(recordsDaoMapper.getChattingRecords(loginUserId, recUserId));
        daoList.addAll(recordsDaoMapper.getChattingRecords(recUserId, loginUserId));
        Collections.sort(daoList);
        UserDao userDao = userDaoMapper.selectByPrimaryKey(recUserId);
        ChattingRecordsBean bean = new ChattingRecordsBean();
        bean.setList(daoList);
        bean.setUserDao(userDao);
        return bean;
    }

    @Override
    public List<UnreadMessageBean> getUnreadMessage(Integer loginUserId) {
        List<ChattingRecordsDao> dao = recordsDaoMapper.selectUnreadMessage(loginUserId);

        List<UnreadMessageBean> unreadMessageBeans = new ArrayList<>();
        dao.forEach(recordsDao -> unreadMessageBeans.add(convertUnreadMessageBeanFromChattingRecordsDao(recordsDao)));
        return unreadMessageBeans;
    }

    /**
     * 由于date类型传递到前端会发生格式上的错误，因此不得不加了一个model曾将时间格式转为字符串的形式进行数据传递，但是获取聊天记录的那个方法并没有使用，
     * 有时间在弄吧。。。。
     *
     * @param dao 聊天记录实体类对象
     * @return 未读消息bean
     */
    private UnreadMessageBean convertUnreadMessageBeanFromChattingRecordsDao(ChattingRecordsDao dao) {
        if (dao == null) {
            return null;
        }
        ChattingRecordsModel model = new ChattingRecordsModel();
        BeanUtils.copyProperties(dao, model);
        model.setDatetime(getFormatTime(dao.getDatetime()));
        UnreadMessageBean bean = new UnreadMessageBean();
        bean.setDao(model);
        bean.setSendUser(userDaoMapper.selectByPrimaryKey(dao.getSendUserId()));
        return bean;
    }

    private String getFormatTime(Date date) {
        String formatTime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatTime = dateFormat.format(date);
        return formatTime;
    }
}
