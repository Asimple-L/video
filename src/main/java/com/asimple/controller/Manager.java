package com.asimple.controller;

import com.asimple.entity.*;
import com.asimple.service.*;
import com.asimple.task.RankTask;
import com.asimple.task.SolrTask;
import com.asimple.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 后台管理
 * @author: Asimple
 */
@Controller
@RequestMapping("/admin")
public class Manager {
    private final static String key = "a1s2i3m4p5l6e7";
    @Resource
    private IUserService userService;
    @Resource
    private ILocService locService;
    @Resource
    private ILevelService levelService;
    @Resource
    private IDecadeService decadeService;
    @Resource
    private ICataLogService cataLogService;
    @Resource
    private IResService resService;
    @Resource
    private IFilmService filmService;
    @Resource
    private ISubClassService subClassService;
    @Resource
    private ITypeService typeService;
    @Resource
    private IVipCodeService vipCodeService;
    @Resource
    private RankTask rankTask;
    @Resource
    private SolrTask solrTask;
    @Resource
    private SolrTemplate solrTemplate;

    /**
     * @Author Asimple
     * @Description 跳转到后台登录页面
     **/
    @RequestMapping(value = "/login.html", method = { RequestMethod.GET })
    public String adminLoginPage() {
        return "manager/login";
    }

    /**
     * @Author Asimple
     * @Description 管理员登录
     **/
    @RequestMapping(value = "/login.html", method = { RequestMethod.POST })
    public String adminLogin(String username, String password, ModelMap map, HttpSession session) {
        // 用户名或者邮箱登录
        boolean flag = false;// 是否登录成功
        User user = new User();
        List<User> users = null;
        user.setUserName(username);
        users = userService.findByCondition(user);
        // 用户名登录
        if( users != null && users.size()>0 ) {
            flag = checkAccount(password, users, session, map);
        } else { // 邮箱登录
            user.setUserName(null);
            user.setUserEmail(username);
            users = userService.findByCondition(user);
            if( users!=null && users.size()>0 ) {
                flag = checkAccount(password, users, session, map);
            } else {
                map.addAttribute("msg", "请登录正确的管理员账号！");
            }
        }
        // 登录成功重定向到后台首页
        if ( flag ) return "redirect:/admin/index.html";
        else return "manager/login";
    }

    /**
     * @Author Asimple
     * @Description 后台首页
     **/
    @RequestMapping(value = {"/", "/index.html"})
    public String backIndex(ModelMap map, HttpSession session) {
        return "manager/index";
    }

    /**
     * @Author Asimple
     * @Description 影片资源管理
     **/
    @RequestMapping(value = "/film.html")
    public String film(ModelMap map, String film_id) {
        if ( film_id != null && !"".equals(film_id.trim()) ) {// 如果有id，则是编辑
            // 获取电影信息
            map.addAttribute("film", filmService.load(film_id));
            // 获取资源信息
            List<Res> list = resService.getListByFilmId(film_id);
            if( list.size()== 0 )  map.addAttribute("res", null);
            else map.addAttribute("res", list);
        }
        getCatalog(map);
        return "manager/addFilm";
    }

    /**
     * @Author Asimple
     * @Description 查看所有影视信息
     **/
    @RequestMapping(value = "/list.html")
    public String filmList(ModelMap map, HttpServletRequest request) {
//        getFilmList(map, request, 0);
        getFilmOfSolr(map, request);
        return "manager/allFilm";
    }

    /**
     * @Author Asimple
     * @Description 用户管理
     **/
    @RequestMapping( value = "/userList.html")
    public String userList(String page, ModelMap map) {
        if( Tools.isEmpty(page) ) page = "1";
        int pc = Integer.parseInt(page);
        // 设置每页的条数
        int pageSize = 10;
        PageBean<User> pageBean = userService.getPage(null, pc, pageSize);
        map.addAttribute("pb", pageBean);
        return "manager/userList";
    }

