package com.asimple.service;

import com.asimple.entity.Decade;
import com.asimple.mapper.DecadeMapper;
import com.asimple.util.Tools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Asimple
 * @ProjectName video
 * @description 年份服务实现类
 */
@Service
public class DecadeService {

    @Resource
    private DecadeMapper decadeMapper;

    /**
     * 查找在使用年份
     *
     * @return 年份列表
     */
    @Cacheable(value = "redis_decadeList")
    public List<Decade> listIsUse() {
        return decadeMapper.findByIsUse();
    }

    /**
     * 添加年份
     *
     * @param decade 年份对象
     * @return 添加成功返回true，否则返回false
     */
    @CacheEvict(value = "redis_decadeList", allEntries = true)
    public boolean add(Decade decade) {
        if (StringUtils.isBlank(decade.getId())) {
            decade.setId(Tools.UUID());
            decade.setIsUse(1);
            return decadeMapper.add(decade) == 1;
        }
        return decadeMapper.update(decade) == 1;
    }


}
