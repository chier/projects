package com.cmcc.anal.framework.controller.common;

import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmcc.anal.common.util.DateUtil;
import com.cmcc.anal.common.util.LogUtils;
import com.cmcc.anal.common.util.StringUtil;
import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.cmcc.anal.framework.controller.home.HomeController;
import com.cmcc.anal.framework.service.CaseInfoService;
import com.cmcc.anal.framework.service.CaseStatisticsService;
import com.cmcc.anal.framework.service.ComprehensiveService;
import com.cmcc.anal.framework.service.LoginService;
import com.cmcc.anal.framework.service.PollutantService;
import com.cmcc.anal.framework.service.SurveyDataService;
import com.talent.platform.core.json.JsonWrap;

/**
 * 请求的主体页
 * 
 * @filename: com.cmcc.anal.framework.controller.common.ReportController
 * @copyright: Copyright (c)2014
 * @company: 北京掌中无限信息技术有限公司
 * @author: 张占亮
 * @version: 1.0
 * @create time: 2014-7-6 下午4:43:30
 * @record <table cellPadding="3" cellSpacing="0" style="width:600px">
 *         <thead style="font-weight:bold;background-color:#e3e197">
 *         <tr>
 *         <td>date</td>
 *         <td>author</td>
 *         <td>version</td>
 *         <td>description</td>
 *         </tr>
 *         </thead> <tbody style="background-color:#ffffeb">
 *         <tr>
 *         <td>2014-7-6</td>
 *         <td>张占亮</td>
 *         <td>1.0</td>
 *         <td>create</td>
 *         </tr>
 *         </tbody>
 *         </table>
 */
@Controller
@RequestMapping("/controller/*")
public class ReportController {
	private static Logger logger = LoggerFactory
			.getLogger(ReportController.class);

	private static Logger jsonLog = LogUtils.getHttpJsonLog();

	// public static final String URL_ACCOUNT_LOGIN = "/login";

	public static final String URL_ACCOUNT_REPORT = "/report";

	@Resource(name = "loginService")
	private LoginService loginService;

	@Resource(name = "comprehensiveService")
	private ComprehensiveService comprehensiveService;

	@Resource(name = "surveyDataService")
	private SurveyDataService surveyDataService;

	@Resource(name = "caseStatisticsService")
	private CaseStatisticsService caseStatisticsService;

	@Resource(name = "caseInfoService")
	private CaseInfoService caseInfoService;

	@Resource(name = "pollutantService")
	private PollutantService pollutantService;

