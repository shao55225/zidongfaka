<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0042)flipin.html -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 

    <%@ include file="_meta.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试页面</title>

    <script type="text/javascript">
    
    //发送异步请求
  	function submitData() {
  		
		  	$.post("${ctx}/order/testOrder",{
		  		"address" : $("#address").val(),
		  		"sId" :$("#sId").val(),
		  		"sKey" :$("#sKey").val(),
		  		"payType" :$("#payType").val(),
		  		"money" :$("#money").val(),
		  		"toUrl" :$("#toUrl").val(),
		  	
		  	}, function(res){
		  		
		  		 if (res.status==1){
		  			 /*
	                    let order_sn=res.data.order_sn;
	                    if(order_sn){
	                        //setCookie('ORDERSNS', order_sn.toString(),365);
	                        //readCookie();
	                    }*/
	                    if (res.data.type=='wechat') {
	                        layer.open({
	                            type: 1
	                            ,title: '打开微信扫描下方二维码'
	                            ,closeBtn: 1
	                            ,area: '280px;'
	                            ,shade: 0.8
	                            ,id: 'LAY_layuipro'
	                            ,btnAlign: 'c'
	                            ,moveType: 0
	                            ,content: '<div ><div style="padding: 10px 5px 0px 10px;"><p>订单号：'+res.data.order_sn+'</p><p style="word-wrap: break-word;word-break: normal;">代付号：'+res.data.fp_id+'</p></div><div style="padding: 10px;"><div id="output"></div></div>'
	                            ,success: function(layero){
	                                jQuery(function(){
	                                    jQuery('#output').qrcode({
	                                        width: 260,
	                                        height: 260,
	                                        text: res.data.qr_url
	                                    });
	                                });
	                                return;
	                                var btn = layero.find('.layui-layer-btn');
	                                btn.find('.layui-layer-btn0').attr({
	                                    href: res.data.order_url
	                                    ,target: '_blank'
	                                });
	                            }
	                            ,yes: function(index, layero){
	                                window.open(res.data.qr_url);
	                            }
	                        });
	                    }
	                    if (res.data.type=='alipay') {
	                        layer.open({
	                            type: 1
	                            ,title: '打开支付宝扫描下方二维码'
	                            ,closeBtn: 1
	                            ,area: '280px;'
	                            ,shade: 0.8
	                            ,id: 'LAY_layuipro'
	                            ,btnAlign: 'c'
	                            ,moveType: 0
	                            ,content: '<div ><div style="padding: 10px 0px 0px 15px;"><p>订单号：'+res.data.order_sn+'</p></div><div style="padding: 10px;"><div id="output"></div></div></div>'
	                            ,success: function(layero){
	                                jQuery(function(){
	                                    jQuery('#output').qrcode({
	                                        width: 260,
	                                        height: 260,
	                                        text: res.data.qr_url
	                                    });
	                                });
	                                return;
	                                var btn = layero.find('.layui-layer-btn');
	                                btn.find('.layui-layer-btn0').attr({
	                                    href: res.data.qr_url
	                                    ,target: '_blank'
	                                });
	                            }
	                        });
	                    }
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
</head>
<body>

    <section id="getintouch" >
        <div class="container" style="border-bottom: 0;">
            <h1>
                <span>pc下单页面</span>
            </h1>
        </div>
        <div class="container contact">
   
            <div class="row clearfix">
                <div class="lbl">
                    <label for="subject">
                        支付方式</label>
                </div>
                <div class="ctrl">
                    <input type="text" name="payType" id="payType" placeholder="支付方式（支付宝填alipay,微信填wechat）">
                </div>
            </div>
            <div class="row clearfix">
                <div class="lbl">
                    <label for="subject">
                        金额</label>
                </div>
                <div class="ctrl">
                    <input type="text" name="money" id="money" placeholder="填写下单金额（必须有此金额的商品存在，否则调用不成功）">
                </div>
            </div>
            
            <div class="row  clearfix">
                <div class="span10 offset2">
                    <input type="submit" name="submit" id="submit" class="submit"  onclick="submitData()" value="提交">
                </div>
            </div>
           
        </div>
    </section>
   
</body>
</html>
