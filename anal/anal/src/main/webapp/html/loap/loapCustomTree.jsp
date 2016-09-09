<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html>
	<head>
		<title>${requestScope.platformTitle}</title>
		<%@ include file="/commons/meta.jsp"%>
		<script src="${base}/js/tree/radiotree/xtree.js"></script>
		<script
			src="${base}/js/tree/radiotree/xloadtree.js"></script>
		<script
			src="${base}/js/tree/radiotree/xmlextras.js"></script>
		<link type="text/css" rel="stylesheet"
			href="${base}/js/tree/checkboxtree/xtree.css" />

		<style type="text/css">
			.bodyTree{
				margin:0; padding:0;overflow-x:hidden;
			}
			.bodySelect{
				margin:0; padding:0;overflow:hidden;
			}
		
		</style>


	</head>
	<body class="bodyTree">
		<input type="hidden" name="searchOwnerValue" id="searchOwnerValue"
			value="${searchOwnerValue}" />
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div id="waitChange" name="waitChange">
						<script language="JavaScript">
				  		function handleCheck(obj, id, name){
				  			// console.info($("input",parent));
 				  			top.document.getElementById("topCsId").value=id;
				  			// parent.pss();
				  			
							// alert(id);
				  			/*
							if (obj.checked) {
							    addSelected(id, name);
							} else {
							    delSelected(id, name);
							}
							*/
						}
				  		function getUserValue(){
				  			/*
				  			var waitUser = $("#2stSelected option:selected");
							if (waitUser.length < 1) {
								return;
							} else {
								var text =  waitUser[0].text;
								_name = text.substring(0,text.indexOf("("));
								if(text.indexOf("【管理员】") != -1){
									_name += "【管理员】";
								}
							    var element = new Element(waitUser[0].value, _name, 0);
							    bselected[0] = element;
							}
							*/
							// alert();
				  		}
				  		
						if(document.getElementById){
				             var tree = new WebFXLoadTree('${rootGroup.modelName}',
				             			'${rootGroup.identifier}',
				             			'false',
				             			'${base}/manage/loap/loapCustomTreeAction.${actionExt}?sendtime='+ (new Date()).getTime());	
				             document.write(tree);
				        }
				    </script>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
