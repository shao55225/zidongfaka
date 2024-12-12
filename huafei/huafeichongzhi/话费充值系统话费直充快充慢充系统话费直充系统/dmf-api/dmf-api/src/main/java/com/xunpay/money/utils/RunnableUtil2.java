package com.xunpay.money.utils;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.Coke;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyIp;
import com.xunpay.money.model.CompanyOrder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

public class RunnableUtil2 extends Thread {

    private String tradeNo;

    private BigDecimal money;

    private String appid;

    private String orderNo;

    private Integer id;
    
    private Date times;

    private CompanyIp companyIp;


    public RunnableUtil2 (String tradeNo,BigDecimal money,String appid,String orderNo,Integer id,Date times,CompanyIp companyIp){
        this.tradeNo=tradeNo;
        this.money=money;
        this.appid=appid;
        this.orderNo=orderNo;
        this.id=id;
        this.times=times;
        this.companyIp=companyIp;
    }

    public void run(){
        CompanyOrder order = new CompanyOrder();
        String currentOrder = "S" + System.currentTimeMillis() + (long) (Math.random() * 1.0E7D);
        String payUrl = "";
        String sessionId = "";
        
        CompanyApiOrder apiOrder=CompanyApiOrder.dao.findById(id);
        
        apiOrder.setRsaPrivate(companyIp.getIp());
        
        apiOrder.update();
        
        /**
         * 实施生产 1移动  2联通  3电信
         * **/
        int successT=1;
        long x = System.currentTimeMillis();
        switch (Integer.valueOf(tradeNo)) {
            case 2:
              /*  CuccTest cuccTest = new CuccTest();
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String moneys = decimalFormat.format(money);
                String url = cuccTest.topUp(appid, moneys,0,companyIp);
                if(url!=null){
                	
                  order.setRemark(url);
                  order.setTradeNo("2");
                  break;
                }
                else {
                	successT=error();
                	break;
                }*/
             
               /* payUrl = cuccMap.get("url").toString();
                order.setReturnUrl("https://unipay.10010.com/udpNewPortal/miniwappayment/weixinH5Return?payStr=" + cuccMap.get("payStr").toString());*/
              
            case 3:
                CtccTest ctcc = new CtccTest();
                Double m = money.doubleValue();
                String money = m.toString();
                Map<String, String> map = ctcc.topUp(appid, money,0,companyIp);
                sessionId = map.get("sessionId");
                payUrl = map.get("payUrl");
                String orderid = map.get("orderid");
                order.setTradeNo("3");
                order.setReturnUrl("https://wappay.189.cn/pay/toPayQuery.do?boId=" + sessionId);
                order.setToken(orderid);
                break;
        }
        
        if(successT!=0) {
        	
	        long y = System.currentTimeMillis();
	        long logtime = y - x;
	        
	    
	        System.out.println("下单处理时长----------" + logtime);
	        System.out.println(payUrl);
	        //手机号码
	        order.setAppid(appid);
	        order.setTitle("支付系统下单");
	        order.setOrderNo(currentOrder);
	        order.setOrderMoney(money);
	        order.setRsaPrivate(companyIp.getIp());
	        order.setPid(sessionId);   //电信查询需要的session
	        //  order.setPayType("wechat_h5_pdd");
	        order.setStatus("匹配中");
	        order.setNotice("未回调");
	        order.setQrUrl(payUrl);
	        Date time = new Date();
	        order.setAddtime(time);
	        
	        //溯源订单号
	        order.setAlipayName(orderNo);
	        //初始化时间
	        order.setNoticetime(times);
	        order.save();
	
	        Db.update("update company_apiorder set noticetime=? where id=?",time,id);
	        Db.update("update company_apiorder set status='匹配中' where id=" + id);
        }
    }
    
    public  static int error() {
    	
    	System.out.println("没有正常产生二维码");
    	
    	return 0;
    }

}
