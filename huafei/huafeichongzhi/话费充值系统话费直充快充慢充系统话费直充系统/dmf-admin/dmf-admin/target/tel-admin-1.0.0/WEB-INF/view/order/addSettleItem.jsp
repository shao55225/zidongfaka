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
 			<small>收款支付宝</small>&nbsp;/&nbsp;
 			<small>添加收款支付宝</small>
 		</div>
	</div>
	
	
	<div class="am-g">
		<div class="am-u-lg-12 am-u-md-12 am-u-sm-centered">
			<form class="am-form m-form-horizontal" action="${ctx}/order/saveSettleItem" method="post">
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">姓名：</label>
					<div class="am-u-sm-9 am-form-group field">
						<input type="text" name="s.alipay_name" data-validate="required:支付宝姓名不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">支付宝账号：</label>
					<div class="am-u-sm-9 am-form-group field">
						<input type="text" name="s.alipay_account" data-validate="required:支付宝账号不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">PID：</label>
					<div class="am-u-sm-9 am-form-group field">
						<input type="text" name="s.pid" data-validate="required:PID不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">状态：</label>
					<div class="am-u-sm-3 am-form-group field">
						<select name="s.status" class="am-form-field">
							<option selected="selected">正常</option>
							<option>禁用</option>
				   		</select>
					</div>
					<div class="am-u-sm-7"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">限额：</label>
					<div class="am-u-sm-5 am-form-group field">
						<input type="text" name="s.max_money" value="0" data-validate="required:限额不能为空">
					</div>
					<div class="am-u-sm-5">
						<strong style="color:red;">限额为小于等于0时，无限收款。</strong>
					</div>
				</div>
				
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">备注：</label>
					<div class="am-u-sm-9 am-form-group field">
						<input type="text" name="s.remark" >
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top" style="margin-bottom:30px;">
					<div class="am-u-sm-8 am-u-sm-push-4">
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
