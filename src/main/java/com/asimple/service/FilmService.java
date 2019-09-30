package com.asimple.service;

import com.asimple.entity.Bullet;
import com.asimple.entity.Film;
import com.asimple.entity.Type;
import com.asimple.mapper.FilmMapper;
import com.asimple.mapper.RatyMapper;
import com.asimple.mapper.ResMapper;
import com.asimple.mapper.TypeMapper;
import com.asimple.util.DateUtil;
import com.asimple.util.LogUtil;
import com.asimple.util.PageBean;
import com.asimple.util.Tools;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @ProjectName video
 * @description 电影service实现类
 * @author Asimple
 */

@Service
public class FilmService {

    @Resource
    private FilmMapper filmMapper;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private RatyMapper ratyMapper;
    @Resource
    private ResMapper resMapper;
    @Resource
    private SolrTemplate solrTemplate;

    public List<Film> findAll() {
        return filmMapper.getAll();
    }

    /**
     * @author Asimple
     * @description 通过类型查找电影
     **/
    public List<Film> listByType_id(String type_id) {
        return filmMapper.listByTypeId(type_id);
    }

    /**
     * @author Asimple
     * @description 通过类型查找电影TOP榜
     **/
    public List<Film> listByType_id(String type_id, int top) {
        return filmMapper.listByTypeId(type_id, top);
    }

    /**
     * @author Asimple
     * @description 通过分类查找电影列表
     **/
    public List<Film> listByCataLog_id(String id) {
        return filmMapper.listByCataLog_id(id);
    }

    /**
     * @author Asimple
     * @description 通过分类查找前12的电影
     **/
    @Cacheable(value = "index_filmTuijian")
    public List<Film> listByCataLog_id(String id, int top) {
        return filmMapper.listByCataLog_id(id, top);
    }

    /**
     * @author Asimple
     * @description  查找评分排行电影
     **/
    public List<Film> listByEvaluation(String id) {
        return filmMapper.listByEvaluation(id);
    }

    /**
     * @author Asimple
     * @description  查找评分排行前top的电影
     **/
    @Cacheable( value = "index_filmPaiHang")
    public List<Film> listByEvaluation(String id, int top) {
        return filmMapper.listByEvaluation(id, top);
    }

    /**
     * @author Asimple
     * @description 分页查询电影
     **/
    public PageBean<Film> getPage(Film ob, int pc, int ps) {
        PageBean<Film> pb = new PageBean<>();
        pb.setPc(pc);
        pb.setPs(ps);
        // 设置总数
        pb.setTr(filmMapper.getTotalCount(ob));
        pb.setBeanList(filmMapper.getPage(ob, (pc-1)*ps, ps));
        return pb;
    }

    /**
     * 通过id获取Film对象
     * @param filmId 根据id记载影片对象
     * @return 影片对象
     */
    public Film load(String filmId) {
        return filmMapper.load(filmId);
    }

    /**
     * @author Asimple
     * @description 更新Film信息
     **/
    public boolean update(Film film) {
        return filmMapper.update(film)==1;
    }

    /**
     * 保存Film对象
     * @param film 电影对象
     * @return 保存成功返回id
     */
    public String save(Film film) {
        // 初始化参数
        film.setIsUse(1);
        film.setUpdateTime(DateUtil.getTime());
        film.setEvaluation(0);
        film.setId(Tools.UUID());

        // 查询出类型
        Type type = typeMapper.load(film.getType_id());
        film.setSubClass_id(type.getSubClass().getId());
        film.setSubClassName(type.getSubClass().getName());
        film.setCataLog_id(type.getSubClass().getCataLog().getId());
        film.setCataLogName(type.getSubClass().getCataLog().getName());

        return filmMapper.add(film)!=0?film.getId():"0";
    }

    /**
     * @author Asimple
     * @description 删除电影
     **/
    public boolean deleteById(String film_id) {
        // 1、删除评分信息
        int sum = ratyMapper.deleteByFilmId(film_id);
        // 2、删除资源信息
        sum += resMapper.deleteByFilmId(film_id);
        // 3、删除电影本身
        sum += filmMapper.deleteById(film_id);
        return sum != 0;
    }

    /**
     * @author Asimple
     * @description 根据视频获取弹幕
     **/
    public List<Bullet> getBulletByFilmId(String filmId) {
        return filmMapper.getBulletByFilmId(filmId);
    }

    /**
     * @author Asimple
     * @description 保存弹幕
     **/
    public boolean saveBullet(Bullet bullet) {
        bullet.setId(Tools.UUID());
        return filmMapper.saveBullet(bullet)!=0;
    }

    /**
     * @author Asimple
     * @description 查找用户上传的视频
     **/
    public List<Film> listByUser(String uid, int pc, int ps){
        int start = (pc-1)*ps;
        return filmMapper.listByUser(uid, start, ps);
    }

    /**
     * @author Asimple
     * @description 统计用户上传的视频
     **/
    public int countListByUser(String uid) {
        return filmMapper.countListByUser(uid);
    }

