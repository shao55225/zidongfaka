package com.xunpay.money.model;

import com.jfinal.plugin.activerecord.Model;
import com.xunpay.money.model.annotation.Table;

@Table(name = "phone_delete")
public class PhoneDelete extends Model<PhoneDelete> {

    public static final PhoneDelete dao=new PhoneDelete();

    public String getId() {
        return (String) get("id");
    }

    public void setId(String id) {
        set("id", id);
    }
}
