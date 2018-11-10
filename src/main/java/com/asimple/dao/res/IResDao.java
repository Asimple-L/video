package com.asimple.dao.res;

import com.asimple.entity.Res;
import com.asimple.util.IBaseDao;

import java.util.List;

public interface IResDao extends IBaseDao<Res> {
    List<Res> getListByFilmId(String film_id);

    int deleteByFilmId(String film_id);

}
