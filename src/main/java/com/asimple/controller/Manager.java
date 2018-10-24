package com.asimple.controller;

import com.asimple.entity.*;
import com.asimple.service.ICataLogService;
import com.asimple.service.IDecadeService;
import com.asimple.service.ILevelService;
import com.asimple.service.ILocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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

    /**
     * @Author Asimple
     * @Description 跳转到后台登录页面
     **/
    @RequestMapping("/loginPage.html")
    public String adminLoginPage() {
        return "manager/login";
    }

    @RequestMapping("/login.html")
    public String adminLogin() {
        return "";
    }

    /**
     * @Author Asimple
     * @Description 后台首页
     **/
    @RequestMapping(value = {"/", "/index.html"})
    public String backIndex(ModelMap map, HttpSession session) {
        return "manager/index";
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

}
