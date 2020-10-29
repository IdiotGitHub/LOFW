package com.xiaoxu.controller.view;

import com.xiaoxu.dataobject.FavouriteDao;
import com.xiaoxu.dataobject.FollowedDao;
import com.xiaoxu.dataobject.LikeDao;
import com.xiaoxu.service.model.CommentModel;

import java.util.List;

/**
 * Created on 2019/11/26 14:33
 *
 * @Author Xiaoxu
 * @Version 1.0
 * 将前端需要的数据进行聚合
 * 文章id，用户id，文章内容，文章图片，收藏数量，评论数量，评论列表，点赞数量
 */
public class ItemView {
    /**
     *
     这个ID是干嘛的?
     what the fuck?
     */
    private Integer id;

    private Integer userId;

    private String userName;

    private String userImg;

    private String context;

    private String conImg;

    private Integer favourite;

    private Integer likes;

    private Integer comments;

    private List<CommentModel> commentModels;

    private String datetime;

    private FavouriteDao isFavourite;

    private LikeDao isLike;

    private FollowedDao isFollowed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getConImg() {
        return conImg;
    }

    public void setConImg(String conImg) {
        this.conImg = conImg;
    }

    public Integer getFavourite() {
        return favourite;
    }

    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public List<CommentModel> getCommentModels() {
        return commentModels;
    }

    public void setCommentModels(List<CommentModel> commentModels) {
        this.commentModels = commentModels;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public FavouriteDao getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(FavouriteDao isFavourite) {
        this.isFavourite = isFavourite;
    }

    public LikeDao getIsLike() {
        return isLike;
    }

    public void setIsLike(LikeDao isLike) {
        this.isLike = isLike;
    }

    public FollowedDao getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(FollowedDao isFollowed) {
        this.isFollowed = isFollowed;
    }
}
