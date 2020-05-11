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
 * @ProjectName video
 * @description 一级分类Service实现类
 * @author Asimple
 */
@Service
public class CataLogService {
    @Resource
    private CataLogMapper cataLogMapper;

    /**
     * 查找可用的列表
     * @return 可用一级分类列表
     */
    @Cacheable(value = "redis_cataLogList")
    public List<CataLog> listIsUse() {
        return cataLogMapper.findByIsUse();
    }

    @CacheEvict( value = "redis_cataLogList")
    public void cleanRedisCache() {
        System.out.println("从redis清除一级分类实体类缓存!");
    }

    /**
     * 添加一级分类并返回id
     * @param cataLog 一级分类对象
     * @return boolean 添加成功返回true,否则返回false
     */
    public boolean add(CataLog cataLog) {
        if(Tools.isEmpty(cataLog.getId()) ) {
            cataLog.setId(Tools.UUID());
            cataLog.setIsUse(1);
            return cataLogMapper.add(cataLog)==1;
        }
        return cataLogMapper.update(cataLog)==1;
    }

    /**
     * 根据id查询一级分类
     * @param cataLogId 一级分类id
     * @return 一级分类实体
     */
    public CataLog load(String cataLogId) {
        return cataLogMapper.load(cataLogId);
    }

}
