package com.xunpay.money.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class MessageUtils {
	
	private static final String[] RULES = "[],【】".split(",");
	
	public static List<String> message(String msg) {
		List<String> datas = new ArrayList<String>();
		for (String rule : RULES) {
			String begin = rule.substring(0, 1);
			String end = rule.substring(1, 2);
			if (msg.indexOf(begin) >= 0 && msg.indexOf(end) > 0) {
				StringTokenizer st = new StringTokenizer(msg, begin);
				while (st.hasMoreElements()) {
					String m = st.nextToken();
					if (m.indexOf(end) >= 0) {
						m = m.substring(0, m.indexOf(end));
						if (StringUtils.isNotEmpty(m)) {
							datas.add("%" + m + "%");
						}
					}
				}
			}
		}
		return datas;
	}
	
}
