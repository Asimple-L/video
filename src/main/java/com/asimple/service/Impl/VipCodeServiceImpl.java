package com.asimple.service.Impl;

import com.asimple.dao.vipCode.IVipCodeDao;
import com.asimple.entity.VipCode;
import com.asimple.service.IVipCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Asimple
 * @Description VIP管理业务层实现
 **/
@Service("vipCodeService")
public class VipCodeServiceImpl implements IVipCodeService {
    @Resource(name = "IVipCodeDao")
    private IVipCodeDao vipCodeDao;

    /**
     * @Author Asimple
     * @Description 查询所有可用的卡号
     **/
    @Override
    public List<VipCode> listIsUse() {
        return vipCodeDao.findByIsUse();
    }

    /**
     * @Author Asimple
     * @Description 批量保存VIPCODE
     **/
    @Override
    public int saveAll(List<VipCode> vipCodes) {
        int count = 0, len = vipCodes.size();
        for(int i=0; i<len; i++) {
            count += vipCodeDao.add(vipCodes.get(i));
        }
        return count;
    }

    /**
     * @Author Asimple
     * @Description 通过VIP卡号查找VIPCODE对象
     **/
    @Override
    public VipCode findByVipCode(String vip_code) {
        return vipCodeDao.findByVipCode(vip_code);
    }

    /**
     * @Author Asimple
     * @Description 更新VIPCODE信息
     **/
    @Override
    public boolean update(VipCode vipCode) {
        return vipCodeDao.update(vipCode)==1;
    }
}
