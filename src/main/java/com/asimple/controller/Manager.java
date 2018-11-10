package com.asimple.controller;

import com.asimple.entity.*;
import com.asimple.service.*;
import com.asimple.util.DateUtil;
import com.asimple.util.MD5Auth;
import com.asimple.util.PageBean;
import com.asimple.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    private final static String key = "a1s2i3m4p5l6e7";
    @Resource
    private IUserService userService;
    @Resource
    private ILocService locService;
    @Resource
    private ILevelService levelService;
    @Resource
    private IDecadeService decadeService;
    @Resource
    private ICataLogService cataLogService;
    @Resource
    private IResService resService;
    @Resource
    private IFilmService filmService;
    @Resource
    private ISubClassService subClassService;
    @Resource
    private ITypeService typeService;
    @Resource
    private IRatyService ratyService;

    /**
     * @Author Asimple
     * @Description 跳转到后台登录页面
     **/
    @RequestMapping(value = "/login.html", method = { RequestMethod.GET })
    public String adminLoginPage() {
        return "manager/login";
    }

    @RequestMapping(value = "/login.html", method = { RequestMethod.POST })
    public String adminLogin(String username, String password, ModelMap map, HttpSession session) {
        // 用户名或者邮箱登录
        boolean flag = false;// 是否登录成功
        User user = new User();
        List<User> users = null;
        user.setUserName(username);
        users = userService.findByCondition(user);
        // 用户名登录
        if( users != null && users.size()>0 ) {
            System.err.println(1);
            flag = checkAccount(password, users, session, map);
        } else { // 邮箱登录
            user.setUserName(null);
            user.setUserEmail(username);
            users = userService.findByCondition(user);
            if( users!=null && users.size()>0 ) {
                System.err.println(2);
                flag = checkAccount(password, users, session, map);
            } else {
                System.err.println(3);
                map.addAttribute("msg", "请登录正确的管理员账号！");
            }
        }
        // 登录成功重定向到后台首页
        if ( flag ) return "redirect:/admin/index.html";
        else return "manager/login";
    }

    /**
     * @Author Asimple
     * @Description 后台首页
     **/
    @RequestMapping(value = {"/", "/index.html"})
    public String backIndex(ModelMap map, HttpSession session) {
        return "manager/index";
    }

    /**
     * @Author Asimple
     * @Description 影片资源管理
     **/
    @RequestMapping(value = "/film.html")
    public String film(ModelMap map, String film_id) {
        if ( film_id != null && !"".equals(film_id) ) {// 如果有id，则是编辑
            // 获取电影信息
            map.addAttribute("film", filmService.load(film_id));
            // 获取资源信息
            List<Res> list = resService.getListByFilmId(film_id);
            if( list.size()== 0 )  map.addAttribute("res", null);
            else map.addAttribute("res", list);
        }
        getCatalog(map);
        return "manager/addFilm";
    }

    /**
     * @Author Asimple
     * @Description 查看所有影视信息
     **/
    @RequestMapping(value = "/list.html")
    public String filmList(ModelMap map, HttpServletRequest request) {
        getFilmList(map, request, 0);
        return "manager/allFilm";
    }

    /**
     * @Author Asimple
     * @Description 添加影片
     **/
    @RequestMapping( value = "/addFilm.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addFilm(Film film) {
        JSONObject jsonObject = new JSONObject();
        String id = filmService.save(film);
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
        System.err.println(film_id);
        JSONObject jsonObject = new JSONObject();
        if ( filmService.deleteById(film_id) ) jsonObject.put("code", "1");
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
           System.err.println(res.getName());
           System.err.println(resName.length);
           // 视频名称
           String name = resName[0];
           // 开始集数与结束集数
           int begin = Integer.parseInt(resName[1]);
           int end = Integer.parseInt(resName[2]);
           // 链接分割标志
            String flag = "";
           if( resName.length > 3 ) {
               flag = resName[3];
               String res_links[] = res.getLink().replaceAll("\\n","").split(flag);
               int cz = begin - 1;
               for(int i=begin; i<=end; i++) {
                   res.setName(name.replace("@@", ""));
                   res.setEpisodes(i);
                   if( "Flh".equals(res.getLinkType()) ) flag = "";
                   res.setLink(flag+res_links[i-cz-1]);
                   id = resService.add(res);
               }
           }
        } else id = resService.add(res);
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

    @RequestMapping( value = "/updateIsUse.html")
    @ResponseBody
    public String updateIsUse(String res_id) {
        JSONObject jsonObject = new JSONObject();
        System.err.println(res_id);
        if( resService.updateIsUse(res_id) ) jsonObject.put("code", "1");
        else jsonObject.put("code", "0");
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

    private boolean checkAccount(String password, List<User> users, HttpSession session, ModelMap map) {
        User user = users.get(0);
        if( MD5Auth.validatePassword(user.getUserPasswd(), password+key, "UTF-8") && user.getIsManager() == 1 ) { // 登录成功
            session.setAttribute("adminUser", user);
            return true;
        } else {
            map.addAttribute("msg", "请登录正确的管理员账号！");
            return false;
        }
    }

    private void getFilmList(ModelMap map, HttpServletRequest request, int flag) {
        String name = request.getParameter("name");
        if( !Tools.isEmpty(name) ) map.addAttribute("name", name);
        // 分页查询所有电影列表
            // 获取url条件信息
        String url = request.getQueryString();
        if( url != null ) {
            int index = url.indexOf("&pc=");
            if( index != -1 ) url = url.substring(0, index);
        }
            // 当前页面数
        String value = request.getParameter("pc");
        int pc = 1;
        if( Tools.notEmpty(value) ) pc = Integer.parseInt(value);
            // 每页显示数目
        int ps = 27;
            // 获取页面传递的查询条件
        Film ob = Tools.toBean(request.getParameterMap(), Film.class);
        if( flag != 0 ) ob.setIsUse(1);

        PageBean<Film> pageBean = filmService.getPage(ob, pc, ps);
        pageBean.setUrl(url);
        map.addAttribute("pb", pageBean);
    }

}
