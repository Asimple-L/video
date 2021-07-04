package com.asimple.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Asimple
 * @ProjectName video
 * @description 一级分类实体类
 */
@Data
public class CataLog implements Serializable {
    /**
     * 主键id
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否在使用
     */
    private int isUse;
    /**
     * 排序级别
     */
    private int sort;
    /**
     * 是否VIP可用
     */
    private int isVip;
    /**
     * 二级分类
     */
    private List<SubClass> subClassList;

}
