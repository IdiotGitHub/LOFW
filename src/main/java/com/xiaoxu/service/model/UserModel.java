package com.xiaoxu.service.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created on 2019/11/26 9:08
 *
 * @Author Xiaoxu
 * @Version 1.0
 */
public class UserModel {

    private Integer id;

    @NotBlank(message = "姓名不能不填")
    private String name;

    private String account;

//    @NotBlank(message = "性别不能不填")
    private Byte gender;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0,message = "年龄不能小于0岁")
    @Max(value = 200,message = "年龄不能超过200岁")
    private Integer age;

    @NotNull(message = "手机号不能为空")
    private String telephone;

    private Byte status;

    @NotNull(message = "密码不能为空")
    private String userPassword;

    private String userImg;
    /**
     * 该用户是否被当前用户所关注
     */
    private Boolean isFollowed;
    /**
     * 用户粉丝数
     */
    private Integer fans;
    /**
     * 用户关注数
     */
    private Integer follows;
    /**
     * 用户个性签名
     */
    private String sign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(Boolean followed) {
        isFollowed = followed;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getFollows() {
        return follows;
    }

    public void setFollows(Integer follows) {
        this.follows = follows;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
