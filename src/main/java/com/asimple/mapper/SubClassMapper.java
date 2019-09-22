package com.asimple.mapper;

import com.asimple.entity.SubClass;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubClassMapper extends IBaseDao<SubClass> {
    List<SubClass> listIsUse(String id);
}
