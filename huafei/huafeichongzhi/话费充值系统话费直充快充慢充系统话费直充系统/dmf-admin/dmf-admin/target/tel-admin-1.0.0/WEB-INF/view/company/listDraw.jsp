<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  function viewDraw(id) {
	  var iWidth = 960;
	  var iHeight = 640;
	  var iTop = (window.screen.height-30-iHeight)/2;
	  var iLeft = (window.screen.width-10-iWidth)/2;
	  window.open('${ctx}/company/viewDraw/' + id, '转账详情', 'width='+iWidth+',height='+iHeight+',top='+iTop+',left='+iLeft+',menubar=no,toolbar=no,location=no,scrollbars=no,status=no,modal=yes');
  }
  </script>
</head>
<body>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>商户管理</small>&nbsp;/&nbsp;
 			<small>商户提现</small>
 		</div>
	</div>
	
	<form method="post" id="form1" class="am-form">
			<div class="am-g">
				<div class="am-u-lg-5">
					<div class="am-input-group am-input-group-secondary am-form-group">
						<span class="am-input-group-label">商户名/姓名/账号</span> <input
							type="text" name="account" placeholder="请输入商户名/姓名/账号"
							value="${account}" class="am-form-field" />
					</div>
				</div>
				<div class="am-u-lg-2">
					<div class="am-input-group am-input-group-secondary am-form-group">
						<span class="am-input-group-label">状态</span> <select name="status"
							class="am-form-field">
							<option value="">请选择</option>
							<option value="待审核" ${status == '待审核' ? 'selected' : ''}>待审核</option>
							<option value="已审核" ${status == '已审核' ? 'selected' : ''}>已审核</option>
							<option value="已拒绝" ${status == '已拒绝' ? 'selected' : ''}>已拒绝</option>
						</select>
					</div>
				</div>
				<div class="am-u-lg-2">
					<div class="am-form-group am-form-icon">
						<i class="am-icon-calendar"></i> <input type="text" name="addtime"
							class="am-form-field" placeholder="日期"
							data-am-datepicker="{theme: 'success'}" value="${addtime}">
					</div>
				</div>
				<div class="am-u-lg-2">
					<div class="am-input-group ">
						<input class="am-btn am-btn-primary" type="submit" value="搜索" />
						&nbsp; <input class="am-btn am-btn-success" type="button"
							value="刷新" onclick="location.reload();" />
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
						<th>商户名称</th>
						<th>提现金额</th>
						<th>手续费</th>
						<th>实发金额</th>
						<th>提现方式</th>
						<th>姓名</th>
						<th>账号</th>
						<th>开户行</th>
						<th>状态</th>
						<th>申请时间</th>
						<th>结束时间</th>
						<th>操作员</th>
						<th class="table-set">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="d" varStatus="i">
					<tr ${u.status == '已拒绝' ? 'style="color:red;"' : ''}>
						<td>${page.pageNumber * 10 + i.index - 9}</td>
						<td>${d.company_name}</td>
						<td>${d.draw_money}</td>
						<td>${d.charge_money}</td>
						<td><strong>${d.draw_money - d.charge_money}</strong></td>
						<td>${d.type}</td>
						<c:if test="${d.sys_username == sysUser}">
						<td>${d.realname}</td>
						<td>${d.account}</td>
						<td>${d.open_bank}</td>
						</c:if>
						<c:if test="${d.sys_username != sysUser}">
						<td>***</td>
						<td>******</td>
						<td>******</td>
						</c:if>
						<td>${d.status}</td>
						<td>${d.addtime}</td>
						<td>${d.drawtime}</td>
						<td>${d.sys_username}</td>
						<td>
							<a href="javascript:viewDraw(${d.id});" class="am-btn am-btn-secondary am-btn-xs">详情</a>
						</td>
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
					<input name="addtime" value="${addtime}"/>
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