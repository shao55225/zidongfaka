<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  </script>
</head>
<body>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>用户权限管理</small>&nbsp;/&nbsp;
 			<small>用户管理</small>
 		</div>
	</div>
	
	<form method="post" id="form1" class="am-form">
	<div class="am-g">
		<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<a href="${ctx}/user/addUser"><span class="am-input-group-label">新增</span></a>
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
	
	
	<div class="am-g" style="margin-bottom:100px;">
		<div class="am-u-sm-12">
			<table class="am-table am-table-striped am-table-hover table-main">
				<thead>
					<tr>
						<th>序号</th>
						<th>昵称</th>
						<th>用户名</th>
						<th>密码</th>
						<th>状态</th>
						<th>登陆时间</th>
						<th>登陆IP</th>
						<th class="table-set">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="u" varStatus="i">
					<tr ${u.status != '正常' ? 'style="color:red;"' : ''}>
						<td>${i.index + 1}</td>
						<td>${u.nickname}</td>
						<td>${u.username}</td>
						<td>
							<shiro:hasRole name="admin">${u.password}</shiro:hasRole>
							<shiro:lacksRole name="admin">******</shiro:lacksRole>
						</td>
						<td>${u.status}</td>
						<td>${u.logintime}</td>
						<td>${u.loginip}</td>
						
						
						<td>
							<div class="am-dropdown" data-am-dropdown>
								<button class="am-btn am-btn-default am-btn-xs am-dropdown-toggle" data-am-dropdown-toggle><span class="am-icon-cog"></span> <span class="am-icon-caret-down"></span></button>
								<ul class="am-dropdown-content">
									<li><a href="${ctx}/user/authUser/${u.id}">设置权限</a></li>
									<li><a href="${ctx}/user/editUser/${u.id}">编辑</a></li>
									<li><a href="javascript:deleteItem('${ctx}/user/deleteUser/${u.id}');">删除</a></li>
								</ul>
							</div>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>