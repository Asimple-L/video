package com.asimple.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName video
 * @description 资源实体类
 * @author Asimple
 */
@Data
public class Res implements Serializable {
    /**
     * 主键id
     */
    private String id;
    /**
     * 集
     */
    private int episodes;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源链接
     */
    private String link;
    /**
     * 资源类型
     */
    private String linkType;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 是否有用
     */
    private int isUse;
    private Film film;

}
