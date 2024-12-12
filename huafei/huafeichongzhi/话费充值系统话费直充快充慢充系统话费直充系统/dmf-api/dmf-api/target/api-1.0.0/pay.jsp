<%@page import="net.sf.json.JSONObject" %>
<%@page import="com.xunpay.money.utils.HttpClientHelper" %>
<%@page import="com.xunpay.money.utils.EncryptUtils" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
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
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>支付请求</title>
    <!--  -->
    <link href="${pageContext.request.contextPath}/css/pay.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="https://cdn.staticfile.org/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/plugins/layer.js"></script>
    <link rel="icon" type="image/png" href="/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/assets/i/favicon.png">
    <meta name="apple-mobile-web-app-title" content="支付收银台"/>
</head>
<body>
<script type="text/javascript">
    //发送异步请求
    function submitData() {
        var second = 15;
        var content = "秒订单匹配中，请稍等";
        layer.msg(content, {
            time: 10000,
            shade: 0.6,
            success: function (layero, index) {
                var msg = layero.text();
                var i = second;
                var timer = null;
                var fn = function () {
                    layero.find(".layui-layer-content").text(i + ' ' + msg + ' ');
                    if (!i) {
                        layer.close(index);
                        clearInterval(timer);
                    }
                    i--;
                };
                timer = setInterval(fn, 1500);
                fn();
            },
        }, function () {
            $("#btnSave").removeAttr("disabled");
        });
        var url = window.location.href;
        var mycars = new Array()
        var attr = url.split("=");
        var orderId = attr[1];
        $.get("${ctx}/api/order/pay", {
            "orderId": orderId,
        }, function (res) {

            if (res.result == "SUCCESS") {
                if (res.data == '' || res.data == null) {
                    alert("获取支付链接失败，请重新获取!");
                } else {
                    var u = navigator.userAgent;
                    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

                    var str = res.data;
                    var reg = RegExp(/weixin:/);
                    if(str.match(reg)){
                        if (isAndroid){
                            window.location.href = '${ctx}/api/cs.jsp?appid='+res.data+'';
                        }else {
                            window.location.href = res.data;
                        }
                    }else {
                        window.location.href = res.data;
                    }

                    //window.location.replace(res.data);
                    //response.sendRedirect(res.data);
                    // top.location.href=res.data;

                }
            } else {
                layer.msg("获取链接失败!", {
                    time: 2000,
                    btn: ['知道了'],
                    btnAlign: 'c',
                    anim: 6
                });
            }
        });
    }


</script>
<div class="body">
    <h1 class="mod-title">
        <span class="ico_log ico_wsechat">支付请求页</span>
    </h1>
    <div class="mod-ct">
        <div class="order">
        </div>
        <div class="paybtn">
            <button type="button" id="alipaybtn" class="btn btn-primary" onClick="submitData();">发起支付</button>
            </br>
        </div>
        <div>
            <span style="color:red">
             温馨提示:<br/>
                1:请在1分钟内支付，超时不支付不上分,不退款！<br/>
			     2:订单匹配结束后未跳转,请重新点击支付！<br/>
                3:安卓跳转页面后，请点击启动微信支付。请勿重复点击！！！<br/>
            </span></br>
        </div>
    </div>
</div>
</body>
</html>