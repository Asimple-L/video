package com.asimple.service;

import com.asimple.entity.CataLog;
import com.asimple.entity.Decade;
import com.asimple.entity.Level;
import com.asimple.entity.Loc;
import com.asimple.util.LogUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.List;

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
