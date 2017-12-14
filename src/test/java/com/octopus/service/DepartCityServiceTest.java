package com.octopus.service;

import com.octopus.CrawlerApplication;
import com.octopus.pojo.DepartCity;
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
public class DepartCityServiceTest {
    public static final Logger logger = LoggerFactory.getLogger(LineServiceTest.class);
    @Autowired
    private DepartCityService departCityService;
    @Test
    public void getDepartCityByCityName() {
        DepartCity departCity = departCityService.getDepartCityByCityName("无锡");
        logger.info(departCity.getCityCode());
        Assert.assertNotNull(departCity);
    }
}