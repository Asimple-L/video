package com.asimple.service;

import com.asimple.entity.SubClass;
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
    @Resource
    private SubClassService subClassService;

    /**
     * 查找二级分类下的所有类型
     * @param subClassId 二级分类id
     * @return 类型列表
     */
    public List<Type> listIsUseBySubClassId(String subClassId) {
        return typeMapper.listIsUseBySubClassId(subClassId);
    }

    /**
     * 根据id查询Type
     * @param id 类型id
     * @return 类型实体
     */
    public Type load(String id) {
        return typeMapper.load(id);
    }

    /**
     * 添加type并返回id
     * @param type 类型对象
     * @param subClassId 对应的二级分类id
     * @return 添加成功返回true，否则返回false
     */
    public boolean add(Type type, String subClassId) {
        SubClass subClass = subClassService.load(subClassId);
        if( subClass == null ) {
            return false;
        }
        type.setSubClass(subClass);
        if(Tools.isEmpty(type.getId()) ) {
            type.setId(Tools.UUID());
            type.setIsUse(1);
            return typeMapper.add(type)==1;
        }
        return typeMapper.update(type)==1;
    }
}
