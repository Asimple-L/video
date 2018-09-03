package com.asimple.service.Impl;

import com.asimple.dao.subClass.ISubClassDao;
import com.asimple.entity.SubClass;
import com.asimple.service.ISubClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 子类服务实现类
 * @author: Asimple
 */
@Service("subClassService")
public class SubClassServiceImpl implements ISubClassService {

    @Resource(name = "ISubClassDao")
    private ISubClassDao subClassDao;

    @Override
    public List<SubClass> listIsUse(String id) {
        return subClassDao.listIsUse(id);
    }
}
