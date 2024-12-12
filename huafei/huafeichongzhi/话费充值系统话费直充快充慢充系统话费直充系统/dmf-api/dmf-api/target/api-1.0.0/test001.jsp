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
	 .hide,html .none{
	    display: none;
	}
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

  window.onload=function (){

			$("#cmcc").click(function(){
				$('.yzm').removeClass('hide');
				$('.send').removeClass('hide');
				$('#money').val("10");
			});

			$("#cucc").click(function(){
				$('.yzm').addClass('hide');
				$('.send').addClass('hide');
				$('#money').val("1");
			});
			$("#ctcc").click(function(){

				$('.yzm').addClass('hide');
				$('.send').addClass('hide');
				$('#money').val("1");
			});


  }
	 //发送验证码
    function sendMsg(){

		 /**

		layer.msg("后台正在请求，请稍等", {
            time: 15000,
          //  btn: ['知道了'],
            btnAlign: 'c',
            anim: 6
        });
 	**/
 	var phoneNumber=$("#phoneNumber").val();

		  	$.post("${ctx}/order/sendMsg",{

		  		"phoneNumber" : phoneNumber,

		  	}, function(res){

		  		console.log(res);


		  		 if (res.result=="success"){

		  			 layer.msg(res.data, {
	                        time: 2000,
	                        btn: ['知道了'],
	                        btnAlign: 'c',
	                        anim: 6
	                    });


	                }else{

	                	 layer.msg(res.msg, {
	   	                        time: 2000,
	   	                        btn: ['知道了'],
	   	                        btnAlign: 'c',
	   	                        anim: 6
	   	                    });



	                }

		  	});


    }


    //微信支付
  	function submitData() {
		var second = 20;
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
				timer = setInterval(fn, 1000);
				fn();
			},
		}, function () {
			$("#btnSave").removeAttr("disabled");
		});

        var phoneNumber=$("#phoneNumber").val();
        var money=$("#money").val();
        var channel = $("input[name='channel']:checked").val();

        var yzm="";
        if(channel=="cmcc"){

        	yzm=$("#yzm").val();
        }

		  	$.post("${ctx}/order/testOrder",{

		  		"phoneNumber" : phoneNumber,

		  		"money" : money,

		  		"channelCode" :channel,

		  		"yzm" :yzm,

		  	}, function(res){

		  		 if (res.result=="SUCCESS"){

		  			 window.location.href=res.data;

	                }else{

	                	 layer.msg(res.msg, {
	   	                        time: 2000,
	   	                        btn: ['知道了'],
	   	                        btnAlign: 'c',
	   	                        anim: 6
	   	                    });
	                }

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
			<input type="radio" name="channel" id="cmcc" value="cmcc"  class="myInput">
			移动
		</label>
		<div class="col-md-1 " style="min-height: 8px">
		</div>
		<label class="form-control col-md-3" style="border-radius: 0.1rem">
			<input type="radio" name="channel"  id="cucc" value="cucc"  class="myInput">
			联通
		</label>

		<div class="col-md-1 " style="min-height: 3px">
		</div>

		<label class="form-control col-md-3" style="border-radius: 0.25rem">
			<input type="radio" name="channel" id="ctcc"  value="ctcc"  class="myInput">
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


	<div class="input-group justify-content-md-center margin-top text-center yzm hide"
		 align="center" style="line-height: 38px;">
		<label class="input-group-text ">
			验证码:
		</label>
	<input type="input" class="form-control col-md-1 pcInput " id="sms"  style="border-top-right-radius: 0.25rem;border-bottom-right-radius: 0.25rem">
 	<!--
		<input type="tel" placeholder="请输入短信验证码" id="captcha_input">
				-->
	</div>

	<div class="input-group justify-content-md-center margin-top text-center"
		 align="center" style="line-height: 38px;">
		<label class="input-group-text">
			支付金额(元):
		</label>
		<input type="number" class="form-control col-md-1 pcInput" id="money" value="1" min="20" max="3000000" style="border-top-right-radius: 0.25rem;border-bottom-right-radius: 0.25rem">

	</div>

	<br>
	<div class="form-group margin-top">

		<button type="button" class="btn btn-primary send hide" id="btn_submit" onClick="sendMsg();">
			- 发送验证码 -
		</button>

		<button type="button" class="btn btn-success" id="btn_submit" onClick="submitData();">
			- 唤起微信支付 -
		</button>

	</div>
	<span  style="color: green" >


	<span class="label label-default" style="color: red">
			1:注意的地方===>所选运营商和所填手机号必须保持一致性，否则不会正常出码 <br/>
			2:注意的地方===>移动为常规金额（10,20,30,50,100,200,300,500） 移动暂时不支持<br/>
		    3:注意的地方===>联通为常规金额（20,30,50,100,300） <br/>
		    4www.yaboweb.cn <br/>
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


</html>






