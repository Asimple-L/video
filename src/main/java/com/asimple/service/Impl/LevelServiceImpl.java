package com.asimple.service.Impl;

import com.asimple.dao.level.ILevelDao;
import com.asimple.entity.Level;
import com.asimple.service.ILevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 等级服务实现类
 * @author: Asimple
 */
@Service("levelService")
public class LevelServiceImpl implements ILevelService {
    @Resource(name = "ILevelDao")
    private ILevelDao levelDao;

    @Override
    public List<Level> listIsUse() {
        return levelDao.findByIsUse();
    }
}
