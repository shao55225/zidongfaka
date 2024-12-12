<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="apple-touch-icon-precomposed" href="${ctx}/resource/images/app-icon72x72@2x.png">
<link rel="stylesheet" href="${ctx}/resource/css/amazeui.min.css"/>
<link rel="stylesheet" href="${ctx}/resource/css/admin.css"/>
<link rel="stylesheet" href="${ctx}/resource/css/validateCss.css"/>
<link rel="stylesheet" href="${ctx}/resource/css/demo.css"/>
<link rel="stylesheet" href="${ctx}/resource/css/dateTime.css"/>
  <!--import layer ui js and css-->
  
<script src="${ctx}/resource/plugins/jquery/jquery-1.9.1.min.js"></script>
<script src="${ctx}/resource/plugins/amazeui.min.js"></script>
<script src="${ctx}/resource/plugins/validateJs.js"></script>
<script src="${ctx}/resource/plugins/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/resource/plugins/jquery-1.10.2.min.js" ></script>
<script src="${ctx}/resource/plugins/jquery-ui.js" ></script>
    <!--End Framework-->
<script src="${ctx}/resource/plugins/jquery.ffform.js" type="text/javascript"></script>
    <!--import layer ui js and css-->
<script src="${ctx}/resource/plugins/layer.js" ></script> 
      <!-- import qr code -->
<script src="${ctx}/resource/plugins/jquery.qrcode.min.js" ></script> 

<script type="text/javascript">
function deleteItem(url) {
	if (confirm("您确认要删除吗？删除后将无法恢复哦~")) {
		location.href = url;
	}
}
</script>