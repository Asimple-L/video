package com.asimple.entity;

import java.util.List;

/**
 * @ProjectName video
 * @Description: 一级分类实体类
 * @author: Asimple
 */
public class CataLog {
    private String id; // 主键id
    private String name; // 名称
    private int isUse;  // 是否在使用
    // 二级分类
    private List<SubClass> subClassList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public List<SubClass> getSubClassList() {
        return subClassList;
    }

    public void setSubClassList(List<SubClass> subClassList) {
        this.subClassList = subClassList;
    }

    @Override
    public String toString() {
        return "CataLog{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isUse=" + isUse +
                '}';
    }
}
