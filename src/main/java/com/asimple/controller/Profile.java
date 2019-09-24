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
import java.util.HashMap;
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
    @Resource
    private ResService resService;
    @Resource
    private FilmService filmService;
    @Resource
    private SubClassService subClassService;
    @Resource
    private TypeService typeService;
    @Resource
    private RankTask rankTask;
    @Resource
    private CommonService commonService;
    @Resource
    private CommentService commentService;

    /**
     * @Author Asimple
     * @Description 进入个人中心页面
     **/
    @RequestMapping(value = "/profilePage")
    public String profile(ModelMap map, HttpSession session) {
        map = commonService.getCatalog(map);
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
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
        // 我的评论
        List<Comment> commentList = commentService.getPageByUid(uid, 1, 4);
        int commentNumber = commentService.getCommentsTotal(uid);
        map.put("comments", commentList);
        map.put("commentPage", 1);
        map.put("commentAllPage", commentNumber/4+((commentNumber%4)>0?1:0));

        long totalLike = 0;
        for (Comment comment: commentList) {
            totalLike += comment.getLikeNum();
        }

        // 左边栏目信息
        map.put("viewCount", viewHistoryNumber);
        map.put("myFilmsCount", total);
        map.put("commentCount", commentNumber);
        map.put("totalLike", totalLike);
        return "index/profile";
    }

    /**
     * @Author Asimple
     * @Description 视频分享页面
     **/
    @RequestMapping(value = "/share")
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
    @RequestMapping( value = "/addFilm", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addFilm(Film film, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
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
    @RequestMapping( value = "/delFilm")
    @ResponseBody
    public String delFilm(String film_id) {
        LogUtil.info(Profile.class, "film_id = " + film_id);
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
    @RequestMapping(value = "/addRes")
    @ResponseBody
    public String addRes(Res res, String film_id) {
        JSONObject jsonObject = new JSONObject();
        String id = resService.addRes(res, film_id);
        jsonObject.put("id", id);
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 删除资源
     **/
    @RequestMapping( value = "/delRes")
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
    @RequestMapping( value = "/updateIsUse")
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
    @RequestMapping( value = "/updateFilmInfo")
    @ResponseBody
    public String updateFilmInfo(String film_id, String val, String key, HttpSession session) {
        Map<String, Object> param = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        Film film = filmService.load(film_id);
        param.put("val", val);
        param.put("key", key);
        param.put("filePath", session.getServletContext().getRealPath(film.getImage()));
        param.put("film", film);
        if( commonService.updateFilmInfo(param) ) {
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
    @RequestMapping(value = "/getSubClass", produces = "text/html;charset=UTF-8")
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
    @RequestMapping(value = "/getType", produces = "text/html;charset=UTF-8")
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
    @RequestMapping(value = "/getFilmAjax", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getFilm(HttpSession session, int pc, String type) {
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
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

    @RequestMapping(value = "/getMyComments", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMyComments(HttpSession session, int pc) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
        jsonObject.put("commentList", commentService.getPageByUid(user.getId(), pc, 4));
        jsonObject.put("pageNo", pc);
        jsonObject.put("totalPage", commentService.getCommentsTotal(user.getId()));
        return jsonObject.toString();
    }

    private void updateRedis(String id) {
        if( !"0".equals(id) ) {
            rankTask.commendRank();
        }
    }

}
