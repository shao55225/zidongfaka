<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <title>订单详情 - ${d.company_name}</title>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  function reload(id) {
	  $.get("http://47.112.31.186/system/settleItem/" + id, function(data){
	  });
	  location.reload();
  }
  </script>
</head>


<body>

<div class="am-cf admin-main" style="font-size:12px;">

	<fieldset>
		<legend>订单信息</legend>
		<div class="am-g">
		  <div class="am-u-sm-2">订单号：</div>
		  <div class="am-u-sm-4">${o.order_no}</div>
		  <div class="am-u-sm-2">商户订单号：</div>
		  <div class="am-u-sm-4">${o.out_order_no}</div>
		</div>
		
		<div class="am-g">
		  <div class="am-u-sm-2">订单金额：</div>
		  <div class="am-u-sm-4">${o.order_money}</div>
		  
		  <div class="am-u-sm-2">商户名：</div>
		  <div class="am-u-sm-4">${o.company_name}</div>
		  
		</div>
		

		

		
		<div class="am-g">
		  <div class="am-u-sm-2">回调地址：</div>
		  <div class="am-u-sm-10">${o.notice_url}</div>
		</div>
		
		<div class="am-g">
		  <div class="am-u-sm-2">申请时间：</div>
		  <div class="am-u-sm-4">${o.addtime}</div>
		</div>

		

		
		<div class="am-g">
		  <div class="am-u-sm-2">
		  	支付状态：
		  	<c:if test="${o.status == '未支付'}">
		  	<script type="text/javascript">
		  	function repairPay() {
		  		if (confirm("确认补单操作吗？补单后￥${o.order_money - o.company_rebate_money}会到客户的余额中")) {
		  			$("#repairForm")[0].submit();
		  		}
		  	}
		  	</script>
		  	<form id="repairForm" style="display:none;" action="${ctx}/order/repairPay" method="post">
		  		<input name="id" value="${o.id}">
		  		<input id="tradeNo" name="tradeNo" value="">
		  	</form>
		  	<button onclick="repairPay();">补单</button>
		  	</c:if>
		  </div>
		  <div class="am-u-sm-4">${o.status}</div>
		  <div class="am-u-sm-2">回调状态：</div>
		  <div class="am-u-sm-4">${o.notice}</div>
		</div>
		
		<div class="am-g">
		  <div class="am-u-sm-2">支付凭证：</div>
		  <div class="am-u-sm-4">${o.token}</div>
		      <div class="am-u-sm-2"></div>
		  <div class="am-u-sm-4"></div>
		  
		  <div class="am-u-sm-2">查单链接：</div>
		  <div class="am-u-sm-4">${o.return_url}</div>
		      <div class="am-u-sm-2"></div>
		  <div class="am-u-sm-4"></div>
		</div>
		<!--
		<div class="am-g">
		  <div class="am-u-sm-2">
		          分账状态：
		  	<c:if test="${o.settle == '分账失败'}">
		  	<script type="text/javascript">
		  	function platformPay() {
		  		if (confirm("确认操作吗？垫付后￥${o.order_money - o.company_rebate_money}会到客户的余额中")) {
		  			location.href = "${ctx}/order/platformPay/${o.id}";
		  		}
		  	}
		  	function notPay() {
		  		if (confirm("确认操作吗？操作后用户将直接改为分账成功")) {
		  			location.href = "${ctx}/order/notPay/${o.id}";
		  		}
		  	}
		  	</script>
		  	<button onclick="platformPay();">平台垫付</button>
		  	<button onclick="notPay();">不用垫付</button>
		  	</c:if>
		  </div>
		  <div class="am-u-sm-4">${o.settle}-${o.settleresult}</div>
		  <div class="am-u-sm-2">结算状态：</div>
		  <div class="am-u-sm-4">${o.is_bill == 'Y' ? "已结算" : "未结算"}</div>
		</div>
		
		
		
		<div class="am-g">
		  <div class="am-u-sm-2">
		          订单备注：
		  </div>
		  <div class="am-u-sm-10"></div>
		</div>  -->
	</fieldset>
	<!--
	<div class="am-tabs" data-am-tabs="{noSwipe: 1}" id="doc-my-tabs">
	  <ul class="am-tabs-nav am-nav am-nav-tabs am-nav-justify">
	    <li class="am-active"><a href="">分账信息</a></li>
	    <li><a href="">商户账单</a></li>
	    <li><a href="">供应商账单</a></li>
	  </ul>
	  <div class="am-tabs-bd">
	    <div class="am-tab-panel am-active">
	    	<table class="am-table">
				<tr>
					<th>订单号</th>
					<th>订单金额</th>
					<th>出账PID</th>
					<th>入账PID</th>
					<th>入账支付宝</th>
					<th>分账金额</th>
					<th>类型</th>
					<th>备注</th>
				</tr>
	    		<c:forEach items="${settles}" var="s">
	    		<tr>
					<td>${s.order_no}</td>
					<td>${s.order_money}</td>
					<td>${s.trans_out}</td>
					<td>${s.trans_in}</td>
					<td>${s.trans_in_account}</td>
					<td>${s.settle_money}</td>
					<td>${s.type}</td>
					<th>
						${s.remark}
						<c:if test="${s.remark == '分账失败'}">
							<button onclick="reload(${s.id});">重分</button>
						</c:if>
					</th>
				</tr>
	    		</c:forEach>
	    	</table>
	    </div>
	    <div class="am-tab-panel">
	    	<table class="am-table">
				<tr>
					<th>商户名</th>
					<th>税率</th>
					<th>税费</th>
					<th>入账</th>
					<th>余额</th>
					<th>类型</th>
					<th>状态</th>
					<th>生成时间</th>
					<th>备注</th>
				</tr>
	    		<c:forEach items="${bills}" var="b">
	    		<tr>
					<td>${b.company_name}</td>
					<td>${b.tax}</td>
					<td>${b.tax_money}</td>
					<td>${b.in_money}</td>
					<td>${b.balance}</td>
					<td>${b.type}</td>
					<td>${b.status}</td>
					<td>${b.addtime}</td>
					<td>${b.remark}</td>
				</tr>
	    		</c:forEach>
	    	</table>
	    </div>
	    <div class="am-tab-panel">
	    	<table class="am-table">
				<tr>
					<th>供应商</th>
					<th>订单金额</th>
					<th>返点</th>
					<th>入账</th>
					<th>余额</th>
					<th>类型</th>
					<th>状态</th>
					<th>生成时间</th>
					<th>备注</th>
				</tr>
	    		<c:forEach items="${agentBills}" var="b">
	    		<tr>
					<td>${b.agent_name}</td>
					<td>${b.order_money}</td>
					<td>${b.rebate}</td>
					<td>${b.rebate_money}</td>
					<td>${b.balance}</td>
					<td>${b.type}</td>
					<td>${b.status}</td>
					<td>${b.addtime}</td>
					<td>${b.remark}</td>
				</tr>
	    		</c:forEach>
	    	</table>
	    </div>
	  </div>
	</div>
  -->
</div>
</body>
</html>
