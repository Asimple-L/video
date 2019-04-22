package com.asimple.controller;

import com.asimple.entity.CataLog;
import com.asimple.entity.Film;
import com.asimple.entity.User;
import com.asimple.service.ICataLogService;
import com.asimple.service.IFilmService;
import com.asimple.util.LogUtil;
import com.asimple.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 系统开始
 * @author: Asimple
 */
@Controller
public class Start {
    @Resource(name = "cataLogService")
    private ICataLogService cataLogService;

    @Resource(name="filmService")
    private IFilmService filmService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * @Author Asimple
     * @Description 首页访问
     **/
    @RequestMapping("/index.html")
    public String index(ModelMap model, HttpSession session) {
//        User user = new User();
//        user.setIsVip(1);
//        user.setUserName("Asimple");
////        model.put("user", user);
//        session.setAttribute("u_skl",user);
//        return "index/profile";
        // 查询用户菜单列表
        List<CataLog> logList = (List<CataLog>) redisUtil.get("index_cataLogList");
        LogUtil.info(Start.class, "logList = " + logList);
        if( null==logList || logList.isEmpty() ) {
            logList = cataLogService.listIsUse();
        }
        model.put("cataLogList", logList);

        // 查询推荐电影
        List<Object> list = (List<Object>) redisUtil.get("index_filmTuijian");
        if( null==list || list.isEmpty() ) {
            list = new ArrayList<>();
            for (CataLog aLogList : logList) {
                List<Film> films = filmService.listByCataLog_id(aLogList.getId(), 12);
                if (films.size() != 0) {
                    list.add(films);
                }
            }
        }
        model.put("filmTuijian", list);

        // 电影排行榜
        List<Object> list1 = (List<Object>) redisUtil.get("index_filmPaiHang");
        if( null==list1 || list1.isEmpty() ) {
            list1 = new ArrayList<>();
            for (CataLog aLogList : logList) {
                List<Film> films = filmService.listByEvaluation(aLogList.getId(), 13);
                if (films.size() != 0) {
                    list1.add(films);
                }
            }
        }
        model.put("filmPaiHang", list1);

        return "index/index";
    }

    /**
     * @Author Asimple
     * @Description 联系我们
     **/
    @RequestMapping("/note.html")
    public String note(ModelMap map) {
        List<CataLog> cataLogList = cataLogService.listIsUse();
        map.addAttribute("cataLogList", cataLogList);
        return "index/note";
    }

    /**
     * @Author Asimple
     * @Description 错误页面
     **/
    @RequestMapping("/error.html")
    public String error() {
        return "index/error";
    }

    public void setCataLogService(ICataLogService cataLogService) {
        this.cataLogService = cataLogService;
    }
}
