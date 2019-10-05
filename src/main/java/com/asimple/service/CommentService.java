package com.asimple.service;

import com.asimple.entity.Comment;
import com.asimple.entity.User;
import com.asimple.mapper.CommentMapper;
import com.asimple.util.PageBean;
import com.asimple.util.Tools;
import com.asimple.util.VideoKeyNameUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName video
 * @description 评论Service实现类
 * @author Asimple
 */
@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;

    /**
     * 分页获取评论
     * @param comment 评论实体
     * @param pc 开始条数
     * @param ps 结束条数
     * @return 评论分页信息
     */
    public PageBean<Comment> getPage(Comment comment, int pc, int ps) {
        if( comment == null ) {
            comment = new Comment();
        }
        PageBean<Comment> pageBean = new PageBean<>();
        pageBean.setPc(pc);
        pageBean.setPs(ps);
        pageBean.setTr(commentMapper.getTotalCount(comment));
        pageBean.setBeanList(commentMapper.getPage(comment, (pc-1)*ps, ps));
        return pageBean;
    }

    /**
     * 保存评论
     * @param params 参数列表，必须包含uid
     * @return 保存成功返回true
     */
    public boolean save(Map<String, Object> params) {
        Comment comment = (Comment) params.get("comment");
        if( null == comment ) {
            comment = new Comment();
            comment.setContext((String) params.get("context"));
            comment.setUser((User) params.get("user"));
            comment.setId(Tools.UUID());
            comment.setDate_create(new Date());
            comment.setDate_update(new Date());
            comment.setLikeNum(0);
            comment.setUnlikeNum(0);
        }
        return commentMapper.add(comment)==1;
    }

    /**
     * 更新评论
     * @param id 评论id
     * @param type 类型
     * @return 更新成功返回true
     */
    public boolean update(String id, String type) {
        Comment comment = load(id);
        if( VideoKeyNameUtil.SUCCESS_CODE.equals(type) ) {
            comment.setLikeNum(comment.getLikeNum()+1);
        } else {
            comment.setUnlikeNum(comment.getUnlikeNum()+1);
        }
        comment.setDate_update(new Date());
        return commentMapper.update(comment)==1;
    }

    /**
     * 根据id获取评论
     * @param id 评论id
     * @return 评论实体
     */
    public Comment load(String id) {
        return commentMapper.load(id);
    }

    /**
     * 分页获取用户评论
     * @param params 参数列表
     * @return 用户评论列表
     */
    public List<Comment> getPageByUid(Map<String, Object> params) {
        String uid = (String) params.get("uid");
        String page = (String) params.get("page");
        String pageSize = (String) params.get("pageSize");
        if(StringUtils.isEmpty(page) ) {
            page = "1";
        }
        if( StringUtils.isEmpty(pageSize) ) {
            pageSize = "4";
        }
        int pc = Integer.valueOf(page);
        int ps = Integer.valueOf(pageSize);
        return commentMapper.getPageByUid(uid, (pc-1)*ps, ps);
    }

    /**
     * 获取用户评论数
     * @param uid 用户id
     * @return 用户评论数
     */
    public int getCommentsTotal(String uid) {
        return commentMapper.getCommentsTotal(uid);
    }
}
