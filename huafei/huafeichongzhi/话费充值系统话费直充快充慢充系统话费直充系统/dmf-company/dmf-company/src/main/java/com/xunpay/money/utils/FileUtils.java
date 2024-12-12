package com.xunpay.money.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;

import com.aliyun.oss.OSSClient;
import com.jfinal.upload.UploadFile;

/**
 * 文件处理
 * 
 */
public class FileUtils {

	private static final String IMGURL = "http://image.114sl.cn/";
	
	public static String upload(UploadFile uploadFile, String fname) {
		if (uploadFile == null) {
			return null;
		}
		return upload(uploadFile.getFile(), fname);
	}
	
	public static String upload(UploadFile uploadFile) {
		if (uploadFile == null) {
			return null;
		}
		return upload(uploadFile.getFile(), null);
	}
	
	public static String upload(File file) {
		return upload(file, null);
	}
	
	public static String upload(File file, String fname) {
		if (file == null) {
			return null;
		}
		// 初始化OSS文件服务
		//String endpoint = "oss-cn-shenzhen-internal.aliyuncs.com";
		String endpoint = "oss-cn-shenzhen.aliyuncs.com";
		String accessKeyId = "vxznItoOZZWQoMde";
		String secretAccessKey = "U8khhGkBCcEeHRBlTdxKPpBiPkwmNv";
		OSSClient client = new OSSClient(endpoint, accessKeyId, secretAccessKey);
		
		if (StringUtils.isEmpty(fname)) {
			String fileName = DateUtils.get(new Date(), "yyyyMMdd") + "/" + DateUtils.get(new Date(), "HHmmssSSS") + RandomUtils.randomString(8);
			fname = "g/" + fileName;
		} else if (!fname.startsWith("g/")){
			fname = "g/" + fname;
		}
		client.putObject("114mm", fname, file);
		client.shutdown();
		if (file.exists()) {
			file.delete();
		}
		return IMGURL + fname;
	}
	
	public static String uploadBase64(String str) {
		return uploadBase64(str, null);
	}
	
	public static String uploadBase64(String str, String fname) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		if (str.startsWith("data:image")) {
			str = str.substring(str.indexOf(",") + 1);
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(str);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			File file = new File(UUID.randomUUID().toString());
			OutputStream out = new FileOutputStream(file);
			out.write(b);
			out.flush();
			out.close();
			String url = upload(file, fname);
			file.delete();
			return url;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
