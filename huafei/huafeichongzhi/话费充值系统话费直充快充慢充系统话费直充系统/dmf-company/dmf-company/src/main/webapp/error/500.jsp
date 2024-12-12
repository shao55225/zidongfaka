<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../WEB-INF/view/_meta.jsp" %>
<script type="text/javascript">
$(function(){
	$("#my-modal").modal();
	$(".am-modal-btn,.am-close").click(function(){
		history.go(-1);
	});
});
</script>
</head>
<body>


<div class="am-modal" tabindex="-1" id="my-modal" style="top:30px;">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">系统提示
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
    	<div class="am-g">
		  <div class="am-u-sm-3">
		  	<img src="${ctx}/resource/images/error.jpg" style="width:100%;height:100%;">
		  </div>
		  <div class="am-u-sm-9">
		  	<strong>500</strong>
		  	服务器内部错误，请联系技术小哥
		  </div>
		</div>
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>



</body>
</html>