    /**
     * @author Asimple
     * @description 添加用户浏览记录
     **/
    public void addViewHistory(String filmId, String uid){
        Map map = new HashMap();
        map.put("film_id", filmId);
        map.put("uid", uid);
        map.put("view_date", new Date());
        int num = 0;
        if( filmMapper.countViewHistory(map)>0 ) {
            num = filmMapper.updateViewHistory(map);
        } else {
            num = filmMapper.addViewHistory(map);
        }
        if( num == 0 ) {
            LogUtil.error("浏览记录添加失败！");
        }
    }

    /**
     * @author Asimple
     * @description 获取用户浏览记录
     **/
    public List<Map> getViewHistory(String uid, int pc, int ps){
        List<Map> temp = filmMapper.getViewHistory(uid, (pc-1)*ps, ps);
        List<Map> res = new ArrayList<>();
        for(int i=0; i<temp.size(); i++) {
            Map item = temp.get(i);
            Film film = load((String)item.get("film_id"));
            item.put("film", film);
            res.add(item);
        }
        return res;
    }

    public int countViewHistory(String uid) {
        Map map = new HashMap();
        map.put("uid", uid);
        return filmMapper.countViewHistory(map);
    }

    public Map<String, Object> getFilmList(HttpServletRequest request) {
        String name = request.getParameter("name");
        Map<String, Object> result = new HashMap<>(8);
        if( !Tools.isEmpty(name) ) {
            result.put("name", name);
        }
        // 分页查询所有电影列表
        // 获取url条件信息
        String url = request.getQueryString();
        if( url != null ) {
            int index = url.indexOf("&pc=");
            if( index != -1 ) {
                url = url.substring(0, index);
            }
        }
        // 当前页面数
        String value = request.getParameter("pc");
        int pc = 1;
        if( Tools.notEmpty(value) ) {
            pc = Integer.parseInt(value);
        }
        // 每页显示数目
        int ps = 27;
        // 获取页面传递的查询条件
        Film ob = Tools.toBean(request.getParameterMap(), Film.class);

        PageBean<Film> pageBean = getPage(ob, pc, ps);
        pageBean.setUrl(url);
        result.put("pb", pageBean);
        return result;
    }

    /**
     * TODO SpringBoot Solr整合有问题，还在解决中
     * 报错信息：org.apache.solr.client.solrj.impl.HttpSolrClient$RemoteSolrException: Error from server at http://127.0.0.1:8090/solr/video: Expected mime type application/octet-stream but got text/html.
     * 未改成SpringBoot的时候，是正常运行的。
     */
    public Map<String, Object> getFilmOfSolr(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(8);
        PageBean<Film> pageBean = new PageBean<>();
        String name = request.getParameter("name");
        if( !Tools.isEmpty(name) ) {
            result.put("name", name);
        }
        // 待改进
        String url = request.getQueryString();
        if( url != null ) {
            int index = url.indexOf("&pc=");
            if( index != -1 ) {
                url = url.substring(0, index);
            }
        }
        // 当前页面数
        String value = request.getParameter("pc");
        int pc = 1;
        if( Tools.notEmpty(value) ) {
            pc = Integer.parseInt(value);
        }
        pageBean.setPc(pc);
        // 每页显示数目
        int ps = 27;
        pageBean.setPs(ps);
        // 获取页面传递的查询条件
        Film ob = Tools.toBean(request.getParameterMap(), Film.class);
        pageBean.setUrl(url);

        HighlightQuery query = new SimpleHighlightQuery(new SimpleStringCriteria("*:*"));
        if( Tools.notEmpty(name) ) {
            Criteria filterCriteria = new Criteria("video_film_name").is("\"*"+name+"*\"");
            FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }
        query.setOffset((pc-1)*ps);//开始索引（默认0）
        query.setRows(ps);//每页记录数(默认10)

        //***********************高亮设置***********************
        //设置高亮的域
        HighlightOptions highlightOptions = new HighlightOptions().addField("video_film_name");
        //高亮前缀
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        //高亮后缀
        highlightOptions.setSimplePostfix("</em>");
        //设置高亮选项
        query.setHighlightOptions(highlightOptions);

        //***********************获取数据***********************
        HighlightPage<Film> page = solrTemplate.queryForHighlightPage(query, Film.class);

        //***********************设置高亮结果***********************
        List<HighlightEntry<Film>> highlighted = page.getHighlighted();
        for (HighlightEntry<Film> h : highlighted) {//循环高亮入口集合
            Film item = h.getEntity();//获取原实体类
            if (h.getHighlights().size() > 0 && h.getHighlights().get(0).getSnipplets().size() > 0) {
                item.setName(h.getHighlights().get(0).getSnipplets().get(0));//设置高亮的结果
            }
        }
        System.err.println(page.getContent());
        pageBean.setBeanList(page.getContent());
        pageBean.setTr((int)page.getTotalElements());
        result.put("pb", pageBean);
        return result;
    }


}
