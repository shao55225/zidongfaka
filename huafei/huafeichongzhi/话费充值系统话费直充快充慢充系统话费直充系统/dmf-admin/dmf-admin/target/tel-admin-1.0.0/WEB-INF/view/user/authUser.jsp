<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  $(function(){
	  <c:forEach items="${urs}" var="ur">
	  $("#auth_${ur.resources_id}").prop("checked", true);
	  <c:if test="${ur.auth == 'R,W'}">
	  $("#auth_${ur.resources_id}_R").prop("checked", true);
	  $("#auth_${ur.resources_id}_W").prop("checked", true);
	  </c:if>
	  <c:if test="${ur.auth == 'R'}">
	  $("#auth_${ur.resources_id}_R").prop("checked", true);
	  </c:if>
	  <c:if test="${ur.auth == 'W'}">
	  $("#auth_${ur.resources_id}_W").prop("checked", true);
	  </c:if>
	  </c:forEach>
  });
  
  function checkGroup(i) {
	  var b = $("#group_" + i)[0].checked;
	  var cks = $(".item_" + i).find("input[type='checkbox']").each(function(){
		  $(this).prop("checked", b);
	  });
  }
  </script>
</head>
<body>
<div class="am-cf admin-main">

	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>用户权限管理</small>&nbsp;/&nbsp;
 			<small>用户管理</small>&nbsp;/&nbsp;
 			<small>设置权限</small>&nbsp;/&nbsp;
 			<small>${u.nickname}</small>
 		</div>
	</div>
	
	<form action="${ctx}/user/saveAuth" method="post">
	<div class="am-g" style="margin-bottom:10px;">
		<div class="am-u-lg-5 am-u-md-5">
			<input name="userId" type="hidden" value="${u.id}"/>
			<button class="am-btn am-btn-success" id="authBtn">保存权限设置</button>
		</div>
	</div>
	
	<div class="am-g">
		<div class="am-u-lg-5 am-u-md-5">
			<ul class="am-list am-list-static">
			  <c:set var="groupName" value="" />
			  <c:set var="groupId" value="0" />
			  <c:forEach items="${rs}" var="r">
			  	<c:if test="${groupName != r.group_name}">
			  		<c:set var="groupName" value="${r.group_name}" />
			  		<c:set var="groupId" value="${groupId + 1}" />
			  		<li style="padding-left:10px;">
			  			<label style="font-weight: normal;margin-bottom:0px;">
					      	<input type="checkbox" id="group_${groupId}" onchange="checkGroup(${groupId});">
					      	${r.group_name}
				      	</label>
			  		</li>
			  	</c:if>
			    <li style="padding-left:50px;" class="item_${groupId}">
			    	<label style="font-weight: normal;margin-bottom:0px;">
				    	<input type="checkbox" id="auth_${r.id}" name="auth" value="${r.id}">
				    	${r.name}
			    	</label>
			    	
			    	<div style="float:right;margin-right:10px;">
				    	<label style="font-weight: normal;margin-bottom:0px;">
				    		<input type="checkbox" id="auth_${r.id}_R" name="authRW" value="${r.id}_R">读权限
				    	</label>
				    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	<label style="font-weight: normal;margin-bottom:0px;">
				    		<input type="checkbox" id="auth_${r.id}_W" name="authRW" value="${r.id}_W">写权限
				    	</label>
			    	</div>
			    </li>
			  </c:forEach>
			</ul>
		</div>
	</div>
	</form>
	
</div>
</body>
</html>
