<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>

	<style>
		.mycontainer input{
			border:1px solid #ccc;
			padding:6px 10px;
		}
	</style>
	
  <%@ include file="../_meta.jsp" %>

  <script type="text/javascript">

	window.onload=function(){
		var totleTp = 0;
		var tppiao = document.getElementsByClassName("tppiao");
		for(var i=0;i<tppiao.length;i++){totleTp+=parseInt(tppiao[i].innerHTML);}
		var tpdetial = document.getElementsByClassName("tpdetial");
		for(var i=0;i<tpdetial.length;i++){var mun = parseInt(tpdetial[i].getElementsByClassName("tppiao")[0].innerHTML);var bfb = (mun*100/totleTp).toFixed(2)+"%";tpdetial[i].getElementsByClassName("tppercent")[0].innerHTML = bfb;
		}
	}
	if(window.location.href.indexOf("udsid=")>-1){
		$("#ctlNext").on("click",function(){
			console.log("执行成功！")
			meteor.track("form", {convert_id: "1641358861914125"})
		})
	}
  
  function viewOrder(id) {
	  var iWidth = 960;
	  var iHeight = 640;
	  var iTop = (window.screen.height-30-iHeight)/2;
	  var iLeft = (window.screen.width-10-iWidth)/2;
	  window.open('${ctx}/order/viewOrder/' + id, '订单详情', 'width='+iWidth+',height='+iHeight+',top='+iTop+',left='+iLeft+',menubar=no,toolbar=no,location=no,scrollbars=no,status=no,modal=yes');
  }
  function reloadItem(id) {
	  
	  alert("操作成功！");
	  location.reload();
	
  }
  function settleItem(id) {
	  $.get("${ctx}/order/settleItem/" + id, function(data) {
		  if (data == "success") {
			  alert("操作成功！");
			  location.reload();
		  }
	  });
  }
  function noticeItem(id) {
	  $.get("${ctx}/order/noticeApi/" + id, function(data) {
		  if (data == "success") {
			  alert("操作成功！");
			  location.reload();
		  }
	  });
  }
  
  function totalRate(i) {
	  $("#read_second").html(i);
	  if (i == 0) {
		  $.get("${ctx}/order/totalRate", function(data){
			  for (var key in data) {
				  $("#" + key).html(data[key]);
			  }
		  });
		  i = 6;
	  }
	  setTimeout(function(){
		  totalRate(i - 1);
	  }, 1000);
  }
  
  $(function(){
	  totalRate(0); 
  });
  

  </script>
  

	
</head>
<body>
    
<script src="https://www.jq22.com/jquery/jquery-1.10.2.js"></script>

<script src="${ctx}/resource/plugins/dateTime.min.js"></script>



