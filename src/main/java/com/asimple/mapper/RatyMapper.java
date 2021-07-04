package com.asimple.mapper;

import com.asimple.entity.Raty;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Asimple
 * @description 评分mapper类
 */

@Repository
public interface RatyMapper extends IBaseDao<Raty> {
    /**
     * 获取影片评分人数
     *
     * @param filmId 影片id
     * @return 评分人数
     */
    int getCountByFilmId(String filmId);

    /**
     * 获取评分列表
     *
     * @param filmId 影片id
     * @return 评分列表
     */
    List<Raty> listByFilmId(String filmId);

    /**
     * 删除评分
     *
     * @param filmId 影片id
     * @return 删除成功返回1
     */
    int deleteByFilmId(String filmId);

}
