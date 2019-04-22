package com.asimple.service.Impl;

import com.asimple.entity.CataLog;
import com.asimple.entity.Decade;
import com.asimple.entity.Level;
import com.asimple.entity.Loc;
import com.asimple.service.*;
import com.asimple.util.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 公共方法包括Redis和Solr的相关操作实现
 * @author: Asimple
 */
@Service("commonService")
public class CommonServiceImpl implements ICommonService {
    @Resource
    private ICataLogService cataLogService;
    @Resource
    private IDecadeService decadeService;
    @Resource
    private ILocService locService;
    @Resource
    private ILevelService levelService;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public ModelMap getCatalog(ModelMap model) {
        List<Loc> locList = (List<Loc>)redisUtil.get("redis_locList");
        if( locList==null || locList.isEmpty() ) {
            locList = locService.listIsUse();
        }
        List<Level> levelList = (List<Level>)redisUtil.get("redis_levelList");
        if( levelList == null || levelList.isEmpty() ) {
            levelList = levelService.listIsUse();
        }
        List<Decade> decadeList = (List<Decade>)redisUtil.get("redis_decadeList");
        if( decadeList==null || decadeList.isEmpty() ) {
            decadeList = decadeService.listIsUse();
        }
        List<CataLog> cataLogList = (List<CataLog>)redisUtil.get("redis_cataLogList");
        if( cataLogList==null || cataLogList.isEmpty() ) {
            cataLogList = cataLogService.listIsUse();
        }

        //读取路径下的文件返回UTF-8类型json字符串
        model.addAttribute("locList", locList);
        model.addAttribute("levelList", levelList);
        model.addAttribute("decadeList", decadeList);
        model.addAttribute("cataLogList", cataLogList);
        return model;
    }
}
