package com.asimple.controller;

import com.asimple.entity.CataLog;
import com.asimple.entity.Decade;
import com.asimple.entity.Level;
import com.asimple.entity.Loc;
import com.asimple.service.ICataLogService;
import com.asimple.service.IDecadeService;
import com.asimple.service.ILevelService;
import com.asimple.service.ILocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 后台管理
 * @author: Asimple
 */
@Controller
@RequestMapping("/admin")
public class Manager {

    @Resource
    private ILocService locService;
    @Resource
    private ILevelService levelService;
    @Resource
    private IDecadeService decadeService;
    @Resource
    private ICataLogService cataLogService;

    @RequestMapping(value = {"/", "/index.html"})
    public String backIndex(ModelMap map) {
        getCatalog(map);
        return "manager/film";
    }


    private void getCatalog(ModelMap model) {
        List<Loc> locList = locService.listIsUse();
        List<Level> levelList = levelService.listIsUse();
        List<Decade> decadeList = decadeService.listIsUse();
        List<CataLog> cataLogList = cataLogService.listIsUse();

        //读取路径下的文件返回UTF-8类型json字符串
        model.addAttribute("locList", locList);
        model.addAttribute("levelList", levelList);
        model.addAttribute("decadeList", decadeList);
        model.addAttribute("cataLogList", cataLogList);
    }

}
