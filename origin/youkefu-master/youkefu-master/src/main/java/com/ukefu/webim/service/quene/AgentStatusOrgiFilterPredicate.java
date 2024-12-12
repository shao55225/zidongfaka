package com.ukefu.webim.service.quene;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hazelcast.query.Predicate;
import com.ukefu.webim.web.model.AgentStatus;

public class AgentStatusOrgiFilterPredicate implements Predicate<String, AgentStatus> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1236581634096258855L;
	private String orgi ;
	/**
	 * 
	 */
	public AgentStatusOrgiFilterPredicate(String orgi){
		this.orgi = orgi ;
	}
	
	public boolean apply(Map.Entry<String, AgentStatus> mapEntry) {
		return mapEntry.getValue()!=null && !StringUtils.isBlank(orgi) && orgi.equals(mapEntry.getValue().getOrgi());
	}
}