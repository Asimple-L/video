package com.asimple.service.Impl;

import com.asimple.dao.level.ILevelDao;
import com.asimple.entity.Level;
import com.asimple.service.ILevelService;
import com.asimple.util.Tools;
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

    /**
     * @Author Asimple
     * @Description 查询所有在使用的等级
     **/
    @Override
    public List<Level> listIsUse() {
        return levelDao.findByIsUse();
    }

    /**
     * @Author Asimple
     * @Description 添加一个等级信息并返回id
     **/
    @Override
    public String add(Level level) {
        if(Tools.isEmpty(level.getId()) ) {
            level.setId(Tools.UUID());
        }
        return levelDao.add(level)==1?level.getId():"0";
    }
}
