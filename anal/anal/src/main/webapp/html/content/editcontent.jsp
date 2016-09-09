<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/js/calendar/calendar2/calendar.jsp"%>
<%@ page import="com.cmcc.common.util.UserSessionObj"%>
<%
			UserSessionObj userInfo = (UserSessionObj) session
			.getAttribute("userInfo");
%>
<html>
	<head>

		<link href="${base}/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<link rel="stylesheet" href="${base }/js/ztree/css/demo.css"
			type="text/css" />
		<link rel="stylesheet"
			href="${base }/js/ztree/css/zTreeStyle/zTreeStyle.css"
			type="text/css" />
		<%@ include file="/commons/meta.jsp"%>
		<title><fmt:message key="cotnentManager.edit" /></title>
		<style type="text/css">
		
			.Per_sel_ul{padding:0px;border: 1px solid #b2bac5;margin-left:10%;width:50%;}
			.Per_sel_ul li{width:50%; display:block; height:25px; cursor:pointer; *margin-bottom:-2px; text-overflow: ellipsis; -moz-text-overflow: ellipsis;
			overflow:hidden; line-height:25px;}
			/*选中状态 */
			.Per_sel_ul li.bg{background:#8ACCFB;}
			.Per_sel_ul label{padding:0 0 0 16px; cursor:pointer; display:block; width:100%;white-space: nowrap; }
			
		</style>
		<script type="text/javascript" src="${base}/js/check_all.js">
		</script>
		<script type="text/javascript" src="${base}/js/DateUtils.js"></script>

		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.exedit-3.5.js"></script>
		<!-- 树的 js 块 -->
		<script language="javascript">
			var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick,
				onCheck: onCheck
			}
		};

		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck(e, treeId, treeNode) {
			/*
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySel");
			cityObj.attr("value", v);
			*/
			$("#sid").val(treeNode.getParentNode().id);
			$("#tid").val(treeNode.id);
		}

		function showMenu() {
			var cityObj = $("#ctId");
			var cityOffset = $("#ctId").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "ctId" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}

		$(document).ready(function(){
			var chartData = '${jsontrees}';
			chartData = eval("(" + chartData + ")");
			$.fn.zTree.init($("#treeDemo"), setting, chartData);
		});
		
		/**
		 * 选择是否显示菜单
		 */
		function isShowMenu(){
			var _opvalue = $("#ctId").val();
			// alert(_opvalue);
			if(_opvalue == 3){
				showMenu();
			}else{
				hideMenu();
			}
		}
		</script>
		<SCRIPT LANGUAGE="JavaScript">

function f_frameStyleResize(targObj,HeightValue){

		var targWin = targObj.parent.parent.document.all[targObj.parent.name];
		
		var searchWin = targObj.parent.document.all["adminRight"];
		
		if(targWin != null) {
		
			var HeightValue1 = targObj.document.body.scrollHeight
			
			if(HeightValue < 600){HeightValue = 600;} //不小于600
		  if(searchWin!=null)
		  { 
		  	var selfWin = targObj.parent.document.all[targObj.name];
		  	
		  	selfWin.style.pixelHeight = HeightValue+60;
		  	HeightValue+=60;
		  }
			targWin.style.pixelHeight = HeightValue;
			
		}

}

function f_iframeResize(){
		bLoadComplete = true; 
		var HeightValue = document.body.scrollHeight;
		//alert(HeightValue);
		f_frameStyleResize(this,HeightValue);

}

var bLoadComplete = false;



window.onload = f_iframeResize;

