package com.xiaoxu.controller;

import com.xiaoxu.service.FavouriteService;
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
@RequestMapping("/favourite")
public class FavouriteController {
    @Autowired
    private FavouriteService favouriteService;
    @RequestMapping(value = "/doFavourite" ,method = {RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> doLike(@RequestParam(name = "userId") Integer userId,
                                     @RequestParam(name = "itemId") Integer itemId){
        Map<String,Object> map = new HashMap<>(2);
        if (userId != null && userId != 0) {
            //用户已经登陆，查询点赞状态
            boolean favouriteStatus = favouriteService.selectFavouriteStatus(userId, itemId);
            if (favouriteStatus){
                favouriteService.cancelFavourite(userId, itemId);
            }else{
                favouriteService.doFavourite(userId, itemId);
            }
            map.put("isLogin",true);
            map.put("favouriteStatus",favouriteStatus);
        }else{
            map.put("isLogin",false);
        }
        return map;
    }
}
