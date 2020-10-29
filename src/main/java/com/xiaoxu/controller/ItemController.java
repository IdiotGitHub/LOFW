package com.xiaoxu.controller;

import com.xiaoxu.controller.view.ItemView;
import com.xiaoxu.dataobject.FavouriteDao;
import com.xiaoxu.dataobject.FollowedDao;
import com.xiaoxu.dataobject.LikeDao;
import com.xiaoxu.error.BusinessException;
import com.xiaoxu.error.EmBusinessError;
import com.xiaoxu.response.CommonReturnType;
import com.xiaoxu.response.PageBean;
import com.xiaoxu.service.AdmUserService;
import com.xiaoxu.service.CommentService;
import com.xiaoxu.service.ItemService;
import com.xiaoxu.service.UserService;
import com.xiaoxu.service.model.ItemModel;
import com.xiaoxu.service.model.UserModel;
import com.xiaoxu.utils.UpLoadFile;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created on 2019/11/26 14:48
 *
 * @author Xiaoxu
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ItemController extends BaseController {

    @Resource
    private ItemService itemService;
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private AdmUserService admUserService;

    @RequestMapping(value = "createItem", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "context") String context,
                                       @RequestParam(name = "userId") Integer userId,
                                       MultipartFile conImg
    ) throws BusinessException {
    /*
    先判断是否登录
    未登录先登录
     */
        Boolean isLogin = (Boolean) request.getSession().getAttribute("isLogin");
        if (isLogin == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        UserModel userModel = (UserModel) request.getSession().getAttribute("loginUser");
        if (!userId.equals(userModel.getId())) {
            throw new BusinessException(EmBusinessError.LOGIN_USER_DIFFERENT);
        }
        String newFileName = "";
        String preFile = "./img/conImg/";
        if (conImg != null) {
            String localPath = "E:/bootstrap-3.3.7-dist/img/conImg";
            newFileName = preFile + UpLoadFile.upLoadFile(conImg, localPath, conImg.getOriginalFilename());
        }
        ItemModel itemModel = new ItemModel();
        itemModel.setUserId(userId);
        itemModel.setUserName(userModel.getName());
        itemModel.setConImg(newFileName);
        itemModel.setContext(context);
        itemModel.setDatetime(new Date(System.currentTimeMillis()));
        itemService.createItem(itemModel);
        ItemView itemView = convertItemViewFromItemModel(itemModel);
        return CommonReturnType.create(itemView);
    }

    /**
     * 获取帖子列表，以分页的形式展示
     *
     * @return 返回通用类型
     */
    @RequestMapping("/getUserItems")
    @ResponseBody
    public CommonReturnType getUserItemsForPage(@RequestParam(name = "search") String search,
                                                @RequestParam(name = "currentPage") Integer currentPage,
                                                @RequestParam(name = "pageSize") Integer pageSize,
                                                @RequestParam(name = "userId") Integer userId) throws BusinessException {
        if (currentPage == null | pageSize == null) {
            currentPage = 1;
            pageSize = 5;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        List<ItemView> itemViews = new ArrayList<>();
        //获取基本的帖子信息
        PageBean<ItemModel> pageBean = itemService.getUserItemModelForPage(search, currentPage, pageSize, userId);
        //根据基本的帖子信息获取所有的关于帖子的信息(这里只有评论信息)，并进行聚合操作，封装到ItemView中返回给前端
        //使用lambda表达式进行操作
        pageBean.getList().forEach(itemModel -> {
            //将ItemModel转成前端需要的ItemView
            itemViews.add(convertItemViewFromItemModel(itemModel));
        });
        //判断一下前端是否有用户id传过来，有就说明用户已经登陆，反之无
        Boolean isLogin = (Boolean) request.getSession().getAttribute("isLogin");
        UserModel loginUser = (UserModel) request.getSession().getAttribute("loginUser");
        if (isLogin != null && isLogin) {
            getLoginItemViews(itemViews, loginUser);
        } else {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "error");
        }
        //将PageBean<ItemModel>转成PageBean<ItemView>
        return CommonReturnType.create(convertPageBean(pageBean, itemViews));
    }

    @RequestMapping("/transmit")
    @ResponseBody
    public CommonReturnType transmit(@RequestParam(name = "itemId") Integer itemId,
                                     @RequestParam(name = "userId") Integer userId) {
        itemService.transmit(itemId, userId);
        return CommonReturnType.create(null);
    }


    /**
     * 获取帖子列表，以分页的形式展示
     *
     * @return 返回通用类型
     */
    @RequestMapping("/getUserItemsByFavourite")
    @ResponseBody
    public CommonReturnType getUserItemsForPageByFavourite(@RequestParam(name = "search") String search,
                                                           @RequestParam(name = "currentPage") Integer currentPage,
                                                           @RequestParam(name = "pageSize") Integer pageSize,
                                                           @RequestParam(name = "userId") Integer userId) throws BusinessException {
        if (currentPage == null | pageSize == null) {
            currentPage = 1;
            pageSize = 5;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        List<ItemView> itemViews = new ArrayList<>();
        //获取基本的帖子信息
        PageBean<ItemModel> pageBean = itemService.getUserItemModelForPageByFavourite(search, currentPage, pageSize, userId);
        //根据基本的帖子信息获取所有的关于帖子的信息(这里只有评论信息)，并进行聚合操作，封装到ItemView中返回给前端
        //使用lambda表达式进行操作
        pageBean.getList().forEach(itemModel -> {
            //将ItemModel转成前端需要的ItemView
            itemViews.add(convertItemViewFromItemModel(itemModel));
        });
        //判断一下前端是否有用户id传过来，有就说明用户已经登陆，反之无
        Boolean isLogin = (Boolean) request.getSession().getAttribute("isLogin");
        UserModel loginUser = (UserModel) request.getSession().getAttribute("loginUser");
        if (isLogin != null && isLogin) {
            getLoginItemViews(itemViews, loginUser);
        } else {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "error");
        }
        //将PageBean<ItemModel>转成PageBean<ItemView>
        return CommonReturnType.create(convertPageBean(pageBean, itemViews));
    }

    /**
     * 获取帖子列表，以分页的形式展示
     *
     * @return 返回通用类型
     */
    @RequestMapping("/getItems")
    @ResponseBody
    public CommonReturnType getItemsForPage(@RequestParam(name = "search") String search,
                                            @RequestParam(name = "currentPage") Integer currentPage,
                                            @RequestParam(name = "pageSize") Integer pageSize,
                                            @RequestParam(name = "userId") Integer userId) throws BusinessException {
        //userId等于0要不就是未登陆，要不就是加载广场，所以不用管是哪种情况，直接加载广场页面就可以了
        if (currentPage == null | pageSize == null) {
            currentPage = 1;
            pageSize = 5;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        List<ItemView> itemViews = new ArrayList<>();
        //获取基本的帖子信息
        PageBean<ItemModel> pageBean = itemService.getItemModelForPage(search, currentPage, pageSize, userId);
        //根据基本的帖子信息获取所有的关于帖子的信息(这里只有评论信息)，并进行聚合操作，封装到ItemView中返回给前端
        //使用lambda表达式进行操作
        pageBean.getList().forEach(itemModel -> {
            //将ItemModel转成前端需要的ItemView
            itemViews.add(convertItemViewFromItemModel(itemModel));
        });
        //判断一下前端是否有用户id传过来，有就说明用户已经登陆，反之无
        Boolean isLogin = (Boolean) request.getSession().getAttribute("isLogin");
        UserModel loginUser = (UserModel) request.getSession().getAttribute("loginUser");
        if (isLogin != null && isLogin) {
            getLoginItemViews(itemViews, loginUser);
        } else {
            itemViews.forEach(itemView -> {
                //将所有评论装进itemView中的list中
                itemView.setCommentModels(commentService.selectByItemId(itemView.getId()));
            });
        }
        //将PageBean<ItemModel>转成PageBean<ItemView>
        return CommonReturnType.create(convertPageBean(pageBean, itemViews));
    }

    private void getLoginItemViews(List<ItemView> itemViews, UserModel loginUser) {
        itemViews.forEach(itemView -> {
            Map<String, Object> map = userService.selectAction(loginUser.getId(), itemView.getId(), itemView.getUserId());
            itemView.setIsFavourite((FavouriteDao) map.get("isFavourite"));
            itemView.setIsLike((LikeDao) map.get("isLike"));
            itemView.setIsFollowed((FollowedDao) map.get("isFollowed"));
            //将所有评论装进itemView中的list中
            itemView.setCommentModels(commentService.selectByItemId(itemView.getId()));
        });
    }

    private ItemView convertItemViewFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemView itemView = new ItemView();
        BeanUtils.copyProperties(itemModel, itemView);
        //如果不进行时间转换传递到前端会发生格式错误
        itemView.setDatetime(getFormatTime(itemModel.getDatetime()));
        return itemView;
    }

    private PageBean<ItemView> convertPageBean(PageBean<ItemModel> pageBean, List<ItemView> itemViews) {
        PageBean<ItemView> pageBean1 = new PageBean<>();
        BeanUtils.copyProperties(pageBean, pageBean1);
        pageBean1.setList(itemViews);
        return pageBean1;
    }

    private String getFormatTime(Date date) {
        String formatTime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatTime = dateFormat.format(date);
        return formatTime;
    }

}
