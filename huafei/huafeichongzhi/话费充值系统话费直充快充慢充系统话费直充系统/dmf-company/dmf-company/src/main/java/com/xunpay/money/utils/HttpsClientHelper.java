package com.xunpay.money.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

public class HttpsClientHelper {

	public static String sendGet(String url) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		
		String token=random();
		
	    //getMethod.addRequestHeader("accesstoken", token);
		
		//String cookie="api_uid=CiFspF5Ga7dhoABBzTi7Ag==; _nano_fp=XpdJXpE8XqXynpTJno_OiYKBi08PnxhxCqlRd9Se; pdd_user_id=2527803663379; pdd_user_uin=HSXFOY4HGU72TOEYCJIFEGFTYE_GEXDA; PDDAccessToken=LRQRCENHEYKHSCFITDPYPPKYSIDPFAFDUFXNXGURKVJASJQLD2NQ1101396;";
	    //	String cookie="api_uid=CiFspF5Ga7dhoABBzTi7Ag==; _nano_fp=XpdJXpE8XqXynpTJno_OiYKBi08PnxhxCqlRd9Se; msec=1800000; rec_list_order_detail=rec_list_order_detail_L788SL; pdd_user_uin=HSXFOY4HGU72TOEYCJIFEGFTYE_GEXDA; pdd_user_id=2527803663379; PDDAccessToken=FZUHBTOROAZFDRFQHM3DGTHONE7ASFP5CRA25IM76ZDNCGRGHEFQ1101396; ua=Mozilla%2F5.0%20(iPhone%3B%20CPU%20iPhone%20OS%2011_0%20like%20Mac%20OS%20X)%20AppleWebKit%2F604.1.38%20(KHTML%2C%20like%20Gecko)%20Version%2F11.0%20Mobile%2F15A372%20Safari%2F604.1; webp=1";

		//String cookie="api_uid=CiSVuV5JVZBrhQA+mDY8Ag==; ua=Mozilla%2F5.0%20(iPhone%3B%20CPU%20iPhone%20OS%2011_0%20like%20Mac%20OS%20X)%20AppleWebKit%2F604.1.38%20(KHTML%2C%20like%20Gecko)%20Version%2F11.0%20Mobile%2F15A372%20Safari%2F604.1; _nano_fp=XpdJXpCjn0PJn5XJXC_XLaSWkvg68cC3v6ua42Es; webp=1; pdd_user_id=2527803663379; pdd_user_uin=HSXFOY4HGU72TOEYCJIFEGFTYE_GEXDA; PDDAccessToken=A6ZTRZOMKTL5FIVAX7A5266OUH3ICUDJZ25LHC7GUX5NWC3FINWA1101396";
		//getMethod.addRequestHeader("cookie",cookie);
		//String cookie="api_uid=CiGtkF5LYtwU5AA5M3d4Ag==; ua=Mozilla%2F5.0%20(Windows%20NT%206.1%3B%20Win64%3B%20x64)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F79.0.3945.88%20Safari%2F537.36; _nano_fp=XpdJXpUbl0mjXqTono_ADd7PtHiWTYbnGDCCsg6e; webp=1; pdd_user_id=8554356252137; pdd_user_uin=FQU4KB3RA5HJ2LGVQV5RHHEJOU_GEXDA; PDDAccessToken=LEUJOROMTKJRT4MCKOQYOYPDHJTG2FH2UWIWSSF3FGO276JLZQIA111e9ab";
	
		getMethod.addRequestHeader("cookie","PDDAccessToken="+token+"");
		
		String result = null;
		try {
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(getMethod);
			if (status != 200) {
				return null;
			}
			result = getMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return result;
	}
	
	public static String sendPost(String url, Map<String, String> params) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		PostMethod postMethod = new PostMethod(url);
		String charset = "utf-8";
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		if (MapUtils.isNotEmpty(params)) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				postMethod.addParameter(key, params.get(key));
			}
		}
		String result = null;
		try {
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(postMethod);
			if (status != 200) {
				return null;
			}
			result = postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return result;
	}
	public static String   random() {
		
		String [] randmoArray= {"CYYONMFGKAVA7VF5ZA6BLJE4SEN6VTEPLK464DZNVHQCXWDPBJTQ111dd90",
								"FPXG247VSCWYUM4JLL4MVLLKZRTOMRMGCFDI2PVDEK7TNIUPI53Q11191ae",
								"LRQRCENHEYKHSCFITDPYPPKYSIDPFAFDUFXNXGURKVJASJQLD2NQ1101396",
								"W2UNMH4FWLUGTM6GE64T6EGAT62MZNRMELE5OX6AKBQ745DSQJRQ112e5a1",
								"UVODVY6HKNCVYRTHHSMN2FCZN3YYMKZUVSZO2PS6BT2FIQ6HCZ7Q1035b5e",
								"FPXG247VSCWYUM4JLL4MVLLKZRTOMRMGCFDI2PVDEK7TNIUPI53Q11191ae",
								"CYYONMFGKAVA7VF5ZA6BLJE4SEN6VTEPLK464DZNVHQCXWDPBJTQ111dd90",
								"XS76BBCEPOWPFB27XIMNW2MCREMYQX3HQZGJGTT2ZZZ42FG6RIIA1122e01",
								"XZYOLFXYWIJDMTEWMUEUTCRYPETVHFAIXVLFIODRPJXW5OHNBJJA1109a1a",
								"H2O5I33GLYPKCQB7AQ3TMHNWVXVTXAD37MV3I7ZFM3YNLBIFLRKA11156b0"};
		
		Random random=new Random();
		
		int  number=random.nextInt(10);	
		
		return randmoArray[number];
		
		
	}
	
}
