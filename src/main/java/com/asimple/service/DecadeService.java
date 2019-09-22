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
 * @Description: 年份服务实现类
 * @author: Asimple
 */
@Service
public class DecadeService {

    @Resource
    private DecadeMapper decadeMapper;

    /**
     * @Author Asimple
     * @Description 查找在使用年份
     **/
    @Cacheable( value = "redis_decadeList")
    public List<Decade> listIsUse() {
        return decadeMapper.findByIsUse();
    }

    @CacheEvict( value = "redis_decadeList")
    public void cleanRedisCache() {
        System.out.println("从redis清除目录等信息的缓存");
    }

    /**
     * @Author Asimple
     * @Description 添加年份
     **/
    public String add(Decade decade) {
        if( decade.getId() == null || "".equals(decade.getId()) ) {
            decade.setId(Tools.UUID());
        }
        return decadeMapper.add(decade)==1?decade.getId():"0";
    }


}
