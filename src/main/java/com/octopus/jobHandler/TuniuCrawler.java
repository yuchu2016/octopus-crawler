package com.octopus.jobHandler;

import com.octopus.service.CrawlerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-12
 * Time: 8:56
 */
@JobHander(value = "TuniuCrawler")
@Service
public class TuniuCrawler extends IJobHandler{
    @Autowired
    private CrawlerService crawlerService;
    @Override
    public synchronized ReturnT<String> execute(String... strings) throws Exception {
        if (strings.length==1) {
            try {
                crawlerService.execute(strings[0]);
            }catch (Exception e){
                if (e instanceof InterruptedException) {
                    throw e;
                }
                XxlJobLogger.log("执行失败,原因为:"+e.getMessage());
                return ReturnT.FAIL;
            }
        }
        if (strings.length==2){
            try {
                crawlerService.execute(strings[0],strings[1]);
                return ReturnT.SUCCESS;
            }catch (Exception e){
                if (e instanceof InterruptedException) {
                    throw e;
                }
                XxlJobLogger.log("执行失败,原因为:"+e.getMessage());
                return ReturnT.FAIL;
            }
        }
        ReturnT returnT = new ReturnT<>();
        returnT.setCode(500);
        returnT.setContent("任务失败");
        returnT.setMsg("参数不正确");
        return returnT;
    }
}
