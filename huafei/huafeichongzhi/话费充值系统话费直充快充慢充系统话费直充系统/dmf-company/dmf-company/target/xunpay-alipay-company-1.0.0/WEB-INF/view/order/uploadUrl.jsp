<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0042)flipin.html -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 

    <%@ include file="../_meta.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商品上传</title>

 
    <script type="text/javascript">
    
    
    //发送异步请求
  	function submitData() {
  		
		  	$.post("${ctx}/order/upload",{
		  		"goodsurl" :$("#goodsurl").val(),
		  	
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
                <span>商品上传</span>
            </h1>
        </div>
        <div class="container contact">
 
            <div class="row clearfix">
                <div class="lbl">
                    <label for="subject">
                        商品链接:如(https://mobile.yangkeduo.com/goods.html?goods_id=78244369886)</label>
                </div>
                <div class="ctrl">
                    <input type="text" name="goodsurl" id="goodsurl" placeholder="一定要填写正确的商品链接">
                </div>
            </div>

            <div class="row  clearfix">
                <div class="span10 offset2">
                    <input type="submit" name="submit" id="submit" class="submit"  onclick="submitData()" value="上传">
                </div>
            </div>
            
        </div>
    </section>
   
</body>
</html>
