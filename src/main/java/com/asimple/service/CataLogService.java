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
     * @author Asimple
     * @description 查找可用的列表
     **/
    @Cacheable(value = "redis_cataLogList")
    public List<CataLog> listIsUse() {
        return cataLogMapper.findByIsUse();
    }

    @CacheEvict( value = "redis_cataLogList")
    public void cleanRedisCache() {
        System.out.println("从redis清除一级分类实体类缓存!");
    }

    /**
     * @author Asimple
     * @description 添加一级分类并返回id
     **/
    public String add(CataLog cataLog) {
        if(Tools.isEmpty(cataLog.getId()) ) {
            cataLog.setId(Tools.UUID());
        }
        return cataLogMapper.add(cataLog)==1?cataLog.getId():"0";
    }

    /**
     * @author Asimple
     * @description 根据id查询一级分类
     **/
    public CataLog load(String cataLog_id) {
        return cataLogMapper.load(cataLog_id);
    }

}
