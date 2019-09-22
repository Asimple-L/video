package com.asimple.mapper;

import com.asimple.entity.Raty;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatyMapper extends IBaseDao<Raty> {
    int getCountByFilm_id(String film_id);

    List<Raty> listByFilmId(String film_id);

    int deleteByFilmId(String film_id);

}
