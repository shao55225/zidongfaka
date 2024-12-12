<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="apple-touch-fullscreen" content="yes"/>
    <meta name="format-detection" content="telephone=no,email=no"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="white">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-control" content="no-cache">
    <meta http-equiv="Cache" content="no-cache">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>支付宝扫码支付</title>
   <!--  --> <link href="${pageContext.request.contextPath}/css/pay.css" rel="stylesheet" media="screen">
    <link href="http://47.106.200.35:8080/API/css/pay.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="https://cdn.staticfile.org/jquery/1.11.1/jquery.min.js"></script>
    <link rel="icon" type="image/png" href="/assets/i/favicon.png">
	<link rel="apple-touch-icon-precomposed" href="/assets/i/favicon.png">
	<meta name="apple-mobile-web-app-title" content="支付收银台" />
</head>
<body>
<div class="body">
    <h1 class="mod-title">
        <span class="ico_log ico_alipay"></span>
    </h1>

    <div class="mod-ct">
        <div class="order">
        </div>
        <div class="amount" id="money">￥${o.order_money }</div>
        
        <div class ="paybtn" >
          <a href="#" id="alipaybtn" class="btn btn-primary" >启动支付宝App支付</a> 
  
        </div>

	
	  <div class="time-item" style = "padding-top: 10px">
           
            <div class="time-item" id="msg"><h1 style="color:#e50e0e">请严格按照显示金额付款哦~，否则后果自负</h1> </div>

        </div>
        


    </div>
    <div class="foot">
        <div class="inner" style="display:none;">
            <p>手机用户可保存上方二维码到手机中</p>
            <p>在微信扫一扫中选择“相册”即可</p>
			            <p>亚博网络提供www.yaboweb.cn</p>

            <p></p>
        </div>
    </div>
</div>
</body>
</html>
