package com.xunpay.money.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunpay.money.model.*;
import com.xunpay.money.utils.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.core.BaseController;

public class OrderController extends BaseController {

    private static final Logger logger = Logger.getLogger(OrderController.class);


    CompanyOrder payOrder = new CompanyOrder();

    public void ypay() {

        renderText("success");
    }


    /**
     * 支付方下单入口
     * 三网兼容
     */
    public void create() {

        //商户ID
        String s_id = getPara("s_id");
        String money = getPara("amount");
        String nextUrl = getPara("notify_url");
        String sign = getPara("sign");
        String apiordersn = getPara("order_no");

        //系统配置
        SysConfig sysconfig = SysConfig.dao.findFirst(" select * from sys_config  where code='getQrTime' ");
        String qrTime = sysconfig.getContent();

        CompanyInfo company = CompanyInfo.dao.findFirst(" select  id,username,md5key from company_info where   appid ='" + s_id + "'  and status = '正常'  and is_del = 'N'  ");

        if (company == null) {
            result(false, "商户不存在", null);
            return;
        }

        String md5Key = company.getMd5key();
        CompanyOrder uuid = (CompanyOrder) CompanyOrder.dao
                .findFirst(" select  id,out_order_no   from company_order  where  out_order_no ='" + apiordersn + "' ");
        if (uuid != null) {
            result(false, "订单号重复", null);
            return;
        }

        if (StringUtils.isEmpty(s_id) || StringUtils.isEmpty(money) || StringUtils.isEmpty(sign)
                || StringUtils.isEmpty(apiordersn)) {
            result(false, "非法访问", null);
            return;
        }
        String currentStr = money + nextUrl + apiordersn + s_id + md5Key;
        String signSystem = EncryptUtils.encrypt(currentStr);

        if (!signSystem.toUpperCase().equals(sign)) {
            result(false, "sign异常", null);
            return;
        }


        synchronized (payOrder) {
            //查询已经生成支付链接的订单
            String sql = " select *   from company_order where  addtime>(select date_sub(now(), interval " + Integer.parseInt(qrTime) + "  second ))  and trade_no!='0'     and    order_money='" + money + "' and status='匹配中'    	order by  addtime     ";
            payOrder = CompanyOrder.dao.findFirst(sql);
        }

        if (payOrder != null && payOrder.getStatus().equals("匹配中")) {
            payOrder.setOutOrderNo(apiordersn);
            //支付时间
            payOrder.setSettletime(new Date());
            payOrder.setCompanyName(company.getUsername());
            payOrder.setCompanyId(company.getId());
            payOrder.setStatus("支付中");
            payOrder.setNoticeUrl(nextUrl);
            payOrder.update();

            //充值单号
            String czOrderId = payOrder.getAlipayName();

            CompanyApiOrder czOrder = CompanyApiOrder.
                    dao.findFirst(" select *   from company_apiorder where  order_no='" + czOrderId + "'  ");

            czOrder.setStatus("充值中");
            //支付时间
            czOrder.setSettletime(new Date());

            czOrder.update();

            Map<String, String> data = new HashMap<>();

            data.put("orderNo", apiordersn);
            String orderNo = payOrder.getOrderNo();
            SysConfig qrURl = SysConfig.dao.findFirst(" select * from sys_config  where code='qrUrl' ");

            data.put("qrCode", qrURl.getContent() + "?orderId=" + orderNo);
            logger.info("四方请求订单号：" + apiordersn + "--得到地址：" + qrURl.getContent() + "?orderId=" + orderNo);
            result(true, "下单成功", data);

        } else {

            result(false, "下单失败，库存不足!", null);

            return;
        }

    }


    /**
     * 话费API下单入口
     * <p>
     * 参数 phone-order_no-s_id-amount-notify_url-sign
     * 返回result-msg
     * Success:fail
     * http://localhost:9999/xunpay-alipay/order/invest?
     **/

