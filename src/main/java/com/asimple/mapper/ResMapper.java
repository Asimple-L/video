package com.asimple.mapper;

import com.asimple.entity.Res;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResMapper extends IBaseDao<Res> {
    List<Res> getListByFilmId(String film_id);

    int deleteByFilmId(String film_id);

}
