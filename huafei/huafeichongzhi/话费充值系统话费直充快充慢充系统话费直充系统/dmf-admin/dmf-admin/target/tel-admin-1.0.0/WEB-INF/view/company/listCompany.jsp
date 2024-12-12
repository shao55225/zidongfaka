<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  function forAlipay(id) {
	  var iWidth = 960;
	  var iHeight = 640;
	  var iTop = (window.screen.height-30-iHeight)/2;
	  var iLeft = (window.screen.width-10-iWidth)/2;
	  window.open('${ctx}/company/forAlipay/' + id, '分配支付宝', 'width='+iWidth+',height='+iHeight+',top='+iTop+',left='+iLeft+',menubar=no,toolbar=no,location=no,scrollbars=no,status=no,modal=yes');
  }
  
  function goPage(targetPage) {
	  
	  $("#content-page").attr("src", targetPage);
	  
  }
  
  function addBalance(id){
	  var money = prompt("请输入你要加的额度（减额度用负数）：");
	  if (isNaN(money)) {
		  alert("请输入正确的数字。");
	  	  return;
	  }
	  $.get("${ctx}/company/addBalance?id=" + id + "&amount=" + money, function(s){
		  if (s == "success") {
			  alert("加额成功！");
			  location.reload();
		  }
	  });
  }
  </script>
</head>
<body>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>商户管理</small>&nbsp;/&nbsp;
 			<small>支付商户列表</small>
 		</div>
	</div>
	
	<form method="post" id="form1" class="am-form">
	<div class="am-g">
		<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<a href="${ctx}/company/addCompany"><span class="am-input-group-label">新增</span></a>
			</div>
		</div>
		<div class="am-u-lg-5">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">账号/昵称</span>
				<input type="text" name="account" placeholder="请输入昵称或用户名" value="${account}" class="am-form-field"/>
			</div>
		</div>
		<div class="am-u-lg-2">
			<div class="am-input-group am-input-group-secondary am-form-group">
				<span class="am-input-group-label">状态</span>
				<select name="status" class="am-form-field">
		   			<option value="">请选择</option>
		   			<option value="正常" ${status == '正常' ? 'selected' : ''}>正常</option>
		   			<option value="禁用" ${status == '禁用' ? 'selected' : ''}>禁用</option>
		   		</select>
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
						<th>昵称</th>
						<th>用户名</th>
						<shiro:hasRole name="admin">
						<th>密码</th>
						<th>商户费率</th>
			
						<!--<th>支付密码</th> 	
									<th>支付宝扫码费率</th>
						<th>微信h5费率</th>
						<th>支付宝h5费率</th>
						
						 -->
						<!--
						<th>网关费率</th>
						<th>快捷费率</th>
						 -->
						<th>余额</th>
						</shiro:hasRole>
						<th>APPID</th>
						<th>密钥</th>
						<th>状态</th>
						<th>新增时间</th>
						<th>登陆时间</th>
							<!--<th>登陆IP</th>		 -->
						<th class="table-set">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="c" varStatus="i">
					<tr ${c.status != '正常' ? 'style="color:red;"' : ''}>
						<td>${page.pageNumber * 10 + i.index - 9}</td>
						
						<td>
							<shiro:hasRole name="admin">
							<a href="#">${c.nickname}</a>
							</shiro:hasRole>
							<shiro:hasRole name="user">
							${c.nickname}
							</shiro:hasRole>
						</td>
						<td>${c.username}</td>
						<shiro:hasRole name="admin">
						<td>${c.password}</td>
						<td>${c.self_rebate}</td>
					<!--<td>${c.pay_password}</td>
						<td>${c.wechatpcrebate}</td>
						<td>${c.alipaypcrebate}</td>
						<td>${c.wechatrebate}</td>
						<td>${c.alipayrebate}</td>
						  -->
						<!--
						<td>${c.gatewayrebate}</td>
						<td>${c.fastrebate}</td>
						-->
						<td>${c.balance}<a href="javascript:void(0);" onclick="addBalance(${c.id});">+加额</a></td>
						</shiro:hasRole>
						<td>${c.appid}</td>
						<td>${c.md5key}</td>
						<td>${c.status}</td>
						<td>${c.addtime}</td>
						<td>${c.logintime}</td>
						<!--<td>${c.loginip}</td> 		 -->
						<td>
							
							
							<a href="${ctx}/company/editCompany/${c.id}" class="am-btn am-btn-secondary am-btn-xs">编辑</a>
							
							<a href="javascript:deleteItem('${ctx}/company/deleteCompany/${c.id}');" class="am-btn am-btn-danger am-btn-xs">删除</a>
							
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
					<input name="account" value="${account}"/>
					<input name="status" value="${status}"/>
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
</html>