    public synchronized void invest() {

        //商户ID
        String s_id = getPara("s_id");
        //商户订单号
        String apiordersn = getPara("order_no");
        //要充值手机号
        String phone = getPara("phone");
        //充值金额
        String money = getPara("amount");
        //回调地址
        String nextUrl = getPara("notify_url");
        //签名
        String sign = getPara("sign");

        CompanyInfo company = CompanyInfo.dao.findFirst(" select  id,username,md5key,balance,self_rebate from company_info where   appid ='" + s_id + "'  and status = '正常'  and is_del = 'N'  ");

        if (company == null) {
            result(false, "商户不存在", null);
            return;
        }

        String md5Key = company.getMd5key();
        CompanyOrder uuid = CompanyOrder.dao
                .findFirst(" select id  from company_apiorder  where  out_order_no ='" + apiordersn + "' ");
        if (uuid != null) {
            result(false, "订单号重复", null);
            return;
        }

        if (StringUtils.isEmpty(s_id) || StringUtils.isEmpty(money) || StringUtils.isEmpty(sign) || StringUtils.isEmpty(apiordersn)) {
            result(false, "非法访问", null);
            return;
        }

        String currentStr = money + nextUrl + apiordersn + phone + s_id + md5Key;
        String signSystem = EncryptUtils.encrypt(currentStr);
        if (!signSystem.toUpperCase().equals(sign)) {
            result(false, "sign异常", null);
            return;
        }

        String currentOrder = "P" + System.currentTimeMillis() + (long) (Math.random() * 1.0E7D);
        CompanyApiOrder order = new CompanyApiOrder();

        @SuppressWarnings("unused")
        Integer opId = MobileCheck.isChinaMobilePhoneNum(phone);

        switch (opId) {

            case 1:
                order.setTradeNo("1");
                break;
            case 2:
                order.setTradeNo("2");
                break;
            case 3:
                order.setTradeNo("3");
                break;
            default:
                order.setTradeNo("0");
                break;
        }
        //电信先走一次，是否可以充值，不能充值直接返回失败
//        if (order.getTradeNo().equals("3")){
//            CtccTest03 test2 = new CtccTest03();
//            System.out.println(test2.getSessionId2());
//            String sessionId = test2.getSessionId();
//            int map = test2.topUp(phone, money, 0, null, sessionId);
//            if (map != 0){
//                result(false, "电信："+phone+":手机号无法充值", null);
//                return;
//            }
//        }

        /** 商户扣款 */
        BigDecimal balance = company.getBalance().subtract(new BigDecimal(money));

        if (balance.compareTo(new BigDecimal(0)) < 1) {
            result(false, "商户余额不足", null);
            return;
        }


        /**
         * 20秒内出现重复金额重复手机号，则无视且返回失败
         * **/

//        String repeatSql = " select * from company_apiorder  where  appid='" + phone + "' and   order_money='" + money + "'    	 and  addtime>(select date_sub(now(), interval 20 second) ) ";
//
//        CompanyApiOrder repeatObj = CompanyApiOrder.dao.findFirst(repeatSql);
//
//        if (repeatObj != null) {
//            result(false, "重复提交订单", null);
//            return;
//        }


        BigDecimal b1 = new BigDecimal(Double.valueOf(money));
        BigDecimal b2 = new BigDecimal(company.getSelfRebate().toString());

        String money1 = String.valueOf(b1.multiply(b2).doubleValue());


        /**
         * 折扣
         * **/
        Db.update("update company_info set balance=balance-" + money1 + " where id=" + company.getId());

        /**
         * 写入财务明细表
         ***/
        CompanyInfo company1 = CompanyInfo.dao.findFirst(" select  id,username,md5key,balance from company_info where   appid ='" + s_id + "'  and status = '正常'  and is_del = 'N'  ");

        CompanyBill bill = new CompanyBill();
        bill.setCompanyId(company.getId());
        bill.setInMoney(BigDecimal.valueOf(Double.valueOf(money1) * -1));
        bill.setType("充值扣款");
        bill.setRemark("充值订单扣款");
        bill.setStatus("已结算");
        bill.setOrderNo(currentOrder);//订单号
        bill.setBalance(company1.getBalance());//新对象
        bill.setCompanyName(company.getNickname());
        bill.setOrderMoney(new BigDecimal(money));
        bill.setAddtime(new Date());
        bill.save();

        //手机号码
        order.setAppid(phone);
        order.setTitle("api系统下单");
        order.setOrderNo(currentOrder);
        order.setOutOrderNo(apiordersn);
        order.setOrderMoney(BigDecimal.valueOf(Double.valueOf(money).doubleValue()));
        order.setNoticeUrl(nextUrl);
        order.setStatus("未充值");
        order.setNotice("未回调");
        order.setCompanyId(company.getId());
        order.setAddtime(new Date());
        order.setCompanyName(company.getUsername());
        order.save();

        result(true, "受理成功", currentOrder);

    }


