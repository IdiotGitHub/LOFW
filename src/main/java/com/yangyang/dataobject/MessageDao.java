package com.yangyang.dataobject;

/**
 * @author xiaoxu
 */
public class MessageDao {
    /**
     * 发送消息的用户ID
     */
    private Integer userId;
    private boolean flag;
    private Integer onlineCount;
    private Object message;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
