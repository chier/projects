package com.cmcc.framework.controller.action.loap;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;

import com.cmcc.common.Global;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.util.DomTreeBuilderUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.CustomVO;

/**
 * 选择目录树
 * 
 * @author zhangzhanliang
 * 
 */
public class LoapCustomTreeAction extends WebActionBase {

	private Logger logger = Logger.getLogger(LoapCustomTreeAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -4286524305604525550L;

	/**
	 * 
	 */
	public String execute() throws BusinessException {
		try {
			List<CustomVO> jlist = null;
			if(this.getUserSessionInfo().getRoleLevel().intValue() == 0){
				jlist = this.customManager.findByGroup();
			}else{
				jlist = this.customManager.findByGroup(getUserSessionInfo().getRid());
			}

			this.getResponse().setContentType(
					"text/xml;charset=" + Global.CHARSET);
			ServletOutputStream stream = this.getResponse().getOutputStream();

			XMLWriter writer = new XMLWriter(stream);
			if (jlist != null && jlist.size() > 0) {
				writer.write(DomTreeBuilderUtil.createCustomDomTree(jlist,
						"", this.getBasePath(), "type=", "", ""));
			} else {
				Document document = DocumentHelper.createDocument();
				writer.write(document);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException("jrxml文件列表获取错误");
		}
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String index() throws BusinessException {
		HttpServletRequest request = this.getRequest();

//		CustomVO vo = new CustomVO();
//		vo.setModelName("定制分析目录");
//		vo.setIdentifier(0);
//		vo.setParentId(0);
//		vo.setIsLeaf(0);
//		vo.setSql("");
//		vo.setActionUrl(getContext() + "/manage/loap/loapCustomTreeAction."
//				+ Global.ACTION_EXT + "?groupId=0&name=定制分析&url=");
//		request.setAttribute("rootGroup", vo);
//		return "loapCustomTree";
		return "saveLoapTree";
	}

}
