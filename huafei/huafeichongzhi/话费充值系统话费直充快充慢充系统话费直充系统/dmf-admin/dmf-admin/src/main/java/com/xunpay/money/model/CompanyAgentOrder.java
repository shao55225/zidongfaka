package com.xunpay.money.model;


import com.jfinal.plugin.activerecord.Model;
import com.xunpay.money.model.annotation.Table;

import java.math.BigDecimal;
import java.util.Date;

@Table(name="company_agent_order")
public class CompanyAgentOrder extends Model<CompanyAgentOrder> {
    public static final CompanyAgentOrder dao=new CompanyAgentOrder();

    public Integer getId(){
        return (Integer)get("id");
    }
    public void setId(Integer id){
        set("id",id);
    }

    /**
     * 商户编号
     * */
    public String getCustomerNo(){

        return (String)get("customer_no");
    }
    public void setCustomerNo(String customerNo){
        set("customer_no",customerNo);
    }

    /**
     * 商户订单号
     * */
    public String getOutTradeNo(){
        return (String)get("outTrade_no");
    }
    public void setOutTradeNo(String outTradeNo){
        set("outTrade_no",outTradeNo);
    }

    /**
     * 代充值号码（0-手机号码/1-加油卡账号）
     * */
    public String getRechargeNo(){
        return (String)get("recharge_no");
    }
    public void setRechargeNo(String rechargeNo){
        set("recharge_no",rechargeNo);
    }


    /**
     * 产品类型(MOBILE 移动，UNICOM 联通,TELECOM 电信,ZSH中石化)
     * */
    public String getProductType(){
        return (String)get("product_type");
    }
    public void setProductType(String productType){
        set("product_type",productType);
    }

    /**
     * 充值卡序号（密文）
     * */
    public String getCardSn(){
        return (String)get("card_sn");
    }
    public void setCardSn(String cardSn){
        set("card_sn",cardSn);
    }

    /**
     * 充值卡密码（密文）
     * */
    public String getCardPwd(){
        return (String)get("card_pwd");
    }
    public void setCardPwd(String cardPwd){
       set("card_pwd",cardPwd);
    }

    /**
     * 充值金额（单位：元，整数）
     * */
    public BigDecimal getAmount(){
        return (BigDecimal)get("amount");
    }
    public void setAmount(BigDecimal amount){
        set("amount",amount);
    }

    /**
     * 回调地址
     * */
    public String getNotifyUrl(){
        return (String)get("notify_url");
    }
    public void setNotifyUrl(String notifyUrl){
        set("notify_url",notifyUrl);
    }

    /**
     * 充值状态(DEALING=充值中、FAILED=充值失败、SUCCESS=充值成功)
     * */
    public String getStatus(){
        return (String)get("status");
    }
    public void setStatus(String status){
        set("status",status);
    }


    /**
     * 失败错误码
     * */
    public String getFailedCode(){
        return (String)get("failed_code");
    }
    public void setFailedCode(String failedCode){
        set("failed_code",failedCode);
    }


    /**
     * 创建时间
     * */
    public Date getCreateTime(){
        return (Date)get("create_time");
    }
    public void setCreateTime(Date createTime){
        set("create_time",createTime);
    }


    /**
     * 充值时间
     * */
    public Date getTopTime(){
        return (Date)get("top_time");
    }
    public void setTopTime(Date topTime){
        set("top_time",topTime);
    }

    /**
     * 订单号
     * */
    public String getOrderNo(){
        return (String)get("order_no");
    }
    public void setOrderNo(String orderNo){
        set("order_no",orderNo);
    }


    /**
     *实际金额
     * */
    public BigDecimal getActualAmount(){
        return (BigDecimal)get("actual_amount");
    }
    public void setActualAmount(BigDecimal actualAmount){
        set("actual_amount",actualAmount);
    }


    /**
     * 充值订单号
     * */
    public String getCredentials(){
        return (String)get("credentials");
    }
    public void setCredentials(String credentials){
        set("credentials",credentials);
    }

    /**
     * 备注
     * */
    public String getRemark(){
        return (String)get("remark");
    }
    public void setRemark(String remark){
        set("remark",remark);
    }

    /**
     * 手续费
     * */
    public BigDecimal getFeeAmount(){
        return (BigDecimal)get("feeAmount");
    }
    public void setFeeAmount(BigDecimal feeAmount){
        set("fee_amount",feeAmount);
    }

    /**
     * 通知结果
     * */
    public Integer getNotifyStatu(){
        return (Integer)get("notify_statu");
    }
    public void setNotifyStatu(Integer notifyStatu){
        set("notify_statu",notifyStatu);
    }








}
