<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  function updateIp(id, t) {
	  var content = $(t).val();
	  $.post("${ctx}/system/updateIp", {
		  "id" : id,
		  "yzm" : content
	  }, function(data) {
		  alert("修改成功");
		  location.reload();
		  
	  });
  }
  </script>
</head>
<body>
<div class="am-cf admin-main">
	
	<div class="am-cf am-padding">

		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>系统设置</small>&nbsp;/&nbsp;
 			<small>长效ip设置</small>&nbsp;/&nbsp;
 			<small>
 			<a href="javascript:location.reload();" class="am-btn am-btn-success am-btn-xs">刷新</a>
 			</small>
 		</div>
	</div>
	
	<div class="am-g" style="margin-bottom:100px;">
		<div class="am-u-sm-12">
			<table class="am-table am-table-striped am-table-hover table-main">
				<thead>
					<tr>
						<th>序号</th>
						<th>省份</th>
						<th>ip(格式为ip:端口)</th>
						<!--  
						<th>操作</th>-->
					</tr>
				</thead>
				<tbody
				<c:forEach items="${page.list}" var="c" varStatus="i">
					<tr>
						<td>${page.pageNumber * 40 + i.index - 39}</td>
						<td>${c.phone}</td>
						<td>
							<input type="text" value="${c.yzm}" onchange="updateIp(${c.id}, this);" style="width:100%;"/>
						</td>
						
									<!--  
						<td><a href="${ctx}/system/editConfig/${c.id}" class="am-btn am-btn-secondary am-btn-xs">编辑</a></td>-->
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>