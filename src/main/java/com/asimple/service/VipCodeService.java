package com.asimple.service;

import com.asimple.entity.User;
import com.asimple.entity.VipCode;
import com.asimple.mapper.VipCodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Asimple
 * @description VIP管理业务层实现
 **/
@Service
public class VipCodeService {
    @Resource
    private VipCodeMapper vipCodeMapper;
    @Resource
    private UserService userService;

    /**
     * 查询所有可用的卡号
     * @return 可用卡号列表
     */
    public List<VipCode> listIsUse() {
        return vipCodeMapper.findByIsUse();
    }

    /**
     * 批量保存vipCode
     * @param vipCodes vipCode列表
     * @return 返回保存成功的行数
     */
    public int saveAll(List<VipCode> vipCodes) {
        int count = 0, len = vipCodes.size();
        for(int i=0; i<len; i++) {
            count += vipCodeMapper.add(vipCodes.get(i));
        }
        return count;
    }

    /**
     * @author Asimple
     * @description 通过VIP卡号查找vipCode对象
     **/
    public VipCode findByVipCode(String vip_code) {
        return vipCodeMapper.findByVipCode(vip_code);
    }

    /**
     * @author Asimple
     * @description 更新vipCode信息
     **/
    public boolean update(VipCode vipCode) {
        return vipCodeMapper.update(vipCode)==1;
    }

    /**
     * 使用vip卡号
     * @param param 须包含用户和vipCode信息
     * @return 是否使用成功 true 成功
     */
    public boolean useCode(Map<String, Object> param) {
        User user = (User) param.get("user");
        VipCode vipCode = (VipCode) param.get("vipCode");
        if( null != user ) {
            // 判断当前改用户的到期时间是否比当前时间大
            Date expireTime = user.getExpireDate();
            Date expireTimeTemp = expireTime;
            long isVip = user.getIsVip();
            // 使用Calendar类操作时间会简洁很多
            Calendar rightNow = Calendar.getInstance();
            if ( expireTime.getTime() > System.currentTimeMillis() ) {
                rightNow.setTime(expireTime);
            }
            // 添加一个月的时间
            rightNow.add(Calendar.MONTH, 1);
            expireTime = rightNow.getTime();
            // 重新设置VIP到期时间
            user.setExpireDate(expireTime);
            user.setIsVip(1);
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("user", user);
            if (userService.update(userMap)) {
                // 设置VIP卡为不可用
                vipCode.setExpire_time(new Date());
                vipCode.setIsUse(0);
                if ( update(vipCode) ) {
                    return true;
                } else {
                    user.setExpireDate(expireTimeTemp);
                    user.setIsVip(isVip);
                    userMap.put("user", user);
                    userService.update(userMap);
                    return false;
                }
            }
        }
        return false;
    }
}
