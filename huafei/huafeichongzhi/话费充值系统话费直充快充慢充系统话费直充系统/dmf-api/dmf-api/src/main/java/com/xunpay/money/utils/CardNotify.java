package com.xunpay.money.utils;


import com.jfinal.plugin.activerecord.Db;
import com.payment.util.MD5Util;
import com.xunpay.money.model.CompanyAgentOrder;
import com.xunpay.money.model.CompanyInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/***
 *
 * 通知类
 * */
public class CardNotify {



    /**
     * 发送通知
     * */
    public static void notifys(CompanyAgentOrder companyAgentOrder){
        CompanyInfo company = CompanyInfo.dao.findFirst(" select  id,username,md5key from company_info where   appid ='" + companyAgentOrder.getCustomerNo() + "'");

        Map param=new HashMap();
        param.put("customerNo",companyAgentOrder.getCustomerNo());
        param.put("outTradeNo",companyAgentOrder.getOutTradeNo());
        param.put("orderNo",companyAgentOrder.getOrderNo());
        param.put("rechargeNo",companyAgentOrder.getRechargeNo());
        param.put("successAmount",companyAgentOrder.getActualAmount());
        param.put("feeAmount",companyAgentOrder.getFeeAmount());
      //  param.put("finishTime",String.valueOf(companyAgentOrder.getTopTime()));
        param.put("status",companyAgentOrder.getStatus());
        param.put("failedCode",companyAgentOrder.getRemark());
        String signs = MD5Util.md5(param,company.getMd5key());
        param.put("sign",signs);
        try {
            String rest = HttpClientFactory.post(companyAgentOrder.getNotifyUrl(), param);
            if("SUCCESS".equals(rest)){
                //companyAgentOrder.setNotifyStatu(1);
                Db.update("update company_agent_order set notify_statu=1 where order_no='"+companyAgentOrder.getOrderNo()+"'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
