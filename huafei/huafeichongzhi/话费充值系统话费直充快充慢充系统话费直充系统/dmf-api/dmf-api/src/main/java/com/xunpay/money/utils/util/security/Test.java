//package com.xunpay.money.utils.util.security;
//
//
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.jfinal.aop.Before;
//import com.jfinal.ext.interceptor.POST;
//import com.jfinal.plugin.activerecord.Db;
//import com.jfinal.plugin.activerecord.IAtom;
//import com.payment.util.Md5Utils;
//import com.xunpay.money.core.BaseController;
//import com.xunpay.money.model.Phone;
//import com.xunpay.money.model.PhoneDelete;
//import com.xunpay.money.utils.HttpClientHelperCtcc;
//
//import net.sf.json.JSONObject;
//
//public class Test extends BaseController {
//	
//	public static final String UA="Mozilla/5.0 (iPhone; CPU iPhone OS 12_4_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/7.0.12(0x17000c31) NetType/WIFI Language/zh_CN";
//									
//	public static final String Referer="http://aaa.votegoo.com/vote/frontend/joinner/party_id/10542/parent_id/4652/player_id/200921/t/1591840224?from=singlemessage&isappinstalled=0";
//						
//	//获取微信cookie
//	  public  static  void getSessions(){
//		  	String   userResultCookie = HttpClientHelperCtcc.sendGetCookie("http://aaa.votegoo.com/vote/frontend/joinner/party_id/10542/parent_id/4652/player_id/200921/t/1591840224?from=singlemessage&isappinstalled=0");
//		  
//		  	if(userResultCookie!=null || "".equals(userResultCookie) ) {
//		  		
//		  		System.out.println("访问用户成功");
//		  	}
//		  	
//		  	//模拟登录
//		  	
//		    System.out.println("请求的sesssion为"+userResultCookie);
//	      // String  loginResult = HttpClientHelperCtcc.sendGetLogin("http://aaa.votegoo.com/member/logintest/wxLogin",userResultCookie);
//	      //  System.out.println("返回的sesssion为"+loginResult);
//	        
//	        //最后重定向
//	     //   redirect("http://aaa.votegoo.com/vote/frontend/joinner/party_id/10542/parent_id/4652/player_id/200921/t/1591840224?from=singlemessage&isappinstalled=0");
//	        
//	
//	  }
//
//    //获取验证码
//    public static  String getyzm(){
//    	
//    	//String imgURl="data:image/png;base64,/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a%0AHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy%0AMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAoAIwDASIA%0AAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA%0AAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3%0AODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm%0Ap6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA%0AAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx%0ABhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK%0AU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3%0AuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD0EVIK%0AYKkFADhTxTRTxQA8U4VBMxWNtrbWI4OM4rzq/l8Z6XazNJ4ss2t4kLGd7BfMAHsDtzQB6gKduVRl%0AiAMgc1518OP7Rewk1fV9QuLy7vsFPOPEUYzgKvRc9Tj29Ky/jJftJpmkWDSFLSe+X7QwOOAOM/mT%0A+FAHry08Vh6Vr2lXSrDb31qxUBRGsq5HtjNbe8AZoAkFSCuAf4qaPaa/qmk38NxbyWDBQ2N/nZ7q%0ABz3BrrdE1/S/ENiLzSryO6gJxuTIIPoQeQfqKANUVEL+z+1G1+1Q/aByYvMG/wDLrTLmfyoy1fO+%0Am6N4d8XahqE2r31xHr019MzCObayANhQAQRwBQB9LKQakFcz4Os30rQLXTnvZ71oQR587FnbJJGc%0Ak9M4+grp1oAeKdimingUAcQKeKaKeKAHiniminigBkse9SK4bx3YSyeGdSWLJbyGOB6Dk134HFZm%0Ap2/mIRjIPUUAYXgl4rzw7YXEJBjeBcY7YGCPzFZ3j/RF1rSZbN+Cfnjc/wALjof5j6E1seEPD8Ph%0A6Ge3tZZzbyymVYpCCsWeoXjIH1zW5qVgLmI8UAeJeEdM0HUrWfSdU05YNYtciVlYrIR2dSO3I9un%0AqK7Dwn4h1DRtdm8JatdPdqYzNp125yzx90b3GD+R9qq+JfBxvpYrq0ney1GA/urmPrjup9Ryfz9y%0ADY0Twps1yHVby9ur27iTZG0u0KmRg4AA96AMXxrazaZ4ot/F1pb+c0AAvIgOWQDG8e4H6Ads1PPq%0AUmlXsHjrw4POtJVzqdtF0mj5zJj+8vOfTHP8Veg32k+Ym/b2rjdL8LXOg61LNps6pptwd0ti6naj%0A/wB5D2+mMfpgA9KtNTtPEGiwX+nzLNbTpuR17+x9CDkEdiMVxWu+FdJvFf7fbxR73+WdfkZXJ4YM%0AO+T36mtTwb4ah0DUb6WxuJo7K7Ic2HHlRyd2XuM+g4/IY6nVNPhubSSOWJJI5FKujrkMD1BHcUAc%0AV4U8U3Gga5B4W8SSh5J/+QdqJGFuR2RvRx09+O5GfVY2DDivAPEfgK41GaK3ttVnjtY3DJbz5kEZ%0A/wBhz8wHtyK9s0F5v7OgjnkMsqIFZz/EQOtAGwKeBTRTxQBxAp4oooAkFPFFFADxQ8QcciiigAit%0A1Q5AqzsBGDRRQBSn01JjnFOttMjibIFFFAF826smCKqnS4y2dooooAt29ksR4FW2hDrgiiigDOl0%0AaOSXdtFadpbCBABRRQBdFPxRRQB//9k=";
//    	String imgURl="http://wapzt.189.cn/wap/getPicRandomCode.do?param=1584771683819&sessionid=2f31f7df70284c6386ddb2702d08f2e2&type=2";
//    	//String imgURl="http://fast.mynatapp.cc";
//        byte [] s = HttpClientHelperCtcc.sendGetVerCode(imgURl,null);
//        
//        String outcode = HttpClientHelperCtcc.getCode(s);
//        Map map=JSONObject.fromObject(outcode);
//        if("false".equals(map.get("result"))){
//           return getyzm();
//        }
//        Map map1=JSONObject.fromObject(map.get("data"));
//        String yzm=map1.get("val").toString();
//        System.out.println(map.get("data"));
//        System.out.println(map1.get("val"));
//        return yzm;
//    }
//    
//    //校验验证码
//    public static boolean  checkCode(String cookie,String yzm) {
//    	
//    	Map<String, String> map=new HashMap<String, String>();
//    	map.put("code", yzm);
//    	
//    	String result=HttpClientHelperCtcc.sendPostCode("http://aaa.votegoo.com/vote/frontendApi/validateCaptcha",map,cookie);
//    
//    	System.out.println(result);
//    	
//    	if(result.indexOf("true")!=-1) {
//    		
//    		return true;
//    	}
//    
//    	return false;
//    }
//    
//    //投票
//    public static String  vote(String cookie) {
//	  
//    	Map<String, String> map=new HashMap<String, String>();
//    	
//    	map.put("pext_id", "10542");
//    	map.put("id", "200921");
//    	
//    	String result=HttpClientHelperCtcc.sendPostCode("http://aaa.votegoo.com/vote/frontendApi/vote",map,cookie);
//    	if("".equals(result) ||  result==null) {
//    		System.out.println("session不可用");
//    		return null;
//    	}	
//    	JSONObject jsonobj=JSONObject.fromObject(result);
//    	
//    	String coke=jsonobj.getString("code");
//    	
//    	System.out.println(result);
//    	if("1".equals(coke)) {
//    		
//    		System.out.println("投票成功");
//    		
//    	}else {
//    		
//    		System.out.println("投票失败");
//    	}
//    
//    	return null;
//    }
//    
//    
//    //测试
//    public static void main(String[] args) {
//    	
//    	
//    	//getSessions();
//		//jo7vejiriu54fme1thds0o2ep0
//    	
////    	for (int i = 0; i <5; i++) {
////    		
////	    	String cookie="PHPSESSID=mfbqtm05jcsedvg019umn1k007";
////	    	String yzm=getyzm(cookie);
////	    	checkCode(cookie, yzm);
////	    	vote(cookie);
////    		 
////			
////		}
//    	
//    	
//        //redirect("http://v.qddlive.cn/member/logintest/wxLogin");
//    	
////        try {
////        	Thread.sleep(20000L);
////
////		} catch (InterruptedException e) {
////			
////			e.printStackTrace();
////		}
//        
//    	//String cookie="PHPSESSID=v07f7iteaj27glsjdbdl8pg8i5";
//    	
//    	String yzm=getyzm();
//   	
//    	//checkCode(cookie, yzm);
//    	
//    	//vote(cookie);
//    
//    	
//	}
//    
//    
//    /*
//     * 接收手机号
//  
//    @Before(POST.class)
//    public void receivePhone() {
//        Map<String, Object> maps = new HashMap<String, Object>();
//        HttpServletRequest request = getRequest();
//        String data = request.getParameter("data");
//        String token = request.getParameter("token");
//        String sign = request.getParameter("sign");
//        boolean fig = validationSign(token, sign);
//        if (fig) {
//            try {
//                //验证签名在这做判断
//                List<Object> list = JSONObject.parseArray(data);
//                //多个项目在这做判断
//                Map<String, Object> map = JSONObject.parseObject(list.get(0).toString());
//                List<Object> phones = JSONObject.parseArray(map.get("mobile").toString());
//                for (int i = 0; i < phones.size(); i++) {
//                    Phone phone = new Phone();
//                    phone.setId(phones.get(i).toString());
//                    phone.setFlag("0");
//                    phone.save();
//                }
//                //int i=1/0;
//                maps.put("code", "25200");
//                maps.put("data", "25200");
//            } catch (Exception e) {
//                maps.put("code", "9999");
//                maps.put("data", "系统错误");
//                System.out.println("接收手机号系统错误----------------");
//                Db.tx(new IAtom() {
//                    @Override
//                    public boolean run() throws SQLException {
//                        return false;
//                    }
//                });
//                e.printStackTrace();
//            } finally {
//                System.out.println("号码接收成功");
//                renderText(JSONObject.toJSONString(maps));
//            }
//        } else {
//            maps.put("code", "9997");
//            maps.put("data", "签名不通过");
//            renderText(JSONObject.toJSONString(maps));
//        }
//    }
//
//
//      删除手机号
//   
//    public void deletePhone() {
//        HttpServletRequest request = getRequest();
//        String data = request.getParameter("data");
//        Map<String, Object> rederMap = new HashMap<String, Object>();
//        String token = request.getParameter("token");
//        String sign = request.getParameter("sign");
//        boolean fig = validationSign(token, sign);
//        if (fig) {
//            try {
//                List<Object> list = JSONObject.parseArray(data);
//                if (list == null) {
//                    list = new ArrayList<>();
//                }
//                if (list.size() > 0) {
//                    Map<String, Object> map = JSONObject.parseObject(list.get(0).toString());
//                    List<Object> phones = JSONObject.parseArray(map.get("mobile").toString());
//                    for (int i = 0; i < phones.size(); i++) {
//                        Phone phone = new Phone();
//                        phone.setId(phones.get(i).toString());
//                        phone.delete();
//                    }
//                }
//                //删除电话预删除表中的数据
//                List<PhoneDelete> phoneDeleteList = PhoneDelete.dao.find("select * from phone_delete");
//                for (PhoneDelete phone : phoneDeleteList) {
//                    Phone ph = new Phone();
//                    ph.setId(phone.getId());
//                    ph.delete();
//                    PhoneDelete phoneDelete = new PhoneDelete();
//                    phoneDelete.setId(phone.getId());
//                    phoneDelete.delete();
//                    list.add(phone.getId());
//                }
//
//                rederMap.put("code", "25200");
//                Map maps = new HashMap();
//                maps.put("projectId", "1020");
//                maps.put("mobile", list);
//                rederMap.put("data", maps);
//                System.out.println("下线处理的号码数量：" + list.size());
//            } catch (Exception e) {
//                rederMap.put("code", "9999");
//                rederMap.put("data", "系统错误");
//                System.out.println("删除手机号系统错误----------------");
//                Db.tx(new IAtom() {
//                    @Override
//                    public boolean run() throws SQLException {
//                        return false;
//                    }
//                });
//                e.printStackTrace();
//            } finally {
//                System.out.println("号码删除成功");
//                renderText(JSONObject.toJSONString(rederMap));
//            }
//        } else {
//            rederMap.put("code", "9997");
//            rederMap.put("data", "签名不通过");
//            renderText(JSONObject.toJSONString(rederMap));
//        }
//
//    }
//
//  
//     保存验证码
//    
//    public void saveCode() {
//        HttpServletRequest request = getRequest();
//        Map<String, Object> maps = new HashMap<String, Object>();
//        String data = request.getParameter("data");
//        String token = request.getParameter("token");
//        String sign = request.getParameter("sign");
//        boolean fig = validationSign(token, sign);
//        if (fig) {
//            try {
//                List<Object> list = JSONObject.parseArray(data);
//                for (int i = 0; i < list.size(); i++) {
//                    Map<String, Object> map = JSONObject.parseObject(list.get(i).toString());
//                    String mapyzm = map.get("sms").toString();
//                    String code = mapyzm.substring(mapyzm.indexOf("：") + 1, mapyzm.indexOf("：") + 7);
//                    Db.update("update phone set yzm=? where id=?", code, map.get("mobile"));
//                }
//                maps.put("code", "25200");
//                maps.put("data", "25200");
//            } catch (Exception e) {
//                maps.put("code", "9999");
//                maps.put("data", "系统错误");
//                Db.tx(new IAtom() {
//                    @Override
//                    public boolean run() throws SQLException {
//                        return false;
//                    }
//                });
//                e.printStackTrace();
//            } finally {
//                System.out.println("验证码保存成功");
//                renderText(JSONObject.toJSONString(maps));
//            }
//        } else {
//            maps.put("code", "9997");
//            maps.put("data", "签名不通过");
//            renderText(JSONObject.toJSONString(maps));
//        }
//    }
//
//
//     验证签名
//  
//    public boolean validationSign(String token, String sign) {
//        String mySign = Md5Utils.getMd5(AppId + AppKey + token);
//        if (mySign.equals(sign)) {
//            return true;
//        }
//        return false;
//    }
//   * */
//   
//
//
////    public void test() {
////        System.out.println("进入删除方法");
////        List<PhoneDelete> phoneDeleteList = PhoneDelete.dao.find("select * from phone_delete");
////        System.out.println("需要删除的个数:" + phoneDeleteList.size());
////        for (PhoneDelete phone : phoneDeleteList) {
////            Phone ph = new Phone();
////            ph.setId(phone.getId());
////            ph.delete();
////            PhoneDelete phoneDelete = new PhoneDelete();
////            phoneDelete.setId(phone.getId());
////            phoneDelete.delete();
////        }
////
////    }
////    
//    
//
//
//}
//
//
//
