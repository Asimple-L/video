package com.asimple.service;

import com.asimple.entity.Comment;
import com.asimple.mapper.CommentMapper;
import com.asimple.util.PageBean;
import com.asimple.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 评论Service实现类
 * @author: Asimple
 */
@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;

    /**
     * @Author Asimple
     * @Description 分页获取评论
     **/
    public PageBean<Comment> getPage(Comment comment, int pc, int ps) {
        if( comment == null ) comment = new Comment();
        PageBean<Comment> pageBean = new PageBean<>();
        pageBean.setPc(pc);
        pageBean.setPs(ps);
        pageBean.setTr(commentMapper.getTotalCount(comment));
        pageBean.setBeanList(commentMapper.getPage(comment, (pc-1)*ps, ps));
        return pageBean;
    }

    /**
     * @Author Asimple
     * @Description 保存评论
     **/
    public boolean save(Comment comment) {
        comment.setId(Tools.UUID());
        comment.setDate_create(new Date());
        comment.setDate_update(new Date());
        comment.setLikeNum(0);
        comment.setUnlikeNum(0);
        return commentMapper.add(comment)==1;
    }

    /**
     * @Author Asimple
     * @Description 更新评论
     **/
    public boolean update(Comment comment) {
        return commentMapper.update(comment)==1;
    }

    /**
     * @Author Asimple
     * @Description 根据id获取评论
     **/
    public Comment load(String id) {
        return commentMapper.load(id);
    }

    /**
     * @Author Asimple
     * @Description 分页获取用户评论
     **/
    public List<Comment> getPageByUid(String uid, int pc, int ps) {
        return commentMapper.getPageByUid(uid, (pc-1)*ps, ps);
    }

    /**
     * @Author Asimple
     * @Description 获取用户评论数目
     **/
    public int getCommentsTotal(String uid) {
        return commentMapper.getCommentsTotal(uid);
    }
}
