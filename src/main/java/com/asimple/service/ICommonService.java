package com.asimple.service;

import org.springframework.ui.ModelMap;

/**
 * @ProjectName video
 * @Description: 公共方法包括Redis和Solr的相关操作
 * @author: Asimple
 */
public interface ICommonService {
    ModelMap getCatalog(ModelMap map);
}
