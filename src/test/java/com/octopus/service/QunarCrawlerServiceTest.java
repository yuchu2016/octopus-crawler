package com.octopus.service;

import com.octopus.CrawlerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrawlerApplication.class)
@WebAppConfiguration
public class QunarCrawlerServiceTest {
    public static final Logger log = LoggerFactory.getLogger(QunarCrawlerServiceTest.class);
    @Autowired
    private QunarLineService qunarLineService;
    @Autowired
    private QunarPriceService qunarPriceService;
    @Test
    public void spiderWithThreadPool() throws Exception{
        int pageIndex = 0; //默认第一页
        String cn = ""; //出发城市 扬州,南京
        String d = "上海"; //站点 如果没有具体的出发城市城市的话。则出发城市不需要填写
        String q = "北京"; //查询条件(XX跟团游)
        String userResident = "上海";
//        QunarCrawlerService qunarCrawlerService = new QunarCrawlerService();
//        qunarCrawlerService.setLineService(lineService);
//        qunarCrawlerService.setPageIndex(pageIndex);
//        qunarCrawlerService.setQ(q);
//        qunarCrawlerService.setD(d);
//        qunarCrawlerService.run();
        QunarCrawlerService main =  new QunarCrawlerService(qunarLineService, qunarPriceService,pageIndex,d,q,userResident);
        int rowNum = main.spider(pageIndex,d,q,userResident);
        pageIndex++;
        //main.interrupt();
        if (rowNum > 60) {
            //进行计算分页
            int count = rowNum % 60 == 0 ? rowNum / 60 : rowNum / 60 + 1;

            if (count > 1) {
                //第一页已经计算。直接从第二页开始
                List<QunarCrawlerService> qunarCrawlerServiceList = new ArrayList<>();
                for (; pageIndex < count; pageIndex++) {
                    //System.out.println(SiteName + "-" + DesName + "第" + (pageIndex + 1) + "页");
                    //QunarCrawlerService qunarCrawlerService = new QunarCrawlerService();
                    //qunarCrawlerService.setLineService(lineService);
//                    QunarCrawlerService qunarCrawlerService = new QunarCrawlerService();
//                    qunarCrawlerService.setPageIndex(pageIndex);
//                    qunarCrawlerService.setQ(q);
//                    qunarCrawlerService.setD(d);

                    qunarCrawlerServiceList.add(new QunarCrawlerService(qunarLineService, qunarPriceService,pageIndex,d,q,userResident));
                }
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);

                for (QunarCrawlerService qunarCrawlerService:qunarCrawlerServiceList) {
//                    fixedThreadPool.execute(qunarCrawlerService);

                    Future<String> future = fixedThreadPool.submit(qunarCrawlerService);
//                    System.out.println(future.get());
                    log.info(future.get());
                }
                fixedThreadPool.shutdown();
            }
        }

    }
    @Test
    public void spiderWithoutThreadPool() throws Exception{
        int pageIndex = 0; //默认第一页
        String cn = ""; //出发城市 扬州,南京
        String d = "上海"; //站点 如果没有具体的出发城市城市的话。则出发城市不需要填写
        String q = "北京"; //查询条件(XX跟团游)
        String userResident = "上海";
        QunarCrawlerService main =  new QunarCrawlerService(qunarLineService, qunarPriceService,pageIndex,d,q,userResident);
        int rowNum = main.spider(pageIndex,d,q,userResident);
        pageIndex++;
        //main.interrupt();
        if (rowNum > 60) {
            //进行计算分页
            int count = rowNum % 60 == 0 ? rowNum / 60 : rowNum / 60 + 1;

            if (count > 1) {
                //第一页已经计算。直接从第二页开始
                List<QunarCrawlerService> qunarCrawlerServiceList = new ArrayList<>();
                for (; pageIndex < count; pageIndex++) {
                    //qunarCrawlerService.setLineService(lineService);
                    QunarCrawlerService qunarCrawlerService = new QunarCrawlerService();
                    qunarCrawlerService.setQunarLineService(qunarLineService);
                    qunarCrawlerService.setQunarPriceService(qunarPriceService);
                    qunarCrawlerService.setPageIndex(pageIndex);
                    qunarCrawlerService.setQ(q);
                    qunarCrawlerService.setD(d);
                    qunarCrawlerService.call();

                }
            }
        }

    }
}