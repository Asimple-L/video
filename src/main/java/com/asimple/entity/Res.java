package com.asimple.entity;

import java.io.Serializable;

/**
 * @ProjectName video
 * @description 资源实体类
 * @author Asimple
 */
public class Res implements Serializable {
    private String id; // 主键id
    private int episodes; // 集
    private String name; // 资源名称
    private String link; // 资源链接
    private String linkType; // 资源类型
    private String updateTime; // 更新时间
    private int isUse; // 是否有用
    private Film film;

    public Res() {
    }

    public Res(int episodes, String name, String link, String linkType, String updateTime, int isUse, Film film) {
        this.episodes = episodes;
        this.name = name;
        this.link = link;
        this.linkType = linkType;
        this.updateTime = updateTime;
        this.isUse = isUse;
        this.film = film;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
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

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @Override
    public String toString() {
        return "Res{" +
                "id='" + id + '\'' +
                ", episodes=" + episodes +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", linkType='" + linkType + '\'' +
                ", updateTime=" + updateTime +
                ", isUse=" + isUse +
                ", film=" + film +
                '}';
    }
}
