package com.asimple.service.Impl;

import com.asimple.dao.film.IFilmDao;
import com.asimple.dao.raty.IRatyDao;
import com.asimple.dao.res.IResDao;
import com.asimple.dao.type.ITypeDao;
import com.asimple.entity.Film;
import com.asimple.entity.Type;
import com.asimple.service.IFilmService;
import com.asimple.service.IRatyService;
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

    @Resource(name = "IRatyDao")
    private IRatyDao ratyDao;

    @Resource(name = "IResDao")
    private IResDao resDao;


    @Override
    public List<Film> findAll() {
        return filmDao.getAll();
    }

    /**
     * @Author Asimple
     * @Description 通过类型查找电影
     **/
    @Override
    public List<Film> listByType_id(String type_id) {
        return filmDao.listByTypeId(type_id);
    }

    /**
     * @Author Asimple
     * @Description 通过类型查找电影TOP榜
     **/
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

    /**
     * @Author Asimple
     * @Description 分页查询电影
     **/
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

    /**
     * @Author Asimple
     * @Description 通过id获取Film对象
     **/
    @Override
    public Film load(String film_id) {
        return filmDao.load(film_id);
    }

    /**
     * @Author Asimple
     * @Description 更新Film信息
     **/
    @Override
    public boolean update(Film film) {
        return filmDao.update(film)==1;
    }

    /**
     * @Author Asimple
     * @Description 保存Film对象
     **/
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

    /**
     * @Author Asimple
     * @Description 删除电影
     **/
    @Override
    public boolean deleteById(String film_id) {
        // 1、删除评分信息
        int sum = ratyDao.deleteByFilmId(film_id);
        // 2、删除资源信息
        sum += resDao.deleteByFilmId(film_id);
        // 3、删除电影本身
        sum += filmDao.deleteById(film_id);
        return sum != 0;
    }


    // 添加set方法，为了变成xml配置开发的时候不会报错
    public void setFilmDao(IFilmDao filmDao) {
        this.filmDao = filmDao;
    }

    public void setTypeDao(ITypeDao typeDao) {
        this.typeDao = typeDao;
    }
}
