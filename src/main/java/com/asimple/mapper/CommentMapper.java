package com.asimple.mapper;

import com.asimple.entity.Comment;
import com.asimple.util.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper extends IBaseDao<Comment> {

    List<Comment> getPageByUid(@Param("uid") String uid, @Param("start") int start, @Param("count") int count);

    int getCommentsTotal(String uid);

}
