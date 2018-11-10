package com.asimple.service.Impl;

import com.asimple.dao.res.IResDao;
import com.asimple.entity.Res;
import com.asimple.service.IResService;
import com.asimple.util.DateUtil;
import com.asimple.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 资源服务层实现类
 * @author: Asimple
 */
@Service("resService")
public class ResServiceImpl implements IResService {
    @Resource(name = "IResDao")
    private IResDao resDao;

    /**
     * @Author Asimple
     * @Description 根据film_id查找所有资源
     **/
    @Override
    public List<Res> getListByFilmId(String film_id) {
        return resDao.getListByFilmId(film_id);
    }

    /**
     * @Author Asimple
     * @Description 添加资源
     **/
    @Override
    public String add(Res res) {
        if(Tools.isEmpty(res.getId()) ) {
            res.setId(Tools.UUID());
        }
        return resDao.add(res)==1?res.getId():"0";
    }

    /**
     * @Author Asimple
     * @Description 删除资源
     **/
    @Override
    public boolean delete(String res_id) {
        return resDao.deleteById(res_id)==1;
    }

    /**
     * @Author Asimple
     * @Description 更改资源在离线状态
     **/
    @Override
    public boolean updateIsUse(String res_id) {
        Res res = resDao.load(res_id);
        res.setIsUse(1-res.getIsUse());
        res.setUpdateTime(DateUtil.getTime());
        return resDao.update(res) == 1;
    }
}
