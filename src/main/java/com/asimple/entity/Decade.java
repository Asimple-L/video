package com.asimple.entity;

import java.io.Serializable;

/**
 * @ProjectName video
 * @Description: 年份实体类
 * @author: Asimple
 */
public class Decade implements Serializable {
    private String id; // 主键id
    private int isUse; // 是否在使用
    private String name; // 名称

    public Decade() {
    }

    public Decade(int isUse, String name) {
        this.isUse = isUse;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Decade{" +
                "id='" + id + '\'' +
                ", isUse=" + isUse +
                ", name='" + name + '\'' +
                '}';
    }
}
