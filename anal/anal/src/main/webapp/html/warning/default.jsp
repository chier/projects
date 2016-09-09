<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>预警分析</title>
		<link href="${base}/css/main.css" rel="stylesheet" type="text/css" media="all" />
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

		<script language="javascript" src="${base}/js/warning/warning.js"></script>
		

	</head>
	<body>
		<div style="width:auto;height:auto;">
			<div class="title">
				<h4>
					<a href="${base }/html/adminSetting/index.jsp">管理设置</a> 
					&gt;&gt;
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
					<input type="button" value="查询" id="btnSelect" name="btnSelect" class="bot1other" />
					<input type="button" value="保存" id="btnSave" name="btnSave" class="bot1other" />
					<a href="${base}/manage/warning/warningAction!settingDefault.${actionExt}" style="float:right;margin-right:10px;color:#000;">设置默认值</a>
					<br />
					<br />
					标题
					<input type="text" name="txtName" id="txtName" value=""/>
				</div>
				<div style="height:108px;">
					<table>
						<tr>
							<td>
								<input type="button" value="第一指标" onclick="btnFirstClick();" class="bot1other" />
							</td>
							<td>
								<table id="tabFirst">
									<tr>
										<td style="width:140px">
											 
										</td>
										<td style="display:none;">
										</td>
										<td  style="display:none;">
										</td>
										<td style="width:80px">
											上限值
										</td>
										<td style="width:80px">
											<input type="text" name="hidFirstMax" class="txtValue" value="">
										</td>
										<td style="width:80px">
											下限值
										</td>
										<td style="width:80px">
											<input type="text" name="hidFirstMin" class="txtValue" value="" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" value="第二指标" onclick="btnSecondClick();" class="bot1other" />
							</td>
							<td>
								<table id="tabSecond">
									<tr>
										<td style="width:140px">
											 
										</td> <td style="display:none;">
										</td>
										<td  style="display:none;">d
										<br /></td>
										<td style="width:80px">
											上限值
										</td>
										<td style="width:80px">
											<input type="text" name="hidSecondMax" class="txtValue" value="" />
										</td>
										<td style="width:80px">
											下限值
										</td>
										<td style="width:80px">
											<input type="text" name="hidSecondMin" class="txtValue" value="" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<div style="height:240px;">
					<div style="width:31%;float:left;position:relative;left:50px;">
						<ul>
							<li style="list-style:none;font-size:20px;">
								<c:forEach var="statUnit" items="${statUnitList}" varStatus="s">
									<c:if test="${s.first}">
												${statUnit}
										</c:if>
								</c:forEach>
							</li>
							<li>
								<ul class="Per_sel_ul"
									style="OVERFLOW:auto;height:225px;width:150px;">
									<c:forEach var="statUnit" items="${statUnitList}" varStatus="s">
										<c:if test="${not s.first}">
											<li id="li_${statUnit}" onclick="perUlLiClick($(this));">
												<label>
													${statUnit}
												</label>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</li>
						</ul>
					</div>
					<div style="width:68%;">
						<ul>
							<li style="list-style:none;">
								选择区域
							</li>
							<li style="list-style:none;">
								<select id="selArea">
									<option value="PRIV_NAME">
										省
									</option>
									<option value="CITY_NAME">
										市
									</option>
									<option value="COUNT_NAME">
										区县
									</option>
									<option value="TOWN_NAME">
										乡镇
									</option>
									<option value="VILL_NAME">
										村或居委会
									</option>
								</select>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="divFormWarning" style="display:none;">
		<form method="post" id="formWarning" target="warnIframe" action="actionurl">
			<input type="hidden" value="" id="hidFirstCode" name="hidFirstCode" />
			<input type="hidden" value="" id="hidFirstName" name="hidFirstName" />
			<input type="hidden" value="" id="hidFirstTable" name="hidFirstTable" />
			<input type="hidden" value="" id="hidFirstMin" name="hidFirstMin" />
       		<input type="hidden" value="" id="hidFirstMax" name="hidFirstMax" />
       		
			<input type="hidden" value="" id="hidSecondCode" name="hidSecondCode" />
			<input type="hidden" value="" id="hidSecondName" name="hidSecondName" />
			<input type="hidden" value="" id="hidSecondTable" name="hidSecondTable" />
			
	       <input type="hidden" value="" id="hidSecondMin" name="hidSecondMin" />
	       <input type="hidden" value="" id="hidSecondMax" name="hidSecondMax" />
        
			<input type="hidden" value="" id="hidArea" name="hidArea" />
		</form>
		</div>
	</body>
</html>
