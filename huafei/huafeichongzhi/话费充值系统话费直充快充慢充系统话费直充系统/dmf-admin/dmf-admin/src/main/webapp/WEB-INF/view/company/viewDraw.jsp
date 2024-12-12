<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <title>提现详情 - ${d.company_name}</title>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  function confirmOpt(url, title) {
	  if (confirm(title)) {
		location.href = url;
      }
  }
  </script>
</head>
<body>
<div class="am-cf admin-main">

	<fieldset>
		<legend>
			<strong style="color:red;">&lt;商户&gt;</strong>
			提现基本信息
		</legend>
		<div class="am-g">
		  <div class="am-u-sm-2">商户名称：</div>
		  <div class="am-u-sm-4">${d.company_name}</div>
		  <div class="am-u-sm-2">提现金额：</div>
		  <div class="am-u-sm-4">${d.draw_money}</div>
		</div>
		
		<div class="am-g">
		  <div class="am-u-sm-2">提现方式：</div>
		  <div class="am-u-sm-4">${d.type}</div>
		  <div class="am-u-sm-2">账户姓名：</div>
		  <div class="am-u-sm-4">
		  	<c:if test="${sysUser == d.sys_username}">${d.realname}</c:if>
		  	<c:if test="${sysUser != d.sys_username}">***</c:if>
		  </div>
		</div>
		
		<div class="am-g">
		  <div class="am-u-sm-2">提现账号：</div>
		  <div class="am-u-sm-4">
		  	<c:if test="${sysUser == d.sys_username}">${d.account}</c:if>
		  	<c:if test="${sysUser != d.sys_username}">******</c:if>
		  </div>
		  <div class="am-u-sm-2">开户行：</div>
		  <div class="am-u-sm-4">
		  	<c:if test="${sysUser == d.sys_username}">${d.open_bank}</c:if>
		  	<c:if test="${sysUser != d.sys_username}">******</c:if>
		  </div>
		</div>
		
		<div class="am-g">
		  <div class="am-u-sm-2">状态：</div>
		  <div class="am-u-sm-4">${d.status}</div>
		  <div class="am-u-sm-2">手续费：</div>
		  <div class="am-u-sm-4">${d.charge_money}</div>
		</div>
		
		<div class="am-g">
		  <div class="am-u-sm-2">申请时间：</div>
		  <div class="am-u-sm-4">${d.addtime}</div>
		  <div class="am-u-sm-2">结束时间：</div>
		  <div class="am-u-sm-4">${d.drawtime}</div>
		</div>
		
	   <c:if test="${d.status == '待审核'}">
		<div class="am-g">
		  <div class="am-u-sm-2">备注：</div>
		  <div class="am-u-sm-10">
		    <c:choose>
				<c:when test="${sysUser == d.sys_username}">
			  	<script type="text/javascript">
			  	function setRemark() {
			  		$.post("${ctx}/company/setDrawRemark", {"id" : ${d.id}, "remark" : $("#drawRemark").val()}, function(data){});
			  	}
			  	</script>
			  	<textarea id="drawRemark" rows="2" style="width:80%;" placeholder="请输入拒绝理由等备注信息" onblur="setRemark();">${d.remark}</textarea>
			  	</c:when>
			  	
			  	<c:otherwise>
					${d.remark}
				</c:otherwise>
		  	</c:choose>
		  </div>
		</div>
	   </c:if>


		<c:if test="${d.status == '已审核' || d.status=='已拒绝'}">
			<div class="am-g">
				<div class="am-u-sm-2">备注：</div>
				<div class="am-u-sm-10">
					<textarea id="drawRemark" rows="2" style="width: 80%;"
						placeholder="请输入拒绝理由等备注信息">${d.remark}</textarea>
				</div>
			</div>
			<div class="am-u-sm-12">
				<button type="submit" class="am-btn am-btn-secondary" onclick="setRemark();">编辑评论</button>
			</div>
         <script type="text/javascript">
			  	function setRemark() {
			  		$.post("${ctx}/company/setDrawRemark", {"id" : ${d.id}, "remark" : $("#drawRemark").val()}, function(data){});
			  	}
		 </script>
		</c:if>

			<c:if test="${d.status == '待审核'}">
		<div class="am-g" style="margin-top:15px;">
			<div class="am-u-sm-12">
				<c:choose>
					<c:when test="${sysUser == d.sys_username}">
						<a href="javascript:confirmOpt('${ctx}/company/approveDraw/${d.id}', '你确认审核通过吗？通过后将会向其账号汇款');" class="am-btn am-btn-success">审核通过</a>
						&nbsp;&nbsp;&nbsp;
						<a href="javascript:confirmOpt('${ctx}/company/rejectDraw/${d.id}', '你确认拒绝申请吗？拒绝后提现金额将退回到余额中');" class="am-btn am-btn-danger">拒绝申请</a>
						&nbsp;&nbsp;&nbsp;
						<a href="javascript:confirmOpt('${ctx}/company/unlockDraw/${d.id}', '你确认解锁操作吗？解锁后其他人将可以操作该笔订单');" class="am-btn am-btn-primary">解锁</a>
					</c:when>
					
					<c:when test="${d.sys_username == null or d.sys_username == ''}">
						<a href="javascript:confirmOpt('${ctx}/company/lockDraw/${d.id}', '你确认锁单操作吗？锁单后其他人将不能操作该笔提现');" class="am-btn am-btn-primary">锁单</a>
						<strong style="color:red;">锁单以后，其他人将不能操作该笔提现</strong>
					</c:when>
					
					<c:otherwise>
						<strong style="color:red;">该笔提现已被用户【${d.sys_username}】锁单，在他“解锁”之前您无法操作。</strong>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		</c:if>
		
		<div class="am-g">
		  <div class="am-u-sm-2">实际打款金额：</div>
		  <div class="am-u-sm-10">${d.draw_money} - ${d.charge_money} = <strong style="color:red;">${d.draw_money - d.charge_money}</strong></div>
		</div>
		
	</fieldset>
	
	<table class="am-table" style="font-size:12px;">
		<thead>
			<tr>
				<th>设备号</th>
				<th>状态</th>
				<th>交易号</th>
				<th>金额</th>
				<th>手续费</th>
				<th>备注</th>
				<th>插入时间</th>
				<th>结束时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${items}" var="t">
			<tr>
				<td>${t.device_name}</td>
				<td>
					${t.status}
					<c:if test="${t.status == '转账失败'}">
					<a href="javascript:confirmOpt('${ctx}/company/reloadDraw/${t.id}', '您确认要重新发起转账吗？');"><i class="am-icon-refresh"></i></a>
					</c:if>
				</td>
				<td>${t.trade_no}</td>
				<td>${t.draw_money}</td>
				<td>${t.charge_money}</td>
				<td>${t.remark}</td>
				<td>${t.addtime}</td>
				<td>${t.drawtime}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

</div>
</body>
</html>
