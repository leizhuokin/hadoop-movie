package org.example.utils;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Created by administrator on 14/6/2022.
 * 通用工具类
 */
public class HadoopUtil {

    /**
     * 分隔符类型,使用正则表达式,表示分隔符为\t或者,
     * 使用方法为SPARATOR.split(字符串)
     */
    public static final Pattern SPARATOR = Pattern.compile("[\t,]");

    /**
     * HDFS路径的根目录
     */
    public static final String HDFS = "hdfs://192.168.229.128:9000";



    /**
     * 将map阶段传递过来的数据按照unixtime从小到大排序(使用TreeMap)
     *
     * @param context reducer的context上下文,用于设置counter
     * @param values  map阶段传递过来的数据
     * @return key为unixtime, value为pos
     */
    public static TreeMap<Long, String> getSortedData(Reducer.Context context, Iterable<Text> values) {
        TreeMap<Long, String> sortedData = new TreeMap<Long, String>();
        for (Text v : values) {
            String[] vs = v.toString().split(",");
            try {
                sortedData.put(Long.parseLong(vs[1]), vs[0]);
            } catch (NumberFormatException num) {
                num.printStackTrace();
            }
        }
        return sortedData;
    }
}
