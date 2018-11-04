package com.asimple.service.Impl;

import com.asimple.dao.film.IFilmDao;
import com.asimple.dao.type.ITypeDao;
import com.asimple.entity.Film;
import com.asimple.entity.Type;
import com.asimple.service.IFilmService;
import com.asimple.util.DateUtil;
import com.asimple.util.PageBean;
import com.asimple.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 电影service实现类
 * @author: Asimple
 */

@Service("filmService")
public class FilmServiceImpl implements IFilmService  {

    @Resource(name="IFilmDao")
    private IFilmDao filmDao;

    @Resource(name="ITypeDao")
    private ITypeDao typeDao;

    @Override
    public List<Film> listByType_id(String type_id) {
        return filmDao.listByTypeId(type_id);
    }

    @Override
    public List<Film> listByType_id(String type_id, int top) {
        return filmDao.listByTypeId(type_id, top);
    }

    /**
     * @Author Asimple
     * @Description 通过分类查找电影列表
     **/
    @Override
    public List<Film> listByCataLog_id(String id) {
        return filmDao.listByCataLog_id(id);
    }

    /**
     * @Author Asimple
     * @Description 通过分类查找前12的电影
     **/
    @Override
    public List<Film> listByCataLog_id(String id, int top) {
        return filmDao.listByCataLog_id(id, top);
    }

    /**
     * @Author Asimple
     * @Description  查找评分排行电影
     **/
    @Override
    public List<Film> listByEvaluation(String id) {
        return filmDao.listByEvaluation(id);
    }

    /**
     * @Author Asimple
     * @Description  查找评分排行前top的电影
     **/
    @Override
    public List<Film> listByEvaluation(String id, int top) {
        return filmDao.listByEvaluation(id, top);
    }

    @Override
    public PageBean<Film> getPage(Film ob, int pc, int ps) {
        PageBean<Film> pb = new PageBean<>();
        pb.setPc(pc);
        pb.setPs(ps);
        // 设置总数
        pb.setTr(filmDao.getTotalCount(ob));
        pb.setBeanList(filmDao.getPage(ob, (pc-1)*ps, ps));
        return pb;
    }

    @Override
    public Film load(String film_id) {
        return filmDao.load(film_id);
    }

    @Override
    public boolean update(Film film) {
        return filmDao.update(film);
    }

    @Override
    public String save(Film film) {
        // 初始化参数
        film.setIsUse(1);
        film.setUpdateTime(DateUtil.getTime());
        film.setEvaluation(0);
        film.setId(Tools.UUID());

        // 查询出类型
        Type type = typeDao.load(film.getType_id());
        film.setSubClass_id(type.getSubClass().getId());
        film.setSubClassName(type.getSubClass().getName());
        film.setCataLog_id(type.getSubClass().getCataLog().getId());
        film.setCataLogName(type.getSubClass().getCataLog().getName());

        return filmDao.add(film)!=0?film.getId():"0";
    }




    // 添加set方法，为了变成xml配置开发的时候不会报错
    public void setFilmDao(IFilmDao filmDao) {
        this.filmDao = filmDao;
    }

    public void setTypeDao(ITypeDao typeDao) {
        this.typeDao = typeDao;
    }
}
