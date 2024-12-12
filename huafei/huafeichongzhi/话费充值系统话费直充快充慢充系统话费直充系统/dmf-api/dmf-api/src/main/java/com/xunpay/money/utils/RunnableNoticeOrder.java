package com.xunpay.money.utils;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.controller.NoticeController;
import com.xunpay.money.core.job.CuccJob;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.model.CompanyOrder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class RunnableNoticeOrder extends Thread{

    private static final Logger logger = Logger.getLogger(RunnableNoticeOrder.class);
    private CompanyOrder companyOrder;

    public RunnableNoticeOrder(CompanyOrder companyOrder){
        this.companyOrder=companyOrder;
    }



    public void run(){

        String treaty = "";

        StringBuffer noticeUrl = new StringBuffer();

        noticeUrl.append(companyOrder.getNoticeUrl());

        if (noticeUrl.indexOf("?") > 0) {

            noticeUrl.append("&");
        } else {

            noticeUrl.append("?");
        }

        String orderNo = companyOrder.getOutOrderNo();
        BigDecimal moeny = companyOrder.getOrderMoney();

        CompanyInfo companyInfo = CompanyInfo.dao.findFirst("select * from company_info where id=" + companyOrder.getCompanyId() + " ");

        String md5Str = moeny.toString() + orderNo + companyInfo.getMd5key();

        String md5 = EncryptUtils.encrypt(md5Str);

        String finalStr = noticeUrl.
                append("order_no=" + orderNo).
                append("&amount=" + moeny).
                append("&sign=" + md5).toString();

        String result = null;
        String noturl = finalStr;
        System.out.println("回调参数四方" + noturl);
        logger.info("回调参数四方" + noturl);
        result = HttpClientFactory.get1(noturl);
        logger.info("回调四方结果：" + result);
        for (int i=0;i<3;i++){
            if("success".equals(result)||"SUCCESS".equals(result)){
                Db.update("update company_order set notice='回调成功' where id=" + companyOrder.getId());
                break;
            }
        }
    }

    public static void main(String[] args) {
       HttpClientFactory.get("https://eomkaq.cn/notify?order_no=689511441295112248&amount=20.00&sign=595b92691daaa413e3029061979943b1");
    }



}
