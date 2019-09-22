package com.asimple.mapper;

import com.asimple.entity.User;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName video
 * @Description: 用户Dao层代码
 * @author: Asimple
 */
@Repository
public interface UserMapper extends IBaseDao<User> {

}
