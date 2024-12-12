package com.xunpay.money.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;
import com.xunpay.money.model.annotation.Table;

@Table(name = "coke")
public class Coke extends Model<Coke> {

    public static final Coke dao=new Coke();

    public Integer getId() {
        return (Integer) get("id");
    }

    public void setId(Integer id) {
        set("id", id);
    }


    public String getCoke() {
        return (String) get("coke");
    }

    public void setCoke(String coke) {
        set("coke", coke);
    }
    /**
     * 时间
     * **/
    public Date getAddTime() {
    	return (Date) get("addtime");
    }
    
    public void setAddTime(Date addtime) {
    	set("addtime", addtime);
    }






}
