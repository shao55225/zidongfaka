package com.xunpay.money.service;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class KuaizhanService {

	public String createOrder(CompanyOrder order) {
		String order_id = order.getOrderNo();
		int money = (int) (order.getOrderMoney().doubleValue() * 100);
		String mch = order.getPid();
		String key = order.getRsaPrivate();
		String return_url = "http://47.92.253.105/success.jsp";
		String notify_url = "http://47.92.253.105/notice/kuaizhan";
		long time = System.currentTimeMillis();
		String pay_type = order.getRsaAlipay();
		String url = order.getAppid();
		url += "?mch=" + mch;
		url += "&money=" + money;
		url += "&time=" + time;
		url += "&sign=" + EncryptUtils.encrypt(order_id + money + pay_type + time + mch + EncryptUtils.encrypt(key));
		url += "&order_id=" + order_id;
		url += "&return_url=" + return_url;
		url += "&notify_url=" + notify_url;
		url += "&pay_type=" + pay_type;
		String result = HttpClientHelper.sendGet(url);
		
		JSONObject obj = JSONObject.fromObject(result);
		if (obj.containsKey("ok") && obj.getBoolean("ok")) {
			if ("aliqrcode".equals(pay_type)) {
				MultiFormatReader formatReader = new MultiFormatReader();   
				try {
					BufferedImage image = ImageIO.read(new URL(obj.getString("data")));
					LuminanceSource source = new BufferedImageLuminanceSource(image);   
			        Binarizer  binarizer = new HybridBinarizer(source);   
			        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);   
			        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();      
			        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");      
			        Result qrResult = formatReader.decode(binaryBitmap,hints); 
			        return qrResult.getText();
				} catch (Exception e) {
					e.printStackTrace();
					return obj.getString("data");
				}
			} else {
				return obj.getString("data");
			}
		}
		return null;
	}
	
}
