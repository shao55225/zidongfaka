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
 			<small>用户权限管理</small>&nbsp;/&nbsp;
 			<small>用户管理</small>&nbsp;/&nbsp;
 			<small>编辑用户</small>&nbsp;/&nbsp;
 			<small>${u.nickname}</small>
 		</div>
	</div>
	
	
	<div class="am-g">
		<div class="am-u-lg-12 am-u-md-12 am-u-sm-centered">
			<form class="am-form m-form-horizontal" action="${ctx}/user/updateUser" method="post">
				<div class="am-g am-margin-top">
					<label class="am-u-sm-3 am-text-right">昵称：</label>
					<div class="am-u-sm-6 am-form-group field">
						<input type="text" name="u.nickname" value="${u.nickname}" data-validate="required:昵称不能为空">
					</div>
					<div class="am-u-sm-3"></div>
				</div>
				<div class="am-g am-margin-top">
					<label class="am-u-sm-3 am-text-right">用户名：</label>
					<div class="am-u-sm-6 am-form-group field">
						<input type="text" name="u.username" value="${u.username}" data-validate="required:用户名不能为空">
					</div>
					<div class="am-u-sm-3"></div>
				</div>
				<div class="am-g am-margin-top">
					<label class="am-u-sm-3 am-text-right">密码：</label>
					<div class="am-u-sm-6 am-form-group field">
						<shiro:hasRole name="admin">
							<input type="password" name="u.password" value="${u.password}" data-validate="required:密码不能为空">
						</shiro:hasRole>
						<shiro:lacksRole name="admin">
							<input type="password" name="u.password" value="" placeholder="不修改密码时，请不要输入" />
						</shiro:lacksRole>
					</div>
					<div class="am-u-sm-3"></div>
				</div>
				<div class="am-g am-margin-top">
					<label class="am-u-sm-3 am-text-right">状态：</label>
					<div class="am-u-sm-3 am-form-group field">
						<select name="u.status" class="am-form-field">
				   			<option ${status == '正常' ? 'selected' : ''}>正常</option>
				   			<option ${status == '禁用' ? 'selected' : ''}>禁用</option>
				   		</select>
					</div>
					<div class="am-u-sm-6"></div>
				</div>
				
				<div class="am-g am-margin-top" style="margin-bottom:30px;">
					<div class="am-u-sm-9 am-u-sm-push-3">
						<input type="hidden" name="u.id" value="${u.id}"/>
						<input type="submit" class="am-btn am-btn-primary" value="确认保存" />&nbsp;
						<input type="reset" class="am-btn am-btn-danger" value="重置" />&nbsp;
						<a class="am-btn am-btn-success" href="javascript:history.go(-1);">返回</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>
