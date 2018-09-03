package com.asimple.service;

import com.asimple.entity.Raty;

import java.util.List;

public interface IRatyService {
    int getCountByFilm_id(String film_id);

    int add(Raty raty);

    List<Raty> listByFilmId(String film_id);
}
