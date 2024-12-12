<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.security.NoSuchAlgorithmException"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.security.MessageDigest"%>
<%@page import="com.xunpay.money.utils.HttpClientHelper"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%
if ("post".equalsIgnoreCase(request.getMethod())) {
	String md5Key = "iDxtLvCHZxHm6Mft8l3KkIVTVNMQLkRPzeKT2aNu4R6lWyO2gsY1vRUjbE0fqoBk";
	Map<String, String> params = new HashMap<String, String>();
	params.put("appId", "15487609288296300753");
	params.put("orderNo", "测试" + System.currentTimeMillis() + (long) (Math.random() * 10000000L));
	params.put("timestamp", String.valueOf(System.currentTimeMillis()));
	params.put("dumpUrl", "http://39.108.152.98/static/notice.jsp");
	params.put("money", request.getParameter("m").trim());
	params.put("noticeUrl", "http://39.108.152.98/static/notice.jsp");
	params.put("sign", md5(params.get("appId") + "^"
						 + params.get("orderNo") + "^"
						 + params.get("money") + "^"
						 + params.get("timestamp") + "^" + md5Key));
	String url = "http://47.92.253.105/order/create";
	String result = HttpClientHelper.sendPost(url, params);
	JSONObject json = JSONObject.fromObject(result);
	response.sendRedirect(json.getJSONObject("data").getString("qrCode"));
	return;
}
%>
<form method="post">
<input name="m" value="1"/>
<button type="submit">支付</button>
</form>
<%!
public String md5(String data) {
	MessageDigest md;
	try {
		md = MessageDigest.getInstance("MD5");
		try {
			md.update(data.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			md.update(data.getBytes());
		}
		byte[] b = md.digest();
		StringBuffer buf = new StringBuffer("");
		int i = 0;
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	} catch (NoSuchAlgorithmException e) {
		return data;
	}
}
%>