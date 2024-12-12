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
 			<small>提现管理</small>&nbsp;/&nbsp;
 			<small>申请提现</small>
 		</div>
	</div>
	
	
	<div class="am-g">
		<div class="am-u-lg-12 am-u-md-12 am-u-sm-centered">
			<form class="am-form m-form-horizontal" action="${ctx}/finance/saveDraw" method="post">
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">提现金额：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="d.draw_money" data-validate="required:提现金额不能为空">
					</div>
					<label class="am-u-sm-2 am-text-right">提现方式：</label>
					<div class="am-u-sm-3 am-form-group field">
						<select name="d.type" class="am-form-field">
							<option selected="selected">银行卡</option>
				   		</select>
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">账户姓名：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="d.realname" data-validate="required:姓名不能为空">
					</div>
					<label class="am-u-sm-2 am-text-right">提现账号：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="d.account" data-validate="required:账号不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">开户行：</label>
					<div class="am-u-sm-8 am-form-group field">
						<input type="text" name="d.open_bank">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<!--  
				<div class="am-g am-margin-top" style="margin-bottom:30px;">
					<div class="am-u-sm-10 am-u-sm-push-2">
						<strong style="color:red;">
							每次转账&lt;20000收10元，&gt;=20000并&lt;=50000收25元一笔。如：提现69999收35元
						</strong>
					</div>
				</div>
				-->
				<div class="am-g am-margin-top" style="margin-bottom:30px;">
					<div class="am-u-sm-2 am-u-sm-push-2">
						当前余额：${balance }
					</div>
					<div class="am-u-sm-8">
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
