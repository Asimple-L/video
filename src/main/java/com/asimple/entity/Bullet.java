package com.asimple.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Asimple
 * @ProjectName video
 * @description 弹幕实体类
 */
@Data
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
}