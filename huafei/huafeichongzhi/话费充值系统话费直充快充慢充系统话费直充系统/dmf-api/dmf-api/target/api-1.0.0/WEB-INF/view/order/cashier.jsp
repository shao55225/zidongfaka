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
    <link href="${pageContext.request.contextPath}/css/pay.css" rel="stylesheet" media="screen">
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
        <div class ="paybtn" style="display:none;">
        	<a href="#" id="alipaybtn" class="btn btn-primary" target="_blank">启动支付宝App支付</a>
        </div>
        <div class="qrcode-img-wrapper" data-role="qrPayImgWrapper">
            <div class="qrcode-img-area">
                <div id="loading" style="font-weight:bolder;color:red;font-size:14px;">二维码图片加载中...</div>
                <div style="position: relative;display:inline-block;">
                    <img id="show_qrcode" width="300" height="300" src="${pageContext.request.contextPath}/images/loading.gif" style="display:block;">
                    <img onclick="$('#use').hide()" id="use" src="${pageContext.request.contextPath}/images/logo_alipay.png" style="position:absolute;top:50%;left:50%;width:32px;height:32px;margin-left:-16px;margin-top:-16px">
                </div>
            </div>
        </div>
        <div class ="payweixinbtn" style = "display: none;"><a href="javascript:return false;" class="btn btn-primary">1.先保存二维码到手机</a></div>
        <div class ="payweixinbtn" style = "display: none;padding-top:10px"><a href="weixin://" class="btn btn-primary">2.打开微信，扫一扫本地图片</a></div>

        <div class="time-item" style = "padding-top: 10px">
            <div class="time-item" id="msg"><h1>付款即时到账 未到账可联系客服</h1> </div>
            <div class="time-item"><h1>订单:${o.out_order_no }</h1> </div>
            <strong id="hour_show">00时</strong>
            <strong id="minute_show">00分</strong>
            <strong id="second_show">00秒</strong>
        </div>
	
        <div class="tip">
            <div class="ico-scan"></div>
            <div class="tip-text">
                <p id="showtext">打开支付宝 [扫一扫]</p>
            </div>
        </div>

    </div>
    <div class="foot">
        <div class="inner" style="display:none;">
            <p>手机用户可保存上方二维码到手机中</p>
            <p>在微信扫一扫中选择“相册”即可</p>
            <p></p>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
 var myTimer;
 var queryOrderInt;
 $(function(){
	 get_qrcode();
 });

 function timer(intDiff) {
     myTimer = window.setInterval(function () {
         var day = 0,
             hour = 0,
             minute = 0,
             second = 0;//时间默认值
         if (intDiff > 0) {
             day = Math.floor(intDiff / (60 * 60 * 24));
             hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
             minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
             second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
         }
         if (minute <= 9) minute = '0' + minute;
         if (second <= 9) second = '0' + second;
         $('#hour_show').html('<s id="h"></s>0' + hour + '时');
         $('#minute_show').html('<s></s>' + minute + '分');
         $('#second_show').html('<s></s>' + second + '秒');
         if (hour <= 0 && minute <= 0 && second <= 0) {
             qrcode_timeout();
             clearInterval(myTimer);
         }
         intDiff--;     
     },1000);
 }
 
 function get_qrcode(errNum){
	$("#loading").hide();
	if (${time} > 0) {
		$("#show_qrcode").attr("src", "${pageContext.request.contextPath}/order/qr/${o.token}");
	}
	query_order();
	timer(${time});
	if(isMobile()){
		if(data.channel=="alipay"){
			$("#alipaybtn").attr("href",data.qrcode).parent().show();
		}else if(data.channel=="wechat"){
			$(".payweixinbtn").show();
			$(".inner").show();
		}
	}
 }

 function query_order(){
	 queryOrderInt=setInterval(function(){
		 $.get("${pageContext.request.contextPath}/order/status/${o.token}", function(data){
			if(data == "已支付"){
				clearInterval(queryOrderInt);
				clearInterval(myTimer);
				$("#show_qrcode").attr("src","${pageContext.request.contextPath}/images/pay_ok.png")
				$("#use").remove();
                $("#money").text("支付成功");
                $("#msg").html("<h1>即将返回商家页</h1>");
                $(".paybtn").html('<a href="${order.return_url}" class="btn btn-primary">返回商家页</a>').show();
                setTimeout(function(){
                	location.replace("${order.return_url}");
                }, 15000);
			}
		});
		
		
	 },1500);
 }

 function qrcode_timeout(){
     $('#show_qrcode').attr("src","${pageContext.request.contextPath}/images/qrcode_timeout.png");
     $("#use").hide();
     $('#msg').html("<h1>付款码已过期，请重新下单</h1>");
 }
 
 function isWeixin() { 
     var ua = window.navigator.userAgent.toLowerCase(); 
     if (ua.match(/MicroMessenger/i) == 'micromessenger') { 
         return true;
     } else { 
         return false;
     } 
 }

 function isMobile(){
     var ua = navigator.userAgent.toLowerCase();
     _long_matches = 'googlebot-mobile|android|avantgo|blackberry|blazer|elaine|hiptop|ip(hone|od)|kindle|midp|mmp|mobile|o2|opera mini|palm( os)?|pda|plucker|pocket|psp|smartphone|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce; (iemobile|ppc)|xiino|maemo|fennec';
     _long_matches = new RegExp(_long_matches);
     _short_matches = '1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|e\-|e\/|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\-|2|g)|yas\-|your|zeto|zte\-';
     _short_matches = new RegExp(_short_matches);
     if (_long_matches.test(ua)) {
         return true;
     }
     user_agent = ua.substring(0, 4);
     if (_short_matches.test(user_agent)) {
         return true;
     }
     return false;
 }
</script>
</html>
