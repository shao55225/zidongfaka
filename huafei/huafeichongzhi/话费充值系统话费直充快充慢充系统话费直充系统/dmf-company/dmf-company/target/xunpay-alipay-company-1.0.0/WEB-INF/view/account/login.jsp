<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta content="telephone=no" name="format-detection">
	<link rel="stylesheet" href="${ctx}/resource/loginFiles/bootstrap.css">
	<link rel="stylesheet" href="${ctx}/resource/loginFiles/main.css">
	<link rel="stylesheet" href="${ctx}/resource/loginFiles/bootstrap-swich.css">
	<link rel="stylesheet" href="${ctx}/resource/loginFiles/layer.css">
	<script type="text/javascript" src="${ctx}/resource/loginFiles/jquery.js">
	</script>
	<script type="text/javascript" src="${ctx}/resource/loginFiles/handlebars-v4.0.5.js">
	</script>
	<script type="text/javascript" src="${ctx}/resource/loginFiles/main.js">
	</script>
	<script type="text/javascript" src="${ctx}/resource/loginFiles/bootstrap.min.js">
	</script>
	<script type="text/javascript" src="${ctx}/resource/loginFiles/jquery.cookie.js">
	</script>
	<script type="text/javascript" src="${ctx}/resource/loginFiles/layer.js">
	</script>
	<link rel="stylesheet" href="${ctx}/resource/loginFiles/layer.css" id="layui_layer_skinlayercss"
	style="">
	<title>
		运营商后台
	</title>
	<style>
		::-webkit-scrollbar{height:2px;width:2px}body{background:#0a254d;height:100%}.login_main{position:relative;height:100%;left:50%;margin-left:-600px;top:0;padding:0}@media
		screen and (max-width:1200px){.login_main{margin-left:-488px}}
	</style>
	<script type="text/javascript" src="${ctx}/resource/loginFiles/_l.js" id="_lxb_jsonp_jryfflx6_"
	charset="utf-8">
	</script>
</head>
<body>
	<div style="position:relative;width:100%;min-height:100%">
		<img src="${ctx}/resource/loginFiles/login_bg.jpg" alt="" style="width:100%;height:100%;opacity:.7;position:absolute"
		class="img-responsive">
		<div class="container login_main">
			<div class="login-title">
				<div>
					<span class="login-line">
						欢迎登录运营商后台
					</span>
				</div>
			</div>
			<div>
				<form method="post" class="am-form" action="${ctx}/account/doLogin" method="post">
					<table class="login_table">
						<tbody>
							<tr>
								<td style="text-align:left">
									<a  target="_blank">
										<!--  <img src="${ctx}/resource/loginFiles/login_logo.png" alt="" style="margin:56px 0 0 40px">-->
									</a>
								</td>
							</tr>
							<tr>
								<td>
									<input type="text" name="username" id="em" value="" placeholder="账号"
									class="login-input" id="loginName" style="margin-top:10px">
								</td>
							</tr>
							<tr>
								<td>
									<input type="password" name="password" id="pd" value="" placeholder="登陆密码"
									class="login-input" style="margin-top:0px" id="loginPwd">
								</td>
							</tr>
							<tr style="display: none;">
								<td>
									<a href="#" class="pull-right rm_input"
									style="padding-right:45px;margin-top:-15px">
										忘记密码?
									</a>
								</td>
							</tr>
							<tr>
								<td style="padding-bottom:30px;margin-top:-15px;">
									<input type="submit" value="账号登录" class="login-input login-bt" id="login_lu"
									style="padding:0">
									<p>
									<c:if
										test="${showMsg != null && showMsg != ''}">
										<small class="tooltips">${showMsg}</small>
									</c:if>
									</p>
									<p style="text-align:left;padding-left:40px;color:#999;margin-top:8px;">
										还没有账户？
										<a class="login-num">
											联系超级管理员
										</a>
									</p>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div style="margin-bottom:40px">
				<p style="text-align:center;color:#fff;margin:50px auto 22px;font-size:14px">
					来自行业的信赖
				</p>
				<p style="text-align:center">
					<img src="${ctx}/resource/loginFiles/login_import.png" alt="">
				</p>
			</div>
		</div>
		<div class="container">
			<div class="container login-foot" style="position:absolute;bottom:0;white-space:nowrap">
				<span>
					运营商后台
				</span>
				|
				<span>
					All Rights Reserved
				</span>
			</div>
		</div>
	</div>
</body>
</html>