    /**
     * 查充值单接口  状态 未充值 充值中 充值成功 充值失败
     * http://localhost:9999/xunpay-alipay/order/queryStatus?
     */
    public void queryStatus() {

        String orderId = getPara("orderId");
        String sid = getPara("sid");
        String sign = getPara("sign");

        if (orderId == null || sid == null || sign == null) {
            result(false, "参数非法", null);
            return;
        }

        CompanyInfo company = CompanyInfo.dao.findFirst(" select  id,username,md5key from company_info where   appid ='" + sid + "'  and status = '正常'  and is_del = 'N'  ");
        if (company == null) {
            result(false, "商户不存在", null);
            return;
        }

        String md5Key = company.getMd5key();
        String currentStr = orderId + sid + md5Key;
        String signSystem = EncryptUtils.encrypt(currentStr);

        if (!signSystem.toUpperCase().equals(sign)) {
            result(false, "sign异常", null);
            return;
        }
        CompanyApiOrder apiOrder = CompanyApiOrder.dao.findFirst("select  * from company_apiorder where out_order_no='" + orderId + "'");

        if (apiOrder != null) {
            String status = apiOrder.getStatus();
            String token = apiOrder.getToken();


            if (status != null || !"".equals(status)) {
                if (status.equals("充值成功")) {
                    result(true, status, token);
                    return;
                } else {
                    result(true, status, "状态查询成功");
                    return;
                }

            }
        }

        result(false, "状态查询失败", "订单号不存在");


    }

    /**
     * 查余额接口
     * http://localhost:9999/xunpay-alipay/order/queryBanlance?
     */

    public void queryBalance() {

        String sid = getPara("sid");
        String sign = getPara("sign");

        if (sid == null || sign == null) {
            result(false, "参数非法", null);
            return;
        }

        CompanyInfo company = CompanyInfo.dao.findFirst(" select  id,username,md5key from company_info where   appid ='" + sid + "'  and status = '正常'  and is_del = 'N'  ");

        if (company == null) {
            result(false, "商户不存在", null);
            return;
        }

        String md5Key = company.getMd5key();
        String currentStr = sid + md5Key;
        String signSystem = EncryptUtils.encrypt(currentStr);

        if (!signSystem.toUpperCase().equals(sign)) {
            result(false, "sign异常", null);
            return;
        }

        BigDecimal balance = Db.queryBigDecimal(" select balance from company_info  where appid='" + sid + "' ");

        result(true, "余额查询成功", balance);

    }

    /**
     * 统计微信支付链接的数据
     **/
    public void getWxData() {


        long ten = Db.queryLong("select COUNT('')   from company_order where order_money='10'  and status='匹配中'  ");
        long twenty = Db.queryLong("select COUNT('')   from company_order where order_money='20'  and status='匹配中'  ");
        long threty = Db.queryLong("select COUNT('')   from company_order where order_money='30'   and status='匹配中' ");
        long fifty = Db.queryLong("select COUNT('')   from company_order where order_money='50'   and status='匹配中' ");
        long hundred = Db.queryLong("select COUNT('')   from company_order where order_money='100'  and status='匹配中'  ");
        long hundred2 = Db.queryLong("select COUNT('')   from company_order where order_money='200'   and status='匹配中' ");
        long hundred3 = Db.queryLong("select COUNT('')   from company_order where order_money='300'   and status='匹配中' ");
        long hundred5 = Db.queryLong("select COUNT('')   from company_order where order_money='500'   and status='匹配中'  ");

        String cData = "["
                + "" + ten + ","
                + "" + twenty + ","
                + "" + threty + ","
                + "" + fifty + ","
                + "" + hundred + ","
                + "" + hundred2 + ","
                + "" + hundred3 + ","
                + "" + hundred5 + "]";

        renderJson("{\"countData\":" + cData + "}");

    }

