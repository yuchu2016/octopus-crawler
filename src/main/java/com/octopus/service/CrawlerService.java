package com.octopus.service;

import com.octopus.pojo.ArriveCity;
import com.octopus.pojo.DepartCity;
import com.octopus.pojo.GroupPrice;
import com.octopus.pojo.Line;
import com.octopus.util.DateUtil;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-11
 * Time: 13:25
 */
@Service
public class CrawlerService{

    @Autowired
    private ArriveCityService arriveCityService;
    @Autowired
    private DepartCityService departCityService;
    @Autowired
    private LineService lineService;
    @Autowired
    private GroupPriceService groupPriceService;

    //private static final Logger log = LoggerFactory.getLogger(CrawlerService.class);
    /**
     * 制定出发地
     * @param depart 出发地
     * @throws Exception
     */
    public void execute(String depart)throws Exception{
        DepartCity departCity = departCityService.getDepartCityByCityName(depart);
        List<ArriveCity> arriveCityList = arriveCityService.getAll();
        //ArriveCity arriveCity = arriveCityService.getArriveCityByCityName(desc);
        for (ArriveCity arriveCity:arriveCityList) {
            lineService.deleteByDepartCityAndDescity(depart,arriveCity.getCityName());
            XxlJobLogger.log("开始爬取"+departCity.getCityName()+"---"+arriveCity.getCityName()+"的数据");
            String url = "http://s.tuniu.com/search_complex/tours-"+departCity.getShortHand()+"-0-"+arriveCity.getCityName()+"/";
            //Thread.sleep(1000);
//            Thread crawlerThread = new TuniuCrawlerThread(url);
//            crawlerThread.run();
            spider(url);
            Thread.sleep(100);
            String nextUrl=getNextPage(url);
            while(!nextUrl.equals("")){
                XxlJobLogger.log("开始爬取下一页,url为:"+nextUrl);
//                Thread thread = new TuniuCrawlerThread(nextUrl);
//                thread.run();
                spider(nextUrl);
                nextUrl=getNextPage(nextUrl);
            }
        }
    }

    /**
     * 制定出发地和目的地
     * @param depart 出发地
     * @param desc 目的地
     * @throws Exception
     */
    public void execute(String depart,String desc)throws Exception{
        DepartCity departCity = departCityService.getDepartCityByCityName(depart);
        ArriveCity arriveCity = arriveCityService.getArriveCityByCityName(desc);
        lineService.deleteByDepartCityAndDescity(depart,desc);
        String url = "http://s.tuniu.com/search_complex/tours-"+departCity.getShortHand()+"-0-"+arriveCity.getCityName()+"/";
        String nextUrl=getNextPage(url);
        spider(url);
        //Thread.sleep(100);
//      Thread crawlerThread = new TuniuCrawlerThread(url);
//      crawlerThread.run();
        while(!nextUrl.equals("")){
//            System.out.println(nextUrl);
//            Thread thread = new TuniuCrawlerThread(nextUrl);
//            thread.run();
            spider(nextUrl);
            nextUrl=getNextPage(nextUrl);
        }
    }

