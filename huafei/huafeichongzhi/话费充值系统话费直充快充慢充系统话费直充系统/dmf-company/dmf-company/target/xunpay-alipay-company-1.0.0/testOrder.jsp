<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0042)flipin.html -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 

    <%@ include file="../_meta.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试页面</title>

 
    <script type="text/javascript">
    
    
    //发送异步请求
  	function submitData() {
  		
		  	$.post("${ctx}/order/testOrder",{
		  		"money" :$("#money").val(),
		  		"phone" :$("#phone").val(),
		  	
		  	}, function(res){
		  		
		  		console.log(res);
		  		
		  	});
		}
  

    </script>
</head>
<body>

    <section id="getintouch" >
        <div class="container" style="border-bottom: 0;">
            <h1>
                <span>测试页面</span>
            </h1>
        </div>
        <div class="container contact">
 
            <div class="row clearfix">
                <div class="lbl">
                    <label for="subject">
                        金额</label>
                </div>
                <div class="ctrl">
                    <input type="text" name="money" id="money" placeholder="填写金额">
                </div>
            </div>
            <div class="row clearfix">
                <div class="lbl">
                    <label for="subject">
                        手机号码</label>
                </div>
                <div class="ctrl">
                    <input type="text" name="phone" id="phone" placeholder="支持全网通手机号">
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
