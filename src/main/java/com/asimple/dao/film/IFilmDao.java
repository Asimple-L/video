package com.asimple.dao.film;

import com.asimple.entity.Bullet;
import com.asimple.entity.Film;
import com.asimple.util.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IFilmDao extends IBaseDao<Film> {

    List<Film> listByTypeId(String type_id);

    List<Film> listByTypeId(String type_id, int top);

    List<Film> listByCataLog_id(String id);

    List<Film> listByCataLog_id(String id, int top);

    List<Film> listByEvaluation(String id);

    List<Film> listByEvaluation(String id, int top);

    List<Film> listByUser(String uid, @Param("start") int start, @Param("count") int count);

    int countListByUser(String uid);

    int addViewHistory(Map map);

    List<Map> getViewHistory(@Param("uid") String uid, @Param("start") int start, @Param("count") int count);

    int countViewHistory(Map map);

    int updateViewHistory(Map map);

    List<Bullet> getBulletByFilmId(String filmId);

    int saveBullet(Bullet bullet);

}
