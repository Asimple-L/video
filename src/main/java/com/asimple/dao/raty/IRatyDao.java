package com.asimple.dao.raty;

import com.asimple.entity.Raty;
import com.asimple.util.IBaseDao;

import java.util.List;

public interface IRatyDao extends IBaseDao<Raty> {
    int getCountByFilm_id(String film_id);

    List<Raty> listByFilmId(String film_id);

    int deleteByFilmId(String film_id);

}
