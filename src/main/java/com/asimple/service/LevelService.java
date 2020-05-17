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
 * @description 等级服务实现类
 * @author Asimple
 */
@Service
public class LevelService {
    @Resource
    private LevelMapper levelMapper;

    /**
     * 查询所有在使用的等级
     * @return 等级列表
     */
    @Cacheable( value = "redis_levelList")
    public List<Level> listIsUse() {
        return levelMapper.findByIsUse();
    }

    @CacheEvict( value = "redis_levelList")
    public void cleanRedisCache() {
        System.out.println("从redis等级缓存!");
    }

    /**
     * 添加一个等级信息并返回id
     * @param level 等级对象
     * @return 添加成功返回true，否则返回 false
     */
    public boolean add(Level level) {
        if(Tools.isEmpty(level.getId()) ) {
            level.setId(Tools.UUID());
            level.setIsUse(1);
            return levelMapper.add(level)==1;
        }
        return levelMapper.update(level)==1;
    }
}
