<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
</head>
<body>
<div class="am-cf admin-main">

<table class="am-table">
    <thead>
        <tr>
            <th>订单号</th>
            <th>收款金额</th>
            <th>税率</th>
            <th>税费</th>
            <th>实收金额</th>
            <th>余额</th>
            <th>类型</th>
            <th>时间</th>
        </tr>
    </thead>
    <tbody>
    	<c:set var="balance" value="0"></c:set>
    	<c:forEach items="${bills}" var="b">
        <c:set var="balance" value="${balance + b.in_money}"></c:set>
        <tr ${balance != b.balance ? "style='background:red;color:#fff;'" : ""}>
        	<td>${b.order_no }</td>
        	<td>${b.order_money }</td>
        	<td>${b.tax }</td>
        	<td>${b.tax_money }</td>
        	<td>${b.in_money }</td>
        	<td>${b.balance }</td>
        	<td>${b.type }</td>
        	<td>${b.addtime }</td>
        </tr>
        </c:forEach>
    </tbody>
</table>


</div>
</body>
</html>

