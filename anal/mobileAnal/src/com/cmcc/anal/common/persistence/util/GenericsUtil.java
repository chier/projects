/*
 * 文件名： GenericsUtils.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.anal.common.persistence.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Generics的util�?通过反射获得Class声明的范型Class.
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 * @version $Revision: 1.2 $
 *
 * @since 2008-11-27
 */
@SuppressWarnings("unchecked")
public class GenericsUtil {

	private static Log log = LogFactory.getLog(GenericsUtil.class);

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public class GroupCodeDAOHibernateImpl
	 * extends HibernateEntityDAO<GroupCode> implements IGroupCodeDAO<GroupCode>
	 * 
	 * @param clazz
	 *            要反射的�?
	 * @return 给定类的第一个直接超类，如果没有找到,返回<code>Object.class</code>�?
	 */
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public class GroupCodeDAOHibernateImpl
	 * extends HibernateEntityDAO<GroupCode> implements IGroupCodeDAO<GroupCode>
	 * 
	 * @param clazz
	 *            要反射的�?
	 * @param index
	 *            该类的超类的索引，从0�?���?
	 * @return 超类。如果没有找�?返回<code>Object.class</code>�?
	 */
	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			//log.warn(clazz.getSimpleName()
					//+ "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log
					.warn(clazz.getSimpleName()
							+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}
}
