<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
</head>
<body>
<div class="am-cf admin-main">

<table class="am-table">
	<c:set var="id" value="${id}"></c:set>
    <thead>
        <tr>
            <th>日期</th>
            <th>商户</th>
            <th>收款金额</th>
            <th>手续费</th>
            <th>实收金额</th>
            <th>代理分红</th>
            <th>支付宝分账</th>
            <th>分账失败</th>
            <th>下发</th>
            <th>余额</th>
        </tr>
    </thead>
    <tbody>
    	<c:set var="total_order_money" value="0"></c:set>
    	<c:set var="total_company_money" value="0"></c:set>
    	<c:set var="total_in_money" value="0"></c:set>
    	<c:set var="total_agent_money" value="0"></c:set>
    	<c:set var="total_settle_money" value="0"></c:set>
    	<c:set var="total_settle_error_money" value="0"></c:set>
    	<c:set var="total_draw_money" value="0"></c:set>

    	
    	
    	
    	<c:set var="balance" value="0"></c:set>
    	<c:forEach items="${result}" var="r">
        <tr>
        	<c:set var="balance" value="${balance + r.in_money + r.agent_money - r.settle_money - r.settle_error_money - r.draw_money}"></c:set>
            <td>${r.date}</td>
            <td>${c.id}-${c.nickname}</td>
            <td>${r.order_money == null ? "0" : r.order_money}</td>
            <td>${r.company_money == null ? "0" : r.company_money}</td>
            <td>${r.in_money == null ? "0" : r.in_money}</td>
            <td>${r.agent_money == null ? "0" : r.agent_money}</td>
            <td>${r.settle_money == null ? "0" : r.settle_money}</td>
            <td>${r.settle_error_money == null ? "0" : r.settle_error_money}</td>
            <td>${r.draw_money == null ? "0" : r.draw_money}</td>
            <td>${balance}</td>
           
	    	<c:set var="total_order_money" value="${r.order_money + total_order_money}"></c:set>
	    	<c:set var="total_company_money" value="${r.company_money + total_company_money}"></c:set>
	    	<c:set var="total_in_money" value="${r.in_money + total_in_money}"></c:set>
	    	<c:set var="total_agent_money" value="${r.agent_money + total_agent_money}"></c:set>
	    	<c:set var="total_settle_money" value="${r.settle_money + total_settle_money}"></c:set>
	    	<c:set var="total_settle_error_money" value="${r.settle_error_money + total_settle_error_money}"></c:set>
	    	<c:set var="total_draw_money" value="${r.draw_money + total_draw_money}"></c:set>
        </tr>
        </c:forEach>
        <tr style="font-weight: bold;color:red;">
        	<td colspan="2">合计：</td>
        	<td>${total_order_money}</td>
        	<td>${total_company_money}</td>
        	<td>${total_in_money}</td>
        	<td>${total_agent_money}</td>
        	<td>${total_settle_money}</td>
        	<td>${total_settle_error_money}</td>
        	<td>${total_draw_money}</td>
        	<td>
        		${c.balance}(实际余额) <br />
        		待下发： ${dxf} <br />
        		差额：${c.balance - balance + dxf}
        	</td>
        </tr>
    </tbody>
</table>


 <script type="text/javascript">
 
 window.onload=function(){
	 
	  var tureMoney=${c.balance - balance + dxf};
	  
	  var amount=parseFloat(tureMoney);
	  
	
	  
	  if(amount>0){
	  
		  var id=${id};
	  
	 	 $.get("${ctx}/company/updateBalance?id=" + id + "&amount=" + amount, function(s){
		
			  if (s == "success") {
				  
				  console.log("自动调整差额成功！");
				  
				  location.reload();
				  
			  }
	 	
	 	});
	  }
	  else {  
		  
		  var id=${id};
		  
		  $.get("${ctx}/company/saveMargin?id=" + id + "&amount=" + amount, function(s){
		 		
				  if (s == "success") {
					  
					  console.log("保存差额成功！");
					  
					//  location.reload();
					  
				  }
		 	
		 	});
		  
		  
		  
	  }
	 
 }


</script>


</div>
</body>
</html>

