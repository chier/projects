<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>

<html>
	<head>
		<!-- 系统广播 管理范围单独设置页面  -->
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<%@ include file="/commons/meta.jsp"%>
		<link href="${base }/css/role/3select.css" rel="stylesheet"
			type="text/css" media="all" />
		<link href="${base }/css/role/common.css" rel="stylesheet"
			type="text/css" media="all" />
		<link href="${base }/css/role/layout.css" rel="stylesheet"
			type="text/css" media="all" />
		<link href="${base }/css/role/main.css" rel="stylesheet"
			type="text/css" media="all" />

		<style>
</style>
	</head>
	<body style="background:none;" onload="focus()">
		<input id="thisDeptId" name="thisDeptId" value="all" type="hidden" />
		<input type="hidden" value="0" id="addMsg" name="addMsg" />
		<input type="hidden" value="0" id="ifsearch" name="ifsearch" />
		<div class="tanchu tanchu_xzfw"
			style="background:none;border:none; width:auto;">
			<div class="tanchu_xzfw_body">
				<div class="vspacer floatleft"></div>
				<div class="midbox floatleft">
					<div class="h28">
						符合条件的用户
						<span class="pdlr4 lightgray">(双击直接添加)</span>
					</div>
					<div class="insbox3">
						<div class="inputbox">
							<input type="text" class="normalinput input floatleft"
								onfocus="deleteText(this)" id="userName" name="userName"
								onblur="addText(this)" onkeydown="toFind(event)" />
							<a id="userNamehref" href="#"
								onclick="UserList.findUser(this); return false;"
								class="inputa floatright"></a>
						</div>
						<div class="insbox4" id="waitChange">
							<ul id="leftEmpUl" class="Per_sel_ul"></ul>
						</div>
					</div>
					<table cellspacing="0" cellpadding="0" border="0" width="100%"
						class="default_pgToolbar" id="dpageInfo" name="dpageInfo"
						style="Display:none; background:none; border:0;">
						<tbody>
							<tr>
								<td>
									<table width="100%">
										<tbody>
											<tr>

												<td align="right">
													<table cellspacing="0" border="0" cellspadding="0">
														<tbody>
															<tr>
																<td valign="middle">
																	<div class="default_pgBtn default_pgFirst" title="首页"
																		id="first" name="first"></div>
																</td>
																<td valign="middle">
																	<div class="default_pgBtn default_pgPrev" title="上页"
																		id="prev" name="prev"></div>
																</td>

																<td valign="middle" id="totalPage" name="totalPage">
																	1/1
																</td>
																<td valign="middle">
																	<div class="default_pgBtn default_pgNext" title="下页"
																		id="next" name="next"></div>
																</td>
																<td valign="middle">
																	<div class="default_pgBtn default_pgLast" title="尾页"
																		id="last" name="last"></div>
																</td>


															</tr>
														</tbody>
													</table>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="vspacer floatleft"></div>
				<div class="midbt floatleft">
					<p>
						<button class="button button1" id="addEmp" name="addEmp"
							onclick="UserList.addLeftEmp();"></button>
					</p>
					<div class="spacer"></div>
					<p>
						<button class="button button2" id="deleteEmp" name="deleteEmp"
							onclick="UserList.removeRightEmp();"></button>
					</p>
					<div class="spacer"></div>
					<p>
						<button class="button button3" id="addAllEmp" name="addAllEmp"
							onclick="UserList.allAddLeft()"></button>
					</p>
					<div class="spacer"></div>
					<p>
						<button class="button button4" id="deleteAllEmp"
							name="deleteAllEmp" onclick="UserList.allRemoveRight();"></button>
					</p>
				</div>
				<div class="vspacer floatleft"></div>
				<div class="midbox floatleft">
					<div class="h28">
						已选部门/用户
						<span class="pdlr4 lightgray">(双击删除)</span>
					</div>
					<div class="insbox3">
						<div class="inputbox">
							<input type="text" class="normalinput input floatleft"
								id="choosedUserName" name="choosedUserName"
								onfocus="deleteText(this)" onblur="addText(this)"
								onkeydown="toFind(event)" />
							<a id="choosedUserNamehref" href="#"
								onclick="UserList.findChoosedUser(); return false;"
								class="inputa floatright"></a>
						</div>
						<div id="nowChange" class="insbox4">
							<ul id="rightEmpUl" class="Per_sel_ul">
								<li>显示内容</li>	
							</ul>
						</div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</body>
</html>
