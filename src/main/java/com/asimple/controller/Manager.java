package com.asimple.controller;

import com.asimple.entity.*;
import com.asimple.service.*;
import com.asimple.task.SolrTask;
import com.asimple.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ProjectName video
 * @description 后台管理
 * @author Asimple
 */
@RestController
@RequestMapping("/admin")
public class Manager {
    @Resource
    private UserService userService;
    @Resource
    private LocService locService;
    @Resource
    private LevelService levelService;
    @Resource
    private DecadeService decadeService;
    @Resource
    private CataLogService cataLogService;
    @Resource
    private ResService resService;
    @Resource
    private FilmService filmService;
    @Resource
    private SubClassService subClassService;
    @Resource
    private TypeService typeService;
    @Resource
    private VipCodeService vipCodeService;
    @Resource
    private CommonService commonService;
    @Resource
    private SolrTask solrTask;

    /**
     * @author Asimple
     * @description 管理员登录
     **/
    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    public Object adminLogin(String username, String password, HttpSession session) {
        // 用户名或者邮箱登录
        User user = commonService.checkUser(username, password);
        // 登录成功重定向到后台首页
        if ( null != user ) {
            session.setAttribute(VideoKeyNameUtil.ADMIN_USER_KEY, user);
            session.setAttribute(VideoKeyNameUtil.USER_KEY, user);
            return ResponseReturnUtil.returnSuccessWithMsg("登录成功!");
        }
        return ResponseReturnUtil.returnErrorWithMsg("请登录正确的管理员账号或密码!");
    }

