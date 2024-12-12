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
	  $("#admin-content").css("height", h - 70);
	  $("#admin-menu").css("height", h - 70);
  });
  function goPage(targetPage) {
	  $("#content-page").attr("src", targetPage);
  }
  </script>
</head>
<body style="overflow-y:hidden">
<header class="am-topbar admin-header">
  <div class="am-topbar-brand">
    <strong>运营商</strong> <small>后台管理系统</small>
  </div>

  <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

  <div class="am-collapse am-topbar-collapse" id="topbar-collapse">

    <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
    <li><a href="${ctx}/error/clause.html" target="_blank" id="tz"><span class="am-icon-envelope-o"></span> 使用须知 <span class="am-badge am-badge-warning" id="noticeCount"></span></a></li>
      <li class="am-dropdown" data-am-dropdown>
        <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
          <span class="am-icon-users"></span> <shiro:principal></shiro:principal> <span class="am-icon-caret-down"></span>
        </a>
        <ul class="am-dropdown-content">
          <li><a href="javascript:goPage('${ctx}/account/config');"><span class="am-icon-user"></span>个人配置 </a></li>
          
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
				
				<li>
					<a class="am-cf" data-am-collapse="{target: '#collapse-order'}">
						<span class="am-icon-book am-icon-fw"></span>
						订单中心
						<span id="hr" class="am-icon-angle-down am-fr am-margin-right"></span>
					</a>
					<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-order">
						 <shiro:hasRole name="tianhao">
							
							<li><a href="javascript:goPage('${ctx}/tianhao/address');"  class="am-cf menuBtn"> 地址管理</a></li>
						 	<li><a href="javascript:goPage('${ctx}/tianhao/list');"  class="am-cf menuBtn"> 数据统计</a></li>
						 
						 </shiro:hasRole>
					
						 <li><a href="javascript:goPage('${ctx}/order/listOrder');"  class="am-cf menuBtn"> 订单列表</a></li>
						 
					</ul>
				</li>
			
				<li>
					<a class="am-cf" data-am-collapse="{target: '#collapse-auth'}">
						<span class="am-icon-money am-icon-fw"></span>
						财务中心
						<span id="hr" class="am-icon-angle-down am-fr am-margin-right"></span>
					</a>
					<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-auth">
					
						 <li><a href="javascript:goPage('${ctx}/finance/listBill');"  class="am-cf menuBtn"> 我的账单</a></li>
						 
					</ul>
				</li>
	
				 
			</ul>
			
		</div>
	</div>

	<div class="admin-content"  id="admin-content">
		<iframe id="content-page" src="${ctx}/welcome" style="height:100%;width:100%;" frameborder="0" ></iframe>
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

