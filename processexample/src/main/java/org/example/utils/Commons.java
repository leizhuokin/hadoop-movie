package org.example.utils;

import org.example.model.DouBanLog;
import org.example.model.MovieLog;
import org.example.model.WebLog;

/**
 * 工具类:实现日志格式的转换
 */
public class Commons {

    /**
     * 功能：读取日志文件中的每行内容封装为WebLog对象
     * @param line
     * @return
     */
   public static DouBanLog PageDouBanLog(String line){
       DouBanLog doubanLog=new DouBanLog();
       String[] str=line.split(",");
       doubanLog.setId(str[0]);
       doubanLog.setName(str[1]);
      doubanLog.setYear(str[2]);
      doubanLog.setScore(str[3]);
      doubanLog.setScorenum(str[4]);
      doubanLog.setType(str[5]);
      doubanLog.setCountry(str[6]);
      return doubanLog;
   }
   public static MovieLog pageMovieLog(String line){
       MovieLog movieLog=new MovieLog();
       String[]str= line.split(",");
       movieLog.setRate(str[0]);
       movieLog.setName(str[1]);
       movieLog.setTime(str[2]);
       movieLog.setType(str[3]);
       movieLog.setDirector(str[4]);

       movieLog.setBoxoffice(str[5].substring(0,str[5].indexOf("$")));
       return movieLog;
   }
    public static WebLog pageWebLog(String line){
        WebLog webLog = new WebLog();
        String[] str = line.split(" ");
        //判断标准的日志格式（数组长度大于11）
        if(str.length > 11){
            webLog.setRemote_addr(str[0]);
            webLog.setRemote_user(str[1]);
            webLog.setRemote_time(str[3].substring(1));
            webLog.setRemote_method(str[5].substring(1));
            webLog.setRemote_page(str[6]);
            if(str[7].length()<1){
                webLog.setRemote_http("http");
            }else{
                webLog.setRemote_http(str[7].substring(0,str[7].length()-1));
            }
            webLog.setRequest_status(str[8]);
            webLog.setSend_bytes(str[9]);
            webLog.setHttp_refers(str[10]);
            webLog.setUser_agent(str[11]);
            webLog.setValidate(true);
        }else{
            webLog.setValidate(false);
        }
        return webLog;
    }

}
