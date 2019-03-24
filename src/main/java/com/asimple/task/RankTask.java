package com.asimple.task;

import com.asimple.entity.CataLog;
import com.asimple.entity.Film;
import com.asimple.service.ICataLogService;
import com.asimple.service.IFilmService;
import com.asimple.util.LogUtil;
import com.asimple.util.RedisUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 排行榜每日更新任务
 * @author: Asimple
 */
@Component
public class RankTask {

    @Resource( name = "cataLogService")
    private ICataLogService cataLogService;

    @Resource( name = "filmService")
    private IFilmService filmService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * @Author Asimple
     * @Description 每日零点推荐榜更新
     **/
    @Scheduled(cron = "0 0/30 * * * ?")
    public void commendRank() {
        long startTime = System.currentTimeMillis();
        LogUtil.info("start update commendRank!");

        // 查询用户菜单列表
        List<CataLog> logList = cataLogService.listIsUse();
        redisUtil.set("index_cataLogList", logList);

        // 查询推荐电影
        List<Object> list =  new ArrayList<>();
        for (CataLog aLogList : logList) {
            List<Film> films = filmService.listByCataLog_id(aLogList.getId(), 12);
            if (films.size() != 0) {
                list.add(films);
            }
        }
        redisUtil.set("index_filmTuijian", list);

        // 电影排行榜
        List<Object> list1 = new ArrayList<>();
        for (CataLog aLogList : logList) {
            List<Film> films = filmService.listByEvaluation(aLogList.getId(), 13);
            if (films.size() != 0) {
                list1.add(films);
            }
        }
        redisUtil.set("index_filmPaiHang", list1);

        long endTime = System.currentTimeMillis();
        LogUtil.info("commendRank update end! run time = " + (endTime - startTime)/1000 + "s");
    }

}
