package com.xunpay.money.model;

import com.jfinal.plugin.activerecord.Model;
import com.xunpay.money.model.annotation.Table;

@Table(name = "cmcc_ck")
public class CmccCk extends Model<CmccCk> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final CmccCk dao=new CmccCk();

    public Integer getId() {
        return (Integer) get("id");
    }

    public void setId(Integer id) {
        set("id", id);
    }

    /**
     * 手机号
     * */
   public String getPhone() {
        return (String) get("phone");
    }

    public void setPhone(String phone) {
        set("phone", phone);
    }

    /**
     * session
     * */
    public String getSessionId() {
        return (String) get("sessionId");
    }

    public void setSessionId(String sessionId) {
        set("sessionId", sessionId);
    }
    
    /**
     * token
     * */
    public String getTokenId() {
    	return (String) get("tokenId");
    }
    
    public void setTokenId(String tokenId) {
    	set("tokenId", tokenId);
    }
    
    /**
     * proxyIp
     * */
    public String getProxyIp() {
    	return (String) get("proxyIp");
    }
    
    public void setProxyIp(String proxyIp) {
    	set("proxyIp", proxyIp);
    }
    
    /**
     * proxyPort
     * */
    public String getProxyPort() {
    	return (String) get("proxyPort");
    }
    
    public void setProxyPort(String proxyPort) {
    	set("proxyPort", proxyPort);
    }
    
    /**
     * isU
     * */
    public String getIsU() {
    	return (String) get("isU");
    }
    
    public void setIsU(String isU) {
    	set("isU", isU);
    }
    
  
}
