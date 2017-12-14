package com.octopus.qunarcrawler;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-13
 * Time: 10:58
 */
public class QunarCrawler {
//    public static final Logger log = LoggerFactory.getLogger(QunarCrawler.class);
//    public static void main(String[] args) throws Exception {
//        int pageIndex = 0; //默认第一页
//        String cn = ""; //出发城市 扬州,南京
//        String d = "上海"; //站点 如果没有具体的出发城市城市的话。则出发城市不需要填写
//        String q = "北京"; //查询条件(XX跟团游)
//        String userResident = "上海";
//        int rowNum = spider(pageIndex,d,q,userResident);
//        if (rowNum > 60) {
//            //进行计算分页
//            int count = rowNum % 60 == 0 ? rowNum / 60 : rowNum / 60 + 1;
//
//            if (count > 1) {
//                //第一页已经计算。直接从第二页开始
//
//                for (; pageIndex < count; pageIndex++) {
//                    //System.out.println(SiteName + "-" + DesName + "第" + (pageIndex + 1) + "页");
//                    spider(pageIndex, d, q, userResident);
//                    Thread.sleep(1000);
//                }
//            }
//        }
//        //spider(pageIndex,d,q,userResident);
//
//    }
//
//    public static int spider(int pageIndex,String d,String q,String userResident) throws Exception {
//        StringBuilder stringBuilder = new StringBuilder();
//        String lmString = Integer.valueOf(pageIndex * 60).toString() + ",60";
//        String url = stringBuilder.append("https://dujia.qunar.com/golfz/routeList/adaptors/pcTop?isTouch=0&t=travel&o=pop-desc&lm=" + lmString + "&fhLimit=" + "0,60" + "&q=" + q + "跟团游&d=" + d + "&s=all&qs_ts=1506566132793&ti=3&tm=l01_all_search_origin&sourcepage=list&userResident=" + userResident + "&random=" + (int) ((Math.random() * 9 + 1) * 100000) + "&aroundWeight=1&qssrc=eyJ0cyI6IjE1MDY1NjYxMzI3OTMiLCJzcmMiOiJhbGwuZW52YSIsImFjdCI6ImZpbHRlciIsInJhbmRvbSI6IjIzNzc0NyJ9&m=l,bookingInfo,browsingInfo,lm&displayStatus=pc&ddf=true&userId=ezu0oVnMXmyOBlorXQ+EAg==&hlFields=title&gpscity=上海&lowPrice=other&lines6To10=0").toString();
//        log.info(url);
//        String jsonStr = loadJson(url);
//        //log.info(jsonStr);
//        // doc.text();
//        ObjectMapper mapper = new ObjectMapper();
//        Map m = mapper.readValue(jsonStr, Map.class);
//        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//        JSONObject dataObj = (JSONObject) jsonObject.get("data");
//        JSONObject listObj = (JSONObject) dataObj.get("list");
//        String numFound = listObj.get("numFound").toString();
//        log.info("共发现" + numFound + "条");
//        JSONArray resultArray = (JSONArray) listObj.get("results");
//        Iterator<Object> it = resultArray.iterator();
//        int lineIndex = 0;
//        while (it.hasNext()) {
//            lineIndex++;
//            if (lineIndex == 3) {
//
//            }
//            Line line = new Line();
//            line.setCDate(DateUtil.format(new Date(), DateUtil.C_DATE_PATTON_DEFAULT));
//            line.setSiteName(Site.QUNAR.getSiteName());
//            line.setLineId(UUID.randomUUID().toString().replaceAll("-", ""));
//            JSONObject ob = (JSONObject) it.next();
//
//            line.setDepartCity(ob.get("dep").toString());
//
//            // String soldCount = "0";
//
//            if (ob.get("soldCount90") != null) {
//                line.setSoldCount(ob.getString("soldCount90"));
//            }
//
//            line.setTypeName(ob.get("extendFunction").toString());
//            line.setLineTitle(ob.get("title").toString());
//            //System.out.println(LINETITLE);
//            line.setDetailAddress(ob.get("url").toString());
//            line.setDesCity(q);
//            line.setDays(ob.get("itineraryDay").toString() + "(" + ob.get("hotelFee").toString() + ")");
//            String sights = "";
//            JSONArray sightsArray = ob.getJSONArray("sights");
//            Iterator<Object> itqq = sightsArray.iterator();
//            while (itqq.hasNext()) {
//                sights += "   " + itqq.next().toString();
//            }
//            line.setSights(sights);
//            String JOURNEY = "";
//            if (ob.get("longPlanTitle") != null) {
//                JOURNEY += ob.get("longPlanTitle").toString();
//            }
//            if (ob.get("productFeatures") != null) {
//                JOURNEY += "  " + ob.get("productFeatures").toString();
//            }
//            line.setJourney(JOURNEY);
//            String HOTELS = ob.get("hotelGradeText").toString();
//            JSONArray hotelTypeArray = ob.getJSONArray("hotelTypes");
//            if (hotelTypeArray != null) {
//                Iterator<Object> ithotel = hotelTypeArray.iterator();
//                while (ithotel.hasNext()) {
//
//                    HOTELS += "   " + ithotel.next().toString();
//                }
//            }
//            line.setHotels(HOTELS);
//            String SUPPLIERNAME = ((JSONObject) ((JSONObject) ob.get("summary")).get("supplier")).getString("name");
//            line.setSupplierName(SUPPLIERNAME);
//            JSONObject TRAFFICObj = (JSONObject) ob.get("trafficInfo");
//            String TRAFFIStr = TRAFFICObj.getString("traffic") + " " + TRAFFICObj.getString("transfer");
//            line.setTrafficStr(TRAFFIStr);
//            //获取机票信息
//            String tuanTtsId = ob.get("tuanTtsId").toString();
//            String id = ob.get("id").toString();
//            String traficInfoUrl = "https://rrcx3.package.qunar.com/user/detail/getTrffcHtlInfo.json?pId=" + tuanTtsId + "&isVer=false&oid=&tId=" + id;
//
//            String traficResponse = loadJson(traficInfoUrl);
//            JSONObject traficJson = JSONObject.parseObject(traficResponse);
//            JSONObject traficDataObj = (JSONObject) traficJson.get("data");
//            StringBuilder traficBuild = new StringBuilder();
//            String PortString = "";
//            if (traficDataObj != null) {
//                JSONArray traficArray = traficDataObj.getJSONArray("roundTripTraffic");
//                Iterator<Object> traficIt = traficArray.iterator();
//                int dateIndex = 0;
//                while (traficIt.hasNext()) {
//
////                    // System.out.println(dateIndex);
//                    JSONObject traficOb = (JSONObject) traficIt.next();
//                    JSONObject trafficsObj = traficOb.getJSONObject("traffics");
//                    JSONArray goArray = trafficsObj.getJSONArray("go");
//                    Iterator<Object> goIt = goArray.iterator();
//                    while (goIt.hasNext()) {
//                        JSONObject goOb = (JSONObject) goIt.next();
//                        traficBuild.append("去程：" + goOb.getString("airlineName") + " " + goOb.getString("departure") + "[" + goOb.getString("dep_airport") + "]" + "-" + goOb.getString("arrive") + "[" + goOb.getString("arr_airport") + "]");
//
//                        PortString = goOb.getString("departure");
//                    }
//                    line.setPort(PortString);
//
//                    JSONArray backArray = trafficsObj.getJSONArray("back");
//                    Iterator<Object> backIt = backArray.iterator();
//                    while (backIt.hasNext()) {
//                        JSONObject backOb = (JSONObject) backIt.next();
//                        traficBuild.append("返程：" + backOb.getString("airlineName") + " " + backOb.getString("departure") + "[" + backOb.getString("dep_airport") + "]" + "-" + backOb.getString("arrive") + "[" + backOb.getString("arr_airport") + "]");
//                    }
//                    line.setTrafficBuild(traficBuild.toString());
//                }
//            }
//            log.info(line.toString());
//
//        }
//        return Integer.valueOf(numFound);
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
//
//    public static String unescape(String s) {
//        StringBuffer sbuf = new StringBuffer();
//        int l = s.length();
//        int ch = -1;
//        int b, sumb = 0;
//        for (int i = 0, more = -1; i < l; i++) {
//            /* Get next byte b from URL segment s */
//            switch (ch = s.charAt(i)) {
//                case '%':
//                    ch = s.charAt(++i);
//                    int hb = (Character.isDigit((char) ch) ? ch - '0'
//                            : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
//                    ch = s.charAt(++i);
//                    int lb = (Character.isDigit((char) ch) ? ch - '0'
//                            : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
//                    b = (hb << 4) | lb;
//                    break;
//                case '+':
//                    b = ' ';
//                    break;
//                default:
//                    b = ch;
//            }
//            /* Decode byte b as UTF-8, sumb collects incomplete chars */
//            if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)
//                sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb
//                if (--more == 0)
//                    sbuf.append((char) sumb); // Add char to sbuf
//            } else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
//                sbuf.append((char) b); // Store in sbuf
//            } else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
//                sumb = b & 0x1f;
//                more = 1; // Expect 1 more byte
//            } else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
//                sumb = b & 0x0f;
//                more = 2; // Expect 2 more bytes
//            } else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
//                sumb = b & 0x07;
//                more = 3; // Expect 3 more bytes
//            } else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
//                sumb = b & 0x03;
//                more = 4; // Expect 4 more bytes
//            } else /*if ((b & 0xfe) == 0xfc)*/{ // 1111110x (yields 1 bit)
//                sumb = b & 0x01;
//                more = 5; // Expect 5 more bytes
//            }
//            /* We don't test if the UTF-8 encoding is well-formed */
//        }
//        return sbuf.toString();
//    }
}
