package com.asimple.dao.vipCode;

import com.asimple.entity.VipCode;
import com.asimple.util.IBaseDao;

public interface IVipCodeDao extends IBaseDao<VipCode> {
    VipCode findByVipCode(String vip_code);
}
