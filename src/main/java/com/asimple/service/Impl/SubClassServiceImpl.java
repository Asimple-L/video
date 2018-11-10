package com.asimple.service.Impl;

import com.asimple.dao.subClass.ISubClassDao;
import com.asimple.entity.SubClass;
import com.asimple.service.ISubClassService;
import com.asimple.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 子类服务实现类
 * @author: Asimple
 */
@Service("subClassService")
public class SubClassServiceImpl implements ISubClassService {

    @Resource(name = "ISubClassDao")
    private ISubClassDao subClassDao;

    /**
     * @Author Asimple
     * @Description 查询所有可使用的二级分类
     **/
    @Override
    public List<SubClass> listIsUse(String id) {
        return subClassDao.listIsUse(id);
    }

    /**
     * @Author Asimple
     * @Description 添加二级分类
     **/
    @Override
    public String add(SubClass subClass) {
        if(Tools.isEmpty(subClass.getId()) ) {
            subClass.setId(Tools.UUID());
        }
        return subClassDao.add(subClass)==1?subClass.getId():"0";
    }

    /**
     * @Author Asimple
     * @Description 根据id加载二级分类
     **/
    @Override
    public SubClass load(String subClass_id) {
        return subClassDao.load(subClass_id);
    }
}
