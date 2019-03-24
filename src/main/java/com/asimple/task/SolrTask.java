package com.asimple.task;

import com.asimple.entity.Film;
import com.asimple.service.IFilmService;
import com.asimple.util.LogUtil;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: solr数据导入
 * @author: Asimple
 */
@Component
public class SolrTask {

    @Resource( name = "filmService")
    private IFilmService filmService;

    @Resource
    private SolrTemplate solrTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void pushToSolr() {
        LogUtil.info(SolrTask.class, "pushSolr start!");
        Long startTime = System.currentTimeMillis();
        List<Film> list = filmService.findAll();
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
        Long endTime = System.currentTimeMillis();
        LogUtil.info(SolrTask.class, "pushSolr end, And run time = " + (endTime - startTime)/1000 + "s");
    }

}
