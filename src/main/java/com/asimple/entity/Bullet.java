package com.asimple.entity;

import java.io.Serializable;

/**
 * @ProjectName video
 * @description 弹幕实体类
 * @author Asimple
 */
public class Bullet implements Serializable {

    private String id;
    private String text;
    private String color;
    private String position;
    private String size;
    private Integer time;
    private String filmId;

    public Bullet() {
    }

    public Bullet(String id, String text, String color, String position, String size, Integer time, String filmId) {
        this.id = id;
        this.text = text;
        this.color = color;
        this.position = position;
        this.size = size;
        this.time = time;
        this.filmId = filmId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    @Override
    public String toString() {
        return "{" + "\"text\":\"" + text + '"' +
                ", \"color\":\"" + color + '"' +
                ", \"position\":\"" + position + '"' +
                ", \"size\":\"" + size + '"' +
                ", \"time\":" + time + '}';
    }
}