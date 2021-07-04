package com.asimple.task;

import com.asimple.entity.Film;
import com.asimple.service.FilmService;
import com.asimple.util.LogUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Asimple
 * @ProjectName video
 * @description solr数据导入
 */
@Component
@Configuration
@EnableScheduling
public class SolrTask {

    @Resource
    private FilmService filmService;

    @Resource
    private SolrTemplate solrTemplate;

    @Scheduled(cron = "0 10 0 * * ?")
    public void pushToSolr() {
        LogUtil.info(SolrTask.class, "pushSolr start!");
        Long startTime = System.currentTimeMillis();
        List<Film> list = filmService.findAll();
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
        Long endTime = System.currentTimeMillis();
        LogUtil.info(SolrTask.class, "pushSolr end, And run time = " + (endTime - startTime) / 1000 + "s");
    }

}
