<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="_meta.jsp" %>
</head>
<body>
<div class="am-cf admin-main">

	
	<div class="am-cf am-padding">
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">温馨提醒</strong> / <small>That’s a success</small></div>
    </div>
	
	
	<div class="am-g">
      <div class="am-u-sm-12 am-text-center">
      	<c:if test="${message != null}">
      		<div class="am-alert am-alert-success" data-am-alert style="margin:0 50px;">
      			${message}
      			<button type="button" class="am-close">&times;</button>
      		</div>
        </c:if>
        <p>
	    	恭喜您操作成功，
	    	点击
	    		<a href="${empty dumpUrl ? 'javascript:history.go(-1);' : dumpUrl}">这里</a>
	    	继续操作
	    </p>
      </div>
    </div>
	
</div>
</body>
</html>

