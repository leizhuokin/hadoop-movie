<%--
  Created by IntelliJ IDEA.
  User: leizhuo
  Date: 2022/6/20
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
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
    $.getJSON("${pageContext.request.contextPath}/Optimum_fiveServlet", function (data) {
        console.log(data);
        var xAxis = []
        var yAxis = []
        var splitYZ=[]
        for (var i = 0; i < data.length; i++) {
            splitYZ=data[i].value.split(":");
            xAxis.push(data[i].key+splitYZ[1]);
            yAxis.push(splitYZ[0]);
        }
        var chartDom = document.getElementById('main');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: xAxis,
                    axisLabel: {
                        interval: 0 , //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
                        rotate: 40,
                        show: false
                    },
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '全球票房',
                    type: 'bar',
                    barWidth: '60%',
                    data: yAxis
                }
            ]
        };
        option && myChart.setOption(option);
    })
</script>
</body>
</html>
