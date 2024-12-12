<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
 <%@ include file="../_meta.jsp" %>
 <style type="text/css">
    .header {
      text-align: center;
    }
    .header h1 {
      font-size: 200%;
      color: #333;
      margin-top: 30px;
    }
    .header p {
      font-size: 14px;
    }
  </style>
  <script type="text/javascript">
  $(function(){
	  if (window != window.parent) {
  	      window.parent.location.reload();
  	      return;
  	  }
  });
  </script>
</head>
<body>
<div class="header">
  <div class="am-g">
    <h1>后台管理系统</h1>
<%--      <h6>如需系统搭建请联系唯一QQ：1483665114</h6>--%>
  </div>
  <hr />
</div>
<div class="am-g">
  <div class="am-u-lg-4 am-u-md-8 am-u-sm-centered">
    <form method="post" class="am-form" action="${ctx}/account/doLogin" method="post">
    
    <div class="am-form-group ">
	      <div class="field am-input-group am-input-group-primary">
	      	<input type="text" name="username" id="em" value="" placeholder="账号" data-validate="required:账号不能为空">
	      	<span class="am-input-group-label"><i class="am-icon-user am-icon-fw"></i></span>
	      </div>
      </div>
      
      <div class="am-form-group ">
	      <div class="field am-input-group am-input-group-primary">
	      	<input type="password" name="password" id="pd" value="" placeholder="登陆密码" data-validate="required:密码不能为空">
	      	<span class="am-input-group-label"><i class="am-icon-lock am-icon-fw"></i></span>
	      </div>
      </div>
      
      <div class="am-form-group">
	      <div class="field am-input-group am-input-group-primary">
	      	<input type="text" name="verifyCode" id="cod" value="" placeholder="验证码" data-validate="required:验证码不能为空">
	      	<span class="am-input-group-btn"><img id="captchaImage"  class="captchaImage" src="${ctx}/verifyCode/login" /></span>
	      </div>
      </div>
      <br />
      <div class="am-cf">
        <input type="submit"  value="登 录" class="am-btn am-btn-primary am-btn-sm am-fl" >
        <c:if test="${showMsg != null && showMsg != ''}">
        <small class="tooltips">${showMsg}</small>
        </c:if>
      </div>
    </form>
    <hr>
  </div>
</div>
</body>
<script type="text/javascript">
	$(function(){
		$('.captchaImage').click(function() {
			$(this).attr("src", "${ctx}/verifyCode/login?" + new Date());
		});
	});
</script>
</html>