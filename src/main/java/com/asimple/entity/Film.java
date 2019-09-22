package com.asimple.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 电影实体
 * @author: Asimple
 */
public class Film implements Serializable {
    @Field
    private String id;// 主键
    @Field("video_film_name")
    private String name;// 片名
    @Field("video_film_image")
    private String image;// 海报图
    @Field("video_film_onDecade")
    private String onDecade;//上影年代
    private String status;//状态
    private String resolution;//分辨率
    @Field("video_type_name")
    private String typeName;//类型名称
    @Field("video_type_id")
    private String type_id;//类型Id
    private String actor;//演员
    private String locName;//地区名称
    @Field("video_film_loc_id")
    private String loc_id;//地区Id
    private String updateTime;//更新时间
    private int isUse;//是否在使用
    @Field("video_film_cataLog_id")
    private String cataLog_id;//一级目录
    private String cataLogName;//一级目录名称
    @Field("video_film_subClass_id")
    private String subClass_id;//二级目录
    private String subClassName;//二级目录名称
    private Integer isVip;//是否vip资源
    private String plot;//剧情
    private double  evaluation;//评分
    // 下载列表
    private List<Res> resList;
    // 浏览数目
    @JsonProperty(value = "viewNumber")
    private Integer view_number;
    // 上传用户
    private User user;

    public Film() {
    }

    public Film(String id, String name, String image, String onDecade, String status, String resolution, String typeName, String type_id, String actor, String locName, String loc_id, String updateTime, int isUse, String cataLog_id, String cataLogName, String subClass_id, String subClassName, Integer isVip, String plot, double evaluation, List<Res> resList, Integer viewNumber, User user) {
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
        this.user = user;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOnDecade() {
        return onDecade;
    }

    public void setOnDecade(String onDecade) {
        this.onDecade = onDecade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(String loc_id) {
        this.loc_id = loc_id;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getCataLog_id() {
        return cataLog_id;
    }

    public void setCataLog_id(String cataLog_id) {
        this.cataLog_id = cataLog_id;
    }

    public String getCataLogName() {
        return cataLogName;
    }

    public void setCataLogName(String cataLogName) {
        this.cataLogName = cataLogName;
    }

    public String getSubClass_id() {
        return subClass_id;
    }

    public void setSubClass_id(String subClass_id) {
        this.subClass_id = subClass_id;
    }

    public String getSubClassName() {
        return subClassName;
    }

    public void setSubClassName(String subClassName) {
        this.subClassName = subClassName;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    public List<Res> getResList() {
        return resList;
    }

    public void setResList(List<Res> resList) {
        this.resList = resList;
    }

    public Integer getViewNumber() {
        return view_number;
    }

    public void setView_number(Integer view_number) {
        this.view_number = view_number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", onDecade='" + onDecade + '\'' +
                ", status='" + status + '\'' +
                ", resolution='" + resolution + '\'' +
                ", typeName='" + typeName + '\'' +
                ", type_id='" + type_id + '\'' +
                ", actor='" + actor + '\'' +
                ", locName='" + locName + '\'' +
                ", loc_id='" + loc_id + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", isUse=" + isUse +
                ", cataLog_id='" + cataLog_id + '\'' +
                ", cataLogName='" + cataLogName + '\'' +
                ", subClass_id='" + subClass_id + '\'' +
                ", subClassName='" + subClassName + '\'' +
                ", isVip=" + isVip +
                ", plot='" + plot + '\'' +
                ", evaluation=" + evaluation +
                ", resList=" + resList +
                ", viewNumber=" + view_number +
                ", user=" + user +
                '}';
    }
}
