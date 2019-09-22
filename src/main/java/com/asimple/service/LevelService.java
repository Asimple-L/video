package com.asimple.service;

import com.asimple.entity.Level;
import com.asimple.mapper.LevelMapper;
import com.asimple.util.Tools;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 等级服务实现类
 * @author: Asimple
 */
@Service
public class LevelService {
    @Resource
    private LevelMapper levelMapper;

    /**
     * @Author Asimple
     * @Description 查询所有在使用的等级
     **/
    @Cacheable( value = "redis_levelList")
    public List<Level> listIsUse() {
        return levelMapper.findByIsUse();
    }

    @CacheEvict( value = "redis_levelList")
    public void cleanRedisCache() {
        System.out.println("从redis等级缓存!");
    }

    /**
     * @Author Asimple
     * @Description 添加一个等级信息并返回id
     **/
    public String add(Level level) {
        if(Tools.isEmpty(level.getId()) ) {
            level.setId(Tools.UUID());
        }
        return levelMapper.add(level)==1?level.getId():"0";
    }
}
