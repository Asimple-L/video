package com.asimple.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @author Asimple
 * @ProjectName video
 * @description 类型实体
 */
@Data
public class Type implements Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否可用
     */
    private int isUse;
    private SubClass subClass;
}
