package com.asimple.service.Impl;

import com.asimple.dao.cataLog.ICataLogDao;
import com.asimple.entity.CataLog;
import com.asimple.service.ICataLogService;
import com.asimple.util.Tools;
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

    /**
     * @Author Asimple
     * @Description 查找可用的列表
     **/
    @Override
    public List<CataLog> listIsUse() {
        return cataLogDao.findByIsUse();
    }

    /**
     * @Author Asimple
     * @Description 添加一级分类并返回id
     **/
    @Override
    public String add(CataLog cataLog) {
        if(Tools.isEmpty(cataLog.getId()) ) {
            cataLog.setId(Tools.UUID());
        }
        return cataLogDao.add(cataLog)==1?cataLog.getId():"0";
    }

    /**
     * @Author Asimple
     * @Description 根据id查询一级分类
     **/
    @Override
    public CataLog load(String cataLog_id) {
        return cataLogDao.load(cataLog_id);
    }

}
