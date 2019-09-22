package com.asimple.service;

import com.asimple.entity.VipCode;
import com.asimple.mapper.VipCodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Asimple
 * @Description VIP管理业务层实现
 **/
@Service
public class VipCodeService {
    @Resource
    private VipCodeMapper vipCodeMapper;

    /**
     * @Author Asimple
     * @Description 查询所有可用的卡号
     **/
    public List<VipCode> listIsUse() {
        return vipCodeMapper.findByIsUse();
    }

    /**
     * @Author Asimple
     * @Description 批量保存VIPCODE
     **/
    public int saveAll(List<VipCode> vipCodes) {
        int count = 0, len = vipCodes.size();
        for(int i=0; i<len; i++) {
            count += vipCodeMapper.add(vipCodes.get(i));
        }
        return count;
    }

    /**
     * @Author Asimple
     * @Description 通过VIP卡号查找VIPCODE对象
     **/
    public VipCode findByVipCode(String vip_code) {
        return vipCodeMapper.findByVipCode(vip_code);
    }

    /**
     * @Author Asimple
     * @Description 更新VIPCODE信息
     **/
    public boolean update(VipCode vipCode) {
        return vipCodeMapper.update(vipCode)==1;
    }
}
