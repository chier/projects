/**
 * 
 */
package com.cmcc.anal.common.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @filename:	 com.talent.platform.util.HttpClientUtils
 * @copyright:   Copyright (c)2010
 * @company:     talent
 * @author:      谭耀武
 * @version:     1.0
 * @create time: 2012-5-31 上午9:26:39
 * @record
 * <table cellPadding="3" cellSpacing="0" style="width:600px">
 * <thead style="font-weight:bold;background-color:#e3e197">
 * 	<tr>   <td>date</td>	<td>author</td>		<td>version</td>	<td>description</td></tr>
 * </thead>
 * <tbody style="background-color:#ffffeb">
 * 	<tr><td>2012-5-31</td>	<td>谭耀武</td>	<td>1.0</td>	<td>create</td></tr>
 * </tbody>
 * </table>
 */
@SuppressWarnings("deprecation")
public class HttpClientUtils {
	//    private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

	private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

	public static final String encoding = "utf-8";

	private static AtomicLong requestSeq = new AtomicLong();

	/**
	 * 
	 */
	public HttpClientUtils() {

	}

	/**
	 * 
	 * @param url
	 * @param cookies
	 * @param headers
	 * @param proxy
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse get(String url, List<Cookie> cookies, List<Header> headers, HttpHost proxy)
			throws ClientProtocolException, IOException {
		return httpRequest(url, null, cookies, headers, proxy, "get");
	}

	/**
	 * 
	 * @param url
	 * @param requestParams
	 * @param requestHeaders
	 * @param proxy
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse post(String url, Map<String, Object> requestParams, Map<String, Object> requestHeaders,
			HttpHost proxy) throws ClientProtocolException, IOException {
		List<Header> headers = mapToHeaders(requestHeaders);
		return post(url, requestParams, null, headers, proxy);
	}

	/**
	 * 
	 * @param url
	 * @param requestBody
	 * @param requestHeaders
	 * @param proxy
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse post(String url, String requestBody, Map<String, Object> requestHeaders, HttpHost proxy)
			throws ClientProtocolException, IOException {
		List<Header> headers = mapToHeaders(requestHeaders);
		return httpRequest(url, null, null, headers, proxy, "post", requestBody);
	}

	/**
	 * 
	 * @param requestHeaders
	 * @return
	 */
	public static List<Header> mapToHeaders(Map<String, Object> requestHeaders) {
		List<Header> headers = null;
		if (requestHeaders != null && requestHeaders.size() > 0) {
			headers = new ArrayList<Header>();
			Set<Entry<String, Object>> entrySet = requestHeaders.entrySet();

			for (Entry<String, Object> entry : entrySet) {
				headers.add(new BasicHeader(entry.getKey(), (String) entry.getValue()));
			}
		}
		return headers;
	}

	public static void headersToMap(List<Header> headers, Map<String, Object> map) {
		if (headers != null) {
			for (Header header : headers) {
				map.put(header.getName(), header.getValue());
			}
		}
	}

	/**
	 * 获取cookie
	 * @param requestParams
	 * @param url
	 * @param headers
	 * @param method
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	public static List<Header> getCookieHeaders(Map<String, Object> requestParams, String url, List<Header> headers,
			String method, HttpHost proxy) throws Exception {
		HttpResponse response = null;
		List<Header> cookieHeaders = new ArrayList<Header>();
		if (headers == null) {
			headers = new ArrayList<Header>();
		}

		while (true) {
			if ("get".equalsIgnoreCase(method)) {
				response = HttpClientUtils.get(url, null, headers, proxy);
			} else {
				response = HttpClientUtils.post(url, requestParams, null, headers, proxy);
			}

			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 302) //重定向
			{
				Header[] locationHeader = response.getHeaders("Location");
				url = locationHeader[0].getValue();

				//                for (int i = 0; i < headers.size(); i++) {
				//                    if ("Cookie".equalsIgnoreCase(headers.get(i).getName())) {
				//                        headers.remove(i);
				//                    }
				//                }

				Header[] setCookieHeaders = response.getHeaders("Set-Cookie");
				List<Header> _cookieHeaders = convertSetCookieToCookie(setCookieHeaders);
				headers.addAll(_cookieHeaders);
				cookieHeaders.addAll(_cookieHeaders);
				continue;
			} else {
				Header[] setCookieHeaders = response.getHeaders("Set-Cookie");
				List<Header> _cookieHeaders = convertSetCookieToCookie(setCookieHeaders);
				cookieHeaders.addAll(_cookieHeaders);
				return cookieHeaders;
			}
		}
	}

	/**
	 * 将set-cookie转换成cookie
	 * @param setCookieHeaders
	 * @return
	 */
	public static List<Header> convertSetCookieToCookie(Header[] setCookieHeaders) {
		List<Header> cookieHeaders = null;
		if (setCookieHeaders != null) {
			cookieHeaders = new ArrayList<Header>();
			if (setCookieHeaders != null && setCookieHeaders.length > 0) {
				for (Header _header : setCookieHeaders) {

					cookieHeaders.add(new BasicHeader("Cookie", _header.getValue()));
				}
			}
		}
		return cookieHeaders;
	}

