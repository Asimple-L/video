package com.asimple.service;

import com.asimple.entity.CataLog;
import com.asimple.mapper.CataLogMapper;
import com.asimple.util.Tools;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Asimple
 * @ProjectName video
 * @description 一级分类Service实现类
 */
@Service
public class CataLogService {
    @Resource
    private CataLogMapper cataLogMapper;

    /**
     * 查找可用的列表
     *
     * @return 可用一级分类列表
     */
    @Cacheable(value = "redis_cataLogList")
    public List<CataLog> listIsUse() {
        return cataLogMapper.findByIsUse();
    }

    /**
     * 添加一级分类并返回id
     *
     * @param cataLog 一级分类对象
     * @return boolean 添加成功返回true,否则返回false
     */
    @CacheEvict(value = "redis_cataLogList", allEntries = true)
    public boolean add(CataLog cataLog) {
        if (Tools.isEmpty(cataLog.getId())) {
            cataLog.setId(Tools.UUID());
            cataLog.setIsUse(1);
            return cataLogMapper.add(cataLog) == 1;
        }
        return cataLogMapper.update(cataLog) == 1;
    }

    /**
     * 根据id查询一级分类
     *
     * @param cataLogId 一级分类id
     * @return 一级分类实体
     */
    public CataLog load(String cataLogId) {
        return cataLogMapper.load(cataLogId);
    }

    /**
     * 根据id删除分类
     *
     * @param id 分类id
     * @return 是否删除成功，true/false
     */
    @CacheEvict(value = "redis_cataLogList", allEntries = true)
    public boolean deleteById(String id) {
        return cataLogMapper.deleteById(id) == 1;
    }

}
