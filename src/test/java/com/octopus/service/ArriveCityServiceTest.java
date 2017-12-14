package com.octopus.service;

import com.octopus.CrawlerApplication;
import com.octopus.pojo.ArriveCity;
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
public class ArriveCityServiceTest {
    public static final Logger log = LoggerFactory.getLogger(ArriveCityServiceTest.class);
    @Autowired
    private ArriveCityService arriveCityService;
    @Test
    public void getArriveCityByCityName() {
        ArriveCity arriveCity = arriveCityService.getArriveCityByCityName("上海");
        log.info(arriveCity.getCityCode());
    }
}