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
<form action="" class="am-form am-form-inline">
  <div class="am-form-group am-form-icon">
    <i class="am-icon-calendar"></i>
    <input type="text" name="date" class="am-form-field" placeholder="日期" data-am-datepicker="{theme: 'success'}" value="${date}" onchange="selectDate(this);">
  </div>
  <div class="am-form-group">
  	<button type="submit" class="am-btn am-btn-success">查询</button>
  </div>
</form>

<script type="text/javascript">
function selectDate(t) {
	location.hreef = "/welcome?date=" + $(t).val();
}
</script>

<div class="am-cf admin-main am-hide-sm-only">
<table class="am-table">
    <thead>
        <tr>
            <th>日期</th>
            <th>商户</th>
            <th>成交数</th>
            <th>流水金额</th>
        
        
        </tr>
    </thead>
    <tbody>
    	<c:set var="totalMoney1" value="0"></c:set>
    	<c:set var="totalMoney2" value="0"></c:set>
    	<c:set var="totalMoney3" value="0"></c:set>
    	<c:set var="totalMoney4" value="0"></c:set>
    	<c:forEach items="${rs}" var="r">
        <tr>
            <td>${r.date}</td>
            <td>${r.company_name}</td>
            <td>${r.c}</td>
            <td>${r.order_money}</td>
     
        
        </tr>
        <c:set var="totalMoney1" value="${r.order_money + totalMoney1}"></c:set>
        <c:set var="totalMoney2" value="${r.rebate_money + totalMoney2}"></c:set>
        <!-- 
        <c:set var="totalMoney3" value="${r.agent_money + totalMoney3}"></c:set>
         -->
        <c:set var="totalMoney4" value="${r.in_money + totalMoney4}"></c:set>
        </c:forEach>
        <tr>
        	<td colspan="3">合计:</td>
        	<td>${totalMoney1}</td>
        	<shiro:hasRole name="admin">
        	 <!-- <td>${totalMoney2}</td>
        	 
        	<td>${totalMoney3}</td>
        	-->
        	</shiro:hasRole>
        	<td>&nbsp;</td>
        </tr>
    </tbody>
</table>
</div>



<div data-am-widget="list_news" class="am-list-news am-list-news-default am-no-layout am-show-sm-only">
	<div class="am-list-news-bd">
		<ul class="am-list">
			<c:forEach items="${rs}" var="r">
			<li class="am-g am-list-item-desced">
				<a href="#" class="am-list-item-hd">${r.date} - ${r.company_name}</a>
				<div class="am-list-item-text">
					下单数：${r.c} / 
					成交额：${r.order_money} / 
					手续费：${r.rebate_money} / 
					成本：${r.agent_money}
				</div>
			</li>
			</c:forEach>
			<li class="am-g am-list-item-desced">
				<a href="#" class="am-list-item-hd">合计：</a>
				<div class="am-list-item-text">
					成交额：${totalMoney1} / 
					手续费：${totalMoney2} / 
					成本：${totalMoney3}
				</div>
			</li>
		</ul>
	</div>
</div>

</body>
</html>

