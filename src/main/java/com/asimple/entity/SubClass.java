package com.asimple.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Asimple
 * @ProjectName video
 * @description 二级子类目录
 */
@Data
public class SubClass implements Serializable {
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
    private CataLog cataLog;
    private List<Type> types;
}
