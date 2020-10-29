package com.xiaoxu.utils;

import com.alibaba.fastjson.JSON;
import com.xiaoxu.dataobject.MessageDao;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket服务类
 *
 * @author Xiaoxu
 * @date  2019-12-19
 **/
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {
    static Log log = LogFactory.getLog(WebSocketServer.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final CopyOnWriteArraySet<WebSocketServer> WEB_SOCKET_SET = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收sid
     */
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String sid) {

        this.session = session;
        WEB_SOCKET_SET.add(this);
        this.sid = sid;
        log.info("已经登陆;在线人数：" + WEB_SOCKET_SET.size());
/*        if (WEB_SOCKET_SET.size() == 0) {
            //加入set中
            WEB_SOCKET_SET.add(this);
            this.sid = sid;

            MessageDao messageDao = new MessageDao();
            messageDao.setUserId(Integer.valueOf(sid));
            messageDao.setFlag(false);
            messageDao.setMessage("连接成功");
            messageDao.setOnlineCount(WEB_SOCKET_SET.size());
            try {
                sendMessage(JSON.toJSONString(messageDao));
            } catch (IOException e) {
                log.error("websocket IO异常");
            }
        } else {
            boolean flag = false;
            //之前能收到是因为无论如何当前页面的socket都被写到了set中，改进了之后如果set中存在相同的id号就不会被写入，所以收不到消息
            for (WebSocketServer item : WEB_SOCKET_SET) {
                if (item.sid.equals(sid)) {
                    WEB_SOCKET_SET.remove(item);
                    this.sid = sid;
                    WEB_SOCKET_SET.add(this);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                log.info("已经登陆;在线人数：" + WEB_SOCKET_SET.size());
            } else {
                //加入set中
                WEB_SOCKET_SET.add(this);
                this.sid = sid;
                MessageDao messageDao = new MessageDao();
                messageDao.setUserId(Integer.valueOf(sid));
                messageDao.setFlag(false);
                messageDao.setMessage("连接成功");
                messageDao.setOnlineCount(WEB_SOCKET_SET.size());
                try {
                    sendMessage(JSON.toJSONString(messageDao));
                } catch (IOException e) {
                    log.error("websocket IO异常");
                }
            }
        }*/
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        WEB_SOCKET_SET.remove(this);
        //在线数减1
        log.info("有一连接关闭！当前在线人数为" + WEB_SOCKET_SET.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息:" + message);
        //群发消息
        for (WebSocketServer item : WEB_SOCKET_SET) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误" + session.getId());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message,String cid, String sid) throws IOException {
        MessageDao messageDao = new MessageDao();
        messageDao.setUserId(Integer.valueOf(cid));
        messageDao.setFlag(true);
        messageDao.setMessage(message);
        messageDao.setOnlineCount(WEB_SOCKET_SET.size());
        log.info("推送消息到窗口" + sid + "，推送内容:" + JSON.toJSONString(messageDao));

        for (WebSocketServer item : WEB_SOCKET_SET) {
            try {
                if (cid.equals(item.sid)) {
                    log.info("跳过给自己发送消息");
                    continue;
                }
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(JSON.toJSONString(messageDao));
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(JSON.toJSONString(messageDao));
                }
            } catch (IOException ignored) {
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSocketServer that = (WebSocketServer) o;
        return Objects.equals(session, that.session) &&
                Objects.equals(sid, that.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, sid);
    }
}
