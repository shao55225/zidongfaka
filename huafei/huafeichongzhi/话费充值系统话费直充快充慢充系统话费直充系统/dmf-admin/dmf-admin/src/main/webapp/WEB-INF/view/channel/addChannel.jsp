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
 			<small>通道管理</small>&nbsp;/&nbsp;
 			<small>通道列表</small>&nbsp;/&nbsp;
 			<small>添加通道</small>
 		</div>
	</div>
	
	
	<div class="am-g">
		<div class="am-u-lg-12 am-u-md-12 am-u-sm-centered">
			<form class="am-form m-form-horizontal" action="${ctx}/channel/saveChannel" method="post">
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">通道昵称：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.nickname" data-validate="required:昵称不能为空">
					</div>
					<label class="am-u-sm-2 am-text-right">通道名称：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.name" data-validate="required:用户名不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">上游id：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.sid" data-validate="required:上游id不能为空">
					</div>
					<label class="am-u-sm-2 am-text-right">上游key：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.skey" data-validate="required:上游key不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
						
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">提交地址：</label>
						<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.surl" data-validate="required:上游id不能为空">
					</div>
					
					<label class="am-u-sm-2 am-text-right">通道代码：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.code"  data-validate="required:通道代码不能为空且不能重复">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
				
					
					<label class="am-u-sm-2 am-text-right">快捷费率：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.fastrebate" data-validate="required:快捷费率不能为空" placeholder="2%，请输入0.02">
					</div>
				
					<label class="am-u-sm-2 am-text-right">微信费率：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.wechatrebate" data-validate="required:微信费率不能为空" placeholder="2%，请输入0.02">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
				
							<label class="am-u-sm-2 am-text-right">支付宝费率：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.alipayrebate" data-validate="required:支付宝费率不能为空" placeholder="2%，请输入0.02">
					</div>
					
					<label class="am-u-sm-2 am-text-right">网关费率：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.gatewayrebate" data-validate="required:网关费率不能为空" placeholder="2%，请输入0.02">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
				
					<label class="am-u-sm-2 am-text-right">状态：</label>
					<div class="am-u-sm-3 am-form-group field">
						<select name="a.status" class="am-form-field">
				   			<option selected="selected">正常</option>
				   			<option>禁用</option>
				   		</select>
					</div>
					
					<label class="am-u-sm-2 am-text-right">通道类别：</label>
					<div class="am-u-sm-3 am-form-group field">
						<select name="a.type" class="am-form-field">
				   			<option selected="selected">网银</option>
				   			<option>快捷</option>
				   			<option>代付</option>
				   			<option>微信H5</option>
				   			<option>微信扫码</option>
				   			<option>支付宝H5</option>
				   			<option>支付宝扫码</option>
				   		</select>
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
