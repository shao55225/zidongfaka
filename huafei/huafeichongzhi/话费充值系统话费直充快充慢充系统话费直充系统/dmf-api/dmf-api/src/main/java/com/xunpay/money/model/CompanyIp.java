package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

@Table(name = "company_ip")
public class CompanyIp extends Model<CompanyIp>{
    public static final CompanyIp dao=new CompanyIp();

    public Integer getId() {
        return (Integer) get("id");
    }

    public void setId(Integer id) {
        set("id", id);
    }

    public String getIp() {
        return (String) get("ip");
    }

    public void setIp(String ip) {
        set("ip", ip);
    }


    public Integer getPort() {
        return (Integer) get("port");
    }

    public void setPort(Integer port) {
        set("port", port);
    }

    public String getIsU() {
        return (String) get("isU");
    }

    public void setIsU(String isU) {
        set("isU", isU);
    }



}
