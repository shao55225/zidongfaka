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
 			<small>提现管理</small>
 		</div>
	</div>
	
	<form method="post" id="form1" class="am-form">
	<div class="am-g">
		<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<a href="${ctx}/finance/addDraw"><span class="am-input-group-label">申请提现</span></a>
			</div>
		</div>
		<div class="am-u-lg-5">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">提现姓名/账号</span>
				<input type="text" name="account" placeholder="请输入提现姓名/账号" value="${account}" class="am-form-field"/>
			</div>
		</div>
		<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">状态</span>
				<select name="status" class="am-form-field">
		   			<option value="">请选择</option>
		   			<option value="核算中" ${status == '核算中' ? 'selected' : ''}>待审批</option>
		   			<option value="已完成" ${status == '已完成' ? 'selected' : ''}>已完成</option>
		   			<option value="已拒绝" ${status == '已拒绝' ? 'selected' : ''}>已拒绝</option>
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
						<th>姓名</th>
						<th>提现方式</th>
						<th>提现账户</th>
						<th>开户行</th>
						<th>提现金额</th>
						<th>手续费</th>
						<th>状态</th>
						<th>备注</th>
						<th>申请时间</th>
						<th>提现时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="d" varStatus="i">
					<tr ${d.status == '已拒绝' ? 'style="color:#dd514c;font-weight:bold;"' : ''}>
						<td>${page.pageNumber * 10 + i.index - 9}</td>
						<td>${d.realname}</td>
						<td>${d.type}</td>
						<td>${d.account}</td>
						<td>${d.open_bank}</td>
						<td>${d.draw_money}</td>
						<td>${d.charge_money}</td>
						<td>${d.status}</td>
						<td>${d.remark}</td>
						<td>${d.addtime}</td>
						<td>${d.drawtime}</td>
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
					<input name="account" value="${account}"/>
					<input name="status" value="${status}"/>
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