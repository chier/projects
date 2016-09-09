package com.cmcc.framework.controller.action.workflow;

import java.util.List;

import com.cmcc.common.controller.action.CommonAction;
import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.model.Workflow;
import com.cmcc.framework.model.WorkflowNode;

public class WorkflowAction extends CommonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1113258392028135994L;

	/**
	 * 工作流首页信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 */
	@GenericLogger(operateMark = OperateMark.ADMIN_ADD, operateDescription = "管理员维护管理-创建管理员", isOperateEmployee = true, isOperateRecords = false)
	public String execute() throws Exception {
		List<Workflow> workflowList = this.workflowManager.findAllWorkflow();
		getRequest().setAttribute("workflowList", workflowList);
		logger.info("execute over");
		return "list";
	}

	/**
	 * 点击后 查看节点信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findByNode() throws Exception {
		String str = getRequest().getParameter("wid");
		Integer wid = 0;
		if (StringUtil.isNumeric(str)) {
			wid = Integer.valueOf(str);
		}
		List<WorkflowNode> nodeList = this.workflowManager.findNodeByWid(wid);
		getRequest().setAttribute("workflowNodeList", nodeList);
		return "listNode";
	}

	/**
	 * 浏览数据库信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectDB() throws Exception {
		String str = getRequest().getParameter("nid");
		Integer nid = 0;
		if (StringUtil.isNumeric(str)) {
			nid = Integer.valueOf(str);
		}
		List[] list = this.workflowManager.findDBbyNid(nid);
		List colList = list[0];
		List stList = list[1];
		getRequest().setAttribute("colList", colList);
		getRequest().setAttribute("stList", stList);
		return "showDB";
	}
}
