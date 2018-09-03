package com.asimple.service.Impl;

import com.asimple.dao.type.ITypeDao;
import com.asimple.entity.Type;
import com.asimple.service.ITypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 类型服务实现类
 * @author: Asimple
 */
@Service("typeService")
public class TypeServiceImpl implements ITypeService {

    @Resource(name = "ITypeDao")
    private ITypeDao typeDao;

    @Override
    public List<Type> listIsUseBySubClass_id(String subClass_id) {
        return typeDao.listIsUseBySubClass_id(subClass_id);
    }
}
