<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="_meta.jsp" %>
  <script type="text/javascript">
  $(function(){
	  if (window != window.parent) {
  	      window.parent.location.reload();
  	      return;
  	  }
	  
	  var h = document.documentElement.clientHeight || document.body.clientHeight;
	  $("#admin-content").css("height", h - 50);
	  $("#admin-menu").css("height", h - 50);
	  
	  //用在iPhone手机
	  if (null != navigator.userAgent.match(/(iPod|iPhone|iPad)/)){
		  $("#admin-content").css("width", '100%');
		  $("#admin-content").css("height", '100%');
		  $("#admin-content").css("-webkit-overflow-scrolling", 'touch');
		  $("#admin-content").css("overflow", 'scroll');
	  } 
	  
	  drawNum();
  });
  function goPage(targetPage) {
	  $("#content-page").attr("src", targetPage);
  }
  
  var daxiao;
  function drawNum() {
	  $.get("${ctx}/drawNum", function(data){
		  $("#adNum").html(data.b);
		  $("#cdNum").html(data.a);
		  if (data.b > 0 || data.a > 0) {
			  if (daxiao != null) {
				  daxiao.pause();
				  daxiao = null;
			  }
			  daxiao = new Audio("${ctx}/resource/upload/notice.mp3");
			  daxiao.play();
		  } else {
			  daxiao.pause();
			  daxiao = null;
		  }
	  });
	  setTimeout(function(){
		  drawNum();
	  }, 20000);
  }
  
  function load()
  {
	
  	
  }
  
  </script>
</head>
<body style="overflow-y:hidden">
<header class="am-topbar admin-header">
  <div class="am-topbar-brand">
    <strong>话费系统</strong>
  </div>

  <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

  <div class="am-collapse am-topbar-collapse" id="topbar-collapse">

    <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
    <li><a href="${ctx}/system/notice/noReadList?status=0" id="tz"><span class="am-icon-envelope-o"></span> 未读公告 <span class="am-badge am-badge-warning" id="noticeCount"></span></a></li>
      <li class="am-dropdown" data-am-dropdown>
        <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
          <span class="am-icon-users"></span> <shiro:principal></shiro:principal> <span class="am-icon-caret-down"></span>
        </a>
        <ul class="am-dropdown-content">
          <li><a href="javascript:goPage('${ctx}/account/password');"><span class="am-icon-lock"></span> 修改密码 </a></li>
          <li><a href="${ctx}/account/logout"><span class="am-icon-power-off"></span> 退出</a></li>
        </ul>
      </li>
    </ul>
  </div>
</header>
<div class="am-cf admin-main">

	<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
		<div class="am-offcanvas-bar admin-offcanvas-bar" id="admin-menu">
			<ul class="am-list admin-sidebar-list">
				<!-- 后台首页  -->
				<li><a class="am-cf" href="javascript:goPage('${ctx}/welcome');"><span class="am-icon-home am-icon-fw"></span> 首页<span class="am-icon-angle-right am-fr am-margin-right"></span></a></li>
				
				<shiro:hasPermission name="订单中心">
				<li>
					<a class="am-cf" data-am-collapse="{target: '#collapse-order'}">
						<span class="am-icon-book am-icon-fw"></span>
						订单中心
						<span id="hr" class="am-icon-angle-down am-fr am-margin-right"></span>
					</a>
					<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-order">
				
						 <shiro:hasPermission name="order">
						 <li><a href="javascript:goPage('${ctx}/order/listOrder');"  class="am-cf menuBtn"> 支付订单列表</a></li>
						 <li><a href="javascript:goPage('${ctx}/order/listApiOrder');"  class="am-cf menuBtn"> 充值订单列表</a></li>
						 </shiro:hasPermission>
			 <!-- 
	 					<li><a href="javascript:goPage('${ctx}/order/listAgent');"  class="am-cf menuBtn"> 订单列表</a></li>-->
						 <!-- 	<li><a href="javascript:goPage('${ctx}/order/listYzm');"  class="am-cf menuBtn"> 防水墙验证码列表</a></li>-->
					</ul>
				</li>
				</shiro:hasPermission>
				<!-- 
				
				<shiro:hasPermission name="通道管理">
				<li>
					<a class="am-cf" data-am-collapse="{target: '#collapse-channel'}">
						<span class="am-icon-credit-card am-icon-fw"></span>
						通道管理
						<span id="hr" class="am-icon-angle-down am-fr am-margin-right"></span>
					</a>
					<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-channel">
					
					 <li><a href="javascript:goPage('${ctx}/channel/listChannel');"  class="am-cf menuBtn"> 通道列表</a></li>
						
				
					</ul>
				</li>
				</shiro:hasPermission>
				 
				 -->
				<shiro:hasPermission name="商户管理">
				<li>
					<a class="am-cf" data-am-collapse="{target: '#collapse-company'}">
						<span class="am-icon-briefcase am-icon-fw"></span>
						商户管理
						<span id="hr" class="am-icon-angle-down am-fr am-margin-right"></span>
					</a>
					<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-company">
						 <shiro:hasPermission name="company">
						 <li><a href="javascript:goPage('${ctx}/company/listCompany');"  class="am-cf menuBtn"> 商户列表</a></li>
						 
						 <!-- 
						 <li><a href="javascript:goPage('${ctx}/company/listApiCompany');"  class="am-cf menuBtn"> 话费商户列表</a></li>
						  -->
						 </shiro:hasPermission>
						 <shiro:hasPermission name="company_draw">
						 
						 
						 </shiro:hasPermission>
					</ul>
				</li>
				</shiro:hasPermission>
				
				
				
				<shiro:hasPermission name="用户权限管理">
				<li>
					<a class="am-cf" data-am-collapse="{target: '#collapse-auth'}">
						<span class="am-icon-users am-icon-fw"></span>
						用户权限管理
						<span id="hr" class="am-icon-angle-down am-fr am-margin-right"></span>
					</a>
					<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-auth">
						 <shiro:hasPermission name="user">
						 <li><a href="javascript:goPage('${ctx}/user/listUser');"  class="am-cf menuBtn"> 用户管理</a></li>
						 </shiro:hasPermission>
					</ul>
				</li>
				</shiro:hasPermission>
				
				<li>
					<a class="am-cf" data-am-collapse="{target: '#collapse-setting'}">
						<span class="am-icon-cog am-icon-fw"></span>
						系统设置
						<span id="hr" class="am-icon-angle-down am-fr am-margin-right"></span>
					</a>
					<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-setting">
						 <li><a href="javascript:goPage('${ctx}/system/listConfig');"  class="am-cf menuBtn"> 系统参数</a></li>
						 
						<!-- <li><a href="javascript:goPage('${ctx}/system/ipConfig');"  class="am-cf menuBtn"> 长效ip配置</a></li>-->
						 
						 <li><a href="javascript:goPage('${ctx}/account/password');"  class="am-cf menuBtn"> 修改密码</a></li>
						 
						 <li><a href="${ctx}/error/clause.html" data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 400, height: 225}" class="am-cf menuBtn"> 使用须知</a></li>
						 
					</ul>
				</li>
			</ul>
			
		</div>
	</div>

	<div class="admin-content"  id="admin-content"	>
		<iframe  name="content-page" id="content-page" src="${ctx}/welcome" style="height:100%;width:100%;" frameborder="1" ></iframe>
	</div>
	
	<a class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}" href="javascript:void(0);">
		<span class="am-icon-btn am-icon-th-list am-fr"></span>
	</a>
	<footer>
	  <hr>
	  <p class="am-padding-left"></p>
	</footer>
</div>
</body>
</html>

