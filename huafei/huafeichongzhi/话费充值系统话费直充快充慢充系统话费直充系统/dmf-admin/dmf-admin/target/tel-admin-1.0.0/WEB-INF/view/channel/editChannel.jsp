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
 			<small>编辑通道</small>
 		</div>
	</div>
	
	
	<div class="am-g">
		<div class="am-u-lg-12 am-u-md-12 am-u-sm-centered">
			<form class="am-form m-form-horizontal" action="${ctx}/channel/updateChannel" method="post">
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">通道昵称：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.nickname" value="${a.nickname}" data-validate="required:昵称不能为空">
					</div>
					<label class="am-u-sm-2 am-text-right">通道名称：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.name" value="${a.name}" data-validate="required:用户名不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">上游id ：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.sid" value="${a.sid}" data-validate="required:上游id不能为空">
					</div>
					<label class="am-u-sm-2 am-text-right">上游key：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.skey" value="${a.skey}" data-validate="required:上游key不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
					
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">提交地址：</label>
						<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.surl"  value="${a.surl}"  data-validate="required:上游id不能为空">
					</div>
					
					<label class="am-u-sm-2 am-text-right">通道代码：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.code"  value="${a.code}"  disabled="disabled">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
			
							<label class="am-u-sm-2 am-text-right">快捷费率：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.fastrebate"   value="${a.fastrebate}"  data-validate="required:快捷费率不能为空" placeholder="2%，请输入0.02">
					</div>
					<label class="am-u-sm-2 am-text-right">微信费率：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.wechatrebate" value="${a.wechatrebate}" data-validate="required:费率不能为空" placeholder="2%，请输入0.02">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
								
				<div class="am-g am-margin-top">
				
					<label class="am-u-sm-2 am-text-right">支付宝费率：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.alipayrebate" value="${a.alipayrebate}" data-validate="required:支付宝费率不能为空" placeholder="2%，请输入0.02">
					</div>
					
					<label class="am-u-sm-2 am-text-right">网关费率：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="a.gatewayrebate"   value="${a.gatewayrebate}" data-validate="required:网关费率不能为空" placeholder="2%，请输入0.02">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
				
					<label class="am-u-sm-2 am-text-right">状态：</label>
					<div class="am-u-sm-3 am-form-group field">
						<select name="a.status" class="am-form-field">
				   			<option ${a.status == "正常" ? "selected" : ""}>正常</option>
				   			<option ${a.status == "禁用" ? "selected" : ""}>禁用</option>
				   		</select>
					</div>
					
					<label class="am-u-sm-2 am-text-right">通道类别：</label>
					<div class="am-u-sm-3 am-form-group field">
						<select name="a.type" class="am-form-field">
				   			<option selected="selected">网银</option>
				   			<option ${a.type == "快捷" ? "selected" : ""}>快捷</option>
				   			<option ${a.type == "代付" ? "selected" : ""}>代付</option>
				   			<option ${a.type == "微信H5" ? "selected" : ""}>微信H5</option>
				   			<option ${a.type == "微信扫码" ? "selected" : ""}>微信扫码</option>
				   			<option ${a.type == "支付宝H5" ? "selected" : ""}>支付宝H5</option>
				   			<option ${a.type == "支付宝扫码" ? "selected" : ""}>支付宝扫码</option>
				   		</select>
					</div>
					<div class="am-u-sm-1"></div>
					
				</div>
				
				
				
				<div class="am-g am-margin-top" style="margin-bottom:30px;">
					<div class="am-u-sm-8 am-u-sm-push-4">
						<input type="hidden" value="${a.id}" name="a.id">
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
