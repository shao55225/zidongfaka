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
 			<small>系统设置</small>&nbsp;/&nbsp;
 			<small> 参数设置</small>&nbsp;/&nbsp;
 			<small>编辑参数</small>&nbsp;/&nbsp;
 			<small>${c.nickname }</small>
 		</div>
	</div>
	
	<div class="am-g">
		<div class="am-u-lg-12 am-u-md-12 am-u-sm-centered">
			<form class="am-form m-form-horizontal" action="${ctx}/system/updateConfig" method="post">
				<div class="am-g am-margin-top">
					<label class="am-u-sm-2 am-text-right">参数说明：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="c.title" value="${c.title}" disabled="disabled">
					</div>
					<label class="am-u-sm-2 am-text-right">参数内容：</label>
					<div class="am-u-sm-3 am-form-group field">
						<input type="text" name="c.content" value="${c.content}" data-validate="required:参数内容不能为空">
					</div>
					<div class="am-u-sm-1"></div>
				</div>
				
				<div class="am-g am-margin-top" style="margin-bottom:30px;">
					<div class="am-u-sm-8 am-u-sm-push-4">
						<input type="hidden" name="c.id" value="${c.id}" />
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
