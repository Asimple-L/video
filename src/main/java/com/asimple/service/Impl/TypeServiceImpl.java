package com.asimple.service.Impl;

import com.asimple.dao.type.ITypeDao;
import com.asimple.entity.Type;
import com.asimple.service.ITypeService;
import com.asimple.util.Tools;
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

    /**
     * @Author Asimple
     * @Description 查找二级分类下的所有类型
     **/
    @Override
    public List<Type> listIsUseBySubClass_id(String subClass_id) {
        return typeDao.listIsUseBySubClass_id(subClass_id);
    }

    /**
     * @Author Asimple
     * @Description 根据id查询Type
     **/
    @Override
    public Type load(String id) {
        return typeDao.load(id);
    }

    /**
     * @Author Asimple
     * @Description 添加type并返回id
     **/
    @Override
    public String add(Type type) {
        if(Tools.isEmpty(type.getId()) ) {
            type.setId(Tools.UUID());
        }
        return typeDao.add(type)==1?type.getId():"0";
    }
}
