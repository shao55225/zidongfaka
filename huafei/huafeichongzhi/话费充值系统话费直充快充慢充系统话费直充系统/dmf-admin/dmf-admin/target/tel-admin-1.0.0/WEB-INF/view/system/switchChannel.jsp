<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  $(function(){
	  <c:forEach items="${cs}" var="c">
	  $("#${c}").attr("checked", "checked");
	  </c:forEach>
  });
  </script>
</head>
<body>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>系统设置</small>&nbsp;/&nbsp;
 			<small>通道开关</small>&nbsp;/&nbsp;
 			<small>
 				<a href="javascript:location.reload();" class="am-btn am-btn-success am-btn-xs">刷新</a>
 			</small>
 		</div>
	</div>
	
	<form action="${ctx}/system/updateChannel" method="post">
	<div class="am-g" style="margin-bottom:100px;">
		<div class="am-u-sm-12">
			<table class="am-table am-table-striped am-table-hover table-main">
				<thead>
					<tr>
						<th>通道名称</th>
						<th>CODE</th>
						<th>是否打开</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>当面付</td>
						<td>alipay_dmf</td>
						<td>
							<label class="am-checkbox">
								<input type="checkbox" id="alipay_dmf" name="channel" value="alipay_dmf" data-am-ucheck> 开
							</label>
						</td>
					</tr>
					
					<tr>
						<td>支付宝红包</td>
						<td>alipay_hb</td>
						<td>
							<label class="am-checkbox">
								<input type="checkbox" id="alipay_hb" name="channel" value="alipay_hb" data-am-ucheck> 开
							</label>
						</td>
					</tr>
					
					<tr>
						<td>支付宝预付</td>
						<td>alipay_fund</td>
						<td>
							<label class="am-checkbox">
								<input type="checkbox" id="alipay_fund" name="channel" value="alipay_fund" data-am-ucheck> 开
							</label>
						</td>
					</tr>
					
					<tr>
						<td>支付宝手机wap支付</td>
						<td>alipay_wap</td>
						<td>
							<label class="am-checkbox">
								<input type="checkbox" id="alipay_wap" name="channel" value="alipay_wap" data-am-ucheck> 开
							</label>
						</td>
					</tr>
					
					<tr>
						<td>简付（自有个码）</td>
						<td>paydog</td>
						<td>
							<label class="am-checkbox">
								<input type="checkbox" id="paydog" name="channel" value="paydog" data-am-ucheck> 开
							</label>
						</td>
					</tr>
					
					<tr>
						<td>Heipay红包个码</td>
						<td>heipay</td>
						<td>
							<label class="am-checkbox">
								<input type="checkbox" id="heipay" name="channel" value="heipay" data-am-ucheck> 开
							</label>
						</td>
					</tr>
					
					<tr>
						<td>ypay三方支付</td>
						<td>ypay</td>
						<td>
							<label class="am-checkbox">
								<input type="checkbox" id="ypay" name="channel" value="ypay" data-am-ucheck> 开
							</label>
						</td>
					</tr>
					
					<tr>
						<td>云易付</td>
						<td>yunyifu</td>
						<td>
							<label class="am-checkbox">
								<input type="checkbox" id="yunyifu" name="channel" value="yunyifu" data-am-ucheck> 开
							</label>
						</td>
					</tr>
					
					
				</tbody>
			</table>
		</div>
		<div class="am-u-sm-12">
			<input type="submit" class="am-btn am-btn-success" value="提交">
		</div>
	</div>
	</form>
	
	
</div>
</body>
</html>