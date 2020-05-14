package com.asimple.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName video
 * @description VIP码
 * @author Asimple
 */
@Data
public class VipCode implements Serializable {
    /**
     * 主键id
     */
    private String id;
    /**
     *  VIP卡码
     */
    private String code;
    /**
     * 是否使用
     */
    private int isUse;
    /**
     * 创建时间
      */
    private Date create_time;
    /**
     * 到期时间
     */
    private Date expire_time;
}
