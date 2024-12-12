<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0042)flipin.html -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="_meta.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>测试二维码支付页面</title>
	<script src="${ctx}/resource/plugins/jquery3.3.js"></script>
	<script src="${ctx}/resource/plugins/popper.min.js"></script>
	<script src="${ctx}/resource/plugins/bootstrap.min.js"></script>
	<script src="${ctx}/resource/plugins/jquery.qrcode.min.js" ></script> 
	<script src="https://cdn.bootcss.com/blueimp-md5/2.10.0/js/md5.js"></script>
	<script type="text/javascript" src="${ctx}/resource/plugins/qrcode.js"></script>
	<link rel="stylesheet" href="${ctx}/resource/css/bootstrap1.min.css">
	<link rel="stylesheet" href="${ctx}/resource/css/tpay.css">
	
</head>
<style type="text/css">
	.margin-top{ margin-top:1em}
	.myContent{width: 90%;margin-left: 5%;}
	.modal-dialog{margin: auto;}
	.mylable{min-width: 118px;}
	.input-group-text{border-top-right-radius: 0;border-bottom-right-radius: 0;border-right: 0}
	.form-control{border-top-right-radius: .25rem;border-bottom-right-radius: .25rem}
	.myInput{vertical-align: middle;}
	#qr img{max-width: 100%;display: inline-block!important;}
	@media screen and (min-width:560px){
		/* .mylable {
          margin-left: -24px;
         } */
		.pcInput{
			min-width: 200px;;
		}
	}

</style>

  <script type="text/javascript">
    
    
    //发送异步请求
  	function submitData() {
    	
  		 var v1= md5('123456'); //56b21847ed32d2d96cf74077b22342eb
  	      console.log(v1);
    	
  		var date=new Date();
  		var time=date.getTime();
  		var number=Math.round(Math.random(100)*100);
  		var str=time+"-"+number;
  		
  		var s_id="15859036582004330878";
  		var privateKey="TOu9LhmzIaO2PFINumTjfCLsNIM7cadfSmng8GKVhGuquF7wKitm65BcdlLc3snf";
  		var money="1";
  		var nextUrl="http://www.baidu.com";
  		var apiordersn=str;
  		
  		var signStr=money+nextUrl+apiordersn+s_id+privateKey;
  		var sign=md5(signStr).toUpperCase();
  		
  		/**
        String s_id = getPara("s_id");
        String money = getPara("amount");
        String nextUrl = getPara("notify_url");
        String sign = getPara("sign");
        String apiordersn = getPara("order_no");
        **/
        //money + nextUrl + apiordersn + s_id + md5Key;
    	//var urls="https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx19165018830591e8001f5a201606885300&package=172503682&redirect_url=https%3A%2F%2Funipay.10010.com%2FudpNewPortal%2Fminiwappayment%2FweixinH5ReturnPre%3FpayStr%3Dkpqwk1dRWZcaYq86pRzYprl34279ECFN5EW3jWD1pWAWbZLQOzuDOtp%252FJYViKi42cfLsasGRQQOTRWRdu9DcM11NeALs2VLM";
  	    
    	$.ajax({
            type: 'GET',
            url: "${ctx}/order/create",
            data:{
            	s_id:s_id,
            	amount:money,
            	notify_url:nextUrl,
            	sign:sign,
            	order_no:apiordersn,
            	
            },
            success: function(data, status, xhr){
            	if(data.result=="SUCCESS"){
            		window.location=data.data.qrCode;
            		
            	}
               console.log(data);
            },
            
            error: function(xhr, type){
                window.location.reload();
            }
        
        
		})
  
    	
    }

    </script>
<body>
<div class="form text-center myContent">
	<br>
	<span class="label label-default">
			三网话费/微信(持续更新!)<br/>
		</span>
	<br />
	<div align="center" id="qr" class="margin-top" style="display:none;text-align: center">

	</div>
	<br>
	<div class="form-group margin-top">
		<button type="button" class="btn btn-primary" id="btn_submit" onClick="submitData();">
			- 获取支付最新二维码 -
		</button>
	</div>



<!-- 下面是loading的模态框 -->
<div class="modal fade" id="loading" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop='static' data-keyboard='false'>
	<div class="modal-dialog" style="width:200px">
		<div class="modal-content">
			<div class="modal-body spinner center text-center">
				<div class="bounce1"></div>
				<div class="bounce2"></div>
				<div class="bounce3"></div>
			</div>
		</div>
	</div>