    /**
     * 统计支付中的数量
     **/
    public void getPaying() {


        long ten = Db.queryLong("select COUNT('')   from company_order where order_money='10'  and status='支付中'  ");
        long twenty = Db.queryLong("select COUNT('')   from company_order where order_money='20'  and status='支付中'  ");
        long threty = Db.queryLong("select COUNT('')   from company_order where order_money='30'   and status='支付中' ");
        long fifty = Db.queryLong("select COUNT('')   from company_order where order_money='50'   and status='支付中' ");
        long hundred = Db.queryLong("select COUNT('')   from company_order where order_money='100'  and status='支付中'  ");
        long hundred2 = Db.queryLong("select COUNT('')   from company_order where order_money='200'   and status='支付中' ");
        long hundred3 = Db.queryLong("select COUNT('')   from company_order where order_money='300'   and status='支付中' ");
        long hundred5 = Db.queryLong("select COUNT('')   from company_order where order_money='500'   and status='支付中'  ");

        String cData = "["
                + "" + ten + ","
                + "" + twenty + ","
                + "" + threty + ","
                + "" + fifty + ","
                + "" + hundred + ","
                + "" + hundred2 + ","
                + "" + hundred3 + ","
                + "" + hundred5 + "]";

        renderJson("{\"countData\":" + cData + "}");

    }


    /**
     * 演示下单
     **/

    public void testOrder() {

        String phoneNumber = getPara("phoneNumber");
        CompanyIp companyIp = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
        String money = getPara("money");
        String channelCode = getPara("channelCode");


        String ip = HttpRequestUtils.getIpAddress(getRequest());
        System.out.println("客户端下单ip：<------------------->" + ip);

        String province = null;
        for (int i = 0; i < 10; i++) {
            //ip省份
            province = getIp(ip);
            System.out.println("匹配到省份的ip第" + i + "次:" + province);
            if (province != null) {
                break;
            }
        }


        //电信
        if ("ctcc".equals(channelCode)) {

            String gotoip = province.split(":")[0];
            //下单端口
            Integer port = Integer.parseInt(province.split(":")[1].trim());
            CompanyIp companyIp1 = new CompanyIp();
            companyIp1.setIp(gotoip);
            companyIp1.setPort(port);
            CtccTest2 ctcc = new CtccTest2();
            String sesid = ctcc.getSessionId2(companyIp);
            System.out.print("sesid---->" + sesid);
            Map<String, String> map = ctcc.topUp(phoneNumber, money, 0, companyIp1, sesid);
            String payUrl = map.get("payUrl");
            if (payUrl != null) {
                result(true, "success", payUrl);
            } else {
                result(false, "faile", null);
            }
            //联通
        } else if ("cucc".equals(channelCode)) {

            if (province == null) {
                result(false, "代理ip为空", null);
            } else {
                String gotoip = province.split(":")[0];
                //下单端口
                Integer port = Integer.parseInt(province.split(":")[1].trim());
                CuccTest2 cuccTest2 = new CuccTest2();
                CompanyIp companyIp1 = new CompanyIp();
                companyIp1.setIp(gotoip);
                companyIp1.setPort(port);
                String payUrl = cuccTest2.topUp(phoneNumber, money + ".00", 0, companyIp1, ip);
                if (payUrl != null && !"".equals(payUrl)) {
                    Map payMap = cuccTest2.toPayAndpayStr(payUrl, gotoip, port, ip);
                    String url = payMap.get("url").toString();
                    if (payUrl != null) {
                        result(true, "success", url);
                    } else {
                        result(false, "faile", null);
                    }

                }
            }
        }
        //移动
        else {
            result(false, "faile", null);

        }

    }

    public void testnot() {
        System.out.println("进入回调-------------------------------------------");
        result(true, "", "success");
    }


    /**
     * 通过客户端真实ip去匹配
     * pay
     **/

