<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
 String userAgent=request.getHeader("User-Agent");
 userAgent=null==userAgent?"":userAgent;
 if(!userAgent.toLowerCase().contains("alipay")){
 	return;
 }
 //###########################################
 String url=request.getParameter("url");
 if(url.equals("")){
 	out.println("url 参数为空");
 	return;
 }
 System.out.println(url);
 
 String userId="";
 String amount="";
 String memo="";
 String[] strArray=url.split("&");
 if(null!=strArray){
 	for(String str:strArray){
 		if(str.contains("userId")){
 			userId=str.split("=")[1];
 		}
 		if(str.contains("amount")){
 			amount=str.split("=")[1];
 		}
 		if(str.contains("memo")){
 			memo=str.split("=")[1];
 		}
 	}
 }else{
 	out.println("url 参数解析失败");
 	return;
 }
 
 if(userId.equals("")||amount.equals("")||memo.equals("")){
 	out.println("url 参数解析失败");
 	return;
 }
 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>付款中</title>
</head>
<body >
<script src="https://gw.alipayobjects.com/as/g/h5-lib/alipayjsapi/3.1.1/alipayjsapi.min.js"></script>
<script>
   function returnApp() {
        AlipayJSBridge.call("exit   App");
    }
    function ready(a) {
        window.AlipayJSBridge ? a && a() : document.addEventListener("AlipayJSBridgeReady", a, false)
    }
    ready(function() {
        try {
      	  var a = {
                  actionType: "scan",
                  u: "<%=userId%>",
		         a: "<%=amount%>",
		         m: "<%=memo%>",
		         biz_data: {
		             s: "money",
		             u: "<%=userId%>",
		             a: "<%=amount%>",
		             m: "<%=memo%>"
		          }
             }
         } catch(b) {
       	  returnApp();
         }
         AlipayJSBridge.call("startApp", {
             appId: "20000123",
             param: a
         }, function(a) {
		    if (a.errorCode == 4) {
			   AlipayJSBridge.call('startApp', {
					appId: '10000113',
					param: {
						"title": "拉起来",
						"url": location.href,
					}
				},function (e) {
				}
			);
		}
	});
     });
     document.addEventListener("resume",function(a) {
         returnApp();
     });
</script>
</body>
</html>