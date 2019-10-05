package com.asimple.mapper;

import com.asimple.entity.Comment;
import com.asimple.util.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Asimple
 * @description 评论mapper类
 */

@Repository
public interface CommentMapper extends IBaseDao<Comment> {

    /**
     * 分页获取评论信息
     * @param uid 用户id
     * @param start 开始条数
     * @param count 结束条数
     * @return 评论列表
     */
    List<Comment> getPageByUid(@Param("uid") String uid, @Param("start") int start, @Param("count") int count);

    /**
     * 获取评论总数
     * @param uid 用户id
     * @return 评论数
     */
    int getCommentsTotal(String uid);

}
