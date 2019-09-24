package com.asimple.service;

import com.asimple.entity.Type;
import com.asimple.mapper.TypeMapper;
import com.asimple.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @description 类型服务实现类
 * @author Asimple
 */
@Service
public class TypeService {

    @Resource
    private TypeMapper typeMapper;

    /**
     * @author Asimple
     * @description 查找二级分类下的所有类型
     **/
    public List<Type> listIsUseBySubClass_id(String subClass_id) {
        return typeMapper.listIsUseBySubClass_id(subClass_id);
    }

    /**
     * @author Asimple
     * @description 根据id查询Type
     **/
    public Type load(String id) {
        return typeMapper.load(id);
    }

    /**
     * @author Asimple
     * @description 添加type并返回id
     **/
    public String add(Type type) {
        if(Tools.isEmpty(type.getId()) ) {
            type.setId(Tools.UUID());
        }
        return typeMapper.add(type)==1?type.getId():"0";
    }
}
