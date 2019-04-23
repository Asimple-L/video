package com.asimple.controller;

import com.asimple.entity.*;
import com.asimple.service.*;
import com.asimple.task.RankTask;
import com.asimple.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName video
 * @Description: 个人中心
 * @author: Asimple
 */
@Controller
@RequestMapping("/profile")
public class Profile {
    private final static String USER_KEY = "u_skl";
    @Resource
    private IResService resService;
    @Resource
    private IFilmService filmService;
    @Resource
    private ISubClassService subClassService;
    @Resource
    private ITypeService typeService;
    @Resource
    private RankTask rankTask;
    @Resource
    private ICommonService commonService;

    @RequestMapping(value = "/profilePage.html")
    public String profile(ModelMap map, HttpSession session) {
        map = commonService.getCatalog(map);
        User user = (User) session.getAttribute(USER_KEY);
        String uid = user.getId();
        // 我的视频
        List<Film> list = filmService.listByUser(uid, 1, 5);
        int total = filmService.countListByUser(uid);
        map.put("totalPage", total/5+((total%5)>0?1:0));
        map.put("pageNo", 1);
        map.put("films", list);
        // 浏览记录
        List<Map> viewHistoryMap = filmService.getViewHistory(uid, 1, 5);
        int viewHistoryNumber = filmService.countViewHistory(uid);
        map.put("viewHistoryList", viewHistoryMap);
        map.put("viewHistoryAllPage", viewHistoryNumber/5+((viewHistoryNumber%5)>0?1:0));
        map.put("viewHistoryPage", 1);
        // 左边栏目信息
        map.put("viewCount", viewHistoryNumber);
        map.put("myFilmsCount", total);
        return "index/profile";
    }

    @RequestMapping(value = "/share.html")
    public String shareVideo(HttpServletRequest request, ModelMap map) {
        String film_id = request.getParameter("film_id");
        Film film = filmService.load(film_id);
        if( film!=null ) {
            map.put("film", film);
            List<Res> list = resService.getListByFilmId(film_id);
            if( list.size()== 0 )  map.addAttribute("res", null);
            else map.addAttribute("res", list);
        }
        map = commonService.getCatalog(map);
        return "index/addFilm";
    }

    /**
     * @Author Asimple
     * @Description 添加影片
     **/
    @RequestMapping( value = "/addFilm.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addFilm(Film film, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) session.getAttribute(USER_KEY);
        film.setUser(user);
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
            String name = resName[0];
            int begin = Integer.parseInt(resName[1]);
            int end = Integer.parseInt(resName[2]);
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
        } else {
            id = resService.add(res);
        }
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
     * @Description 返回我的视频
     **/
    @RequestMapping(value = "/getFilmAjax.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getFilm(HttpSession session, int pc, String type) {
        User user = (User) session.getAttribute(USER_KEY);
        JSONObject jsonObject = new JSONObject();
        if( "my-films".equalsIgnoreCase(type) ) {
            int total = filmService.countListByUser(user.getId());
            jsonObject.put("totalPage", total/5+((total%5)>0?1:0));
            List<Film> list = filmService.listByUser(user.getId(), pc, 5);
            jsonObject.put("films", list);
        } else {
            int viewHistoryNumber = filmService.countViewHistory(user.getId());
            jsonObject.put("totalPage", viewHistoryNumber/5+((viewHistoryNumber%5)>0?1:0));
            List<Map> viewHistoryMap = filmService.getViewHistory(user.getId(), pc, 5);
            jsonObject.put("viewHistoryList", viewHistoryMap);
        }
        jsonObject.put("pageNo", pc);
        return  jsonObject.toString();
    }

    private void updateRedis(String id) {
        if( !"0".equals(id) ) {
            rankTask.commendRank();
        }
    }

}
