package com.asimple.service.Impl;

import com.asimple.dao.decade.IDecadeDao;
import com.asimple.entity.Decade;
import com.asimple.service.IDecadeService;
import com.asimple.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @ProjectName video
 * @Description: 年份服务实现类
 * @author: Asimple
 */
@Service("decadeService")
public class DecadeServiceImpl implements IDecadeService {

    @Resource( name = "IDecadeDao")
    private IDecadeDao decadeDao;

    /**
     * @Author Asimple
     * @Description 查找在使用年份
     **/
    @Override
    public List<Decade> listIsUse() {
        return decadeDao.findByIsUse();
    }

    /**
     * @Author Asimple
     * @Description 添加年份
     **/
    @Override
    public String add(Decade decade) {
        if( decade.getId() == null || "".equals(decade.getId()) ) {
            decade.setId(Tools.UUID());
        }
        return decadeDao.add(decade)==1?decade.getId():"0";
    }


}
