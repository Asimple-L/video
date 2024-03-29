package com.asimple.controller;

import com.alibaba.fastjson.JSONObject;
import com.asimple.entity.*;
import com.asimple.service.*;
import com.asimple.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Asimple
 * @ProjectName video
 * @description 查询首页
 */

@RestController
@RequestMapping("/xl")
public class SearchController extends CommonController {
    @Resource
    private FilmService filmService;
    @Resource
    private SubClassService subClassService;
    @Resource
    private TypeService typeService;
    @Resource
    private RatyService ratyService;
    @Resource
    private ResService resService;
    @Resource
    private CommonService commonService;

    /**
     * @author Asimple
     * @description 查询页面
     **/
    @RequestMapping("/index")
    public Object index(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(8);
        Map<String, Object> param = new HashMap<>(4);
        String cataLogId = request.getParameter("cataLog_id");
        param.put("name", request.getParameter("name"));
        param.put("pc", request.getParameter("pc"));
        param.put("ps", request.getParameter("ps"));
        param.put("film", Tools.toBean(request.getParameterMap(), Film.class));
        result.putAll(filmService.getFilmList(param));

        if (Tools.notEmpty(cataLogId)) {
            List<SubClass> subClasses = subClassService.listIsUse(cataLogId);
            result.put("subClassList", subClasses);
            result.put("cataLog_id", cataLogId);
        }
        String subClassId = request.getParameter("subClass_id");
        if (Tools.notEmpty(subClassId)) {
            List<Type> types = typeService.listIsUseBySubClassId(subClassId);
            result.put("typeList", types);
        }
        result.putAll(commonService.getCatalog());
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 影片详情
     **/
    @RequestMapping("/detail")
    public Object detail(HttpServletRequest request, HttpSession session) {
        String filmId = request.getParameter("filmId");
        if (StringUtils.isEmpty(filmId)) {
            return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.PARAM_MISS);
        }
        Map<String, Object> map = new HashMap<>(16);
        String src = request.getParameter("src");
        String j = request.getParameter("j");
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);

        // 鉴权
        Film film = filmService.load(filmId);
        film.setResList(resService.getListByFilmId(filmId));
        // VIP资源校验
        if (film.getIsVip() == 1) {
            if (user == null || user.getIsVip() == 0) {
                return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.PERMISSION_DENIED);
            }
        }

        if (Tools.notEmpty(src)) {
            map.put("src", src);
        }
        if (Tools.notEmpty(j)) {
            map.put("j", j);
        }
        Map<String, Object> param = new HashMap<>(8);
        param.put("film", film);
        param.put("user", user);
        map.putAll(filmService.getFilmDetailInfo(param));

        // 添加浏览记录
        if (user != null) {
            filmService.addViewHistory(film.getId(), user.getId());
        }

        return ResponseReturnUtil.returnSuccessWithData(map);
    }

    /**
     * @author Asimple
     * @description 添加评分
     **/
    @RequestMapping("/addRaty")
    public Object addRaty(Raty raty) {
        raty.setEnTime(DateUtil.getTime());
        double evaluation = ratyService.add(raty);
        if (evaluation != -1) {
            return ResponseReturnUtil.returnSuccessWithData(evaluation);
        }
        return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.OPERATION_ERROR);
    }

    /**
     * @author Asimple
     * @description 查询弹幕
     **/
    @RequestMapping(value = "/queryBullet")
    public String queryBullet(String filmId) {
        StringBuilder stringBuilder = new StringBuilder("[");
        List<Bullet> list = filmService.getBulletByFilmId(filmId);
        int length = list.size();
        for (int i = 0; i < length; i++) {
            Bullet bullet = list.get(i);
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("'").append(bullet.toString()).append("'");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * @author Asimple
     * @description 保存弹幕
     **/
    @RequestMapping(value = "/saveBullet")
    public String saveBullet(String filmId, String danmu) {
        Map map = JSONObject.parseObject(danmu, Map.class);
        Bullet bullet = new Bullet(null, (String) map.get("text"), (String) map.get("color"), (String) map.get("position"), (String) map.get("size"), (Integer) map.get("time"), filmId);
        boolean result = filmService.saveBullet(bullet);
        if (!result) {
            LogUtil.error("保存弹幕失败哟~ 失败弹幕信息：" + bullet);
        }
        return "callback";
    }

}
