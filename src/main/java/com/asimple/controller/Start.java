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
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
     * @description 导航栏信息返回
     **/
    @RequestMapping( value = "/getHeaderInfo")
    public Object indexInfo(HttpSession session) {
        Map<String, Object> result = new HashMap<>(5);
        result.put("cataLogList", cataLogService.listIsUse());
        result.put("user", session.getAttribute(VideoKeyNameUtil.USER_KEY));
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 留言
     **/
    @RequestMapping(value = "/note", produces = "application/json;charset=UTF-8")
    public Object note(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        int ps = 20;
        int pc = 1;
        if(StringUtils.isNotBlank(pageSize) ) {
            ps = Integer.valueOf(pageSize);
        }
        if( StringUtils.isNotBlank(pageNo) ) {
            pc = Integer.valueOf(pageNo);
        }
        Map<String, Object> map = new HashMap<>(2);
        PageBean<Comment> pageBean = commentService.getPage(null, pc, ps);
        map.put("pb", pageBean);
        return ResponseReturnUtil.returnSuccessWithData(map);
    }

    /**
     * @author Asimple
     * @description 保存留言
     **/
    @RequestMapping(value = "/saveComment", produces = "application/json;charset=UTF-8")
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
    @RequestMapping(value = "/changeLikeNum", produces = "application/json;charset=UTF-8")
    public Object changeLikeNum(HttpServletRequest request) {
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        if( StringUtils.isEmpty(type) || StringUtils.isEmpty(id) ) {
            return ResponseReturnUtil.returnErrorWithMsg("参数错误,请重试!");
        }
        if( commentService.update(type, id) ) {
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        }
        return ResponseReturnUtil.returnErrorWithMsg("操作失败,请稍后重试!");
    }

}
