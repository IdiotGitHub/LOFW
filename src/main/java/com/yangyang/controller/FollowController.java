package com.yangyang.controller;

import com.yangyang.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxu
 */
@Controller
@CrossOrigin(origins = "*" , allowCredentials = "true")
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;
    @RequestMapping(value = "/doFollow" ,method = {RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> doFollow(@RequestParam(name = "userId") Integer userId,
                                       @RequestParam(name = "itemUserId") Integer itemUserId){
        Map<String,Object> map = new HashMap<>(2);
        if (userId != null && userId != 0) {
            //用户已经登陆，查询点赞状态
            boolean followStatus = followService.selectFollowStatus(userId, itemUserId);
            if (followStatus){
                followService.cancelFollow(userId, itemUserId);
            }else{
                followService.doFollow(userId, itemUserId);
            }
            map.put("isLogin",true);
            map.put("followStatus",followStatus);
        }else{
            map.put("isLogin",false);
        }
        return map;
    }
}
