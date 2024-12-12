package com.xunpay.money.utils;

import com.jfinal.plugin.activerecord.Db;

public class ProvinceIp {

    //湖南
   // public static final String hunan="175.6.244.14:28803";
	
    public static  String hunan=Db.queryStr(" select yzm from phone_yzm  where phone='湖南' ");
    
    //湖北
    public static  String hubei=Db.queryStr(" select yzm from phone_yzm  where phone='湖北' ");
    
    //甘肃
    public static  String ganshu=Db.queryStr(" select yzm from phone_yzm  where phone='甘肃' ");
    
    //陕西
    public static  String shanxi=Db.queryStr(" select yzm from phone_yzm  where phone='陕西' ");

    //江苏
    public static  String jiangsu=Db.queryStr(" select yzm from phone_yzm  where phone='江苏' ");
    
    //云南
    public static  String yunnan=Db.queryStr(" select yzm from phone_yzm  where phone='云南' ");
    
    //广东
    public static  String guangdong=Db.queryStr(" select yzm from phone_yzm  where phone='广东' ");
    
    //上海
    public static  String shanghai=Db.queryStr(" select yzm from phone_yzm  where phone='上海' ");
    
    //浙江
    public static  String zhejiang=Db.queryStr(" select yzm from phone_yzm  where phone='浙江' ");

    //贵州
    public static  String guizhou=Db.queryStr(" select yzm from phone_yzm  where phone='贵州' ");

    //内蒙古
    public static  String neimenggu=Db.queryStr(" select yzm from phone_yzm  where phone='内蒙古' ");

    //宁夏
    public static  String ningxia=Db.queryStr(" select yzm from phone_yzm  where phone='宁夏' ");

    //四川
    public static  String sichuan=Db.queryStr(" select yzm from phone_yzm  where phone='四川' ");

    //吉林
    public static  String jilin=Db.queryStr(" select yzm from phone_yzm  where phone='吉林' ");

    //福建
    public static  String fujian=Db.queryStr(" select yzm from phone_yzm  where phone='福建' ");

    //新疆维吾尔自治区
    public static  String xinjiang=Db.queryStr(" select yzm from phone_yzm  where phone='新疆' ");

    //河北
    public static  String hebei=Db.queryStr(" select yzm from phone_yzm  where phone='河北' ");

    //青海
    public static  String qinghai=Db.queryStr(" select yzm from phone_yzm  where phone='青海' ");

    //河南
    public static  String henan=Db.queryStr(" select yzm from phone_yzm  where phone='河南' ");

    //山西
    public static  String shanxiS=Db.queryStr(" select yzm from phone_yzm  where phone='山西' ");

    //北京
    public static  String beijing=Db.queryStr(" select yzm from phone_yzm  where phone='北京' ");

    //广西
    public static  String guangxi=Db.queryStr(" select yzm from phone_yzm  where phone='广西' ");

    //重庆
    public static  String chongqing=Db.queryStr(" select yzm from phone_yzm  where phone='重庆' ");

    //海南
    public static  String hainan=Db.queryStr(" select yzm from phone_yzm  where phone='海南' ");

    //天津
    public static  String tianjin=Db.queryStr(" select yzm from phone_yzm  where phone='天津' ");

    //安徽
    public static  String anhui=Db.queryStr(" select yzm from phone_yzm  where phone='安徽' ");

    //辽宁
    public static  String liaoning=Db.queryStr(" select yzm from phone_yzm  where phone='辽宁' ");

    //山东
    public static  String shandong=Db.queryStr(" select yzm from phone_yzm  where phone='山东' ");

    //江西
    public static  String jiangxi=Db.queryStr(" select yzm from phone_yzm  where phone='江西' ");

    //黑龙江
    public static  String heilongjiang=Db.queryStr(" select yzm from phone_yzm  where phone='黑龙江' ");

    
    

}
