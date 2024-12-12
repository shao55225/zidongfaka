<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="_meta.jsp" %>
</head>
<body>
<div class="am-cf admin-main">

	<div class="am-cf am-padding">
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">ERROR</strong> / <small>That’s an error</small></div>
    </div>

    <div class="am-g">
      <div class="am-u-sm-12">
        <h2 class="am-text-center am-text-xl am-margin-top-lg">系统错误：</h2>
        <p class="am-text-center">
        	您的操作出现问题了，
        	点击
        		<a href="${empty dumpUrl ? 'javascript:history.go(-1);' : dumpUrl}">这里</a>
        	继续操作
        </p>
        <c:if test="${message != null}">
        	<div class="am-alert am-alert-danger" data-am-alert style="margin:20px 50px;">
        		${message}
        		<button type="button" class="am-close">&times;</button>
        	</div>
        </c:if>
      </div>
    </div>


</div>
</body>
</html>

