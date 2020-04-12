package com.asimple.entity;

import java.io.Serializable;

/**
 * @ProjectName video
 * @description 级别实体
 * @author Asimple
 */
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
