<!DOCTYPE html>
<!-- saved from url=(0079)http://report.kt762.cn:8807/report/amountCount/6844981a64b4485caffcf45e24700f01 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="${pageContext.request.contextPath}/resource/js/echarts/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/echarts/echarts.min.js"></script>
</head>
<body>

<div id="WXH5" style="width: 500px; height: 300px; margin-left: 20px; -webkit-tap-highlight-color: transparent; user-select: none; position: relative; background: transparent;" _echarts_instance_="ec_1586575799482"><div style="position: relative; overflow: hidden; width: 500px; height: 300px;"><canvas width="500" height="300" data-zr-dom-id="zr_0" style="position: absolute; left: 0px; top: 0px; width: 500px; height: 300px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas></div><div></div></div>

	  
<script>

var myChart2 = echarts.init(document.getElementById('WXH5'));

function loadData() {
	
$.ajax({
	url:"${ctx}/api/order/getWxData",
	type:'get',
	dataType:'json',
	success:function(dataMap) {
		   
	    var option2 = {
		        title: {
		            text: 'WXH5库存数量'
		        },
		        tooltip: {},
		        legend: {
		            data:['WXH5库存数量']
		        },
		        xAxis: {
		        	   data: ['10', '20', '30', '50', '100', '200', '300','500'],
		            //
		            name:'面额'
		        },
		        yAxis: {name:'库存数量'},
		        series: [{
		            name: '库存数量',
		            type: 'bar',
		            data: dataMap.countData,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'inside'
		                }
		            },
		        }]
		    };
	    


		    myChart2.setOption(option2);
	
	},
	error:function(data) {
		alert('系统错误，请稍后再试');
	}
});
}
loadData();
setInterval(function(){loadData();}, 5000);
</script></body></html>