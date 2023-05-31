<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2022/6/18
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>实现用户浏览器统计分析可视化展示</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.min.js"></script>
    <style type="text/css">
        .divstyle {
            width: 800px;
            height: 400px;
        }
    </style>
</head>
<body>
<!--为Echarts设计一个显示图表的div-->
<div id="main" class="divstyle"></div>
<script type="text/javascript">
    $.getJSON("${pageContext.request.contextPath}/AvgScore_fourServlet", function (data) {
        console.log(data);
        var xAxis = []
        var yAxis = []
        for (var i = 0; i < data.length; i++) {
            xAxis.push(data[i].key);
            yAxis.push(data[i].value);
        }

        var chartDom = document.getElementById('main');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
            xAxis: {
                type: 'category',
                axisLabel: {
                    interval: 0 , //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
                    rotate: 40
                },
                data: xAxis
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    data: yAxis,
                    type: 'line'
                }
            ]
        };
        option && myChart.setOption(option);
    })

</script>
</body>
</html>
