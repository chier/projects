<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<%@ include file="/commons/meta.jsp"%>
		<style type="text/css">
	body{background:#e5e8ed;}
	#main{padding:0 2px 0 2px}
	.li_select a{
		color:red;
	}
	
	/*//鼠标移近   下划线   */
	li a:hover {
		text-decoration: underline;
		cursor: pointer;
	}
	/*激活  */
	li a:active:{
		text-decoration: none;
	} 
	/*已经访问 去掉下划线*/
	li a:visited{
		text-decoration: none;
	}
	/*未访问 无划线*/
	li a:link{
		text-decoration: none;
	}
	</style>
	</head>
	<body>
		<div id="main">
			<ul id="ul_setting" style="margin-left:5px;list-style:none;">
				<li id="li_admin" style="margin-top:15px;cursor:pointer;"
					class="li_select">
					<a
						href="${base}/manage/safemanage/webadmin/listWebAdminAction.${actionExt}"
						target="adminRight">用户管理</a>
				</li>
				<li id="li_content" style="margin-top:15px;cursor:pointer;">
					内容管理
				</li>
				<li id="li_content_main">
					<ul id="model_ul" style="margin-left:10px;list-style:none;">
						<c:forEach items="${rootGroups}" var="model">
							<li style="margin-top:10px;" type="2">
								<a href="${base}${model.actionUrl}" target="adminRight">${model.modelName}</a>
							</li>
						</c:forEach>
					</ul>
				</li>
				<li id="li_channel" style="margin-top:15px;cursor:pointer;">
					<a
						href="${base}/html/adminSetting/channel_index.jsp"
						target="adminRight">频道管理</a>
					
				</li>

			</ul>

		</div>
	</body>
</html>
<script language="JavaScript">
	$(document).ready(function(){
		// 模块点击节点操作
		$("#model_ul li").each(function(i,v){
			
			$(this).click(function(){
				$("#model_ul li").each(function(){
					$(this).removeAttr("class");
				});
				$(this).attr("class","li_select");
			});
		});
		
		//内容管理点击
		$("#ul_setting>li").each(function(i,v){
			
			$(this).click(function(){
				$("#ul_setting li").each(function(){
					$(this).removeAttr("class");
				});
				if(v.id == "li_content" || v.id == "li_content_main"){
					$("#li_content").attr("class","li_select");
					$("#li_content_main").show();
				}else{
					$(this).attr("class","li_select");
					$("#li_content_main").hide();
				}
			});
		});
		
		
		
	});
</script>
