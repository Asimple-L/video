package com.asimple.service.Impl;

import com.asimple.dao.raty.IRatyDao;
import com.asimple.entity.Raty;
import com.asimple.service.IRatyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 评分服务层实现
 * @author: Asimple
 */
@Service("ratyService")
public class RatyServiceImpl implements IRatyService {
    @Resource(name = "IRatyDao")
    private IRatyDao ratyDao;

    @Override
    public int getCountByFilm_id(String film_id) {
        return ratyDao.getCountByFilm_id(film_id);
    }

    @Override
    public int add(Raty raty) {
        return ratyDao.add(raty);
    }

    @Override
    public List<Raty> listByFilmId(String film_id) {
        return ratyDao.listByFilmId(film_id);
    }
}
