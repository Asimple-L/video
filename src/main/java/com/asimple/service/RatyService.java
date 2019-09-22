package com.asimple.service;

import com.asimple.entity.Raty;
import com.asimple.mapper.RatyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 评分服务层实现
 * @author: Asimple
 */
@Service
public class RatyService {
    @Resource
    private RatyMapper ratyMapper;

    public int getCountByFilm_id(String film_id) {
        return ratyMapper.getCountByFilm_id(film_id);
    }

    public int add(Raty raty) {
        return ratyMapper.add(raty);
    }

    public List<Raty> listByFilmId(String film_id) {
        return ratyMapper.listByFilmId(film_id);
    }
}
