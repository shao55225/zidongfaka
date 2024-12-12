<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<title>产马任务</title>
	<style type="text/css">td {text-align: center;}</style>
</head>
<body>
	<form method="post" action="${pageContext.request.contextPath}/taobao/addItem">
		店铺名称：
		<input name="shop_name" />&nbsp;&nbsp;
		商品名称：
		<input name="good_name" />&nbsp;&nbsp;
		订单数量：
		<input name="num" />&nbsp;&nbsp;
		<input name="task_id" type="hidden" value="${tid}"/>
		<button type="submit">提交</button>
		
		<button type="button" onclick="javascript:location.href='${pageContext.request.contextPath}/taobao/list'">返回任务列表 </button>
	</form>
	
	<hr />
	
	<table style="width:100%;" >
		<tr>
			<th>店铺名称</th>
			<th>商品名称</th>
			<th>订单数量</th>
			<th>读取数量</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${items}" var="t" varStatus="i">
		<tr>
			<td>${t.shop_name}</td>
			<td>${t.good_name}</td>
			<td>${t.num}</td>
			<td>${t.read_num}</td>
			<td>
				<a href="delItem?id=${t.id}">删除</a>
			</td>
		</tr>
		</c:forEach>
	</table>
	
	<hr />
	<table style="width:100%;" >
		<tr>
			<th colspan="5" style="background: #ccc;">已产码订单</th>
		</tr>
		<tr>
			<th>订单号</th>
			<th>交易号</th>
			<th>订单金额</th>
			<th>二维码地址</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${orders}" var="o" varStatus="i">
		<tr>
			<td>${o.order_no}</td>
			<td>${o.trade_no}</td>
			<td>${o.amount}</td>
			<td>${o.qr_code}</td>
			<td>
				<a href="delOrder?id=${o.id}">删除</a>
			</td>
		</tr>
		</c:forEach>
	</table>

</body>
</html>