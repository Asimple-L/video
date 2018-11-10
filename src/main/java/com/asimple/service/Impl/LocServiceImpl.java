package com.asimple.service.Impl;

import com.asimple.dao.loc.ILocDao;
import com.asimple.entity.Loc;
import com.asimple.service.ILocService;
import com.asimple.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 地区服务实现类
 * @author: Asimple
 */
@Service("locService")
public class LocServiceImpl implements ILocService {
    @Resource( name = "ILocDao")
    private ILocDao locDao;

    /**
     * @Author Asimple
     * @Description 查询所有在使用的地区信息
     **/
    @Override
    public List<Loc> listIsUse() {
        return locDao.findByIsUse();
    }

    /**
     * @Author Asimple
     * @Description 添加地区信息并返回id
     **/
    @Override
    public String add(Loc loc) {
        if(Tools.isEmpty(loc.getId()) ) {
            loc.setId(Tools.UUID());
        }
        return locDao.add(loc)==1?loc.getId():"0";
    }
}
