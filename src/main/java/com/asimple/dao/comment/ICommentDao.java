package com.asimple.dao.comment;

import com.asimple.entity.Comment;
import com.asimple.util.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICommentDao extends IBaseDao<Comment> {

    List<Comment> getPageByUid(@Param("uid") String uid, @Param("start") int start, @Param("count") int count);

    int getCommentsTotal(String uid);

}
