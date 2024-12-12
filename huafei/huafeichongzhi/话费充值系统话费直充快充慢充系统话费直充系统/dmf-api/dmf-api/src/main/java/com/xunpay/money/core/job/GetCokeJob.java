package com.xunpay.money.core.job;

import com.xunpay.money.model.SysConfig;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xunpay.money.model.CmccCk;
import com.xunpay.money.utils.CmccHttp;

public class GetCokeJob extends BaseJob {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        SysConfig sysconfig = (SysConfig) SysConfig.dao.findFirst(" select * from sys_config  where code='cardS' ");
        String cardS = sysconfig.getContent();

        System.out.println("进入获取coke任务-------------");

        CmccCk cmcc = new CmccCk();

        String reslut = CmccHttp.sendGet("http://47.115.31.216/sms/api/sms?merchantId=" + cardS);
        if (reslut != null) {

            if (reslut.indexOf("成功") != -1) {

                JSONObject obj = JSONObject.parseObject(reslut);
                JSONObject dataObj = obj.getObject("data", JSONObject.class);
                String phone = dataObj.getString("phone");
                String proxyIp = dataObj.getString("proxyIp");
                String proxyPort = dataObj.getString("proxyPort");
                String sessionId = null;
                String tokenId = null;

                JSONArray jsonArray = JSONArray.parseArray(dataObj.getString("cookies"));
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = JSONObject.parseObject(jsonArray.get(i).toString());
                    if (jsonObject.getString("name").equals("jsessionid-cmcc")) {
                        String value = jsonObject.getString("value");
                        sessionId = value;
                    }
                    if (jsonObject.getString("name").equals("cmccssotoken")) {
                        String value = jsonObject.getString("value");
                        tokenId = value;
                    }
                }
                cmcc.setPhone(phone);
                cmcc.setProxyIp(proxyIp);
                cmcc.setProxyPort(proxyPort);
                cmcc.setSessionId(sessionId);
                cmcc.setTokenId(tokenId);
                cmcc.save();
                System.out.println("sessionId=" + sessionId + "-----" + "tokenId=" + tokenId);
            } else {

                System.out.println("获取验证码异常-------------------------->");

            }

        }
    }

    public static void main(String[] args) {

        // String result1="{\"code\":0,\"msg\":\"成功\",\"data\":{\"cookies\":[{\"name\":\"jsessionid-cmcc\",\"value\":\"n2047EFDDAA607CCE0605CEF2EAE23E60-1\",\"domain\":\".10086.cn\",\"path\":\"/\",\"expires\":\"-1\"},{\"name\":\"c\",\"value\":\"284440bc15104bbeb560f57228b518f9\",\"domain\":\".10086.cn\",\"path\":\"/\",\"expires\":\"-1\"},{\"name\":\"iPCrGkRfd0XkP\",\"value\":\"5UunwWTZZvYlqqqm0rW31wGHIPvG3_XXL1DIcyNLmQxVcq5EVKVgu_NoXClsxJ3xgyUbEHvwm5EHUHp9eIS4qnylCN1zwEt0UW4ioys95DMFVDQ21_bCVEqe0_jUQPaNdZF5PB6OGZEyEQCN4xHKnl1\",\"domain\":\"touch.10086.cn\",\"path\":\"/\",\"expires\":\"1598412754\"},{\"name\":\"mobile\",\"value\":\"51048-62992-6253-30731\",\"domain\":\".touch.10086.cn\",\"path\":\"/\",\"expires\":\"1629342671\"},{\"name\":\"cmccssotoken\",\"value\":\"284440bc15104bbeb560f57228b518f9@.10086.cn\",\"domain\":\".10086.cn\",\"path\":\"/\",\"expires\":\"-1\"},{\"name\":\"is_login\",\"value\":\"true\",\"domain\":\".10086.cn\",\"path\":\"/\",\"expires\":\"-1\"},{\"name\":\"iPCrGkRfd0XkO\",\"value\":\"50rFtlj225YogEyCg0ku7Nd0Pc5dED4wOw_FwheRB7EG_0671Nv32CT1z3t1Daq4.p_4uxBej57bg8H8EbL48za\",\"domain\":\"touch.10086.cn\",\"path\":\"/\",\"expires\":\"1913133484.433803\"},{\"name\":\"WT_FPC\",\"value\":\"id=2f449f2cb854abc359d1597773514920:lv=1597806671291:ss=1597806671291\",\"domain\":\".10086.cn\",\"path\":\"/\",\"expires\":\"1660920522\"},{\"name\":\"sendflag\",\"value\":\"20200819015814666112\",\"domain\":\".10086.cn\",\"path\":\"/\",\"expires\":\"-1\"},{\"name\":\"channel\",\"value\":\"0705\",\"domain\":\"touch.10086.cn\",\"path\":\"/i/\",\"expires\":\"-1\"},{\"name\":\"ssologinprovince\",\"value\":\"280\",\"domain\":\"touch.10086.cn\",\"path\":\"/i\",\"expires\":\"-1\"},{\"name\":\"login\",\"value\":\"true\",\"domain\":\"touch.10086.cn\",\"path\":\"/i/mobile\",\"expires\":\"-1\"}],\"phone\":\"18780768610\",\"tradeNO\":\"0f2d49c6-04e8-48f9-871a-edd73d1acec4-729bc7ba-5627-462d-847d-0b2b69379f57\",\"proxyIp\":\"47.242.32.181\",\"proxyPort\":36898}}";

        System.out.println("进入获取coke任务-------------");
        String reslut = CmccHttp.sendGet("http://47.115.31.216/sms/api/sms?merchantId=f006b706-e1d2-11ea-8ad1-00163e0815ca");

        if (reslut != null) {

            if (reslut.indexOf("成功") != -1) {

                JSONObject obj = JSONObject.parseObject(reslut);
                JSONObject dataObj = obj.getObject("data", JSONObject.class);
                String phone = dataObj.getString("phone");
                String proxyIp = dataObj.getString("proxyIp");
                String proxyPort = dataObj.getString("proxyPort");
                String sessionId = null;
                String tokenId = null;

                JSONArray jsonArray = JSONArray.parseArray(dataObj.getString("cookies"));
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = JSONObject.parseObject(jsonArray.get(i).toString());
                    if (jsonObject.getString("name").equals("jsessionid-cmcc")) {
                        String value = jsonObject.getString("value");
                        sessionId = value;
                    }
                    if (jsonObject.getString("name").equals("cmccssotoken")) {
                        String value = jsonObject.getString("value");
                        tokenId = value;
                    }

                }
                System.out.println("sessionId=" + sessionId + "-----" + "tokenId=" + tokenId);
            } else {

                System.out.println("获取验证码异常-------------------------->");

            }


        }

    }

}
