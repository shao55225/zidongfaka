<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  function viewOrder(id) {
	  var iWidth = 960;
	  var iHeight = 640;
	  var iTop = (window.screen.height-30-iHeight)/2;
	  var iLeft = (window.screen.width-10-iWidth)/2;
	  window.open('${ctx}/order/viewOrder/' + id, '订单详情', 'width='+iWidth+',height='+iHeight+',top='+iTop+',left='+iLeft+',menubar=no,toolbar=no,location=no,scrollbars=no,status=no,modal=yes');
  }
  </script>
</head>
<body>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>订单中心</small>&nbsp;/&nbsp;
 			<small>代理订单【${c.nickname }】</small>
 		</div>
	</div>
	
	<form method="post" id="form1" class="am-form">
	<div class="am-g">
		<div class="am-u-lg-3">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">日期</span>
				<input type="text" name="date" value="${date}" placeholder="请选择日期" class="am-form-field" data-am-datepicker/>
			</div>
		</div>
		<div class="am-u-lg-3">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">订单号</span>
				<input type="text" name="orderNo" placeholder="请输入订单号" value="${orderNo}" class="am-form-field"/>
			</div>
		</div>
		<div class="am-u-lg-4">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">状态</span>
				<select name="status" style="float:left;width:100px;">
		   			<option value="">支付状态</option>
		   			<option value="已支付" ${status == '已支付' ? 'selected' : ''}>已支付</option>
		   			<option value="未支付" ${status == '未支付' ? 'selected' : ''}>未支付</option>
		   		</select>
		   		<select name="settle" style="float:left;width:100px;">
		   			<option value="">分账状态</option>
		   			<option value="分账成功" ${settle == '分账成功' ? 'selected' : ''}>分账成功</option>
		   			<option value="分账失败" ${settle == '分账失败' ? 'selected' : ''}>分账失败</option>
		   			<option value="未分账" ${settle == '未分账' ? 'selected' : ''}>未分账</option>
		   			<option value="分账中" ${settle == '分账中' ? 'selected' : ''}>分账中</option>
		   			
		   		</select>
		   		<select name="notice" style="float:left;width:100px;">
		   			<option value="">回调状态</option>
		   			<option value="回调成功" ${notice == '回调成功' ? 'selected' : ''}>回调成功</option>
		   			<option value="回调失败" ${notice == '回调失败' ? 'selected' : ''}>回调失败</option>
		   			<option value="未回调" ${notice == '未回调' ? 'selected' : ''}>未回调</option>
		   			<option value="回调中" ${notice == '回调中' ? 'selected' : ''}>回调中</option>
		   		</select>
			</div>
		</div>
		<div class="am-u-lg-2">
			<div class="am-input-group ">
				<input class="am-btn am-btn-primary" type="submit" value="搜索"/>
				&nbsp;
				<input class="am-btn am-btn-success" type="button" value="刷新" onclick="location.reload();"/>
			</div>
		</div>
	</div>
	</form>
	
	
	<div class="am-g" style="font-size:12px;">
		<div class="am-u-sm-12 am-scrollable-horizontal">
			<table class="am-table am-table-striped am-table-hover am-table-striped am-text-nowrap">
				<thead>
					<tr>
						<th>序号</th>
						<th>订单号</th>
						<th>商户订单号</th>
						<th>交易号</th>
						<th>订单金额</th>
						<th>服务费</th>
						<th>支付状态</th>
						<th>分账状态</th>
						<th>回调状态</th>
						<th>下单时间</th>
						<th>支付时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="o" varStatus="i">
					<tr ${u.status == '已拒绝' ? 'style="color:red;"' : ''}>
						<td>${page.pageNumber * 10 + i.index - 9}</td>
						<td>${o.order_no}</td>
						<td>${o.out_order_no}</td>
						<td>${o.trade_no}</td>
						<td>${o.order_money}</td>
						<td>${o.company_rebate_money}</td>
						<td>
							${o.status}
							<c:if test="${o.status == '未支付'}">
							<a href="javascript:reloadItem(${o.id});"><i class="am-icon-refresh"></i></a>
							</c:if>
						</td>
						<td>
							${o.settle}
							<c:if test="${o.settle == '分账失败'}">
							<a href="javascript:reloadItem(${o.id});"><i class="am-icon-refresh"></i></a>
							</c:if>
						</td>
						<td>
							${o.notice}
							<c:if test="${o.notice == '回调成功' or o.notice == '回调失败'}">
							<a href="javascript:reloadItem(${o.id});"><i class="am-icon-refresh"></i></a>
							</c:if>
						</td>
						<td>${o.addtime}</td>
						<td>${o.paytime}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div class="am-cf">
				共 ${page.totalRow } 条记录<span class="tooltip"></span>
				<script type="text/javascript">
					function gridPage(pageNumber) {
						$("#pageNumber").val(pageNumber);
						$("#pageForm").submit();
					}
				</script>
				<form id="pageForm" style="display:none;" method="post">
					<input name="orderNo" value="${orderNo}"/>
					<input name="status" value="${status}"/>
					<input name="date" value="${date}"/>
					<input id="pageNumber" name="page" value="${page.pageNumber}" />
				</form>
				
				<c:if test="${page.totalPage > 1}">
				<div class="am-fr">
					<ul class="am-pagination ">
						<li class="am-pagination-first"><a href="javascript:gridPage(1);">&lt;&lt;</a></li>
						<c:forEach var="i" begin="${page.pageNumber - 7 < 1 ? 1 : page.pageNumber - 7}" end="${page.pageNumber + 7 > page.totalPage ? page.totalPage : page.pageNumber + 7}">
						<c:if test="${page.pageNumber == i}"><li class="am-active"><a class="am-active" href="javascript:void(0);">${i}</a></li></c:if>
						<c:if test="${page.pageNumber != i}"><li><a href="javascript:gridPage(${i});">${i}</a></li></c:if>
						</c:forEach>
						<li class="am-pagination-last"><a href="javascript:gridPage(${page.totalPage});">&gt;&gt;</a></li>
					</ul>
				</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
</body>
</html>