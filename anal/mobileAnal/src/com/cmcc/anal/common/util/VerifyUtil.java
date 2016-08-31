package com.cmcc.anal.common.util;


 /**
 *  Class Name: VerifyUtil.java
 *  Function:
 *  	数据数据校验接口
 *     Modifications:   
 *  
 *  @author zhijide@pica.com  DateTime 2012-11-5 下午03:26:56    
 *  @version 1.0
 */
public class VerifyUtil {
	
	/**
	 *  Function:
	 *		校验UUID
	 *  @author zhijide@pica.com  DateTime 2012-11-5 下午03:39:57
	 *  @param str
	 *  @return
	 */
	public static boolean verifyUUID(String uuid){
		return uuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
	}
	
	/**
	 *  Function:
	 *		校验EMail地址
	 *  @author zhijide@pica.com  DateTime 2012-11-5 下午03:54:24
	 *  @param email
	 *  @return
	 */
	public static boolean verifyEMail(String email){
		return email.matches("^[a-zA-Z0-9_.]+@[a-zA-Z0-9_.]+[.a-zA-Z_.]+$");
	}
	
	/**
	 *  Function:
	 *		校验手机号码
	 *  @author zhijide@pica.com  DateTime 2012-11-5 下午03:54:24
	 *  @param email
	 *  @return
	 */
	public static boolean verifyPhone(String phoneNuber){
		return phoneNuber.matches("(\\+\\d+)?1[3458]\\d{9}$");
	}
	
	/**
	 *  Function:
	 *		校验固化号码
	 *  @author zhijide@pica.com  DateTime 2012-11-5 下午03:54:24
	 *  @param email
	 *  @return
	 */
	public static boolean verifyTelephone(String phoneNuber){
		return phoneNuber.matches("(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$");
	}
	
	/**
	 *  Function:
	 *		校验身份证号码
	 *  @author zhijide@pica.com  DateTime 2012-11-5 下午03:54:24
	 *  @param email
	 *  @return
	 */
	public static boolean verifyIdentityCard(String identity){
		return identity.matches("[1-9]\\d{13,16}[a-zA-Z0-9]{1}");
	}
	
	/**
	 *  Function:
	 *		校验邮政编码
	 *  @author zhijide@pica.com  DateTime 2012-11-5 下午06:05:17
	 *  @param postalcode 邮编
	 *  @return
	 */
	public static boolean verifyPostalcode(String postalcode){
		return postalcode.matches("[0-9]\\d{5}");
	}

	/**
	 *  Function:
	 *		校验URL
	 *  @author zhijide@pica.com  DateTime 2012-11-14 上午11:22:07
	 *  @param postalcode
	 *  @return
	 */
	public static boolean verifyURL(String postalcode){
		// ((http|https|HTTP|HTTPS)?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?
		// (http[s]?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?
		
		return postalcode.matches("((http|https)?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?");
	}
	
	
}
