<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="renderer" content="webkit">
    <title>数据可视化</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/index.css"/>
</head>
<body>
<!-- 头部 -->
<header>
    <h1>数据可视化-ECharts</h1>
    <div class="show-time"></div>
    <script>
        var t = null;
        t = setTimeout(time, 1000); //开始运行
        function time() {
            clearTimeout(t); //清除定时器
            dt = new Date();
            var y = dt.getFullYear();
            var mt = dt.getMonth() + 1;
            var day = dt.getDate();
            var h = dt.getHours(); //获取时
            var m = dt.getMinutes(); //获取分
            var s = dt.getSeconds(); //获取秒
            document.querySelector(".show-time").innerHTML =
                "当前时间：" +
                y +
                "年" +
                mt +
                "月" +
                day +
                "日-" +
                h +
                "时" +
                m +
                "分" +
                s +
                "秒";
            t = setTimeout(time, 1000); //设定定时器，循环运行
        }
    </script>
</header>

<!-- 页面主体 -->
<section class="mainbox">
    <!-- 左侧盒子 -->
    <div class="column">
        <div class="panel bar">
            <h2>柱形图-就业行业</h2>
            <!-- 图表放置盒子 -->
            <div class="chart"></div>
            <!-- 伪元素绘制盒子下边角 -->
            <div class="panel-footer"></div>
        </div>
        <div class="panel line">
            <h2>折线图-人员变化
                <a class="a-active" href="javascript:;">2020</a>
                <a href="javascript:;">2021</a>
            </h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
        <div class="panel pie">
            <h2>饼形图-年龄分布</h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
    </div>
    <!-- 中间盒子 -->
    <div class="column">
        <!-- 头部 no模块 -->
        <div class="no">
            <div class="no-hd">
                <ul>
                    <li>12434</li>
                    <li>10983</li>
                </ul>
            </div>
            <div class="no-bd">
                <ul>
                    <li>前端需求人数</li>
                    <li>市场供应人数</li>
                </ul>
            </div>
        </div>
        <!-- map模块 -->
        <div class="map">
            <div class="map1"></div>
            <div class="map2"></div>
            <div class="map3"></div>
            <div class="chart"></div>
        </div>
    </div>
    <!-- 右侧盒子 -->
    <div class="column">
        <div class="panel bar2">
            <h2>柱形图-就业行业</h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
        <div class="panel line2">
            <h2>折线图-播放量</h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
        <div class="panel pie2">
            <h2>饼形图-地区分布</h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
    </div>
</section>
<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}"/>
<script src="${pageContext.request.contextPath}/static/js/flexible.js"></script>
<script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<!-- 引入china.js 完成地图模块 -->
<script src="${pageContext.request.contextPath}/static/js/china.js"></script>
<script src="${pageContext.request.contextPath}/static/js/index.js"></script>
<script>
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
        var myChart = echarts.init(document.querySelector(".bar .chart"));
        var option;

        option = {
            color: ['#2f89cf'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            // 修改图表位置大小
            grid: {
                left: '0%',
                top: '10px',
                right: '0%',
                bottom: '4%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: xAxis,
                    axisTick: {
                        alignWithLabel: true
                    },
                    // 修改刻度标签，相关样式
                    axisLabel: {
                        color: "rgba(255,255,255,0.8)",
                        fontSize: 10,
                        interval: 0 , //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
                        rotate: 40,
                        show: false
                    },
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    // 修改刻度标签，相关样式
                    axisLabel: {
                        color: "rgba(255,255,255,0.6)",
                        fontSize: 12
                    },
                    // y轴样式修改
                    axisLine: {
                        lineStyle: {
                            color: "rgba(255,255,255,0.6)",
                            width: 2
                        }
                    },
                    // y轴分割线的颜色
                    splitLine: {
                        lineStyle: {
                            color: "rgba(255,255,255,0.1)"
                        }
                    }
                }
            ],
            series: [
                {
                    name: '全球票房',
                    type: 'bar',
                    barWidth: '35%',
                    data: yAxis,
                    itemStyle: {
                        // 修改柱子圆角
                        barBorderRadius: 5
                    }
                }
            ]
        };
        option && myChart.setOption(option);
        // 4.让图表随屏幕自适应
        window.addEventListener('resize', function () {
            myChart.resize();
        })
    })
</script>
</body>
</html>

