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
    	
  		layer.msg("后台正在请求，请稍等", {
               time: 15000,
             //  btn: ['知道了'],
               btnAlign: 'c',
               anim: 6
           });
    	
        var phoneNumber=$("#phoneNumber").val();
        
        var money=$("#money").val();
        
        var channel = $("input[name='channel']:checked").val();
        
        console.log("channel======>"+channel);
        
        console.log("money======>"+money);
  		
		  	$.post("${ctx}/order/testOrder",{
		  		
		  		"phoneNumber" : phoneNumber,
		  		
		  		"money" : money,
		  		
		  		"channelCode" :channel,
		  	
		  	}, function(res){
		  		
		  		console.log(res);
		  		
		  		
		  		 if (res.result=="success"){
		  			 
		  			 window.location.href=res.data;
	                 
	                    
	                }else{
	                	
	                	 layer.msg(res.msg, {
	   	                        time: 2000,
	   	                        btn: ['知道了'],
	   	                        btnAlign: 'c',
	   	                        anim: 6
	   	                    });
	                
	     
	                	
	                }
		  		// console.log(data);
		  		
		  		
		  	});
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
	<div class="input-group justify-content-md-center margin-top">
		<label class="form-control col-md-3" style="border-radius: 0.1rem">
			<input type="radio" name="channel" value="cmcc"  class="myInput">
			移动
		</label>
		<div class="col-md-1 " style="min-height: 8px">
		</div>
		<label class="form-control col-md-3" style="border-radius: 0.1rem">
			<input type="radio" name="channel" value="cucc"  class="myInput">
			联通
		</label>

		<div class="col-md-1 " style="min-height: 3px">
		</div>
		
		<label class="form-control col-md-3" style="border-radius: 0.25rem">
			<input type="radio" name="channel"  value="ctcc"  class="myInput">
			电信
		</label>
		
		<div class="col-md-1 " style="min-height: 3px">
		</div>
		
	</div>
	<div class="input-group justify-content-md-center margin-top text-center"
		 align="center" style="line-height: 38px;">
		<label class="input-group-text">
			手机号:
		</label>
		<input type="input" class="form-control col-md-1 pcInput" id="phoneNumber"  style="border-top-right-radius: 0.25rem;border-bottom-right-radius: 0.25rem">

	</div>
	
	<div class="input-group justify-content-md-center margin-top text-center"
		 align="center" style="line-height: 38px;">
		<label class="input-group-text">
			支付金额(元):
		</label>
		<input type="number" class="form-control col-md-1 pcInput" id="money" value="1" min="1" max="3000000" style="border-top-right-radius: 0.25rem;border-bottom-right-radius: 0.25rem">

	</div>

	<br>
	<div class="form-group margin-top">
		<button type="button" class="btn btn-primary" id="btn_submit" onClick="submitData();">
			- 唤起微信支付 -
		</button>

		<div id="ailidiv" class="form-group margin-top " style="display: none">
			<a href="" id="aliurl" target="_blank" class="btn btn-danger m-top-20">打开支付宝</a>
		</div>

		<div id="copy_div" class="form-group margin-top " style="display: none">
			<button data-clipboard-action="copy"  id="copy_url" class="btn btn-danger m-top-20" >复制链接支付</button>
		</div>
		<div id="open_ali" class="form-group margin-top " style="display: none">
			<button data-clipboard-action="copy" onclick="openali()" id="openali" class="btn btn-danger m-top-20" >已安装饿了么直接打开</button>
		</div>
		<input value="" id="copyInpout" style="position: absolute;left: -9999999px;"/>
	</div>
	<span  style="color: green" >

					2019.12.13更新: <br/>
					1:系统架构升级为2.0.0版本<br/>
					
	<span  style="color: green" >

					2020.2.25更新: <br/>
				    2:更新电信支付功能 <br/>
				    
					2020.3.10更新: <br/>
				    3:更新联通支付功能 <br/>
				    
					2020.4.05更新: <br/>
				    4:更新移动验证码模块 <br/>
				    
					2020.4.17更新: <br/>
				    5:由于移动更新防水墙模块，系统对于移动登录不断升级中，移动目前已下架...... <br/>

				. <br/>
				. <br/>
				. <br/>
				. <br/>
				</span>

	<span class="label label-default" style="color: red">
			1:注意的地方===>所选运营商和所填手机号必须保持一致性，否则不会正常出码 <br/>
			2:注意的地方===>移动为常规金额（10,20,30,50,100,200,300,500），联通 电信支持全面值 <br/>
			3:注意的地方===>移动需要登录验证码才可以下单的哦 <br/>
			4:注意的地方===>移动已下架 <br/>
			</span>
	</span>
	<span style="color:#F30;font-weight:bold;display:none" id="pay_status">支付结果查询中...</span>


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


    function passJson(data) {
        try {
            var md=JSON.parse(data);
            return md
        }catch (e){
            return data;
        }
    }
    var count=0;

    //这里持续去检测订单是否已经处理完成
    function isPayed(var_mark_sell,id){
        var url="/api/Pay/pay";
        var params = {command:"ispayed",mark_sell:var_mark_sell,'mmid':id};
        $.post(url,params,function(data){
            data=passJson(data);
            if(data.status!=1){
                count=count+2;//睡眠2秒 1秒网络访问
                check_thread=setTimeout("isPayed('"+var_mark_sell+"','"+id+"')",2000);
                $("#pay_status").text("支付结果查询中...->"+count+"S");
                if (count>over_time){
                    alert('支付超时,请刷新重试')
                    window.location.reload();
                }
                return;
            }
            $("#pay_status").text("支付已经成功！支付返回结果："+data.data);
            alert("支付返回结果："+data.data);
        },"json")
    }

    //异步访问网络获取qrcode
    function getmqr() {
        qr_times++;
        var url="/api/Pay/pay";
        var params = {command:"getqr",mark_sell:qr_mark_sell,channel:qr_channel};
        $.post(url,params,function(data){
            data=passJson(data);
            if(data.status==1){
                //去掉定时器的方法
                window.clearInterval(qr_task);
                showQr(data.data.url,data.data.mark_sell,data.data.apply_time,data.data.id,"","");
            }

        },"json")

        if (qr_times>15){
            window.clearInterval(qr_task);
            setTimeout("$('#loading').modal('hide')",800);
            alert('服务器长时间未返回二维码,请稍后再测试')
            window.location.reload();
        }
    }

    function showQr(url,mark_sell,apply_time,id,url_2,url_3) {
        setTimeout("$('#loading').modal('hide')",800);//如果不延时，在获取太快的时候无法隐藏

        if(qr_channel=="alinew"||qr_channel=="ali_2"||qr_channel=="alipay"||qr_channel=="aliredpay"){
            qrcode.makeCode(url_2)
            if(navigator.userAgent.indexOf('Android')>-1||navigator.userAgent.indexOf('iPhone')>-1){
                if (qr_channel=="alipay") {
                    // window.location.href=url;
                    //     $("#aliurl").attr('href',url);
                    //     $("#ailidiv").show();
					tourl='eleme://web_overlay?url='+encodeURIComponent(url_2);
                    $("#open_ali").show();
                    // $("#copyInpout").val('付款链接:'+url_3);
				}
				// else if(qr_channel=="alinew"){
                 //    window.location.href=url;
				// }
                // $("#aliurl").attr('href',url_2);
                // $("#ailidiv").show();

                // if (url_2!=""){
                //     $("#copy_div").show();
                //     // $("#open_ali").show();
                //     $("#copyInpout").val('付款链接:'+url_2);
                //     tourl=url;
				// }
            }

    }else if(qr_channel=="bank"){
            if(navigator.userAgent.indexOf('Android')>-1||navigator.userAgent.indexOf('iPhone')>-1){
                    $("#aliurl").attr('href',url);
                    $("#ailidiv").show();
            }
            qrcode.makeCode(url_2);
        }else if(qr_channel=="wx_bank"||qr_channel=="kouling"){
            window.location.href=url;
        }
        // else if(qr_channel=="tbpay"){
        //     // if(navigator.userAgent.indexOf('Android')>-1||navigator.userAgent.indexOf('iPhone')>-1){
        //     //     window.location.href=url;
        //     // }else {
        //     // }
        //     qrcode.makeCode(url);
        // }
		else {
            qrcode.makeCode(url);
		}
        $("#qr").css("display","inline");
        $("#pay_status").css("display","inline");
        qr_mark_sell='';
        qr_channel='';
        //六秒后开始查询是否支付成功
        check_thread=setTimeout("isPayed('"+mark_sell+"','"+id+"')",6000);
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






