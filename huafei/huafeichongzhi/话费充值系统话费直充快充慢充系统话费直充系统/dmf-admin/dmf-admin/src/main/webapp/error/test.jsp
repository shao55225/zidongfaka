<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0042)flipin.html -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商户下单支付页面</title>
    <script src="${ctx}/resource/plugins/jquery3.3.js"></script>
    <script src="${ctx}/resource/plugins/popper.min.js"></script>
    <script src="${ctx}/resource/plugins/bootstrap.min.js"></script>
    <script src="${ctx}/resource/plugins/jquery.qrcode.min.js" ></script>
    <script type="text/javascript" src="${ctx}/resource/plugins/qrcode.js"></script>
    <link rel="stylesheet" href="${ctx}/resource/css/bootstrap1.min.css">
    <link rel="stylesheet" href="${ctx}/resource/css/tpay.css">
</head>
<body>
<div class="form text-center myContent">
    <div class="input-group justify-content-md-center margin-top text-center" align="center" style="line-height: 38px;">
        <label class="input-group-text">
            支付金额(元):
        </label>
        <input type="number" class="form-control col-md-1 pcInput" id="money" value="1" min="1" max="3000000">
    </div>
    <div class="input-group justify-content-md-center margin-top text-center" align="center">
        <label class="input-group-text">下单手机号:</label>
        <input  class="form-control col-md-1 pcInput" id="phone" >
    </div>
    <div id="open_ali" class="form-group margin-top " style="display: none">
        <button onclick="openali()" > 确定</button>
    </div>
</div>
</body>
</html>
