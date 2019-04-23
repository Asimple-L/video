package com.asimple.service;

import com.asimple.entity.Bullet;
import com.asimple.entity.Film;
import com.asimple.util.PageBean;

import java.util.List;
import java.util.Map;

public interface IFilmService {

    List<Film> findAll();

    List<Film> listByType_id(String type_id);

    List<Film> listByType_id(String type_id, int top);

    List<Film> listByCataLog_id(String id);

    List<Film> listByCataLog_id(String id, int top);

    List<Film> listByEvaluation(String id);

    List<Film> listByEvaluation(String id, int top);

    List<Film> listByUser(String uid, int pc, int ps);

    int countListByUser(String uid);

    void addViewHistory(String filmId, String uid);

    List<Map> getViewHistory(String uid, int pc, int ps);

    int countViewHistory(String uid);

    PageBean<Film> getPage(Film ob, int pc, int ps);

    Film load(String film_id);

    boolean update(Film film);
    
    String save(Film film);

    boolean deleteById(String film_id);

    List<Bullet> getBulletByFilmId(String filmId);

    boolean saveBullet(Bullet bullet);

}
