package com.xunpay.money.utils;

import com.xunpay.money.model.CompanyAgentOrder;

/**
 * 异步代理充值
 * */
public class RunnableAgentOrder extends Thread{


    private CompanyAgentOrder companyAgentOrder;

    public RunnableAgentOrder(CompanyAgentOrder companyAgentOrder){
        this.companyAgentOrder=companyAgentOrder;
    }

    public void run(){
        if("UNICOM".equals(companyAgentOrder.getProductType())){
            CuccCard cuccCard=new CuccCard();
            cuccCard.topUp(companyAgentOrder,"",0);
        }

    }


}
