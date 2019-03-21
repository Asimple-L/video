package com.asimple.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 二级子类目录
 * @author: Asimple
 */

public class SubClass implements Serializable {
    private String id;
    private String name;
    private int isUse;
    private CataLog cataLog;
    private List<Type> types;

    public SubClass() {
    }

    public SubClass(String name, int isUse, CataLog cataLog, List<Type> types) {
        this.name = name;
        this.isUse = isUse;
        this.cataLog = cataLog;
        this.types = types;
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

    public CataLog getCataLog() {
        return cataLog;
    }

    public void setCataLog(CataLog cataLog) {
        this.cataLog = cataLog;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "SubClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isUse=" + isUse +
                ", cataLog=" + cataLog +
                ", types=" + types +
                '}';
    }
}
