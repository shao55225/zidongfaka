package com.xunpay.money.core.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xunpay.money.model.CompanyIp;
import com.xunpay.money.model.SysConfig;
import com.xunpay.money.utils.HttpClientHelper;

public class GetIpPortJob extends BaseJob {

    private static Logger logger=Logger.getLogger(GetIpPortJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("进入获取ip------------");

        SysConfig sysconfig=SysConfig.dao.findFirst(" select * from sys_config  where code='ipProxy' ");
        String url=sysconfig.getContent();
        if(url!=null && !"".equals(url)) {
            String s = HttpClientHelper.sendGet(url);
            String []  ips=s.split("T");
            System.out.println("获取ip长度:"+ips.length);
            for (int i = 0; i < ips.length; i++) {
                String ip=ips[i].split(":")[0];
                String port=ips[i].split(":")[1];
                CompanyIp companyIp=new CompanyIp();

                companyIp.setIp(ip);
                companyIp.setPort(Integer.valueOf(port));
                companyIp.save();

            }
        }
        else {
            logger.info("代理ip异常");
        }
    }
}
