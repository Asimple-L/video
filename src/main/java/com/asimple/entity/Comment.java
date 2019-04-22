package com.asimple.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName video
 * @Description: 评论
 * @author: Asimple
 */
public class Comment implements Serializable {

    // 主键
    private String id;
    // 正文
    private String context;
    // 创建时间
    private Date date_create;
    // 修改时间
    private Date date_update;
    // 点赞数
    private Integer likeNum;
    // 踩数
    private Integer unlikeNum;
    // 创建用户
    private User user;

    public Comment() {
    }

    public Comment(String id, String context, Date date_create, Date date_update, Integer likeNum, Integer unlikeNum, User user) {
        this.id = id;
        this.context = context;
        this.date_create = date_create;
        this.date_update = date_update;
        this.likeNum = likeNum;
        this.unlikeNum = unlikeNum;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getDate_create() {
        return date_create;
    }

    public void setDate_create(Date date_create) {
        this.date_create = date_create;
    }

    public Date getDate_update() {
        return date_update;
    }

    public void setDate_update(Date date_update) {
        this.date_update = date_update;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getUnlikeNum() {
        return unlikeNum;
    }

    public void setUnlikeNum(Integer unlikeNum) {
        this.unlikeNum = unlikeNum;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", context='" + context + '\'' +
                ", date_create=" + date_create +
                ", date_update=" + date_update +
                ", likeNum=" + likeNum +
                ", unlikeNum=" + unlikeNum +
                ", user=" + user +
                '}';
    }
}
