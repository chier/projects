/**
 * 
 */
package com.cmcc.anal.common.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import net.sf.json.JSONObject;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.talent.platform.core.json.JsonWrap;

/**
 * @author 谭耀武
 * @date 2012-2-8
 * 
 */
public class WebUtils extends org.springframework.web.util.WebUtils {

	private static Logger log = LoggerFactory.getLogger(WebUtils.class);

	private static Logger httpXmlLog = LogUtils.getHttpXmlLog();

	private static Logger jsonLog = LogUtils.getHttpJsonLog();

	/**
	 * Try to resolve the message. Return default message if no message was
	 * found.
	 * 
	 * @param code
	 *            the code to lookup up, such as 'calculator.noRateSet'. Users
	 *            of this class are encouraged to base message names on the
	 *            relevant fully qualified class name, thus avoiding conflict
	 *            and ensuring maximum clarity.
	 * @param args
	 *            array of arguments that will be filled in for params within
	 *            the message (params look like "{0}", "{1,date}", "{2,time}"
	 *            within a message), or <code>null</code> if none.
	 * @param defaultMessage
	 *            String to return if the lookup fails
	 * @return the resolved message if the lookup was successful; otherwise the
	 *         default message passed as a parameter
	 * @see java.text.MessageFormat
	 */
	public static String getI18NMessage(String code, Object[] args,
			String defaultMessage) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return WebSpringUtils.getApplicationContext().getMessage(code, args,
				defaultMessage, request.getLocale());
	}

	/**
	 * Try to resolve the message. Treat as an error if the message can't be
	 * found.
	 * 
	 * @param code
	 *            the code to lookup up, such as 'calculator.noRateSet'
	 * @param args
	 *            Array of arguments that will be filled in for params within
	 *            the message (params look like "{0}", "{1,date}", "{2,time}"
	 *            within a message), or <code>null</code> if none.
	 * @return the resolved message
	 * @throws NoSuchMessageException
	 *             if the message wasn't found
	 * @see java.text.MessageFormat
	 */
	public static String getI18NMessage(String code, Object[] args)
			throws NoSuchMessageException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return WebSpringUtils.getApplicationContext().getMessage(code, args,
				request.getLocale());
	}

	/**
	 * 
	 * @param response
	 * @param str
	 * @throws Exception
	 */
	public static void outputString(HttpServletResponse response, String str,
			String callback) throws Exception {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			if (str == null) {
				out.write("");
			} else {
				if (!StringUtil.isBlank(callback)) {
//					str.replaceAll("\"", "\\\"");
					out.write(callback + '(' + str + ')');
				} else {
					out.write(str);
				}
				log.debug("output:{}", str);
				httpXmlLog.debug("output:{}", str);
				jsonLog.debug("output:{}", str);
			}
		} catch (IOException ex1) {
			throw ex1;
		} finally {
			out.close();
		}
	}

	/**
	 * 
	 * @param response
	 * @param str
	 * @throws Exception
	 */
	public static void outputString(HttpServletResponse response, String str)
			throws Exception {
		outputString(response, str, null);
	}

	/**
	 * 输出JsonResponseVo对象的json串
	 * 
	 * @param response
	 * @param result
	 *            0表示成功, 1表示异常
	 * @param errorCode
	 * @param resultdesc
	 * @param data
	 *            业务数据
	 * @throws Exception
	 */
	public static void outputJsonResponseVo(HttpServletResponse response,
			String op, int code, String msg, Object data) throws Exception {
		outputString(response, JsonWrap.beanToJsonString(new JsonResponseVo(op,
				code, msg, data)));
	}
	
	/**
	 * 输出JsonResponseVo对象的json串
	 * 
	 * @param response
	 * @param result
	 *            0表示成功, 1表示异常
	 * @param errorCode
	 * @param resultdesc
	 * @param data
	 *            业务数据
	 * @throws Exception
	 */
	public static void outputJsonResponseVo(HttpServletResponse response,
			String op, int code, String msg, Object data,String callback) throws Exception {
		outputString(response, JsonWrap.beanToJsonString(new JsonResponseVo(op,
				code, msg, data)),callback);
	}
	
	/**
	 * 输出JsonResponseVo对象的json串
	 * 
	 * @param response
	 * @param result
	 *            0表示成功, 1表示异常
	 * @param errorCode
	 * @param resultdesc
	 * @param data
	 *            业务数据
	 * @throws Exception
	 */
	public static void outputJsonResponseVo(HttpServletResponse response,
			JsonResponseVo jsonResponseVo,String callback) throws Exception {
		outputString(response, JSONObject.fromObject(jsonResponseVo).toString(),callback);
	}
	
	/**
	 * 输出JsonResponseVo对象的json串
	 * 
	 * @param response
	 * @param result
	 *            0表示成功, 1表示异常
	 * @param errorCode
	 * @param resultdesc
	 * @param data
	 *            业务数据
	 * @throws Exception
	 */
	public static void outputJsonResponseVo(HttpServletResponse response,
			int code, String msg, Object data) throws Exception {
		outputString(response, JsonWrap.beanToJsonString(new JsonResponseVo(
				code, msg, data)));
	}

	/**
	 * 输出JsonResponseVo对象的json串
	 * 
	 * @param response
	 * @param result
	 *            0表示成功, 1表示异常
	 * @param resultdesc
	 * @throws Exception
	 */
	public static void outputJsonResponseVo(HttpServletResponse response,
			int result, String resultdesc) throws Exception {
		outputJsonResponseVo(response, result, resultdesc, null);
	}

	/**
	 * 
	 * @param response
	 * @param errorCode
	 * @param resultdesc
	 * @throws Exception
	 */
	public static void outputSuccessJsonResponseVo(
			HttpServletResponse response, String resultdesc) throws Exception {
		outputSuccessJsonResponseVo(response, resultdesc, null);
	}

	/**
	 * 
	 * @param response
	 * @param errorCode
	 * @param resultdesc
	 * @param data
	 * @throws Exception
	 */
	public static void outputSuccessJsonResponseVo(
			HttpServletResponse response, String resultdesc, Object data)
			throws Exception {
		outputJsonResponseVo(response, 0, resultdesc, data);
	}

	/**
	 * 
	 * @param response
	 * @param errorCode
	 * @param resultdesc
	 * @throws Exception
	 */
	public static void outputFailJsonResponseVo(HttpServletResponse response,
			String resultdesc) throws Exception {
		outputFailJsonResponseVo(response, resultdesc, null);
	}

	/**
	 * 
	 * @param response
	 * @param errorCode
	 * @param resultdesc
	 * @param data
	 * @throws Exception
	 */
	public static void outputFailJsonResponseVo(HttpServletResponse response,
			String resultdesc, Object data) throws Exception {
		outputJsonResponseVo(response, 1, resultdesc, data);
	}

	/**
	 * @author tanyaowu
	 * @param args
	 */
	public static void main(String[] args) {
		String xx = WebSpringUtils.getApplicationContext().getMessage(
				"talent.oper.success", null, "hh", Locale.ENGLISH);// WebUtils.getI18NMessage("talent.oper.success",
																	// null,
																	// "oper
																	// success");
		System.out.println(xx);
	}

	/**
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> fillParams(HttpServletRequest request,
			Map<String, Object> map) {
		Map<String, Object> ret = map;
		if (ret == null) {
			ret = new HashMap<String, Object>();
		}
		Map<String, String[]> map1 = request.getParameterMap();
		Set<Entry<String, String[]>> entrySet = map1.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			if (entry.getValue() != null && entry.getValue().length > 0) {
				ret.put(entry.getKey(), entry.getValue()[0]);
			}
		}
		return ret;
	}

	/**
	 * 根据异常输出JsonResponseVo对象
	 * 
	 * @param response
	 * @param e
	 * @throws Exception
	 */
	public static void outputExceptionJsonResponseVo(
			HttpServletResponse response, Exception e) throws Exception {
		if (e instanceof DuplicateKeyException) {// talent.oper.fail
													// talent.record.has.exists
			WebUtils.outputFailJsonResponseVo(response, getI18NMessage(
					"talent.oper.fail", null, "oper fail"), getI18NMessage(
					"talent.record.has.exists", null,
					"record is already exists"));
		} else {
			WebUtils.outputFailJsonResponseVo(response, getI18NMessage(
					"talent.oper.fail", null, "oper fail"), e.toString());
		}
	}

	/**
	 * 
	 * @param req
	 * @return
	 */
	public static boolean isGzipSupport(HttpServletRequest req) {
		String headEncoding = req.getHeader("Accept-Encoding");
		if (headEncoding == null || (headEncoding.indexOf("gzip") == -1)) { // 客户端
																			// 不支持
																			// gzip
			return false;
		} else { // 支持 gzip 压缩
			return true;
		}
	}

	/**
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	public static PrintWriter createGzipPw(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		PrintWriter pw = null;
		if (isGzipSupport(req)) { // 支持 gzip 压缩
			pw = new PrintWriter(new GZIPOutputStream(resp.getOutputStream()));
			// 在 header 中设置返回类型为 gzip
			resp.setHeader("Content-Encoding", "gzip");
		} else { // // 客户端 不支持 gzip
			pw = resp.getWriter();
		}
		return pw;
	}

	/**
	 * 是否是ajax请求
	 * 
	 * @param request
	 * @return true: 是ajax请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith)
				: false;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String readRequestBody(ServletRequest request)
			throws UnsupportedEncodingException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String charset = request.getCharacterEncoding();
		if (charset == null) {
			charset = "utf-8";
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(request
				.getInputStream(), charset));

		CharArrayWriter data = new CharArrayWriter();
		char[] buf = new char[8192];
		int ret;
		while ((ret = in.read(buf, 0, 8192)) != -1) {
			data.write(buf, 0, ret);
		}

		String requestBody = data.toString();

		return requestBody;
	}

	/**
	 * 
	 */
	public WebUtils() {

	}
}
