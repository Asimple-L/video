package com.asimple.service;

import com.asimple.entity.*;
import com.asimple.mapper.FilmMapper;
import com.asimple.mapper.RatyMapper;
import com.asimple.mapper.ResMapper;
import com.asimple.mapper.TypeMapper;
import com.asimple.util.DateUtil;
import com.asimple.util.LogUtil;
import com.asimple.util.PageBean;
import com.asimple.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private CataLogService cataLogService;
    @Resource
    private RatyService ratyService;

    /**
     * 查询所有影片信息
     * @return 影片列表
     */
    public List<Film> findAll() {
        return filmMapper.getAll();
    }

    /**
     * 通过类型查找电影
     * @param typeId 类型id
     * @return 影片列表
     */
    public List<Film> listByTypeId(String typeId) {
        return filmMapper.listByTypeId(typeId);
    }

    /**
     * 通过类型查找电影TOP榜
     * @param typeId 类型id
     * @param top 前top
     * @return 影片列表
     */
    public List<Film> listByTypeId(String typeId, int top) {
        return filmMapper.listByTypeId(typeId, top);
    }

    /**
     * 通过分类查找电影列表
     * @param id 分类id
     * @return 影片列表
     */
    public List<Film> listByCataLogId(String id) {
        return filmMapper.listByCataLogId(id);
    }

    /**
     * 通过分类查找前top的电影
     * @param id 分类id
     * @param top 查看前多少
     * @return 影片列表
     */
    @Cacheable(value = "index_filmTuijian")
    public List<Film> listByCataLogId(String id, int top) {
        return filmMapper.listByCataLogId(id, top);
    }

    /**
     * 查找评分排行电影
     * @param id 影片id
     * @return 影片列表
     */
    public List<Film> listByEvaluation(String id) {
        return filmMapper.listByEvaluation(id);
    }

    /**
     * 查找评分排行前top的电影
     * @param id 分类id
     * @param top 前top
     * @return 影片列表
     */
    @Cacheable( value = "index_filmPaiHang")
    public List<Film> listByEvaluation(String id, int top) {
        return filmMapper.listByEvaluation(id, top);
    }

    /**
     * 分页查询电影
     * @param ob 影片信息
     * @param pc 开始条数
     * @param ps 结束条数
     * @return 影片分页信息
     */
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
     * 更新Film信息
     * @param film 影片实体
     * @return 更新成功返回true
     */
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
     * 删除电影
     * @param filmId 电影id
     * @return 删除成功返回true
     */
    public boolean deleteById(String filmId) {
        LogUtil.info(FilmService.class, "待删除的影片：filmId = " + filmId);
        // 1、删除评分信息
        int sum = ratyMapper.deleteByFilmId(filmId);
        // 2、删除资源信息
        sum += resMapper.deleteByFilmId(filmId);
        // 3、删除电影本身
        sum += filmMapper.deleteById(filmId);
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
     * 保存弹幕
     * @param bullet 弹幕对象
     * @return 保存成功返回true
     */
    public boolean saveBullet(Bullet bullet) {
        bullet.setId(Tools.UUID());
        return filmMapper.saveBullet(bullet)!=0;
    }

    /**
     * 查找用户上传的视频
     * @param params 参数列表 必须要有uid
     * @return 用户上传的视频
     */
    public List<Film> listByUser(Map<String, Object> params){
        String uid = (String) params.get("uid");
        String page = (String) params.get("page");
        String pageSize = (String) params.get("pageSize");
        if(StringUtils.isEmpty(page) ) {
            page = "1";
        }
        if( StringUtils.isEmpty(pageSize) ) {
            pageSize = "5";
        }
        int pc = Integer.valueOf(page);
        int ps = Integer.valueOf(pageSize);
        int start = (pc-1)*ps;
        return filmMapper.listByUser(uid, start, ps);
    }

    /**
     * 统计用户上传的视频
     * @param uid 用户id
     * @return 用户上传的视频数
     */
    public int countListByUser(String uid) {
        return filmMapper.countListByUser(uid);
    }

    /**
     * 添加用户浏览记录
     * @param filmId 影片id
     * @param uid 用户id
     */
    public void addViewHistory(String filmId, String uid){
        Map<String, Object> map = new HashMap<>(4);
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
     * 获取用户浏览记录
     * @param params 参数
     * @return 用户浏览历史列表
     */
    public List<Map> getViewHistory(Map<String, Object> params){
        String uid = (String) params.get("uid");
        String page = (String) params.get("page");
        String pageSize = (String) params.get("pageSize");
        if(StringUtils.isEmpty(page) ) {
            page = "1";
        }
        if( StringUtils.isEmpty(pageSize) ) {
            pageSize = "5";
        }
        int pc = Integer.valueOf(page);
        int ps = Integer.valueOf(pageSize);
        List<Map> temp = filmMapper.getViewHistory(uid, (pc-1)*ps, ps);
        List<Map> res = new ArrayList<>();
        for (Map item : temp) {
            Film film = load((String) item.get("film_id"));
            item.put("film", film);
            res.add(item);
        }
        return res;
    }

    /**
     * 统计用户浏览历史数目
     * @param uid 用户id
     * @return 用户浏览数
     */
    public int countViewHistory(String uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        return filmMapper.countViewHistory(map);
    }

    /**
     * TODO SpringBoot Solr整合有问题，还在解决中
     * 报错信息：org.apache.solr.client.solrj.impl.HttpSolrClient$RemoteSolrException: Error from server at http://127.0.0.1:8090/solr/video: Expected mime type application/octet-stream but got text/html.
     * 未改成SpringBoot的时候，是正常运行的。
     */
    public Map<String, Object> getFilmOfSolr(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>(8);
        PageBean<Film> pageBean = new PageBean<>();
        String name = (String) params.get("name");
        if( !Tools.isEmpty(name) ) {
            result.put("name", name);
        }
        // 待改进
        String url = (String) params.get("url");
        if( url != null ) {
            int index = url.indexOf("&pc=");
            if( index != -1 ) {
                url = url.substring(0, index);
            }
        }
        // 当前页面数
        String value = (String) params.get("pc");
        int pc = 1;
        if( Tools.notEmpty(value) ) {
            pc = Integer.parseInt(value);
        }
        pageBean.setPc(pc);
        // 每页显示数目
        int ps = 27;
        pageBean.setPs(ps);
        // 获取页面传递的查询条件
        Film ob = (Film) params.get("film");
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

    /**
     * 获取影片信息
     * @param params 参数列表
     * @return 影片列表信息
     */
    public Map<String, Object> getFilmList(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>(16);
        String name = (String) params.get("name");
        if (Tools.notEmpty(name)) {
            map.put("name", name );
        }

        // 获取当前页数，默认为1
        String value = (String) params.get("pc");
        int pc = 1;
        if (!Tools.isEmpty(value)) {
            pc = Integer.parseInt(value);
        }
        // 设置每页显示的个数
        value = (String) params.get("ps");
        int ps = 15;
        if (!Tools.isEmpty(value)) {
            ps = Integer.parseInt(value);
        }

        // 转化成Film对象
        Film ob = (Film) params.get("film");
        ob.setIsUse(1);
        PageBean<Film> pageBean = getPage(ob, pc, ps);
        map.put("pageBean", pageBean);
        return map;
    }

    /**
     * 获取影片详细信息
     * @param params 参数列表
     * @return 影片详情信息
     */
    public Map<String, Object> getFilmDetailInfo(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>(16);
        Film film = (Film) params.get("film");
        String filmId = film.getId();
        result.put("film", film);
        // 获取影评总人数
        result.put("totalRatys", ratyService.getCountByFilmId(filmId));

        // 获取相同类型的影片
        List<Film> films = listByTypeId(film.getType_id(), 8);
        result.put("films", films);

        // 下载链接
        List<Res> resListEd2k = new ArrayList<>();
        List<Res> resListThunder = new ArrayList<>();
        List<Res> resListHttp = new ArrayList<>();
        List<Res> resListDupan = new ArrayList<>();
        List<Res> resListFlh = new ArrayList<>();
        List<Res> resListOther = new ArrayList<>();

        for (Res res : film.getResList()) {
            if ("ed2k".equals(res.getLinkType()) && res.getIsUse() == 1) {
                resListEd2k.add(res);
            } else if ("thunder".equals(res.getLinkType()) && res.getIsUse() == 1) {
                resListThunder.add(res);
            } else if ("http".equals(res.getLinkType()) && res.getIsUse() == 1) {
                resListHttp.add(res);
            } else if ("dupan".equals(res.getLinkType()) && res.getIsUse() == 1) {
                resListDupan.add(res);
            } else if ("Flh".equals(res.getLinkType()) && res.getIsUse() == 1) {
                resListFlh.add(res);
            } else if (res.getIsUse() == 1) {
                resListOther.add(res);
            }
        }
        resListEd2k.sort(Comparator.comparingInt(Res::getEpisodes));
        resListThunder.sort(Comparator.comparingInt(Res::getEpisodes));
        resListHttp.sort(Comparator.comparingInt(Res::getEpisodes));
        resListDupan.sort(Comparator.comparingInt(Res::getEpisodes));
        resListFlh.sort(Comparator.comparingInt(Res::getEpisodes));
        resListOther.sort(Comparator.comparingInt(Res::getEpisodes));
        result.put("resListEd2k", resListEd2k);
        result.put("resListThunder", resListThunder);
        result.put("resListHttp", resListHttp);
        result.put("resListDupan", resListDupan);
        result.put("resListFlh", resListFlh);
        result.put("resListOther", resListOther);

        return result;
    }

    /**
     * 首页信息获取
     * @return 首页显示信息
     */
    public Map<String, Object> getIndexInfo() {
        Map<String, Object> result = new HashMap<>(4);
        // 查询用户菜单列表
        List<CataLog> logList =  cataLogService.listIsUse();
        result.put("cataLogList", logList);

        // 查询推荐电影
        List<Object> list = new ArrayList<>();
        for (CataLog aLogList : logList) {
            List<Film> films = listByCataLogId(aLogList.getId(), 12);
            if (films.size() != 0) {
                list.add(films);
            }
        }
        result.put("filmTuijian", list);

        // 电影排行榜
        List<Object> list1 = new ArrayList<>();
        for (CataLog aLogList : logList) {
            List<Film> films = listByEvaluation(aLogList.getId(), 12);
            if (films.size() != 0) {
                list1.add(films);
            }
        }
        result.put("filmPaiHang", list1);
        return result;
    }

}
