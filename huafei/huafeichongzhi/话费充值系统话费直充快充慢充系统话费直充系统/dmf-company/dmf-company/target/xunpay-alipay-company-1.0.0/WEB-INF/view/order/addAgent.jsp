<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  	function randomStr(type) {
  		var result = "";
  		if (type == "appid") {
  			result = new Date().getTime() + "" + (Math.random() * 10000000);
  			result = result.substring(0, result.indexOf("."));
  		} else if (type == "md5key") {
  			var ws = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'v', 'u', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'V', 'U', 'W', 'X', 'Y', 'Z'];
  			for (var i = 0; i < 64; i++) {
  				result += ws[Math.floor(Math.random() * ws.length)];
  			}
  		}
  		$("#" + type).val(result);
  	}
  </script>
</head>
<body>
<div class="am-cf admin-main">

	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>商户管理</small>&nbsp;/&nbsp;
 			<small>商户列表</small>&nbsp;/&nbsp;
 			<small>添加商户</small>
 		</div>
	</div>
	
	
	<div class="am-g">
		<div class="am-u-lg-12 am-u-md-12 am-u-sm-centered">
			<form class="am-form m-form-horizontal" action="${ctx}/order/saveAgent" method="post">
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">昵称：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="c.nickname" data-validate="required:昵称不能为空">
					</div>
					<label class="am-u-sm-2 am-text-right">用户名：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="c.username" data-validate="required:用户名不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">密码：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="password" name="c.password" data-validate="required:密码不能为空">
					</div>
					<label class="am-u-sm-2 am-text-right">支付密码：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="password" name="c.pay_password" data-validate="required:支付密码不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">状态：</label>
					<div class="am-u-sm-3 am-form-group field">
						<select name="c.status" class="am-form-field">
				   			<option selected="selected">正常</option>
				   			<option>禁用</option>
				   		</select>
					</div>
					<label class="am-u-sm-2 am-text-right">费率：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="c.rebate" maxlength="6" data-validate="required:费率不能为空" placeholder="5%，请输入0.05">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">
						APPID：
					</label>
					<div class="am-u-sm-3 am-form-group field">
						<button class="am-btn am-btn-success" type="button" style="position: absolute;" onclick="randomStr('appid');">
						  <i class="am-icon-random"></i>
						</button>
						<input type="text" name="c.appid" id="appid" style="padding-left:55px;" data-validate="required:APPID不能为空">
					</div>
					<label class="am-u-sm-2 am-text-right">
						密钥：
					</label>
					<div class="am-u-sm-3 am-form-group field">
						<button class="am-btn am-btn-success" type="button" style="position: absolute;" onclick="randomStr('md5key');">
						  <i class="am-icon-random"></i>
						</button>
						<input type="text" name="c.md5key" id="md5key" style="padding-left:55px;" data-validate="required:密钥不能为空">
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