	@RequestMapping(value = URL_ACCOUNT_REPORT)
	public void report(@RequestBody String requestBody,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {

		JsonResponseVo jsonResponseVo = new JsonResponseVo();
		JsonRequestVo jsonRequestVo = null;
		try {
			String jsonStr = request.getParameter("jsonStr");

			// 返回业务响应
			if (StringUtil.isBlank(requestBody)) {
				jsonLog.debug("input:{}", jsonStr);
				requestBody = request.getParameter("tt_requestbody");
				if (StringUtil.isBlank(requestBody)
						&& StringUtil.isBlank(jsonStr)) {
					return;
				}
			} else {
				jsonLog.debug("input:{}", requestBody);
			}

			if (StringUtil.isBlank(requestBody) && !StringUtil.isBlank(jsonStr)) {
				requestBody = jsonStr;
				OtherController.other(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			}

			String callback = request.getParameter("callback");

			jsonRequestVo = JsonWrap.jsonStringToBean_Fastjson(requestBody,
					JsonRequestVo.class);

			// jsonResponseVo.setToken(jsonRequestVo.getToken());
			jsonResponseVo.setOp(jsonRequestVo.getOp());
			jsonResponseVo.setMsg("ok");
			// jsonResponseVo.setCallback(callback);

			if (jsonRequestVo.getOp().indexOf("System") != -1) {
				loginService.report(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			} else if ("Report.getTime".equalsIgnoreCase(jsonRequestVo.getOp())) {
				this.reportGetTime(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			} else if (jsonRequestVo.getOp().indexOf("Home") != -1) {
				HomeController.report(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			} else if (jsonRequestVo.getOp().indexOf("Comprehensive") != -1) {
				comprehensiveService.report(jsonRequestVo, jsonResponseVo,
						request, response, modelMap);
			} else if (jsonRequestVo.getOp().indexOf("SurveyData") != -1) {
				surveyDataService.report(jsonRequestVo, jsonResponseVo,
						request, response, modelMap);
			} else if (jsonRequestVo.getOp().indexOf("CaseStatistics") != -1) {
				caseStatisticsService.report(jsonRequestVo, jsonResponseVo,
						request, response, modelMap);
			} else if (jsonRequestVo.getOp().indexOf("CaseInfo") != -1) {
				caseInfoService.report(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			} else if (jsonRequestVo.getOp().indexOf("Pollutant") != -1) {
				pollutantService.report(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			} else {
				jsonResponseVo.setCode(-1);
				jsonResponseVo.setMsg("请求参数错误！");
				WebUtils.outputString(response,
						JsonWrap.beanToJsonString(jsonResponseVo), callback);
			}
		} catch (Exception e) {
			try {
				OtherController.other(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			} catch (Exception e1) {
				logger.error("", e1);
			}
			// logger.error("", e);
			// JsonResponseVo jsonResponseVo = new JsonResponseVo();
			// jsonResponseVo.setResult(-1);
			// jsonResponseVo.setResultdesc("系统异常，请联系管理员！");
			// try {
			// WebUtils.outputString(response,
			// JsonWrap.beanToJsonString(jsonResponseVo));
			// } catch (Exception e1) {
			// logger.error("", e1);
			// }
		}
	}

	@RequestMapping(value = "/showImage")
	public ModelAndView showImage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
//		String imageUrl = "E:\\data\\imagepage\\s001\\pages\\1236\\0039001.png";
		String imageUrl = request.getParameter("imageUrl");
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			response.setContentType("image/*");
//			String fname = request.getParameter("filename");
//			String newpath = new String(fname.getBytes("ISO-8859-1"), "UTF-8");
			
			File file = new File(imageUrl);
			if(file.isFile()){
				System.out.print("true");
			}
			FileInputStream fis = new FileInputStream(file);
//			byte[]	bytes = IOUtils.toByteArray(fis);
////			ImageInputStream iis = ImageIO.createImageInputStream(file);
////			Bitmap rawBitmap2 = BitmapFactory.decodeStream(iis);   
//			BufferedImage image = null;
//			BufferedImage image = ImageIO.read(file);
//			OutputStream os = response.getOutputStream();
			BufferedOutputStream bos = null;
			try {
//				ImageIO.write(image, "jpg", os);
				
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = fis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bos != null)
					bos.close();
				if (fis != null)
					fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 当 op == Report.getTime
	 * 
	 * @param jsonRequestVo
	 * @param jsonResponseVo
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	private void reportGetTime(JsonRequestVo jsonRequestVo,
			JsonResponseVo jsonResponseVo, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String callback = request.getParameter("callback");
		try {
			jsonResponseVo.setCode(0);
			jsonResponseVo.setMsg("服务器端的时间！");

			String currentDate = DateUtil.getSystemCurrentDateTime();
			Map map = new HashMap();
			map.put("time", currentDate);
			jsonResponseVo.setData(map);
			WebUtils.outputString(response,
					JsonWrap.beanToJsonString(jsonResponseVo), callback);
		} catch (Exception e) {
			logger.error("", e);
			jsonResponseVo.setCode(1);
			jsonResponseVo.setMsg("系统异常，请联系管理员！");
			WebUtils.outputString(response,
					JsonWrap.beanToJsonString(jsonResponseVo), callback);
		}
	}

	 private static final int width = 778; // 此处设定不同的分辨率  
	 private static final int height = 443;  
	 private static final int numBands = 3;
    byte[] mPixel = new byte[width * height * numBands];

    private static final int[] bandOffsets = new int[] { 0, 1, 2 };
    private static final SampleModel sampleModel = new PixelInterleavedSampleModel(
            DataBuffer.TYPE_BYTE, width, height, 3, width * 3, bandOffsets);
    // ColorModel
    private static final ColorSpace cs = ColorSpace
            .getInstance(ColorSpace.CS_sRGB);
    private static final ComponentColorModel cm = new ComponentColorModel(cs,
            false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

    private BufferedImage im;

//绘制 

//    public void paint(Graphics g) {
//        im = getImageFromByteArray(mPixel);
//        g.drawImage(im, 0, 0, 352, 288, this);
//    }

    private BufferedImage getImageFromByteArray(byte[] byteArray) {

        DataBuffer dataBuffer = new DataBufferByte(byteArray, numBands);
        WritableRaster wr = Raster.createWritableRaster(sampleModel,
                dataBuffer, new Point(0, 0));
        BufferedImage tmp = new BufferedImage(cm, wr, false, null);
        
        return tmp;
    }
}
