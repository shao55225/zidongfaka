package com.xunpay.money.controller;

import com.payment.util.DESUtils;
import com.payment.util.MD5Util;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.CompanyAgentOrder;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.utils.Constant;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.RunnableAgentOrder;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgentOrderController extends BaseController {

    private ExecutorService executor = Executors.newCachedThreadPool();

    private static final String key = "eBAod9zr";

    Runnable runnable = null;

    /**
     * 代充下单入口
     */
    public void create() throws Exception {
        String customerNo = getPara("customerNo");//商户编号
        String outTradeNo = getPara("outTradeNo");//商户订单号
        String rechargeNo = getPara("rechargeNo");//代充值号码（手机号码/加油卡账号）
        String productType = getPara("productType");//产品类型
        String cardSn = getPara("cardSn");//充值卡序号（加密）
        String cardPwd = getPara("cardPwd");//充值卡密码（加密）
        String amount = getPara("amount");//充值金额
        String notifyUrl = getPara("notifyUrl");//回调地址
        String timestamp = getPara("timestamp");//时间戳
        String sign = getPara("sign");//MD5签名
        //系统生成订单号
        String orderNo = "";
        orderNo = "A" + System.currentTimeMillis() + (long) (Math.random() * 1.0E7D);

        Map validation = validation(customerNo, outTradeNo, rechargeNo, productType, cardSn, cardPwd, amount, notifyUrl, timestamp, sign);
        if (validation.get("obj") == null) {
            resultAgent(validation);
            return;
        }

        CompanyAgentOrder companyAgentOrder = (CompanyAgentOrder) validation.get("obj");
        companyAgentOrder.setOrderNo(orderNo);//订单号
        companyAgentOrder.save();

        Map rest = new HashMap();
        rest.put("returnCode", "ok");
        rest.put("returnMsg", "订单提交成功");
        rest.put("customerNo", customerNo);
        rest.put("outTradeNo", outTradeNo);
        rest.put("orderNo", orderNo);
        rest.put("sign", sign);
        runnable = new RunnableAgentOrder(companyAgentOrder);
        try {
            fun();
        } catch (Exception e) {
            System.out.println("报错了");
            e.printStackTrace();
        }

        resultAgent(rest);

    }

    /**
     * 订单查询
     */
    public void query() {
        Map rest = new HashMap();
        try {
            String customerNo = getPara("customerNo");//商户编号
            String outTradeNo = getPara("outTradeNo");//商户订单号
            String timestamp = getPara("timestamp");//时间戳
            String sign = getPara("sign");//MD5签名

            if (customerNo == null || "".equals(customerNo)) {
                rest.put("returnCode", "0001");
                rest.put("returnMsg", "必要参数为空");
                rest.put("customerNo", customerNo);
                rest.put("outTradeNo", outTradeNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }
            if (outTradeNo == null || "".equals(outTradeNo)) {
                rest.put("returnCode", "0001");
                rest.put("returnMsg", "必要参数为空");
                rest.put("customerNo", customerNo);
                rest.put("outTradeNo", outTradeNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }
            if (timestamp == null || "".equals(timestamp)) {
                rest.put("returnCode", "0001");
                rest.put("returnMsg", "必要参数为空");
                rest.put("customerNo", customerNo);
                rest.put("outTradeNo", outTradeNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }
            if (sign == null || "".equals(sign)) {
                rest.put("returnCode", "0001");
                rest.put("returnMsg", "必要参数为空");
                rest.put("customerNo", customerNo);
                rest.put("outTradeNo", outTradeNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }
            CompanyInfo company = CompanyInfo.dao.findFirst(" select  * from company_info where   appid ='" + customerNo + "'");
            if (company == null) {
                rest.put("returnCode", "0007");
                rest.put("returnMsg", "此商户不存在");
                rest.put("customerNo", customerNo);
                rest.put("outTradeNo", outTradeNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }
            if (!"正常".equals(company.getStatus())) {
                rest.put("returnCode", "0008");
                rest.put("returnMsg", "无效的商户状态");
                rest.put("customerNo", customerNo);
                rest.put("outTradeNo", outTradeNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }

            /** 验证签名 */
            Map<String, String> data = new HashMap<>();
            data.put("customerNo", customerNo);
            data.put("outTradeNo", outTradeNo);
            data.put("timestamp", timestamp);

            String signs = MD5Util.md5(data, company.getMd5key());
            if (!sign.equals(signs)) {
                rest.put("returnCode", "0010");
                rest.put("returnMsg", "无效的数字签名");
                rest.put("customerNo", customerNo);
                rest.put("outTradeNo", outTradeNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }

            CompanyAgentOrder first = CompanyAgentOrder.dao.findFirst("select * from company_agent_order where customer_no='" + customerNo + "' and outTrade_no='" + outTradeNo + "'");
            if (first == null) {
                rest.put("returnCode", "0013");
                rest.put("returnMsg", "此订单不存在");
                rest.put("customerNo", customerNo);
                rest.put("outTradeNo", outTradeNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }

            rest.put("returnCode", "ok");
            rest.put("returnMsg", "查询请求成功");
            rest.put("customerNo", customerNo);
            rest.put("outTradeNo", outTradeNo);
            rest.put("orderNo", first.getOrderNo());
            rest.put("rechargeNo", first.getRechargeNo());
            rest.put("status", first.getStatus());
            rest.put("successAmount", first.getActualAmount());
        /*if (first.getProductType().equals("MOBILE")) {
            rest.put("feeAmount", Constant.MOBILE.doubleValue());
        } else if (first.getProductType().equals("UNICOM")) {
            rest.put("feeAmount", Constant.UNICOM.doubleValue());
        } else if (first.getProductType().equals("TELECOM")) {
            rest.put("feeAmount", Constant.TELECOM.doubleValue());
        } else if (first.getProductType().equals("ZSH")) {
            rest.put("feeAmount", Constant.ZSH.doubleValue());
        }*/
            System.out.println(first.getFeeAmount()+"---------");
            rest.put("feeAmount", first.getFeeAmount());
            rest.put("finishTime", first.getTopTime());
            rest.put("failedCode", first.getFailedCode());
            rest.put("sign", sign);
            resultAgent(rest);
        } catch (Exception e) {
            e.printStackTrace();
            rest.put("returnCode", "error");
            rest.put("returnMsg", "系统异常");
            resultAgent(rest);
            return;
        }
    }

    /**
     * 余额查询
     */
    public void selectAmt() {
        Map rest = new HashMap();
        try {
            String customerNo = getPara("customerNo");//商户编号
            String timestamp = getPara("timestamp");//时间戳
            String sign = getPara("sign");//MD5签名
            if (customerNo == null || "".equals(customerNo)) {
                rest.put("returnCode", "0001");
                rest.put("returnMsg", "必要参数为空");
                rest.put("customerNo", customerNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }
            if (timestamp == null || "".equals(timestamp)) {
                rest.put("returnCode", "0001");
                rest.put("returnMsg", "必要参数为空");
                rest.put("customerNo", customerNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }

            CompanyInfo company = CompanyInfo.dao.findFirst(" select  * from company_info where   appid ='" + customerNo + "'");
            if (company == null) {
                rest.put("returnCode", "0007");
                rest.put("returnMsg", "此商户不存在");
                rest.put("customerNo", customerNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }
            if (!"正常".equals(company.getStatus())) {
                rest.put("returnCode", "0008");
                rest.put("returnMsg", "无效的商户状态");
                rest.put("customerNo", customerNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }

            /** 验证签名 */
            Map<String, String> data = new HashMap<>();
            data.put("customerNo", customerNo);
            data.put("timestamp", timestamp);

            String signs = MD5Util.md5(data, company.getMd5key());
            if (!sign.equals(signs)) {
                rest.put("returnCode", "0010");
                rest.put("returnMsg", "无效的数字签名");
                rest.put("customerNo", customerNo);
                rest.put("sign", sign);
                resultAgent(rest);
                return;
            }

            rest.put("returnCode", "ok");
            rest.put("returnMsg", "查询请求成功");
            rest.put("customerNo", customerNo);
            rest.put("balance", company.getBalance());
            rest.put("sign", sign);
            resultAgent(rest);

        } catch (Exception e) {
            e.printStackTrace();
            rest.put("returnCode", "error");
            rest.put("returnMsg", "系统异常");
            resultAgent(rest);
            return;
        }
    }


    public Map validation(String customerNo, String outTradeNo, String rechargeNo, String productType, String cardSn, String cardPwd, String amount, String notifyUrl, String timestamp, String sign) {
        Map map = new HashMap();
        CompanyInfo company = CompanyInfo.dao.findFirst(" select  * from company_info where   appid ='" + customerNo + "'");
        BigDecimal balance = null;//余额
        String cardSns = "";
        String cardPwds = "";
        try {
            cardSns = DESUtils.decryptDES(cardSn, key);//卡号解密
            cardPwds = DESUtils.decryptDES(cardPwd, key);//卡密解密
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("customerNo", customerNo);
        map.put("outTradeNo", outTradeNo);
        map.put("sign", sign);


        if (customerNo == null || "".equals(customerNo)) {
            map.put("returnCode", "0001");
            map.put("returnMsg", "必要参数为空");
            return map;
        }
        if (outTradeNo == null || "".equals(outTradeNo)) {
            map.put("returnCode", "0001");
            map.put("returnMsg", "必要参数为空");
            return map;
        }
        if (rechargeNo == null || "".equals(rechargeNo)) {
            map.put("returnCode", "0001");
            map.put("returnMsg", "必要参数为空");
            return map;
        }
        if (productType == null || "".equals(productType)) {
            map.put("returnCode", "0001");
            map.put("returnMsg", "必要参数为空");
            return map;
        }
        if (cardSn == null || "".equals(cardSn)) {
            map.put("returnCode", "0001");
            map.put("returnMsg", "必要参数为空");
            return map;
        }
        if (cardPwd == null || "".equals(cardPwd)) {
            map.put("returnCode", "0001");
            map.put("returnMsg", "必要参数为空");
            return map;
        }
        if (amount == null || "".equals(amount)) {
            map.put("returnCode", "0001");
            map.put("returnMsg", "必要参数为空");
            return map;
        }
        if (!isNumeric(amount)) {
            map.put("returnCode", "0003");
            map.put("returnMsg", "无效的充值金额");
            return map;
        }
        if (notifyUrl == null || "".equals(notifyUrl)) {
            map.put("returnCode", "0001");
            map.put("returnMsg", "必要参数为空");
            return map;
        }
        if (timestamp == null || "".equals(timestamp)) {
            map.put("returnCode", "0001");
            map.put("returnMsg", "必要参数为空");
            return map;
        }
        if (sign == null || "".equals(sign)) {
            map.put("returnCode", "0001");
            map.put("returnMsg", "必要参数为空");
            return map;
        }


        if (company == null) {
            map.put("returnCode", "0007");
            map.put("returnMsg", "此商户不存在");
            return map;
        } else {
            if (!"正常".equals(company.getStatus())) {
                map.put("returnCode", "0008");
                map.put("returnMsg", "无效的商户状态");
                return map;
            }
            /**  判断商户余额是否充足 */
            balance = company.getBalance().subtract(Constant.MOBILE);
            if (balance.compareTo(new BigDecimal(0)) == -1) {
                //System.out.println("a小于b");
                map.put("returnCode", "0009");
                map.put("returnMsg", "账户余额不足");
                return map;
            }
            /** 是否配置秘钥 */
            if (company.getMd5key() == null || "".equals(company.getMd5key())) {
                map.put("returnCode", "0016");
                map.put("returnMsg", "未配置密钥信息");
                return map;
            }
        }
        company.setBalance(balance);
        company.update();
        /** 验证签名 */
        Map<String, String> data = new HashMap<>();
        data.put("customerNo", customerNo);
        data.put("outTradeNo", outTradeNo);
        data.put("rechargeNo", rechargeNo);
        data.put("productType", productType);
        data.put("cardSn", cardSn);
        data.put("cardPwd", cardPwd);
        data.put("amount", amount);
        data.put("notifyUrl", notifyUrl);
        data.put("timestamp", timestamp);

        String signs = MD5Util.md5(data, company.getMd5key());

        if (!sign.equals(signs)) {
            map.put("returnCode", "0010");
            map.put("returnMsg", "无效的数字签名");
            return map;
        }


        /** 判断卡密信息 */
        if ("MOBILE".equals(productType) || "UNICOM".equals(productType) || "TELECOM".equals(productType)) {
            if (rechargeNo.length() != 11) {
                map.put("returnCode", "0004");
                map.put("returnMsg", "无效的充值号码");
                return map;
            }
            if (cardSns.trim().length() != 15) {
                map.put("returnCode", "0005");
                map.put("returnMsg", "卡号或卡密格式错误");
                return map;
            }
            if (cardPwds.trim().length() != 19) {
                map.put("returnCode", "0005");
                map.put("returnMsg", "卡号或卡密格式错误");
                return map;
            }
        }
        /** 判断订单号是否重复 */
        List<CompanyAgentOrder> companyAgentOrders = CompanyAgentOrder.dao.find("select id from company_agent_order where outTrade_no=" + outTradeNo);
        if (companyAgentOrders.size() > 0) {
            map.put("returnCode", "0006");
            map.put("returnMsg", "商户订单号已存在");
            return map;
        }

        CompanyAgentOrder companyAgentOrder = new CompanyAgentOrder();
        companyAgentOrder.setCustomerNo(customerNo);
        companyAgentOrder.setOutTradeNo(outTradeNo);
        companyAgentOrder.setRechargeNo(rechargeNo);
        companyAgentOrder.setProductType(productType);
        companyAgentOrder.setCardSn(cardSns);
        companyAgentOrder.setCardPwd(cardPwds);
        companyAgentOrder.setAmount(new BigDecimal(amount));
        companyAgentOrder.setNotifyUrl(notifyUrl);
        companyAgentOrder.setCreateTime(new Date());
        companyAgentOrder.setFeeAmount(Constant.UNICOM);   //以后做判断
        companyAgentOrder.setStatus(Constant.DEALING);//充值中
        map.put("obj", companyAgentOrder);
        return map;


    }

    public void fun() throws Exception {

        executor.submit(runnable);
    }


    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }


}
