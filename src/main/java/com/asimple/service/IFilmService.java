package com.asimple.service;

import com.asimple.entity.Film;
import com.asimple.util.PageBean;

import java.util.List;

public interface IFilmService {

    List<Film> findAll();

    List<Film> listByType_id(String type_id);

    List<Film> listByType_id(String type_id, int top);

    List<Film> listByCataLog_id(String id);

    List<Film> listByCataLog_id(String id, int top);

    List<Film> listByEvaluation(String id);

    List<Film> listByEvaluation(String id, int top);

    PageBean<Film> getPage(Film ob, int pc, int ps);

    Film load(String film_id);

    boolean update(Film film);
    
    String save(Film film);

    boolean deleteById(String film_id);

}
