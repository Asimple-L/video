package com.asimple.service.Impl;

import com.asimple.dao.comment.ICommentDao;
import com.asimple.entity.Comment;
import com.asimple.service.ICommentService;
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
@Service("commentService")
public class CommentServiceImpl implements ICommentService {
    @Resource(name = "ICommentDao")
    private ICommentDao commentDao;

    /**
     * @Author Asimple
     * @Description 分页获取评论
     **/
    @Override
    public PageBean<Comment> getPage(Comment comment, int pc, int ps) {
        if( comment == null ) comment = new Comment();
        PageBean<Comment> pageBean = new PageBean<>();
        pageBean.setPc(pc);
        pageBean.setPs(ps);
        pageBean.setTr(commentDao.getTotalCount(comment));
        pageBean.setBeanList(commentDao.getPage(comment, (pc-1)*ps, ps));
        return pageBean;
    }

    /**
     * @Author Asimple
     * @Description 保存评论
     **/
    @Override
    public boolean save(Comment comment) {
        comment.setId(Tools.UUID());
        comment.setDate_create(new Date());
        comment.setDate_update(new Date());
        comment.setLikeNum(0);
        comment.setUnlikeNum(0);
        return commentDao.add(comment)==1;
    }

    /**
     * @Author Asimple
     * @Description 更新评论
     **/
    @Override
    public boolean update(Comment comment) {
        return commentDao.update(comment)==1;
    }

    /**
     * @Author Asimple
     * @Description 根据id获取评论
     **/
    @Override
    public Comment load(String id) {
        return commentDao.load(id);
    }

    /**
     * @Author Asimple
     * @Description 分页获取用户评论
     **/
    @Override
    public List<Comment> getPageByUid(String uid, int pc, int ps) {
        return commentDao.getPageByUid(uid, (pc-1)*ps, ps);
    }

    /**
     * @Author Asimple
     * @Description 获取用户评论数目
     **/
    @Override
    public int getCommentsTotal(String uid) {
        return commentDao.getCommentsTotal(uid);
    }
}
