package com.asimple.task;

import com.asimple.entity.*;
import com.asimple.service.*;
import com.asimple.util.LogUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Asimple
 * @ProjectName video
 * @description 排行榜每日更新任务
 */
@Component
@Configuration
@EnableScheduling
public class RankTask {

    @Resource
    private CataLogService cataLogService;
    @Resource
    private FilmService filmService;

    /**
     * @author Asimple
     * @description 每日零点推荐榜更新
     **/
    @Scheduled(cron = "0 0 0 * * ?")
    public void commendRank() {
        long startTime = System.currentTimeMillis();
        LogUtil.info("start update commendRank!");

        // 查询用户菜单列表
        List<CataLog> logList = cataLogService.listIsUse();
        LogUtil.info("用户菜单列表：" + logList.size());

        // 查询推荐电影
        List<Object> list = new ArrayList<>();
        for (CataLog aLogList : logList) {
            List<Film> films = filmService.listByCataLogId(aLogList.getId(), 12);
            if (films.size() != 0) {
                list.add(films);
            }
        }
        LogUtil.info("推荐电影列表：" + list.size());

        // 电影排行榜
        List<Object> list1 = new ArrayList<>();
        for (CataLog aLogList : logList) {
            List<Film> films = filmService.listByEvaluation(aLogList.getId(), 13);
            if (films.size() != 0) {
                list1.add(films);
            }
        }
        LogUtil.info("电影排行榜信息：" + list1.size());

        long endTime = System.currentTimeMillis();
        LogUtil.info("commendRank update end! run time = " + (endTime - startTime) / 1000 + "s");
    }

}
