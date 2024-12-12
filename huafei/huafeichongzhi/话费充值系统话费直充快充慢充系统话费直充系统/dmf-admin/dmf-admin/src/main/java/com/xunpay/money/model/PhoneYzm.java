package com.xunpay.money.model;

import com.jfinal.plugin.activerecord.Model;
import com.xunpay.money.model.annotation.Table;

import java.util.Date;

@Table(name="phone_yzm")
public class PhoneYzm extends Model<PhoneYzm> {
    public static final PhoneYzm dao=new PhoneYzm();

    public Integer getId() {
        return (Integer) get("id");
    }

    public void setId(Integer id) {
        set("id", id);
    }


    public String getPhone() {
        return (String) get("phone");
    }

    public void setPhone(String phone) {
        set("phone", phone);
    }

    public String getYzm() {
        return (String) get("yzm");
    }

    public void setYzm(String yzm) {
        set("yzm", yzm);
    }

    public Date getAddTime() {
        return (Date) get("addTime");
    }

    public void setAddTime(Date addTime) {
        set("addTime", addTime);
    }

}
