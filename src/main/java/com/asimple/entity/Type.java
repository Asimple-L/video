package com.asimple.entity;


import java.io.Serializable;

/**
 * @ProjectName video
 * @description 类型实体
 * @author Asimple
 */
public class Type implements Serializable {
    private String id;
    private String name;
    private int isUse;
    private SubClass subClass;

    public Type() {
    }

    public Type(String name, int isUse, SubClass subClass) {
        this.name = name;
        this.isUse = isUse;
        this.subClass = subClass;
    }

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

    public SubClass getSubClass() {
        return subClass;
    }

    public void setSubClass(SubClass subClass) {
        this.subClass = subClass;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isUse=" + isUse +
                ", subClass=" + subClass +
                '}';
    }
}
