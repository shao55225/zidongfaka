<%@page import="com.xunpay.money.utils.DateUtils"%>
<%@page import="com.alipay.api.response.AlipayFundCouponOrderPagePayResponse"%>
<%@page import="com.alipay.api.request.AlipayFundCouponOrderPagePayRequest"%>
<%@page import="com.xunpay.money.test.DefaultAlipayClient"%>
<%@page import="com.alipay.api.AlipayClient"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
		"2018122962722356",
		"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCFr5rdLAgvgOPOKsAmHALy5YdbzPRaEYRlOM64F/xF3uH8Qtg30q7SWte2cWMezgZY07HE8xoZr/FTOv6Eqv3M2lbaQXPXI1ZL+mtPyzH0s5yHisMMfUkv6yd6fsL0iuuApxc7aoKSvLnz7a9a91L7RdqBA/kClo75ZDu4WXxGg2Egf9ulSF4my9eavVHx69jiJGL+uetf5B/4KkUF+c3WIlaHHnx1cI4Qpuvo8rNbK6ihv/hVdLyhXMPdJLGawOP6h2kttJuyVKXgPzYQhlH+wf3o1os9HLOwOP53yhvw0VZOlmtAuphtTZPlyc2d/DY0gojmgyc774K+j2hfPVTTAgMBAAECggEAFgQig/QhcPlQCv79YAlS7v+f+B1bzI1/+U+Os8C3eiZHn0y+4QqgYeI2DkTEJymU4AIVD7NdhAd2vddEXULvw1Hd5dnLow2dw6t9CsoS8xK1X5kZoC/t4qdefAzuPGm4eRqLd+yFjnboROTK8Tc0Ak+xuXeXBbcX2riIIUcTcWUn5pjkozZ5B5SpGldj1C4WuvDo7RTfKW2eH4YpkUU7iQxBuZQJpRV7/y6h/cTECLd4huWP18xmeVkAY/JxhiBNH9Yyez8LZJoUsYGz1h1AknJS/oseJrXTcnrVLSlSRMSflUAymVzuqjZrflRDngDZZO12az+FUKJ4ke6v2a68gQKBgQD5gZs1KHcUvonGtot8EaBQB4UeQ/iC+7gSK+QcvAuQl5wS0VGi4uO5b8UlC7xkAUii3SJB3+A8KSb1x/m25QsE7hXUudc/kyN5hIo7IIHf2mb4Vl6TT0l90AgSoqKB/de7cagHMHNo1JECLnVpIlsL+XBeY1tPX6UlxCKktDLiQQKBgQCJKlGcNvZUh72aOYxFsrV9ZZ/dVpg5xrTUKD/260vW3bVH9bR8MAScKUaB818ciI3RXSa+d8GoEAxl0JmYDHSLAjaPLStbUuNwWM3IQRZKQPCDPSwTHXoqUTrF1bZPEN0WFiupKuz7SumMK/xfTkqUtgKlQ/IbK+ul56IdGqAKEwKBgQDBc1DglWBG0DjIvm5FovD2O+5lefPrsrmUNT2nXdFlqXcUTAEM1vgTpepn/8Jwk2I7DFNsWeVlH88aT5N4z/uMNgDtL64Jl86wgCghEeJAqvidYXHwq0/i4XHw6PIw8Zzbs5LBCDrjM7S+yUC1ZDLNhPgv5r88On80We82XpT5QQKBgCxfXVFM0nlcBDEtJryxaFbtlj7TMaGXL45mivLFAZ670FY8HSwDmKowDBgj9APWBRLSsKLzONm2f1w8lEOE2/rQN3gv45PCh8MjZ0a+D4jr4yKBmo2AfrHL31h6n2SKjwsDFxK9yTYt8MmgvR7Tw/rBuq9eAKdbmPrtqRsDLvyzAoGAB0+GcKdCj3UE0KPojlRFoqm+aLZKLEISzfy/1vvnk0m1iRNptUUw0C8Etl6h3L4T6fX4bKOuSqbFKhu7VjDEyhvoewJveZYHXa+E1MtzaP2ebx6HYdnZQ4Nrkbs2FMiwunjDhDQKU0oNEuXLaK3hahaUvgNMSUQKkPSEOdbgXwU=",
		"json",
		"GBK",
		"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi02ag/z1Z5cDZWUERKj8/SOT+OGql+AGapES8lHa58POxeooeNWhRYXEw46VKm0KM3pj/RJUjwpY3bNoAQ28TBmn2w5EmhZbPIg3RD/EODhZ4HhpYM8ogROCFYQZUMLjxdBZxgwMVNbeqx/e74XSPHFwNEl9e89N8RPjarjVF5+ArQ0P3FrggyfTwpYK3cxNJbuIh8jMC5A85nsAuWzjcW1yh2VT1mT+QmixTzHEAeLIvrWXbk4dQj5GVzKe5H7ASQq4fDGShx45HuGUy6iqnb5fzx0fMWZOPHNzWxb/EOBaqoivxs9nvACS5XbpAnAF0aVP+wSKCMrHn6Po9nclwwIDAQAB",
		"RSA2");
AlipayFundCouponOrderPagePayRequest request2 = new AlipayFundCouponOrderPagePayRequest();
request2.setNotifyUrl("http://39.108.152.98/static/notice.jsp");
request2.setBizContent("{" +
"\"out_order_no\":\"" + DateUtils.getDateSerializable("S", "yyMMddHHmmssSSS", 8) + "\"," +
"\"out_request_no\":\"" + DateUtils.getDateSerializable("S", "yyMMddHHmmssSSS", 8) + "\"," +
"\"order_title\":\"发送红包\"," +
"\"amount\":200.00," +
"\"pay_timeout\":\"1h\"," +
"\"extra_param\":\"{\\\"merchantExt\\\":\\\"key=value\\\"}\"" +
"  }");


AlipayFundCouponOrderPagePayResponse response2 = alipayClient.execute(request2);
out.write(response2.getBody());
%>

