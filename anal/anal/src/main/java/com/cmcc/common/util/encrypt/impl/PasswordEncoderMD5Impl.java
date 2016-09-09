/*
 * 文件名： PasswordEncoderMD5Impl.java
 * 
 * 创建日期： 2008-5-12
 *
 * Copyright(C) 2008, by xiaozhi.
 *
 * 原始作者: <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 *
 */
package com.cmcc.common.util.encrypt.impl;

import com.cmcc.common.util.encrypt.IPasswordEncoder;
import com.cmcc.common.util.encrypt.MD5;

/**
 * 
 * 
 * @author <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-5-12
 */
public class PasswordEncoderMD5Impl implements IPasswordEncoder {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.util.encrypt.IPasswordEncoder#decode(java.lang.String)
	 */
	public String decode(String inStr) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.util.encrypt.IPasswordEncoder#encode(java.lang.String)
	 */
	public String encode(String inStr) {
		MD5 static_MD5 = new MD5();
		return static_MD5.getMD5ofStr(inStr);
	}

}
