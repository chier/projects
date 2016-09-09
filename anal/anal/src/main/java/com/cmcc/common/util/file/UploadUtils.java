package com.cmcc.common.util.file;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.cmcc.common.Global;
import com.cmcc.common.exception.BusinessException;
/**
 * 文件上传类
 *
 */
public class UploadUtils implements Runnable {

	// private static String URL =
	// "http://221.176.28.67:80/blockuploadsharecontent.aspx";
	
	private String downloadUrl = null;
	private String nextURL = null;
	
	private String SSIC = null;
	private final String BOUNDARY = "eba5ba3d948f4ef0b107";
	private final int SIZE = 50000;
	private final String PRAGMA = "xz4BBcVF923244C-0141-4DF2-9D65-47BED8C75931200961516583401660--eba5ba3d948f4ef0b107";
	private int numOfCycle;
	private long fileSize;
	
	private FileInputStream fis;
	private Log log = LogFactory.getLog(UploadUtils.class);
	private String downloadPath = "c:/result.jpg";
	
	private int getRandom(int size) {
		Random rand = new Random();
		return Math.abs(rand.nextInt()) % size;
	}

	private String getDownloadURL(String result) throws DocumentException {
		Document doc = DocumentHelper.parseText(result);
		Node node = doc.selectSingleNode("/results/uri");
		return node.valueOf("@download");
	}
	
	/**
	 * Resume broken transfer 断点续传
	 * 
	 * @param uploadFile
	 * @return
	 * @throws DocumentException 
	 * @throws BusinessException 
	 * @throws IOException 
	 * @since  2009-11-5
	 */
	public synchronized String uploadFileResume(File uploadFile) throws DocumentException, BusinessException, IOException{
		
		String uploadUri = "/BlockUploadFile.aspx";
		String downloadURL = null;
		int random = getRandom(Global.mediaMap.size());
		String mediaServerUrl = Global.mediaMap.get(random) + uploadUri;
		try {
			fis = new FileInputStream(uploadFile);
			downloadURL =  this.uploadFileResume(uploadFile, null,  mediaServerUrl);
			
		} catch (IOException e) {		
			log.error(mediaServerUrl, e);	
			for (int i = 0; i < Global.mediaMap.size(); i++) {
				mediaServerUrl = Global.mediaMap.get(random) + uploadUri ;
				try {
					downloadURL = this.uploadFileResume(uploadFile, null,  mediaServerUrl);
				} catch (Exception ex) {
					
					log.error(mediaServerUrl, ex);
					continue;
				}
			}
		}finally{
			if( null != fis){
				fis.close();
			}
		}
		if( null == downloadURL || "".equals(downloadURL)){
			throw new BusinessException("Media Server error , or can not return a valid URL");
		}
		return this.getDownloadURL(downloadURL);
	}
	/**
	 * 
	 * @param uploadFile  需要上传的文件，支持断点续传
	 * @param ssic  该值可以不提供 调用的时候传入null
	 * @param signal  表示是否需要同时下载的标志 ，如果只是上传， signal=0， 如果上传的同时还在下载，signal=1
	 * @param serverUrl 媒体服务器的url地址
	 * @return   下载文件的地址
	 * @throws IOException
	 */
	private String uploadFileResume(File uploadFile , String ssic,String serverUrl) throws IOException {
		
		String returnString = null;
		try{
			if (ssic != null && !ssic.trim().equals("")) {
				SSIC = ssic;
			}
			String urlAddress  = serverUrl;
		

			fileSize = uploadFile.length();

			log.info("upload file size : " + fileSize);
			numOfCycle = (int) (fileSize % (SIZE + 1) == 0 ? fileSize / (SIZE + 1)
					: fileSize / (SIZE + 1) + 1);
			
			int flag = 0;
			for (int i = 0; i < numOfCycle; i++) {
				URL url = null;
				if (i == 0) {
					url = new URL(urlAddress);
				} else {
					url = new URL(nextURL);
				}
				
				log.info("upload url : " + url);
				HttpURLConnection httpConn = (HttpURLConnection) url
						.openConnection();
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				httpConn.setRequestMethod("POST");
				httpConn.setRequestProperty("Host", "");
				
				httpConn.setRequestProperty("Cookie", "ssic=" + SSIC);
				httpConn.setRequestProperty("Accept-Encoding", "identity");
				byte[] buffer = new byte[(int) SIZE + 1];
				httpConn.setRequestProperty("Content-Type",
						"multipart/form-data; boundary=" + BOUNDARY);
				httpConn.setRequestProperty("Pragma", PRAGMA);

				DataOutputStream out = null;
				if (i == numOfCycle - 1) {
					httpConn.setRequestProperty("Range", "bytes="
							+ ((int) (i * SIZE) + flag) + "-" + (fileSize - 1));
				} else {
					httpConn.setRequestProperty("Range", "bytes="
							+ ((int) (i * SIZE) + flag) + "-"
							+ ((int) ((i + 1) * SIZE) + flag));
				}
				int len = fis.read(buffer);

				flag++;
				try{
					out = new DataOutputStream(httpConn.getOutputStream());
					out.write(("--" + BOUNDARY + "\r\n").getBytes("UTF-8"));
					out
							.write(("Content-Disposition:form-data;name=\"UploadFile\";filename=\""
									+ uploadFile.getName() + "\"\r\n")
									.getBytes("UTF-8"));
					out.write("Content-Type:image/gif\r\n\r\n".getBytes("UTF-8"));
					out.write(buffer, 0, len);

					out.write(("\r\n--" + BOUNDARY + "\r\n").getBytes("UTF-8"));
					
					InputStream inStream = httpConn.getInputStream();
					if (i == 0) {
						byte[] returnBuffer = new byte[10240];
						int length = inStream.read(returnBuffer);
						returnString = new String(returnBuffer, 0, length, "UTF-8");
						String firstProcessedStr = returnString.substring(returnString
								.indexOf("http:"));
						String secondProcessedStr = firstProcessedStr.substring(0,
								firstProcessedStr.indexOf("\""));
						nextURL = serverUrl + secondProcessedStr.substring(secondProcessedStr.indexOf("?"));
						String firstStr = returnString.substring(returnString
								.indexOf("download=") + 10);
						downloadUrl = firstStr.substring(0, firstStr.indexOf("\""));
					}
					inStream.close();
					out.flush();
				}catch(Exception e){
					throw new RuntimeException(e);
				}finally{
					if( null != out ){
						out.close();
					}
				}
				
				
//				if (signal == 1 && i == 0) {
//					try {
//					Thread	thread = new Thread(this);
//						thread.start();
//					} catch (Exception e) {
//						log.error("start file upload thread exception.", e);
//					}
//				}
			}
//			fis.close();
		}catch (Exception e) {
			log.error("Media server return："+returnString);
			throw new RuntimeException(e);
		}
		
		return returnString;
	}

	

