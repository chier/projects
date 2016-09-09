<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link href="${base }/css/page.css" rel="stylesheet" type="text/css"
	media="all" />
<table cellspacing="0" cellpadding="0" border="0" width="100%"
	class="default_pgToolbar">
	<tbody>
		<tr>
			<c:if test="${topFlag == 'true'}">
				<td>
					<a
						href="${base }/manage/usercommu/lmtWordAction!lmtWordIndex.portal"><<返回排行榜
					</a>
				</td>
			</c:if>
			<td>
				<table width="100%">
					<tbody>
						<tr>
							<td align="left" width="170">
								<table cellspacing="0" border="0"
									cellspadding="0">
									<tbody>
										<tr>
											<td align="left" valign="middle">
												<fmt:message key="efetionmanage.common.page2.searchresult">
													<fmt:param value="${pageInfo.resultCount}" />
													<fmt:param value="${pageInfo.pageSize}" />
												</fmt:message>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
							<td align="right" width="*">
								<table cellspacing="0" border="0" cellspadding="0">
									<tbody>
										<tr>
											<td valign="middle">
												<div class="default_separator" />
											</td>
											<c:choose>
												<c:when test="${pageInfo.page <= 1 }">
													<td valign="middle">
														<div
															title="<fmt:message key="efetionmanage.common.page2.first_page"/>"
															class="default_pgBtn default_pgFirst default_pgFirstDisabled" />
													</td>
													<td valign="middle">
														<div
															title="<fmt:message key="efetionmanage.common.page2.previous_page"/>"
															class="default_pgBtn default_pgPrev default_pgPrevDisabled" />
													</td>
												</c:when>
												<c:otherwise>
													<td valign="middle">
														<div
															title="<fmt:message key="efetionmanage.common.page2.first_page"/>"
															class="default_pgBtn default_pgFirst"
															onclick="JavaScript:goPage('1');" />
													</td>
													<td valign="middle">
														<div
															title="<fmt:message key="efetionmanage.common.page2.previous_page"/>"
															class="default_pgBtn default_pgPrev"
															onclick="JavaScript:goPage('${pageInfo.previousPage}');" />
													</td>
													
												</c:otherwise>
											</c:choose>
											
											<td valign="middle">
												<div class="default_separator" />
											</td>
											<c:choose>
												<c:when test="${pageInfo.totalPage > 7}">
													<!-- 页面的起始页 -->
													<c:set var="startPage"
														value="${pageInfo.page-3>1?pageInfo.page-3:1}" />
													<!-- 页面的结束页 -->
													<c:set var="endPage"
														value="${startPage+6>pageInfo.totalPage?pageInfo.totalPage:startPage+6}" />
													<!-- 当结束页是页面总数时，情况变化 -->
													<c:if
														test="${(pageInfo.totalPage == endPage)&&(endPage >7)}">
														<c:set var="startPage" value="${pageInfo.totalPage - 6}" />
													</c:if>
													<td valign="middle" width="*">
														<div>
															<ul class="pageLink">
																<c:forEach begin="${startPage}" end="${endPage}"
																	varStatus="statusPage">
																	<c:choose>
																	   <%-- 后期修改，有时page默认为1，有时 page 默认为0 --%>
																		<c:when test="${(statusPage.index  == pageInfo.page) || (pageInfo.page == 0)}">
																			<li class="nolink">&nbsp;${statusPage.index}</li>
																		</c:when>
																		<c:otherwise>
																			<li>
																				<a href="#" onclick="JavaScript:goPage('${statusPage.index}')">
																					${statusPage.index} </a>
																			</li>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</ul>
														</div>
													</td>
												</c:when>
												<c:otherwise>
													<td valign="middle" width="*">
														<div>
															<ul class="pageLink">
																<c:forEach begin="1" end="${pageInfo.totalPage > 1?pageInfo.totalPage:1}"
																	varStatus="statusPage">
																	<c:choose>
																		<c:when test="${(statusPage.index  == pageInfo.page) || (pageInfo.page == 0)}">
																			<li class="nolink">
																				&nbsp;${statusPage.index}
																			</li>
																		</c:when>
																		<c:otherwise>
																			<li>
																				<a onclick="JavaScript:goPage('${statusPage.index}')">
																					${statusPage.index} 
																				</a>
																			</li>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</ul>
														</div>
													</td>
												</c:otherwise>
											</c:choose>
											
											<td valign="middle">
												<div class="default_separator" />
											</td>
											<c:choose>
												<c:when test="${pageInfo.page >= pageInfo.totalPage}">
													<td valign="middle">
														<div
															title="<fmt:message key="efetionmanage.common.page2.next_page"/>"
															class="default_pgBtn default_pgNext default_pgNextDisabled" />
													</td>
													<td valign="middle">
														<div
															title="<fmt:message key="efetionmanage.common.page2.last_page"/>"
															class="default_pgBtn default_pgLast default_pgLastDisabled" />
													</td>
												</c:when>
												<c:otherwise>
													<td valign="middle">
														<div
															title="<fmt:message key="efetionmanage.common.page2.next_page"/>"
															class="default_pgBtn default_pgNext"
															onclick="JavaScript:goPage('${pageInfo.nextPage}');" />
													</td>
													<td valign="middle">
														<div
															title="<fmt:message key="efetionmanage.common.page2.last_page"/>"
															class="default_pgBtn default_pgLast"
															onclick="JavaScript:goPage('${pageInfo.totalPage}');" />
													</td>
												</c:otherwise>
											</c:choose>
											<td valign="middle">
												<div class="default_separator" />
											</td>
											<td valign="middle">
												<fmt:message key="efetionmanage.common.page2.totalpage">
													<fmt:param value="${pageInfo.totalPage >1?pageInfo.totalPage:1}" />
												</fmt:message>
											</td>
											<td valign="middle">
												<div class="default_separator" />
											</td>
											<td valign="middle">
												<fmt:message key="efetionmanage.common.page2.jump_to" />
											</td>
											<td valign="middle">
												<input type="text"
													title="<fmt:message key="efetionmanage.common.page2.pagenum"/>"
													value="" class="default_pgCurrentPage" id="toPage"
													name="toPage" />
											</td>
											<td valign="middle">
												<fmt:message key="efetionmanage.common.page2.pages" />
											</td>
											<td valign="middle">
												<div class="default_separator" />
											</td>
											<td align="right" width="40" valign="middle">
												<input type="button" value="确定"
													style="width:40px;height:22px;text-decoration:none;"
													onclick="JavaScript:checkpage();" />
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
<script type="text/javascript">



