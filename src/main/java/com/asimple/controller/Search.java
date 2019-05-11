package com.asimple.controller;

import com.alibaba.fastjson.JSONObject;
import com.asimple.entity.*;
import com.asimple.service.*;
import com.asimple.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.tools.Tool;
import java.util.*;

/**
 * @ProjectName video
 * @Description: 查询首页
 * @author: Asimple
 */

@Controller
@RequestMapping("/xl")
public class Search {
    private final static String USER_KEY = "u_skl";
    @Resource
    private IFilmService filmService;
    @Resource
    private ICataLogService cataLogService;
    @Resource
    private ISubClassService subClassService;
    @Resource
    private ITypeService typeService;
    @Resource
    private IRatyService ratyService;
    @Resource
    private IResService resService;
    @Resource
    private ICommonService commonService;

    /**
     * @Author Asimple
     * @Description 查询页面
     **/
    @RequestMapping("/1.html")
    public String index(ModelMap model, HttpServletRequest request) {
        getFilm(model, request);
        String cataLogId = request.getParameter("cataLog_id");
        if (Tools.notEmpty(cataLogId)) {
            List<SubClass> subClasses = subClassService.listIsUse(cataLogId);
            model.addAttribute("subClassList", subClasses);
            model.addAttribute("cataLog_id", cataLogId);
        }
        String subClass_id = request.getParameter("subClass_id");
        if (Tools.notEmpty(subClass_id)) {
            List<Type> types = typeService.listIsUseBySubClass_id(subClass_id);
            model.put("typeList", types);
        }
        model = commonService.getCatalog(model);
        return "index/1";
    }

    /**
     * @Author Asimple
     * @Description 影片详情
     **/
    @RequestMapping("/detail.html")
    public String detail(ModelMap map, String film_id, String src, String j, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        if (film_id == null) return "index/error";
        Film film = filmService.load(film_id);
        film.setResList(resService.getListByFilmId(film_id));
        // VIP资源校验
        if (film.getIsVip() == 1) {
            User u_sk1 = (User) session.getAttribute(USER_KEY);
            // 得到发送请求的页面
            String referer = request.getHeader("referer");
            if (u_sk1 != null) {
                if (u_sk1.getIsVip() == 0) {
                    redirectAttributes.addFlashAttribute("error_info", "not_vip");
                    return "redirect:" + referer;
                }
            } else {
                redirectAttributes.addFlashAttribute("error_info", "not_login");
                return "redirect:" + referer;
            }
        }

        if (Tools.notEmpty(src)) map.addAttribute("src", src);
        if (Tools.notEmpty(j)) map.addAttribute("j", j);
        // 导航栏栏目加载
        List<CataLog> cataLogList = cataLogService.listIsUse();
        map.addAttribute("cataLogList", cataLogList);
        map.addAttribute("film", film);
        // 获取影评总人数
        map.addAttribute("totalRatys", ratyService.getCountByFilm_id(film_id));

        // 获取相同类型的影片
        List<Film> films = filmService.listByType_id(film.getType_id(), 8);
        map.addAttribute("films", films);

        // 下载链接
        List<Res> resListEd2k = new ArrayList<Res>();
        List<Res> resListThunder = new ArrayList<Res>();
        List<Res> resListHttp = new ArrayList<Res>();
        List<Res> resListDupan = new ArrayList<Res>();
        List<Res> resListFlh = new ArrayList<Res>();
        List<Res> resListOther = new ArrayList<Res>();

        for (Res res : film.getResList()) {
            if ("ed2k".equals(res.getLinkType()) && res.getIsUse() == 1) {
                resListEd2k.add(res);
            } else if ("thunder".equals(res.getLinkType()) && res.getIsUse() == 1) {
                resListThunder.add(res);
            } else if ("http".equals(res.getLinkType()) && res.getIsUse() == 1) {
                resListHttp.add(res);
            } else if ("dupan".equals(res.getLinkType()) && res.getIsUse() == 1) {
                resListDupan.add(res);
            } else if ("Flh".equals(res.getLinkType()) && res.getIsUse() == 1) {
                resListFlh.add(res);
            } else if (res.getIsUse() == 1) {
                resListOther.add(res);
            }
        }
        Collections.sort(resListEd2k, new Comparator<Res>() {
            @Override
            public int compare(Res o1, Res o2) {
                return o1.getEpisodes() - o2.getEpisodes();
            }
        });
        Collections.sort(resListThunder, new Comparator<Res>() {
            @Override
            public int compare(Res o1, Res o2) {
                return o1.getEpisodes() - o2.getEpisodes();
            }
        });
        Collections.sort(resListHttp, new Comparator<Res>() {
            @Override
            public int compare(Res o1, Res o2) {
                return o1.getEpisodes() - o2.getEpisodes();
            }
        });
        Collections.sort(resListDupan, new Comparator<Res>() {
            @Override
            public int compare(Res o1, Res o2) {
                return o1.getEpisodes() - o2.getEpisodes();
            }
        });
        Collections.sort(resListFlh, new Comparator<Res>() {
            @Override
            public int compare(Res o1, Res o2) {
                return o1.getEpisodes() - o2.getEpisodes();
            }
        });
        Collections.sort(resListOther, new Comparator<Res>() {
            @Override
            public int compare(Res o1, Res o2) {
                return o1.getEpisodes() - o2.getEpisodes();
            }
        });
        map.addAttribute("resListEd2k", resListEd2k);
        map.addAttribute("resListThunder", resListThunder);
        map.addAttribute("resListHttp", resListHttp);
        map.addAttribute("resListDupan", resListDupan);
        map.addAttribute("resListFlh", resListFlh);
        map.addAttribute("resListOther", resListOther);

        // 添加浏览记录
        User user =(User) session.getAttribute(USER_KEY);
        if( user!=null ) {
            filmService.addViewHistory(film.getId(), user.getId());
        }

        return "index/detail";
    }

