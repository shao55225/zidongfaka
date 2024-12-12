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
 			<small>地址管理</small>&nbsp;/&nbsp;
 			<small>编辑地址</small>
 		</div>
	</div>
	
	
	<div class="am-g">
		<div class="am-u-lg-12 am-u-md-12 am-u-sm-centered">
			<form class="am-form m-form-horizontal" action="${ctx}/tianhao/update" method="post">
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">昵称：</label>
					<div class="am-u-sm-9 am-form-group field">
						<input type="text" name="t.nickname" value="${t.nickname }" data-validate="required:昵称不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">地址：</label>
					<div class="am-u-sm-9 am-form-group field">
						<input type="text" name="t.url" value="${t.url }" data-validate="required:地址不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">备注信息：</label>
					<div class="am-u-sm-9 am-form-group field">
						<input type="text" name="t.remark" value="${t.remark }">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top" style="margin-bottom:30px;">
					<div class="am-u-sm-2 am-u-sm-push-2"></div>
					<div class="am-u-sm-8">
						<input type="hidden" name="t.id" value="${t.id }">
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
