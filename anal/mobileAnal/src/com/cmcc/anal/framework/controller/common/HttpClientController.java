/**
 * 
 */
package com.cmcc.anal.framework.controller.common;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmcc.anal.common.util.HttpClientUtils;
import com.cmcc.anal.common.util.WebUtils;
/**
 * 
 *  http请求的业务类 拦截 /common/httpclient/的请求 再返回到相对应的页面 页面存储位置在 /WEB-INF/views
 * @filename:	 com.cmcc.anal.framework.controller.common.HttpClientController
 * @copyright:   Copyright (c)2014
 * @company:     北京掌中无限信息技术有限公司
 * @author:      张占亮
 * @version:     1.0
 * @create time: 2014-7-6 上午12:19:36
 * @record
 * <table cellPadding="3" cellSpacing="0" style="width:600px">
 * <thead style="font-weight:bold;background-color:#e3e197">
 * 	<tr>   <td>date</td>	<td>author</td>		<td>version</td>	<td>description</td></tr>
 * </thead>
 * <tbody style="background-color:#ffffeb">
 * 	<tr><td>2014-7-6</td>	<td>张占亮</td>	<td>1.0</td>	<td>create</td></tr>
 * </tbody>
 * </table>
 */
@Controller
@RequestMapping("/common/httpclient/*")
public class HttpClientController
{
    private static Logger logger = LoggerFactory.getLogger(HttpClientController.class);

    @RequestMapping(value = "/init")
    public String init(String suffix)
    {
        String path = "common/httpclient/httpclientInit";
    	if (suffix != null) {
    		path += "_"+suffix;
        }
    	return path;
    }

    /**
     * 
     * @param tt_request_url
     * @param request
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/getCookie")
    public static void getCookie(String proxyHostName, int proxyPort, String proxySchemeName, String tt_request_url, String[] cookies, HttpServletRequest request,
            HttpServletResponse httpServletResponse, String tt_method, String tt_requestbody) throws Exception
    {
        HttpHost proxy = null;
        if (!StringUtils.isBlank(proxyHostName)) {
            proxy = new HttpHost(proxyHostName, proxyPort, proxySchemeName);
        }
        
        Map<String, Object> requestHeaders = new HashMap<String, Object>();
        appendParams(request, requestHeaders, "header_name", "header_value");
        
        try
        {
            if (cookies != null && cookies.length > 0)
            {
                for (String cookie : cookies)
                {
                    if (cookie != null && !"".equals(cookie))
                    {
                        requestHeaders.put("Cookie", cookie);
                    }
                }
            }

            Map<String, Object> params = new HashMap<String, Object>();
            WebUtils.fillParams(request, params);
            appendParams(request, params, "param_name", "param_value");
            List<Header> cookieHeaders = HttpClientUtils.getCookieHeaders(params, tt_request_url,
                    HttpClientUtils.mapToHeaders(requestHeaders), tt_method, proxy);

            String[] _cookies = cookiesToStrings(cookieHeaders);

            WebUtils.outputSuccessJsonResponseVo(httpServletResponse, "操作成功", _cookies);
        } catch (Exception e)
        {
            logger.error("", e);
            WebUtils.outputExceptionJsonResponseVo(httpServletResponse, e);
        }
    }


    /**
     * 
     * @param proxyHostName
     * @param proxyPort
     * @param proxySchemeName
     * @param tt_request_url
     * @param request
     * @param cookies
     * @param tt_method  get,post,put and so on
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/getHtml")
    public static void getHtml(String proxyHostName, int proxyPort, String proxySchemeName, String tt_request_url, HttpServletRequest request, String[] cookies, String tt_method,
            HttpServletResponse httpServletResponse, String tt_requestbody) throws Exception
    {
        HttpHost proxy = null;
        if (!StringUtils.isBlank(proxyHostName)) {
            proxy = new HttpHost(proxyHostName, proxyPort, proxySchemeName);
        }
        
        Map<String, Object> params = new HashMap<String, Object>();
        WebUtils.fillParams(request, params);

        appendParams(request, params, "param_name", "param_value");

        Map<String, Object> requestHeaders = new HashMap<String, Object>();
        appendParams(request, requestHeaders, "header_name", "header_value");
        try
        {
            if (cookies != null && cookies.length > 0)
            {
                for (String cookie : cookies)
                {
                    if (cookie != null && !"".equals(cookie))
                    {
                        requestHeaders.put("Cookie", cookie);
                    }
                }
            }

            while (true)
            {
                HttpResponse response = null;

                if ("get".equalsIgnoreCase(tt_method))
                {
                    List<Header> headers = HttpClientUtils.mapToHeaders(requestHeaders);
                    response = HttpClientUtils.get(tt_request_url, null, headers, proxy);
                } else if ("post".equalsIgnoreCase(tt_method))
                {
                    if (!StringUtils.isBlank(tt_requestbody)) {  //有请求体
                    	response = HttpClientUtils.post(tt_request_url, tt_requestbody, requestHeaders, proxy);
                    }else {
                    	response = HttpClientUtils.post(tt_request_url, params, requestHeaders, proxy);
                    }
                } else {
                	throw new RuntimeException("only post and get are supported.");
                }

                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == 302) //重定向
                {
                    Header[] locationHeader = response.getHeaders("Location");
                    tt_request_url = locationHeader[0].getValue();

                    Header[] setCookieHeaders = response.getHeaders("Set-Cookie");
                    HttpClientUtils.headersToMap(HttpClientUtils.convertSetCookieToCookie(setCookieHeaders), params);

                    continue;
                } else
                {
                    String responseHtml = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
                    WebUtils.outputSuccessJsonResponseVo(httpServletResponse, "操作成功", responseHtml);
                    break;
                }
            }
        } catch (Exception e)
        {
            //            WebUtils.outputException(httpServletResponse, e);
            logger.error("", e);
            WebUtils.outputExceptionJsonResponseVo(httpServletResponse, e);
        }
    }

   /**
    * 将HttpServletRequest中{name4Name}和{name4Value}组装成相应的请求参数：params.put({name4Name}[i], {name4Value}[i]);
    * @param request
    * @param params
    * @param name4Name
    * @param name4Value
    */
    public static void appendParams(HttpServletRequest request, Map<String, Object> params, String name4Name, String name4Value)
    {
        String[] paramNames = request.getParameterValues(name4Name);
        String[] paramValues = request.getParameterValues(name4Value);
        if (paramNames != null)
        {
            for (int i = 0; i < paramNames.length; i++)
            {
                if (!StringUtils.isBlank(paramNames[i]))
                {
                    params.put(paramNames[i], paramValues[i]);
                }
            }
        }
    }

    /**
     * 
     * @param cookieHeaders
     * @return
     */
    public static String[] cookiesToStrings(List<Header> cookieHeaders)
    {
        String[] cookies = null;
        if (cookieHeaders != null && cookieHeaders.size() > 0)
        {
            cookies = new String[cookieHeaders.size()];
            int i = 0;
            for (Header header : cookieHeaders)
            {
                cookies[i++] = header.getValue();
            }
        }
        return cookies;
    }

    /**
     * 
     */
    public HttpClientController()
    {

    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {

    }
}