    /**
     * @Author Asimple
     * @Description 添加评分
     **/
    @RequestMapping("/addRaty.html")
    @ResponseBody
    public String addRaty(Raty raty) {
        JSONObject jsonObject = new JSONObject();
        raty.setEnTime(DateUtil.getTime());
        if (ratyService.add(raty) == 1) {
            // 添加成功，重新计算评分
            List<Raty> ratyList = ratyService.listByFilmId(raty.getFilm_id());
            long count = 0;
            for (Raty raty1 : ratyList) {
                count = count + Integer.parseInt(raty1.getScore());
            }
            long tem = count / ratyList.size();
            double evaluation = Math.floor(tem * 10d) / 10;
            Film film = filmService.load(raty.getFilm_id());
            film.setEvaluation(evaluation);
            // 更新最新的评分
            if (filmService.update(film)) {
                jsonObject.put("code", "1");
            } else {
                jsonObject.put("code", "0");
            }
        } else {
            jsonObject.put("code", "0");
        }
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 播放器
     **/
    @RequestMapping(value = "/pc.html")
    public String pc(ModelMap map, HttpServletRequest request) {
        return "index/pc";
    }

    /**
     * @Author Asimple
     * @Description 查询弹幕
     **/
    @RequestMapping(value = "/queryBullet.html")
    @ResponseBody
    public String queryBullet(String film_id) {
        StringBuilder stringBuilder = new StringBuilder("[");
        List<Bullet> list = filmService.getBulletByFilmId(film_id);
        int length = list.size();
        for(int i=0; i<length; i++) {
            Bullet bullet = list.get(i); if( i!=0 ) stringBuilder.append(",");
            stringBuilder.append("'"+bullet.toString()+"'");
        }
        stringBuilder.append("]");
        LogUtil.info(stringBuilder.toString()); return stringBuilder.toString();
    }

    /**
     * @Author Asimple
     * @Description 保存弹幕
     **/
    @RequestMapping(value = "/saveBullet.html")
    @ResponseBody
    public String saveBullet(String film_id, String danmu) {
        Map map = JSONObject.parseObject(danmu, Map.class);
        LogUtil.info("map = " + map);
        Bullet bullet = new Bullet(null, (String) map.get("text"), (String) map.get("color"), (String) map.get("position"), (String) map.get("size"), (Integer)map.get("time"), film_id);
        boolean result = filmService.saveBullet(bullet);
        if( !result ) {
            LogUtil.error("保存弹幕失败哟~ 失败弹幕信息：" + bullet);
        } return "callback";
    }


    private void getFilm(ModelMap map, HttpServletRequest request) {
        String name = request.getParameter("name");
        if (Tools.notEmpty(name)) map.addAttribute("name", name);

        // 获取url
        String url = request.getQueryString();
        if (url != null) {
            int index = url.lastIndexOf("&pc=");
            if (index != -1) {
                url = url.substring(0, index);
            }
        }

        // 获取当前页数，默认为1
        String value = request.getParameter("pc");
        int pc = 1;
        if (!Tools.isEmpty(value)) {
            pc = Integer.parseInt(value);
        }
        // 设置每页显示的个数
        int ps = 18;

        // 转化成Film对象
        Film ob = Tools.toBean(request.getParameterMap(), Film.class);
        ob.setIsUse(1);
        PageBean<Film> pageBean = filmService.getPage(ob, pc, ps);
        pageBean.setUrl(url);
        map.addAttribute("pb", pageBean);
    }
}