    public void pay() {

        String orderId = getPara("orderId");

        System.out.println("进入获取URL---" + orderId);
        if (orderId != null && !"".equals(orderId)) {   //status='支付中' and

            CompanyOrder order = CompanyOrder.dao.findFirst(" select id , qr_url , remark, pid , trade_no ,order_money,appid from company_order  where status='支付中' and order_no ='" + orderId + "'");
            String ip = HttpRequestUtils.getIpAddress(getRequest());
            //String ip="110.187.130.141";
            System.out.println("客户端下单ip：<------------------->" + ip);
            if (order != null) {
                String province = null;
                for (int i = 0; i < 10; i++) {
                    //ip省份
                    province = getIp(ip);
                    System.out.println("匹配到省份的ip第" + i + "次:" + province);
                    if (province != null) {
                        break;
                    }
                }
                String gotoip = province.split(":")[0];
                //下单端口
                Integer port = Integer.parseInt(province.split(":")[1].trim());
                order.setRsaAlipay(province);//代理ip
                order.setRsaPrivate(ip);//用戶ip
                CompanyIp companyIp = new CompanyIp();
                companyIp.setIp(gotoip);
                companyIp.setPort(port);

                //通过客户的ip去下单
                int successT = 1;
                if (order != null) {
                    String Operator = order.getTradeNo();
                    String qrUrl = "";
                    //联通才用长效ip，电信则直接返回收银台
                    if ("2".equals(Operator)) {
                        if (province == null) {
                            result(false, "代理ip为空", null);
                        } else {
                            CuccTest2 cuccTest2 = new CuccTest2();
                            DecimalFormat decimalFormat = new DecimalFormat("0.00");
                            String moneys = decimalFormat.format(order.getOrderMoney());
                            String url = cuccTest2.topUp(order.getAppid(), moneys, 0, companyIp, ip);
                            if (url != null && !"".equals(url)) {
                                order.setRemark(url);
                                order.setTradeNo("2");
                                Map payMap = cuccTest2.toPayAndpayStr(url, gotoip, port, ip);
                                //返回的支付链接
                                qrUrl = payMap.get("url").toString();
                                System.out.println("联通微信支付链接：" + qrUrl);
                                //跟新数据保存联通微信深度链接和订单查询地址
                                order.setQrUrl(qrUrl);
                                order.setReturnUrl("https://unipay.10010.com/udpNewPortal/miniwappayment/weixinH5Return?payStr=" + payMap.get("payStr").toString());
                                //order.setReturnUrl("https://unipay.10010.com/udpNewPortal/miniwappayment/weixinH5ReturnPre?payStr=" + payMap.get("payStr").toString());
                                //order.setReturnUrl(payMap.get("payStr").toString());
                            } else {
                                //successT = error();
                                qrUrl = "";
                            }
                            // result(true, "2", qrUrl);
                        }
                    } else if ("3".equals(Operator)) {

                        //qrUrl = order.getQrUrl();
                        CtccTest2 ctcc = new CtccTest2();
                        Integer m = order.getOrderMoney().intValue();
                        String money = m.toString();
                        String sesid = ctcc.getSessionId2(companyIp);
                        System.out.print("sesid---->" + sesid);
                        Map<String, String> map = ctcc.topUp(order.getAppid(), money, 0, companyIp, sesid);
                        String sessionId = map.get("sessionid");
                        qrUrl = map.get("payUrl");
                        String orderid = map.get("orderid");
                        order.setReturnUrl("http://wapzt.189.cn:8010/rechargeV2/rechargeV2_success.html?sessionid=" + sessionId + "&shopid=20001&channel=wap&ct=0&code=-10008&islogin=false");
                        //order.setReturnUrl("https://wappay.189.cn/pay/toPayQuery.do?boId=" + sessionId);
                        order.setToken(orderid);
                        order.setQrUrl(qrUrl);
                        order.setPid(sessionId);   //电信查询需要的session
                        //result(true, "3", qrUrl);

                    } else if ("1".equals(Operator)) {


                        SysConfig qrURl = SysConfig.dao.findFirst(" select * from sys_config  where code='qrUrl' ");
                        String ctx = qrURl.getContent();
                        String[] temp = ctx.split("pay.jsp");
                        //1.获取移动用户的ck
                        CmccCk cmccCk = CmccCk.dao.findFirst(" select * from cmcc_ck  where isU<10 order by  id desc ");//null;
                        if (cmccCk != null) {
                            Db.update("update cmcc_ck set isU=isU+1 where id=" + cmccCk.getId());
                        }
                        //2.获取移动移动h5防风控标识
                        SysConfig sysconfig = SysConfig.dao.findFirst(" select * from sys_config  where code='risk' ");
                        String userName = sysconfig.getContent();//"2Ekg8zdp0P0GUbosRYInUw==";
                        String cmccToken = "";
                        if (cmccCk != null) {
                            cmccToken = cmccCk.getSessionId();//"jsessionid-cmcc=nED0E568B262558BA56463ACD289009D1-1";
                        } else {
                            cmccToken = "jsessionid-cmcc=nED0E568B262558BA56463ACD289009D1-1";
                        }
                        CmccTest cmccTest = new CmccTest();
                        CmccUtil util = new CmccUtil();
                        //看看是否有优惠
                        boolean isYh = util.isYh(order.getAppid());
                        //下单
                        String payRul = cmccTest.saveOrder(order.getAppid(), order.getOrderMoney().doubleValue(), cmccToken, isYh, companyIp.getIp(), companyIp.getPort());
                        //下单失败的情况则再次换ck下单
                        if (payRul != null) {
                            order.setRemark(payRul);
                            order.setReturnUrl("");
                            String location = util.ssoCheckHtml(payRul);
                            Map maps = util.getHtmlParam(location);
                            Map<String, String> bodyMap = cmccTest.payAndH5(maps, userName);
                            String body = bodyMap.get("body");
                            String orderId1 = bodyMap.get("orderId");
                            String noticeUrl = bodyMap.get("noticeUrl");
                            String voucherUrl = bodyMap.get("voucherUrl");
                            //下载
                            util.downLoad(body, orderId1);
//                            qrUrl = orderId1 + ".jsp";
                            order.setQrUrl(orderId1 + ".jsp");
                            order.setToken(orderId1);
                            order.setReturnUrl(noticeUrl);//查单地址
                            order.setRsaAlipay(voucherUrl);//最终的凭证地址


                            qrUrl = temp[0] + "order/" + order.getQrUrl();
                        } else {
                            //轮回再次获取ck
                            CmccCk ck2 = CmccCk.dao.findFirst(" select * from cmcc_ck  where isU<10 order by  id desc     ");
                            String payU = cmccTest.saveOrder(order.getAppid(), order.getOrderMoney().doubleValue(), ck2.getSessionId(), isYh, companyIp.getIp(), companyIp.getPort());
                            if (payU == null) {
                                qrUrl="";
                            }else {
                                order.setRemark(payRul);
                                order.setReturnUrl("");
                                String location = util.ssoCheckHtml(payU);
                                Map maps = util.getHtmlParam(location);
                                Map<String, String> bodyMap = cmccTest.payAndH5(maps, userName);
                                String body = bodyMap.get("body");
                                String orderId1 = bodyMap.get("orderId");
                                String noticeUrl = bodyMap.get("noticeUrl");
                                String voucherUrl = bodyMap.get("voucherUrl");
                                //下载
                                util.downLoad(body, orderId1);
                                //qrUrl = orderId1 + ".jsp";
                                order.setQrUrl(orderId1 + ".jsp");
                                order.setToken(orderId1);
                                order.setReturnUrl(noticeUrl);//查单地址
                                order.setRsaAlipay(voucherUrl);//最终的凭证地址
                                qrUrl = temp[0] + "order/" + order.getQrUrl();
                            }
                        }
                    }
                    //重定向
                    result(true, "success", qrUrl);
                    order.update();
                } else {
                    result(false, "客官出错了呢，查询结果为空", null);
                }
            } else {
                result(false, "客官出错了呢11", null);
                return;
            }
        }

    }

