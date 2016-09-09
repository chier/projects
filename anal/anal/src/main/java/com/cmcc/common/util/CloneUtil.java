/*
 * 文件名： CloneUtil.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 对象拷贝的工具类
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
public class CloneUtil {

	/**
	 * Provides a deep clone serializing/de-serializng <code>objToClone</code>
	 * 
	 * @param objToClone
	 *            The object to be cloned
	 * @return The cloned object
	 */
	public static final Object deepClone(Object objToClone) {
		try {
			ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(
					100);
			ObjectOutputStream objectoutputstream = new ObjectOutputStream(
					bytearrayoutputstream);
			objectoutputstream.writeObject(objToClone);
			byte abyte0[] = bytearrayoutputstream.toByteArray();
			objectoutputstream.close();
			ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(
					abyte0);
			ObjectInputStream objectinputstream = new ObjectInputStream(
					bytearrayinputstream);
			Object clone = objectinputstream.readObject();
			objectinputstream.close();
			return clone;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用apache的BeanUtils工具进行JavaBean对象属性的copy
	 * 
	 * @param fromObj
	 *            源对象
	 * @param toObj
	 *            目的对象
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static final void copyProperties(Object fromObj, Object toObj)
			throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties(fromObj, toObj);
	}

}
