package com.xunpay.money.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyInfo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import sun.misc.BASE64Decoder;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.service.AipayBoxSevice;
import com.xunpay.money.service.DingService;
import com.xunpay.money.service.EdaPayService;
import com.xunpay.money.service.HeipayService;
import com.xunpay.money.service.Pay91Service;
import com.xunpay.money.service.PayService;
import com.xunpay.money.service.SuixinPayService;
import com.xunpay.money.service.THDPayService;
import com.xunpay.money.service.Wtvip6Service;
import com.xunpay.money.service.ZhanshiService;
import com.xunpay.money.service.abjngd.RSAEncrypt;
import com.xunpay.money.utils.Constant;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientFactory;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.HttpsClientHelper;

public class NoticeController extends BaseController {

    private static final Logger logger = Logger.getLogger(NoticeController.class);

    private Map<String, String> getReqMap(HttpServletRequest request) {
        Map<String, String[]> paraMap = request.getParameterMap();
        Map<String, String> params = new HashMap<String, String>();
        logger.info("**********************************");
        for (Iterator<String> iter = paraMap.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) paraMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
            logger.info(name + " ----> " + valueStr);
        }
        logger.info("**********************************");
        return params;
    }

    /**
     * 回调四方
     **/
    public String noticeExe(CompanyOrder companyOrder) {

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

        @SuppressWarnings("unused")
        String result = null;
        String noturl = finalStr;
        System.out.println("回调参数四方" + noturl);
        logger.info("回调四方："+noturl);
        result = HttpClientFactory.get(noturl);

        return result;
    }


    /**
     * 回调话费Api（运营商）
     **/
    public String noticePhoneApi(CompanyApiOrder companyApiOrder) {


        String treaty = "";

        StringBuffer noticeUrl = new StringBuffer();

        noticeUrl.append(companyApiOrder.getNoticeUrl());

        if (noticeUrl.indexOf("?") > 0) {

            noticeUrl.append("&");
        } else {

            noticeUrl.append("?");
        }

        //回调规则
        //20200327001hfapi10
        //str=  apiordersn=20200327001^payType=hfapi^money=10^order^sign=sssssss

        String orderNo = companyApiOrder.getToken();//商务合作
        //String payType = companyOrder.getPayType();
        BigDecimal moeny = companyApiOrder.getOrderMoney();
        String results = "";
        String msg = "";
        if ("充值成功".equals(companyApiOrder.getStatus())) {
            results = "success";
            msg = "1";
        } else if ("充值异常".equals(companyApiOrder.getStatus())) {
            results = "success";
            msg = "2";
        } else {
            results = "fail";
            msg = "0";
        }


        String phone = companyApiOrder.getAppid();
        String op_no = companyApiOrder.getOutOrderNo();

        CompanyInfo companyInfo = CompanyInfo.dao.findFirst("select * from company_info where id=" + companyApiOrder.getCompanyId() + " ");

        String md5Str = results + msg + orderNo + phone + moeny.toString() + op_no + companyInfo.getMd5key();

        String md5 = EncryptUtils.encrypt(md5Str);

        String finalStr = noticeUrl.
                append("result=" + results).
                append("&msg=" + msg).
                append("&order=" + orderNo).
                append("&phone_no=" + phone).
                append("&amount=" + moeny).
                append("&op_no=" + op_no).
                append("&sign=" + md5).toString();
        System.out.println("回调参数运营商" + finalStr);
        logger.info("回调参数运营商："+finalStr);

        @SuppressWarnings("unused")
        String noturl = finalStr;
        String result = HttpClientFactory.get(noturl);

        return result;
    }


}
