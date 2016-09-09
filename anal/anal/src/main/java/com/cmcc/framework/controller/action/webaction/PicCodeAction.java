package com.cmcc.framework.controller.action.webaction;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cmcc.common.controller.action.BaseAction;

/**
 * 图形验证码Action
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-6
 */
public class PicCodeAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static int width = 60;

	private static int height = 20;

	public String execute() throws Exception {

		HttpSession session = this.getSession();
		HttpServletResponse response = this.getResponse();

		response.setContentType("image/jpeg");
		ServletOutputStream sos = response.getOutputStream();

		// 设置浏览器不要缓存此图片
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 创建内存图象并获得其图形上下文
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		// 产生随机的认证码
		char[] rands = generateCheckCode();

		// 产生图像
		drawBackground(g);
		drawRands(g, rands);

		// 结束图像的绘制过程，完成图像
		g.dispose();

		// 将图像输出到客户端
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(image, "JPEG", bos);
		byte[] buf = bos.toByteArray();
		response.setContentLength(buf.length);
		// 下面的语句也可写成：bos.writeTo(sos);
		// System.out.println(buf.toString());
		sos.write(buf);
		bos.close();
		sos.close();

		// 将当前验证码存入到Session中
		session.setAttribute("check_code", new String(rands));
		// 直接使用下面的代码将有问题，Session对象必须在提交响应前获得
		// request.getSession().setAttribute("check_code",new String(rands));
		return null;
	}

	private char[] generateCheckCode() {
		// 定义验证码的字符表
		String chars = "0123456789";
		char[] rands = new char[4];
		for (int i = 0; i < 4; i++) {
			int rand = (int) (Math.random() * 10);
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}

	private void drawRands(Graphics g, char[] rands) {
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));
		// 在不同的高度上输出验证码的每个字符
		g.drawString("" + rands[0], 1, 17);
		g.drawString("" + rands[1], 16, 15);
		g.drawString("" + rands[2], 31, 18);
		g.drawString("" + rands[3], 46, 16);
		// System.out.println(rands);
	}

	private void drawBackground(Graphics g) {
		// 画背景
		g.setColor(new Color(0xABDCEC));
		g.fillRect(0, 0, width, height);
		// 随机产生120个干扰点
		for (int i = 0; i < 10; i++) {
			int x = (int) (Math.random() * width);
			int y = (int) (Math.random() * height);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			g.setColor(new Color(red, green, blue));
			g.drawOval(x, y, 1, 0);
		}
	}
	// public String getYanZ() throws Exception{
	//		
	// this.getRequest().setCharacterEncoding("utf-8");
	// this.getResponse().setCharacterEncoding("utf-8");
	// this.getResponse().setContentType("text/xml");
	// PrintWriter out = this.getResponse().getWriter();
	// //String yanzheng = (String)
	// this.getRequest().getSession().getAttribute("check_code");
	// String yanzheng = (String) this.getSession().getAttribute("check_code");
	// out.print(yanzheng);
	// return null;
	// }

}
