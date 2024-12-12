package com.xunpay.money.utils.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.alipay.api.internal.util.StringUtils;
import com.xunpay.money.utils.util.http.Http;
import com.xunpay.money.utils.util.security.Base64;
import com.xunpay.money.utils.util.security.MD5;
import com.xunpay.money.utils.util.security.PassWrodsCreater;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

public class TencentCrawler {
	
	private static final String UA="Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36";

	private static Log log = LogFactory.getLog(TencentCrawler.class);
	//private static int START_DISTANCE = (22 + 16) * 2;
	private static String collect_value=null;
	
	private static String eks=null;
	
	private static int tdcUseCount=0;
	
	private static String TDC_HEAD="var document={getElementById:getElementById,addEventListener:addListener,mozHidden:undefined,referrer:\"\",all:false,charset:\"UTF-8\",characterSet:\"UTF-8\",cookie:\"\",documentElement:{clientWidth:1879,clientHeight:956},body:{appendChild:appendChild},createElement:div,location:{href:\"https://captcha.guard.qcloud.com/cap_union_new_show\"}};var navigator={userAgent:\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.26 Safari/537.36 Core/1.63.5478.400 QQBrowser/10.1.1550.400\",platform:\"Win32\",plugins:{length:3,\"Shockwave Flash\":{description:\"Shockwave Flash 27.9 r0\",name:\"Chromium PDF Plugin\"}},languages:[\"zh-CN\",\"zh\"],cookieEnabled:true};var window={RTCPeerConnection:RTCPeerConnection,location:{host:{replace:replace}},host:{replace:replace},postMessage:postMessage,addEventListener:addListener,document:document,event:event,\"Array\":Array,innerWidth:1879,innerHeight:956,navigator:navigator,BJ_REPORT:1,setTimeout:setTimeout};var ms={},sid;var ceOrientationEvent={alpha:null,beta:null,gamma:null};var event={pageX:190,pageY:296};var screen={availHeight:1050,availLeft:0,availTop:0,availWidth:1920,colorDepth:24,height:1080,orientation:ScreenOrientation={angle:0,type:\"landscape-primary\",onchange:null},pixelDepth:24,width:1920};var BJ_REPORT={report:setTimeout};function RTCPeerConnection(a,b){return{localDescription:{sdp:\"v=0 \\n o=- 1066198649781495907 2 IN IP4 127.0.0.1 \\n s=- \\n t=0 0 \\n a=group:BUNDLE data \\n a=msid-semantic: WMS \\n m=application 53086 UDP/TLS/RTP/SAVPF 109 \\n c=IN IP4 192.168.0.101 \\n b=AS:30 \\n a=rtcp:9 IN IP4 0.0.0.0 \\n  a=candidate:3350409123 1 udp 2113937151 192.168.0.101 53086 typ host generation 0 network-cost 50 \\n a=ice-ufrag:4KyV \\n a=ice-pwd:l1ZQY9+C5Zid43ev8pE3u+nG \\n a=ice-options:trickle \\n a=fingerprint:sha-256 3C:96:8E:17:FA:2E:D9:8A:72:B5:9C:6D:99:34:1B:83:E3:E9:12:02:3B:CA:E2:05:BC:36:F9:CD:5F:2C:3E:68 \\n a=setup:actpass \\n a=mid:data \\n a=sendrecv \\n a=rtcp-mux \\n a=rtpmap:109 google-data/90000 \\n \",type:\"offer\"},createOffer:createOffer,createDataChannel:createDataChannel}}function createDataChannel(a){}function createOffer(a,b){this.onicecandidate({candidate:{candidate:\"candidate:3350409123 1 udp 2113937151 192.168.0.101 52489 typ host generation 0 ufrag p0CF network-cost 50\",sdpMLineIndex:0,sdpMid:\"data\"}})}function getElementById(a){if(a==\"slideBkg\"){return{src:sid}}}function replace(a,b){}function appendChild(a){}function getSplice(a){var b=[];for(var i=0;i<a.length-2;i++){b.push([a[i+1][0],a[i+1][1],a[i+1][2]])}return b}function getMouserup(a){var x=0,y=0,t=0;for(var i=0;i<a.length;i++){x+=a[i][0];y+=a[i][1];t+=a[i][2]}return{pageX:x,pageY:y,t:t}}function div(name){if(\"iframe\"==name){return{style:[],addEventListener:addListener,contentWindow:window}}return{setAttribute:setAttribute,style:{display:\"\"},appendChild:appendChild}}function setAttribute(){}function getArray(a){return[a]}function setTimeout(a,b){var begtime=0,endtime=new Date().getTime()+b;while(begtime<endtime){begtime=new Date().getTime()}return a()}function sTimeout(b){var begtime=0,endtime=new Date().getTime()+b;while(begtime<endtime){begtime=new Date().getTime()}}function addListener(a,b,c){ms[a]=b;if(a==\"deviceorientation\"){b(ceOrientationEvent)}if(a==\"load\"){b()}if(a==\"message\"){}}function postMessage(a,b){return 0}\"object\"!=typeof JSON&&(JSON={}),function(){function f(t){return t<10?\"0\"+t:t}function quote(t){return escapable.lastIndex=0,escapable.test(t)?'\"'+t.replace(escapable,function(t){var e=meta[t];return\"string\"==typeof e?e:\"\\\\u\"+(\"0000\"+t.charCodeAt(0).toString(16)).slice(-4)})+'\"':'\"'+t+'\"'}function str(t,e){var n,r,o,i,a,f=gap,u=e[t];switch(u&&\"object\"==typeof u&&\"function\"==typeof u.toJSON&&(u=u.toJSON(t)),\"function\"==typeof rep&&(u=rep.call(e,t,u)),typeof u){case\"string\":return quote(u);case\"number\":return isFinite(u)?String(u):\"null\";case\"boolean\":case\"null\":return String(u);case\"object\":if(!u){return\"null\"}if(gap+=indent,a=[],\"[object Array]\"===Object.prototype.toString.apply(u)){for(i=u.length,n=0;n<i;n+=1){a[n]=str(n,u)||\"null\"}return o=0===a.length?\"[]\":gap?\"[\\n\"+gap+a.join(\",\\n\"+gap)+\"\\n\"+f+\"]\":\"[\"+a.join(\",\")+\"]\",gap=f,o}if(rep&&\"object\"==typeof rep){for(i=rep.length,n=0;n<i;n+=1){\"string\"==typeof rep[n]&&(r=rep[n],o=str(r,u),o&&a.push(quote(r)+(gap?\": \":\":\")+o))}}else{for(r in u){Object.prototype.hasOwnProperty.call(u,r)&&(o=str(r,u),o&&a.push(quote(r)+(gap?\": \":\":\")+o))}}return o=0===a.length?\"{}\":gap?\"{\\n\"+gap+a.join(\",\\n\"+gap)+\"\\n\"+f+\"}\":\"{\"+a.join(\",\")+\"}\",gap=f,o}}\"function\"!=typeof Date.prototype.toJSON&&(Date.prototype.toJSON=function(){return isFinite(this.valueOf())?this.getUTCFullYear()+\"-\"+f(this.getUTCMonth()+1)+\"-\"+f(this.getUTCDate())+\"T\"+f(this.getUTCHours())+\":\"+f(this.getUTCMinutes())+\":\"+f(this.getUTCSeconds())+\"Z\":null},String.prototype.toJSON=Number.prototype.toJSON=Boolean.prototype.toJSON=function(){return this.valueOf()});var cx,escapable,gap,indent,meta,rep;\"function\"!=typeof JSON.stringify&&(escapable=/[\\\\\\\"\\x00-\\x1f\\x7f-\\x9f\\u00ad\\u0600-\\u0604\\u070f\\u17b4\\u17b5\\u200c-\\u200f\\u2028-\\u202f\\u2060-\\u206f\\ufeff\\ufff0-\\uffff]/g,meta={\"\\b\":\"\\\\b\",\"\\t\":\"\\\\t\",\"\\n\":\"\\\\n\",\"\\f\":\"\\\\f\",\"\\r\":\"\\\\r\",'\"':'\\\\\"',\"\\\\\":\"\\\\\\\\\"},JSON.stringify=function(t,e,n){var r;\r\n" + 
			"if(gap=\"\",indent=\"\",\"number\"==typeof n){for(r=0;r<n;r+=1){indent+=\" \"}}else{\"string\"==typeof n&&(indent=n)}if(rep=e,e&&\"function\"!=typeof e&&(\"object\"!=typeof e||\"number\"!=typeof e.length)){throw new Error(\"JSON.stringify\")}return str(\"\",{\"\":t})}),\"function\"!=typeof JSON.parse&&(cx=/[\\u0000\\u00ad\\u0600-\\u0604\\u070f\\u17b4\\u17b5\\u200c-\\u200f\\u2028-\\u202f\\u2060-\\u206f\\ufeff\\ufff0-\\uffff]/g,JSON.parse=function(text,reviver){function walk(t,e){var n,r,o=t[e];if(o&&\"object\"==typeof o){for(n in o){Object.prototype.hasOwnProperty.call(o,n)&&(r=walk(o,n),void 0!==r?o[n]=r:delete o[n])}}return reviver.call(t,e,o)}var j;if(text=String(text),cx.lastIndex=0,cx.test(text)&&(text=text.replace(cx,function(t){return\"\\\\u\"+(\"0000\"+t.charCodeAt(0).toString(16)).slice(-4)})),/^[\\],:{}\\s]*$/.test(text.replace(/\\\\(?:[\"\\\\\\/bfnrt]|u[0-9a-fA-F]{4})/g,\"@\").replace(/\"[^\"\\\\\\n\\r]*\"|true|false|null|-?\\d+(?:\\.\\d*)?(?:[eE][+\\-]?\\d+)?/g,\"]\").replace(/(?:^|:|,)(?:\\s*\\[)+/g,\"\"))){return j=eval(\"(\"+text+\")\"),\"function\"==typeof reviver?walk({\"\":j},\"\"):j}throw new SyntaxError(\"JSON.parse\")})}();function getEks(){return window.TDC.getInfo().info}function getData(tokenid,e,src){e=e.replace(/\\s*/g,\"\");e=JSON.parse(e);var up,bt=0,mt=0,o=0,x=0,y=0;sid=src;up=getMouserup(e);mouse=e;var token=JSON.stringify({\"message\":{\"val\":tokenid+\":\"+ +new Date(),\"type\":\"set\"}});window.TDC.setData({\"ft\":\"qf_7P_n_H\",\"coordinate\":[10,24,0.40512820512820513],\"clientType\":\"2\",trycnt:++bt,refreshcnt:mt,slideValue:e,dragobj:o});for(var i=0;i<mouse.length-1;i++){sTimeout(mouse[i][2]);ms.mousemove({pageX:mouse[i][0]+x,pageY:mouse[i][1]+y});x=x+mouse[i][0];y=y+mouse[i][1]}ms.mouseup(up);ms.message(token);return window.TDC.getData(!0)};";
	
