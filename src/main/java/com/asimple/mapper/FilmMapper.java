package com.asimple.mapper;

import com.asimple.entity.Bullet;
import com.asimple.entity.Film;
import com.asimple.entity.FilmUpdateInfo;
import com.asimple.util.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Asimple
 * @description 影片mapper类
 */

@Repository
public interface FilmMapper extends IBaseDao<Film> {

    /**
     * 根据类型查找影片列表
     *
     * @param typeId 类型id
     * @return 影片列表
     */
    List<Film> listByTypeId(String typeId);

    /**
     * 根据类型查找影片列表
     *
     * @param typeId 类型id
     * @param top    前top
     * @return 影片列表
     */
    List<Film> listByTypeId(String typeId, int top);

    /**
     * 根据分类查找影片列表
     *
     * @param id 分类id
     * @return 影片列表
     */
    List<Film> listByCataLogId(String id);

    /**
     * 根据分类查找影片列表
     *
     * @param id  分类id
     * @param top 前top
     * @return 影片列表
     */
    List<Film> listByCataLogId(String id, int top);

    /**
     * 有条件查询列表
     *
     * @param id 影片id
     * @return 影片列表
     */
    List<Film> listByEvaluation(String id);

    /**
     * 有条件查询列表
     *
     * @param id  影片id
     * @param top 前top
     * @return 影片列表
     */
    List<Film> listByEvaluation(String id, int top);

    /**
     * 查询用户创建的影片列表
     *
     * @param uid   用户id
     * @param start 开始条数
     * @param count 结束条数
     * @return 影片列表
     */
    List<Film> listByUser(String uid, @Param("start") int start, @Param("count") int count);

    /**
     * 统计用户创建的影片数
     *
     * @param uid 用户id
     * @return 用户创建的影片数
     */
    int countListByUser(String uid);

    /**
     * 添加访问历史记录
     *
     * @param map 参数
     * @return 添加成功返回1
     */
    int addViewHistory(Map map);

    /**
     * 分页获取浏览记录
     *
     * @param uid   用户id
     * @param start 开始条数
     * @param count 结束条数
     * @return 影片列表
     */
    List<Map> getViewHistory(@Param("uid") String uid, @Param("start") int start, @Param("count") int count);

    /**
     * 统计浏览记录总数
     *
     * @param map 参数
     * @return 浏览记录总数
     */
    int countViewHistory(Map map);

    /**
     * 更新浏览记录
     *
     * @param map 参数
     * @return 更新成功返回1
     */
    int updateViewHistory(Map map);

    /**
     * 查询影片弹幕列表
     *
     * @param filmId 影片id
     * @return 弹幕列表
     */
    List<Bullet> getBulletByFilmId(String filmId);

    /**
     * 保存弹幕
     *
     * @param bullet 弹幕实体类
     * @return 添加成功返回1
     */
    int saveBullet(Bullet bullet);

    /**
     * 删除影片弹幕
     *
     * @param filmId 影片ID
     * @return 删除成功返回1
     */
    int deleteBullet(String filmId);

    /**
     * 更新影片信息
     *
     * @param film 影片信息
     * @return 更新成功返回1
     */
    int updateFilm(FilmUpdateInfo film);

}
