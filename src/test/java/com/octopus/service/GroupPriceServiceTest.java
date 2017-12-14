package com.octopus.service;

import com.octopus.CrawlerApplication;
import com.octopus.pojo.GroupPrice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrawlerApplication.class)
@WebAppConfiguration
public class GroupPriceServiceTest {

    @Autowired
    private GroupPriceService groupPriceService;
    @Test
    public void save() {
        GroupPrice groupPrice = new GroupPrice();
        groupPrice.setId(UUID.randomUUID().toString().replaceAll("-",""));
        groupPrice.setLineId("!23");
        groupPrice.setChildPrice("childPrice");
        groupPrice.setAdultPrice("adultPrice");
        groupPrice.setGroupDate("1996-01-01");
        groupPriceService.save(groupPrice);
    }
}