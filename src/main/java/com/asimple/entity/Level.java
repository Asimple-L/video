package com.asimple.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName video
 * @description 级别实体
 * @author Asimple
 */
@Data
public class Level implements Serializable {
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
