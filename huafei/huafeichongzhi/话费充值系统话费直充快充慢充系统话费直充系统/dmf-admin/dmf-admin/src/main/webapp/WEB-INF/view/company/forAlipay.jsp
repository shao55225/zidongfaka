<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  function selectAlipay(t, id){
	  if (t.checked) {
		  $.get("${ctx}/company/addAlipay?cid=${cid}&aid=" + id);
	  } else {
		  $.get("${ctx}/company/removeAlipay?cid=${cid}&aid=" + id);
	  }
  }
  $(function(){
	  <c:forEach items="${caList}" var="c">
	  $("#ck_${c.alipay_id}").prop("checked", true);
	  </c:forEach>
  });
  </script>
</head>
<body>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>商户管理</small>&nbsp;/&nbsp;
 			<small>商户列表</small>&nbsp;/&nbsp;
 			<small>分配收款账号</small>
 		</div>
	</div>
	
	<div class="am-g" style="font-size:12px;">
		<div class="am-u-sm-12 am-scrollable-horizontal">
			<table class="am-table am-table-striped am-table-hover am-table-striped am-text-nowrap">
				<thead>
					<tr>
						<th>选择</th>
						<th>序号</th>
						<th>供应商</th>
						<th>支付宝账号</th>
						<th>最小金额</th>
						<th>最大金额</th>
						<th>当日限额</th>
						<th>费率</th>
						<th>状态</th>
						<th>账号类型</th>
						<th>上传时间</th>
						<th>是否老号</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${alipays}" var="c" varStatus="i">
					<tr ${c.status == '在线' ? 'style="color:red;"' : ''}>
						<td>
							<input type="checkbox" id="ck_${c.id}" onchange="selectAlipay(this, ${c.id});"/>
						</td>
						<td>${i.index + 1}</td>
						<td>${c.agent_name}</td>
						<td>${c.alipay_name}</td>
						<td>${c.min_money}</td>
						<td>${c.max_money}</td>
						<td>${c.full_money}</td>
						<td>${c.rebate}</td>
						<td>${c.status}</td>
						<td>${c.type}</td>
						<td>${c.addtime}</td>
						<td>${c.is_good == "Y" ? "无敌号" : "新号"}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>
	</div>
</div>

</body>
</html>