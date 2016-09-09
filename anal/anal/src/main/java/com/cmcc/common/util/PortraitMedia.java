package com.cmcc.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.Element;

/*******************************************************************************
 * 
 * @author wangzhengzhan
 * 
 * @since Mar 16, 2009
 * 
 ******************************************************************************/
public class PortraitMedia {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(PortraitMedia.class);

	/**
	 * 
	 * @param file
	 * @param url
	 * @param fid
	 *            这里传userid
	 * @param eid
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	@SuppressWarnings("deprecation")
	public int uploadImg(File file, String url, int uid, int eid)
			throws HttpException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("uploadImg(File file=" + file + ", String url=" + url
					+ ", int uid=" + uid + ", int eid=" + eid + ") - start");
		}

		int crc = 0;
		FileInputStream myfile = new FileInputStream(file);
		InputStream in = new BufferedInputStream(myfile);
		PostMethod post = new PostMethod(url);
		post.setRequestBody(in);
		HttpClient client = new HttpClient();
		post.setRequestHeader("Uid", String.valueOf(uid));
		post.setRequestHeader("Eid", String.valueOf(eid));
		client.executeMethod(post);

		String response = new String(post.getResponseBodyAsString().getBytes(
				"ISO-8859-1"), "UTF-8");
		post.releaseConnection();
		in.close();
		myfile.close();
		crc = Integer.parseInt(response);
		// } catch (Exception e) {
		// if (logger.isErrorEnabled()) {
		// logger.error("uploadImg(File file=" + file + ", String url="
		// + url + ", int uid=" + uid + ", int eid=" + eid + ")",
		// e);
		// }
		//
		// e.printStackTrace();
		// }

		if (logger.isInfoEnabled()) {
			logger.info("uploadImg(File file=" + file + ", String url=" + url
					+ ", int uid=" + uid + ", int eid=" + eid
					+ ") - end - return value=" + crc);
		}
		return crc;
	}

	/**
	 * 
	 * @param file
	 * @param url
	 * @param cluId
	 * @param eid
	 * @return int
	 */
	@SuppressWarnings("deprecation")
	public int uploadClusterPortrait(File file, String url, int cluId, int eid) {
		if (logger.isDebugEnabled()) {
			logger.debug("uploadClusterPortrait(File file=" + file
					+ ", String url=" + url + ", int cluId=" + cluId
					+ ", int eid=" + eid + ") - start");
		}

		int crc = 0;
		url += "?group-uri=";
		StringBuffer param = new StringBuffer("sip:PG").append(cluId).append(
				"@fetion.com.cn;p=7642");
		try {
			url += URLEncoder.encode(param.toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			if (logger.isErrorEnabled()) {
				logger.error("uploadClusterPortrait(File file=" + file
						+ ", String url=" + url + ", int cluId=" + cluId
						+ ", int eid=" + eid + ")", e);
			}

			e.printStackTrace();
		}
		try {
			FileInputStream myfile = new FileInputStream(file);
			InputStream in = new BufferedInputStream(myfile);
			PostMethod post = new PostMethod(url);
			post.setRequestBody(in);
			HttpClient client = new HttpClient();
			post.setRequestHeader("Eid", String.valueOf(eid));
			client.executeMethod(post);

			String response = new String(post.getResponseBodyAsString()
					.getBytes("ISO-8859-1"), "UTF-8");
			post.releaseConnection();
			in.close();
			myfile.close();
			crc = Integer.parseInt(response);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("uploadClusterPortrait(File file=" + file
						+ ", String url=" + url + ", int cluId=" + cluId
						+ ", int eid=" + eid + ")", e);
			}

			e.printStackTrace();
		}

		if (logger.isInfoEnabled()) {
			logger.info("uploadClusterPortrait(File file=" + file
					+ ", String url=" + url + ", int cluId=" + cluId
					+ ", int eid=" + eid + ") - end - return value=" + crc);
		}
		return crc;
	}

