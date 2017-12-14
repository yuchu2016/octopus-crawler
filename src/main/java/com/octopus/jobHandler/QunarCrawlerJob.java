package com.octopus.jobHandler;

import com.octopus.pojo.QunarSite;
import com.octopus.service.QunarCrawlerService;
import com.octopus.service.QunarLineService;
import com.octopus.service.QunarPriceService;
import com.octopus.service.QunarSiteService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-13
 * Time: 16:25
 */
@JobHander(value = "QunarCrawler")
@Service
public class QunarCrawlerJob extends IJobHandler{


    private static Logger log = LoggerFactory.getLogger(QunarCrawlerJob.class);
    @Autowired
    private QunarLineService qunarLineService;
    @Autowired
    private QunarPriceService qunarPriceService;
    @Autowired
    private QunarSiteService qunarSiteService;

    @Override
    public ReturnT<String> execute(String... strings) throws Exception {
        int pageIndex = 0; //默认第一页
        String cn = ""; //出发城市 扬州,南京
        //String d = "上海"; //站点 如果没有具体的出发城市城市的话。则出发城市不需要填写
        String q = "北京"; //查询条件(XX跟团游)
//        String userResident = "上海";
        List<QunarSite> siteList = qunarSiteService.findAll();
        for (QunarSite site:siteList) {
            try {
                spiderWithThreadPool(pageIndex, cn, site.getSiteName(), q, site.getSiteName());
            }catch (Exception e){
                if (e instanceof InterruptedException){
                    throw e;
                }
                XxlJobLogger.log("出错");
            }
        }
        return null;
    }

    /**
     *
     * @param pageIndex 开始页码
     * @param cn 出发城市 扬州,南京
     * @param d 站点 如果没有具体的出发城市城市的话。则出发城市不需要填写
     * @param q 查询条件(XX跟团游)
     * @param userResident
     * @throws Exception
     */
    public void spiderWithThreadPool(int pageIndex, String cn, String d, String q, String userResident) throws Exception{
//        int pageIndex = 0; //默认第一页
//        String cn = ""; //出发城市 扬州,南京
//        String d = "上海"; //站点 如果没有具体的出发城市城市的话。则出发城市不需要填写
//        String q = "北京"; //查询条件(XX跟团游)
//        String userResident = "上海";
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


}
