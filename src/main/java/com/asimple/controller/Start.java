package com.asimple.controller;

import com.asimple.entity.CataLog;
import com.asimple.entity.Film;
import com.asimple.service.ICataLogService;
import com.asimple.service.IFilmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
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

    /**
     * @Author Asimple
     * @Description 首页访问
     **/
    @RequestMapping("/index.html")
    public String index(ModelMap model) {
        // 查询用户菜单列表
        List<CataLog> logList = cataLogService.listIsUse();
        model.put("cataLogList", logList);

        // 查询推荐电影
        List<Object> list = new ArrayList<>();
        for (CataLog aLogList : logList) {
            List<Film> films = filmService.listByCataLog_id(aLogList.getId(), 12);
            if (films.size() != 0) {
                list.add(films);
            }
        }
        model.put("filmTuijian", list);

        // 电影排行榜
        List<Object> list1 = new ArrayList<>();
        for (CataLog aLogList : logList) {
            List<Film> films = filmService.listByEvaluation(aLogList.getId(), 13);
            if (films.size() != 0) {
                list1.add(films);
            }
        }
        model.put("filmPaiHang", list1);

        return "index/index";
    }

    @RequestMapping("/note.html")
    public String note(ModelMap map) {
        List<CataLog> cataLogList = cataLogService.listIsUse();
        map.addAttribute("cataLogList", cataLogList);
        return "index/note";
    }

    @RequestMapping("/error.html")
    public String error() {
        return "index/error";
    }

    public void setCataLogService(ICataLogService cataLogService) {
        this.cataLogService = cataLogService;
    }
}
