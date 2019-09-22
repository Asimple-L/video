package com.asimple.controller;

import com.alibaba.fastjson.JSONObject;
import com.asimple.entity.CataLog;
import com.asimple.entity.Comment;
import com.asimple.entity.Film;
import com.asimple.entity.User;
import com.asimple.service.CataLogService;
import com.asimple.service.CommentService;
import com.asimple.service.FilmService;
import com.asimple.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 系统开始
 * @author: Asimple
 */
@Controller
public class Start {
    @Resource
    private CataLogService cataLogService;

    @Resource
    private FilmService filmService;

    @Resource
    private CommentService commentService;

    /**
     * @Author Asimple
     * @Description 首页访问
     **/
    @RequestMapping(value = { "/index", "/"})
    public String index(ModelMap model) {
        // 查询用户菜单列表
        List<CataLog> logList =  cataLogService.listIsUse();
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

        // 电影排行榜0
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

    /**
     * @Author Asimple
     * @Description 留言
     **/
    @RequestMapping("/note")
    public String note(ModelMap map) {
        List<CataLog> cataLogList = cataLogService.listIsUse();
        PageBean<Comment> pageBean = commentService.getPage(null, 1, 20);
        map.addAttribute("cataLogList", cataLogList);
        map.addAttribute("pb", pageBean);
        return "index/note";
    }

    /**
     * @Author Asimple
     * @Description 保存留言
     **/
    @RequestMapping(value = "/saveComment", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addComment(String context, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) session.getAttribute("u_skl");
        if( user == null ) {
            jsonObject.put("msg", "请登录后评论!");
            return jsonObject.toString();
        }
        Comment comment = new Comment();
        comment.setContext(context);
        comment.setUser(user);
        if( commentService.save(comment) ) {
            jsonObject.put("msg", "发布成功!");
        } else {
            jsonObject.put("msg", "发布评论失败!请稍后重试!");
        }
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 评论点赞或者踩
     **/
    @RequestMapping(value = "/changeLikeNum", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String changeLikeNum(String type, String id) {
        JSONObject jsonObject = new JSONObject();
        Comment comment = commentService.load(id);
        if( "0".equals(type) ) {
            comment.setLikeNum(comment.getLikeNum()+1);
        } else {
            comment.setUnlikeNum(comment.getUnlikeNum()+1);
        }
        comment.setDate_update(new Date());
        if( commentService.update(comment) ) {
            jsonObject.put("code", "000000");
        } else {
            jsonObject.put("code", "111111");
        }
        return jsonObject.toString();
    }

}
