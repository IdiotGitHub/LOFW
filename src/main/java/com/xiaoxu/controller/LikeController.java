package com.xiaoxu.controller;

import com.xiaoxu.error.BusinessException;
import com.xiaoxu.error.EmBusinessError;
import com.xiaoxu.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxu
 */
@Controller
@CrossOrigin(origins = "*" , allowCredentials = "true")
@RequestMapping("/like")
public class LikeController extends BaseController{
    @Autowired
    private LikeService likeService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @RequestMapping(value = "/doLike" ,method = {RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> doLike(@RequestParam(name = "userId") Integer userId,
                      @RequestParam(name = "itemId") Integer itemId) throws BusinessException {
        //先判断一下后台用户是否在线
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("isLogin");
        if (!isLogin){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        Map<String,Object> map = new HashMap<>(2);
        if (userId != null && userId != 0) {
            //用户已经登陆，查询点赞状态
            boolean likeStatus = likeService.selectLikeStatus(userId, itemId);
            if (likeStatus){
                likeService.cancelLike(userId, itemId);
            }else{
                likeService.doLike(userId, itemId);
            }
            map.put("isLogin",true);
            map.put("likeStatus",likeStatus);
        }else{
            map.put("isLogin",false);
        }
        return map;
    }
}
