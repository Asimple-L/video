package com.asimple.controller;

import com.asimple.entity.*;
import com.asimple.service.*;
import com.asimple.task.RankTask;
import com.asimple.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName video
 * @description 个人中心
 * @author Asimple
 */
@RestController
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
    @Resource
    private UserService userService;

    /**
     * @author Asimple
     * @description 进入个人中心页面
     **/
    @RequestMapping(value = "/profilePage")
    public Object profile(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(16);
        if( RequestUtil.isLogin(request) ) {
            return ResponseReturnUtil.returnErrorWithMsg("未登录，请登录后重试!");
        }
        if( RequestUtil.isSelfLogin(request) ) {
            return ResponseReturnUtil.returnErrorWithMsg("参数错误，请登录后重试!");
        }
        User user = RequestUtil.getUserInformation(request);
        Map<String, Object> param = new HashMap<>(4);
        param.put("uid", user.getId());
        result.putAll(userService.getProfileInfo(param));
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 视频分享页面初始化
     **/
    @RequestMapping(value = "/share", produces = "application/json;charset=UTF-8")
    public Object shareVideo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(8);
        String filmId = request.getParameter("film_id");
        Film film = filmService.load(filmId);
        if( film!=null ) {
            result.put("film", film);
            List<Res> list = resService.getListByFilmId(filmId);
            if( list.size()== 0 ) {
                result.put("res", null);
            } else {
                result.put("res", list);
            }
        }
        result.putAll( commonService.getCatalog() );
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 添加影片
     **/
    @RequestMapping( value = "/addFilm", produces = "application/json;charset=UTF-8")
    public Object addFilm(Film film, HttpSession session) {
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
        film.setUser(user);
        String id = filmService.save(film);
        this.updateRedis(id);
        return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
    }

    /**
     * @author Asimple
     * @description 删除影片
     **/
    @RequestMapping( value = "/delFilm", produces = "application/json;charset=UTF-8")
    public Object delFilm(String filmId) {
        if ( filmService.deleteById(filmId) ) {
            this.updateRedis("1");
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        } else {
            return ResponseReturnUtil.returnErrorWithMsg("系统繁忙，请稍后重试!");
        }
    }

    /**
     * @author Asimple
     * @description 添加资源
     **/
    @RequestMapping(value = "/addRes")
    public Object addRes(Res res, String filmId) {
        Map<String, Object> result = new HashMap<>(1);
        result.put("id", resService.addRes(res, filmId));
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
            return ResponseReturnUtil.returnErrorWithMsg("删除失败，请稍后重试!");
        }
    }

    /**
     * @author Asimple
     * @description 更改在离线状态
     **/
    @RequestMapping( value = "/updateIsUse")
    public Object updateIsUse(String resId) {
        if( resService.updateIsUse(resId) ) {
            this.updateRedis("1");
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        }
        return ResponseReturnUtil.returnErrorWithMsg("更新失败，请稍后重试!");
    }

    /**
     * @author Asimple
     * @description 更新影片信息
     **/
    @RequestMapping( value = "/updateFilmInfo")
    public Object updateFilmInfo(String filmId, String val, String key, HttpSession session) {
        Map<String, Object> param = new HashMap<>(8);
        Film film = filmService.load(filmId);
        param.put("val", val);
        param.put("key", key);
        param.put("filePath", session.getServletContext().getRealPath(film.getImage()));
        param.put("film", film);
        if( commonService.updateFilmInfo(param) ) {
            this.updateRedis("1");
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        }
        return ResponseReturnUtil.returnErrorWithMsg("更新失败，请稍后重试!");
    }

    /**
     * @author Asimple
     * @description 获取二级目录信息
     **/
    @RequestMapping(value = "/getSubClass", produces = "application/json;charset=UTF-8")
    public Object getSubClass(String catalogId) {
        List<SubClass> subClasses = subClassService.listIsUse(catalogId);
        JsonConfig jsonConfig = new JsonConfig();
        // 过滤列表
        jsonConfig.setJsonPropertyFilter((o, s, o1) -> !("id".equals(s) || "name".equals(s)));
        JSONArray jsonArray = JSONArray.fromObject(subClasses, jsonConfig);
        return ResponseReturnUtil.returnSuccessWithData(jsonArray);
    }

    /**
     * @author Asimple
     * @description 获取类型
     **/
    @RequestMapping(value = "/getType", produces = "application/json;charset=UTF-8")
    public Object getType(String subClassId) {
        List<Type> types = typeService.listIsUseBySubClassId(subClassId);
        JsonConfig jsonConfig = new JsonConfig();
        // 过滤列表
        jsonConfig.setJsonPropertyFilter((o, s, o1) -> !("id".equals(s) || "name".equals(s)));
        JSONArray jsonArray = JSONArray.fromObject(types, jsonConfig);
        return ResponseReturnUtil.returnSuccessWithData(jsonArray);
    }

    /**
     * @author Asimple
     * @description 返回我的视频
     **/
    @RequestMapping(value = "/getFilmAjax", produces = "application/json;charset=UTF-8")
    public Object getFilm(HttpSession session,@RequestParam(required = false) String pc, String type) {
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
        Map<String, Object> result = new HashMap<>(8);
        Map<String, Object> param = new HashMap<>(4);
        if( StringUtils.isEmpty(pc) ) {
            pc = "1";
        }
        param.put("uid", user.getId());
        param.put("page", String.valueOf(pc));
        param.put("type", type);
        result.putAll(userService.getProfileInfoByType(param));
        return  ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 获取我的评论
     */
    @RequestMapping(value = "/getMyComments", produces = "application/json;charset=UTF-8")
    public Object getMyComments(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(4);
        Map<String, Object> params = new HashMap<>(4);
        if ( RequestUtil.isSelfLogin(request) ) {
            return ResponseReturnUtil.returnErrorWithMsg("请登录!");
        }
        String pc = request.getParameter("pc");
        if( StringUtils.isEmpty(pc) ) {
            pc = "1";
        }
        User user = RequestUtil.getUserInformation(request);
        params.put("uid", user.getId());
        params.put("page", String.valueOf(pc));
        result.put("comments", commentService.getPageByUid(params));
        result.put("pageNo", pc);
        result.put("total", commentService.getCommentsTotal(user.getId()));
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    private void updateRedis(String id) {
        if( !"0".equals(id) ) {
            rankTask.commendRank();
        }
    }

}