	public void downloadFile() throws Exception {
		File downloadFile = new File(downloadPath);
		FileOutputStream fos = new FileOutputStream(downloadFile);
		int flag = 0;
		for (int i = 0; i < numOfCycle; i++) {
			int statusCode = request(flag, i);
			
			while (statusCode == 416) {
				Thread.sleep(50);
				statusCode = request(flag, i);
			}
			flag++;
		}
		fos.flush();
		fos.close();
	}

	/**
	 * @param flag
	 * @param i
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private int request(int flag, int i) throws MalformedURLException,
			IOException, ProtocolException {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(downloadUrl);
		postMethod.setRequestHeader("Cookie", "ssic=" + SSIC);
		if (i == numOfCycle - 1) {
			postMethod.setRequestHeader("Range", "bytes="
					+ ((int) (i * SIZE) + flag) + "-" + (fileSize - 1));

		} else {
			postMethod.setRequestHeader("Range", "bytes="
					+ ((int) (i * SIZE) + flag) + "-"
					+ ((int) ((i + 1) * SIZE) + flag));
		}
		int statusCode = httpClient.executeMethod(postMethod);
		String range = getValue(postMethod.getResponseHeader("Range"));
		range = range.substring(range.indexOf("=") + 1);

		String[] rangearea = range.split("-");
		long rangeStart = Long.parseLong(rangearea[0]); 
		long rangeEnd = 0;
		if (rangearea.length == 2) {
			rangeEnd = Long.parseLong(rangearea[1]);
		}
		if (statusCode == HttpStatus.SC_OK) {
			InputStream stream = postMethod.getResponseBodyAsStream();
			DataInputStream in = new DataInputStream(stream);
			File savedFile = new File(downloadPath);
			RandomAccessFile oSavedFile = new RandomAccessFile(savedFile, "rw");
			byte[] inc = new byte[1024];
			int insize = 0;
			oSavedFile.seek(rangeStart);
			while ((insize = in.read(inc)) != -1) {
				oSavedFile.write(inc, 0, insize);
			}
			oSavedFile.close();
			in.close();
		}
		return statusCode;
	}

	public static String getValue(Object obj) {
		if (obj == null || "null".equals(obj)) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}

	public void run() {
		try {

			downloadFile();

		} catch (Exception e) {
			log.error("invoke download file method exception.", e);
		}

	}

}
