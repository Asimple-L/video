package com.asimple.service;

import com.asimple.entity.Loc;
import com.asimple.mapper.LocMapper;
import com.asimple.util.Tools;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Asimple
 * @ProjectName video
 * @description 地区服务实现类
 */
@Service
public class LocService {
    @Resource
    private LocMapper locMapper;

    /**
     * 查询所有在使用的地区信息
     *
     * @return 地区列表
     */
    @Cacheable(value = "redis_locList")
    public List<Loc> listIsUse() {
        return locMapper.findByIsUse();
    }

    /**
     * 添加地区信息并返回id
     *
     * @param loc 地区对象
     * @return 添加成功返回true 否则返回false
     */
    @CacheEvict(value = "redis_locList", allEntries = true)
    public boolean add(Loc loc) {
        if (Tools.isEmpty(loc.getId())) {
            loc.setId(Tools.UUID());
            loc.setIsUse(1);
            return locMapper.add(loc) == 1;
        }
        return locMapper.update(loc) == 1;
    }
}
