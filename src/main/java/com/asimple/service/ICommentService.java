package com.asimple.service;

import com.asimple.entity.Comment;
import com.asimple.util.PageBean;

import java.util.List;

public interface ICommentService {

    PageBean<Comment> getPage(Comment comment, int pc, int ps);

    boolean save(Comment comment);

    boolean update(Comment comment);

    Comment load(String id);

    List<Comment> getPageByUid(String uid, int pc, int ps);

    int getCommentsTotal(String uid);

}
