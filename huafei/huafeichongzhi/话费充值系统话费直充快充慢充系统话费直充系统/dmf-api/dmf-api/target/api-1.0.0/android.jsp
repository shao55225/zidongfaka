<%@page import="com.xunpay.money.utils.Constant"%>
<%@page import="org.apache.http.util.EntityUtils"%>
<%@page import="org.apache.http.HttpEntity"%>
<%@page import="org.apache.http.client.methods.CloseableHttpResponse"%>
<%@page import="org.apache.http.entity.StringEntity"%>
<%@page import="org.apache.http.client.methods.HttpPost"%>
<%@page import="org.apache.http.impl.client.HttpClients"%>
<%@page import="org.apache.http.impl.client.CloseableHttpClient"%>
<%@page import="com.xunpay.money.utils.DateUtils"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.xunpay.money.utils.HttpClientHelper"%>
<%@page import="com.xunpay.money.utils.EncryptUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
if ("post".equalsIgnoreCase(request.getMethod())) {
	String pid = "QR1705";
	String md5key = "odipeixidledseqasx";
	JSONObject params = new JSONObject();
	params.put("encryptType", "MD5");
	JSONObject content = new JSONObject();
	content.put("merchNo", pid);
	content.put("orderNo", DateUtils.getDateSerializable("SP", "yyMMddHHmmssSSS", 6));
	content.put("amount", request.getParameter("amount"));
	content.put("currency", "CNY");
	content.put("outChannel", "jfali");
	content.put("bankCode", "1001");
	content.put("title", "1");
	content.put("product", "1");
	content.put("memo", "1");
	content.put("returnUrl", "http://119.23.215.133/success.jsp");
	content.put("notifyUrl", "http://119.23.215.133/success.jsp");
	content.put("reqTime", DateUtils.get(new Date(), "yyyyMMddHHmmss"));
	content.put("userId", "1");
	params.put("sign", EncryptUtils.encrypt(EncryptUtils.encryptBASE64(content.toString()).replace("\n", "").replace("\r", "") + md5key));
	params.put("context", EncryptUtils.encryptBASE64(content.toString()).replace("\n", "").replace("\r", ""));
	
	CloseableHttpClient client = HttpClients.createDefault();
	HttpPost post = new HttpPost("http://47.103.83.68/pay/order");
	post.addHeader("Content-Type", "application/json");
	post.setEntity(new StringEntity(params.toString()));
	System.out.println(params.toString(4));
	
	CloseableHttpResponse resp = client.execute(post);
	HttpEntity entity = resp.getEntity();
	String responseContent = EntityUtils.toString(entity, "UTF-8"); 
	System.out.println(responseContent);
	resp.close();
	client.close();
	
	JSONObject json = JSONObject.fromObject(responseContent);
	if ("0".equals(json.getString("code")) && Constant.SUCCESS.equals(json.getString("msg"))) {
		String context = json.getString("context");
		String qrUrl = JSONObject.fromObject(context).getString("qrcode_url");
		response.sendRedirect(qrUrl);
		return;
	} else {
		out.write("<script type='text/javascript'>alert('" + json.getString("msg") + "');</script>");
	}
}
%>



<!DOCTYPE html>
<html>
<head>
	<title>支付测试</title>
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
    <meta name="apple-mobile-web-app-title" content="支付测试" />
    <link href="http://cdn.amazeui.org/amazeui/2.7.2/css/amazeui.min.css" rel="stylesheet">
    <script type="text/javascript" src="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.min.js"></script>
    <script type="text/javascript" src="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.ie8polyfill.min.js"></script>
    <script type="text/javascript" src="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.widgets.helper.min.js"></script>
</head>
<body>
	
<form class="am-form" method="post">
  <fieldset>
    <legend>确认支付</legend>

    <div class="am-form-group">
      <label for="doc-ipt-email-1">金额</label>
      <input type="text" name="amount" id="doc-ipt-email-1" placeholder="输入金额">
    </div>

	<p>
		<button type="submit" class="am-btn am-btn-primary">
			<i class="am-icon-shopping-cart"></i>
			提交
		</button>
	</p>
  </fieldset>
</form>


</body>
</html>