    /**
     * 根据url爬取数据
     * @param url
     * @throws Exception
     */
    public void spider(String url) throws Exception{
        try{
            Document doc = Jsoup.connect(url)
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Referer", "https://www.baidu.com/")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(15000)
                    .get();

            Line line = new Line();
            Elements eachElements = doc.select(".clearfix");
            Elements detailElements = eachElements.select(".detail");
            Elements priceElements = eachElements.select(".priceinfo");
            for (int i =0;i<priceElements.size();i++){
                line.setLineId(UUID.randomUUID().toString().replaceAll("-",""));
                line.setSiteName("途牛");
                line.setTypeName("跟团游");
                line.setCDate(DateUtil.format(new Date(),DateUtil.C_DATE_PATTON_DEFAULT));
                line.setLineTitle(getTitle(detailElements.get(i)));
                line.setSupplierName(getSupplier(detailElements.get(i)));
                String soldNum = getSoldNum(priceElements.get(i));
                Integer soldQty=0;
                if (StringUtils.isNotBlank(soldNum)){
                    try {
                        soldQty = soldNum.contains("万")? new BigDecimal(soldNum.substring(0,soldNum.length()-1)).multiply(new BigDecimal("10000")).intValue():Integer.parseInt(soldNum);
                    }catch (Exception e){
                        if (e instanceof InterruptedException) {
                            throw e;
                        }
                        XxlJobLogger.log("获取出游人数失败,原因为"+e.getMessage());
                    }
                }
                //log.warn(soldNum);
                line.setSoldCount(soldQty.toString());
                line.setCommentNumber(getCommentNum(priceElements.get(i)));
                line.setSights(getScenic(detailElements.get(i)));
                line.setDetailAddress(getDurl(detailElements.get(i)));
                Document detailDoc;
                try {
                    detailDoc = Jsoup.connect(line.getDetailAddress())
                            .header("Accept", "*/*")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                            .header("Referer", "https://www.baidu.com/")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                            .timeout(20000)
                            .get();
                }catch (Exception e){
                    if (e instanceof InterruptedException) {
                        throw e;
                    }
//                    XxlJobLogger.log("获取详情页面失败，原因为:{}",e.getMessage());
                    XxlJobLogger.log("获取详情页面失败，原因为:"+e.getMessage());
                    break;
                }
                Elements summaryElements = detailDoc.getElementsByClass("detail-feature-brief"); //详情节点
                line.setDays(getDays(summaryElements));
                line.setHotels(getHotels(detailDoc));
                line.setTrafficStr(getTravel(summaryElements));
                line.setDesCity(getDestCity(summaryElements));
                line.setDepartCity(getDepartCity(detailElements));
                line.setPort(getPort(summaryElements));
                //System.out.println("---标题："+title+"---出发城市："+departCity+"---目的城市："+destCity+"---成团地点："+port+"---售价："+price+"---供应商："+supplier+"---天数："+days+"---销售数量："+soldNum+"---评论数："+commentNum+"---名胜："+scenic+"---链接："+durl+"---酒店："+hotels+"---往返交通："+travel);
                //Thread.sleep(100);
                XxlJobLogger.log("当前线程为:"+Thread.currentThread().getId());
                XxlJobLogger.log(line.toString());
                lineService.save(line);
                try {
                    String lineNo = line.getDetailAddress().substring(line.getDetailAddress().lastIndexOf("/") + 1, line.getDetailAddress().length());
                    String cityCode = arriveCityService.getArriveCityByCityName(line.getDepartCity()).getCityCode();
                    String urlPrice = String.format("http://www.tuniu.com//package/api/calendar?productId=%s&bookCityCode=%s", lineNo, cityCode);
                    //System.out.println(urlPrice);
                    String priceJson = loadJson(urlPrice);
                    JSONObject jsonObject = new JSONObject(priceJson);
                    if (null != jsonObject.getJSONObject("data").getJSONArray("calendarInfo")) {
                        JSONArray priceArray = jsonObject.getJSONObject("data").getJSONArray("calendarInfo");
                        for (int j = 0; j < priceArray.length(); j++) {
                            JSONObject object = priceArray.getJSONObject(j);
                            Integer adultPrice = object.getInt("adultPrice");
                            Integer childPrice = object.getInt("childPrice");
                            String date = object.getString("planDate");
                            GroupPrice groupPrice = new GroupPrice();
                            groupPrice.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                            groupPrice.setGroupDate(date);
                            groupPrice.setAdultPrice(adultPrice.toString());
                            groupPrice.setChildPrice(childPrice.toString());
                            groupPrice.setLineId(line.getLineId());
                            groupPriceService.save(groupPrice);
                            //System.out.println("线路"+j+":成人价格:"+adultPrice+"儿童价格："+childPrice+"预定团期："+date);
                        }
                    }
                }catch (Exception e){
                    XxlJobLogger.log("获取详细价格失败,线路url为:"+line.getDetailAddress()+",原因:"+e.getMessage());
                    if (e instanceof InterruptedException) {
                        throw e;
                    }

                }
            }
        }catch (Exception e){
            XxlJobLogger.log("连接超时，超时url为:"+url);
            if (e instanceof InterruptedException) {
                throw e;
            }


        }
    }

