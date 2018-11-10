package com.asimple.service;

import com.asimple.entity.VipCode;

import java.util.List;

/**
 * @Author Asimple
 * @Description VIP管理业务层
 **/
public interface IVipCodeService {
    // 查询可以使用的VIP卡号
    List<VipCode> listIsUse();
    // 批量保存VIPCODE返回保存好的条数
    int saveAll(List<VipCode> vipCodes);
}
