/*
 * 文件名： DomTreeBuilderUtil.java
 * 
 * 创建日期�?2009-2-18
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作�?: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.common.util;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cmcc.common.Global;
import com.cmcc.common.model.CommonObject;
import com.cmcc.common.model.GroupObject;
import com.cmcc.framework.controller.formbean.CustomVO;
import com.cmcc.framework.controller.formbean.JrxmlVO;
import com.cmcc.framework.controller.formbean.QueriesVO;
import com.cmcc.framework.model.model.WebModel;

/**
 * 
 * 
 * @author <a href="mailto:sun128837@126.com">conntsing</a>
 * 
 * @version $Revision: 1.15 $
 * 
 * @since 2009-2-18
 */
public class DomTreeBuilderUtil {

	/**
	 * 为xtree.js生成组结构树的Dom信息�?
	 * objNodeList必须是Module模型里根据parentModuleId查询出来的的实例�?��成的List�?
	 * 
	 * @param objNodeList
	 *            Module模型里根据parentModuleId查询出来的的实例
	 * 
	 * @param srcUrl
	 *            点开树节点前的＋号对应的Action，例如listGroup.action，
	 *            本方法会在URL后自动拼加上此节点的Identifier
	 *            ，以groupId为参数名。例如：listGroup.action?groupId=1�?
	 * 
	 * @param path
	 *            点击树节点对应的上下文路径�?例如�?portal
	 * 
	 * @param srcUrlAddParam
	 *            srcUrl的附加参数，例如type=userGroup，则�?��srcUrl其实�?listGroup.action?
	 *            groupId =1&type=userGroup。可以为null，或""�?
	 * 
	 * @param actionUrlAddParam
	 *            actionUrl的附加参数，例如type=user，则�?��actionUrl其实�?listGroup.action?
	 *            groupId =1&type=user。可以为null，或""�?
	 * 
	 * @param target
	 *            点击树节点连接的target，即HTML�?a href="#" target...></a>中的target�?
	 * 
	 * @param language
	 *            根据客户端浏览器使用的语�?��进行相关国际化�?
	 * 
	 * @return 返回 为xtree.js生成树的Dom信息
	 */
	// zhangzhanliang
	public static Document createRawDataDomTree(List<JrxmlVO> objNodeList,
			String srcUrl, String path, String srcUrlAddParam,
			String actionUrlAddParam, String target) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		if (objNodeList == null) {
			return document;
		}
		String actionUrl = null;
		String param = null;
		StringBuffer sb = new StringBuffer();
		JrxmlVO objNode = null;
		for (int i = 0; i < objNodeList.size(); i++) {
			objNode = objNodeList.get(i);
			Element eachElement = rootElement.addElement("tree");
			eachElement.addAttribute("text", objNode.getModelName()); // 显示的铝箔

			sb.append(srcUrl).append("?").append("groupId=").append(
					objNode.getIdentifier());
					/*
					.append("&name=").append(
					objNode.getModelName()).append("&parentUrl=").append(
					objNode.getParentUrl());
					*/
			param = null;
			actionUrl = path + objNode.getActionUrl();

			if (srcUrlAddParam != null && !srcUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(srcUrlAddParam + param);
			}
			eachElement.addAttribute("src", sb.toString());
			sb.delete(0, sb.length());
			sb.append(actionUrl);
			// 判断当前URL字符串中拼接方式
			if (sb.indexOf("?") > 0) {
				sb.append("&");
			} else {
				sb.append("?");
			}
			sb.append("groupId=").append(objNode.getIdentifier());

			if (actionUrlAddParam != null
					&& !actionUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(actionUrlAddParam);
			}
			if (!objNode.getActionUrl().equals("javascript:void(0)")) {
				eachElement.addAttribute("action", sb.toString());
			}
			sb.delete(0, sb.length());
			eachElement.addAttribute("_name", objNode.getModelName());
			eachElement.addAttribute("_parentUrl", objNode.getParentUrl());
			eachElement.addAttribute("target", target);
			eachElement.addAttribute("isleaf", String.valueOf(objNode
					.getIsLeaf()));
		}
		sb = null;
		return document;
	}

	/**
	 * 为xtree.js生成组结构树的Dom信息�?
	 * objNodeList必须是Module模型里根据parentModuleId查询出来的的实例�?��成的List�?
	 * 
	 * @param objNodeList
	 *            Module模型里根据parentModuleId查询出来的的实例
	 * 
	 * @param srcUrl
	 *            点开树节点前的＋号对应的Action，例如listGroup.action，
	 *            本方法会在URL后自动拼加上此节点的Identifier
	 *            ，以groupId为参数名。例如：listGroup.action?groupId=1�?
	 * 
	 * @param path
	 *            点击树节点对应的上下文路径�?例如�?portal
	 * 
	 * @param srcUrlAddParam
	 *            srcUrl的附加参数，例如type=userGroup，则�?��srcUrl其实�?listGroup.action?
	 *            groupId =1&type=userGroup。可以为null，或""�?
	 * 
	 * @param actionUrlAddParam
	 *            actionUrl的附加参数，例如type=user，则�?��actionUrl其实�?listGroup.action?
	 *            groupId =1&type=user。可以为null，或""�?
	 * 
	 * @param target
	 *            点击树节点连接的target，即HTML�?a href="#" target...></a>中的target�?
	 * 
	 * @param language
	 *            根据客户端浏览器使用的语�?��进行相关国际化�?
	 * 
	 * @return 返回 为xtree.js生成树的Dom信息
	 */
	// zhangzhanliang
	public static Document createQueriesDomTree(List<QueriesVO> objNodeList,
			String srcUrl, String path, String srcUrlAddParam,
			String actionUrlAddParam, String target) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		if (objNodeList == null) {
			return document;
		}
		String actionUrl = null;
		String param = null;
		StringBuffer sb = new StringBuffer();
		QueriesVO objNode = null;
		for (int i = 0; i < objNodeList.size(); i++) {
			objNode = objNodeList.get(i);
			Element eachElement = rootElement.addElement("tree");
			eachElement.addAttribute("text", objNode.getModelName()); // 显示的铝箔

			sb.append(srcUrl).append("?").append("groupId=").append(
					objNode.getIdentifier()).append("&stype=").append(objNode.getStype());
					// .append("&name=").append(objNode.getModelName());
			if(!StringUtil.isBlank(objNode.getTableCode())){
				sb.append("&tableCode=").append(objNode.getTableCode());
			}
			
			
			param = null;
			actionUrl = path + objNode.getActionUrl();

			if (srcUrlAddParam != null && !srcUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(srcUrlAddParam + param);
			}
			eachElement.addAttribute("src", sb.toString());
			sb.delete(0, sb.length());
			sb.append(actionUrl);
			// 判断当前URL字符串中拼接方式
			if (sb.indexOf("?") > 0) {
				sb.append("&");
			} else {
				sb.append("?");
			}
			sb.append("tableCode=").append(objNode.getTableCode());
			sb.append("&name=").append(
					objNode.getModelName());

			if (actionUrlAddParam != null
					&& !actionUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(actionUrlAddParam);
			}
			if (!objNode.getActionUrl().equals("javascript:void(0)")) {
				eachElement.addAttribute("action", sb.toString());
			}
			sb.delete(0, sb.length());
			eachElement.addAttribute("_name", objNode.getModelName());
			eachElement.addAttribute("target", target);
			eachElement.addAttribute("isleaf", String.valueOf(objNode
					.getIsLeaf()));
		}
		sb = null;
		return document;
	}
	
	// zhangzhanliang
		public static Document createQueriesDomTreeYear(List<QueriesVO> objNodeList, String path, String srcUrlAddParam,
				String actionUrlAddParam, String target) {
			/** 建立document对象 */
			Document document = DocumentHelper.createDocument();
			/** 建立XML文档的根tree */
			Element rootElement = document.addElement("tree");
			if (objNodeList == null) {
				return document;
			}
			String actionUrl = null;
			String param = null;
			StringBuffer sb = new StringBuffer();
			QueriesVO objNode = null;
			for (int i = 0; i < objNodeList.size(); i++) {
				objNode = objNodeList.get(i);
				Element eachElement = rootElement.addElement("tree");
				eachElement.addAttribute("text", objNode.getModelName()); // 显示的铝箔

				sb.append(objNode.getActionUrl()).append("&").append("groupId=").append(
						objNode.getIdentifier());
						// .append("&name=").append(objNode.getModelName());
				if(!StringUtil.isBlank(objNode.getTableCode())){
					sb.append("&tableCode=").append(objNode.getTableCode());
				}
				
				
				param = null;
				actionUrl = path + objNode.getActionUrl();

				if (srcUrlAddParam != null && !srcUrlAddParam.equalsIgnoreCase("")) {
					sb.append("&").append(srcUrlAddParam + param);
				}
				eachElement.addAttribute("src", sb.toString());
				sb.delete(0, sb.length());
				sb.append(actionUrl);
				// 判断当前URL字符串中拼接方式
				if (sb.indexOf("?") > 0) {
					sb.append("&");
				} else {
					sb.append("?");
				}
				sb.append("tableCode=").append(objNode.getTableCode());
				sb.append("&name=").append(
						objNode.getModelName());

				if (actionUrlAddParam != null
						&& !actionUrlAddParam.equalsIgnoreCase("")) {
					sb.append("&").append(actionUrlAddParam);
				}
				if (!objNode.getActionUrl().equals("javascript:void(0)")) {
					eachElement.addAttribute("action", sb.toString());
				}
				sb.delete(0, sb.length());
				eachElement.addAttribute("_name", objNode.getModelName());
				eachElement.addAttribute("target", target);
				eachElement.addAttribute("isleaf", String.valueOf(objNode
						.getIsLeaf()));
			}
			sb = null;
			return document;
		}
		
	public static Document createLoapDomTree(List<QueriesVO> objNodeList,
			String srcUrl, String path, String srcUrlAddParam,
			String actionUrlAddParam, String target) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		if (objNodeList == null) {
			return document;
		}
		String actionUrl = null;
		String param = null;
		StringBuffer sb = new StringBuffer();
		QueriesVO objNode = null;
		for (int i = 0; i < objNodeList.size(); i++) {
			objNode = objNodeList.get(i);
			Element eachElement = rootElement.addElement("tree");
			eachElement.addAttribute("text", objNode.getModelName()); // 显示的铝箔

			sb.append(srcUrl).append("?").append("groupId=").append(
					objNode.getIdentifier());
					//.append("&name=").append(	objNode.getModelName());
			
			if(!StringUtil.isBlank(objNode.getTableCode())){
				sb.append("&tableCode=").append(objNode.getTableCode());
			}
			
			
			param = null;
//			actionUrl = path + objNode.getActionUrl();
			actionUrl = "loapAction!leftFrame."
					+ Global.ACTION_EXT;

			if (srcUrlAddParam != null && !srcUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(srcUrlAddParam + param);
			}
			eachElement.addAttribute("src", sb.toString());
			sb.delete(0, sb.length());
			sb.append(actionUrl);
			// 判断当前URL字符串中拼接方式
			if (sb.indexOf("?") > 0) {
				sb.append("&");
			} else {
				sb.append("?");
			}
			sb.append("tableCode=").append(objNode.getTableCode());
			sb.append("&name=").append(
					objNode.getModelName());

			if (actionUrlAddParam != null
					&& !actionUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(actionUrlAddParam);
			}
			if (!objNode.getActionUrl().equals("javascript:void(0)")) {
				eachElement.addAttribute("action", sb.toString());
				
			}
			sb.delete(0, sb.length());
			eachElement.addAttribute("_name", objNode.getModelName());
			eachElement.addAttribute("target", target);
			eachElement.addAttribute("isleaf", String.valueOf(objNode
					.getIsLeaf()));
			
		}
		sb = null;
		return document;
	}
	
	
	public static Document createLoapDomTreeYear(List<QueriesVO> objNodeList, String path, String srcUrlAddParam,
			String actionUrlAddParam, String target) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		if (objNodeList == null) {
			return document;
		}
		String actionUrl = null;
		String param = null;
		StringBuffer sb = new StringBuffer();
		QueriesVO objNode = null;
		for (int i = 0; i < objNodeList.size(); i++) {
			objNode = objNodeList.get(i);
			Element eachElement = rootElement.addElement("tree");
			eachElement.addAttribute("text", objNode.getModelName()); // 显示的铝箔

			sb.append(objNode.getActionUrl()).append("&").append("groupId=").append(
					objNode.getIdentifier());
					//.append("&name=").append(	objNode.getModelName());
			
			if(!StringUtil.isBlank(objNode.getTableCode())){
				sb.append("&tableCode=").append(objNode.getTableCode());
			}
			
			
			param = null;
//			actionUrl = path + objNode.getActionUrl();
			actionUrl = "loapAction!leftFrame."
					+ Global.ACTION_EXT;

			if (srcUrlAddParam != null && !srcUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(srcUrlAddParam + param);
			}
			eachElement.addAttribute("src", sb.toString());
			sb.delete(0, sb.length());
			sb.append(actionUrl);
			// 判断当前URL字符串中拼接方式
			if (sb.indexOf("?") > 0) {
				sb.append("&");
			} else {
				sb.append("?");
			}
			sb.append("tableCode=").append(objNode.getTableCode());
			sb.append("&name=").append(
					objNode.getModelName());

			if (actionUrlAddParam != null
					&& !actionUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(actionUrlAddParam);
			}
			if (!objNode.getActionUrl().equals("javascript:void(0)")) {
				eachElement.addAttribute("action", sb.toString());
				
			}
			sb.delete(0, sb.length());
			eachElement.addAttribute("_name", objNode.getModelName());
			eachElement.addAttribute("target", target);
			eachElement.addAttribute("isleaf", String.valueOf(objNode
					.getIsLeaf()));
			
		}
		sb = null;
		return document;
	}
	
	
	/**
	 * 为xtree.js生成组结构树的Dom信息�?
	 * objNodeList必须是Module模型里根据parentModuleId查询出来的的实例�?��成的List�?
	 * 
	 * @param objNodeList
	 *            Module模型里根据parentModuleId查询出来的的实例
	 * 
	 * @param srcUrl
	 *            点开树节点前的＋号对应的Action，例如listGroup.action，
	 *            本方法会在URL后自动拼加上此节点的Identifier
	 *            ，以groupId为参数名。例如：listGroup.action?groupId=1�?
	 * 
	 * @param path
	 *            点击树节点对应的上下文路径�?例如�?portal
	 * 
	 * @param srcUrlAddParam
	 *            srcUrl的附加参数，例如type=userGroup，则�?��srcUrl其实�?listGroup.action?
	 *            groupId =1&type=userGroup。可以为null，或""�?
	 * 
	 * @param actionUrlAddParam
	 *            actionUrl的附加参数，例如type=user，则�?��actionUrl其实�?listGroup.action?
	 *            groupId =1&type=user。可以为null，或""�?
	 * 
	 * @param target
	 *            点击树节点连接的target，即HTML�?a href="#" target...></a>中的target�?
	 * 
	 * @param language
	 *            根据客户端浏览器使用的语�?��进行相关国际化�?
	 * 
	 * @return 返回 为xtree.js生成树的Dom信息
	 */
	// wangbo
	public static Document createCustomDomTree(List<CustomVO> objNodeList, String srcUrl, String path, String srcUrlAddParam, String actionUrlAddParam, String target) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		if (objNodeList == null) {
			return document;
		}
		String actionUrl = null;
		String param = null;
		StringBuffer sb = new StringBuffer();
		CustomVO objNode = null;
		for (int i = 0; i < objNodeList.size(); i++) {
			objNode = objNodeList.get(i);
			Element eachElement = rootElement.addElement("tree");
			eachElement.addAttribute("text", objNode.getModelName()); // 显示的铝箔

			sb.append(srcUrl).append("?").append("groupId=").append(objNode.getIdentifier()).append("&name=").append(objNode.getModelName());
			if (!StringUtil.isBlank(objNode.getTableCode())) {
				sb.append("&tableCode=").append(objNode.getTableCode());
			}

			param = null;
			actionUrl = path + objNode.getActionUrl();

			if (srcUrlAddParam != null && !srcUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(srcUrlAddParam + param);
			}
			eachElement.addAttribute("src", sb.toString());
			sb.delete(0, sb.length());
			sb.append(actionUrl);
			// 判断当前URL字符串中拼接方式
			if (sb.indexOf("?") > 0) {
				sb.append("&");
			} else {
				sb.append("?");
			}
			sb.append("time=").append(new Date().getTime());
			if (actionUrlAddParam != null && !actionUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(actionUrlAddParam);
			}
			if (!objNode.getActionUrl().equals("javascript:void(0)")) {
				eachElement.addAttribute("action", sb.toString());
//				eachElement.addAttribute("action", "javascript:showTable()");
			}
			sb.delete(0, sb.length());
			// 异步刷新iframeid
			eachElement.addAttribute("target", target);
			eachElement.addAttribute("identifier", String.valueOf(objNode.getIdentifier()));
			eachElement.addAttribute("isleaf", String.valueOf(objNode.getIsLeaf()));
		}
		sb = null;
		return document;
	}

	/**
	 * 为xtree.js生成组结构树的Dom信息，objNodeList必须是GroupObject的派生类的实例所组成的List�?
	 * 
	 * @param objNodeList
	 *            GroupObject的派生类的实例所组成的List�?
	 * @param srcUrl
	 *            点开树节点前的＋号对应的Action，例如listGroup.action，
	 *            本方法会在URL后自动拼加上此节点的Identifier
	 *            ，以groupId为参数名。例如：listGroup.action?groupId=1�?
	 * @param actionUrl
	 *            点击树节点对应的Action，例如listUser.action�?
	 * @param srcUrlAddParam
	 *            srcUrl的附加参数，例如type=userGroup，则�?��srcUrl其实�?listGroup.action?
	 *            groupId =1&type=userGroup。可以为null，或""�?
	 * @param actionUrlAddParam
	 *            actionUrl的附加参数，例如type=user，则�?��actionUrl其实�?listGroup.action?
	 *            groupId =1&type=user。可以为null，或""�?
	 * @param target
	 *            点击树节点连接的target，即HTML�?a href="#" target...></a>中的target�?
	 * 
	 * @return 为xtree.js生成树的Dom信息
	 * 
	 * 指定actionUrl�? target 为_self则只展开节点不影响其他页�?*
	 * 
	 * 
	 */
	public static Document createGroupDomTree(List objNodeList, String srcUrl,
			String actionUrl, String srcUrlAddParam, String actionUrlAddParam,
			String target) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();

		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		if (objNodeList == null) {
			return document;
		}

		StringBuffer sb = new StringBuffer();
		GroupObject objNode = null;
		for (int i = 0; i < objNodeList.size(); i++) {

			objNode = (GroupObject) objNodeList.get(i);
			Element eachElement = rootElement.addElement("tree");

			eachElement.addAttribute("text", objNode.getName());

			sb.append(srcUrl).append("?").append("groupId=").append(
					objNode.getIdentifier());
			if (srcUrlAddParam != null && !srcUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(srcUrlAddParam);
			}
			eachElement.addAttribute("src", sb.toString());
			sb.delete(0, sb.length());

			sb.append(actionUrl).append("?").append("groupId=").append(
					objNode.getIdentifier());
			if (actionUrlAddParam != null
					&& !actionUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(actionUrlAddParam);
			}
			eachElement.addAttribute("action", sb.toString());
			sb.delete(0, sb.length());

			eachElement.addAttribute("target", target);
			eachElement.addAttribute("isleaf", objNode.getIsLeaf().toString());
		}
		sb = null;

		return document;
	}

	/**
	 * 部门权限设置
	 * 
	 * 
	 */

	public static Document deptTree(List objNodeList, String srcUrl,
			String actionUrl, String srcUrlAddParam, String actionUrlAddParam,
			String target) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();

		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		if (objNodeList == null) {
			return document;
		}
		StringBuffer sb = new StringBuffer();
		GroupObject objNode = null;
		for (int i = 0; i < objNodeList.size(); i++) {

			objNode = (GroupObject) objNodeList.get(i);
			Element eachElement = rootElement.addElement("tree");

			eachElement.addAttribute("text", objNode.getName());

			sb.append(srcUrl).append("?").append("groupId=").append(
					objNode.getIdentifier());
			if (srcUrlAddParam != null && !srcUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(srcUrlAddParam);
			}
			sb.append("&").append("kind=setper");
			eachElement.addAttribute("src", sb.toString());
			sb.delete(0, sb.length());

			// sb.append(actionUrl).append("?").append("groupId=").append(
			// objNode.getIdentifier());
			// if (actionUrlAddParam != null
			// && !actionUrlAddParam.equalsIgnoreCase("")) {
			// sb.append("&").append(actionUrlAddParam);
			// }
			eachElement.addAttribute("action", String.valueOf(objNode
					.getIdentifier()));
			sb.delete(0, sb.length());

			eachElement.addAttribute("target", target);
			eachElement.addAttribute("isleaf", objNode.getIsLeaf().toString());
		}
		sb = null;

		return document;
	}

	/**
	 * 为xtree.js生成组结构树的Dom信息，objNodeList必须是GroupObject的派生类的实例所组成的List�?
	 * 
	 * @param objNodeList
	 *            GroupObject的派生类的实例所组成的List�?
	 * @param srcUrl
	 *            点开树节点前的＋号对应的Action，例如listGroup.action，
	 *            本方法会在URL后自动拼加上此节点的Identifier
	 *            ，以groupId为参数名。例如：listGroup.action?groupId=1�?
	 * @param srcUrlAddParam
	 *            srcUrl的附加参数，例如type=userGroup，则�?��srcUrl其实�?listGroup.action?
	 *            groupId =1&type=userGroup。可以为null，或""�?
	 * 
	 * @return 为xtree.js生成树的Dom信息
	 */
	public static Document createGroupDomCheckBoxTree(List objNodeList,
			String srcUrl, String srcUrlAddParam) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();

		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		if (objNodeList == null) {
			return document;
		}
		StringBuffer sb = new StringBuffer();
		GroupObject objNode = null;
		for (int i = 0; i < objNodeList.size(); i++) {

			objNode = (GroupObject) objNodeList.get(i);
			Element eachElement = rootElement.addElement("tree");

			eachElement.addAttribute("text", objNode.getName());

			sb.append(srcUrl).append("?").append("groupId=").append(
					objNode.getIdentifier());
			if (srcUrlAddParam != null && !srcUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(srcUrlAddParam);
			}
			eachElement.addAttribute("src", sb.toString());
			sb.delete(0, sb.length());

			eachElement.addAttribute("action", "");

			eachElement.addAttribute("identifier", objNode.getIdentifier()
					.toString());
			eachElement.addAttribute("value", objNode.getIdentifier()
					.toString());
			eachElement.addAttribute("isleaf", objNode.getIsLeaf().toString());
			eachElement.addAttribute("isItem", "group");
		}
		sb = null;

		return document;
	}

	public static Document createGroupDomLeafCheckBoxTree(List objNodeList,
			String srcUrl, String srcUrlAddParam) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();

		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		if (objNodeList == null) {
			return document;
		}
		StringBuffer sb = new StringBuffer();
		GroupObject objNode = null;
		for (int i = 0; i < objNodeList.size(); i++) {

			objNode = (GroupObject) objNodeList.get(i);
			if (objNode.getIsLeaf() != null && objNode.getIsLeaf().equals(0)) {
				Element eachElement = rootElement.addElement("tree");

				eachElement.addAttribute("text", objNode.getName());

				sb.append(srcUrl).append("?").append("groupId=").append(
						objNode.getIdentifier());
				if (srcUrlAddParam != null
						&& !srcUrlAddParam.equalsIgnoreCase("")) {
					sb.append("&").append(srcUrlAddParam);
				}
				eachElement.addAttribute("src", sb.toString());
				sb.delete(0, sb.length());

				eachElement.addAttribute("action", "folder");

				eachElement.addAttribute("identifier", objNode.getIdentifier()
						.toString());
				eachElement.addAttribute("value", objNode.getIdentifier()
						.toString());
				eachElement.addAttribute("isItem", "group");
			} else {
				Element eachElement = rootElement.addElement("tree");

				eachElement.addAttribute("text", objNode.getName());

				sb.append(srcUrl).append("?").append("groupId=").append(
						objNode.getIdentifier());
				if (srcUrlAddParam != null
						&& !srcUrlAddParam.equalsIgnoreCase("")) {
					sb.append("&").append(srcUrlAddParam);
				}
				eachElement.addAttribute("src", sb.toString());
				sb.delete(0, sb.length());

				eachElement.addAttribute("action", "");

				eachElement.addAttribute("identifier", objNode.getIdentifier()
						.toString());
				eachElement.addAttribute("value", objNode.getIdentifier()
						.toString());
				eachElement.addAttribute("isItem", "item");
			}
		}
		sb = null;

		return document;
	}

	/**
	 * 为xtree.js生成组结构树的Dom信息，groupObjNodeList必须是GroupObject的派生类的实例所组成的List�?
	 * 
	 * @param groupObjNodeList
	 *            GroupObject的派生类的实例所组成的List�?
	 * @param itemObjNodeList
	 *            和GroupObject的派生类相关联的Item类（例如：和UserGroup关联的UserInfo类）实例�?��
	 *            成的List�?
	 * @param srcUrl
	 *            点开树节点前的＋号对应的Action，例如listGroup.action，
	 *            本方法会在URL后自动拼加上此节点的Identifier
	 *            ，以groupId为参数名。例如：listGroup.action?groupId=1�?
	 * @param srcUrlAddParam
	 *            srcUrl的附加参数，例如type=userGroup，则�?��srcUrl其实�?listGroup.action?
	 *            groupId =1&type=userGroup。可以为null，或""�?
	 * 
	 * @return 为xtree.js生成树的Dom信息
	 */
	public static Document createItemDomCheckBoxTree(List groupObjNodeList,
			List itemObjNodeList, String srcUrl, String srcUrlAddParam) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();

		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		StringBuffer sb = new StringBuffer();
		GroupObject objNode = null;
		if (groupObjNodeList != null && groupObjNodeList.size() > 0) {
			for (int i = 0; i < groupObjNodeList.size(); i++) {

				objNode = (GroupObject) groupObjNodeList.get(i);
				Element eachElement = rootElement.addElement("tree");

				eachElement.addAttribute("text", objNode.getName());

				sb.append(srcUrl).append("?").append("groupId=").append(
						objNode.getIdentifier());
				if (srcUrlAddParam != null
						&& !srcUrlAddParam.equalsIgnoreCase("")) {
					sb.append("&").append(srcUrlAddParam);
				}
				eachElement.addAttribute("src", sb.toString());
				sb.delete(0, sb.length());

				eachElement.addAttribute("action", "folder");

				eachElement.addAttribute("identifier", objNode.getIdentifier()
						.toString());
				eachElement.addAttribute("value", objNode.getIdentifier()
						.toString());
				eachElement.addAttribute("isItem", "group");
				eachElement.addAttribute("isleaf", objNode.getIsLeaf()
						.toString());
			}
		}
		CommonObject itemObj = null;
		if (itemObjNodeList != null && itemObjNodeList.size() > 0) {
			for (int i = 0; i < itemObjNodeList.size(); i++) {
				itemObj = (CommonObject) itemObjNodeList.get(i);
				Element eachElement = rootElement.addElement("tree");
				eachElement.addAttribute("text", itemObj.getName());
				eachElement.addAttribute("src", "");
				eachElement.addAttribute("action", "");
				eachElement.addAttribute("identifier", itemObj.getIdentifier()
						.toString());
				eachElement.addAttribute("value", itemObj.getIdentifier()
						.toString());
				eachElement.addAttribute("isItem", "item");
			}
		}
		sb = null;

		return document;
	}

	/**
	 * 为xtree.js生成组结构树的Dom信息�?
	 * objNodeList必须是Module模型里根据parentModuleId查询出来的的实例�?��成的List�?
	 * 
	 * @param objNodeList
	 *            Module模型里根据parentModuleId查询出来的的实例
	 * 
	 * @param srcUrl
	 *            点开树节点前的＋号对应的Action，例如listGroup.action，
	 *            本方法会在URL后自动拼加上此节点的Identifier
	 *            ，以groupId为参数名。例如：listGroup.action?groupId=1�?
	 * 
	 * @param path
	 *            点击树节点对应的上下文路径�?例如�?portal
	 * 
	 * @param srcUrlAddParam
	 *            srcUrl的附加参数，例如type=userGroup，则�?��srcUrl其实�?listGroup.action?
	 *            groupId =1&type=userGroup。可以为null，或""�?
	 * 
	 * @param actionUrlAddParam
	 *            actionUrl的附加参数，例如type=user，则�?��actionUrl其实�?listGroup.action?
	 *            groupId =1&type=user。可以为null，或""�?
	 * 
	 * @param target
	 *            点击树节点连接的target，即HTML�?a href="#" target...></a>中的target�?
	 * 
	 * @param language
	 *            根据客户端浏览器使用的语�?��进行相关国际化�?
	 * 
	 * @return 返回 为xtree.js生成树的Dom信息
	 */
	public static Document createModuleDomTree(List objNodeList, String srcUrl,
			String path, String srcUrlAddParam, String actionUrlAddParam,
			String target, UserSessionObj obj) {

		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();

		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");

		if (objNodeList == null) {
			return document;
		}

		String actionUrl = null;

		String param = null;

		StringBuffer sb = new StringBuffer();

		Object objNode = null;

		for (int i = 0; i < objNodeList.size(); i++) {

			objNode = (Object) objNodeList.get(i);

			// if (obj != null && obj.isIsadc()) {
			// if (((WebModel) objNode).getModelName().equals("超管角色配置")) {
			// continue;
			// }
			// }
			Element eachElement = rootElement.addElement("tree");
			String iurl = ((WebModel) objNode).getImageUrl();
			// 如果是叶子节点(0:非叶子,1：叶子)
			if (0 != ((WebModel) objNode).getIsLeaf()
					&& 0 != ((WebModel) objNode).getParentId()) {
				if (!"#".equals(iurl)) {
					eachElement.addAttribute("closeUrl", iurl);
				} else {
					eachElement.addAttribute("closeUrl",
							"/js/tree/checkboxtree/images/filef_blank.gif");
				}
				// 二级以下非叶子节点
			} else {
				if (!"#".equals(iurl)) {
					eachElement.addAttribute("closeUrl", iurl);
					eachElement.addAttribute("openUrl", iurl);
				} else {
					eachElement.addAttribute("closeUrl",
							"/js/tree/checkboxtree/images/folder-open.png");
				}
			}
			// eachElement.addAttribute("openUrl",
			// "http://localhost:9080/mop330/images/commontree/birth_small.png");
			// eachElement.addAttribute("closeUrl",
			// "http://localhost:9080/mop330/js/tree/checkboxtree/images/mf.png");

			try {
				eachElement.addAttribute("text", ((WebModel) objNode)
						.getModelName());
			} catch (Exception ex) {
				eachElement.addAttribute("text", ((WebModel) objNode)
						.getModelName());
			}

			sb.append(srcUrl).append("?").append("groupId=").append(
					((WebModel) objNode).getIdentifier());
			if ((((WebModel) objNode).getActionUrl().equals("#"))
					|| (objNode.equals("#"))) {
				param = "parent";

				actionUrl = path + "/manage/right.jsp";

			} else {
				param = null;

				actionUrl = path + ((WebModel) objNode).getActionUrl();
			}

			if (srcUrlAddParam != null && !srcUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(srcUrlAddParam + param);
			}
			eachElement.addAttribute("src", sb.toString());

			sb.delete(0, sb.length());

			sb.append(actionUrl);
			// 判断当前URL字符串中拼接方式
			if (sb.indexOf("?") > 0) {
				sb.append("&");
			} else {
				sb.append("?");
			}
			sb.append("groupId=").append(((WebModel) objNode).getIdentifier());

			if (actionUrlAddParam != null
					&& !actionUrlAddParam.equalsIgnoreCase("")) {
				sb.append("&").append(actionUrlAddParam);
			}
			// sw add 930菜单�?��
			String inameString = ((WebModel) objNode).getModelName();
			if (!inameString.equals("权限管理")
					&& !inameString.equals("管理员维护")
					&& !inameString.equals("业务管理")
					&& !inameString.equals("业务统计")
					&& !((WebModel) objNode).getActionUrl().equals(
							"javascript:void(0)")) {
				eachElement.addAttribute("action", sb.toString());
			}
			// eachElement.addAttribute("action", sb.toString());
			sb.delete(0, sb.length());

			eachElement.addAttribute("target", target);

		}

		sb = null;

		return document;
	}

	/**
	 * 为xtree.js生成组结构树的Dom信息，objNodeList必须是GroupObject的派生类的实例所组成的List�?
	 * 
	 * @param objNodeList
	 *            栏目的派生类的实例所组成的List�?
	 * @param srcUrl
	 *            点开树节点前的＋号对应的Action，例如listGroup.action，
	 *            本方法会在URL后自动拼加上此节点的Identifier
	 *            ，以groupId为参数名。例如：listGroup.action?groupId=1�?
	 * @param actionUrl
	 *            点击树节点对应的Action，例如listUser.action�?
	 * @param srcUrlAddParam
	 *            srcUrl的附加参数，例如type=userGroup，则�?��srcUrl其实�?listGroup.action?
	 *            groupId =1&type=userGroup。可以为null，或""�?
	 * @param actionUrlAddParam
	 *            actionUrl的附加参数，例如type=user，则�?��actionUrl其实�?listGroup.action?
	 *            groupId =1&type=user。可以为null，或""�?
	 * @param target
	 *            点击树节点连接的target，即HTML�?a href="#" target...></a>中的target�?
	 * 
	 * @return 为xtree.js生成树的Dom信息
	 */
	public static Document createColumnTree(List objNodeList, String srcUrl,
			String actionUrl, String srcUrlAddParam, String actionUrlAddParam,
			String target) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();

		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");

		if (objNodeList == null) {
			return document;
		}

		StringBuffer sb = new StringBuffer();
		GroupObject objNode = null;
		if (objNodeList != null && objNodeList.size() > 0) {
			for (int i = 0; i < objNodeList.size(); i++) {

				objNode = (GroupObject) objNodeList.get(i);
				// Element eachElement = rootElement.addElement("tree");
				Element eachElement = DocumentHelper.createElement("tree");

				eachElement.addAttribute("text", objNode.getName());

				sb.append(srcUrl).append("?").append("groupId=").append(
						objNode.getIdentifier());
				if (srcUrlAddParam != null
						&& !srcUrlAddParam.equalsIgnoreCase("")) {
					sb.append("&").append(srcUrlAddParam);
				}
				eachElement.addAttribute("src", sb.toString());
				sb.delete(0, sb.length());

				sb.append(actionUrl).append("?").append("groupId=").append(
						objNode.getIdentifier());
				if (actionUrlAddParam != null
						&& !actionUrlAddParam.equalsIgnoreCase("")) {
					sb.append("&").append(actionUrlAddParam);
				}
				if (actionUrl != null) {
					eachElement.addAttribute("action", sb.toString());
				}
				sb.delete(0, sb.length());

				eachElement.addAttribute("target", target);
				eachElement.addAttribute("isleaf", objNode.getIsLeaf()
						.toString());
				eachElement.addAttribute("id", "" + objNode.getIdentifier());
				// 遍历Node,生成树形结构
				if (objNode.getParentId() != null && objNode.getParentId() > 0) {
					Element el = getParentNodeById(rootElement, objNode
							.getParentId().toString());
					if (el != null) {
						el.add(eachElement);
					} else {
						rootElement.add(eachElement);
					}
				} else {
					rootElement.add(eachElement);
				}
			}
		}

		sb = null;

		return document;
	}

	/**
	 * 获取根节点下边的�?��子节�?
	 * 
	 * @param rootElement
	 * @param id
	 * @return Element
	 */
	public static Element getParentNodeById(Element rootElement, String id) {

		Iterator<Element> it = rootElement.elementIterator();
		if (it != null) {
			while (it.hasNext()) {
				Element el = it.next();
				Attribute att = el.attribute("id");
				if (att != null && att.getText().equals(id)) {
					return el;
				}
			}
		}

		return null;
	}

	/**
	 * 客户端�?信记录查�?and cluster member
	 * 
	 * @param groupObjNodeList
	 * @param itemObjNodeList
	 * @param srcUrl
	 * @param srcUrlAddParam
	 * @return
	 */
	public static Document createItemDomTree(List groupObjNodeList,
			List itemObjNodeList, String srcUrl, String srcUrlAddParam,
			String target, String base, String clusterFlag) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();

		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		StringBuffer sb = new StringBuffer();
		GroupObject objNode = null;
		if (groupObjNodeList != null && groupObjNodeList.size() > 0) {
			for (int i = 0; i < groupObjNodeList.size(); i++) {

				objNode = (GroupObject) groupObjNodeList.get(i);
				Element eachElement = rootElement.addElement("tree");

				eachElement.addAttribute("text", objNode.getName());

				sb.append(srcUrl).append("?").append("groupId=").append(
						objNode.getIdentifier()).append(
						"&clusterFlag=" + clusterFlag + "&kind=clusterOwner");
				if (srcUrlAddParam != null
						&& !srcUrlAddParam.equalsIgnoreCase("")) {
					sb.append("&").append(srcUrlAddParam);
				}
				eachElement.addAttribute("src", sb.toString());
				sb.delete(0, sb.length());

				eachElement.addAttribute("action", "folder");

				// eachElement.addAttribute("actionDepart", "/aaa=" +
				// objNode.getIdentifier());

				if (target != null && target.equals("listMessageFrame")) {
					eachElement
							.addAttribute(
									"actionDepart",
									base
											+ "/manage/usercommu/userCommuAction!listMessage.portal?groupId="
											+ objNode.getIdentifier());
				} else {
					if (clusterFlag != null && clusterFlag.equals("true")) {
						eachElement
								.addAttribute(
										"actionDepart",
										base
												+ "/manage/usermanage/cluster/clusterDispatherAction!listEmp.portal?groupId="
												+ objNode.getIdentifier()
												+ "&clusterFlag=true&"
												+ srcUrlAddParam);
					} else {
						eachElement
								.addAttribute(
										"actionDepart",
										base
												+ "/manage/contentmanage/sharefile/sharefileAjax!listEmp.portal?groupId="
												+ objNode.getIdentifier() + "&"
												+ srcUrlAddParam);
					}
				}

				eachElement.addAttribute("identifier", objNode.getIdentifier()
						.toString());
				eachElement.addAttribute("value", objNode.getIdentifier()
						.toString());
				eachElement.addAttribute("isItem", "group");

				eachElement.addAttribute("target", target);
			}
		}
		CommonObject itemObj = null;
		if (itemObjNodeList != null && itemObjNodeList.size() > 0) {
			for (int i = 0; i < itemObjNodeList.size(); i++) {
				itemObj = (CommonObject) itemObjNodeList.get(i);
				Element eachElement = rootElement.addElement("tree");
				eachElement.addAttribute("text", itemObj.getName());
				eachElement.addAttribute("src", "");
				eachElement.addAttribute("action", "");

				if (target != null && target.equals("listMessageFrame")) {
					eachElement
							.addAttribute(
									"actionDepart",
									base
											+ "/manage/usercommu/userCommuAction!listMessage.portal?empId="
											+ itemObj.getIdentifier());
				} else {
					eachElement.addAttribute("actionDepart", "/bbb="
							+ itemObj.getIdentifier());
				}

				eachElement.addAttribute("identifier", itemObj.getIdentifier()
						.toString());
				eachElement.addAttribute("value", itemObj.getIdentifier()
						.toString());
				eachElement.addAttribute("isItem", "item");

				eachElement.addAttribute("target", target);
			}
		}
		sb = null;

		return document;
	}

	/**
	 * 为xtree.js生成组结构树的Dom信息，objNodeList必须是GroupObject的派生类的实例所组成的List�?
	 * 
	 * @param objNodeList
	 *            GroupObject的派生类的实例所组成的List�?
	 * @param srcUrl
	 *            点开树节点前的＋号对应的Action，例如listGroup.action，
	 *            本方法会在URL后自动拼加上此节点的Identifier
	 *            ，以groupId为参数名。例如：listGroup.action?groupId=1�?
	 * @param srcUrlAddParam
	 *            srcUrl的附加参数，例如type=userGroup，则�?��srcUrl其实�?listGroup.action?
	 *            groupId =1&type=userGroup。可以为null，或""�?
	 * 
	 * @return 为xtree.js生成树的Dom信息
	 */
	public static Document createGroupDomCheckBoxTreeSyn(List objNodeList) {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();

		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");

		if (objNodeList == null) {
			return document;
		}

		GroupObject objNode = null;
		Integer isLeaf = -1;
		for (int i = 0, sizeLength = objNodeList.size(); i < sizeLength; i++) {

			objNode = (GroupObject) objNodeList.get(i);

			if (objNode.getParentId() == 0) {
				Element eachElement = rootElement.addElement("tree");
				eachElement.addAttribute("text", objNode.getName());
				eachElement.addAttribute("action", "");
				eachElement.addAttribute("identifier", objNode.getIdentifier()
						.toString());
				eachElement.addAttribute("value", objNode.getIdentifier()
						.toString());
				eachElement.addAttribute("isItem", "group");
				isLeaf = objNode.getIsLeaf();
				eachElement.addAttribute("isleaf", String.valueOf(isLeaf));

				if (isLeaf == 0) {
					initDomList(eachElement,
							(Integer) (objNode.getIdentifier()), objNodeList);
				}
			}
		}
		return document;
	}

	/**
	 * 递归将数据传入指定类�?
	 * 
	 * @param jtbList
	 *            bean 对象,里面�? list 属�?,存储它的�?��子类
	 * @param objNodeList
	 *            �?��数据 zhangzhanliang@live.cn
	 */
	public static int initDomList(Element eachElement, Integer parentId,
			List objNodeList) {
		GroupObject objNode = null;
		for (int i = 0, size = objNodeList.size(); i < size; i++) {
			objNode = (GroupObject) objNodeList.get(i);
			if (parentId.intValue() == objNode.getParentId().intValue()) {
				Element eachElementChild = eachElement.addElement("tree");
				eachElementChild.addAttribute("text", objNode.getName());
				eachElementChild.addAttribute("action", "");
				eachElementChild.addAttribute("identifier", objNode
						.getIdentifier().toString());
				eachElementChild.addAttribute("value", objNode.getIdentifier()
						.toString());
				eachElementChild.addAttribute("isItem", "group");
				eachElementChild.addAttribute("isleaf", String.valueOf(objNode
						.getIsLeaf()));

				if (objNode.getIsLeaf() == 0) {
					initDomList(eachElementChild, (Integer) (objNode
							.getIdentifier()), objNodeList);
				}
			}
		}
		return 0;
	}

	public static Document deptTreeSup(List objNodeList, String srcUrl,
			String actionUrl, String srcUrlAddParam, String actionUrlAddParam,
			String target) {
		Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根tree */
		Element rootElement = document.addElement("tree");
		deptTreeAppend(rootElement, objNodeList, srcUrl, actionUrl,
				srcUrlAddParam, actionUrlAddParam, target);
		return document;
	}

	public static Element deptTreeAppend(Element rootElement, List objNodeList,
			String srcUrl, String actionUrl, String srcUrlAddParam,
			String actionUrlAddParam, String target) {
		StringBuffer sb = new StringBuffer();
		GroupObject objNode = null;
		for (int i = 0; i < objNodeList.size(); i++) {

			objNode = (GroupObject) objNodeList.get(i);
			Element eachElement = rootElement.addElement("tree");

			eachElement.addAttribute("text", objNode.getName());
			if (StringUtils.isNotBlank(srcUrl)) {
				sb.append(srcUrl).append("?groupId=").append(
						objNode.getIdentifier());
			}
			if (StringUtils.isNotBlank(srcUrlAddParam)) {
				sb.append("&").append(srcUrlAddParam);
			}
			eachElement.addAttribute("src", sb.toString());
			sb.delete(0, sb.length());
			if (StringUtils.isNotBlank(actionUrl)) {
				sb.append(actionUrl).append("?groupId=").append(
						objNode.getIdentifier());
			}
			if (StringUtils.isNotBlank(actionUrlAddParam)) {
				sb.append("&").append(actionUrlAddParam);
			}
			eachElement.addAttribute("action", sb.toString());
			sb.delete(0, sb.length());

			eachElement.addAttribute("target", target);
			eachElement.addAttribute("isleaf", objNode.getIsLeaf().toString());
		}
		sb = null;

		return rootElement;
	}
}