	public static List<Header> perfectHeader(List<Header> headers) {
		if (headers == null) {
			headers = new ArrayList<Header>();
		}
		Header h = null;
		//		h = new BasicHeader("Accept-Encoding", "gzip, deflate");
		//		        headers.add(h);

		h = new BasicHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
		headers.add(h);
		//        
		//        h = new BasicHeader("Accept-Language", "zh-CN");
		//        headers.add(h);

		//        h = new BasicHeader("Referer", "http://as.cnsuning.com/login.htm");   //告诉服务器我是从哪个页面链接过来的
		//        headers.add(h);

		//        h = new BasicHeader("Accept-Language", "zh-CN");
		//        headers.add(h);

		return headers;
	}

	/**
	 * 
	 * @param url
	 * @param requestParams
	 * @param _cookies
	 * @param headers
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse post(String url, Map<String, Object> requestParams, List<Cookie> _cookies,
			List<Header> headers, HttpHost proxy) throws ClientProtocolException, IOException {
		return httpRequest(url, requestParams, _cookies, headers, proxy, "post");
	}

	public static HttpResponse httpRequest(String uri, Map<String, Object> requestParams, List<Cookie> _cookies,
			List<Header> headers, HttpHost proxy, String method) throws ClientProtocolException, IOException

	{
		return httpRequest(uri, requestParams, _cookies, headers, proxy, method, null);
	}

	public static HttpResponse httpRequest(String uri, Map<String, Object> requestParams, List<Cookie> _cookies,
			List<Header> headers, HttpHost proxy, String method, String requestBody) throws ClientProtocolException,
			IOException {
		long seq = requestSeq.incrementAndGet();

		DefaultHttpClient httpclient = new ContentEncodingHttpClient();
		httpclient
				.getParams()
				.setParameter("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
		HttpRequestBase httpRequestBase = createHttpRequestBaseByMethod(method);
		httpRequestBase.setURI(URI.create(uri));

		if (proxy != null && !StringUtils.isBlank((proxy.getHostName()))) {
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		try {
			if (log.isInfoEnabled()) {
				StringBuilder sb4headers = new StringBuilder();
				if (headers != null) {
					sb4headers.append("\r\n[\r\n");
					for (Header header : headers) {
						sb4headers.append("{").append(header.getName()).append("  :  ");
						sb4headers.append(header.getValue()).append("}\r\n");
					}
					sb4headers.append("]\r\n");
				}

				StringBuilder sb4requestparams = new StringBuilder();
				if (requestParams != null) {
					sb4requestparams.append("\r\n[\r\n");
					Set<Map.Entry<String, Object>> entrySet = requestParams.entrySet();
					for (Map.Entry<String, Object> entry : entrySet) {
						sb4requestparams.append("{").append(entry.getKey()).append("  :  ");
						sb4requestparams.append(entry.getValue()).append("}\r\n");
					}
					sb4requestparams.append("]\r\n");
				}

				StringBuilder sb4cookies = new StringBuilder();
				if (_cookies != null) {
					sb4cookies.append("\r\n[\r\n");
					for (Cookie cookie : _cookies) {
						sb4cookies.append("{").append(cookie.getName()).append("  :  ");
						sb4cookies.append(cookie.getValue()).append("}\r\n");
					}
					sb4cookies.append("]\r\n");
				}

				log.info(
						"request url {}:{}, \r\nheaders:{} \r\ncookies:{} \r\nrequestParams:{} \r\nrequest body:{}",
						new Object[] { seq, uri, sb4headers, sb4cookies, sb4requestparams, requestBody });
			}

			List<Cookie> cookies = httpclient.getCookieStore().getCookies();

			headers = perfectHeader(headers);
			if (headers != null) {
				for (Header header : headers) {
					httpRequestBase.addHeader(header);
				}
			}

			List<NameValuePair> nameValuePairs = null;// new ArrayList<NameValuePair>();

			if (requestParams != null) {
				nameValuePairs = new ArrayList<NameValuePair>();
				Set<Map.Entry<String, Object>> entrySet = requestParams.entrySet();

				for (Map.Entry<String, Object> entry : entrySet) {
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
				}
			}
			if (_cookies != null) {
				cookies.addAll(_cookies);
			}
			if (nameValuePairs != null) {
				if (httpRequestBase instanceof HttpPost) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, encoding);
					((HttpPost) httpRequestBase).setEntity(entity);
				}
			}

			if (requestBody != null) {
				if (httpRequestBase instanceof HttpPost) {
					StringEntity entity = new StringEntity(requestBody, encoding);
					((HttpPost) httpRequestBase).setEntity(entity);
				} else {
					throw new RuntimeException("发送请求体时，必须用post!");
				}
			}

			HttpResponse response = httpclient.execute(httpRequestBase);

			//
			//			log.info("response:[status:{}]", response.getStatusLine().getStatusCode());
			if (log.isInfoEnabled()) {
				log.info("response {}:{}", seq, response);
			}

			//			if (log.isDebugEnabled()) {
			//				
			//			}

			return response;

		} finally {
			//            httpclient.getConnectionManager().shutdown();
			//07075929:buzhidao
		}

	}

	/**
	 * 
	 * @param method
	 * @return
	 */
	public static HttpRequestBase createHttpRequestBaseByMethod(String method) {
		HttpRequestBase ret = null;
		if (HttpGet.METHOD_NAME.equalsIgnoreCase(method)) {
			ret = new HttpGet();
		} else if (HttpPost.METHOD_NAME.equalsIgnoreCase(method)) {
			ret = new HttpPost();
		} else if (HttpDelete.METHOD_NAME.equalsIgnoreCase(method)) {
			ret = new HttpDelete();
		} else if (HttpHead.METHOD_NAME.equalsIgnoreCase(method)) {
			ret = new HttpHead();
		} else if (HttpOptions.METHOD_NAME.equalsIgnoreCase(method)) {
			ret = new HttpOptions();
		} else if (HttpTrace.METHOD_NAME.equalsIgnoreCase(method)) {
			ret = new HttpTrace();
		} else if (HttpPut.METHOD_NAME.equalsIgnoreCase(method)) {
			ret = new HttpPut();
		} else if (HttpPatch.METHOD_NAME.equalsIgnoreCase(method)) {
			ret = new HttpPatch();
		}
		return ret;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		//        oa("07075929", "buzhidao");
	}
	//
	//    /**
	//     * @param args
	//     */
	//    public static void oa(String username, String password, HttpHost proxy) throws ClientProtocolException, IOException
	//    {
	//        Map<String, String> params = new HashMap<String, String>();
	//        params.put("username", username);
	//        params.put("password", password);
	//
	//        String url = "http://as.cnsuning.com/names.nsf?Login";
	//        HttpResponse response = post(url, params, null, null);
	//
	//        Header[] cookiesHeaders = response.getHeaders("Set-Cookie");
	//        List<Header> _headers = null;
	//        if (cookiesHeaders != null && cookiesHeaders.length > 0)
	//        {
	//            _headers = new ArrayList<Header>();
	//            for (Header _header : cookiesHeaders)
	//            {
	//                _headers.add(new BasicHeader("Cookie", _header.getValue()));
	//            }
	//        }
	//
	//        //获取响应内容
	//        String responseHtml =  EntityUtils.toString(response.getEntity());
	//        userLog.info(responseHtml);
	//
	//        response = post("http://as.cnsuning.com/Produce/WeboaConfig.nsf/WFQbHomeMenu?OpenForm&login", null, null, _headers, proxy);
	//        responseHtml = EntityUtils.toString(response.getEntity());//
	//        System.out.println(responseHtml);
	//    }
	//
	//    public static void soa(String[] args) throws ClientProtocolException, IOException
	//    {
	//        Map<String, String> params = new HashMap<String, String>();
	//        //        params.put("j_username", "11041811");
	//        //        params.put("j_password", "11041811");
	//
	//        String url = "http://oasit.cnsuning.com/SuningUUMWeb/AuthenticationServlet.do?j_username=11041811&j_password=11041811"; //
	//        HttpResponse response = post(url, params, null, null);
	//
	//        org.apache.http.Header[] headers = response.getAllHeaders();
	//        String cookieValue = null;
	//        String location = null;
	//        BasicClientCookie cookie;
	//        for (int i = 0; i < headers.length; i++)
	//        {
	//            String name = headers[i].getName();
	//            if ("Set-Cookie".equals(name))
	//            {
	//                cookieValue = headers[i].getValue();
	//                cookie = new BasicClientCookie("Set-Cookie", cookieValue);
	//
	//            } else if ("Location".equals(name))
	//            {
	//                location = headers[i].getValue();
	//            }
	//        }
	//
	//        org.apache.http.Header[] setCookies = response.getHeaders("Set-Cookie");
	//
	//        //组装cookie
	//        List<Cookie> cookies = new ArrayList<Cookie>();
	//
	//        //获取响应内容
	//        String content =  EntityUtils.toString(response.getEntity());
	//        System.out.println("cookieValue:" + cookieValue);
	//        System.out.println("location:" + location);
	//        System.out.println(content);
	//        userLog.info(content);
	//
	//        response = post(location, params, null, Arrays.asList(setCookies));
	//        content = EntityUtils.toString(response.getEntity());
	//        System.out.println(content);
	//
	//    }
}
