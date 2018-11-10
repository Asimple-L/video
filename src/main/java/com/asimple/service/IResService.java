package com.asimple.service;

import com.asimple.entity.Res;

import java.util.List;

public interface IResService {
    List<Res> getListByFilmId(String film_id);

    String add(Res res);

    boolean delete(String res_id);

    boolean updateIsUse(String res_id);

}
