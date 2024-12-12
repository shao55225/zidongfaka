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
	  $.get("${ctx}/order/noticeItem/" + id, function(data) {
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
 			<small>联通验证码列表</small>
 		</div>
	</div>
	
	<form method="post" id="form1" class="am-form">
	
	<div class="am-g">
	
	<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<a href="${ctx}/order/addYzm"><span class="am-input-group-label">新增</span></a>
			</div>
		</div>

		<div class="am-u-lg-3">
		
		
		</div>
		
		<div class="am-u-lg-3">
		
				<div class="am-input-group am-input-group-secondary am-form-group">
			<span class="am-input-group-label">开始时间</span>
					
				<input type="text" name="startTime"  value="${startTime}"  placeholder=" 请选择开始日期和时间" id="datetime">
				 <!--   <i class="am-icon-calendar"></i>  -->
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

		<div class="am-u-lg-2">
			<div class="am-input-group ">
				<input class="am-btn am-btn-primary" type="submit" value="搜索"/>
				&nbsp;
				<input class="am-btn am-btn-success" type="button" value="刷新" onclick="location.reload();"/>
			</div>
		</div>
	</div>
	</form>

	<div class="am-g" style="font-size:12px;">
		<div class="am-u-sm-12 am-scrollable-horizontal">
			<table class="am-table am-table-striped am-table-hover am-table-striped am-text-nowrap">
				<thead>
					<tr>
						<th>序号</th>
						<th>验证码</th>
						<th>创建时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="ss" varStatus="i">
					<tr>
						<td>${page.pageNumber * 20 + i.index - 19}</td>
						<td>${ss.coke}</td>
						<td>${ss.addtime}</td>
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
			value:[2019,12,1,01,01]
		})
		
		$("#datetime2").datetime({
			type:"datetime",
			value:[2019,12,31,23,59]
		})


		
	</script>
	

</html>