    public static int error() {
        System.out.println("没有正常产生二维码");
        return 0;
    }


    /**
     *Sec request
     *
     **/
//    public  void getDeeplink() {
//        String orderId = getPara("orderId");
//        if (orderId != null && !"".equals(orderId)) {
//            String qrUrl = "";
//	        CompanyOrder order = CompanyOrder.dao.findFirst(" select id , qr_url , remark, pid , trade_no from company_order  where  order_no ='" + orderId + "' ");
//	    	   CuccTest cuccTest = new CuccTest();
//	        String pay = order.getRemark();
//	        Map payMap = cuccTest.toPayAndpayStr(pay, gotoip, port);
//	        //返回的支付链接
//	        qrUrl = payMap.get("url").toString();
//	        //跟新数据保存联通微信深度链接和订单查询地址
//	        order.setQrUrl(qrUrl);
//        }
//    }

    /**
     * 查找ip
     **/

    public static String getIp(String ip) {

        String url = "http://api.ip138.com/query/?ip=" + ip;
        String token = "ec97f055cc74ff0cdde05c35bb6b9fcc";
        String result = HttpClientFactory.getIp(url, token);
        JSONObject jso = JSONObject.parseObject(result);
        String proStr = jso.getString("data").split(",")[1].replaceAll("\"", "");
        String ipPort = "";
        SysConfig sysconfig = SysConfig.dao.findFirst(" select * from sys_config  where code='noticeIp' ");
        String IpUrl = sysconfig.getContent();
        String pid = "pid=" + IpUrl.split("&pid=")[1].toString().split("&")[0];

        switch (proStr) {
            case "湖南":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=23"));
                break;
            case "陕西":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=11"));
                break;
            case "湖北":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=24"));
                break;
            case "江苏":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=27"));
                break;
            case "云南":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=17"));
                break;
            case "广东":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=21"));
                break;
            case "浙江":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=26"));
                break;
            case "上海":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=2"));
                break;
            case "山东":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=13"));
                break;
            case "内蒙古":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=7"));
                break;
            case "宁夏":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=8"));
                break;
            case "四川":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=18"));
                break;
            case "吉林":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=5"));
                break;
            case "甘肃":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=9"));
                break;
            case "福建":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=22"));
                break;
            case "新疆":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=10"));
                break;
            case "河北":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=14"));
                break;
            case "青海":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=29"));
                break;
            case "河南":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=15"));
                break;
            case "山西":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=12"));
                break;
            case "北京":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=0"));
                break;
            case "广西":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=20"));
                break;
            case "重庆":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=3"));
                break;
            case "海南":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=30"));
                break;
            case "天津":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=1"));
                break;
            case "安徽":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=28"));
                break;
            case "辽宁":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=6"));
                break;
            case "江西":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=25"));
                break;
            case "黑龙江":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=4"));
                break;
            case "贵州":
                ipPort = HttpClientFactory.get(IpUrl.replaceAll(pid, "pid=19"));
                break;
        }
        System.out.println("流冠获取ip：" + ipPort);
        logger.info("使用Ip-------------->" + ipPort);
        return ipPort;

    }


