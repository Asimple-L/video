package com.asimple.mapper;

import com.asimple.entity.VipCode;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface VipCodeMapper extends IBaseDao<VipCode> {
    VipCode findByVipCode(String vip_code);
}
