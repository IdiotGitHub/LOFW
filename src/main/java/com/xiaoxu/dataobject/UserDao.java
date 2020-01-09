package com.xiaoxu.dataobject;

public class UserDao {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.id
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.name
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.account
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    private String account;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.age
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    private Integer age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.gender
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    private Byte gender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_img
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    private String userImg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.telephone
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    private String telephone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.status
     *
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    private Byte status;

    private String sign;

    private Integer follows;

    private Integer fans;

    private Byte onlineStatus;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.id
     *
     * @return the value of user_info.id
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.id
     *
     * @param id the value for user_info.id
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.name
     *
     * @return the value of user_info.name
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.name
     *
     * @param name the value for user_info.name
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.account
     *
     * @return the value of user_info.account
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.account
     *
     * @param account the value for user_info.account
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.age
     *
     * @return the value of user_info.age
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.age
     *
     * @param age the value for user_info.age
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.gender
     *
     * @return the value of user_info.gender
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.gender
     *
     * @param gender the value for user_info.gender
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_img
     *
     * @return the value of user_info.user_img
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public String getUserImg() {
        return userImg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_img
     *
     * @param userImg the value for user_info.user_img
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public void setUserImg(String userImg) {
        this.userImg = userImg == null ? null : userImg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.telephone
     *
     * @return the value of user_info.telephone
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.telephone
     *
     * @param telephone the value for user_info.telephone
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.status
     *
     * @return the value of user_info.status
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.status
     *
     * @param status the value for user_info.status
     * @mbg.generated Tue Nov 26 10:11:39 CST 2019
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getFollows() {
        return follows;
    }

    public void setFollows(Integer follows) {
        this.follows = follows;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Byte getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Byte onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}