    /**
     * ip检测
     ***/
    public void ipCheck() {
        //http://localhost:9999/api/order/ipCheck
        List<PhoneYzm> list = PhoneYzm.dao.find(" select * from phone_yzm ");

        for (int i = 0; i < list.size(); i++) {
            String ip = list.get(i).getYzm().split(":")[0];
            int port = Integer.valueOf(list.get(i).getYzm().split(":")[1]);
            String result = HttpClientHelper.sendGetProxy("http://www.baidu.com", ip, port);

            System.out.println(result);
            if (result == null) {
                logger.info("ip不能用" + ip);
            }
        }

        result(true, "ip校验成功", null);

    }

    public static void main(String[] arg) {
        // getIp("175.154.204.72");

//        String url = "http://154.89.8.27:8080/api/pay.jsp";
//        String[] temp = url.split("pay.jsp");
//        System.out.println(temp[0]);

        // ipCheck("183.105.154.12", 28803);

//        String ip = "118.178.181.27";
//        String url = "http://api.ip138.com/query/?ip=" + ip;
//        String token = "ec97f055cc74ff0cdde05c35bb6b9fcc";
//        String result = HttpClientFactory.getIp(url, token);
//        JSONObject jso = JSONObject.parseObject(result);
//        String proStr = jso.getString("data").split(",")[1].replaceAll("\"", "");
//        System.out.println(proStr);


    }

}
