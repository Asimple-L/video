package com.asimple.service;

import com.asimple.entity.Decade;
import com.asimple.mapper.DecadeMapper;
import com.asimple.util.Tools;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @description 年份服务实现类
 * @author Asimple
 */
@Service
public class DecadeService {

    @Resource
    private DecadeMapper decadeMapper;

    /**
     * 查找在使用年份
     * @return 年份列表
     */
    @Cacheable( value = "redis_decadeList")
    public List<Decade> listIsUse() {
        return decadeMapper.findByIsUse();
    }

    @CacheEvict( value = "redis_decadeList")
    public void cleanRedisCache() {
        System.out.println("从redis清除目录等信息的缓存");
    }

    /**
     * 添加年份
     * @param decade 年份对象
     * @return 添加成功返回true，否则返回false
     */
    public boolean add(Decade decade) {
        if( decade.getId() == null || "".equals(decade.getId()) ) {
            decade.setId(Tools.UUID());
            decade.setIsUse(1);
            return decadeMapper.add(decade)==1;
        }
        return decadeMapper.update(decade)==1;
    }


}
