package com.asimple.controller;

import com.asimple.entity.*;
import com.asimple.service.*;
import com.asimple.util.DateUtil;
import com.asimple.util.PageBean;
import com.asimple.util.Tools;
import net.sf.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestHandler;
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

    @Resource
    private IFilmService filmService;
    @Resource
    private ICataLogService cataLogService;
    @Resource
    private ISubClassService subClassService;
    @Resource
    private IDecadeService decadeService;
    @Resource
    private ITypeService typeService;
    @Resource
    private ILocService locService;
    @Resource
    private ILevelService levelService;
    @Resource
    private IRatyService ratyService;
    @Resource
    private IResService resService;

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
        getCatalog(model);
        return "index/1";
    }

    @RequestMapping("/detail.html")
    public String detail(ModelMap map, String film_id, String src, String j, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        if (film_id == null) return "index/error";
        Film film = filmService.load(film_id);
        film.setResList(resService.getListByFilmId(film_id));
        // VIP资源校验
        if (film.getIsVip() == 1) {
            User u_sk1 = (User) session.getAttribute(Authentication.USER_KEY);
            // 得到发送请求的页面
            String referer = request.getHeader("referer");
            if (u_sk1 != null) {
                if (u_sk1.getIsVip() == 0) {
                    redirectAttributes.addFlashAttribute("error_info", "用户不是VIP，不能访问");
                    return "redirect:" + referer;
                }
            } else {
                redirectAttributes.addFlashAttribute("error_info", "未登陆，请先登录");
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

        return "index/detail";
    }

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

    @RequestMapping(value = "/pc.html")
    public String pc(ModelMap map, HttpServletRequest request) {
        return "index/pc";
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

    public void setFilmService(IFilmService filmService) {
        this.filmService = filmService;
    }

    public void setCataLogService(ICataLogService cataLogService) {
        this.cataLogService = cataLogService;
    }

    public void setSubClassService(ISubClassService subClassService) {
        this.subClassService = subClassService;
    }

    public void setDecadeService(IDecadeService decadeService) {
        this.decadeService = decadeService;
    }

    public void setTypeService(ITypeService typeService) {
        this.typeService = typeService;
    }

    public void setLocService(ILocService locService) {
        this.locService = locService;
    }

    public void setLevelService(ILevelService levelService) {
        this.levelService = levelService;
    }
}
