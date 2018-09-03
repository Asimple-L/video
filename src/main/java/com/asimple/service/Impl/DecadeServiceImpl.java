package com.asimple.service.Impl;

import com.asimple.dao.decade.IDecadeDao;
import com.asimple.entity.Decade;
import com.asimple.service.IDecadeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 年份服务实现类
 * @author: Asimple
 */
@Service("decadeService")
public class DecadeServiceImpl implements IDecadeService {

    @Resource( name = "IDecadeDao")
    private IDecadeDao decadeDao;

    // 查找可用列表
    @Override
    public List<Decade> listIsUse() {
        return decadeDao.findByIsUse();
    }
}
