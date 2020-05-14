package com.asimple.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName video
 * @description 用户实体类
 * @author Asimple
 */
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPasswd;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户创建时间
     */
    private Date createDate;
    /**
     * VIP失效时间
     */
    private Date expireDate;
    /**
     * 是否是VIP
     */
    private long isVip;
    /**
     * 是否是管理员
     */
    private int isManager;

}
