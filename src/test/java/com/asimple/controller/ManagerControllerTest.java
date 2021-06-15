package com.asimple.controller;

import com.asimple.App;
import com.asimple.util.LogUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName video
 * @description 后台管理测试
 * @author Asimple
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManagerControllerTest {

    @Resource
    private ManagerController manager;

    @Test
    public void testSome() {
        Object object = manager.createVipCode("1");
        System.out.println(object);
    }

    @Test
    public void test_deleteCatalog() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("type", "catalog");
        request.setParameter("id", "B961C8D1C3714676ABE6F70FC088982A");
        Object object = manager.deleteCatalog(request);
        System.out.println(object);
        Assert.assertNotNull(object);
    }

}
