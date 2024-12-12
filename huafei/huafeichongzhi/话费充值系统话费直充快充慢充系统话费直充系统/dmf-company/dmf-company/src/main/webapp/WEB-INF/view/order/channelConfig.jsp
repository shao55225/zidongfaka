<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>

</head>
<body>

<script type="text/javascript">

</script>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>通道配置</small>&nbsp;/&nbsp;
 		</div>
	</div>
	
	
	<div class="am-g" style="font-size:12px;">
		<div class="am-u-sm-12 am-scrollable-horizontal">
			<table class="am-table am-table-striped am-table-hover am-table-striped am-text-nowrap">
				<thead>
					<tr>
						<th>通道类型</th>
						<th>通道昵称</th>
						<th>通道代码</th>
						<th>费率</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page}" var="c" varStatus="i">
					<tr ${c.status != '正常' ? 'style="color:red;"' : ''}>
					
						<td>
							${c.type}
						</td>
						
						<td>
							${c.nickname}
							
						</td>
						
						<td>${c.code} </td>
						
						<td>${c.rebate}</td>
						
						<td>${c.status}</td>
							
						
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<input type="hidden" value="${c.id}" />
	
</div>

	<div class="am-g am-margin-top" style="margin-bottom:30px;">
		
					<div class="am-u-sm-8 am-u-sm-push-4">
					
						<input type="hidden" name="c.id" value="${c.id}" />
						
						<!--
						<input type="submit" class="am-btn am-btn-primary"  onclick="test();" value="确认保存" />&nbsp; 
						 -->
						<a class="am-btn am-btn-success" onclick="javascript:history.go(-1);">返回</a>
						
				</div>
	 </div>

</body>
				
</html>