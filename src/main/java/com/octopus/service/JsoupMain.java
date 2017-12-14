package com.octopus.service;//package com.octopus.service;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// * User: luqinglin
// * Date: 2017-12-08
// * Time: 10:45
// */
//public class JsoupMain {
//
//    /**
//     * 需要抓取字段  ：1.标题 2.价格  3.天数  4.出发城市  5.目的城市  6.景点名称   7.供应商  8.星级
//     * @param args
//     * @throws Exception
//     */
//    public static void main(String[] args) throws Exception{
//        /**
//         * init初始化
//         */
//        String cityShorthand = "sz";
//        String targetCityName = "厦门";
//        String url = "http://s.tuniu.com/search_complex/tours-"+cityShorthand+"-0-"+targetCityName+"/";
//        String nextUrl=getNextPage(url);
//        System.out.println("爬取url"+url);
//        /**
//         * 节点分析
//         */
//        spider(url);
//        //nextUrl=getNextPage(url);
//        while(!nextUrl.equals("")){
//            System.out.println(nextUrl);
//            spider(nextUrl);
//            nextUrl=getNextPage(nextUrl);
//        }
//    }
//
//    public static void spider(String url) throws Exception{
//        Document doc = Jsoup.connect(url)
//                .header("Accept", "*/*")
//                .header("Accept-Encoding", "gzip, deflate")
//                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
//                .header("Referer", "https://www.baidu.com/")
//                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
//                .timeout(15000)
//                .get();
//
//        Elements eachElements = doc.select(".clearfix");
//        Elements detailElements = eachElements.select(".detail");
//        Elements priceElements = eachElements.select(".priceinfo");
//        for (int i =0;i<priceElements.size();i++){
//            //String title = detailElements.get(i).select(".title").text();//获取标题
//            String title;
//            String price;
//            String days;
//            String supplier;//供应商
//            String soldNum;//销售数量
//            String commentNum;//评论数
//            String scenic;//名胜
//            String durl;//详情链接
//            String hotels;//住宿标准
//            String travel;//往返交通
//            String destCity;//目的城市
//            String departCity;//出发城市
//            String port;//组团城市
//            title=getTitle(detailElements.get(i));
//            price=getPrice(priceElements.get(i));
//            supplier=getSupplier(detailElements.get(i));
//            soldNum=getSoldNum(priceElements.get(i));
//            commentNum=getCommentNum(priceElements.get(i));
//            scenic=getScenic(detailElements.get(i));
//            durl=getDurl(detailElements.get(i));
//            Document detailDoc = Jsoup.connect(durl)
//                    .header("Accept", "*/*")
//                    .header("Accept-Encoding", "gzip, deflate")
//                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
//                    .header("Referer", "https://www.baidu.com/")
//                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
//                    .timeout(15000)
//                    .get();
//            Elements summaryElements = detailDoc.getElementsByClass("detail-feature-brief"); //详情节点
//            days=getDays(summaryElements);
//            hotels=getHotels(detailDoc);
//            travel=getTravel(summaryElements);
//            destCity=getDestCity(summaryElements);
//            departCity=getDepartCity(detailElements);
//            port=getPort(summaryElements);
//            System.out.println("---标题："+title+"---出发城市："+departCity+"---目的城市："+destCity+"---成团地点："+port+"---售价："+price+"---供应商："+supplier+"---天数："+days+"---销售数量："+soldNum+"---评论数："+commentNum+"---名胜："+scenic+"---链接："+durl+"---酒店："+hotels+"---往返交通："+travel);
//            String lineNo = durl.substring(durl.lastIndexOf("/")+1,durl.length());
//            //Todo citycode 从数据库获取
//            String cityCode = "2500";
//            String urlPrice = String.format("http://www.tuniu.com//package/api/calendar?productId=%s&bookCityCode=%s", lineNo, cityCode);
//            System.out.println(urlPrice);
//            String priceJson = loadJson(urlPrice);
//            JSONObject jsonObject = new JSONObject(priceJson);
//            JSONArray priceArray = jsonObject.getJSONObject("data").getJSONArray("calendarInfo");
//            for (int j = 0; j <priceArray.length() ; j++) {
//                JSONObject object = priceArray.getJSONObject(j);
//               // System.out.println(object);
//                Integer adultPrice = object.getInt("adultPrice");
//                Integer childPrice = object.getInt("childPrice");
//                String date = object.getString("planDate");
//                System.out.println("线路"+j+":成人价格:"+adultPrice+"儿童价格："+childPrice+"预定团期："+date);
//            }
//        }
//    }
//
//    /**
//     * 获取标题
//     * @param detailElement
//     * @return
//     */
//    public static String getTitle(Element detailElement){
//        return  detailElement.select(".title").text();
//    }
//
//    /**
//     * 获取价格
//     * @param priceElement
//     * @return
//     */
//    public static String getPrice(Element priceElement){
//        return  priceElement.select(".tnPrice").text().replaceAll("起","").replaceAll("¥","");
//    }
//
////    /**
////     * 根据标题获取天数
////     * @param detailElement
////     * @return
////     *
////    public static String getDays(Element detailElement){
////        String title = getTitle(detailElement);
////        if (title.contains("日")) {
////            return title.substring(title.indexOf("日") - 1, title.indexOf("日"));
////        }
////        return "";
////    }*/
//
//    /**
//     *获取天数
//     * @param summaryElements
//     * @return
//     */
//    public static String getDays(Elements summaryElements){
//        if (summaryElements.size()>0){
//            Elements element = summaryElements.get(0).getElementsByClass("detail-feature-brief-item").get(0).select("strong");
//            return element.text();
//        }
//        return "";
//    }
//
//    /**
//     * 获取供应商
//     * @param detailElement
//     * @return
//     */
//    public static String getSupplier(Element detailElement){
//        Elements supplierElements = detailElement.select(".brand").select("span>span");
//        if (supplierElements.size()>0){
//           return supplierElements.get(0).text();
//        }
//        return "";
//    }
//
//    /**
//     * 获取销售数
//     * @param priceElement
//     * @return
//     */
//    public static String getSoldNum(Element priceElement){
//        Elements soldNumElements = priceElement.select(".person-num");//销售数量节点
//        if (soldNumElements.size()>0){
//            return soldNumElements.get(0).getElementsByTag("i").text();//获取销售数量
//        }
//        return "";
//    }
//
//    /**
//     * 获取评论数
//     * @param priceElement
//     * @return
//     */
//    public static String getCommentNum(Element priceElement){
//        Elements commentNumElements =priceElement.select(".person-comment");//评论数量节点
//        if (commentNumElements.size()>0){
//            return commentNumElements.get(0).getElementsByTag("i").text();//获取评论数量
//        }
//        return "";
//    }
//
//    /**
//     * 获取名胜
//     * @param detailElement
//     * @return
//     */
//    public static String getScenic(Element detailElement){
//        return detailElement.select(".overview-scenery").text();
//    }
//
//    /**
//     * 获取链接
//     * @param detailElement
//     * @return
//     */
//    public static String getDurl(Element detailElement){
//        return detailElement.parent().attr("abs:href");
//    }
//
//    /**
//     * 获取住宿标准
//     * @param document
//     * @return
//     */
//    public static String getHotels(Document document){
//        Elements elements = document.select(".detail-journey-star");
//        if (elements.size()>0){
//
//            return elements.get(0).text();
//        }
//        return "";
//    }
//
//    /**
//     * 往返交通
//     * @param summaryElements
//     * @return
//     */
//    public static String getTravel(Elements summaryElements) {
//        if (summaryElements.size()>0){
//            Elements element = summaryElements.get(0).getElementsByClass("detail-feature-brief-item").get(3).select("strong");
//            return element.text();
//        }
//        return "";
//    }
//    public static String getPort(Elements summaryElements) {
//        if (summaryElements.size()>0){
//            Elements element = summaryElements.get(0).getElementsByClass("detail-feature-brief-item").get(1).select("strong");
//            return element.text().replaceAll("成团","");
//        }
//        return "";
//    }
//    /**
//     * 获取目标城市
//     * @param summaryElements
//     * @return
//     */
//    public static String getDestCity(Elements summaryElements){
//        if (summaryElements.size()>0){
//            Elements element = summaryElements.get(0).getElementsByClass("detail-feature-brief-item").get(2).select("strong");
//            return element.text();
//        }
//        return "";
//    }
//
//    /**
//     * 获取出发城市
//     * @param detailElement
//     * @return
//     */
//    public static String getDepartCity(Elements detailElement){
//       return detailElement.select(".subtitle").select("span").get(0).text().replaceAll("出发","");
//    }
//
//    /**
//     * 获取下一页URL
//     * @param url
//     * @return
//     * @throws Exception
//     */
//    public static String getNextPage(String url) throws Exception{
//        Document doc = Jsoup.connect(url)
//                .header("Accept", "*/*")
//                .header("Accept-Encoding", "gzip, deflate")
//                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
//                .header("Referer", "https://www.baidu.com/")
//                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
//                .timeout(5000)
//                .get();
//       return doc.select(".page-next").attr("abs:href");
//    }
//    public static String loadJson (String url) {
//        StringBuilder json = new StringBuilder();
//        try {
//            URL urlObject = new URL(url);
//            URLConnection uc = urlObject.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//            String inputLine = null;
//            while ( (inputLine = in.readLine()) != null) {
//                json.append(inputLine);
//            }
//            in.close();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return json.toString();
//    }
//}
