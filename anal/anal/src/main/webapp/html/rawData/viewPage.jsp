<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<link href="${base }/css/page.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<SCRIPT LANGUAGE="JavaScript">

function f_frameStyleResize(targObj,HeightValue){

		var targWin = targObj.parent.parent.document.all[targObj.parent.name];
		
		
		if(targWin != null) {
		
			//var HeightValue = targObj.document.body.scrollHeight
		
			if(HeightValue < 600){HeightValue = 600;} //不小于600
		  
		  var selfWin = targObj.parent.document.all[targObj.name];
		 
		    selfWin.style.pixelHeight = (HeightValue + 30);
			targWin.style.pixelHeight = (HeightValue + 60); // 30
		}

}

function f_iframeResize(){
		bLoadComplete = true; 
		var HeightValue = document.body.scrollHeight;
		
		f_frameStyleResize(this,HeightValue);

}

var bLoadComplete  = false;

window.onload =  f_iframeResize;

</SCRIPT>
		<style type="text/css">
/*
ul {
	list-style: none;
}
li{
	display: list-item;
	text-align: -webkit-match-parent;
}
p,span{
	margin: 0;
	padding: 0;
	border: 0;
	outline: 0;
	font-size: 100%;
	vertical-align: baseline;
	background: transparent;
}
*/
.menu {
	text-align:left;
	position:absolute;
	width:100px;
	cursor: pointer;
	list-style: none;
	margin:0;
}

.menu>.content {
	position:relative;
	padding:7px 0;
}

/* Vertical Menus
-------------------------------------------------------------- */

.menu.vertical {
	display:none;
	min-width: 100px;
	background-color:#ffffff;
	border:1px solid #d3d5d6;
	-moz-box-shadow: 2px 2px 1px #eee;
	-webkit-box-shadow: 2px 2px 1px #eee;
	box-shadow: 2px 2px 1px #eee;
}
.menu.vertical .wrap {
	line-height: 2em;
	height:24px;
	valign:middle;
	white-space: nowrap;
	background-repeat: no-repeat;
	margin-bottom:0;
}
.menu.context>.content {
/* set coordinates to visual (0,0,0,0) of .menu.context.context background,
   as this object used as reference for positioning
   child objects */
	left:0;
	margin-bottom:-6px;
	margin-right:0;
	position:relative;
	top:-2px;
}


</style>

		<script language="Javascript">
	function goPage(pageNum) {
			document.getElementById("pageFrxmlForm").action="${base}${pathUrl}&page=" + pageNum;
			document.getElementById("pageFrxmlForm").submit();
	}
	
	/*页面加载完后的特效*/
	$(document).ready(function(e) {
		
		var _btnstr = "${btns}";
		if(_btnstr){
			var _btnArr = _btnstr.split(",");
			for(var i=0;i<_btnArr.length;i++){
				if(_btnArr[i]){
					$("#" + _btnArr[i]).show();
					$("#" + _btnArr[i]).css("display","block");
				}
			}
		}else{
			$("li").each(function(){
				$(this).show();
			});
		}
	
	
		$("#li_export").mousemove(function(e) {
            var $menu = $(this),
			 	offset = $menu.offset(),
				height = $menu.outerHeight(),
				width = $menu.outerWidth();
			$("#menu").css({
				display: "block",
				top: offset.top + height +10,
				left: offset.left // main should overlay submenu
			});
        });
		
		$("#menu").mousemove(function(e) {
           $("#menu").css({
				display: "block" 
			});
        });
		
		$("#menu").mouseout(function(e) {
           $("#menu").css({
				display: "none"
			});
        });
        
        $("#menuList > li").each(function(){
        	$(this).mousemove(function(){
        		$(this).css("background","#FF9900");
        	});
        	
        	$(this).mouseout(function(){
        		$(this).css("background","#FFFFFF");
        	});
        });
    });
    
    function exportReport(type){
	    //window.open("${base}${exportReportPathUrl}&type=" + type);
    	 document.getElementById("pageFrxmlForm").action="${base}${exportReportPathUrl}&type=" + type;
		 document.getElementById("pageFrxmlForm").submit();
    }
