<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="_meta.jsp" %>
  <style type="text/css">
  .am-text-left {color:#F37B1D;}
  </style>
  <%--<c:set var="api_host" value="api.ngjmr.cn"/>  --%>
</head>
<body style="padding: 10px 15%; background: rgb(255, 255, 255);">
<div class="am-article">
	
	<div class="am-article-bd">
		<h2>1，创建订单</h2>
		<blockquote>
			接口地址：http://ip地址/API/order/create
		</blockquote> 
		
		
		<h4>请求参数：（Get请求）</h4>
		<table class="am-table am-table-bordered">
			<tr>
				<th>参数名</th>
				<th>必选</th>
				<th>参与加密</th>
				<th>类型</th>
				<th>说明</th>
			</tr>
			
			<tr><td>sId</td><td>是</td><td>是</td><td>string</td><td>商户分配的appId</td></tr>
			<tr><td>sKey</td><td>是</td><td>是</td><td>string</td><td>商户密钥</td></tr>
			<tr><td>apiordersn</td><td>是</td><td>是</td><td>string</td><td>订单号（必须唯一）</td></tr>
			<tr><td>payType</td><td>是</td><td>是</td><td>string</td><td>通道编码（此处填写通道编码，如alipay_h5_pdd）</td></tr>
			<tr><td>money</td><td>是</td><td>是</td><td>string</td><td>订单金额，单位元。如：10.00</td></tr>
			<tr><td>toUrl</td><td>是</td><td>是</td><td>string</td><td>回调地址</td></tr>
		    <tr><td>timeStr</td><td>是</td><td>是</td><td>string</td><td>13位时间戳（毫秒），1514776271000</td></tr>
			<tr><td>sign</td><td>是</td><td>否</td><td>string</td><td>32位Md5大写签名  md5(sKey+"api_order_sn"+apiordersn
		+ "client_id"+sId
		+ "notify_url"+toUrl
		+ "timestamp"+timeStr
		+ "total"+money
		+"type"+payType+sKey)</td></tr>
		</table>
		
		
		<h4 style="margin-top: 5px;">请求案列</h4>
		<div class="am-tabs" data-am-tabs="{noSwipe: 1}" id="doc-tab-demo-1">
		  <ul class="am-tabs-nav am-nav am-nav-tabs">
		    <li class="am-active"><a href="javascript: void(0)">JAVA</a></li>
		  
		    <li><a href="javascript: void(0)">PHP</a></li>
		     <!-- <li><a href="javascript: void(0)">HTML</a></li>
		      -->
		  </ul>
		
		  <div class="am-tabs-bd">
		    <div class="am-tab-panel am-active"><pre>String sKey = "输入您的商户秘钥";
 String sign= md5(sKey+"api_order_sn"+apiordersn
		+ "client_id"+sId
		+ "notify_url"+toUrl
		+ "timestamp"+timeStr
		+ "total"+money
		+"type"+payType+sKey).toUpperCase();
String youurl = "http://ip地址/API/order/create";
String url=youurl+?sId=sId&sKey=sKey&apiordersn=apiordersn&payType=alipay_h5_pdd&money=money&toUrl=toUrl&timeStr=timeStr&sign=sign;
String response = HttpClientHelper.sendGet(url);</pre>

		    </div>
		    <div class="am-tab-panel"><pre>
$sKey  = "输入您的商户秘钥";
$sign = strtoupper(md5(sKey+"api_order_sn"+apiordersn
		+ "client_id"+sId
		+ "notify_url"+toUrl
		+ "timestamp"+timeStr
		+ "total"+money
		+"type"+payType+sKey));

$url = "http://ip地址/API/order/create?";
$url .= "&sId=$sId";
$url .= "&sKey=$sKey";
$url .= "&apiordersn=$apiordersn";
$url .= "&amp;payType=$payType;
$url .= "&amp;money=$money;
$url .= "&amp;toUrl=$toUrl;
$url .= "&timeStr=$timeStr;
$url .= "&sign=$sign;

$res = sendRequest($url); 
</pre>
		    </div>
		    <div class="am-tab-panel"><pre>例: appId=18112317414123456, md5key=123456789
签名加密前：18112317414145420^T15439463213686716877^1.00^1543946321368^123456789
Get请求URL:http://${api_host}/order/create?appId=18112317414123456&orderNo=T15439463213686716877&amp;timestamp=1543946321368&money=1.00&amp;noticeUrl=http%3A%2F%2F127.0.0.1%2Fnotice.jsp&dumpUrl=&sign=6bbc92565b4e3e8a9536fd4fb5f1769d</pre>
		    </div>
		  </div>
		</div>
		
		<h4 style="margin-top: 5px;">返回案列</h4>
		<div><pre>{
    "result": "success",                                              -- 下单成功是返回success
    "msg": "下单成功",                                                   				  -- 返回说明
    "data": {
        "orderNo": "S2018111614434368153196918",                      -- 商户订单号
        "qrCode": "https://mapi.alipay.com/gateway.do?service=alipay  -- 支付二维码
        "type": "alipay",   					      -- 支付方式
    }
}</pre></div>
	</div>
	

	
	
	<div class="am-article-bd" style="margin-top:10px;">
		<h2>2，订单支付成功回调（只有支付成功以后才会回调哦...）</h2>参数名
		<blockquote>
			接口地址： url在“创建支付订单”时已上传
		</blockquote>
		
		
		<h4>回调参数：（Get请求）需要解签</h4>
		<table class="am-table am-table-bordered">
			<tr>
				<th>参数名</th>
				<th>参与加密</th>
				<th>说明</th>
			</tr>
			<tr><td>type</td><td>是</td><td>支付方式</td></tr>
			<tr><td>total</td><td>是</td><td>金额</td></tr>
			<tr><td>api_order_sn</td><td>是</td><td>商户订单号</td></tr>
			<tr><td>sign</td><td>否</td><td>回调签名md5(字典排序+key)32位大写</td></tr>
		</table>
	</div>

		<h4 style="margin-top: 5px;">回调说明</h4>
		<div><pre>{
		
		
    "传参方式": "get",                                             
    "算法: "md5加签回调",                                                   				
    "加签方式":"字典排序+商户key",   
    "加签Demo":"md5("api_order_sn"+api_order_sn
			+ "total"+total
			+ "type"+type+key)"
}</pre></div>



</div>
</body>
</html>

