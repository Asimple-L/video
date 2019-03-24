package com.asimple.entity;

import java.io.Serializable;

/**
 * @ProjectName video
 * @Description: 弹幕实体类
 * @author: Asimple
 */
public class Bullet implements Serializable {

    private String id;
    private String film_id;
    private String text;
    private String color;
    private String position;
    private String size;
    private String time;

    public Bullet() {
    }

    public Bullet(String id, String film_id, String text, String color, String position, String size, String time) {
        this.id = id;
        this.film_id = film_id;
        this.text = text;
        this.color = color;
        this.position = position;
        this.size = size;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilm_id() {
        return film_id;
    }

    public void setFilm_id(String film_id) {
        this.film_id = film_id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "id='" + id + '\'' +
                ", film_id='" + film_id + '\'' +
                ", text='" + text + '\'' +
                ", color='" + color + '\'' +
                ", position='" + position + '\'' +
                ", size='" + size + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
