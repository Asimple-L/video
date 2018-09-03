package com.asimple.entity;

import java.util.Date;

/**
 * @ProjectName video
 * @Description: 资源实体类
 * @author: Asimple
 */
public class Res {
    private String id; // 主键id
    private int episodes; // 集
    private String name; // 资源名称
    private String link; // 资源链接
    private String linkType; // 资源类型
    private Date updateTime; // 更新时间
    private int isUse; // 是否有用
    private Film film;

    public Res() {
    }

    public Res(int episodes, String name, String link, String linkType, Date updateTime, int isUse, Film film) {
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
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
