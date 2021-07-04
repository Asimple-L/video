package com.asimple.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Asimple
 * @ProjectName video
 * @description 年份实体类
 */
@Data
public class Decade implements Serializable {
    /**
     * 主键id
     */
    private String id;
    /**
     * 是否在使用
     */
    private int isUse;
    /**
     * 名称
     */
    private String name;

}