</SCRIPT>
		<script type="text/javascript">
			var now = new Date(); 
			var yy = now.getYear(); 
			var mm = now.getMonth()+1 + "";
			if(mm.length<2){
				mm = "0" + mm;
			}
			var date= now.getDate();
			if(date<10){
			date = "0" + date;
			}
			var nowdate = yy + "-" + mm + "-" + date;
		function goBack(){
		var editContentForm = document.getElementById("editContentForm");
			editContentForm.action="${base}/manage/contentmanage/content/listContentInfos.${actionExt}?groupId=${requestScope.groupId}" ;
			editContentForm.target="";
			editContentForm.submit() ;
		}
		function cleanError(){
			$(".erro").each(function(){
				$(this).html("");
			});
			/*
			var arr=document.getElementsByTagName("span");
			for(var i=0;i<arr.length;i++){
				arr[i].innerHTML="";
			}
			*/
		}
		
		// 发布事件
		function doSubmit(){
			 //alert("doSubmit=="+"${requestScope.action}");
			 // return false;
			<c:choose>
			 <c:when test="${(action eq 'modify') || (action eq 'saveToDraft')}">
			         //方法参数中的action的值固定为modify
			 		 actionURL="${base}/manage/contentmanage/content/modifyContentInfo.${actionExt}?groupId=${requestScope.groupId}&action=modify";
			 </c:when>
			 <c:otherwise>
					 actionURL="${base}/manage/contentmanage/content/createContentInfo.${actionExt}?groupId=${requestScope.groupId}";
			 </c:otherwise>
			</c:choose>		
			if(validateContent()){
				ad(actionURL);
			}
		}
		//再次发布新闻(作为一条新纪录发送出去)
		function redeploy(){
			actionURL="${base}/manage/contentmanage/content/createContentInfo.${actionExt}?groupId=${requestScope.groupId}";
			if(validateContent()){
				ad(actionURL);
			}
		}
		//更新已发布新闻
		function updateDraft(){
		    // alert("updateDraft=="+"${requestScope.action}");
		    // return false;
			actionURL="${base}/manage/contentmanage/content/modifyContentInfo.${actionExt}?groupId=${requestScope.groupId}&action=${requestScope.action}";
			if(validateContent()){
				ad(actionURL);
			}
		}
		
		// 保存草稿事件
		function dosaveToDraft(){
			actionURL="${base}/manage/contentmanage/content/createContentInfo!saveToDraft.${actionExt}?groupId=${requestScope.groupId}";
			if(validateContent()){
				ad(actionURL);
			}
		}
		
		// 预览事件 
		function doFrontView(){
			if(!validateContent()){
					return ;
			}
			actionURL="${base}/public/common/contentmanager/content/contentInfoView!frontView.${actionExt}";
			var txt = '<br/><iframe id = "newWindow" name="newWindow" width="640px" height="340px" framespacing="0" marginheight="0" frameborder="0" scrolling="auto" hspace="0" vspace="0" style="display:block;margin:0 auto;"></iframe>';
			
			var editform = document.getElementById("editContentForm");
			$.prompt(txt,{buttons:{},top:"1%",width:"650px",height:"390px",left:"30%"});	
			editform.target="newWindow";
			document.getElementById("newWindow").focus();
			editform.action=actionURL;
			editform.submit();
		}
	// 对内容信息进行验证
	function validateContent(){
		var haveError = true;
		var releasedate = trim(document.getElementById("releasedate").value);
		var author = trim(document.getElementById("author").value);
		var title = trim(document.getElementById("contentTitle").value);
		var contentSource=trim(document.getElementById("contentSource").value);
		var contentSourceLink =trim(document.getElementById("contentSourceLink").value);
		if(document.getElementById('up')!=null){
			var up = document.getElementById('up').value;
			var ups=document.getElementById('up');
		}
		var authorerror =document.getElementById("authorerror");
		var titleerror =document.getElementById("titleerror");
		var timeerror =document.getElementById("timeerror");
		var fckcontent = FCKeditorAPI.GetInstance("content")
		var content =trim(fckcontent.GetXHTML());
		var bodyerror =document.getElementById("bodyerror");
		var sourcelinkerror =document.getElementById("sourcelinkerror");
		var sourceerror =document.getElementById("sourceerror");
		var date1 =  new Date(Date.parse(nowdate.replace("-","/")));
		var date2 = new Date(Date.parse(releasedate.replace("-","/")));
		//date1.setDate(date2.getDate());
	//	<!-- c:choose-->
//		 <!--c:when test="${action eq 'create'}"-->
//				if(date2<date1){
//				timeerror.innerHTML="<fmt:message key="efetionmanage.framework.contentManager.releasedate.Error"/>";		 
//				  document.getElementById("releasedate").focus();
//				   document.getElementById("releasedate").value=nowdate;
//				  return false;
//				}
//		 <!--/c:when-->	
//		 <!--/c:choose-->	
//		if(author.length == 0){
//			authorerror.innerHTML="<fmt:message key="efetionmanage.framework.contentManager.author.nullError"/>";
//			document.getElementById("author").value="";
//			document.getElementById("author").focus();
//			return  false;
//		}	

		/*验证前，先全部清除所有错误记录*/
		cleanError();
		
		if(title.length == 0){
		
			titleerror.innerHTML="<fmt:message key="efetionmanage.framework.contentManager.title.nullError"/>";
			document.getElementById("contentTitle").value="";
			document.getElementById("contentTitle").focus();
			haveError = false;
		}
		if(content.length == 0 || isContentNUll(content)){
			bodyerror.innerHTML="<fmt:message key="efetionmanage.framework.contentManager.content.nullError"/>";
			fckcontent.Focus();
			haveError = false;
		}
		if(contentSourceLink.length !=0 && !isURL(contentSourceLink)){
			sourcelinkerror.innerHTML="<fmt:message key="efetionmanage.framework.contentManager.contentsourcelink.Error"/>";
			document.getElementById("contentSourceLink").focus();
			haveError = false;
		}
		if(up!=null){
			var upname = up.substring(up.lastIndexOf("\\" +1,up.length));
			if(checkFile(upname)){
				document.getElementById("message").innerHTML="<fmt:message key="efetionmanage.framework.contentManager.uploadAttachError3"/>";
				ups.outerHTML=ups.outerHTML;
				haveError = false;
			}
		}
		
		if(contentSourceLink.length!=0){
			if(contentSource.length==0){
				document.getElementById("contentSource").focus();
				sourceerror.innerHTML="<fmt:message key="efetionmanage.framework.contentManager.contentsource.nullError"/>";
				haveError = false;
			}
		}
		
		var _opvalue = $("#ctId").val();
		if(_opvalue == 3 && ($("#sid").val() == "" || $("tid").val() == "")){
			$.prompt("  选择数据管理栏目时，需要选择类别！",{buttons:{确定:true},alertType:'msg'});
			haveError = false;
		}
		
		return haveError;
}
function FCKeditor_OnComplete( editorInstance )
{
	if (document.all) { 
	editorInstance.EditorDocument.attachEvent( 'onkeydown', DoSomething ) ;
	}else{
		editorInstance.EditorDocument.addEventListener( 'keypress', DoSomething, true ) ;
		
	}
}
		function DoSomething( editorInstance )
		{
			if(document.getElementById("bodyerror").innerHTML!='')
				document.getElementById("bodyerror").innerHTML='';
		}
		// 判断内容管理是否为空
		function isContentNUll(content){
			var regExpIE = new RegExp("^\<p\>(&nbsp;)*\<\/p\>$");
			var regExpFF = new RegExp("^\<p\>(&nbsp;)* \<\/p\>$");
			if (regExpIE.test(content) || regExpFF.test(content)) {
				return true;
			} else {
				return false;
			}
	   }
		
