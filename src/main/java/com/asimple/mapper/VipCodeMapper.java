package com.asimple.mapper;

import com.asimple.entity.VipCode;
import com.asimple.util.IBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Asimple
 * @description 会员卡mapper类
 */

@Repository
public interface VipCodeMapper extends IBaseDao<VipCode> {
    /**
     * 根据卡号查找vipCode信息
     *
     * @param vipCode 卡号
     * @return VIPCODE实体
     */
    VipCode findByVipCode(String vipCode);

    /**
     * 分页查找可用的VIP卡列表
     *
     * @param start 开始位置
     * @param count 查询的大小
     * @return VIP卡列表
     */
    List<VipCode> findByIsUseByPage(@Param("start") int start, @Param("count") int count);

    /**
     * 可用总数
     *
     * @return 可用总数
     */
    int getTotalIsUse();
}