$(document).ready(function(){
	
	$("#toPage").keypress(function(e)     
    {     
     switch(e.which)     
        {        
        case 13: 
            if(document.getElementById('toPage').value == ""){
            	$.prompt('<fmt:message key="efetionmanage.common.pageisnull" />',{buttons:{是:true},alertType:'msg'});
   				document.getElementById('toPage').value="";
   				$("#toPage").blur();
   				return false;
            }else if($("#toPage").val() > ${pageInfo.totalPage}){
   				$.prompt('<fmt:message key="efetionmanage.common.outofpagetotal" />',{buttons:{是:true},alertType:'msg'});
   				document.getElementById('toPage').value="";
   				$("#toPage").blur();
   				return false;
   			}else if($("#toPage").val() == 0 ){
   			    $.prompt('<fmt:message key="efetionmanage.common.pageiszero" />',{buttons:{是:true},alertType:'msg'});
   				document.getElementById('toPage').value="";
   				$("#toPage").blur();
   				return false;
   			} else if($("#toPage").val() < 0 ){
   				$.prompt('<fmt:message key="efetionmanage.common.page2.pageiszerosmall" />',{buttons:{是:true},alertType:'msg'});
   				document.getElementById('toPage').value="";
   				$("#toPage").blur();
   				return false;
   			} else if(!isInteger($("#toPage").val())){
   				$.prompt("<fmt:message key='efetionmanage.common.not_integer'/>",{buttons:{是:true},alertType:'msg'});
   				$("#toPage").blur();
   				return false;
   			}
   			goPage($("#toPage").val());
   			 $("#toPage").blur();
       		 return false;
       		 //break  页面会跳转二次
        }    
    });  
	
});

function checkpage(){
	
   if(document.getElementById('toPage').value > ${pageInfo.totalPage}){
   		$.prompt('<fmt:message key="efetionmanage.common.outofpagetotal" />',{buttons:{是:true},alertType:'msg'});
   		document.getElementById('toPage').value="";
   } else if(document.getElementById('toPage').value == "" ){
   		$.prompt('<fmt:message key="efetionmanage.common.pageisnull" />',{buttons:{是:true},alertType:'msg'});
   		document.getElementById('toPage').value="";
   } else if(document.getElementById('toPage').value == 0 ){
   		$.prompt('<fmt:message key="efetionmanage.common.pageiszero" />',{buttons:{是:true},alertType:'msg'});
   		document.getElementById('toPage').value="";
   } else if(document.getElementById('toPage').value < 0 ){
   		$.prompt('<fmt:message key="efetionmanage.common.page2.pageiszerosmall" />',{buttons:{是:true},alertType:'msg'});
   		document.getElementById('toPage').value="";
   } else{
   		goPage(document.getElementById('toPage').value);
   }
}
</script>
