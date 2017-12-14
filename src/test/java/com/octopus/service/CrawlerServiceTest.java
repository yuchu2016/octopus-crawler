package com.octopus.service;

import com.octopus.CrawlerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrawlerApplication.class)
@WebAppConfiguration
public class CrawlerServiceTest {

    @Autowired
    public CrawlerService crawlerService;

    @Test
    public void spider() throws Exception{
        crawlerService.execute("苏州","厦门");
    }
    @Test
    public void spider2() throws Exception{
        crawlerService.execute("无锡");

    }
}