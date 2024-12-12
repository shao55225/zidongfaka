<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
</head>
<body>
<div class="am-cf admin-main">

	<div class="am-tabs" data-am-tabs="{noSwipe: 1}" id="doc-tab-demo-1">
	  <ul class="am-tabs-nav am-nav am-nav-tabs">
	    <li class="am-active"><a href="javascript: void(0)">登陆密码</a></li>
	 
	  </ul>
	
	  <div class="am-tabs-bd">
	    <div class="am-tab-panel am-active">
	    	
	    	<fieldset>
				<legend>修改登陆密码</legend>
				<form action="${ctx}/account/editPassword" method="post" class="am-form am-form-horizontal">
					<div class="am-form-group">
					    <label class="am-u-sm-2 am-form-label">新的密码:</label>
					    <div class="am-u-sm-8">
					    	<input type="password" name="newPassword" placeholder="输入新的密码">
					    </div>
					    <div class="am-u-sm-2">&nbsp;</div>
					</div>
					<div class="am-form-group">
					    <div class="am-u-sm-10 am-u-sm-offset-2">
					      <button type="submit" class="am-btn am-btn-secondary">提交修改</button>
					    </div>
					</div>
				</form>
			</fieldset>
	    
	    </div>
	    <div class="am-tab-panel">

			<fieldset>
				<legend>修改支付密码</legend>
				<form action="${ctx}/account/editPayPassword" method="post" class="am-form am-form-horizontal">
					<div class="am-form-group">
					    <label class="am-u-sm-2 am-form-label">新的密码:</label>
					    <div class="am-u-sm-8">
					    	<input type="password" name="newPayPassword" placeholder="输入新的密码">
					    </div>
					    <div class="am-u-sm-2">&nbsp;</div>
					</div>
					<div class="am-form-group">
					    <div class="am-u-sm-10 am-u-sm-offset-2">
					      <button type="submit" class="am-btn am-btn-secondary">提交修改</button>
					    </div>
					</div>
				</form>
			</fieldset>
	    
	    </div>
	  </div>
	</div>


</div>
</body>
</html>

