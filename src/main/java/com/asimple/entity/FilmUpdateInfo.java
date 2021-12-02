package com.asimple.entity;

import lombok.Data;

@Data
public class FilmUpdateInfo {

    /**
     * 主键
     */
    private String id;
    /**
     * 片名
     */
    private String name;
    /**
     * 海报图
     */
    private String image;
    /**
     * 上映年代
     */
    private String onDecade;
    /**
     * 状态
     */
    private String status;
    /**
     * 分辨率
     */
    private String resolution;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 类型Id
     */
    private String type_id;
    /**
     * 演员
     */
    private String actor;
    /**
     * 地区名称
     */
    private String locName;
    /**
     * 地区Id
     */
    private String loc_id;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 是否在使用
     */
    private int isUse;
    /**
     * 一级目录
     */
    private String cataLog_id;
    /**
     * 一级目录名称
     */
    private String cataLogName;
    /**
     * 二级目录
     */
    private String subClass_id;
    /**
     * 二级目录名称
     */
    private String subClassName;
    /**
     * 是否vip资源
     */
    private Integer isVip;
    /**
     * 剧情
     */
    private String plot;
    /**
     * 上传用户的id
     */
    private String uid;

    public FilmUpdateInfo() {
    }

    public FilmUpdateInfo(String id, String name, String image, String onDecade, String status, String resolution, String type_id, String actor, String locName, String loc_id, String updateTime, int isUse, String cataLog_id, String subClass_id, Integer isVip, String plot) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.onDecade = onDecade;
        this.status = status;
        this.resolution = resolution;
        this.type_id = type_id;
        this.actor = actor;
        this.locName = locName;
        this.loc_id = loc_id;
        this.updateTime = updateTime;
        this.isUse = isUse;
        this.cataLog_id = cataLog_id;
        this.subClass_id = subClass_id;
        this.isVip = isVip;
        this.plot = plot;
    }

}
