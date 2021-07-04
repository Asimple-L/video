package com.asimple.controller;

import com.asimple.entity.Comment;
import com.asimple.entity.User;
import com.asimple.service.CataLogService;
import com.asimple.service.CommentService;
import com.asimple.service.FilmService;
import com.asimple.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Asimple
 * @ProjectName video
 * @description 系统开始
 */
@RestController
public class StartController extends CommonController {
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
    @RequestMapping(value = {"/index", "/"})
    public Object index() {
        Map<String, Object> result = filmService.getIndexInfo();
        return ResponseReturnUtil.returnSuccessWithData(result);
    }

    /**
     * @author Asimple
     * @description 导航栏信息返回
     **/
    @RequestMapping(value = "/getHeaderInfo")
    public Object indexInfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(5);
        result.put("cataLogList", cataLogService.listIsUse());
        result.put("user", getUserInfo(request));
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
        int ps = 6;
        int pc = 1;
        if (StringUtils.isNotBlank(pageSize)) {
            ps = Integer.valueOf(pageSize);
        }
        if (StringUtils.isNotBlank(pageNo)) {
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
    public Object addComment(HttpServletRequest request) {
        if (!RequestUtil.isLogin(request)) {
            return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.LOGIN_FIRST);
        }
        User user = getUserInfo(request);
        String context = request.getParameter("context");
        Map<String, Object> param = new HashMap<>(2);
        param.put("user", user);
        param.put("context", context);

        if (commentService.save(param)) {
            return ResponseReturnUtil.returnSuccessWithMsg(ResponseReturnUtil.OPERATION_SUC);
        }
        return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.OPERATION_ERROR);
    }

    /**
     * @author Asimple
     * @description 评论点赞或者踩
     **/
    @RequestMapping(value = "/changeLikeNum", produces = "application/json;charset=UTF-8")
    public Object changeLikeNum(HttpServletRequest request) {
        if (!RequestUtil.isLogin(request)) {
            return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.LOGIN_FIRST);
        }
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(id)) {
            return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.PARAMETER_ERROR);
        }
        if (commentService.update(id, type)) {
            return ResponseReturnUtil.returnSuccessWithMsg(ResponseReturnUtil.OPERATION_SUC);
        }
        return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.OPERATION_ERROR);
    }

}
