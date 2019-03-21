package com.asimple.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName video
 * @Description: VIP码
 * @author: Asimple
 */
public class VipCode implements Serializable {
    private String id; // 主键id
    private String code; // VIP卡码
    private int isUse; // 是否使用
    private Date create_time; // 创建时间
    private Date expire_time; // 到期时间

    public VipCode() {
    }

    public VipCode(String code, int isUse, Date create_time, Date expire_time) {
        this.code = code;
        this.isUse = isUse;
        this.create_time = create_time;
        this.expire_time = expire_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Date expire_time) {
        this.expire_time = expire_time;
    }

    @Override
    public String toString() {
        return "VipCode{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", isUse=" + isUse +
                ", create_time=" + create_time +
                ", expire_time=" + expire_time +
                '}';
    }
}