</div>

</body>
<script src="${ctx}/resource/plugins/clipboard.min.js"></script>
<script>

    var check_thread=0;
    $('#loading').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#loading .modal-dialog').height() / 2;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight
        });
    });
    
    var qrcode = new QRCode(document.getElementById("qr"), {
        width : 300,
        height : 300
    });

    var qr_mark_sell='';
    var qr_channel='';
    var qr_task=null;
    var qr_times=0;
    var over_time='180';
    over_time=parseInt(over_time);

	var tourl="";

    // function copyUrl2(){
    //     var Url2=document.getElementById("copyInpout");
    //     Url2.select(); // 选择对象
    //     document.execCommand("Copy"); // 执行浏览器复制命令
    //     alert("付款链接复制成功！点击打开支付宝，到好友界面点击任意好友或可发信息的公众号发送链接，点击付款链接进行付款！");
    // }

    function openali() {
        window.location.href = tourl;
        // window.location.href = tourl;
    }

    var clipboard = new ClipboardJS('#copy_url',{
        text: function() {
          var Url2=  $("#copyInpout").val();
            return Url2;
        }});
    clipboard.on('success', function(e) {
        alert("付款链接复制成功！点击打开支付宝，到好友界面点击任意好友或可发信息的公众号发送链接，点击付款链接进行付款！");
    });
    clipboard.on('error', function(e) {
        alert("付款链接复制成功！点击打开支付宝，到好友界面点击任意好友或可发信息的公众号发送链接，点击付款链接进行付款！");
    });

    function getQr(){
        qr_mark_sell='';
        qr_channel='';
        qr_task=null;
        qr_times=0;
        if(check_thread>0){
            $("#pay_status").text("支付结果查询中...");
            clearTimeout(check_thread);
            check_thread=0;
        }

        var money=$("#money").val()*100;
        var user_name=$("#user_name").val();
        var channel = $("input[name='channel']:checked").val();
        if(channel=="weibo"&&navigator.userAgent.indexOf('Android')<=-1&&navigator.userAgent.indexOf('iPhone')<=-1){
            alert("此通道请在手机浏览器测试");
            return;
        }
        if(money<1){
            alert("兄弟，这样不好哦~~嘿嘿");
            return;
        }

        $('#loading').modal('show');
        $("#qr").css("display","none");

        //alert(channel+"|"+email+"|"+descp+"|"+money);

        var url="/api/Pay/pay";
        var params = {command:"applyqr",money:money,user_name:user_name,channel:channel};
        $.post(url,params,function(d){
            d=passJson(d);
            if(d.status!=1){
                alert(d.message);
                setTimeout("$('#loading').modal('hide')",800);
            }else{
                qr_channel=d.data.channel;
                qr_mark_sell=d.data.mark_sell;

                if (qr_channel=="weibo"){
                    if (d.data.html==''||d.data.html==null){
                        alert("账号离线,请联系管理员测试");
						return;
					}
                    if(navigator.userAgent.indexOf('Android')>-1||navigator.userAgent.indexOf('iPhone')>-1){
                        localStorage.removeItem("callbackhtml");
                        localStorage.setItem("callbackhtml", d.data.html);
                        window.location.href='/pay/index/callback';
                        // window.open('/pay/index/callback');
                        var id=0;
                        check_thread=setTimeout("isPayed('"+qr_mark_sell+"','"+id+"')",6000);
                        return;
					}else {
                        alert("请在手机端测试")
						return;
					}

                }
                if (d.data.url){
                    showQr(d.data.url,d.data.mark_sell,d.data.apply_time,d.data.id,d.data.url_2,d.data.url_3);
                }else {
                    //每2秒去获取二维码
                    qr_task=window.setInterval(getmqr, 2000);
                }
            }
        })
    }



    $(document).ready(function() {
        $('input[type="radio"][name="channel"]').change(function() {
            if (this.value == 'tbpay') {
     			$("#money").val("5");
     			$("#money").attr("readonly",true);
            }else  {
                $("#money").val("1");
                $("#money").attr("readonly",false);
            }
        });
    });


</script>

</html>






