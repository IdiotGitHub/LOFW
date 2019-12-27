package com.xiaoxu.controller;


import com.xiaoxu.error.BusinessException;
import com.xiaoxu.error.EmBusinessError;
import com.xiaoxu.service.FollowService;
import com.xiaoxu.utils.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 消息推送
 *
 * @author :ZHANGPENGFEI
 **/
@Controller
@RequestMapping("/websocket")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = {"Access-Control-Allow-Origin: *"})
public class WebSocketController {
    @Autowired
    FollowService followService;
    /**
     * 推送数据接口
     *
     * @param cid 发送消息的用户ID
     * @param message 发送的消息
     * @return 返回发送消息的用户ID
     */
    @ResponseBody
    @RequestMapping(value = "socket/push", method = RequestMethod.POST)
    public String pushToWeb(@RequestParam("cid") String cid, @RequestParam("message") String message) {
        try {
            WebSocketServer.sendInfo(message, cid);
        } catch (IOException e) {
            e.printStackTrace();
            return cid + "#" + e.getMessage();
        }
        return cid;
    }

    @ResponseBody
    @RequestMapping(value = "socket/sendMsg", method = RequestMethod.POST)
    public void sendMessages(@RequestParam("userId") Integer userId) throws BusinessException {
        if (userId == 0) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        List<Integer> idList = followService.selectUserId(userId);
        if (idList == null) {
            return;
        }
        for (Integer integer : idList) {
            try {
                WebSocketServer.sendInfo("@newMessage@", String.valueOf(integer));
            } catch (IOException e) {
                throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
            }
        }
    }
}

