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
 			<small>我的代理</small>
 		</div>
	</div>
	
	<form method="post" id="form1" class="am-form">
	<div class="am-g">
		<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<a href="${ctx}/order/addAgent"><span class="am-input-group-label">新增</span></a>
			</div>
		</div>
		<div class="am-u-lg-5">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">账号/昵称</span>
				<input type="text" name="account" placeholder="请输入昵称或用户名" value="${account}" class="am-form-field"/>
			</div>
		</div>
		<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">状态</span>
				<select name="status" class="am-form-field">
		   			<option value="">请选择</option>
		   			<option value="正常" ${status == '正常' ? 'selected' : ''}>正常</option>
		   			<option value="禁用" ${status == '禁用' ? 'selected' : ''}>禁用</option>
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
						<th>昵称</th>
						<th>用户名</th>
						<th>APPID</th>
						<th>密钥</th>
						<th>状态</th>
						<th>费率</th>
						<th>余额</th>
						<th>登陆时间</th>
						<th>登陆IP</th>
						<th class="table-set">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="c" varStatus="i">
					<tr ${c.status != '正常' ? 'style="color:red;"' : ''}>
						<td>${page.pageNumber * 10 + i.index - 9}</td>
						<td>${c.nickname}</td>
						<td>${c.username}</td>
						<td>${c.appid}</td>
						<td>${c.md5key}</td>
						<td>${c.status}</td>
						<td>${c.rebate}</td>
						<td>${c.balance}</td>
						<td>${c.logintime}</td>
						<td>${c.loginip}</td>
						<td>
							<a href="${ctx}/order/editAgent/${c.id}" class="am-btn am-btn-secondary am-btn-xs">编辑</a>
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