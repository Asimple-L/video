package com.asimple.service;

import com.asimple.entity.Film;
import com.asimple.entity.Res;
import com.asimple.mapper.ResMapper;
import com.asimple.util.DateUtil;
import com.asimple.util.LogUtil;
import com.asimple.util.Tools;
import com.asimple.util.VideoKeyNameUtil;
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
     * 根据film_id查找所有资源
     * @param filmId 影片id
     * @return 影片所对应的所有资源
     */
    public List<Res> getListByFilmId(String filmId) {
        return resMapper.getListByFilmId(filmId);
    }

    /**
     * 添加资源
     * @param res 资源实体
     * @return 添加成功返回id，否则返回0
     */
    public String add(Res res) {
        if(Tools.isEmpty(res.getId()) ) {
            res.setId(Tools.UUID());
        }
        return resMapper.add(res)==1?res.getId():"0";
    }

    /**
     * 上传资源
     * @param res 资源信息
     * @param filmId 对应的影片id
     * @return 添加资源成功返回资源id
     */
    public String addRes(Res res, String filmId) {
        // 初始化
        res.setIsUse(1);
        Film film = filmService.load(filmId);
        res.setFilm(film);
        res.setUpdateTime(DateUtil.getTime());

        // 多资源上传
        String id = "";
        if ( res.getName().contains(VideoKeyNameUtil.RES_NAME_SPLIT) ) {
            //  xxxx@@集##集数开始##集数结束##分割符号
            String[] resName = res.getName().trim().split(VideoKeyNameUtil.EPISODES_NAME_SPLIT);
            // 视频名称
            String name = resName[0];
            // 开始集数与结束集数
            int begin = Integer.parseInt(resName[1]);
            int end = Integer.parseInt(resName[2]);
            // 链接分割标志
            String flag = "";
            if( resName.length > 3 ) {
                flag = resName[3];
                String resLinks[] = res.getLink().replaceAll("\\n","").split(flag);
                int cz = begin - 1;
                for(int i=begin; i<=end; i++) {
                    res.setName(name.replace(VideoKeyNameUtil.RES_NAME_SPLIT, ""));
                    res.setEpisodes(i);
                    if( "Flh".equals(res.getLinkType()) ) {
                        flag = "";
                    }
                    res.setLink(flag+resLinks[i-cz]);
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
     * 删除资源
     * @param resId 资源id
     * @return 是否删除成功
     */
    public boolean delete(String resId) {
        return resMapper.deleteById(resId)==1;
    }

    /**
     * 更改资源在离线状态
     * @param resId 资源id
     * @return 更新成功返回true
     */
    public boolean updateIsUse(String resId) {
        LogUtil.info(ResService.class, "resId = " + resId);
        Res res = resMapper.load(resId);
        res.setIsUse(1-res.getIsUse());
        res.setUpdateTime(DateUtil.getTime());
        return resMapper.update(res) == 1;
    }
}
