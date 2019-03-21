package com.asimple.entity;

import java.io.Serializable;

/**
 * @ProjectName video
 * @Description: 级别实体
 * @author: Asimple
 */
public class Level implements Serializable {
    private String id; // 主键id
    private int isUse; // 是否在使用
    private String name; // 名称

    public Level() {
    }

    public Level(String id, int isUse, String name) {
        this.id = id;
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
        return "Level{" +
                "id='" + id + '\'' +
                ", isUse=" + isUse +
                ", name='" + name + '\'' +
                '}';
    }
}