    /**
     * 获取标题
     * @param detailElement
     * @return
     */
    public String getTitle(Element detailElement){
        return  detailElement.select(".title").text();
    }

    /**
     * 获取价格
     * @param priceElement
     * @return
     */
    public String getPrice(Element priceElement){
        return  priceElement.select(".tnPrice").text().replaceAll("起","").replaceAll("¥","");
    }

    /**
     *获取天数
     * @param summaryElements
     * @return
     */
    public String getDays(Elements summaryElements){
        if (summaryElements.size()>0){
            Elements element = summaryElements.get(0).getElementsByClass("detail-feature-brief-item").get(0).select("strong");
            return element.text();
        }
        return "";
    }

    /**
     * 获取供应商
     * @param detailElement
     * @return
     */
    public String getSupplier(Element detailElement){
        Elements supplierElements = detailElement.select(".brand").select("span>span");
        if (supplierElements.size()>0){
            return supplierElements.get(0).text();
        }
        return "";
    }

    /**
     * 获取销售数
     * @param priceElement
     * @return
     */
    public String getSoldNum(Element priceElement){
        Elements soldNumElements = priceElement.select(".person-num");//销售数量节点
        if (soldNumElements.size()>0){
            return soldNumElements.get(0).getElementsByTag("i").text();//获取销售数量
        }
        return "";
    }

    /**
     * 获取评论数
     * @param priceElement
     * @return
     */
    public String getCommentNum(Element priceElement){
        Elements commentNumElements =priceElement.select(".person-comment");//评论数量节点
        if (commentNumElements.size()>0){
            return commentNumElements.get(0).getElementsByTag("i").text();//获取评论数量
        }
        return "";
    }

    /**
     * 获取名胜
     * @param detailElement
     * @return
     */
    public String getScenic(Element detailElement){
        return detailElement.select(".overview-scenery").text();
    }

    /**
     * 获取链接
     * @param detailElement
     * @return
     */
    public String getDurl(Element detailElement){
        return detailElement.parent().attr("abs:href");
    }

    /**
     * 获取住宿标准
     * @param document
     * @return
     */
    public String getHotels(Document document){
        Elements elements = document.select(".detail-journey-star");
        if (elements.size()>0){
            return elements.get(0).text();
        }
        return "";
    }

    /**
     * 往返交通
     * @param summaryElements
     * @return
     */
    public String getTravel(Elements summaryElements) {
        if (summaryElements.size()>0){
            Elements element = summaryElements.get(0).getElementsByClass("detail-feature-brief-item").get(3).select("strong");
            return element.text();
        }
        return "";
    }

    /**
     * 获取成团地点
     * @param summaryElements
     * @return
     */
    public String getPort(Elements summaryElements) {
        if (summaryElements.size()>0){
            Elements element = summaryElements.get(0).getElementsByClass("detail-feature-brief-item").get(1).select("strong");
            return element.text().replaceAll("成团","");
        }
        return "";
    }
    /**
     * 获取目标城市
     * @param summaryElements
     * @return
     */
    public String getDestCity(Elements summaryElements){
        if (summaryElements.size()>0){
            Elements element = summaryElements.get(0).getElementsByClass("detail-feature-brief-item").get(2).select("strong");
            return element.text();
        }
        return "";
    }

    /**
     * 获取出发城市
     * @param detailElement
     * @return
     */
    public String getDepartCity(Elements detailElement){
        return detailElement.select(".subtitle").select("span").get(0).text().replaceAll("出发","");
    }

    /**
     * 获取下一页URL
     * @param url
     * @return
     * @throws Exception
     */
    public String getNextPage(String url) throws Exception{
        Document doc = Jsoup.connect(url)
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Referer", "https://www.baidu.com/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                .timeout(15000)
                .get();
        String nextUrl = doc.select(".page-next").attr("abs:href");
        String totalUrl = doc.select(".page-next").prev().text();
        XxlJobLogger.log("下一页url为："+nextUrl+",总共:"+totalUrl+"页");
        return nextUrl;
    }

   /**
     * 根据url获取json数据
     * @param url
     * @return
     */
    public String loadJson (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
