package com.example.processweb.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：封装工具类读取HDFS上传的分析结果
 */
public class ReadHDFSFile {

    /**
     * 读取HDFS上的文件
     * @param filename :存放分析结果的路径
     * @return List
     * @throws Exception
     */
    public static List<Map> readFile(String filename) throws Exception{
        List<Map> datas = new ArrayList<>();

        Configuration conf = new Configuration();
        Path p = new Path(filename);
        FileSystem fs = p.getFileSystem(conf);
        FileStatus[] file = fs.listStatus(p);
        for(int i=0; i<file.length;i ++){
            InputStream in = fs.open(file[i].getPath());
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
            String line = null;
            Map<String,String> row =null;
            while((line = br.readLine())!=null){
                row = new HashMap<>();
                String[] data = line.split("\t");
                row.put("key",data[0]);
                row.put("value",data[1]);
                datas.add(row);
            }
        }

        return datas;
    }


}
