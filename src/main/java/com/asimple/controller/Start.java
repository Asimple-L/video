package com.asimple.controller;

import com.asimple.entity.CataLog;
import com.asimple.entity.Comment;
import com.asimple.entity.User;
import com.asimple.service.CataLogService;
import com.asimple.service.CommentService;
import com.asimple.service.FilmService;
import com.asimple.util.PageBean;
import com.asimple.util.ResponseReturnUtil;
import com.asimple.util.VideoKeyNameUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ProjectName video
 * @description 系统开始
 * @author Asimple
 */
@RestController
public class Start {
    @Resource
    private CataLogService cataLogService;
    @Resource
    private FilmService filmService;
    @Resource
    private CommentService commentService;

    /**
     * @author Asimple
     * @description 首页访问
     **/
    @RequestMapping(value = { "/index", "/"})
    public Object index() {
        Map<String, Object> result = filmService.getIndexInfo();
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 留言
     **/
    @RequestMapping("/note")
    public Object note() {
        Map<String, Object> map = new HashMap<>(2);
        List<CataLog> cataLogList = cataLogService.listIsUse();
        PageBean<Comment> pageBean = commentService.getPage(null, 1, 20);
        map.put("cataLogList", cataLogList);
        map.put("pb", pageBean);
        return ResponseReturnUtil.returnSuccessWithData(map);
    }

    /**
     * @author Asimple
     * @description 保存留言
     **/
    @RequestMapping(value = "/saveComment", produces = "text/html;charset=UTF-8")
    public Object addComment(String context, HttpSession session) {
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
        if( user == null ) {
            return ResponseReturnUtil.returnErrorWithMsg("请登录后评论!");
        }
        Map<String, Object> param = new HashMap<>(2);
        param.put("user", user);
        param.put("context", context);

        if( commentService.save(param) ) {
            return ResponseReturnUtil.returnSuccessWithMsg("发布成功!");
        }
        return ResponseReturnUtil.returnErrorWithMsg("发布评论失败!请稍后重试!");
    }

    /**
     * @author Asimple
     * @description 评论点赞或者踩
     **/
    @RequestMapping(value = "/changeLikeNum", produces = "text/html;charset=UTF-8")
    public Object changeLikeNum(String type, String id) {
        if( commentService.update(type, id) ) {
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        }
        return ResponseReturnUtil.returnErrorWithMsg("操作失败,请稍后重试!");
    }

}
