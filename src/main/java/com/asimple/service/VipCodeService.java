package com.asimple.service;

import com.asimple.entity.User;
import com.asimple.entity.VipCode;
import com.asimple.mapper.VipCodeMapper;
import com.asimple.util.PageBean;
import com.asimple.util.Tools;
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
     * 可用卡号列表
     *
     * @param page     当前页
     * @param pageSize 一页大小
     * @return vipCode列表
     */
    public PageBean<VipCode> listIsUse(int page, int pageSize) {
        PageBean<VipCode> pageBean = new PageBean<>();
        pageBean.setPc(page);
        pageBean.setPs(pageSize);
        pageBean.setTr(vipCodeMapper.getTotalIsUse());
        List<VipCode> list = vipCodeMapper.findByIsUseByPage((page - 1) * pageSize, pageSize);
        pageBean.setBeanList(list);
        return pageBean;
    }

    /**
     * 批量保存vipCode
     *
     * @param vipCodes vipCode列表
     * @return 返回保存成功的行数
     */
    private int saveAll(List<VipCode> vipCodes) {
        int count = 0;
        for (VipCode vipCode : vipCodes) {
            count += vipCodeMapper.add(vipCode);
        }
        return count;
    }

    /**
     * 通过VIP卡号查找vipCode对象
     *
     * @param vipCode VIP卡号
     * @return vipCode对象
     */
    public VipCode findByVipCode(String vipCode) {
        return vipCodeMapper.findByVipCode(vipCode);
    }

    /**
     * 更新vipCode信息
     *
     * @param vipCode vipCode对象
     * @return 更新成功返回true
     */
    public boolean update(VipCode vipCode) {
        return vipCodeMapper.update(vipCode) == 1;
    }

    /**
     * 使用vip卡号
     *
     * @param param 须包含用户和vipCode信息
     * @return 是否使用成功 true 成功
     */
    public boolean useCode(Map<String, Object> param) {
        User user = (User) param.get("user");
        VipCode vipCode = (VipCode) param.get("vipCode");
        if (null != user) {
            // 判断当前改用户的到期时间是否比当前时间大
            Date expireTime = user.getExpireDate();
            Date expireTimeTemp = expireTime;
            long isVip = user.getIsVip();
            // 使用Calendar类操作时间会简洁很多
            Calendar rightNow = Calendar.getInstance();
            if (expireTime.getTime() > System.currentTimeMillis()) {
                rightNow.setTime(expireTime);
            }
            // 添加一个月的时间
            rightNow.add(Calendar.MONTH, 1);
            expireTime = rightNow.getTime();
            // 重新设置VIP到期时间
            user.setExpireDate(expireTime);
            user.setIsVip(1);
            Map<String, Object> userMap = new HashMap<>(4);
            userMap.put("user", user);
            if (userService.update(userMap)) {
                // 设置VIP卡为不可用
                vipCode.setExpire_time(new Date());
                vipCode.setIsUse(0);
                if (update(vipCode)) {
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

    /**
     * 批量添加vipCode
     *
     * @param n 添加数目
     * @return 添加成功的vipCode对象列表
     */
    public List<VipCode> addVipCodes(int n) {
        VipCode vipCode;
        List<VipCode> vipCodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            vipCode = new VipCode();
            vipCode.setId(Tools.UUID());
            vipCode.setCreate_time(new Date());
            vipCode.setExpire_time(new Date());
            vipCode.setCode(Tools.UUID());
            vipCode.setIsUse(1);
            vipCodes.add(vipCode);
        }
        int num = saveAll(vipCodes);
        if (num == n) {
            return vipCodes;
        }
        return new ArrayList<>();
    }
}
