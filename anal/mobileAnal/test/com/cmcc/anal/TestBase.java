/*
 * 文件名： TestBase.java
 * 
 * 创建日期： 2009-2-26
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.anal;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cmcc.anal.common.Global;
import com.cmcc.anal.framework.controller.login.AccountController;

/**
 * 所有测试用例基类，供测试环境初始化配置
 * 
 * @filename:	 com.cmcc.anal.TestBase
 * @copyright:   Copyright (c)2014
 * @company:     北京掌中无限信息技术有限公司
 * @author:      张占亮
 * @version:     1.0
 * @create time: 2014-7-6 下午12:53:51
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
public class TestBase {
	private static Logger logger = LoggerFactory.getLogger(TestBase.class);
	private static ApplicationContext ctx = null;
	@BeforeClass
	public static void setUpBeforeClass() {
		
		ctx= new ClassPathXmlApplicationContext("/config/applicationContext.xml");
		Global._ctx = ctx;
	}

	protected static Object getBean(String beanName) {
		return Global._ctx.getBean(beanName);
	}
	
	@Test
	public void testBeanNotNull() {

		Object datasource_s9999 = getBean("datasource_s9999");
		Assert.assertNotNull(datasource_s9999);
		
		Object sessionfactory_anal_r_0 = getBean("s9999_w");
		Assert.assertNotNull(sessionfactory_anal_r_0);

		Object sessionfactory_anal2_r_0 = getBean("analysis_w");
		Assert.assertNotNull(sessionfactory_anal2_r_0);
	}
}