    /**
     * @Author Asimple
     * @Description 更新用户信息
     **/
    @RequestMapping( value = "/updateUser.html", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(String uid, String key) {
        JSONObject jsonObject = new JSONObject();
        User user = userService.load(uid);
        if( "manager".equals(key) ) {
            int isManager = user.getIsManager();
            user.setIsManager(1-isManager);
        } else {
            long isVip = user.getIsVip();
            user.setIsVip(1L-isVip);
        }
        if( userService.update(user) ) jsonObject.put("code", "1");
        else jsonObject.put("code", "0");
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 添加影片
     **/
    @RequestMapping( value = "/addFilm.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addFilm(Film film) {
        JSONObject jsonObject = new JSONObject();
        String id = filmService.save(film);
        this.updateRedis(id);
        jsonObject.put("id", id);
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 删除影片
     **/
    @RequestMapping( value = "/delFilm.html")
    @ResponseBody
    public String delFilm(String film_id) {
        LogUtil.info(Manager.class, "film_id = " + film_id);
        JSONObject jsonObject = new JSONObject();
        if ( filmService.deleteById(film_id) ) {
            jsonObject.put("code", "1");
            this.updateRedis("1");
        }
        else jsonObject.put("code", "0");
        return jsonObject.toString();
    }


    /**
     * @Author Asimple
     * @Description 添加资源
     **/
    @RequestMapping(value = "/addRes.html")
    @ResponseBody
    public String addRes(Res res, String film_id) {
        JSONObject jsonObject = new JSONObject();
        // 初始化
        res.setIsUse(1);
        Film film = filmService.load(film_id);
        res.setFilm(film);
        res.setUpdateTime(DateUtil.getTime());

        // 多资源上传
        String id = "";
        if ( res.getName().contains("@@") ) {
            //  xxxx@@集##集数开始##集数结束##分割符号
           String resName[] = res.getName().trim().split("##");
           // 视频名称
           String name = resName[0];
           // 开始集数与结束集数
           int begin = Integer.parseInt(resName[1]);
           int end = Integer.parseInt(resName[2]);
           // 链接分割标志
            String flag = "";
           if( resName.length > 3 ) {
               flag = resName[3];
               String res_links[] = res.getLink().replaceAll("\\n","").split(flag);
               int cz = begin - 1;
               for(int i=begin; i<=end; i++) {
                   res.setName(name.replace("@@", ""));
                   res.setEpisodes(i);
                   if( "Flh".equals(res.getLinkType()) ) flag = "";
                   res.setLink(flag+res_links[i-cz]);
                   id = resService.add(res);
               }
           }
        } else id = resService.add(res);
        film.setUpdateTime(DateUtil.getTime());
        filmService.update(film);
        jsonObject.put("id", id);
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 删除资源
     **/
    @RequestMapping( value = "/delRes.html")
    @ResponseBody
    public String delRes(String res_id) {
        JSONObject jsonObject = new JSONObject();
        if( resService.delete(res_id) ) jsonObject.put("code", "1");
        else jsonObject.put("code", "0");
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 更改在离线状态
     **/
    @RequestMapping( value = "/updateIsUse.html")
    @ResponseBody
    public String updateIsUse(String res_id) {
        JSONObject jsonObject = new JSONObject();
        LogUtil.info(Manager.class, "res_id = " + res_id);
        if( resService.updateIsUse(res_id) ) {
            jsonObject.put("code", "1");
            this.updateRedis("1");;
        }
        else jsonObject.put("code", "0");
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 更新影片信息
     **/
    @RequestMapping( value = "/updateFilmInfo.html")
    @ResponseBody
    public String updateFilmInfo(String film_id, String val, String key, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        Film film = filmService.load(film_id);
        LogUtil.info(Manager.class, "key = " + key + "   val = " + val);
        switch ( key ) {
            case "name":
                film.setName(val);
                break;
            case "image":
                FileOperate.delFile(session.getServletContext().getRealPath(film.getImage()));
                film.setImage(val);
                break;
            case "onDecade":
                film.setOnDecade(val);
                break;
            case "status":
                film.setStatus(val);
                break;
            case "resolution":
                film.setResolution(val);
                break;
            case "typeName":
                film.setTypeName(val);
                break;
            case "type_id":
                film.setType_id(val);
                Type type = typeService.load(val);
                LogUtil.info(Manager.class, "type = " + type);
                film.setSubClass_id(type.getSubClass().getId());
                film.setSubClassName(type.getSubClass().getName());
                film.setCataLog_id(type.getSubClass().getCataLog().getId());
                film.setCataLogName(type.getSubClass().getCataLog().getName());
                break;
            case "actor":
                film.setActor(val);
                break;
            case "loc_id":
                film.setLoc_id(val);
                break;
            case "plot":
                film.setPlot(val);
                break;
            case "isVip":
                film.setIsVip(Integer.valueOf(val));
                break;
        }
        LogUtil.info(Manager.class, "film = " + film);
        if( filmService.update(film) ) {
            jsonObject.put("code", "1");
            this.updateRedis("1");
        } else {
            jsonObject.put("code", "0");
        }
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 目录管理
     **/
    @RequestMapping( value = "/catalog.html")
    public String catalog(ModelMap map) {
        getCatalog(map);
        return "manager/catalog";
    }

    /**
     * @Author Asimple
     * @Description 目录查看与修改
     **/
    @RequestMapping( value = "/editCatalog.html")
    public String editCatalog(ModelMap map) {
        getCatalog(map);
        return "manager/editCatalog";
    }

    /**
     * @Author Asimple
     * @Description 添加年列表
     **/
    @RequestMapping( value = "addDecade.html")
    @ResponseBody
    public String addDecade(Decade decade) {
        decade.setIsUse(1);
        String id = decadeService.add(decade);
        this.updateRedisList(id);
        return addReturned(id);
    }

    /*
     * @Author Asimple
     * @Description 添加级别
     **/
    @RequestMapping( value = "/addLevel.html")
    @ResponseBody
    public String addLevel(Level level) {
        level.setIsUse(1);
        String id = levelService.add(level);
        this.updateRedisList(id);
        return addReturned(id);
    }

    /*
     * @Author Asimple
     * @Description 添加地区
     **/
    @RequestMapping( value = "/addLoc.html")
    @ResponseBody
    public String addLoc(Loc loc) {
        loc.setIsUse(1);
        String id = locService.add(loc);
        this.updateRedisList(id);
        return addReturned(id);
    }

    /**
     * @Author Asimple
     * @Description 添加一级分类
     **/
    @RequestMapping(value = "/addCataLog.html")
    @ResponseBody
    public String addCataLog(CataLog cataLog) {
        cataLog.setIsUse(1);
        String id = cataLogService.add(cataLog);
        this.updateRedis(id);
        this.updateRedisList(id);
        return addReturned(id);
    }

    /**
     * @Author Asimple
     * @Description 添加二级分类
     **/
    @RequestMapping(value = "/addSubClass.html")
    @ResponseBody
    public String addSubClass(SubClass subClass, String cataLog_id) {
        subClass.setIsUse(1);
        CataLog cataLog = cataLogService.load(cataLog_id);
        subClass.setCataLog(cataLog);
        String id = subClassService.add(subClass);
        return addReturned(id);
    }

    /**
     * @Author Asimple
     * @Description 添加类型
     **/
    @RequestMapping(value = "/addType.html")
    @ResponseBody
    public String addType(Type type,String subClass_id) {
        type.setIsUse(1);
        SubClass subClass = subClassService.load(subClass_id);
        type.setSubClass(subClass);
        String id = typeService.add(type);
        return addReturned(id);
    }

    /**
     * @Author Asimple
     * @Description 获取二级目录信息
     **/
    @RequestMapping(value = "/getSubClass.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getSubClass(String catalog_id) {
        List<SubClass> subClasses = subClassService.listIsUse(catalog_id);
        JsonConfig jsonConfig = new JsonConfig();
        // 过滤列表
        jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
            @Override
            public boolean apply(Object o, String s, Object o1) {
                return !("id".equals(s) || "name".equals(s));
            }
        });
        JSONArray jsonArray = JSONArray.fromObject(subClasses, jsonConfig);
        return jsonArray.toString();
    }

    /**
     * @Author Asimple
     * @Description VIP管理
     **/
    @RequestMapping(value = "/vipCode.html")
    public String vipCode(ModelMap map) {
        getCatalog(map);
        List<VipCode> list = vipCodeService.listIsUse();
        map.addAttribute("vip_codes",list);
        return "manager/vipManager";
    }

    /**
     * @Author Asimple
     * @Description 创建VIP卡号
     **/
    @RequestMapping(value = "/createVipCode.html", method = RequestMethod.POST)
    @ResponseBody
    public String createVipCode(String num) {
        JSONObject jsonObject = new JSONObject();
        if( StringUtils.isNoneBlank(num) ) {
            int n = Integer.parseInt(num);
            VipCode vipCode;
            List<VipCode> vipCodes = new ArrayList<>();
            for(int i=0; i<n; i++) {
                vipCode = new VipCode();
                vipCode.setId(Tools.UUID());
                vipCode.setCreate_time(new Date());
                vipCode.setExpire_time(new Date());
                vipCode.setCode(Tools.UUID());
                vipCode.setIsUse(1);
                vipCodes.add(vipCode);
            }
            int cnt = vipCodeService.saveAll(vipCodes);
            if( cnt == n ) {
                jsonObject.put("code", "1");
                jsonObject.put("data", vipCodes);
            } else jsonObject.put("code", "0");
        } else jsonObject.put("code", "0");
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 获取类型
     **/
    @RequestMapping(value = "/getType.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getType(String subClass_id) {
        List<Type> types = typeService.listIsUseBySubClass_id(subClass_id);
        JsonConfig jsonConfig = new JsonConfig();
        // 过滤列表
        jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
            @Override
            public boolean apply(Object o, String s, Object o1) {
                return !("id".equals(s) || "name".equals(s));
            }
        });
        JSONArray jsonArray = JSONArray.fromObject(types, jsonConfig);
        return jsonArray.toString();
    }

    /**
     * @Author Asimple
     * @Description 数据导入页面
     **/
    @RequestMapping(value = "/loadInSolrPage.html")
    public String loadSolr() {
        return "manager/loadSolr";
    }

    /**
     * @Author Asimple
     * @Description 导入Solr库
     **/
    @RequestMapping(value = "/loadIn.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String loadInSolr() {
        solrTask.pushToSolr();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "1");
        return jsonObject.toString();
    }

    private void getCatalog(ModelMap model) {
        List<Loc> locList = locService.listIsUse();
        model.addAttribute("locList", locList);
        List<Level> levelList = levelService.listIsUse();
        model.addAttribute("levelList", levelList);
        List<Decade> decadeList = decadeService.listIsUse();
        model.addAttribute("decadeList", decadeList);
        List<CataLog> cataLogList = cataLogService.listIsUse();
        model.addAttribute("cataLogList", cataLogList);
    }

    private boolean checkAccount(String password, List<User> users, HttpSession session, ModelMap map) {
        User user = users.get(0);
        if( MD5Auth.validatePassword(user.getUserPasswd(), password+key, "UTF-8") && user.getIsManager() == 1 ) { // 登录成功
            session.setAttribute("adminUser", user);
            return true;
        } else {
            map.addAttribute("msg", "请登录正确的管理员账号！");
            return false;
        }
    }

    private void getFilmList(ModelMap map, HttpServletRequest request, int flag) {
        String name = request.getParameter("name");
        if( !Tools.isEmpty(name) ) map.addAttribute("name", name);
        // 分页查询所有电影列表
            // 获取url条件信息
        String url = request.getQueryString();
        if( url != null ) {
            int index = url.indexOf("&pc=");
            if( index != -1 ) url = url.substring(0, index);
        }
            // 当前页面数
        String value = request.getParameter("pc");
        int pc = 1;
        if( Tools.notEmpty(value) ) pc = Integer.parseInt(value);
            // 每页显示数目
        int ps = 27;
            // 获取页面传递的查询条件
        Film ob = Tools.toBean(request.getParameterMap(), Film.class);
        if( flag != 0 ) ob.setIsUse(1);

        PageBean<Film> pageBean = filmService.getPage(ob, pc, ps);
        pageBean.setUrl(url);
        map.addAttribute("pb", pageBean);
    }

    private void getFilmOfSolr(ModelMap map, HttpServletRequest request) {
        PageBean<Film> pageBean = new PageBean<>();
        String name = request.getParameter("name");
        if( !Tools.isEmpty(name) ) map.addAttribute("name", name);
        String url = request.getQueryString();
        if( url != null ) {
            int index = url.indexOf("&pc=");
            if( index != -1 ) url = url.substring(0, index);
        }
        // 当前页面数
        String value = request.getParameter("pc");
        int pc = 1;
        if( Tools.notEmpty(value) ) pc = Integer.parseInt(value);
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
        map.addAttribute("pb", pageBean);
    }

    private String addReturned(String id){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", id);
        return jsonObject.toString();
    }

    private void updateRedis(String id) {
        if( !"0".equals(id) ) {
            rankTask.commendRank();
        }
    }

    private void updateRedisList(String id) {
        if( !"0".equals(id) ) {
            rankTask.addListInfoToRedis();
        }
    }

}