</script>


		<link href="${base }/js/fileuploader/fineuploader.css"
			rel="stylesheet" type="text/css" />
		<script src="${base }/js/fileuploader/js/header.js"></script>
		<script src="${base }/js/fileuploader/js/util.js"></script>
		<script src="${base }/js/fileuploader/js/button.js"></script>
		<script src="${base }/js/fileuploader/js/ajax.requester.js"></script>
		<script src="${base }/js/fileuploader/js/deletefile.ajax.requester.js"></script>
		<script src="${base }/js/fileuploader/js/handler.base.js"></script>
		<script src="${base }/js/fileuploader/js/handler.base.js"></script>
		<script src="${base }/js/fileuploader/js/window.receive.message.js"></script>
		<script src="${base }/js/fileuploader/js/handler.form.js"></script>
		<script src="${base }/js/fileuploader/js/handler.xhr.js"></script>
		<script src="${base }/js/fileuploader/js/uploader.basic.js"></script>
		<script src="${base }/js/fileuploader/js/dnd.js"></script>
		<script src="${base }/js/fileuploader/js/uploader.js"></script>
		<script src="${base }/js/fileuploader/uploader-demo.js"></script>


	</head>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<div id="main" style="padding:15px 0px 0 0px;">
			<div class="title">
				<h4>
					<a href="${base }/html/adminSetting/index.jsp">管理设置</a> &gt;&gt;
					<a
						href="${base}/manage/contentmanage/content/listContentInfos.${actionExt}?groupId=1">内容管理</a>
				</h4>
			</div>

			<div class="area-warp">
				<h5>
					<fmt:message key="efetionmanage.framework.contentManager.edit" />
				</h5>
				<div class="area-content">
					<div class="com-info">
						<form name="editContentForm" id="editContentForm" action=""
							method="post">
							<input type="hidden" name="attIds" id="attIds"
								value="${contentVO.attIds}" />

							<input type="hidden" name="sid" id="sid" value="${contentVO.sid}" />
							<input type="hidden" name="tid" id="tid" value="${contentVO.tid}" />
							<ss:token name="addOrUpdateContent.token" />
							<table width="99%">
								<tr>
									<td align="right">
										<fmt:message
											key="efetionmanage.framework.contentManager.contentTitle" />
										：
									</td>
									<td width="5">
										<font style="color:#9d0c0c">*</font>
									</td>
									<td colspan="4" width="444">
										<input type="text" id="contentTitle" name="title"
											maxlength="150" class="f-text" size="50"
											onkeydown="cleanError();"
											value="<c:out value="${contentVO.title}" escapeXml="true"></c:out>" />
										<span class="erro" style="color:#9d0c0c" id="titleerror"></span>
									</td>
								</tr>
								<tr>
									<td width="80" align="right">

										<fmt:message
											key="efetionmanage.framework.contentManager.contentAuthor" />
										：
									</td>
									<td width="5">
										<font></font>
									</td>
									<td width="80" align="left">

										<input type="text" name="author" id="author"
											value="<c:out value="${contentVO.author}" escapeXml="true"></c:out>"
											maxlength="20" size="10" class="f-text" />
									</td>
									<td width="100">
										<span class="erro" id="authorerror">&nbsp;</span>
									</td>
									<td width="90" align="left">
										<!--fmt:message
										key="efetionmanage.framework.contentManager.contentReleaseDate" /  -->
										<!-- 
										<fmt:message
											key="efetionmanage.framework.contentManager.istop" />
											 -->
										栏目：
									</td>
									<td align="left">
										<select id="ctId" name="ctId" onclick="isShowMenu();">
											<c:forEach var="type" items="${typeList}">
												<c:choose>
													<c:when test="${type.ctId == contentVO.ctId }">
														<option value="${type.ctId}" selected="selected">
															${type.ctName}
														</option>
													</c:when>
													<c:otherwise>
														<option value="${type.ctId}">
															${type.ctName}
														</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<input type="hidden" name="releasedate" id="releasedate"
											value="<fmt:formatDate value="${requestScope.contentVO.releasedate}" type="date" pattern="yyyy-MM-dd"/>" />
										<c:if test="${action ne 'modify'}">
											<script type='text/javascript'>
															
																	document.getElementById("releasedate").value = nowdate;
																	</script>
										</c:if>
										<input type="hidden" name="isTop" id="isTop" value="0" />
										<span id="timeerror" class="erro">&nbsp;</span>
									</td>

								</tr>
								<tr>
									<td align="right">
										<fmt:message
											key="efetionmanage.framework.contentManager.contentSource" />
										：
									</td>
									<td width="5">
										<font></font>
									</td>
									<td>
										<input type="text" name="contentSource" id="contentSource"
											onkeydown="cleanError();" class="f-text" size="10"
											value="<c:out value="${contentVO.contentSource}" escapeXml="true"></c:out>"
											maxlength="20" />
									</td>
									<td>
										<span id="sourceerror" class="erro">&nbsp;</span>
									</td>
									<td align="left">
										<fmt:message
											key="efetionmanage.framework.contentManager.contentSourceLink" />
										：
									</td>
									<td align="left">
										<input type="text" name="contentSourceLink"
											id="contentSourceLink" class="f-text" size="10"
											onkeydown="cleanError();"
											value="<c:out value="${contentVO.contentSourceLink}" escapeXml="false"></c:out>"
											maxlength="256" />
										<span id="sourcelinkerror" class="erro">&nbsp;</span>
									</td>

								</tr>
								<tr>
									<td align="right">
										<fmt:message key="efetionmanage.framework.contentManager.body" />
										：
									</td>
									<td width="5">
										<font style="color:#9d0c0c">*</font>
									</td>
									<td colspan="4">
										<span class="erro" style="color:#9d0c0c" id="bodyerror"></span>
									</td>
								</tr>
								<tr>
									<td colspan="6" style="text-align:center">
										<c:choose>
											<c:when test="${ contentpath eq '/'}">
												<fck:editor instanceName="content" basePath="js/fckeditor"
													toolbarSet="Fetion" width="800"
													value="${requestScope.contentVO.content}" height="200"></fck:editor>
											</c:when>
											<c:otherwise>
												<fck:editor instanceName="content" basePath="/js/fckeditor"
													toolbarSet="Fetion" width="800"
													value="${requestScope.contentVO.content}" height="200"></fck:editor>
											</c:otherwise>
										</c:choose>
										<input type="hidden" name="identifier"
											value="${requestScope.contentVO.identifier }" />
									</td>
								</tr>
								<c:if test="${not empty attachList}">
									<tr>
										<td colspan="6" style="text-align:center">
											<div>
												<ul class="Per_sel_ul"
													style="OVERFLOW:auto;height:240;width:50%;">
													<c:forEach var="attach" items="${attachList}">
														<li>
															<label>
																${attach.attName}
															</label>
														</li>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
								</c:if>
								<tr>
									<td colspan="6" style="text-align:center">
										<div>
											<ul id="basicUploadSuccessExample" class="unstyled">
											</ul>
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<div style="margin-top:5px;">
					<input type="button"
						value="<fmt:message key='efetionmanage.framework.contentManager.deploy' />"
						class="bot1other" onclick="doSubmit();" />

					<input type="button"
						value="<fmt:message key='efetionmanage.framework.contentManager.frontview'/>"
						class="bot1other" onclick="doFrontView();" />

					<c:choose>
						<c:when test="${contentVO.state eq '4'}">
							<input type="button" value="另存" class="bot1other"
								onclick="dosaveToDraft();" />
						</c:when>
						<c:when test="${contentVO.state eq '0'}">
							<input type="button" value="保存" class="bot1other"
								onclick="updateDraft();" />
						</c:when>
						<c:otherwise>
							<input type="button"
								value="<fmt:message key='efetionmanage.framework.contentManager.save' />"
								class="bot1other" onclick="dosaveToDraft();" />
						</c:otherwise>
					</c:choose>
					<input type="button" value="<fmt:message key='back'/>"
						class="bot1other" onclick="javascript:goBack();" />
				</div>
				<script type="text/javascript" src="${base}/js/ajaxfileupload.js"></script>
				<script type="text/javascript">
			$("#file").change(function(){
				alert("开始");
			 	ajaxFileUpload();
				alert("结束");
			});
			
			function ajaxFileUpload()
			{
				alert("提交");
				$("#loading")
					.ajaxStart(function(){
						$(this).show();
					})//开始上传文件时显示一个图片
					.ajaxComplete(function(){
						$(this).hide();
					});//文件上传完成将图片隐藏起来
					
					
			
				
				$.ajaxFileUpload
				(
					{
						url:'${base}/upload',//用于文件上传的服务器端请求地址
						secureuri:false,//一般设置为false
						fileElementId:'file',//文件上传空间的id属性  <input type="file" id="file" name="file" />
						dataType: 'json',//返回值类型 一般设置为json
						success: function (data, status)  //服务器成功响应处理函数
						{
							console.info(data);
							/*
							alert(data.message);//从服务器返回的json中取出message中的数据,其中message为在struts2中定义的成员变量
							
							if(typeof(data.error) != 'undefined')
							{
								if(data.error != '')
								{
									alert(data.error);
								}else
								{
									alert(data.message);
								}
							}
							*/
						},
						error: function (data, status, e)//服务器响应失败处理函数
						{
							// alert(e);
							console.info(data);
							console.info(status);
							console.info(e);
						}
					}
				)
				
				return false;
		
			}
	</script>
				<div id="menuContent" class="menuContent"
					style="display:none; position: absolute;">
					<ul id="treeDemo" class="ztree"
						style="margin-top:0; width:180px; height: 300px;background: #f0f6e4"></ul>
				</div>
	</body>
</html>
