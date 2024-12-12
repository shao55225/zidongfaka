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
	<form method="post" action="${pageContext.request.contextPath}/taobao/addTask">
		任务名称：<input name="taskName" />
		<button type="submit">提交</button>
	</form>
	<hr />
	<table style="width:100%;" >
		<tr>
			<th>任务名称</th>
			<th>新增时间</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${items}" var="t" varStatus="i">
		<tr>
			<td>${t.task_name}</td>
			<td>${t.addtime}</td>
			<td>${t.status}</td>
			<td>
				<c:if test="${t.status == '待启动'}">
					<a href="start?id=${t.id}">启动任务</a>
				</c:if>
				
				<c:if test="${t.status == '运行中'}">
					<a href="stop?id=${t.id}">停止任务</a>
				</c:if>
				
				<a href="items?id=${t.id}">明细数据</a>
				
				<a href="delTask?id=${t.id}">删除</a>
			</td>
		</tr>
		</c:forEach>
	</table>
	

</body>
</html>