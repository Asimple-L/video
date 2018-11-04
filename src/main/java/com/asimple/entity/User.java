package com.asimple.entity;

import java.util.Date;

/**
 * @ProjectName video
 * @Description: 用户实体类
 * @author: Asimple
 */
public class User {
    private String id;// 主键
    private String userName;// 用户名
    private String userPasswd;// 用户密码
    private String userEmail;// 用户邮箱
    private Date createDate;// 用户创建时间
    private Date expireDate;// VIP失效时间
    private long isVip;// 是否是VIP
    private int isManager;// 是否是管理员

    public User() {
    }

    public User(String userName, String userPasswd, String userEmail, Date createDate, Date expireDate, long isVip) {
        this.userName = userName;
        this.userPasswd = userPasswd;
        this.userEmail = userEmail;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.isVip = isVip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public long getIsVip() {
        return isVip;
    }

    public void setIsVip(long isVip) {
        this.isVip = isVip;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", userPasswd='" + userPasswd + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", createDate=" + createDate +
                ", expireDate=" + expireDate +
                ", isVip=" + isVip +
                ", isManager=" + isManager +
                '}';
    }
}
