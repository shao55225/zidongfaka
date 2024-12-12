package com.xunpay.money.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.AlipayAgent;
import com.xunpay.money.model.AlipayAgentDraw;
import com.xunpay.money.model.CompanyChannel;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.model.DrawItem;
import com.xunpay.money.utils.Constant;
import com.xunpay.money.utils.ShiroUtils;

@RequiresPermissions("通道管理")
public class ChannelController extends BaseController {

	public void listChannel() {
		String select = "select *";
		String except = "from company_channel where is_del = 'N' ";
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "account", "nickname", "username");
		except += getParaSql(args, "status", "status");
		except += " order by id desc";
		keepPara();
		setAttr("page", AlipayAgent.dao.paginate(getParaToInt("page", 1), 10, select, except, args.toArray()));
	}

	@RequiresPermissions("agent:W")
	public void addChannel() {}
	
	@RequiresPermissions("agent:W")
	public void editChannel() {
		setAttr("a", CompanyChannel.dao.findById(getParaToInt()));
	}
	
	@RequiresPermissions("agent:W")
	public void saveChannel() {
		
		CompanyChannel channel = getModel(CompanyChannel.class, "a");
		
		String code=channel.getCode();
		
		//查重
		CompanyChannel model=CompanyChannel.dao.findFirst("select * from company_channel  where   code='"+code+"'");
		
		if(model!=null){
				
			message(false, "通道代码重复", "/channel/listChannel");
			
		}else {
			
			channel.setAddtime(new Date());
			
			channel.save();
			
			message(true, "恭喜，通道添加成功！", "/channel/listChannel");
			
			//更新所有商户的通道列表
			List<CompanyInfo> companyinfo=CompanyInfo.dao.find("	select id   from company_info  where  is_del='N'  and  status='正常' ");
			
			//更新company_channel_child
			for (int i = 0; i < companyinfo.size(); i++) {
				
				String updateSql="	INSERT INTO `pay`.`company_channel_child`"
						
				+ " (`infoid`, `chanid`, `rebate`, `status`) VALUES ("+companyinfo.get(i).getId()+", '"+channel.getId()+"', '0', '正常')";
				
		        Db.update(updateSql);	
			}
		
		}
	}

	
	@RequiresPermissions("agent:W")
	public void updateChannel() {
		CompanyChannel channel = getModel(CompanyChannel.class, "a");
		
		channel.update();
		
		message(true, "恭喜，通道修改成功！", "/channel/listChannel");
		
	}
	
	@RequiresPermissions("agent:W")
	public void deleteChannel() {
		
	  int para=getParaToInt();
		
	  Db.update("update company_channel set is_del = 'Y' where id = ?", para);
		
	  //更新company_channel_child数据
	  Db.update("delete from company_channel_child where chanid='"+para+"' ");
		
	  message(true, "恭喜，通道删除成功！", "/channel/listChannel");
		
	}
	
	@RequiresPermissions("agent_draw")
	public void listDraw() {
		String addtime = getPara("addtime");
		String select = "select *";
		String except = "from alipay_agent_draw where 1=1 ";
		if (StringUtils.isNotEmpty(addtime)) {
			except += "date_format(addtime,'%Y-%m-%d') = '" + addtime + "'";
		}
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "account", "agent_name", "realname", "account");
		except += getParaSql(args, "status", "status");
		except += " order by id desc";
		keepPara();
		setAttr("sysUser", ShiroUtils.getUsername());
		setAttr("page", AlipayAgentDraw.dao.paginate(getParaToInt("page", 1), 10, select, except, args.toArray()));
	}
	

}