	/**
	 * 续传文件
	 * 
	 * @param file
	 * @param strURL
	 * @param rangeStart
	 * @param sendSize
	 * @return
	 */
	public String blockUpload(File file, String strURL, long rangeStart,
			long sendSize) {

		long rangeEnd = 0;
		String contentLength = "";
		if (sendSize > 0) {
			rangeEnd = rangeStart + sendSize;
			contentLength = String.valueOf(sendSize);
		} else {
			rangeEnd = file.length();
			contentLength = String.valueOf(file.length());
		}
		strURL = strURL + "?FileSize=" + rangeStart + "&To=" + rangeEnd;
		logger.info("in bloakUpload(strURL=" + strURL + "");
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		DataInputStream inStream = null;
		// String exsistingFileName = "d:\\mysql-4.0.23-win-noinstall.zip";
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		// String urlString = "http://localhost/uploadbean/SimpleUpload.jsp";
		try {
			// ------------------ CLIENT REQUEST
			FileInputStream in = new FileInputStream(file);
			// open a URL connection to the Servlet
			URL url = new URL(strURL);
			// Open a HTTP connection to the URL
			conn = (HttpURLConnection) url.openConnection();
			// Allow Inputs
			conn.setDoInput(true);
			// Allow Outputs
			conn.setDoOutput(true);
			// Don't use a cached copy.
			conn.setUseCaches(false);
			// Use a post method.
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Range", "bytes="
					+ String.valueOf(rangeStart) + "-"
					+ String.valueOf(rangeEnd));
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("Content-Length", contentLength);
			conn.setRequestProperty("Accept-Charset", "utf-8");
			dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos
					.writeBytes("Content-Disposition: form-data; name=\"UploadFile\";"
							+ " filename=\"" + file.getName() + "\"" + lineEnd);
			dos.writeBytes("Content-Type: text/html" + lineEnd);
			dos.writeBytes(lineEnd);
			byte inc[] = new byte[1024];
			int insize = 0, readsize = 0;
			in.skip(rangeStart);
			while ((insize = in.read(inc)) != -1 && rangeEnd == 0 ? true
					: (rangeStart + readsize < rangeEnd)) {
				readsize += insize;
				// oSavedFile.write(inc, 0, insize);
				dos.write(inc, 0, insize);
			}
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			// close streams
			in.close();

			dos.flush();
			dos.close();
		} catch (MalformedURLException ex) {
			if (logger.isErrorEnabled()) {
				logger.error("blockUpload(File file=" + file
						+ ", String strURL=" + strURL + ", long rangeStart="
						+ rangeStart + ", long sendSize=" + sendSize + ")", ex);
			}
			if (logger.isInfoEnabled()) {
				logger.info("blockUpload(File file=" + file
						+ ", String strURL=" + strURL + ", long rangeStart="
						+ rangeStart + ", long sendSize=" + sendSize
						+ ") - end - return value=-1");
			}
			return "-1";
		} catch (IOException ioe) {
			if (logger.isErrorEnabled()) {
				logger
						.error("blockUpload(File file=" + file
								+ ", String strURL=" + strURL
								+ ", long rangeStart=" + rangeStart
								+ ", long sendSize=" + sendSize + ")", ioe);
			}
			if (logger.isInfoEnabled()) {
				logger.info("blockUpload(File file=" + file
						+ ", String strURL=" + strURL + ", long rangeStart="
						+ rangeStart + ", long sendSize=" + sendSize
						+ ") - end - return value=-1");
			}
			return "-1";
		}
		// ------------------ read the SERVER RESPONSE
		StringBuffer outBuffer = new StringBuffer();
		try {
			inStream = new DataInputStream(conn.getInputStream());
			String str;
			while ((str = inStream.readLine()) != null) {
				outBuffer.append(str);
			}
			inStream.close();
		} catch (IOException ioex) {
			if (logger.isErrorEnabled()) {
				logger.error("blockUpload(File file=" + file
						+ ", String strURL=" + strURL + ", long rangeStart="
						+ rangeStart + ", long sendSize=" + sendSize + ")",
						ioex);
			}

			if (logger.isInfoEnabled()) {
				logger.info("blockUpload(File file=" + file
						+ ", String strURL=" + strURL + ", long rangeStart="
						+ rangeStart + ", long sendSize=" + sendSize
						+ ") - end - return value=-1");
			}
			return "-1";
		}
		String returnString = outBuffer.toString();
		if (logger.isInfoEnabled()) {
			logger.info("blockUpload( return value=" + returnString + ")");
		}
		return returnString;
	}

	public String uploadContentAttach(File file, String strURL) {

		int start = 0;
		long buffersize = 1024;
		String returnurl = "";
		long runs = 0;
		String returns = "";
		Document doc = null;
		Element root = null;
		Element url = null;
		String next = null;
		while (true) {
			returns = this.blockUpload(file, strURL, start, buffersize);
			if (returns.equals("-1")) {
				break;
			} else {
				doc = StringUtil.getDocument(returns);
				if (doc != null) {
					root = doc.getRootElement();
					url = root.element("uri");
					strURL = url.attribute("upload").getStringValue();
					returnurl = url.attributeValue("download");
					next = url.attributeValue("next-start-position");
					if (next.equals("accomplished")) {
						break;
					}
					start = Integer.parseInt(next);
					if (runs + buffersize < file.length()) {
						runs = runs + buffersize;
					} else {
						buffersize = file.length() - runs;
					}

				} else {
					break;
				}
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("uploadContentAttach(File file=" + file
					+ ", String strURL=" + strURL + ") - end - return value="
					+ returnurl);
		}
		return returnurl;
	}

	/**
	 * 
	 * @param file
	 * @param url
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String uploadContentImage(File file, String url) {
		if (logger.isDebugEnabled()) {
			logger.debug("uploadImg(File file=" + file + ", String url=" + url
					+ ") - start");
		}
		String crc = null;
		String downurl = null;
		Document doc = null;
		String downparm = null;
		try {
			FileInputStream myfile = new FileInputStream(file);
			InputStream in = new BufferedInputStream(myfile);
			PostMethod post = new PostMethod(url);
			post.setRequestBody(in);
			HttpClient client = new HttpClient();
			client.executeMethod(post);

			crc = new String(post.getResponseBodyAsString().getBytes(
					"ISO-8859-1"), "UTF-8");
			post.releaseConnection();
			in.close();
			myfile.close();
			myfile = null;
			in = null;
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("uploadImg(File file=" + file + ", String url="
						+ url + ")", e);
			}
			e.printStackTrace();
		}

		if (logger.isInfoEnabled()) {
			logger.info("uploadImg(File file=" + file + ", String url=" + url
					+ ") - end - return value=" + crc);
		}
		try {
			int istatecode = crc.lastIndexOf("status-code=\"");
			if (istatecode != -1) {
				String statecode = crc.substring(istatecode + 13,
						istatecode + 16);
				int last = crc.lastIndexOf("/></files></results>");
				if (statecode.equals("200")) {
					istatecode = crc.lastIndexOf("fileuri=");
					if (istatecode != -1 && last != -1) {
						downparm = crc.substring(istatecode + 8, last).trim();
					}

				}
			}

		} catch (Exception ex) {
			logger.error("uploadImage error", ex);
		}

		return downparm.replaceAll("\n", "");
	}

	public static void main(String[] args) {
		PortraitMedia media = new PortraitMedia();
		File f = new File("d:/logo.jpg");
		System.out.println(media.uploadContentImage(f,
				"http://192.168.12.94:8099/settabimage.aspx"));
	}
}
