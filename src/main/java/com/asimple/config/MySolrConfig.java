package com.asimple.config;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * @ProjectName video
 * @Description Solr 配置
 * @Author Asimple
 * @date 2019/8/31 22:24
 */
@Configuration
public class MySolrConfig {

    @Autowired
    SolrClient solrClient;

    @Bean
    public SolrTemplate getSolrTemplate(){
        return  new SolrTemplate(solrClient);
    }
}
