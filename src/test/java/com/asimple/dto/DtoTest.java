package com.asimple.dto;

import com.asimple.App;
import com.asimple.entity.Bullet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description DTO测试类
 * @Author Asimple
 * @date 2020/5/14 22:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DtoTest {

    @Test
    public void test_bullet() {
        Bullet bullet = new Bullet();
        bullet.setFilmId("123");
        System.out.println(bullet);
    }

}
