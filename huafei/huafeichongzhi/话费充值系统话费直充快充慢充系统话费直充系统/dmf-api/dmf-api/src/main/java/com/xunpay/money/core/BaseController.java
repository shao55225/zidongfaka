package com.xunpay.money.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;
import com.xunpay.money.utils.Constant;

public class BaseController extends Controller {
	
	protected void renderSuccess(boolean success) {
		renderText(success ? Constant.SUCCESS : Constant.FAIL);
	}
	
	protected void result(boolean success, String message, Object data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", success ? Constant.SUCCESS : Constant.FAIL);
		result.put("msg", message);
		result.put("data", data == null ? new Object() : data);
		renderJson(result);
	}
	
	public String readAsString(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
	
	 protected void resultAgent(Map<String, Object> result){
	        renderJson(result);
	    }

}
