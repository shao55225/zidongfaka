package com.xunpay.money.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.hash.Hashing;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.AlipayAgentBill;
import com.xunpay.money.model.Coke;
import com.xunpay.money.model.CompanyApiInfo;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyBill;
import com.xunpay.money.model.CompanyChannel;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.model.CompanyOrderSettle;
import com.xunpay.money.model.CompanySettleItem;
import com.xunpay.money.utils.Constant;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientFactory;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.HttpsClientHelper;
import com.xunpay.money.utils.ShiroUtils;

@RequiresPermissions("订单中心")
public class OrderController extends BaseController {

    @RequiresPermissions("order")
    public void listOrder() {
        String pay_type = getPara("pay_type");
        String trade_no = getPara("trade_no");

        String select = "select *";
        String except = "from company_order where is_del = 'N' ";
        List<Object> args = new ArrayList<Object>();
        except += getParaSqlLikeWithOr(args, "orderNo", "order_no", "out_order_no", "trade_no", "alipay_name");
        except += getParaSql(args, "status", "status");
        except += getParaSql(args, "notice", "notice");
        except += getParaSql(args, "company_id", "company_id");
        except += getParaSql(args, "trade_no", "trade_no");

        //按照通道类型查询
        except += getParaSql(args, "pay_type", "pay_type");

        String startTime = getPara("startTime");

        String endTime = getPara("endTime");

        if (startTime != null && endTime != null) {

            except += getParaDateCompare(startTime, endTime, "addtime");
        }
        except += " order by id desc";
        keepPara();
        String companyId = getPara("company_id");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -7);
        setAttr("hourList", Db.find("select date_format(addtime, '%H 点') as h, sum(order_money) as m from company_order where status = '已支付' and addtime > '" + DateUtils.get(c.getTime(), "yyyy-MM-dd HH") + "' group by date_format(addtime, '%H')"));
        setAttr("page", CompanyOrder.dao.paginate(getParaToInt("page", 1), 20, select, except, args.toArray()));
        setAttr("companys", CompanyInfo.dao.find("select * from company_info where is_del = 'N'"));
        setAttr("startTime", startTime);
        setAttr("endTime", endTime);
        setAttr("companyId", companyId);
        setAttr("payType", pay_type);

        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String currentDay = format.format(date);

        String startDate = currentDay + " 00:00:00";

        String endDate = currentDay + " 23:59:59";

        //System.out.println(ShiroUtils.getUser().getId());

        //默认统计当天所有商户的流水金额

