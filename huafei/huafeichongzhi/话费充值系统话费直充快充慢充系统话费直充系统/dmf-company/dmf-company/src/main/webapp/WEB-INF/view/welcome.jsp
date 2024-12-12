<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="_meta.jsp" %>
  <style type="text/css">
  .am-text-left {color:#F37B1D;}
  </style>
</head>
<body>
<div class="am-cf admin-main">
	
<label style="margin:10px;">
当前余额：${balance}
</label>

<table class="am-table">
    <thead>
        <tr>
            <th>日期</th>
            <th>成交数</th>
            <th>流水金额</th>
       
        </tr>
    </thead>
    <tbody>
    	<c:forEach items="${rs}" var="r">
        <tr>
            <td>${r.date}</td>
            <td>${r.c}</td>
            <td>${r.order_money}</td>
        </tr>
        </c:forEach>
    </tbody>
</table>

</div>
</body>
</html>

