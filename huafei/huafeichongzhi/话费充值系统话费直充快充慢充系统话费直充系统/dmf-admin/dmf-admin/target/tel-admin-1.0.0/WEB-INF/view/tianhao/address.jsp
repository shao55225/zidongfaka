<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
</head>
<body>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>订单中心</small>&nbsp;/&nbsp;
 			<small>地址管理</small>
 		</div>
	</div>
	
	<div class="am-g" style="font-size:12px;">
		<div class="am-u-sm-12 am-scrollable-horizontal">
			<a href="${ctx}/tianhao/add" class="am-btn am-btn-primary">添加地址</a>
		</div>
		<div class="am-u-sm-12 am-scrollable-horizontal">
			<table class="am-table am-table-striped am-table-hover am-table-striped am-text-nowrap">
				<thead>
					<tr>
						<th>序号</th>
						<th>昵称</th>
						<th>接口地址</th>
						<th>备注</th>
						<th class="table-set">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${items}" var="t" varStatus="i">
					<tr>
						<td>${i.index + 1}</td>
						<td>${t.nickname}</td>
						<td>${t.url}</td>
						<td>${t.remark}</td>
						<td>
							<a href="${ctx}/tianhao/edit/${t.id}" class="am-btn am-btn-secondary am-btn-xs">编辑</a>
							<a href="javascript:deleteItem('${ctx}/tianhao/delete/${t.id}');" class="am-btn am-btn-danger am-btn-xs">删除</a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>
	</div>
</div>
</body>
</html>