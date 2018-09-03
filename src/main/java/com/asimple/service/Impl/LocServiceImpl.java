package com.asimple.service.Impl;

import com.asimple.dao.loc.ILocDao;
import com.asimple.entity.Loc;
import com.asimple.service.ILocService;
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

    @Override
    public List<Loc> listIsUse() {
        return locDao.findByIsUse();
    }
}
