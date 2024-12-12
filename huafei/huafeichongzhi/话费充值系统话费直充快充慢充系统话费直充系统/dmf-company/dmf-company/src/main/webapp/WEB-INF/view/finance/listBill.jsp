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
 			<small>财务中心</small>&nbsp;/&nbsp;
 			<small>我的账单</small>
 		</div>
	</div>
	
	<form method="post" id="form1" class="am-form">
	<div class="am-g">
	<!-- 
		<div class="am-u-lg-3">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">类型</span>
				<select name="type" class="am-form-field">
		   			<option value="">请选择</option>
		   			<option value="订单收款" ${type == '订单收款' ? 'selected' : ''}>订单收款</option>
		   			<option value="代理分红" ${type == '代理分红' ? 'selected' : ''}>代理分红</option>
		   			<option value="申请提现" ${type == '申请提现' ? 'selected' : ''}>申请提现</option>
		   			<option value="提现驳回" ${type == '提现驳回' ? 'selected' : ''}>提现驳回</option>
		   		</select>
			</div>
		</div>
		 -->
		<div class="am-u-lg-4">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">订单号</span>
				<input type="text" name="orderNo" placeholder="订单号" value="${orderNo}" class="am-form-field"/>
			</div>
		</div>
		<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">状态</span>
				<select name="status" class="am-form-field">
		   			<option value="">请选择</option>
		   			<option value="核算中" ${status == '核算中' ? 'selected' : ''}>核算中</option>
		   			<option value="已结算" ${status == '已结算' ? 'selected' : ''}>已结算</option>
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
						<th>状态</th>
						<th>类型</th>
						<th>订单金额</th>
						<th>入账</th>
						<th>余额</th>
						<th>备注</th>
						<th>时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="b" varStatus="i">
					<tr>
						<td>${page.pageNumber * 30 + i.index - 29}</td>
						<td>${b.order_no}</td>
						<td>${b.status}</td>
						<td>${b.type}</td>
						<td>${b.order_money}</td>
						<td>${b.in_money}</td>
						<td>${b.balance}</td>
						<td>${b.remark}</td>
						<td>${b.addtime}</td>
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
					<input name="type" value="${type}"/>
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