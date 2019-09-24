package com.asimple.service;

import com.asimple.entity.Film;
import com.asimple.entity.Res;
import com.asimple.mapper.ResMapper;
import com.asimple.util.DateUtil;
import com.asimple.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @description 资源服务层实现类
 * @author Asimple
 */
@Service
public class ResService {
    @Resource
    private FilmService filmService;
    @Resource
    private ResMapper resMapper;

    /**
     * @author Asimple
     * @description 根据film_id查找所有资源
     **/
    public List<Res> getListByFilmId(String film_id) {
        return resMapper.getListByFilmId(film_id);
    }

    /**
     * @author Asimple
     * @description 添加资源
     **/
    public String add(Res res) {
        if(Tools.isEmpty(res.getId()) ) {
            res.setId(Tools.UUID());
        }
        return resMapper.add(res)==1?res.getId():"0";
    }

    /**
     * @author Asimple
     * @description 上传资源，如果是
     **/
    public String addRes(Res res, String film_id) {
        // 初始化
        res.setIsUse(1);
        Film film = filmService.load(film_id);
        res.setFilm(film);
        res.setUpdateTime(DateUtil.getTime());

        // 多资源上传
        String id = "";
        if ( res.getName().contains("@@") ) {
            //  xxxx@@集##集数开始##集数结束##分割符号
            String resName[] = res.getName().trim().split("##");
            // 视频名称
            String name = resName[0];
            // 开始集数与结束集数
            int begin = Integer.parseInt(resName[1]);
            int end = Integer.parseInt(resName[2]);
            // 链接分割标志
            String flag = "";
            if( resName.length > 3 ) {
                flag = resName[3];
                String res_links[] = res.getLink().replaceAll("\\n","").split(flag);
                int cz = begin - 1;
                for(int i=begin; i<=end; i++) {
                    res.setName(name.replace("@@", ""));
                    res.setEpisodes(i);
                    if( "Flh".equals(res.getLinkType()) ) flag = "";
                    res.setLink(flag+res_links[i-cz]);
                    id = add(res);
                }
            }
        } else {
            id = add(res);
        }
        film.setUpdateTime(DateUtil.getTime());
        filmService.update(film);
        return id;
    }

    /**
     * @author Asimple
     * @description 删除资源
     **/
    public boolean delete(String res_id) {
        return resMapper.deleteById(res_id)==1;
    }

    /**
     * @author Asimple
     * @description 更改资源在离线状态
     **/
    public boolean updateIsUse(String res_id) {
        Res res = resMapper.load(res_id);
        res.setIsUse(1-res.getIsUse());
        res.setUpdateTime(DateUtil.getTime());
        return resMapper.update(res) == 1;
    }
}
