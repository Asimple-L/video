package com.asimple.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Asimple
 * @ProjectName video
 * @description 评分实体类
 */
@Data
public class Raty implements Serializable {
    /**
     * 主键id
     */
    private String id;
    /**
     * 影片id
     */
    private String film_id;
    /**
     * 评分
     */
    private String score;
    /**
     * 评分时间
     */
    private String enTime;

}
