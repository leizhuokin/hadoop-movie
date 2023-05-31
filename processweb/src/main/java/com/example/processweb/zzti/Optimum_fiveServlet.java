package com.example.processweb.zzti;


import com.example.processweb.utils.ReadHDFSFile;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
@WebServlet(name = "Optimum_fiveServlet", urlPatterns = "/Optimum_fiveServlet")
public class Optimum_fiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.读取HDFS上的数据分析结果
        String path = "hdfs://192.168.100.101:9000/zzti/result/Optimum_five";
        List<Map> rows = null;
        try {
            rows = ReadHDFSFile.readFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2.将读取的数据转换为Json格式({"browser":"chrome","count",10})
        String jsonStr = new Gson().toJson(rows);
        //3.将转换后的数据发送到前端页面
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
    }
}
