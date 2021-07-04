package com.asimple.mapper;

import com.asimple.entity.Res;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Asimple
 * @description 资源mapper类
 */

@Repository
public interface ResMapper extends IBaseDao<Res> {
    /**
     * 根据影片id获取资源列表
     *
     * @param filmId 影片id
     * @return 资源列表
     */
    List<Res> getListByFilmId(String filmId);

    /**
     * 根据影片id删除资源
     *
     * @param filmId 影片id
     * @return 返回删除成功的行数
     */
    int deleteByFilmId(String filmId);

}
