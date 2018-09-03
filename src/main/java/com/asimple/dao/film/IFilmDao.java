package com.asimple.dao.film;

import com.asimple.entity.Film;
import com.asimple.util.IBaseDao;

import java.util.List;

public interface IFilmDao extends IBaseDao<Film> {

    List<Film> listByTypeId(String type_id);

    List<Film> listByTypeId(String type_id, int top);

    List<Film> listByCataLog_id(String id);

    List<Film> listByCataLog_id(String id, int top);

    List<Film> listByEvaluation(String id);

    List<Film> listByEvaluation(String id, int top);
}
