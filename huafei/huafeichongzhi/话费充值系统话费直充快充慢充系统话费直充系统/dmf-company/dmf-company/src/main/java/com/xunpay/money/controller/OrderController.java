package com.xunpay.money.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mysql.jdbc.log.Log;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.AlipayAgentBill;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyBill;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.model.CompanyOrderSettle;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.HttpsClientHelper;
import com.xunpay.money.utils.MobileCheck;
import com.xunpay.money.utils.ShiroUtils;

//import net.sf.json.JSONObject;

public class OrderController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(OrderController.class);
	
	public void listOrder() {
		
		String pay_type=getPara("pay_type");
		
		String select = "select *";
		String except = "from company_apiorder where  company_id = " + ShiroUtils.getUserId();
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "orderNo", "order_no", "out_order_no", "trade_no");
		except += getParaSql(args, "status", "status");
		except += getParaSql(args, "notice", "notice");
		except += getParaSql(args, "appid", "appid");
		
		//按照通道类型查询
		except += getParaSql(args, "pay_type", "pay_type");
		
		String startTime=getPara("startTime");
		
		String endTime= getPara("endTime");
		
		if(startTime!=null && endTime!=null) {
			
			except+= getParaDateCompare(startTime, endTime, "addtime");
		}

		except += " order by id desc";
		keepPara();
		setAttr("page", CompanyOrder.dao.paginate(getParaToInt("page", 1), 20, select, except, args.toArray()));
		setAttr("startTime",startTime);
		setAttr("endTime",endTime);
		setAttr("payType",pay_type);
		
		//默认设置当天总流水
		Date date=new Date();
		    
		    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		    
		    String currentDay=format.format(date);
		    
		    String startDate=currentDay+" 00:00:00";
		    
		    String endDate=currentDay+" 23:59:59";
			
			//默认统计当天所有商户的流水金额
		
		    BigDecimal orderMoney=null;
		    
		   	 String uid=String.valueOf(ShiroUtils.getUser().getId());
		   	 
			//当查询条件不存在的时候
		    if((args.size()==0 || args.isEmpty()) && ( startTime==null && endTime ==null)) {
		    	
		    	String sql="select sum(order_money)  from company_order where  addtime>='"+startDate+"' and  addtime<='"+endDate+"'  and status='已支付' and  company_id='"+uid+"' ";
		    	
		    	orderMoney=Db.queryBigDecimal(sql);
		    	
		    	setAttr("orderMoney",orderMoney==null ? 0:orderMoney.intValue());
		    	
		    	setAttr("day","当天");
		    	
		    }else {
		    	 
		    	   
		    	StringBuffer sb=new StringBuffer();
		    	
				String sql  = "select sum(order_money)   from company_order where is_del = 'N' and status='已支付'  and  company_id='"+uid+"' ";
				
				sb.append(sql);
				
				//String status=getPara("status");
				
				//String notice= getPara("notice");
				
				String company_id= getPara("company_id");
				
				String startTime1=getPara("startTime");
				
				String endTime1= getPara("endTime");
				
				String pay_type1=getPara("pay_type");
				
				if(company_id!=null && !"".equals(company_id)) {
					
					sb.append(" and company_id='"+company_id+"' ");
					
				}
				
				if(startTime1!=null && endTime1!=null) {
					
					String str= getParaDateCompare(startTime1, endTime1, "addtime");
					
					sb.append(str);
				}
				
				if(pay_type1!=null && !"".equals(pay_type1)) {
					
					sb.append(" and pay_type='"+pay_type1+"' ");
					
				}
		    	
		    	orderMoney=Db.queryBigDecimal(sb.toString());
		    	
		    	setAttr("orderMoney",orderMoney==null?BigDecimal.ZERO:orderMoney.intValue());
		    	setAttr("day","查询");
		    }
	}
	
	//订单成功率
	public void totalRate() {
		String sql = "select " +
				" concat(ifnull(round(sum(case when `status` = '已支付' and date_sub(now(), interval 10 minute) <= addtime then 1 else 0 end)/sum(case when date_sub(now(), interval 10 minute) <= addtime then 1 else 0 end)*100, 2), '0.00'),'%') as min10_rate," +
				" concat(ifnull(round(sum(case when `status` = '已支付' and date_sub(now(), interval 30 minute) <= addtime then 1 else 0 end)/sum(case when date_sub(now(), interval 30 minute) <= addtime then 1 else 0 end)*100, 2), '0.00'),'%') as min30_rate," +
				" concat(ifnull(round(sum(case when `status` = '已支付' and date_sub(now(), interval 24 hour) <= addtime then 1 else 0 end)/sum(case when date_sub(now(), interval 24 hour) <= addtime then 1 else 0 end)*100, 2), '0.00'),'%') as hour24_rate, " +
				" concat(ifnull(round(sum(case when `status` = '已支付' then 1 else 0 end)/count(1)*100, 2), '0.00'),'%') as all_rate " +
				" from company_order where company_id = ?";
		Record r = Db.findFirst(sql, ShiroUtils.getUserId());
		renderJson(r);
	}
	
	public void reloadItem() {
		Integer id = getParaToInt();
		String token = Db.queryStr("select token from company_order where id = ? and company_id = ?", id, ShiroUtils.getUserId());
		String result = HttpClientHelper.sendGet("http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/system/reloadOrder?id=" + id + "&token=" + token);
		renderText(result);
	}
	
	public void settleItem() {
		Integer id = getParaToInt();
		String token = Db.queryStr("select token from company_order where id = ? and company_id = ?", id, ShiroUtils.getUserId());
		String result = HttpClientHelper.sendGet("http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/system/settleOrder?id=" + id + "&token=" + token);
		renderText(result);
	}
	//商户手动回调
	public void noticeItem() {
		
		
		String  treaty="";
		
		//订单号
		Integer id = getParaToInt();//notice_url
		
		String url = Db.queryStr("select notice_url  from company_order where id = ?", id);
		
		if(url.startsWith("http://")) {
			
			treaty="http";
		}
		
		else if(url.startsWith("https://")) {
			
			treaty="https";
		}
		
		StringBuffer sb=new StringBuffer();
		
		sb.append(url);
		
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where id = ?", id);
		
		sb.append("?type=").append(order.getPayType());
		
		sb.append("&total=").append(order.getOrderMoney());
		
		sb.append("&api_order_sn=").append(order.getOutOrderNo());
		
		//回调验证签名
		//全局加签
		CompanyInfo company = CompanyInfo.dao.findById(order.getCompanyId());
		
		//商户key
		String key=company.getMd5key();
		
		//第一个签名，是下游传过来的签名,用于对比准备
		String sign=EncryptUtils.encrypt("api_order_sn"+order.getOutOrderNo()
		+ "total"+order.getOrderMoney()
		+ "type"+order.getPayType()+key).toUpperCase();
		
		sb.append("&sign=").append(sign);
		
		String result =null;
		if(treaty.equals("http")) {
			
			 result =	HttpClientHelper.sendGet(sb.toString());
			
		}else if(treaty.equals("https")) {
			
			 result =HttpsClientHelper.sendGet(sb.toString());	
			
		}
		
			System.out.println("手动回调日志====>"+result);
					
			//sif(result!=null && !result.equals("")) {
			
			Db.update("update company_order set notice='回调成功'  where  id = ? ", id);
			
			renderText("success");
			
		//}
		
	}
	
	public void agentOrder() {
		Integer id = getParaToInt();
		CompanyInfo company = CompanyInfo.dao.findFirst("select * from company_info where id = ? and pid = ?", id, ShiroUtils.getUserId());
		if (company == null) {
			message(false, "对不起，没有找到代理信息", null);
			return;
		}
		setAttr("c", company);
		String select = "select *";
		String except = "from company_order where is_del = 'N' and company_id = " + company.getId();
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "orderNo", "order_no", "out_order_no", "trade_no");
		except += getParaSql(args, "status", "status");
		except += getParaSql(args, "settle", "settle");
		except += getParaSql(args, "notice", "notice");
		String date = getPara("date");
		if (StringUtils.isNotEmpty(date)) {
			except += " and date_format(addtime, '%Y-%m-%d') = ?";
			args.add(date);
		}
		except += " order by id desc";
		keepPara();
		setAttr("page", CompanyOrder.dao.paginate(getParaToInt("page", 1), 10, select, except, args.toArray()));
	}
	
	public void listAgent() {
		String select = "select *";
		String except = "from company_info where is_del = 'N' and pid = " + ShiroUtils.getUserId();
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "account", "nickname", "username");
		except += getParaSql(args, "status", "status");
		keepPara();
		setAttr("page", CompanyInfo.dao.paginate(getParaToInt("page", 1), 10, select, except, args.toArray()));
	}
	
	public void addAgent() {}
	
	public void editAgent() {
		CompanyInfo company = CompanyInfo.dao.findFirst("select * from company_info where pid = ? and id = ?", ShiroUtils.getUserId(), getParaToInt());
		if (company == null) {
			message(false, "没有找到代理商户信息", "/order/listAgent");
			return;
		}
		setAttr("c", company);
	}
	
	public void updateAgent() {
		CompanyInfo company = getModel(CompanyInfo.class, "c");
		CompanyInfo self = CompanyInfo.dao.findById(ShiroUtils.getUserId());
		if (company.getRebate().doubleValue() <= self.getRebate().doubleValue()) {
			message(false, "代理的费率必须大于自己的", "/order/listAgent");
			return;
		}
		if (company.getPid() != null
				|| StringUtils.isNotEmpty(company.getPassword())
				|| StringUtils.isNotEmpty(company.getPayPassword())) {
			message(false, "参数错误", "/order/listAgent");
			return;
		}
		company.update();
		message(true, "恭喜，代理修改成功", "/order/listAgent");
	}
	
	public void saveAgent() {
		CompanyInfo company = getModel(CompanyInfo.class, "c");
		CompanyInfo self = CompanyInfo.dao.findById(ShiroUtils.getUserId());
		if (company.getRebate().doubleValue() <= self.getRebate().doubleValue()) {
			message(false, "代理的费率必须大于自己的", "/order/listAgent");
			return;
		}
		company.setAddtime(new Date());
		company.setPid(ShiroUtils.getUserId());
		company.setBalance(BigDecimal.ZERO);
		company.save();
		message(true, "恭喜，代理添加成功", "/order/listAgent");
	}
	
	
	public void channelConfig() {
		
		Integer id = ShiroUtils.getUserId();
		
		String select = " SELECT \r\n" + 
				"	channel.code, \r\n" + 
				"	channel.type, \r\n" + 
				"	channel.nickname,\r\n" + 
				"	child.rebate,\r\n" + 
				"	child.`status`,\r\n" + 
				"	info.id,\r\n" + 
				"	info.username \r\n" + 
				"FROM\r\n" + 
				"	company_channel AS channel\r\n" + 
				"	LEFT JOIN company_channel_child AS child ON child.chanid = channel.id\r\n" + 
				"	LEFT JOIN company_info AS info ON child.infoid = info.id WHERE   info.id = '"+id+"' ";
				
		List<Record> recordList =Db.find(select);
		
		keepPara();
		
		setAttr("page",recordList);
		
	}
	
	//pc页面
	public void testOrder() {
		
	      //通道代码
	      String payType="hf_api";
	       
	       //话费面额
	       String money=getPara("money");
	       
	       //下游传过来的手机号码
	       String phone=getPara("phone");
	       
	       //回调地址
	       String nextUrl="http://www.baidu.com";//getPara("toUrl");
	       
		    Map<String, String> map = new HashMap<String, String>();
		  
	        //用户编号
	        map.put("userId", "727");
	        
	        //通过手机号码运营商校验获取商品ID
	        
	        int phoneChannel=MobileCheck.isChinaMobilePhoneNum(phone);
	        
	        String itemId="";
	        
	        switch (phoneChannel) {
	        
			case 1:
			    itemId="20075";
				break;
				
			case 2:
				
				itemId="20085";
				break;
				
			case 3:
				itemId="20027";
				break;

			default:
				itemId="20027";
				break;
			}
	        
	        //商品编号
	        map.put("itemId", itemId);
	        
	        //校验商品面值,单位厘,比如10元话费就传10000
	        map.put("checkItemFacePrice", "20000");
	        
	        //充值号码
	        map.put("uid",phone );
	        
	        String custom_number="S"+System.currentTimeMillis()+(long)(Math.random()*1000000L);
	        
	        //合作方商户系统的流水号
	        map.put("serialno", custom_number);
	        
	        String orderTime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());	        
	        
	        //交易时间,yyyyMMddHHmmss格式
	        map.put("dtCreate",orderTime );
	        
	        String md5Key="db3122af5bcd9ec7c4bfa302026b99d89f5f8af8b367cddf5ff04e9d65d26929";
	        
	        map.put("sign",EncryptUtils.encrypt(
	        		map.get("dtCreate") 
	        		+map.get("itemId")
	        		+map.get("serialno")
	        		+map.get("uid")
	        		+map.get("userId")
	        	    +md5Key));
	        
	        String url="http://47.93.197.171:8760/unicomAync/buy.do";
	        
	        String toUrl=url+"?sign="+map.get("sign")+"&uid="+map.get("uid")+"&dtCreate="+map.get("dtCreate")+"&userId="+map.get("userId")+"&itemId="+map.get("itemId")+"&serialno="+map.get("serialno");
	        
	    	/**
			 * 
			 * 下单保存
			 * 
			 * **/
			CompanyInfo company = CompanyInfo.dao.findFirst("select * from company_info where status = '正常'   and is_del = 'N' and appid = 1 ");
			
			if (company == null) {
				
				result(false, "商户不存在", null);
				
				return;
			}
			
			//获取商户当前通道费率
			String rebateSql=" SELECT child.rebate as rebate  \r\n" + 
					"FROM\r\n" + 
					"	company_channel AS channel\r\n" + 
					"	LEFT JOIN company_channel_child AS child ON child.chanid = channel.id\r\n" + 
					"	LEFT JOIN company_info AS info ON child.infoid = info.id \r\n" + 
					"WHERE\r\n" + 
					"	info.id = '"+company.getId()+"' \r\n" + 
					"	AND child.`status` = '正常' \r\n" + 
					"	AND CODE = '"+payType+"' 	";
			
			
			String isUse=Db.queryStr(rebateSql);
			
			if(isUse==null) {
				
				result(false, "通道被禁用", null);
				
				return;
				
			}
			
			String response = HttpClientHelper.sendGet(toUrl);
			
			System.out.println("返回的结果"+response);
			
			JSONObject model=JSONObject.parseObject(response);
			
			//受理成功
			if(model.getString("status").equals("success")) {
	
				CompanyOrder order = new CompanyOrder();
				
				order.setTitle("话费下单");
				
				order.setOrderNo(custom_number);
				
				order.setTradeNo(phone);
				
				order.setOutOrderNo("0000000");
				
				//下单的金额
				order.setOrderMoney(BigDecimal.valueOf(Double.valueOf(money)));
			
				//下游回调地址
				order.setNoticeUrl(nextUrl);
				
				//支付方式
				order.setPayType("hf");
				
				//充值状态
				order.setStatus("未充值");
				
				//回调状态
				order.setNotice("未回调");
				
				order.setAddtime(new Date());
				
				//商户费率  0.035
				BigDecimal companyRebate=null;
				
				companyRebate=new BigDecimal(isUse);
					
				//下单的金额
				BigDecimal  companyMoney=new BigDecimal(money);
		
				//折扣金额
				BigDecimal rebateMonay=companyRebate.multiply(companyMoney);
				
				System.out.println("收费为=======》"+rebateMonay);
				
				//sava服务费
				order.setCompanyRebateMoney(rebateMonay);
				
				//商户折扣
				order.setCompanyRebate(companyRebate);
				
				//商户名
				order.setCompanyName(company.getUsername());
				
				order.save();
				
				//商户余额减法操作
				BigDecimal balance=company.getBalance();
				
				BigDecimal newBalance=balance.subtract(rebateMonay);
				
				company.setBalance(newBalance);
				
				company.update();
				
				//result(true, "下单成功", apiordersn);
				
			}
		
	}
	/**
	 *  单个上传
	 * 通过浏览器插件系统上传pdd商品到pdd后台
	 * 
	 * **/
	
	public void upload() {
		//http://39.100.64.113/index/product/
		
		//http://47.56.224.219/index/product
		
		//用户id
		CompanyInfo company = CompanyInfo.dao.findById(ShiroUtils.getUserId());
		
		String httpUrl=company.getMd5key();
		
		//商品链接
		String goodsUrl=getPara("goodsurl");
		
		//pdd后台系统ip
		StringBuffer  ip=new StringBuffer();
		
		StringBuffer uploadMethod=ip.append(httpUrl);
		
    	String response = HttpsClientHelper.sendGet(goodsUrl);
    	
    	logger.info(response);
    	
    	Map<String, String> maps=new HashMap<String, String>();

    	maps.put("goods_url", goodsUrl);
    	
    	maps.put("html", response);
    	
    	String result=HttpClientHelper.sendPost(uploadMethod.toString(), maps);
    	
    	logger.info(result);
    	
    	String data=result;
    	
      if(result!=null) {
    	
    	  if(result.indexOf("您提供的地址")!=-1) {
    		
    		result(false, result, data);
    		
    		
    		}else {
    		result(true, result, data);
    	
    		
    	}
      }
      
     else {
    		  
    		  result(true, result, "系统异常");
    	
      }
		
	}
	
	/**
	 *  批量上传
	 * 通过浏览器插件系统上传pdd商品到pdd后台
	 * 
	 * **/
	
	public void uploadFech() {
		//http://39.100.64.113/index/product/
		
		//pdd后台系统ip
		StringBuffer  ip=new StringBuffer();
		
//		Integer id = ShiroUtils.getUserId();
//		
//		CompanyInfo company = CompanyInfo.dao.findFirst("select * from company_info where id = ? ", id);
		
		CompanyInfo company = CompanyInfo.dao.findById(ShiroUtils.getUserId());
		
		String httpUrl=company.getMd5key();
		
		StringBuffer uploadMethod=ip.append(httpUrl);
		
		//商品链接
		String goodsUrls=getPara("goodsurl");
		
		String [] goodsArray=goodsUrls.split("\n");
		
		//计数器
		int uploadNumber=goodsArray.length;
		
		int successNumber=0;

		for (int i = 0; i < goodsArray.length; i++) {
			
			String response = HttpsClientHelper.sendGet(goodsArray[i]);
			
			logger.info(response);
			
			Map<String, String> maps=new HashMap<String, String>();
			
			maps.put("goods_url", goodsArray[i]);
			
			maps.put("html", response);
			
			String result=HttpClientHelper.sendPost(uploadMethod.toString(), maps);
			
			logger.info(result);
			
			String data=result;
			
			if(result!=null) {
				
				
			
			
				if(result.indexOf("您提供的地址")==-1) {
				
				successNumber+=1;
				}
			
			}else {
				
			 result(false, "系统异常", "系统异常");
				
			}
		
		}
		
		String msg=String.valueOf(successNumber);
		
		result(true, msg, uploadNumber);
		
	}
	
    public void viewOrder() {
        CompanyApiOrder order = CompanyApiOrder.dao.findById(getParaToInt());
//        List<CompanyOrderSettle> settles = CompanyOrderSettle.dao.find("select * from company_order_settle where order_id = ?", order.getId());
//        List<CompanyBill> bills = CompanyBill.dao.find("select * from company_bill where order_id = ?", order.getId());
//        List<AlipayAgentBill> agentBills = AlipayAgentBill.dao.find("select * from alipay_agent_bill where order_id = ?", order.getId());
        setAttr("o", order);
//        setAttr("settles", settles);
//        setAttr("bills", bills);
//        setAttr("agentBills", agentBills);
    }

	
	
	
	
}
