<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>预警分析-设置默认值</title>
		<link href="${base}/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<link rel="stylesheet" href="${base }/js/ztree/css/demo.css"
			type="text/css" />
		<link rel="stylesheet"
			href="${base }/js/ztree/css/zTreeStyle/zTreeStyle.css"
			type="text/css" />
		<style type="text/css">
		.Per_sel_ul{padding:0px;border: 1px solid #b2bac5;}
		.Per_sel_ul li{width:100%; display:block; height:25px; cursor:pointer; *margin-bottom:-2px; text-overflow: ellipsis; -moz-text-overflow: ellipsis;
		overflow:hidden; line-height:25px;}
		/*选中状态 */
		 .Per_sel_ul li.bg{background:#8ACCFB;}
				
		.Per_sel_ul label{padding:0 0 0 16px; cursor:pointer; display:block; width:100%;white-space: nowrap; }
		</style>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.excheck-3.5.js"></script>

		<script language="javascript" src="${base}/js/warning/settingDefault.js"></script>


	</head>
	<body>
		<div style="width:auto;height:auto;">
			<div class="title">
				<h4>
					<a href="${base }/html/adminSetting/index.jsp">管理设置</a> &gt;&gt;
					<a href="${base}/manage/warning/warningAction.${actionExt}">预警设置</a>
				</h4>
			</div>
			<div style="height:463px;width:240px;float:left;">
				<ul id="treeDemo-warning" class="ztree"
					style="width:228px;height:100%;margin:0;border-bottom:none;"></ul>
			</div>
			<div style="height:474px;border:1px solid #617775;">
				<div style="height:80px;width:auto;">
					&nbsp;&nbsp;
					<input type="button" value="保存" id="btnSave" name="btnSave" class="bot1other"/>
					<a href="${base}/manage/warning/warningAction.${actionExt}" style="float:right;margin-right:10px;color:#000;">预警设置</a>
					<br />
				</div>
				<div style="height:108px;">
					<input type="hidden" value="" id="hidId" name="hidId" />
					<input type="hidden" value="" id="hidFirstMin" name="hidFirstMin" />
       				<input type="hidden" value="" id="hidFirstMax" name="hidFirstMax" />
					<table>
						<tr>
							<td>
								<input type="button" value="指标" onclick="btnFirstClick();" class="bot1other"/>
							</td>
							<td>
								<table id="tabFirst">
									<tr>
										<td style="width:140px">

										</td>
										<td style="display:none;">
										</td>
										<td style="display:none;">
										</td>
										<td style="width:80px">
											上限值
										</td>
										<td style="width:80px">
											<input type="text" name="hidFirstMax" class="txtValue"
												value="">
										</td>
										<td style="width:80px">
											下限值
										</td>
										<td style="width:80px">
											<input type="text" name="hidFirstMin" class="txtValue"
												value="" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>
