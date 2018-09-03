package com.asimple.service.Impl;

import com.asimple.dao.cataLog.ICataLogDao;
import com.asimple.entity.CataLog;
import com.asimple.service.ICataLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 一级分类Service实现类
 * @author: Asimple
 */
@Service("cataLogService")
public class CataLogServiceImpl implements ICataLogService {
    @Resource(name = "ICataLogDao")
    private ICataLogDao cataLogDao;

    public void setCataLogDao(ICataLogDao cataLogDao) {
        this.cataLogDao = cataLogDao;
    }

    /**
     * @Author Asimple
     * @Description 查找可用的列表
     **/
    @Override
    public List<CataLog> listIsUse() {
        return cataLogDao.findByIsUse();
    }
}
