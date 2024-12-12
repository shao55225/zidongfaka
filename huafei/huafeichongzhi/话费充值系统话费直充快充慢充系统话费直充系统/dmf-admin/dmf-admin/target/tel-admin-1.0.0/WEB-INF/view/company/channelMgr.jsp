<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>

</head>
<body>

<script type="text/javascript">

/**
document.getElementById("content-page").onload=function (){
	
	  var iFrame=document.getElementById('content-page');
	  var icontent=iFrame.contentWindow;
	  var $ibody=icontent.$;
	 // window.inputVal=$ibody;
	  var ss= $ibody("td[id='code']");
	  
	  console.log(ss);
	  
}
**/

</script>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>商户通道管理</small>&nbsp;/&nbsp;
 			<small>通道分配</small>
 		</div>
	</div>
	
	
	<div class="am-g" style="font-size:12px;">
		<div class="am-u-sm-12 am-scrollable-horizontal">
			<table class="am-table am-table-striped am-table-hover am-table-striped am-text-nowrap">
				<thead>
					<tr>
						<th>通道类型</th>
						<th>通道昵称</th>
						<th>通道代码</th>
						<th>费率</th>
						<th>状态</th>
						<th class="table-set">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page}" var="c" varStatus="i">
					<tr ${c.status != '正常' ? 'style="color:red;"' : ''}>
					
						<td>
							${c.type}
						</td>
						
						<td>
							${c.nickname}
							
						</td>
						
						<td>${c.code} </td>
						
						<td  contenteditable='true' id="rebate${i.index}">${c.rebate}</td>
						
						<td  contenteditable='true' id="status${i.index}">${c.status}</td>
							<!-- href="${ctx}/company/editCompany/${c.id}"  -->
							
						<td><a  onclick="submitData('${i.index}','${c.code}','${c.id}');"   class="am-btn am-btn-secondary am-btn-xs">保存</a></td> 
						
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<input type="hidden" value="${c.id}" />
	
</div>

	<div class="am-g am-margin-top" style="margin-bottom:30px;">
		
					<div class="am-u-sm-8 am-u-sm-push-4">
					
						<input type="hidden" name="c.id" value="${c.id}" />
						
						<!--
						<input type="submit" class="am-btn am-btn-primary"  onclick="test();" value="确认保存" />&nbsp; 
						 -->
						<a class="am-btn am-btn-success" onclick="javascript:history.go(-1);">返回</a>
						
				</div>
	 </div>

</body>

  <script type="text/javascript">
  
  function submitData(id,code,infoid){
	
	//费率
	var rebates=$("#rebate"+id);
	  
	var rebate=rebates[0].innerText;
	 
	//状态
	var statuses=$("#status"+id);
	  
    var status=statuses[0].innerText;
	 
	  $.ajax({ url:"${ctx}/company/channelSave",
		  
			async:true,
			
		    data:{
		    	
			 	 "id" :infoid,
			 	 "rebate" :rebate,
			 	 "status" :status,
			 	 "code" :code,
			 	 
			},
			
		    type:"POST", 
		    
			success:function(result){
				
				if(result.result="success"){
					
					alert("保存成功");
					
				   //location.reload();
						
				}
				
				
			}
			
		});
	
	  
  }
	
  </script>
	
				
</html>