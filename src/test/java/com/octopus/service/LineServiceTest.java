package com.octopus.service;

import com.octopus.CrawlerApplication;
import com.octopus.pojo.Line;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrawlerApplication.class)
@WebAppConfiguration
public class LineServiceTest {

    public static final Logger logger = LoggerFactory.getLogger(LineServiceTest.class);
    @Autowired
    private LineService lineService;
    @Test
    public void getLineById() {
        Line line = lineService.getLineById("cfb2b4809e194c4aabdc11f6a750782d");
        Assert.assertNotNull(line);

    }
    @Test
    public void deleteByDepartCity(){
        lineService.deleteByDepartCityAndDescity("大连","北京");
    }
}