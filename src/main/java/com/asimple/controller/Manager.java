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
     * @deprecated
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
    @RequestMapping(value = "/film", produces = "application/json;charset=UTF-8")
    public Object film(HttpServletRequest request) {
        if( !RequestUtil.isAdmin(request) ) {
            return ResponseReturnUtil.returnErrorWithMsg("请重新登录后重试!");
        }
        Map<String, Object> result = new HashMap<>(4);
        String filmId = request.getParameter("filmId");
        // 如果有id，则是编辑
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
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 查看所有影视信息
     **/
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public Object filmList(HttpServletRequest request) {
        if( !RequestUtil.isAdmin(request) ) {
            return ResponseReturnUtil.returnErrorWithMsg("请重新登录后重试!");
        }
        Map<String, Object> result = new HashMap<>(8);
        Map<String, Object> param = new HashMap<>(4);
        param.put("name", request.getParameter("name"));
        param.put("pc", request.getParameter("pc"));
        param.put("film", Tools.toBean(request.getParameterMap(), Film.class));
        result.putAll(filmService.getFilmList(param));
//     TODO   result.putAll(filmService.getFilmOfSolr(request));
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 用户管理
     **/
    @RequestMapping( value = "/userList", produces = "application/json;charset=UTF-8")
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
    @RequestMapping( value = "/addFilm", produces = "application/json;charset=UTF-8")
    public Object addFilm(Film film, HttpSession session) {
        Map<String, Object> map = new HashMap<>(1);
        User user = (User) session.getAttribute(VideoKeyNameUtil.ADMIN_USER_KEY);
        film.setUser(user);
        String id = filmService.save(film);
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
            return ResponseReturnUtil.returnSuccessWithMsg("更新成功!");
        }
        return ResponseReturnUtil.returnErrorWithMsg("更新失败,请稍后重试!");
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
        }
        return ResponseReturnUtil.returnErrorWithMsg("删除失败!请稍后重试!");
    }

    /**
     * @author Asimple
     * @description 更改在离线状态
     **/
    @RequestMapping( value = "/updateIsUse")
    public Object updateIsUse(String resId) {
        LogUtil.info(Manager.class, "res_id = " + resId);
        if( resService.updateIsUse(resId) ) {
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
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        }
        return ResponseReturnUtil.returnErrorWithMsg("更新失败,请稍后重试!");
    }

    /**
     * @author Asimple
     * @description 目录管理 目录查看与修改
     **/
    @RequestMapping( value = {"/catalog", "/editCatalog"})
    public Object catalog(HttpServletRequest request) {
        if( !RequestUtil.isAdmin(request) ) {
            return ResponseReturnUtil.returnErrorWithMsg("登录超时,请重新登录!");
        }
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
        if( decadeService.add(decade) ) {
            return ResponseReturnUtil.returnSuccessWithMsg("操作成功!");
        }
        return ResponseReturnUtil.returnErrorWithMsg("参数错误，请重试!");
    }

    /**
     * @author Asimple
     * @description 添加级别
     **/
    @RequestMapping( value = "/addLevel")
    public Object addLevel(Level level) {
        if( levelService.add(level) ) {
            return ResponseReturnUtil.returnSuccessWithMsg("操作成功!");
        }
        return ResponseReturnUtil.returnErrorWithMsg("参数错误，请重试!");
    }

    /**
     * @author Asimple
     * @description 添加地区
     **/
    @RequestMapping( value = "/addLoc")
    public Object addLoc(Loc loc) {
        if( locService.add(loc) ) {
            return ResponseReturnUtil.returnSuccessWithMsg("操作成功!");
        }
        return ResponseReturnUtil.returnErrorWithMsg("参数错误，请重试!");
    }

    /**
     * @author Asimple
     * @description 添加/修改一级分类
     **/
    @RequestMapping(value = "/addCataLog")
    public Object addCataLog(CataLog cataLog) {
        if ( cataLogService.add(cataLog) ) {
            return ResponseReturnUtil.returnSuccessWithMsg("操作成功!");
        }
        return ResponseReturnUtil.returnErrorWithMsg("添加失败，请稍后重试!");
    }

    /**
     * @author Asimple
     * @description 添加二级分类
     **/
    @RequestMapping(value = "/addSubClass")
    public Object addSubClass(SubClass subClass, String cataLogId) {
        if( subClassService.add(subClass, cataLogId) ) {
            return ResponseReturnUtil.returnSuccessWithMsg("操作成功!");
        }
        return ResponseReturnUtil.returnErrorWithMsg("参数错误，请重试!");
    }

    /**
     * @author Asimple
     * @description 添加类型
     **/
    @RequestMapping(value = "/addType")
    public Object addType(Type type,String subClassId) {
        Map<String, Object> result = new HashMap<>(1);
        if( typeService.add(type, subClassId) ) {
            return ResponseReturnUtil.returnSuccessWithMsg("操作成功!");
        }
        return ResponseReturnUtil.returnErrorWithMsg("参数错误，请重试!");
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
    @RequestMapping(value = "/loadIn", produces = "application/json;charset=UTF-8")
    public Object loadInSolr() {
        solrTask.pushToSolr();
        return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
    }

    /**
     * @author Asimple
     * @description 删除分类
     **/
    @RequestMapping(value = "/deleteCatalogById", produces = "application/json;charset=UTF-8")
    public Object deleteCatalog(HttpServletRequest request) {
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        if( StringUtils.isEmpty(id) || StringUtils.isEmpty(type) ) {
            return ResponseReturnUtil.returnErrorWithMsg("参数错误，请重试!");
        }
        boolean flag = false;
        if ( "catalog".equals(type) ) {
            flag = cataLogService.deleteById(id);
        }
        if ( !flag ) {
            return ResponseReturnUtil.returnErrorWithMsg("删除失败!");
        }
        return ResponseReturnUtil.returnSuccessWithMsg("操作成功!");
    }

}
