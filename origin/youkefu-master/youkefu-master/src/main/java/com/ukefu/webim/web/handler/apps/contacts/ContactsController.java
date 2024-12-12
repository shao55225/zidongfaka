package com.ukefu.webim.web.handler.apps.contacts;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.util.Menu;
import com.ukefu.util.PinYinTools;
import com.ukefu.util.UKTools;
import com.ukefu.util.task.DSData;
import com.ukefu.util.task.DSDataEvent;
import com.ukefu.util.task.ExcelImportProecess;
import com.ukefu.util.task.export.ExcelExporterProcess;
import com.ukefu.util.task.process.ContactsProcess;
import com.ukefu.webim.service.es.ContactsRepository;
import com.ukefu.webim.service.repository.AgentUserContactsRepository;
import com.ukefu.webim.service.repository.MetadataRepository;
import com.ukefu.webim.service.repository.PropertiesEventRepository;
import com.ukefu.webim.service.repository.ReporterRepository;
import com.ukefu.webim.util.PropertiesEventUtils;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.AgentUserContacts;
import com.ukefu.webim.web.model.Contacts;
import com.ukefu.webim.web.model.MetadataTable;
import com.ukefu.webim.web.model.PropertiesEvent;

@Controller
@RequestMapping("/apps/contacts")
public class ContactsController extends Handler{
	
	@Autowired
	private ContactsRepository contactsRes ;
	
	@Autowired
	private PropertiesEventRepository propertiesEventRes ;
	
	@Autowired
	private ReporterRepository reporterRes ;
	
	@Autowired
	private MetadataRepository metadataRes ;
	
	@Autowired
	private AgentUserContactsRepository agentUserContactsRes; 
	
	@Value("${web.upload-path}")
    private String path;
	
    @RequestMapping("/index")
    @Menu(type = "customer" , subtype = "index")
    public ModelAndView index(ModelMap map , HttpServletRequest request , @Valid String q , @Valid String ckind) {
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    	if(!StringUtils.isBlank(q)){
        	map.put("q", q) ;
        }
    	if(!StringUtils.isBlank(ckind)){
    		boolQueryBuilder.must(termQuery("ckind" , ckind)) ;
        	map.put("ckind", ckind) ;
        }
    	map.addAttribute("contactsList", contactsRes.findByCreaterAndSharesAndOrgi(super.getUser(request).getId(), super.getUser(request).getId(),super.getOrgi(request), null , null , false, boolQueryBuilder ,q , new PageRequest(super.getP(request) , super.getPs(request)))) ;
    	
    	return request(super.createAppsTempletResponse("/apps/business/contacts/index"));
    }
    
    @RequestMapping("/today")
    @Menu(type = "customer" , subtype = "today")
    public ModelAndView today(ModelMap map , HttpServletRequest request , @Valid String q , @Valid String ckind) {
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    	if(!StringUtils.isBlank(q)){
        	map.put("q", q) ;
        }
    	if(!StringUtils.isBlank(ckind)){
    		boolQueryBuilder.must(termQuery("ckind" , ckind)) ;
        	map.put("ckind", ckind) ;
        }
    	map.addAttribute("contactsList", contactsRes.findByCreaterAndSharesAndOrgi(super.getUser(request).getId(), super.getUser(request).getId(), super.getOrgi(request),UKTools.getStartTime() , null , false, boolQueryBuilder ,q , new PageRequest(super.getP(request) , super.getPs(request)))) ;
    	
    	return request(super.createAppsTempletResponse("/apps/business/contacts/index"));
    }
    
    @RequestMapping("/week")
    @Menu(type = "customer" , subtype = "week")
    public ModelAndView week(ModelMap map , HttpServletRequest request , @Valid String q , @Valid String ckind) {
    	
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    	if(!StringUtils.isBlank(q)){
        	map.put("q", q) ;
        }
    	if(!StringUtils.isBlank(ckind)){
    		boolQueryBuilder.must(termQuery("ckind" , ckind)) ;
        	map.put("ckind", ckind) ;
        }
    	map.addAttribute("contactsList", contactsRes.findByCreaterAndSharesAndOrgi(super.getUser(request).getId(), super.getUser(request).getId(),super.getOrgi(request), UKTools.getWeekStartTime() , null , false, boolQueryBuilder ,q , new PageRequest(super.getP(request) , super.getPs(request)))) ;
    	
    	return request(super.createAppsTempletResponse("/apps/business/contacts/index"));
    }
    
