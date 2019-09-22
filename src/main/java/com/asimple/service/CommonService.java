package com.asimple.service;

import com.asimple.controller.Manager;
import com.asimple.controller.Profile;
import com.asimple.entity.*;
import com.asimple.util.FileOperate;
import com.asimple.util.LogUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName video
 * @Description: 公共方法包括Redis和Solr的相关操作实现
 * @author: Asimple
 */
@Service
public class CommonService {
    @Resource
    private CataLogService cataLogService;
    @Resource
    private DecadeService decadeService;
    @Resource
    private LocService locService;
    @Resource
    private LevelService levelService;
    @Resource
    private FilmService filmService;
    @Resource
    private TypeService typeService;

    public boolean updateFilmInfo(Map params) {
        String key = (String) params.get("key");
        String val = (String) params.get("val");
        String filePath = (String) params.get("filePath");
        Film film = (Film) params.get("film");
        LogUtil.info(CommonService.class, "key = " + key + "   val = " + val);
        switch ( key ) {
            case "name":
                film.setName(val);
                break;
            case "image":
                FileOperate.delFile(filePath);
                film.setImage(val);
                break;
            case "onDecade":
                film.setOnDecade(val);
                break;
            case "status":
                film.setStatus(val);
                break;
            case "resolution":
                film.setResolution(val);
                break;
            case "typeName":
                film.setTypeName(val);
                break;
            case "type_id":
                film.setType_id(val);
                Type type = typeService.load(val);
                LogUtil.info(CommonService.class, "type = " + type);
                film.setSubClass_id(type.getSubClass().getId());
                film.setSubClassName(type.getSubClass().getName());
                film.setCataLog_id(type.getSubClass().getCataLog().getId());
                film.setCataLogName(type.getSubClass().getCataLog().getName());
                break;
            case "actor":
                film.setActor(val);
                break;
            case "loc_id":
                film.setLoc_id(val);
                break;
            case "plot":
                film.setPlot(val);
                break;
            case "isVip":
                film.setIsVip(Integer.valueOf(val));
                break;
        }
        LogUtil.info(CommonService.class, "film = " + film);
        return filmService.update(film);
    }


    public ModelMap getCatalog(ModelMap model) {
        List<Loc> locList =  locService.listIsUse();
        List<Level> levelList = levelService.listIsUse();
        List<Decade> decadeList = decadeService.listIsUse();
        List<CataLog> cataLogList = cataLogService.listIsUse();

        //读取路径下的文件返回UTF-8类型json字符串
        model.addAttribute("locList", locList);
        model.addAttribute("levelList", levelList);
        model.addAttribute("decadeList", decadeList);
        model.addAttribute("cataLogList", cataLogList);
        return model;
    }

    public void cleanRedisCache() {
        cataLogService.cleanRedisCache();
        locService.cleanLocList();
        decadeService.cleanRedisCache();
        levelService.cleanRedisCache();
        this.cleanIndexCache();
    }

    public void cleanIndexCache() {
        this.cleanIndexCachePaiHang();
        this.cleanIndexCacheTuiJian();
        cataLogService.cleanRedisCache();
    }

    @CacheEvict(value = "index_filmTuijian")
    public void cleanIndexCacheTuiJian() {
        LogUtil.info("从redis清除首页推荐缓存!");
    }

    @CacheEvict(value = "index_filmPaiHang")
    public void cleanIndexCachePaiHang() {
        LogUtil.info("从redis清除首页排行缓存!");
    }

}
