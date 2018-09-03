package com.asimple.dao.subClass;

import com.asimple.entity.SubClass;
import com.asimple.util.IBaseDao;

import java.util.List;

public interface ISubClassDao extends IBaseDao<SubClass> {
    List<SubClass> listIsUse(String id);
}
