package com.xunpay.money.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import com.xunpay.money.core.BaseController;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;

/**
 * 图片验证码
 * 2016年7月18日
 */
public class VerifyCodeController extends BaseController {
	
	private static final String SUFFIX = "verifyCode_";
	private int width = 120;// 定义图片的width
	private int height = 37;// 定义图片的height
	private int codeCount = 4;// 定义图片上显示验证码的个数
	private int xx = 20;
	private int fontHeight = 30;
	private int codeY = 30;
	char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

	public void index() throws IOException {
		String key = getPara();
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics gd = buffImg.getGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);
		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
		// 设置字体。
		gd.setFont(font);
		// 画边框。
		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
		gd.setColor(Color.BLACK);
		for (int i = 0; i < 40; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;

		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String code = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);

			// 用随机产生的颜色将验证码绘制到图像中。
			gd.setColor(new Color(red, green, blue));
			gd.drawString(code, (i + 1) * xx, codeY);

			// 将产生的四个随机数组合在一起。
			randomCode.append(code);
		}
		// 将四位数字的验证码保存到Session中。
		setSessionAttr(SUFFIX + key, randomCode.toString());
		// 禁止图像缓存。
		getResponse().setHeader("Pragma", "no-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setDateHeader("Expires", 0);
		getResponse().setContentType("image/jpeg");
		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = getResponse().getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
		renderNull();
	}
	
	/**
	 * 验证 验证码是否正确
	 * @param c
	 * @param verifyCode 
	 * @param key
	 * @return true正确  false错误
	 */
	public static boolean check(Controller c, String verifyCode, String key) {
		String serverCode = c.getSessionAttr(SUFFIX + key);
		return StringUtils.isNotEmpty(verifyCode) && verifyCode.equalsIgnoreCase(serverCode);
	}

}
