package com.asimple.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName video
 * @description 电影实体
 * @author Asimple
 */
@Data
public class Film implements Serializable {
    /**
     * 主键
     */
    @Field
    private String id;
    /**
     * 片名
     */
    @Field("video_film_name")
    private String name;
    /**
     * 海报图
     */
    @Field("video_film_image")
    private String image;
    /**
     * 上映年代
     */
    @Field("video_film_onDecade")
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
    @Field("video_type_name")
    private String typeName;
    /**
     * 类型Id
     */
    @Field("video_type_id")
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
    @Field("video_film_loc_id")
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
    @Field("video_film_cataLog_id")
    private String cataLog_id;
    /**
     * 一级目录名称
     */
    private String cataLogName;
    /**
     * 二级目录
     */
    @Field("video_film_subClass_id")
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
     * 评分
     */
    private double  evaluation;
    /**
     * 下载列表
     */
    private List<Res> resList;
    /**
     * 浏览数目
     */
    @JsonProperty(value = "viewNumber")
    private Integer view_number;
    /**
     * 上传用户的id
     */
    private String uid;

    public Film() {
    }

    public Film(String id, String name, String image, String onDecade, String status, String resolution, String typeName, String type_id, String actor, String locName, String loc_id, String updateTime, int isUse, String cataLog_id, String cataLogName, String subClass_id, String subClassName, Integer isVip, String plot, double evaluation, List<Res> resList, Integer viewNumber, String uid) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.onDecade = onDecade;
        this.status = status;
        this.resolution = resolution;
        this.typeName = typeName;
        this.type_id = type_id;
        this.actor = actor;
        this.locName = locName;
        this.loc_id = loc_id;
        this.updateTime = updateTime;
        this.isUse = isUse;
        this.cataLog_id = cataLog_id;
        this.cataLogName = cataLogName;
        this.subClass_id = subClass_id;
        this.subClassName = subClassName;
        this.isVip = isVip;
        this.plot = plot;
        this.evaluation = evaluation;
        this.resList = resList;
        this.view_number = viewNumber;
        this.uid = uid;
    }

}