    @RequestMapping("/creater")
    @Menu(type = "customer" , subtype = "creater")
    public ModelAndView creater(ModelMap map , HttpServletRequest request , @Valid String q , @Valid String ckind) {
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    	boolQueryBuilder.must(termQuery("creater" , super.getUser(request).getId())) ;
    	
    	if(!StringUtils.isBlank(ckind)){
    		boolQueryBuilder.must(termQuery("ckind" , ckind)) ;
        	map.put("ckind", ckind) ;
        }
    	if(!StringUtils.isBlank(q)){
        	map.put("q", q) ;
        }
    	
    	map.addAttribute("contactsList", contactsRes.findByCreaterAndSharesAndOrgi(super.getUser(request).getId(), super.getUser(request).getId(),super.getOrgi(request), null , null , false, boolQueryBuilder ,q , new PageRequest(super.getP(request) , super.getPs(request)))) ;
        return request(super.createAppsTempletResponse("/apps/business/contacts/index"));
    }
    
    @RequestMapping("/delete")
    @Menu(type = "contacts" , subtype = "contacts")
    public ModelAndView delete(HttpServletRequest request ,@Valid Contacts contacts ,@Valid String p) {
    	if(contacts!=null){
    		contacts = contactsRes.findOne(contacts.getId()) ;
    		contacts.setDatastatus(true);							//客户和联系人都是 逻辑删除
    		contactsRes.save(contacts) ;
    		List<AgentUserContacts> agentUserContactsList = agentUserContactsRes.findByContactsidAndOrgi(contacts.getId(), super.getOrgi(request)) ;
    		agentUserContactsRes.delete(agentUserContactsList);
    		
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/apps/contacts/index.html?p="+p+"&ckind="+contacts.getCkind()));
    }
    
    @RequestMapping("/add")
    @Menu(type = "contacts" , subtype = "add")
    public ModelAndView add(ModelMap map , HttpServletRequest request,@Valid String ckind) {
    	map.addAttribute("ckind",ckind);
        return request(super.createRequestPageTempletResponse("/apps/business/contacts/add"));
    }
    
    @RequestMapping(  "/save")
    @Menu(type = "contacts" , subtype = "save")
    public ModelAndView save(HttpServletRequest request  , @Valid Contacts contacts) {
		contacts.setCreater(super.getUser(request).getId());
		contacts.setOrgi(super.getOrgi(request));
		contacts.setOrgan(super.getUser(request).getOrgan());
		contacts.setPinyin(PinYinTools.getInstance().getFirstPinYin(contacts.getName()));
		if(StringUtils.isBlank(contacts.getCusbirthday())) {
			contacts.setCusbirthday(null);
		}else {
			contacts.setCusbirthday(contacts.getCusbirthday() + " 00:00:00");
		}
		contactsRes.save(contacts) ;
		return request(super.createRequestPageTempletResponse("redirect:/apps/contacts/index.html?ckind="+contacts.getCkind()));
    }
    
    @RequestMapping("/edit")
    @Menu(type = "contacts" , subtype = "contacts")
    public ModelAndView edit(ModelMap map , HttpServletRequest request , @Valid String id) {
    	map.addAttribute("contacts", contactsRes.findOne(id)) ;
        return request(super.createRequestPageTempletResponse("/apps/business/contacts/edit"));
    }
    
    @RequestMapping("/detail")
    @Menu(type = "contacts" , subtype = "contacts")
    public ModelAndView detail(ModelMap map , HttpServletRequest request , @Valid String id) {
    	map.addAttribute("contacts", contactsRes.findOne(id)) ;
        return request(super.createRequestPageTempletResponse("/apps/business/contacts/detail"));
    }
    
    
    @RequestMapping(  "/update")
    @Menu(type = "contacts" , subtype = "contacts")
    public ModelAndView update(HttpServletRequest request  , @Valid Contacts contacts) {
    	Contacts data = contactsRes.findOne(contacts.getId()) ;
    	if(data!=null){
	    	List<PropertiesEvent> events = PropertiesEventUtils.processPropertiesModify(request, contacts , data , "id" , "orgi" , "creater" ,"createtime" , "updatetime") ;	//记录 数据变更 历史
	    	if(events.size()>0){
	    		String modifyid = UKTools.getUUID() ;
	    		Date modifytime = new Date();
	    		for(PropertiesEvent event : events){
	    			event.setDataid(contacts.getId());
	    			event.setCreater(super.getUser(request).getId());
	    			event.setOrgi(super.getOrgi(request));
	    			event.setModifyid(modifyid);
	    			event.setCreatetime(modifytime);
	    			propertiesEventRes.save(event) ;
	    		}
	    	}
	    	
	    	contacts.setCreater(data.getCreater());
	    	contacts.setCreatetime(data.getCreatetime());
	    	contacts.setOrgi(super.getOrgi(request));
	    	contacts.setOrgan(super.getUser(request).getOrgan());
	    	contacts.setPinyin(PinYinTools.getInstance().getFirstPinYin(contacts.getName()));
	    	if(StringUtils.isBlank(contacts.getCusbirthday())) {
				contacts.setCusbirthday(null);
			}else {
				contacts.setCusbirthday(contacts.getCusbirthday() + " 00:00:00");
			}
	    	contactsRes.save(contacts);
    	}
    	
        return request(super.createRequestPageTempletResponse("redirect:/apps/contacts/index.html?ckind="+contacts.getCkind()));
    }
    
    @RequestMapping("/imp")
    @Menu(type = "contacts" , subtype = "contacts")
    public ModelAndView imp(ModelMap map , HttpServletRequest request,@Valid String ckind) {
    	map.addAttribute("ckind",ckind);
        return request(super.createRequestPageTempletResponse("/apps/business/contacts/imp"));
    }
    
    @RequestMapping("/impsave")
    @Menu(type = "contacts" , subtype = "contacts")
    public ModelAndView impsave(ModelMap map , HttpServletRequest request , @RequestParam(value = "cusfile", required = false) MultipartFile cusfile,@Valid String ckind) throws IOException {
    	DSDataEvent event = new DSDataEvent();
    	String fileName = "contacts/"+UKTools.getUUID()+cusfile.getOriginalFilename().substring(cusfile.getOriginalFilename().lastIndexOf(".")) ;
    	File excelFile = new File(path , fileName) ;
    	if(!excelFile.getParentFile().exists()){
    		excelFile.getParentFile().mkdirs() ;
    	}
    	MetadataTable table = metadataRes.findByTablename("uk_contacts") ;
    	if(table!=null){
	    	FileUtils.writeByteArrayToFile(new File(path , fileName), cusfile.getBytes());
	    	event.setDSData(new DSData(table,excelFile , cusfile.getContentType(), super.getUser(request)));
	    	event.getDSData().setClazz(Contacts.class);
	    	event.getDSData().setProcess(new ContactsProcess(contactsRes));
	    	event.setOrgi(super.getOrgi(request));
	    	/*if(!StringUtils.isBlank(ckind)){
	    		event.getValues().put("ckind", ckind) ;
	    	}*/
	    	event.getValues().put("creater", super.getUser(request).getId()) ;
	    	reporterRes.save(event.getDSData().getReport()) ;
	    	new ExcelImportProecess(event).process() ;		//启动导入任务
    	}
    	
    	return request(super.createRequestPageTempletResponse("redirect:/apps/contacts/index.html"));
    }
    
    @RequestMapping("/expids")
    @Menu(type = "contacts" , subtype = "contacts")
    public void expids(ModelMap map , HttpServletRequest request , HttpServletResponse response , @Valid String[] ids) throws IOException {
    	if(ids!=null && ids.length > 0){
    		Iterable<Contacts> contactsList = contactsRes.findAll(Arrays.asList(ids)) ;
    		MetadataTable table = metadataRes.findByTablename("uk_contacts") ;
    		List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
    		for(Contacts contacts : contactsList){
    			values.add(UKTools.transBean2Map(contacts)) ;
    		}
    		
    		response.setHeader("content-disposition", "attachment;filename=UCKeFu-Contacts-"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".xls");  
    		
    		ExcelExporterProcess excelProcess = new ExcelExporterProcess( values, table, response.getOutputStream()) ;
    		excelProcess.process();
    	}
    	
        return ;
    }
    
    @RequestMapping("/expall")
    @Menu(type = "contacts" , subtype = "contacts")
    public void expall(ModelMap map , HttpServletRequest request , HttpServletResponse response) throws IOException {
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    	boolQueryBuilder.must(termQuery("datastatus" , false)) ;		//只导出 数据删除状态 为 未删除的 数据
    	Iterable<Contacts> contactsList = contactsRes.findByCreaterAndSharesAndOrgi(super.getUser(request).getId(), super.getUser(request).getId(), super.getOrgi(request),null , null , false, boolQueryBuilder , null , new PageRequest(super.getP(request) , super.getPs(request)));
    	
    	MetadataTable table = metadataRes.findByTablename("uk_contacts") ;
		List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
		for(Contacts contacts : contactsList){
			values.add(UKTools.transBean2Map(contacts)) ;
		}
		
		response.setHeader("content-disposition", "attachment;filename=UCKeFu-Contacts-"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".xls");  
		
		ExcelExporterProcess excelProcess = new ExcelExporterProcess( values, table, response.getOutputStream()) ;
		excelProcess.process();
        return ;
    }
    
    @RequestMapping("/expsearch")
    @Menu(type = "contacts" , subtype = "contacts")
    public void expall(ModelMap map , HttpServletRequest request , HttpServletResponse response , @Valid String q , @Valid String ekind) throws IOException {
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    	if(!StringUtils.isBlank(q)){
        	map.put("q", q) ;
        }
    	if(!StringUtils.isBlank(ekind)){
    		boolQueryBuilder.must(termQuery("ekind" , ekind)) ;
        	map.put("ekind", ekind) ;
        }
    	
    	Iterable<Contacts> contactsList = contactsRes.findByCreaterAndSharesAndOrgi(super.getUser(request).getId(), super.getUser(request).getId(),super.getOrgi(request), null , null , false, boolQueryBuilder ,q , new PageRequest(super.getP(request) , super.getPs(request)));
    	MetadataTable table = metadataRes.findByTablename("uk_contacts") ;
    	List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
    	for(Contacts contacts : contactsList){
    		values.add(UKTools.transBean2Map(contacts)) ;
    	}

    	response.setHeader("content-disposition", "attachment;filename=UCKeFu-Contacts-"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".xls");  

    	ExcelExporterProcess excelProcess = new ExcelExporterProcess( values, table, response.getOutputStream()) ;
    	excelProcess.process();
    	
        return ;
    }
    
    
    @RequestMapping("/embed/index")
    @Menu(type = "customer" , subtype = "embed")
    public ModelAndView embed(ModelMap map , HttpServletRequest request , @Valid String q , @Valid String ckind, @Valid String ani) {
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    	if(!StringUtils.isBlank(q)){
        	map.put("q", q) ;
        }
    	if(!StringUtils.isBlank(ani)){
    		BoolQueryBuilder phoneBooleanQuery = QueryBuilders.boolQuery();
    		phoneBooleanQuery.should(termQuery("phone" , ani)) ;
    		phoneBooleanQuery.should(termQuery("mobilephone" , ani)) ;
    		boolQueryBuilder.must(phoneBooleanQuery) ;
        	map.put("ani", ani) ;
        }
    	map.addAttribute("contactsList", contactsRes.findByCreaterAndSharesAndOrgi(super.getUser(request).getId(), super.getUser(request).getId(), super.getOrgi(request),null , null , false, boolQueryBuilder ,q , new PageRequest(super.getP(request) , super.getPs(request)))) ;
    	
    	return request(super.createRequestPageTempletResponse("/apps/business/contacts/embed/index"));
    }
    
    @RequestMapping("/embed/add")
    @Menu(type = "contacts" , subtype = "embedadd")
    public ModelAndView embedadd(ModelMap map , HttpServletRequest request, @Valid String ani) {
    	if(!StringUtils.isBlank(ani)){
        	map.put("ani", ani) ;
        }
        return request(super.createRequestPageTempletResponse("/apps/business/contacts/embed/add"));
    }
    
    @RequestMapping(  "/embed/save")
    @Menu(type = "contacts" , subtype = "embedsave")
    public ModelAndView embedsave(HttpServletRequest request  , @Valid Contacts contacts, @Valid String ani) {
		contacts.setCreater(super.getUser(request).getId());
		contacts.setOrgi(super.getOrgi(request));
		contacts.setOrgan(super.getUser(request).getOrgan());
		contacts.setPinyin(PinYinTools.getInstance().getFirstPinYin(contacts.getName()));
		if(StringUtils.isBlank(contacts.getCusbirthday())) {
			contacts.setCusbirthday(null);
		}
		contactsRes.save(contacts) ;
        return request(super.createRequestPageTempletResponse("redirect:/apps/contacts/embed/index.html"+(!StringUtils.isBlank(ani) ? "?ani="+ani : null)));
    }
    
    @RequestMapping("/embed/edit")
    @Menu(type = "contacts" , subtype = "embededit")
    public ModelAndView embededit(ModelMap map , HttpServletRequest request , @Valid String id) {
    	map.addAttribute("contacts", contactsRes.findOne(id)) ;
        return request(super.createRequestPageTempletResponse("/apps/business/contacts/embed/edit"));
    }
    
    @RequestMapping(  "/embed/update")
    @Menu(type = "contacts" , subtype = "embedupdate")
    public ModelAndView embedupdate(HttpServletRequest request  , @Valid Contacts contacts) {
    	Contacts data = contactsRes.findOne(contacts.getId()) ;
    	if(data!=null){
	    	List<PropertiesEvent> events = PropertiesEventUtils.processPropertiesModify(request, contacts , data , "id" , "orgi" , "creater" ,"createtime" , "updatetime") ;	//记录 数据变更 历史
	    	if(events.size()>0){
	    		String modifyid = UKTools.getUUID() ;
	    		Date modifytime = new Date();
	    		for(PropertiesEvent event : events){
	    			event.setDataid(contacts.getId());
	    			event.setCreater(super.getUser(request).getId());
	    			event.setOrgi(super.getOrgi(request));
	    			event.setModifyid(modifyid);
	    			event.setCreatetime(modifytime);
	    			propertiesEventRes.save(event) ;
	    		}
	    	}
	    	
	    	contacts.setCreater(data.getCreater());
	    	contacts.setCreatetime(data.getCreatetime());
	    	contacts.setOrgi(super.getOrgi(request));
	    	contacts.setOrgan(super.getUser(request).getOrgan());
	    	contacts.setPinyin(PinYinTools.getInstance().getFirstPinYin(contacts.getName()));
	    	if(StringUtils.isBlank(contacts.getCusbirthday())) {
				contacts.setCusbirthday(null);
			}
	    	contactsRes.save(contacts);
    	}
    	
        return request(super.createRequestPageTempletResponse("redirect:/apps/contacts/embed/index.html"));
    }
    
    @RequestMapping("/updatemapping")
    @ResponseBody
    public void updatemapping(ModelMap map , HttpServletRequest request) {
    	contactsRes.updateMapping();
    }
    
    @RequestMapping("/batdel")
    @Menu(type = "contacts" , subtype = "contacts")
    public ModelAndView batdel(ModelMap map , HttpServletRequest request , HttpServletResponse response , @Valid String[] ids ,@Valid String ckind) throws IOException {
    	if(ids!=null && ids.length > 0){
    		Iterable<Contacts> contactsList = contactsRes.findAll(Arrays.asList(ids)) ;
    		for(Contacts contacts :contactsList) {
    			contacts.setDatastatus(true);	
    		}
    		this.contactsRes.save(contactsList);
    	}
    	
    	 return request(super.createRequestPageTempletResponse("redirect:/apps/contacts/index.html?ckind="+ckind));
    }
}