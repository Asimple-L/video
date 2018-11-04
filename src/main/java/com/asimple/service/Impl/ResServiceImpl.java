package com.asimple.service.Impl;

import com.asimple.dao.res.IResDao;
import com.asimple.entity.Res;
import com.asimple.service.IResService;
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

    @Override
    public List<Res> getListByFilmId(String film_id) {
        return resDao.getListByFilmId(film_id);
    }

    @Override
    public String add(Res res) {
        if(Tools.isEmpty(res.getId()) ) {
            res.setId(Tools.UUID());
        }
        return resDao.add(res)==1?res.getId():"0";
    }
}