<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>订单中心</small>&nbsp;/&nbsp;
 			<small>订单列表</small>
 		</div>
	</div>
	
	<form method="post" id="form1" class="am-form">
	<div class="am-g">
	
		<div class="am-u-lg-3">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">订单号</span>
				<input type="text" name="orderNo" placeholder="请输入订单号" value="${orderNo}" class="am-form-field"/>
			</div>
		</div>
		
		<div class="am-u-lg-3">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">手机号</span>
				<input type="text" name="appid" placeholder="请输入手机号" value="${appid}" class="am-form-field"/>
			</div>
		</div>
		
		<div class="am-u-lg-4">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">状态</span>
				<select name="status" style="float:left;width:100px;">
		   			<option value="">充值状态</option>
		   			<option value="未充值" ${status == '未充值' ? 'selected' : ''}>未充值</option>
		   			<option value="待匹配" ${status == '待匹配' ? 'selected' : ''}>待匹配</option>
		   			<option value="匹配中" ${status == '匹配中' ? 'selected' : ''}>匹配中</option>
		   			<option value="充值中" ${status == '充值中' ? 'selected' : ''}>充值中</option>
		   			<option value="充值成功" ${status == '充值成功' ? 'selected' : ''}>充值成功</option>
		   			<option value="充值失败" ${status == '充值失败' ? 'selected' : ''}>充值失败</option>
		   		</select>

		   		<select name="notice" style="float:left;width:100px;">
		   			<option value="">回调状态</option>
		   			<option value="回调成功" ${notice == '回调成功' ? 'selected' : ''}>回调成功</option>
		   			<option value="回调失败" ${notice == '回调失败' ? 'selected' : ''}>回调失败</option>
		   			<option value="未回调" ${notice == '未回调' ? 'selected' : ''}>未回调</option>
		   		</select>
			</div>
		</div>
		
				
		<div class="am-u-lg-3">
		
		</div>
		
		<div class="am-u-lg-3">
		
				<div class="am-input-group am-input-group-secondary am-form-group">
			<span class="am-input-group-label">开始时间</span>
					
				<input type="text" name="startTime"  value="${startTime}"  placeholder=" 请选择开始日期和时间" id="datetime">
				 <!--   <i class="am-icon-calendar"></i>  	-->
				 </div>
					  
		</div>
	
		<div class="am-u-lg-1">
					 
		</div>
	
		
		<div class="am-u-lg-3">
					<div class="am-input-group am-input-group-secondary am-form-group">
					<span class="am-input-group-label">结束时间</span>
					<input type="text" name="endTime"  value="${endTime}"  placeholder=" 请选择结束日期和时间" id="datetime2">
					 </div>
		</div>
	
	<!--
		<div class="am-u-lg-3">
			<div class="am-input-group am-input-group-secondary am-form-group">
			
				<span class="am-input-group-label">运营商</span>
				
				<select name="trade_no" style="float:left;width:160px;">
				
		   			<option value="">运营商</option>
		   			<option value="1"  ${trade_no == '1' ? 'selected' : ''}>移动</option>
		   			<option value="2"  ${trade_no == '2' ? 'selected' : ''}>联通</option>
		   			<option value="3"  ${trade_no == '3' ? 'selected' : ''}>电信</option>
		   			
		   		</select>

			</div>
		</div>
	
  -->  
		
		<div class="am-u-lg-2">
			<div class="am-input-group ">
				<input class="am-btn am-btn-primary" type="submit" value="搜索"/>
				&nbsp;
				<input class="am-btn am-btn-success" type="button" value="刷新" onclick="location.reload();"/>
			</div>
		</div>
	</div>
	</form>
	
	
	<div class="am-alert" style="margin:15px;background-color: #0e90d2;">
	
	</div>
	
	<div class="am-g" style="font-size:12px;">
		<div class="am-u-sm-12 am-scrollable-horizontal">
			<table class="am-table am-table-striped am-table-hover am-table-striped am-text-nowrap">
				<thead>
					<tr>
						<th>序号</th>
						<th>订单号</th>
						<th>商户订单号</th>
						<!-- 
						<th>原始订单链接</th> -->
						<th>订单金额</th>
						<th>手机号码</th>
						<th>商户</th>
						<th>充值状态</th>
						<th>回调状态</th>
						<th>下单ip</th>
						<th>运营商</th>
						<!--  <th>支付凭证</th>-->
						<shiro:hasAnyRoles name="admin">
						</shiro:hasAnyRoles>
						<th>下单时间</th>
						<th>匹配时间</th>
						 <th class="table-set">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="o" varStatus="i">
					<tr ${u.status == '已拒绝' ? 'style="color:red;"' : ''}>
						<td>${page.pageNumber * 20 + i.index - 19}</td>
						<td>${o.order_no}</td>
						<td>${o.out_order_no}</td>
						<!-- 
						<td>${o.return_url}</td> -->
						<td>${o.order_money}</td>
						<td>${o.appid}</td>
						<td>${o.company_name}</td>
					 	
					 <c:if test="${o.status == '未充值'}">
							
						<td style="font-weight: bold;color: #ea0f0f;">
							${o.status}
							<c:if test="${o.status == '未充值'}">
							<a href="javascript:reloadItem(${o.id});"><i class="am-icon-refresh"></i></a>
							</c:if>
						</td>
					</c:if>
					
					 <c:if test="${o.status == '待匹配'}">
							
						<td style="font-weight: bold;color: #c80feaa6;">
							${o.status}
							<c:if test="${o.status == '待匹配'}">
							<a href="javascript:reloadItem(${o.id});"><i class="am-icon-refresh"></i></a>
							</c:if>
						</td>
					</c:if>
					
					 <c:if test="${o.status == '匹配中'}">
							
						<td style="font-weight: bold;color: #450fea;">
							${o.status}
							<c:if test="${o.status == '匹配中'}">
							<a href="javascript:reloadItem(${o.id});"><i class="am-icon-refresh"></i></a>
							</c:if>
						</td>
					</c:if>
					
					<c:if test="${o.status == '充值中'}">
							
						<td style="font-weight: bold;color: #ea0f0f;">
							${o.status}
							<c:if test="${o.status == '充值中'}">
							<a href="javascript:reloadItem(${o.id});"><i class="am-icon-refresh"></i></a>
							</c:if>
						</td>
					</c:if>
					
					<c:if test="${o.status == '充值成功'}">
							
					<td style="font-weight: bold;color: #13b919;">
							${o.status}
			
						</td>
					</c:if>
					
					<c:if test="${o.status == '充值异常'}">
						<td style="font-weight: bold;color: #3f89b5;">
							${o.status}
						</td>
					</c:if>
					
					<c:if test="${o.status == '充值失败'}">
					
						<td style="font-weight: bold;color: #ea0f0f;">
							${o.status}
			
						</td>
					</c:if>
						
					<c:if test="${o.notice == '未回调'}">
							
							<td>
								${o.notice}
								<c:if test="${o.status == '充值成功'}">
								<a href="javascript:reloadItem(${o.id});"><i class="am-icon-refresh"></i></a>
								</c:if>
							</td>
					</c:if>
					
					<c:if test="${o.notice == '回调失败'}">
							
							<td style="font-weight: bold;color: #ea0f0f;">
								${o.notice}
								<c:if test="${o.status == '充值成功'}">
								<a href="javascript:reloadItem(${o.id});"><i class="am-icon-refresh"></i></a>
								</c:if>
							</td>
					</c:if>
					
					<c:if test="${o.notice == '回调成功'}">
							
							<td style="font-weight: bold;color: #13b919;">
								${o.notice}
								<c:if test="${o.status == '充值成功'}">
								<a href="javascript:reloadItem(${o.id});"><i class="am-icon-refresh"></i></a>
								</c:if>
							</td>
					</c:if>
					
					 	<td>${o.rsa_private}</td>	
					 	
						<td style="font-weight: bold;color: #4caf50;">
						<c:if test="${o.trade_no == '1'}">
					        移动
					 	</c:if>
					 	
					 		<c:if test="${o.trade_no == '2'}">
					        联通
					 	</c:if>
					 	
		   				<c:if test="${o.trade_no == '3'}">
					        电信
					 	</c:if>
						
						
						</td>
						<!-- <td>${o.token}</td> -->
	
						<td>${o.addtime}</td>
						
						<td>${o.noticetime}</td>
						 
						<td>
							<a href="javascript:viewOrder(${o.id});">详情</a>
							
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div class="am-cf">
				共 ${page.totalRow } 条记录<span class="tooltip"></span>
				<script type="text/javascript">
					function gridPage(pageNumber) {
						$("#pageNumber").val(pageNumber);
						$("#pageForm").submit();
					}
				</script>
				<form id="pageForm" style="display:none;" method="post">
					<input name="orderNo" value="${orderNo}"/>
					<input name="appid" value="${appid}"/>
					<input name="status" value="${status}"/>
					<input name="notice" value="${notice}"/>
					<input name="company_id" value="${companyId}"/>
					<input name="pay_type" value="${payType}"/>
					<input name="startTime" value="${startTime}"/>
					<input name="endTime" value="${endTime}"/>
					<input id="pageNumber" name="page" value="${page.pageNumber}" />
				</form>
				
				<c:if test="${page.totalPage > 1}">
				<div class="am-fr">
					<ul class="am-pagination ">
						<li class="am-pagination-first"><a href="javascript:gridPage(1);">&lt;&lt;</a></li>
						<c:forEach var="i" begin="${page.pageNumber - 7 < 1 ? 1 : page.pageNumber - 7}" end="${page.pageNumber + 7 > page.totalPage ? page.totalPage : page.pageNumber + 7}">
						<c:if test="${page.pageNumber == i}"><li class="am-active"><a class="am-active" href="javascript:void(0);">${i}</a></li></c:if>
						<c:if test="${page.pageNumber != i}"><li><a href="javascript:gridPage(${i});">${i}</a></li></c:if>
						</c:forEach>
						<li class="am-pagination-last"><a href="javascript:gridPage(${page.totalPage});">&gt;&gt;</a></li>
					</ul>
				</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
</body>

	<script>

		$("#datetime").datetime({
			type:"datetime",
			value:[2020,08,1,01,01]
		})
		
		$("#datetime2").datetime({
			type:"datetime",
			value:[2020,12,31,23,59]
		})


		
	</script>
	

</html>