package com.asimple.controller;

import com.asimple.util.LogUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ProjectName video
 * @Description: 后台管理测试
 * @author: Asimple
 */
@ContextConfiguration(locations = {
        "classpath:springmvc.xml",
        "classpath:spring-service.xml",
        "classpath:spring-mybatis.xml",
        "classpath:spring-db.xml",
        "classpath:spring-redis.xml",
        "classpath:spring-tx.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ManagerControllerTest {

    @Test
    public void testSome() {
        LogUtil.info("test start!");
    }

}
