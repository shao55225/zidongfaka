<%--
  Created by IntelliJ IDEA.
  User: RxTao
  Date: 2020/11/20
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>微信支付请求</title>
    <style type="text/css">
        a:link {
            background: #2E993C;
            color: #fff;
            border:1px solid  #999999 ;//给超链接a加边框
        }  </style>
</head>


<body>
<script>

    <!--获取页面之间的传递的参数-->
    function getQueryString(name) {
        var a = decodeURI(window.location.search);
        var r = a.substr(a.indexOf("=") + 1, a.length);
        if (r != null)
            return unescape(r);
        return null;
    }


    function testApp(url) {
        var timeout, t = 1000, hasApp = true;
        setTimeout(function () {
            if (!hasApp) {
                //没有安装微信
                //   var r=confirm("您没有安装微信，请先安装微信!");
                //   if (r==true){
                //location.href="http://weixin.qq.com/"
                //   }
            } else {
                //安装微信
            }
            document.body.removeChild(ifr);
        }, 2000)

        var t1 = Date.now();
        var ifr = document.createElement("iframe");
        ifr.setAttribute('src', url);
        ifr.setAttribute('style', 'display:none');
        document.body.appendChild(ifr);
    }

    //判断访问终端
    var browser = {
        versions: function () {
            var u = navigator.userAgent, app = navigator.appVersion;
            return {
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
                weixin: u.indexOf('MicroMessenger') > -1, //是否微信 （2015-01-22新增）
                qq: u.match(/\sQQ/i) == " qq" //是否QQ
            };
        }(),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    }
</script>
<%--<p style="text-align: center"><a href="javascript:testApp(getQueryString('appid'))" class="dl-btn" id="download">打开微信支付</a></p>--%>
<table width=100% height=100%>
    <tr>
        <td>
            <center><font face="times"><span style="font-size:50px;color:green;font-family:century"><a
                    href="javascript:testApp(getQueryString('appid'))"
                    style="text-decoration:none;">启动微信支付</a></span></font></center>
        </td>
    </tr>
</table>
</body>
</html>
www.yaboweb.cn