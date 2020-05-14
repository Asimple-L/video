package com.asimple.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName video
 * @description 评论
 * @author Asimple
 */
@Data
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
}