	public static String getNewTicket(String proxyIp,int proxyPort) {
		try {
			String ua= Base64.encodeString(UA);
			Map<String,String> headers=new HashMap<String,String>();
			headers.put("User-Agent", UA);
			headers.put("Referer", "http://upay.10010.com/npfwap/npfMobWap/bankcharge/index.html");
			Http.Response res=Http.create("http://upay.10010.com/npfwap/NpfMob/VerifyCodeNew/getUrl").proxy(proxyIp, proxyPort).bodys("channelType=307").post().send().getResponse();
			String body=res.getResult();
			if(res.getStatus()==0|| StringUtils.isEmpty(body))return null;
			JSONObject json=JSONObject.fromObject(body);
			String jsUlr=json.getString("jsUrl");
			Map<String, String> map=getUrlParams(jsUlr.substring(jsUlr.indexOf("?") + 1));
			String appid=map.get("appid");
			String asig=map.get("asig");
			String clientype=map.get("clientype");
			String lang=map.get("lang");
			//获取sess和sid
			res=Http.create("https://captcha.guard.qcloud.com/cap_union_prehandle?asig="+asig+"&aid="+appid+"&captype=&protocol=https&clientype="+clientype+"&disturblevel=&apptype=&noheader=&color=FF8C00&showtype=popup&fb=1&theme=&lang="+lang+"&ua="+ua+"&cap_cd=&uid=&callback=_aq_152037&sess=&subsid=1").proxy(proxyIp, proxyPort).heads(headers).get().send().getResponse();
			body=res.getResult();
			if(res.getStatus()==0||StringUtils.isEmpty(body))return null;
			body=body.replace("_aq_152037(", "").replace(")", "");
			json=JSONObject.fromObject(body);
			String state=json.getString("state");
			String sid=json.getString("sid");
			String sess=json.getString("sess");
			if(!"1".equals(state)) {
				return null;
			}
			//获取滑块显示页面
			String rnd= PassWrodsCreater.generateRandomNumber(6);
			String TCapIframeLoadTime=PassWrodsCreater.generateRandomNumber(2);
			String prehandleLoadTime=PassWrodsCreater.generateRandomNumber(3);
			long createIframeStart=System.currentTimeMillis();
			res=Http.create("https://captcha.guard.qcloud.com/cap_union_new_show?asig="+asig+"&aid="+appid+"&captype=&protocol=https&clientype="+clientype+"&disturblevel=&apptype=&noheader=&color=FF8C00&showtype=popup&fb=1&theme=&lang="+lang+"&ua="+ua+"&sess="+sess+"&fwidth=0&sid="+sid+"&subsid=2&uid=&cap_cd=&rnd="+rnd+"&forcestyle=undefined&wxLang=&TCapIframeLoadTime="+TCapIframeLoadTime+"&prehandleLoadTime="+prehandleLoadTime+"&createIframeStart="+createIframeStart).proxy(proxyIp, proxyPort).heads(headers).get().send().getResponse();
			body=res.getResult();
			if(res.getStatus()==0||StringUtils.isEmpty(body))return null;
			String pattern = ",Q=\"(.*?)\",";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(body);
			m.find();
			String vsig=m.group(1);
			pattern = "websig=([0-9a-f]{128})";
			r = Pattern.compile(pattern);
			m = r.matcher(body);
			m.find();
			String websig=m.group(1);
			pattern = "a\\D\\D&([a-z].{5})=";
			r = Pattern.compile(pattern);
			m = r.matcher(body);
			m.find();
			String collect_name=m.group(1);
			pattern = "&quot;ans&quot;:&quot;([0-9a-f]{32})&quot";
			r = Pattern.compile(pattern);
			m = r.matcher(body);
			m.find();
			String ans_ =m.group(1);
			pattern = "&quot;M&quot;:&quot;(\\d+)&quot;";
			r = Pattern.compile(pattern);
			m = r.matcher(body);
			m.find();
			String M =m.group(1);
			pattern = "&quot;randstr&quot;:&quot;(.{4})&quot;";
			r = Pattern.compile(pattern);
			m = r.matcher(body);
			m.find();
			String randstr =m.group(1);
			pattern = "Number\\D\\D([0-9]{1,3})\\D\\D";
			r = Pattern.compile(pattern);
			m = r.matcher(body);
			m.find();
			String height=m.group(1);
			int cdata=getCdata(ans_, M, randstr);
			List slide_value=get_slide_value(cdata);
			//获取大图片1
			String imgUrl1="https://captcha.guard.qcloud.com/cap_union_new_getcapbysig?asig="+asig+"&aid="+appid+"&captype=&protocol=https&clientype="+clientype+"&disturblevel=&apptype=&noheader=&color=FF8C00&showtype=popup&fb=1&theme=&lang="+lang+"&ua="+ua+"&sess="+sess+"&fwidth=0&sid="+sid+"&subsid=3&uid=&cap_cd=&rnd="+rnd+"&forcestyle=undefined&wxLang=&TCapIframeLoadTime="+TCapIframeLoadTime+"&prehandleLoadTime="+prehandleLoadTime+"&createIframeStart="+createIframeStart+"&rand=0.4143937011513462&websig="+websig+"&vsig="+vsig+"&img_index=1";
			//String imgUrl2="https://captcha.guard.qcloud.com/cap_union_new_getcapbysig?asig="+asig+"&aid="+appid+"&captype=&protocol=https&clientype="+clientype+"&disturblevel=&apptype=&noheader=&color=FF8C00&showtype=popup&fb=1&theme=&lang="+lang+"&ua="+ua+"&sess="+sess+"&fwidth=0&sid="+sid+"&subsid=4&uid=&cap_cd=&rnd="+rnd+"&forcestyle=undefined&wxLang=&TCapIframeLoadTime="+TCapIframeLoadTime+"&prehandleLoadTime="+prehandleLoadTime+"&createIframeStart="+createIframeStart+"&rand=0.4143937011513462&websig="+websig+"&vsig="+vsig+"&img_index=2";
			/*try {
				downLoadFile(imgUrl1, "d://", "1.jpeg");
				downLoadFile(imgUrl2, "d://", "2.png");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			int width=calcMoveDistance(imgUrl1, 680f);
			String ans=width+","+height+";";
			if(collect_value==null||tdcUseCount>3)
			{
				if(!getNewTDC(slide_value))return "";
				TencentCrawler.tdcUseCount=0;
			}
			else
			{
				tdcUseCount+=1;
			}
			headers.put("Referer", "https://captcha.guard.qcloud.com");
			String params="asig="+asig+"&aid="+appid+"&captype=&protocol=https&clientype="+clientype+"&disturblevel=&apptype=&noheader=&color=FF8C00&showtype=popup&fb=1&theme="
			+ "&lang="+lang+"&ua="+ua+"&sess="+sess+"&fwidth=0&sid="+sid+"&subsid=6&"
			+ "uid=&cap_cd=&rnd="+rnd+"&forcestyle=undefined&wxLang=&TCapIframeLoadTime="+TCapIframeLoadTime+"&prehandleLoadTime="
			+prehandleLoadTime+"&createIframeStart="+createIframeStart+"&rand=0.4143937011513462&buid=&subcapclass=10"
			+ "&vsig="+vsig+"&ans="+ans+"&"+collect_name+"="+collect_value+"&websig="+websig+"&cdata="+cdata+"&fpinfo=undefined&eks="+eks+"&tlg=1&vlg=0_0_0&vmtime=_&vmData=";
			res=Http.create("https://captcha.guard.qcloud.com/cap_union_new_verify").proxy(proxyIp, proxyPort).heads(headers).bodys(params).post().requestType(Http.RequestType.FORM).send().getResponse();
			if(res.getStatus()==0||StringUtils.isEmpty(body))return null;
			body=res.getResult();
			log.info(proxyIp+"_______________"+body);
			json=JSONObject.fromObject(body);
			if("0".equals(json.getString("errorCode")))
				return json.getString("ticket");
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "";
		}
	}
	
	private static boolean getNewTDC(List slide_value) throws Exception
	{
		Map<String,String> headers=new HashMap<String,String>();
		headers.put("User-Agent", UA);
		headers.put("Referer", "http://upay.10010.com/npfwap/npfMobWap/bankcharge/index.html");
		Http.Response res=Http.create("https://dj.captcha.qq.com/tdc.js?v=1.6.2").heads(headers).get().send().getResponse();
		String body=res.getResult();
		if(res.getStatus()==0||StringUtils.isEmpty(body))return false;
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		engine.eval(TDC_HEAD+body);
		Invocable in = (Invocable) engine;
		String collect_value= (String) in.invokeFunction("getData",System.currentTimeMillis()/1000,slide_value.toString(),"");
		String eks=(String) in.invokeFunction("getEks");
		TencentCrawler.collect_value=collect_value;
		TencentCrawler.eks=eks;
		return true;
	}
	
	public static Map<String, String> getUrlParams(String param) {  
        Map<String, String> map = new HashMap<String, String>(0);  
        if (StringUtils.isEmpty(param)) {  
            return map;  
        }  
        String[] params = param.split("&");  
        for (int i = 0; i < params.length; i++) {  
            String[] p = params[i].split("=");  
            if (p.length == 2) {  
                map.put(p[0], p[1]);  
            }  
        }  
        return map;  
    }  
	
	
	public static void downLoadFile(String server,String path,String fileName) throws Exception
	{
		 URL url = new URL(server);  
	     HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	     conn.setRequestMethod("GET");  
	     conn.setConnectTimeout(5 * 1000);  
	     InputStream inStream = conn.getInputStream();  
	     byte[] data = readInputStream(inStream);  
	     File imageFile = new File(path+fileName);  
	     FileOutputStream outStream = new FileOutputStream(imageFile);  
	     outStream.write(data);  
	     outStream.close();  
	}
	public static byte[] readInputStream(InputStream inStream) throws Exception{  
		
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
	
	public static String getCk() {
		/*Map<String,String> headers=new HashMap<String,String>();
		headers.put("User-Agent", UnicomConfig.Wap_User_Agent);
		headers.put("Referer", "http://upay.10010.com/npfwap/npfMobWap/bankcharge/index.html");
		Response res=Http.create("http://upay.10010.com/npfwap/NpfMob/needCode?channelType=307&_="+System.currentTimeMillis()).heads(headers).cookies("upay_user=c5419812d8560e713d8d27daa766e014;").get().send().getResponse();
		String body=res.getResult();
		System.out.println(body);
		if(res.getStatus()==0||StringUtils.isEmpty(body))
		{
			return;
		}
		String ticket="ticket";
		if("yes".equals(body.trim()))
		{
			String newTicket=getNewTicket("", 0);
			if(StringUtils.isEmpty(newTicket))
				return;
			else
				ticket=newTicket;
		}*/
		String newTicket=getNewTicket("", 0);
		//System.out.println(newTicket);
		return newTicket;
	}
	public static void main(String[] args) {
		
		String newTicket=getNewTicket("", 0);
		System.out.println(newTicket);
	}
	
	public static int nextInt(final int min, final int max)  
	{  
	  
	      Random rand= new Random();  
	      int tmp = Math.abs(rand.nextInt());  
	      return tmp % (max - min + 1) + min;  
	  
	}   
	
	public static List  get_slide_value(int cdata)
	{
		int randomTimes=nextInt(12, 16);
		List<List> dataList=new ArrayList<List>();
		List cList2=new ArrayList();
		cList2.add(83);
		cList2.add(280);
		cList2.add(4);
		dataList.add(cList2);
		for(int i=0;i<randomTimes;i++)
		{
			int random1=nextInt(1, 7);
			int random2=Math.random()>0.5?-1:0;
			int random3=nextInt(16, 17);
			List cList=new ArrayList();
			cList.add(random1);
			cList.add(random2);
			cList.add(random3);
			dataList.add(cList);
		}
		for(int i=0;i<randomTimes-1;i++)
		{
			int random1=nextInt(1, 7);
			int random2=Math.random()>0.5?-1:0;
			int random3=nextInt(80, 300);
			List cList=new ArrayList();
			cList.add(random1);
			cList.add(random2);
			cList.add(random3);
			dataList.add(cList);
		}
		List cList1=new ArrayList();
		cList1.add(1);
		cList1.add(0);
		cList1.add(nextInt(80, 300));
		dataList.add(cList1);
		List cList3=new ArrayList();
		cList3.add(0);
		cList3.add(0);
		cList3.add(cdata);
		dataList.add(cList3);
		return dataList;
	}
	
	public static int getCdata(String ans,String M,String randstr)
	{
		ans=ans.toLowerCase();
		int p=0;
		for(int i=0;i<Integer.parseInt(M)&&i<1e3;i++)
		{
			String f=randstr+i;
			String m= MD5.getMD5(f);
			if(ans.equals(m))
			{
				p = i;
				break;
			}
		}
		return p;
	}
	
	public static int calcMoveDistance(String imageUrl, float bgWrapWidth) throws IOException {
		URL url = new URL(imageUrl);
		BufferedImage fullBI = ImageIO.read(url.openStream());
		if(fullBI==null) throw new RuntimeException("滑块下载超时");
        //BufferedImage fullBI = ImageIO.read(new File("d://1.jpeg"));
        for(int w = 340 ; w < fullBI.getWidth() - 18; w++){
            int whiteLineLen = 0;
            for (int h = 0; h < fullBI.getHeight(); h++){
                int[] fullRgb = new int[3];
                fullRgb[0] = (fullBI.getRGB(w, h)  & 0xff0000) >> 16;
                fullRgb[1] = (fullBI.getRGB(w, h)  & 0xff00) >> 8;
                fullRgb[2] = (fullBI.getRGB(w, h)  & 0xff);
                if (isBlack28(fullBI, w, h) && isWhite(fullBI, w, h)) {
                    whiteLineLen++;
                } else {
//                    whiteLineLen = 0;
                    continue;
                }
                if (whiteLineLen >= 50){
                    //System.out.println("找到缺口成功，实际缺口位置x：" + w);
                    return w-20;
                   // System.out.println("应该移动距离：" + (w - START_DISTANCE) / (fullBI.getWidth() / bgWrapWidth));
                    //return (int) ((w - START_DISTANCE) / (fullBI.getWidth() / bgWrapWidth));
                }
            }
        }
        throw new RuntimeException("计算缺口位置失败");
    }
	
	   /**
     * 当前点的后28个是不是黑色
     *
     * @return 后28个中有80%是黑色返回true, 否则返回false
     */
    private static boolean isBlack28(BufferedImage fullBI, int w, int h) {
        int[] fullRgb = new int[3];
        double blackNum = 0;
        int num = Math.min(fullBI.getWidth() - w, 28);
        for (int i = 0; i < num; i++) {
            fullRgb[0] = (fullBI.getRGB(w + i, h) & 0xff0000) >> 16;
            fullRgb[1] = (fullBI.getRGB(w + i, h) & 0xff00) >> 8;
            fullRgb[2] = (fullBI.getRGB(w + i, h) & 0xff);
            if (isBlack(fullRgb)) {
                blackNum = blackNum + 1;
            }
        }

        return blackNum / num > 0.8;
    }
    
    private static boolean isWhite(int[] fullRgb) {
        return (Math.abs(fullRgb[0] - 0xff) + Math.abs(fullRgb[1] - 0xff) + Math.abs(fullRgb[2] - 0xff)) < 125;
    }

    private static boolean isBlack(int[] fullRgb) {
        return fullRgb[0] * 0.3 + fullRgb[1] * 0.6 + fullRgb[2] * 0.1 <= 125;
    }
    
    /**
     * 当前点是不是白色
     *
     * @param fullBI
     * @param w
     * @param h
     * @return
     */
    private static boolean isWhite(BufferedImage fullBI, int w, int h) {
        int[] fullRgb = new int[3];
        fullRgb[0] = (fullBI.getRGB(w, h) & 0xff0000) >> 16;
        fullRgb[1] = (fullBI.getRGB(w, h) & 0xff00) >> 8;
        fullRgb[2] = (fullBI.getRGB(w, h) & 0xff);

        return isWhite(fullRgb);
    }
    
}
