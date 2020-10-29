package com.xiaoxu.service.model;

import java.util.Date;

/**
 * Created on 2019/11/26 14:25
 *
 * @author Xiaoxu
 * 根据这些属性就可以查询所有有关此文章的所有信息，在view层进行聚合操作，将前端所需展现的信息进行聚合
 */
public class ItemModel {

    private Integer id;

    private String context;

    private Integer userId;

    private String userName;

    private String userImg;

    private String conImg;

    private Integer favourite;

    private Integer likes;

    private Integer comments;

    private Date datetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void setComments(Integer comments) {

        this.comments = comments;
    }
}