        BigDecimal orderMoney = null;
        //当查询条件不存在的时候
        if ((args.size() == 0 || args.isEmpty()) && (startTime == null && endTime == null)) {

            String sql = "select sum(order_money)  from company_order where  addtime>='" + startDate + "' and  addtime<='" + endDate + "' and status='已支付' ";

            orderMoney = Db.queryBigDecimal(sql);

            setAttr("orderMoney", orderMoney == null ? 0 : orderMoney.intValue());
            setAttr("day", "当天");

        } else {

            StringBuffer sb = new StringBuffer();

            String sql = "select sum(order_money)   from company_order where is_del = 'N' and status='已支付'  ";

            sb.append(sql);

            //String status=getPara("status");

            //String notice= getPara("notice");

            String company_id = getPara("company_id");

            String startTime1 = getPara("startTime");

            String endTime1 = getPara("endTime");

            String pay_type1 = getPara("pay_type");


            if (company_id != null && !"".equals(company_id)) {

                sb.append(" and company_id='" + company_id + "' ");

            }

            if (startTime1 != null && endTime1 != null) {

                String str = getParaDateCompare(startTime1, endTime1, "addtime");

                sb.append(str);
            }


            if (pay_type1 != null && !"".equals(pay_type1)) {

                sb.append(" and pay_type='" + pay_type1 + "' ");

            }


            orderMoney = Db.queryBigDecimal(sb.toString());

            setAttr("orderMoney", orderMoney == null ? BigDecimal.ZERO : orderMoney.intValue());
            setAttr("day", "查询");
        }


    }


    @RequiresPermissions("order")
    public void listApiOrder() {
        String pay_type = getPara("pay_type");
        String trade_no = getPara("trade_no");

        String select = "select *";
        String except = "from company_apiorder where is_del = 'N' ";
        List<Object> args = new ArrayList<Object>();
        except += getParaSqlLikeWithOr(args, "orderNo", "order_no", "out_order_no", "trade_no");
        except += getParaSql(args, "status", "status");
        except += getParaSql(args, "notice", "notice");
        except += getParaSql(args, "company_id", "company_id");
        except += getParaSql(args, "trade_no", "trade_no");
        except += getParaSql(args, "appid", "appid");

        //按照通道类型查询
        except += getParaSql(args, "pay_type", "pay_type");

        String startTime = getPara("startTime");

        String endTime = getPara("endTime");

        if (startTime != null && endTime != null) {

            except += getParaDateCompare(startTime, endTime, "addtime");
        }
        except += " order by id desc";
        keepPara();
        String companyId = getPara("company_id");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -7);
        setAttr("hourList", Db.find("select date_format(addtime, '%H 点') as h, sum(order_money) as m from company_apiorder where status = '已支付' and addtime > '" + DateUtils.get(c.getTime(), "yyyy-MM-dd HH") + "' group by date_format(addtime, '%H')"));
        setAttr("page", CompanyOrder.dao.paginate(getParaToInt("page", 1), 20, select, except, args.toArray()));
        setAttr("companys", CompanyInfo.dao.find("select * from company_info where is_del = 'N'"));
        setAttr("startTime", startTime);
        setAttr("endTime", endTime);
        setAttr("companyId", companyId);
        setAttr("payType", pay_type);

        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String currentDay = format.format(date);

        String startDate = currentDay + " 00:00:00";

        String endDate = currentDay + " 23:59:59";

        //System.out.println(ShiroUtils.getUser().getId());

        //默认统计当天所有商户的流水金额

        BigDecimal orderMoney = null;
        //当查询条件不存在的时候
        if ((args.size() == 0 || args.isEmpty()) && (startTime == null && endTime == null)) {

            String sql = "select sum(order_money)  from company_apiorder where  addtime>='" + startDate + "' and  addtime<='" + endDate + "' and status='已支付' ";

            orderMoney = Db.queryBigDecimal(sql);

            setAttr("orderMoney", orderMoney == null ? 0 : orderMoney.intValue());
            setAttr("day", "当天");

        } else {

            StringBuffer sb = new StringBuffer();

            String sql = "select sum(order_money)   from company_apiorder where is_del = 'N' and status='已支付'  ";

            sb.append(sql);

            //String status=getPara("status");

            //String notice= getPara("notice");

            String company_id = getPara("company_id");

            String startTime1 = getPara("startTime");

            String endTime1 = getPara("endTime");

            String pay_type1 = getPara("pay_type");


            if (company_id != null && !"".equals(company_id)) {

                sb.append(" and company_id='" + company_id + "' ");

            }

            if (startTime1 != null && endTime1 != null) {

                String str = getParaDateCompare(startTime1, endTime1, "addtime");

                sb.append(str);
            }


            if (pay_type1 != null && !"".equals(pay_type1)) {

                sb.append(" and pay_type='" + pay_type1 + "' ");

            }


            orderMoney = Db.queryBigDecimal(sb.toString());

            setAttr("orderMoney", orderMoney == null ? BigDecimal.ZERO : orderMoney.intValue());
            setAttr("day", "查询");
        }


    }

    @RequiresPermissions("order:R")
    public void totalRate() {
        String sql = "select " +
                " concat(ifnull(round(sum(case when `status` = '已支付' and date_sub(now(), interval 10 minute) <= addtime then 1 else 0 end)/sum(case when date_sub(now(), interval 10 minute) <= addtime then 1 else 0 end)*100, 2), '0.00'),'%') as min10_rate," +
                " concat(ifnull(round(sum(case when `status` = '已支付' and date_sub(now(), interval 30 minute) <= addtime then 1 else 0 end)/sum(case when date_sub(now(), interval 30 minute) <= addtime then 1 else 0 end)*100, 2), '0.00'),'%') as min30_rate," +
                " concat(ifnull(round(sum(case when `status` = '已支付' and date_sub(now(), interval 24 hour) <= addtime then 1 else 0 end)/sum(case when date_sub(now(), interval 24 hour) <= addtime then 1 else 0 end)*100, 2), '0.00'),'%') as hour24_rate, " +
                " concat(ifnull(round(sum(case when `status` = '已支付' then 1 else 0 end)/count(1)*100, 2), '0.00'),'%') as all_rate " +
                " from company_order  where  out_order_no is not null ";
        Record r = Db.findFirst(sql);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -7);
        List<Record> hourList = Db.find("select date_format(addtime, '%H 点') as h, sum(order_money) as m from company_order where status = '已支付' and addtime > '" + DateUtils.get(c.getTime(), "yyyy-MM-dd HH") + "' group by date_format(addtime, '%H')");
        String hourStr = "";
        for (Record h : hourList) {
            hourStr += h.getStr("h") + " -&gt; " + h.getBigDecimal("m") + "&nbsp;|&nbsp;";
        }
        r.set("hourList", hourStr);

        r.set("n50", numRate(50));
        r.set("n200", numRate(200));
        r.set("n500", numRate(500));

        renderJson(r);
    }

    private String numRate(int num) {
        String numSql = "select status, count(*) as c from (select * from company_order where  out_order_no is not null  order by id desc limit 0, ?) as t group by status";
        List<Record> rs = Db.find(numSql, 50);
        Long numA = 0L;
        for (Record rec : rs) {
            if ("已支付".equals(rec.getStr("status"))) {
                numA = rec.getLong("c");
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(100 * (double) numA / num) + "%";
    }

    @RequiresPermissions("order:R")
    public void viewOrder() {
        CompanyOrder order = CompanyOrder.dao.findById(getParaToInt());
        List<CompanyOrderSettle> settles = CompanyOrderSettle.dao.find("select * from company_order_settle where order_id = ?", order.getId());
        List<CompanyBill> bills = CompanyBill.dao.find("select * from company_bill where order_id = ?", order.getId());
        List<AlipayAgentBill> agentBills = AlipayAgentBill.dao.find("select * from alipay_agent_bill where order_id = ?", order.getId());
        setAttr("o", order);
        setAttr("settles", settles);
        setAttr("bills", bills);
        setAttr("agentBills", agentBills);
    }

    @RequiresPermissions("order:W")
    public void reloadItem() {
        Integer id = getParaToInt();
        String token = Db.queryStr("select token from company_order where id = ?", id);
        String result = HttpClientHelper.sendGet("http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/system/reloadOrder?id=" + id + "&token=" + token);
        renderText(result);
    }

    @RequiresPermissions("order:W")
    public void settleItem() {
        Integer id = getParaToInt();
        String token = Db.queryStr("select token from company_order where id = ?", id);
        String result = HttpClientHelper.sendGet("http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/system/settleOrder?id=" + id + "&token=" + token);
        renderText(result);
    }

    @RequiresPermissions("order:W")
    public void noticeItem() {

        String treaty = "";

        //订单号
        Integer id = getParaToInt();//notice_url

        String url = Db.queryStr("select notice_url  from company_order where id = ?", id);

        if (url.startsWith("http://")) {

            treaty = "http";
        } else if (url.startsWith("https://")) {

            treaty = "https";
        }

        StringBuffer sb = new StringBuffer();

        sb.append(url);

        CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where id = ?", id);

        String orderNo = order.getOutOrderNo();

        BigDecimal moeny = order.getOrderMoney();


        CompanyInfo company = CompanyInfo.dao.findById(order.getCompanyId());

        //商户key
        String key = company.getMd5key();

        String md5Str = moeny.toString() + orderNo + key;

        String md5 = EncryptUtils.encrypt(md5Str);

        sb.append("?order_no=").append(orderNo);

        sb.append("&amount=").append(moeny);

        sb.append("&sign=").append(md5);

        String result = null;


        if (treaty.equals("http")) {

            result = HttpClientHelper.sendGet(sb.toString());

        } else if (treaty.equals("https")) {

            result = HttpsClientHelper.sendGet(sb.toString());

        }

        System.out.println("手动回调日志====>" + result);

        //sif(result!=null && !result.equals("")) {

        Db.update("update company_order set notice='回调成功'  where  id = ? ", id);

        renderText("success");

        //}

    }


    /*
     *充值方回调成功
     */
    public void noticeApi() {

        String treaty = "";

        //订单号
        Integer id = getParaToInt();//notice_url

        String url = Db.queryStr("select notice_url  from company_apiorder where id = ?", id);

        if (url.startsWith("http://")) {

            treaty = "http";
        } else if (url.startsWith("https://")) {

            treaty = "https";
        }

        StringBuffer sb = new StringBuffer();

        sb.append(url);

        CompanyApiOrder order = CompanyApiOrder.dao.findFirst("select * from company_apiorder where id = ?", id);


        StringBuffer noticeUrl = new StringBuffer();

        noticeUrl.append(order.getNoticeUrl());

        if (noticeUrl.indexOf("?") > 0) {

            noticeUrl.append("&");
        } else {

            noticeUrl.append("?");
        }

        //回调规则
        //20200327001hfapi10
        //str=  apiordersn=20200327001^payType=hfapi^money=10^order^sign=sssssss

        String orderNo = order.getToken();
        //String payType = companyOrder.getPayType();
        BigDecimal moeny = order.getOrderMoney();
        String results = "";
        String msg = "";

        if ("充值成功".equals(order.getStatus())) {
            results = "success";
            msg = "1";
        } else {
            results = "fail";
            msg = "0";
        }


        String phone = order.getAppid();
        String op_no = order.getOutOrderNo();

        CompanyInfo companyInfo = CompanyInfo.dao.findFirst("select * from company_info where id=" + order.getCompanyId() + " ");

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

        System.out.println("回调参数" + finalStr);

        System.out.println(finalStr);

        @SuppressWarnings("unused")
        String result = HttpClientFactory.get(finalStr.toString());
        System.out.println("充值方返回结果为" + result);

        if ("success".equals(result)) {

            Db.update("update company_apiorder set notice='回调成功'  where  id = ? ", id);
        }


        renderText("success");

    }


    /*
     *支付方回调成功
     */
    public void noticeOrder() {

        String treaty = "";

        //订单号
        Integer id = getParaToInt();//notice_url

        String url = Db.queryStr("select notice_url  from company_order where id = ?", id);

        if (url.startsWith("http://")) {

            treaty = "http";
        } else if (url.startsWith("https://")) {

            treaty = "https";
        }

        StringBuffer sb = new StringBuffer();

        sb.append(url);

        CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where id = ?", id);

        String orderNo = order.getOutOrderNo();

        BigDecimal moeny = order.getOrderMoney();


        CompanyInfo company = CompanyInfo.dao.findById(order.getCompanyId());

        //商户key
        String key = company.getMd5key();

        String md5Str = moeny.toString() + orderNo + key;

        String md5 = EncryptUtils.encrypt(md5Str);

        sb.append("?order_no=").append(orderNo);

        sb.append("&amount=").append(moeny);

        sb.append("&sign=").append(md5);

        String result = null;


        if (treaty.equals("http")) {

            result = HttpClientHelper.sendGet(sb.toString());

        } else if (treaty.equals("https")) {

            result = HttpClientFactory.get(sb.toString());

        }

        System.out.println("手动回调日志====>" + result);

        if ("success".equals(result)) {
            Db.update("update company_order set notice='回调成功'  where  id = ? ", id);
        }
        renderText("success");

    }

    /**
     * 补单
     */
    @RequiresPermissions("order:W")
    public void repairPay() {
        Integer id = getParaToInt("id");
        String tradeNo = getPara("tradeNo");
        CompanyOrder order = CompanyOrder.dao.findById(id);
        if (!"未支付".equals(order.getStatus())) {
            message(false, "订单状态错误，无法补单", "/order/viewOrder/" + order.getId());
            return;
        }
        order.setTradeNo(tradeNo);
        order.setStatus("已支付");
        order.setSettle("分账成功");
        order.setRemark(ShiroUtils.getUsername() + "补单");
        order.setPaytime(DateUtils.getYmdHmis(new Date()));
        order.update();

        //HttpClientHelper.sendGet("http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/system/noticeOrder?id=" + id + "&token=" + order.getToken());

        // 添加一条客户账单
        CompanyInfo company = CompanyInfo.dao.findById(order.getCompanyId());
        if (company != null) {
            CompanyBill bill = new CompanyBill();
            bill.setCompanyId(company.getId());
            bill.setCompanyName(company.getUsername());
            bill.setOrderId(order.getId());
            bill.setOrderNo(order.getOrderNo());
            bill.setOrderMoney(order.getOrderMoney());
            bill.setTax(order.getCompanyRebate());
            bill.setTaxMoney(order.getCompanyRebateMoney());
            bill.setInMoney(order.getOrderMoney().subtract(order.getCompanyRebateMoney()));
            bill.setType("订单收款");
            bill.setStatus("已结算");
            bill.setBalance(company.getBalance().add(bill.getInMoney()));
            bill.setRemark("(1)订单分账失败,转入余额");
            bill.save();
            Db.update("update company_info set balance = balance + ? where id = ?", bill.getInMoney(), company.getId());
        }

        // 结算商户得上级代理
        int level = 2;
        while (company != null && company.getPid() != null && company.getPid() > 0) {
            CompanyInfo faterCompany = CompanyInfo.dao.findById(company.getPid());
            if (faterCompany != null) {
                CompanyBill bill = new CompanyBill();
                bill.setCompanyId(faterCompany.getId());
                bill.setCompanyName(faterCompany.getUsername());
                bill.setOrderId(order.getId());
                bill.setOrderNo(order.getOrderNo());
                bill.setOrderMoney(order.getOrderMoney());
                bill.setTax(company.getWechatRebate().subtract(faterCompany.getWechatRebate()));
                bill.setTaxMoney(order.getOrderMoney().multiply(bill.getTax()));
                bill.setInMoney(bill.getTaxMoney());
                bill.setType("代理分红");
                bill.setRemark("(" + level + ")来自您的下线:" + company.getUsername());
                bill.setAddtime(new Date());
                bill.setStatus("已结算");
                bill.setBalance(faterCompany.getBalance().add(bill.getInMoney()));
                bill.save();
                Db.update("update company_info set balance = balance + ? where id = ?", bill.getInMoney(), faterCompany.getId());
                level++;
            }
            company = faterCompany;
        }
        redirect("/order/viewOrder/" + order.getId());
    }

    /**
     * 分账垫付
     */
    @RequiresPermissions("order:W")
    public void platformPay() {
        CompanyOrder order = CompanyOrder.dao.findById(getParaToInt());
        if (!"分账失败".equals(order.getSettle())) {
            message(false, "订单状态错误，无法垫付", "/order/viewOrder/" + order.getId());
            return;
        }
        order.setSettle("分账成功");
        order.setRemark("分账失败，平台垫付");
        order.update();

        // 添加一条客户账单
        CompanyInfo company = CompanyInfo.dao.findById(order.getCompanyId());
        if (company != null) {
            CompanyBill bill = new CompanyBill();
            bill.setCompanyId(company.getId());
            bill.setCompanyName(company.getUsername());
            bill.setOrderId(order.getId());
            bill.setOrderNo(order.getOrderNo());
            bill.setOrderMoney(order.getOrderMoney());
            bill.setTax(order.getCompanyRebate());
            bill.setTaxMoney(order.getCompanyRebateMoney());
            bill.setInMoney(order.getOrderMoney().subtract(order.getCompanyRebateMoney()));
            bill.setType("订单收款");
            bill.setStatus("已结算");
            bill.setBalance(company.getBalance().add(bill.getInMoney()));
            bill.setRemark("(1)订单分账失败,转入余额");
            bill.save();
            Db.update("update company_info set balance = balance + ? where id = ?", bill.getInMoney(), company.getId());
        }

        // 结算商户得上级代理
        int level = 2;
        while (company != null && company.getPid() != null && company.getPid() > 0) {
            CompanyInfo faterCompany = CompanyInfo.dao.findById(company.getPid());
            if (faterCompany != null) {
                CompanyBill bill = new CompanyBill();
                bill.setCompanyId(faterCompany.getId());
                bill.setCompanyName(faterCompany.getUsername());
                bill.setOrderId(order.getId());
                bill.setOrderNo(order.getOrderNo());
                bill.setOrderMoney(order.getOrderMoney());
                bill.setTax(company.getWechatRebate().subtract(faterCompany.getWechatRebate()));
                bill.setTaxMoney(order.getOrderMoney().multiply(bill.getTax()));
                bill.setInMoney(bill.getTaxMoney());
                bill.setType("代理分红");
                bill.setRemark("(" + level + ")来自您的下线:" + company.getUsername());
                bill.setAddtime(new Date());
                bill.setStatus("已结算");
                bill.setBalance(faterCompany.getBalance().add(bill.getInMoney()));
                bill.save();
                Db.update("update company_info set balance = balance + ? where id = ?", bill.getInMoney(), faterCompany.getId());
                level++;
            }
            company = faterCompany;
        }

        redirect("/order/viewOrder/" + order.getId());
    }

    @RequiresPermissions("order:W")
    public void notPay() {
        CompanyOrder order = CompanyOrder.dao.findById(getParaToInt());
        if (!"分账失败".equals(order.getSettle())) {
            message(false, "订单状态错误", "/order/viewOrder/" + order.getId());
            return;
        }
        order.setSettle("分账成功");
        order.setRemark("分账失败，不用垫付");
        order.update();
        redirect("/order/viewOrder/" + order.getId());
    }

    @RequiresPermissions("settle")
    public void listSettle() {
        setAttr("defaultPid", Db.queryStr("select content from sys_config where code = 'default_settle_pid'"));
        setAttr("ss", CompanySettleItem.dao.find("select * from company_settle_item where company_id = 0"));
    }

    @RequiresPermissions("settle:W")
    public void addSettleItem() {
    }

    @RequiresPermissions("settle:W")
    public void editSettleItem() {
        setAttr("s", CompanySettleItem.dao.findFirst("select * from company_settle_item where id = ?", getParaToInt()));
    }

    @RequiresPermissions("settle:W")
    public void saveSettleItem() {
        CompanySettleItem item = getModel(CompanySettleItem.class, "s");
        item.setCompanyId(0);
        item.setAddtime(new Date());
        item.setSettleNum(0);
        item.setTotalMoney(BigDecimal.ZERO);
        item.save();
        message(true, "恭喜，收款支付宝添加成功", "/order/listSettle");
    }

    @RequiresPermissions("settle:W")
    public void updateSettleItem() {
        CompanySettleItem item = getModel(CompanySettleItem.class, "s");
        if (item.getCompanyId() != null) {
            message(true, "修改参数错误", "/order/listSettle");
            return;
        }
        item.setCompanyId(0);
        item.update();
        message(true, "恭喜，收款支付宝添加成功", "/order/listSettle");
    }

    @RequiresPermissions("settle:W")
    public void deleteSettleItem() {
        Db.update("delete from company_settle_item where company_id = 0 and id = ?", getParaToInt());
        message(true, "恭喜，收款支付宝删除成功", "/order/listSettle");
    }

    @RequiresPermissions("settle:W")
    public void upSettleItem() {
        Db.update("update company_settle_item set status = '正常' where company_id = 0 and id = ?", getParaToInt());
        redirect("/order/listSettle");
    }

    @RequiresPermissions("settle:W")
    public void downSettleItem() {
        Db.update("update company_settle_item set status = '禁用' where company_id = 0 and id = ?", getParaToInt());
        redirect("/order/listSettle");
    }

    @RequiresPermissions("settle:R")
    public void createQr() {
        CompanyInfo company = CompanyInfo.dao.findById(1);
        Map<String, String> params = new HashMap<String, String>();
        params.put("appId", company.getAppid());
        params.put("orderNo", "S" + System.currentTimeMillis() + (long) (Math.random() * 10000000L));
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("money", "1.00");
        params.put("sign", EncryptUtils.encrypt(params.get("appId") + "^"
                + params.get("orderNo") + "^"
                + params.get("money") + "^"
                + params.get("timestamp") + "^" + company.getMd5key()));
        String url = "http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/order/create";
        renderJson(HttpClientHelper.sendPost(url, params));
    }

    @RequiresPermissions("settle:R")
    public void queryOrder() {
        String orderNo = getPara("orderNo");
        CompanyInfo company = CompanyInfo.dao.findById(1);
        Map<String, String> params = new HashMap<String, String>();
        params.put("appId", company.getAppid());
        params.put("orderNo", orderNo);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("sign", EncryptUtils.encrypt(params.get("appId") + "^"
                + params.get("orderNo") + "^"
                + params.get("timestamp") + "^" + company.getMd5key()));
        String url = "http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/order/query";
        renderJson(HttpClientHelper.sendPost(url, params));
    }


    public void addYzm() {

    }


    public void saveYzm() {

        Coke coke = getModel(Coke.class, "y");

        coke.setAddTime(new Date());

        coke.save();

        message(true, "恭喜，验证码添加成功", "/order/listYzm");
    }


    public void listYzm() {

        String startTime = getPara("startTime");

        String endTime = getPara("endTime");

        //System.out.println(startTime+"--------------"+endTime);

        String select = "select *";

        StringBuffer stringBuffer = new StringBuffer(" from coke where 1=1");

        if (startTime != null && endTime != null) {
            stringBuffer.append(getParaDateCompare(startTime, endTime, "addTime"));
        }
        setAttr("page", Coke.dao.paginate(getParaToInt("page", 1), 20, select, stringBuffer.toString()));
        setAttr("startTime", startTime);
        setAttr("endTime", endTime);
    }

    /**
     * 补单
     **/
    public void repayOrder() {
        Integer id = getParaToInt();
        CompanyOrder order = CompanyOrder.dao.findById(id);
        //   List<CompanyOrderSettle> settles = CompanyOrderSettle.dao.find("select
        //  	String id=getPara("id");
        // 	CompanyOrder order=	CompanyOrder.dao.findFirst("select * from company_order where id="+id+" ");
        String apiOrderNo = order.getAlipayName();
        CompanyApiOrder apiOrder = CompanyApiOrder.dao.findFirst("select * from company_apiorder where order_no='" + apiOrderNo + "' ");

        //更新两个表的数据 然后依次回调
        Db.update("update company_order set status='已支付' where id=?", id);

        Db.update("update company_apiorder set status='充值成功' where order_no=?", apiOrderNo);

        //回调API
        String s3 = noticePhoneApi(apiOrder);

        System.out.println("联通回调话费API结果为--------------------------->" + s3);

        //回调支付方
        String s2 = noticeExe(order);

        System.out.println("联通回调支付方结果为--------------------------->" + s2);

        if ("success".equals(s3)) {

            Db.update("update company_apiorder set notice='回调成功' where order_no='" + apiOrderNo + "' ");
        }

        if ("success".equals(s2)) {

            Db.update("update company_order set notice='回调成功' where id=" + id);
        }

    }


    /**
     * 手动回调
     **/
    public void Manualcorrection() {
        String order_no = getPara("order_no");
        if (order_no.equals("") || order_no==null){
            message(true, "订单号为空", "/account/password");
            return;
        }
        CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where alipay_name='" + order_no + "' ");
        Integer id = order.getId();
        String apiOrderNo = order.getAlipayName();
        CompanyApiOrder apiOrder = CompanyApiOrder.dao.findFirst("select * from company_apiorder where order_no='" + apiOrderNo + "' ");

        //更新两个表的数据 然后依次回调
        Db.update("update company_order set status='已支付' where id=?", id);

        Db.update("update company_apiorder set status='充值成功' where order_no=?", apiOrderNo);

        //回调API
        String s3 = noticePhoneApi(apiOrder);
        System.out.println("联通回调话费API结果为--------------------------->" + s3);
        if ("success".equals(s3)) {
            Db.update("update company_apiorder set notice='回调成功' where order_no='" + apiOrderNo + "' ");
        }

        //回调支付方
        if (order.getNoticeUrl() != null) {
            String s2 = noticeExe(order);
            System.out.println("联通回调支付方结果为--------------------------->" + s2);
            if ("success".equals(s2)) {
                Db.update("update company_order set notice='回调成功' where id=" + id);
            }
        }
        message(true, "回调成功", "/welcome");

    }


    /**
     * 校验超级管理员密码
     **/
    public void checkPassWord() {

        String passWord = getPara("passWord");
        String dbPassWord = Db.queryStr("select password from  sys_user  where  username='admin' ");

        if (passWord.equals(dbPassWord)) {
            renderText("success");
            return;
        }
        renderText("faile");

    }

    /**
     * 回调四方
     **/
    public String noticeExe(CompanyOrder companyOrder) {

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
        result = HttpClientFactory.get(finalStr.toString());

        return result;
    }

    /**
     * 回调话费Api
     **/
    public String noticePhoneApi(CompanyApiOrder companyApiOrder) {

        StringBuffer noticeUrl = new StringBuffer();
        noticeUrl.append(companyApiOrder.getNoticeUrl());
        if (noticeUrl.indexOf("?") > 0) {

            noticeUrl.append("&");
        } else {

            noticeUrl.append("?");
        }
        String orderNo = companyApiOrder.getToken();
        BigDecimal moeny = companyApiOrder.getOrderMoney();
        String results = "";
        String msg = "";
        results = "success";
        msg = "1";

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
        System.out.println("回调参数" + finalStr);

        @SuppressWarnings("unused")
        String result = HttpClientFactory.get(finalStr.toString());
        return result;
    }

    public static void main(String[] args) {


    }

}