    /**
     * @author Asimple
     * @description 影片资源管理
     **/
    @RequestMapping(value = "/film")
    public Object film(String filmId) {
        // 如果有id，则是编辑
        Map<String, Object> result = new HashMap<>(4);
        if ( StringUtils.isNotEmpty(filmId) ) {
            // 获取电影信息
            result.put("film", filmService.load(filmId));
            // 获取资源信息
            List<Res> list = resService.getListByFilmId(filmId);
            if( list.size()== 0 ) {
                result.put("res", null);
            } else {
                result.put("res", list);
            }
        }
        result.putAll(commonService.getCatalog());
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 查看所有影视信息
     **/
    @RequestMapping(value = "/list")
    public Object filmList(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(8);
        result.putAll(filmService.getFilmList(request));
//        result.putAll(filmService.getFilmOfSolr(request));
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 用户管理
     **/
    @RequestMapping( value = "/userList")
    public Object userList(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(2);
        Map<String, Object> param = new HashMap<>(2);
        param.put("page", request.getParameter("page"));
        param.put("pageSize", request.getParameter("pageSize"));
        PageBean<User> pageBean = userService.getPage(param);
        result.put("pb", pageBean);
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 更新用户信息
     **/
    @RequestMapping( value = "/updateUser", method = RequestMethod.POST)
    public Object updateUser(String uid, String key) {
        Map<String, Object> param = new HashMap<>(4);
        User user = userService.load(uid);
        param.put("user", user);
        param.put("key", key);
        if( userService.update(param) ) {
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        } else {
            return ResponseReturnUtil.returnErrorWithMsg("更新失败!");
        }
    }

    /**
     * @author Asimple
     * @description 添加影片
     **/
    @RequestMapping( value = "/addFilm", produces = "text/html;charset=UTF-8")
    public Object addFilm(Film film, HttpSession session) {
        Map<String, Object> map = new HashMap<>(1);
        User user = (User) session.getAttribute(VideoKeyNameUtil.ADMIN_USER_KEY);
        film.setUser(user);
        String id = filmService.save(film);
        commonService.cleanRedisCache();
        map.put("id", id);
        return ResponseReturnUtil.returnSuccessWithData(map);
    }

    /**
     * @author Asimple
     * @description 删除影片
     **/
    @RequestMapping( value = "/delFilm")
    public Object delFilm(HttpServletRequest request) {
        String filmId = request.getParameter("filmId");
        LogUtil.info(Manager.class, "film_id = " + filmId);
        if ( filmService.deleteById(filmId) ) {
            commonService.cleanRedisCache();
            return ResponseReturnUtil.returnSuccessWithMsg("更新成功!");
        } else {
            return ResponseReturnUtil.returnErrorWithMsg("更新失败,请稍后重试!");
        }
    }


    /**
     * @author Asimple
     * @description 添加资源
     **/
    @RequestMapping(value = "/addRes")
    public Object addRes(Res res, String filmId) {
        Map<String, Object> result = new HashMap<>(1);
        String id = resService.addRes(res, filmId);
        result.put("id", id);
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 删除资源
     **/
    @RequestMapping( value = "/delRes")
    public Object delRes(String resId) {
        if( resService.delete(resId) ) {
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        } else {
            return ResponseReturnUtil.returnErrorWithMsg("删除失败!请稍后重试!");
        }
    }

    /**
     * @author Asimple
     * @description 更改在离线状态
     **/
    @RequestMapping( value = "/updateIsUse")
    public Object updateIsUse(String resId) {
        LogUtil.info(Manager.class, "res_id = " + resId);
        if( resService.updateIsUse(resId) ) {
            commonService.cleanRedisCache();
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        } else {
            return ResponseReturnUtil.returnErrorWithMsg("更新失败,请稍后重试!");
        }
    }

    /**
     * @author Asimple
     * @description 更新影片信息
     **/
    @RequestMapping( value = "/updateFilmInfo")
    public Object updateFilmInfo(String filmId, String val, String key, HttpSession session) {
        Map<String, Object> param = new HashMap<>(16);
        Film film = filmService.load(filmId);
        param.put("key", key);
        param.put("val", val);
        param.put("film", film);
        param.put("filePath", session.getServletContext().getRealPath(film.getImage()));
        if( commonService.updateFilmInfo(param) ) {
            commonService.cleanRedisCache();
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        } else {
            return ResponseReturnUtil.returnErrorWithMsg("更新失败,请稍后重试!");
        }
    }

    /**
     * @author Asimple
     * @description 目录管理 目录查看与修改
     **/
    @RequestMapping( value = {"/catalog", "/editCatalog"})
    public Object catalog() {
        Map<String, Object> result = new HashMap<>(8);
        result.putAll(commonService.getCatalog());
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 添加年列表
     **/
    @RequestMapping( value = "addDecade")
    public Object addDecade(Decade decade) {
        Map<String, Object> result = new HashMap<>(1);
        result.put("id", decadeService.add(decade));
        commonService.cleanRedisCache();
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 添加级别
     **/
    @RequestMapping( value = "/addLevel")
    public Object addLevel(Level level) {
        Map<String, Object> result = new HashMap<>(1);
        result.put("id", levelService.add(level));
        commonService.cleanRedisCache();
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 添加地区
     **/
    @RequestMapping( value = "/addLoc")
    public Object addLoc(Loc loc) {
        Map<String, Object> result = new HashMap<>(1);
        result.put("id", locService.add(loc));
        commonService.cleanRedisCache();
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 添加一级分类
     **/
    @RequestMapping(value = "/addCataLog")
    public Object addCataLog(CataLog cataLog) {
        Map<String, Object> result = new HashMap<>(1);
        result.put("id", cataLogService.add(cataLog));
        commonService.cleanRedisCache();
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 添加二级分类
     **/
    @RequestMapping(value = "/addSubClass")
    public Object addSubClass(SubClass subClass, String cataLogId) {
        Map<String, Object> result = new HashMap<>(1);
        result.put("id", subClassService.add(subClass, cataLogId));
        commonService.cleanRedisCache();
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 添加类型
     **/
    @RequestMapping(value = "/addType")
    public Object addType(Type type,String subClassId) {
        Map<String, Object> result = new HashMap<>(1);
        result.put("id", typeService.add(type, subClassId));
        commonService.cleanRedisCache();
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description VIP管理
     **/
    @RequestMapping(value = "/vipCode")
    public Object vipCode() {
        Map<String, Object> map = new HashMap<>(2);
        List<VipCode> list = vipCodeService.listIsUse();
        map.put("vip_codes",list);
        return ResponseReturnUtil.returnSuccessWithData(map);
    }

    /**
     * @author Asimple
     * @description 创建VIP卡号
     **/
    @RequestMapping(value = "/createVipCode", method = RequestMethod.POST)
    public Object createVipCode(String num) {
        Map<String, Object> result = new HashMap<>(1);
        if ( StringUtils.isEmpty(num) ) {
            num = "5";
        }
        int n = Integer.parseInt(num);
        List<VipCode> vipCodes = vipCodeService.addVipCodes(n);
        if( vipCodes.size() == n ) {
            result.put("data", vipCodes);
            return ResponseReturnUtil.returnSuccessWithData(result);
        }
        return ResponseReturnUtil.returnErrorWithMsg("添加失败!请稍后重试!");
    }

    /**
     * @author Asimple
     * @description 导入Solr库
     **/
    @RequestMapping(value = "/loadIn", produces = "text/html;charset=UTF-8")
    public Object loadInSolr() {
        solrTask.pushToSolr();
        return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
    }

}
