package com.asimple.mapper;

import com.asimple.entity.VipCode;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Asimple
 * @description 会员卡mapper类
 */

@Repository
public interface VipCodeMapper extends IBaseDao<VipCode> {
    /**
     * 根据卡号查找vipCode信息
     * @param vipCode 卡号
     * @return VIPCODE实体
     */
    VipCode findByVipCode(String vipCode);
}
