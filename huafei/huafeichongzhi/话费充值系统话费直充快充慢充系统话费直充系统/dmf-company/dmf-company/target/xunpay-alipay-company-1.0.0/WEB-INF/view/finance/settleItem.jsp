<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  function queryPid() {
	  $.get("${ctx}/finance/createQr", function(data){
		  if (data.result == "success") {
			  $("#doc-qrcode").empty().qrcode(data.data.qrCode);
			  $('#doc-modal-1').modal();
			  queryOrder(data.data.orderNo);
		  }
	  });
  }
  
  function queryOrder(orderNo) {
	  $.get("${ctx}/finance/queryOrder?orderNo=" + orderNo, function(data) {
		  if (data.result == "success") {
			  if (data.data.buyerPid != null && data.data.buyerPid != "") {
				  $("#myPid").html("扫码支付宝的PID:" + data.data.buyerPid);
			  } else {
				  setTimeout(function(){
					  queryOrder(orderNo);
				  }, 3000);
			  }
		  }
	  });
  }
  </script>
</head>
<body>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>财务中心</small>&nbsp;/&nbsp;
 			<small>收款支付宝</small>
 		</div>
	</div>
	
	<div class="am-g">
		<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<a href="${ctx}/finance/addSettleItem"><span class="am-input-group-label">添加收款支付宝</span></a>
			</div>
		</div>
		<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-success am-form-group">
				<a href="javascript:queryPid();"><span class="am-input-group-label">查询PID</span></a>
			</div>
		</div>
		<div class="am-u-lg-7">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<strong style="color:red;">未设置收款支付宝时，会自动转入到余额中</strong>
			</div>
		</div>
		<div class="am-u-lg-1">
			<div class="am-input-group ">
				<input class="am-btn am-btn-success" type="button" value="刷新" onclick="location.reload();"/>
			</div>
		</div>
	</div>
	
	
	<div class="am-g" style="font-size:12px;">
		<div class="am-u-sm-12 am-scrollable-horizontal">
			<table class="am-table am-table-striped am-table-hover am-table-striped am-text-nowrap">
				<thead>
					<tr>
						<th>序号</th>
						<th>支付宝账号</th>
						<th>PID</th>
						<th>状态</th>
						<th>转账次数</th>
						<th>总计金额</th>
						<th>今日金额</th>
						<th>最大金额</th>
						<th>添加时间</th>
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ss}" var="s" varStatus="i">
					<tr ${s.status == '禁用' ? 'style="color:#dd514c;font-weight:bold;"' : ''}>
						<td>${i.index + 1}</td>
						<td>${s.alipay_account}</td>
						<td>${s.pid}</td>
						<td>${s.status}</td>
						<td>${s.settle_num}</td>
						<td>${s.total_money}</td>
						<td>${s.today_money}</td>
						<td>${s.max_money}</td>
						<td>${s.addtime}</td>
						<td>${s.remark}</td>
						<td>
							<a href="${ctx}/finance/editSettleItem/${s.id}" class="am-btn am-btn-secondary am-btn-xs">编辑</a>
							<a href="javascript:deleteItem('${ctx}/finance/deleteSettleItem/${s.id}');" class="am-btn am-btn-danger am-btn-xs">删除</a>
							
							<c:if test="${s.status == '正常'}">
							<a href="${ctx}/finance/downSettleItem/${s.id}" class="am-btn am-btn-warning am-btn-xs">禁用</a>
							</c:if>
							
							<c:if test="${s.status == '禁用'}">
							<a href="${ctx}/finance/upSettleItem/${s.id}" class="am-btn am-btn-success am-btn-xs">启用</a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>
	</div>
</div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1" style="top:30px;">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">扫码获取PID
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
    	<div style="margin:0px auto;" id="doc-qrcode"></div>
    	<strong id="myPid" style="color:red;">扫描二维码获取支付宝的PID</strong>
    </div>
  </div>
</div>

</body>
</html>