</script>
	</head>
	<body text="#000000" link="#000000" alink="#000000" vlink="#000000">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="50%">
					&nbsp;
					<form id="pageFrxmlForm" name="pageFrxmlForm" action=""
						method="post">
						<input type="hidden" name="fname" id="fname" value="${fname}" />
						<input type="hidden" name="parStr" id="parStr" value="${parStr }" />
					</form>
				</td>
				<td align="left">
					<hr size="1" color="#000000">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td>
								<div style="valign:middle;">
									<ul>
										<li id="li_export"
											style="width:74px;cursor: pointer;display:none;">
											<%-- <img src="${base}/images/savefile.png">--%>
											<input type="button" value="保存" class="bot1other">
										</li>
									</ul>
								</div>
							</td>
							<td>
								<%--
								<a href="javascript:void(0);" onclick="goPage(${page.page });"><img
										src="${base}/images/reload.GIF" border="0">
								</a>
								--%>
								<input type="button" value="刷新" class="bot1other"
									onclick="goPage(${page.page });">
							</td>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="middle">
								<div class="default_separator" />
							</td>
							<c:choose>
								<c:when test="${page.page >1}">
									<td>
										<%-- 
										<a href="javascript:void(0);" onclick="goPage(1);"><img
												src="${base}/images/first.GIF" border="0">
										</a>
										--%>
										<div
											title="<fmt:message key="efetionmanage.common.page2.first_page"/>"
											class="default_pgBtn default_pgFirst"
											onclick="JavaScript:goPage('1');" />
									</td>
									<td>
										<%--
										<a href="javascript:void(0);" class="bot1other" 
											onclick="goPage(${page.previousPage});"><img
												src="${base}/images/previous.GIF" border="0">
										</a> --%>
										<div
											title="<fmt:message key="efetionmanage.common.page2.previous_page"/>"
											class="default_pgBtn default_pgPrev"
											onclick="JavaScript:goPage('${page.previousPage}');" />
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<%-- 
										<img src="${base}/images/first_grey.GIF" border="0">
										--%>
										<div
											title="<fmt:message key="efetionmanage.common.page2.first_page"/>"
											class="default_pgBtn default_pgFirst default_pgFirstDisabled"></div>
									</td>
									<td>
										<%-- 
										<img src="${base}/images/previous_grey.GIF" border="0">
										--%>
										<div
											title="<fmt:message key="efetionmanage.common.page2.previous_page"/>"
											class="default_pgBtn default_pgPrev default_pgPrevDisabled"></div>
									</td>
								</c:otherwise>
							</c:choose>

							<td valign="middle">
								<div class="default_separator" />
							</td>
							<c:choose>
								<c:when test="${page.page < page.totalPage}">
									<td>
										<!--  
										<a href="javascript:void(0);"
											onclick="goPage(${page.nextPage });"><img
												src="${base}/images/next.GIF" border="0">
										</a>
										-->
										<div
											title="<fmt:message key="efetionmanage.common.page2.next_page"/>"
											class="default_pgBtn default_pgNext"
											onclick="JavaScript:goPage('${page.nextPage}');" />
									</td>
									<td>
										<!-- 
										<a href="javascript:void(0);"
											onclick="goPage(${page.totalPage});"><img
												src="${base}/images/last.GIF" border="0">
										</a>
										 -->
										<div
											title="<fmt:message key="efetionmanage.common.page2.last_page"/>"
											class="default_pgBtn default_pgLast"
											onclick="JavaScript:goPage('${page.totalPage}');" />
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<!-- 
										<img src="${base}/images/next_grey.GIF" border="0">
										 -->
										<div
											title="<fmt:message key="efetionmanage.common.page2.next_page"/>"
											class="default_pgBtn default_pgNext default_pgNextDisabled" />
									</td>
									<td>
										<!-- 
										<img src="${base}/images/last_grey.GIF" border="0">
										 -->
										<div
											title="<fmt:message key="efetionmanage.common.page2.last_page"/>"
											class="default_pgBtn default_pgLast default_pgLastDisabled" />
									</td>
								</c:otherwise>
							</c:choose>
							<td valign="middle">
								<div class="default_separator" />
							</td>
							<c:choose>
								<c:when test="${page.totalPage > 7}">
									<!-- 页面的起始页 -->
									<c:set var="startPage" value="${page.page-3>1?page.page-3:1}" />
									<!-- 页面的结束页 -->
									<c:set var="endPage"
										value="${startPage+6>page.totalPage?page.totalPage:startPage+6}" />
									<!-- 当结束页是页面总数时，情况变化 -->
									<c:if test="${(page.totalPage == endPage)&&(endPage >7)}">
										<c:set var="startPage" value="${page.totalPage - 6}" />
									</c:if>
									<td valign="middle">
										<div style="width:188px;">
											<ul class="pageLink" style="padding:0;">
												<c:forEach begin="${startPage}" end="${endPage}"
													varStatus="statusPage">
													<c:choose>
														<%-- 后期修改，有时page默认为1，有时 page 默认为0 --%>
														<c:when
															test="${(statusPage.index  == page.page) || (page.page == 0)}">
															<li class="nolink">
																&nbsp;${statusPage.index}
															</li>
														</c:when>
														<c:otherwise>
															<li>
																<a href="#"
																	onclick="JavaScript:goPage('${statusPage.index}')">
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
										<div style="width:188px;">
											<ul class="pageLink" style="padding:0;">
												<c:forEach begin="1"
													end="${page.totalPage > 1?page.totalPage:1}"
													varStatus="statusPage">
													<c:choose>
														<c:when
															test="${(statusPage.index  == page.page) || (page.page == 0)}">
															<li class="nolink">
																&nbsp;${statusPage.index}
															</li>
														</c:when>
														<c:otherwise>
															<li>
																<a onclick="JavaScript:goPage('${statusPage.index}')">
																	${statusPage.index} </a>
															</li>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</ul>
										</div>
									</td>
								</c:otherwise>
							</c:choose>
							<td width="100%" style="padding-left:20px;">
								&nbsp;
							</td>
						</tr>
					</table>
					<hr size="1" color="#000000">
				</td>
				<td width="50%">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td width="50%">
					&nbsp;
				</td>
				<td align="center">
					${sbuffer}
				</td>
				<td width="50%">
					&nbsp;
				</td>
			</tr>
		</table>

		<div id="menu" class="menu vertical" tabindex="-1"
			style="z-index: 99999;">
			<div class="content">
				<ul id="menuList">
					<li id="exportReportPdf" class="leaf"
						onclick="exportReport('pdf');" style="display:none;">
						<p class="wrap button">
							<span class="icon"></span>&nbsp&nbsp
							<img src="${base}/images/share-pdf.gif" width="16" height="16">
								</><!--Item text goes here-->
								&nbsp&nbsp为 PDF
						</p>
					</li>
					<li id="exportReportExcel" class="leaf"
						onclick="exportReport('excel');" style="display:none;">
						<p class="wrap button">
							<span class="icon"></span>&nbsp&nbsp
							<img src="${base}/images/page_excel.png" width="16" height="16">
								<!--Item text goes here-->
								&nbsp&nbsp为 Excel
						</p>
					</li>
					<li id="exportReportHtml" class="leaf"
						onclick="exportReport('html');" style="display:none;">
						<p class="wrap button">
							<span class="icon"></span>&nbsp&nbsp
							<img src="${base}/images/mime_html.png" width="16" height="16">
								<!--Item text goes here-->
								&nbsp&nbsp为 html
						</p>
					</li>
			</div>
		</div>
	</body>
</html>
