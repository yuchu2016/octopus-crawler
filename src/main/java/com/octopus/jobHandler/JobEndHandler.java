package com.octopus.jobHandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-12
 * Time: 10:45
 */
@JobHander(value = "JobEnder")
@Service
public class JobEndHandler extends IJobHandler {
    public ReturnT<String> execute(String... params) throws Exception {
        XxlJobLogger.log(params[0]);
        //根据进程号找到所在进程
        Thread thread = findThread(Integer.valueOf(params[0]));
        //thread.stop();
        if (null != thread) {
            thread.stop();//销毁爬虫所在进程
            return ReturnT.SUCCESS;
        } else {
            ReturnT returnT = new ReturnT<>();
            returnT.setCode(500);
            returnT.setMsg("进程未找到");
            returnT.setContent("未找到该进程");
            return ReturnT.FAIL;
        }
    }

    public Thread findThread(long threadId) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while(group != null) {
            Thread[] threads = new Thread[(int)(group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for(int i = 0; i < count; i++) {
                if(threadId == threads[i].getId()) {
                    return threads[i];
                }
            }
            group = group.getParent();
        }
        return null;
    }
}
