package com.asimple.service;

import com.asimple.entity.CataLog;
import com.asimple.entity.SubClass;
import com.asimple.mapper.SubClassMapper;
import com.asimple.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @description 子类服务实现类
 * @author Asimple
 */
@Service
public class SubClassService {

    @Resource
    private SubClassMapper subClassMapper;
    @Resource
    private CataLogService cataLogService;

    /**
     * 查询一级分类下所有可使用的二级分类
     * @param id 一级分类id
     * @return 二级分类列表
     */
    public List<SubClass> listIsUse(String id) {
        return subClassMapper.listIsUse(id);
    }

    /**
     * 添加二级分类
     * @param subClass 二级分类对象
     * @param cataLogId 对应的一级分类id
     * @return 添加成功返回id，否则返回0
     */
    public String add(SubClass subClass, String cataLogId) {
        CataLog cataLog = cataLogService.load(cataLogId);
        subClass.setCataLog(cataLog);
        subClass.setIsUse(1);
        if(Tools.isEmpty(subClass.getId()) ) {
            subClass.setId(Tools.UUID());
        }
        return subClassMapper.add(subClass)==1?subClass.getId():"0";
    }

    /**
     * 根据id加载二级分类
     * @param subClassId 二级分类id
     * @return 二级分类实体
     */
    public SubClass load(String subClassId) {
        return subClassMapper.load(subClassId);
    }
}
