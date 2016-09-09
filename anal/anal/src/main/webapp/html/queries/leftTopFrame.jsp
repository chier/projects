<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>即席查询</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<%@ include file="/commons/meta.jsp"%>
		<script language="javascript">
			$(document).ready(function(){
				var _btnstr = "${btns}";
				if(_btnstr){
					var _btnArr = _btnstr.split(",");
					for(var i=0;i<_btnArr.length;i++){
						$("#" + _btnArr[i]).show();
					}
				}else{
					$(":button").each(function(){
						$(this).show();
					});
				}
			});
			function perUlLiClick(obj){
				var itemName = obj.attr("itemName");
					var itemType = obj.attr("itemType");
					var itemCode =obj.attr("itemCode");
					var _cls = obj.attr("class");
					if(_cls == "bg"){
							obj.removeAttr("class");
							$("#li_"+itemCode).remove();
					}else{
						obj.attr("class","bg");
						showItem(itemName,itemType,itemCode);
					}
					
					$(".ul_search li:even").css({background:"#e8e9ed"})
			}
			
			/**
			 * 删除图片事件
			 */
			function removeImg(itemCode){
// 				alert(itemCode);
				$("#_perLi_"+itemCode).removeAttr("class");
				$("#li_"+itemCode).remove();
			}
			/**
			 * 显示 item 信息
			 * 姓名   类型 : 1表示数字 2 表示字符。 3 code  代码信息
			 */
			function showItem(itemName,itemType,itemCode){
				
				var _itemHtmlLi = $("<li>").attr("id","li_"+itemCode);
				// table 信息
				var _itemHtml = "<table border=0>";
				//tr
				_itemHtml += "<tr valign='top'>";
				// 字段名称
				_itemHtml += "<td width='165' valign='top'>"
				_itemHtml += "<label>";
				_itemHtml += "<img style='cursor:pointer;margin-right:5px;' width='10' height='10' onclick='removeImg(\""+itemCode+"\");' src='"+base+"/images/delete.png'>";
				_itemHtml += " " + itemName + "";	
				_itemHtml += "</label>";
				_itemHtml += "<input class='inputItCode' type='hidden' value='"+itemCode+"' />" ;	
				_itemHtml += "</td>"
				// 字段类型  1表示数字 2表示字符
				_itemHtml += "<td width='100'>"
				if(itemType == 1){
					_itemHtml += '<select class="inputItComparison" name="sex_T" style="width:80px">'
						+'<option value="<">&nbsp;&nbsp;&nbsp;&lt;&nbsp;</option>'
						+'<option value=">">&nbsp;&nbsp;&nbsp;&gt;&nbsp;</option>'
						+'<option value="=">&nbsp;&nbsp;&nbsp;=&nbsp;</option>'
						+'<option value="<>">&nbsp;&nbsp;&lt;&gt;&nbsp;</option>'
						+'<option value="<=">&nbsp;&nbsp;&nbsp;&lt;=&nbsp;</option>'
						+'<option value=">=">&nbsp;&nbsp;&nbsp;&gt;=&nbsp;</option>'
					+'</select>';
				}
				if(itemType == 2){
					_itemHtml += '<select class="inputItComparison" name="inputItComparison " style="width:80px">'
						+'<option value="=">&nbsp;&nbsp;等于&nbsp;&nbsp;</option>'
						+'<option value="like">&nbsp;&nbsp;包含&nbsp;&nbsp;</option>'
					+'</select>';
				}
				if(itemType == 3){
					_itemHtml += '<select class="inputItComparison" name="inputItComparison " style="width:80px">'
						+'<option value="=">&nbsp;&nbsp;等于&nbsp;&nbsp;</option>'
						+'<option value="<>">&nbsp;&nbsp;不等于&nbsp;&nbsp;</option>'
					+'</select>';
				}
				_itemHtml += "</td>"

				// 字段的值  下拉还是选项
				_itemHtml += "<td>"
					 if (itemType == 1 || itemType == 2) {
						_itemHtml +='<input class="inputItValue" myType="'+itemType+'" name="inputItyValue" type="text" style="width:100px;" value="" maxlength="11"/>';
					}
					if(itemType == 3){
						_itemHtml += '<select class="inputItValue" name="inputItValue " style="width:80px">';
						
						<c:forEach var="vo" items="${itemList}">
							if("${vo.itemCode}" == itemCode){
								<c:forEach var="map" items="${vo.exprMap}">
									if("${map.key}" == itemCode){
										<c:forEach var="v" items="${map.value}">  
											_itemHtml += '<option value="${v.code}">&nbsp;&nbsp;${v.name}&nbsp;&nbsp;</option>';
										</c:forEach>
									}
								</c:forEach>
							}
						</c:forEach>
					
						_itemHtml += '</select>';
					}
				_itemHtml += "</td>"
					// 字段的值  下拉还是选项
				_itemHtml += "<td>"
				_itemHtml += "<span style='color: rgb(157, 12, 12);'></span>";
				_itemHtml += "</td>"
				_itemHtml += "</tr>";
				
				_itemHtml += "</table>";				
				// _itemHtml += "</li>"
				_itemHtmlLi.append(_itemHtml);
				// console.info(_itemHtml);
				// $(_itemHtml).appendTo(#showTable);
				$("#showTable").append(_itemHtmlLi);
			}
			/**
			 * 提交事件
			 */
			function clickSubmit(){
			
	          var _searchValue = $("#searchValue");
	          _searchValue.val("");
	          var searCompar;
				$("#showTable  tr").each(function(i){
				  	// 再次增加条件
					if(i != 0){
						_searchValue.val(_searchValue.val() + " and ");
					}
					//  tableCode 信息
					$(this).find(".inputItCode").each(function(){
						_searchValue.val(_searchValue.val() + " " + $(this).val());
					});
					
					// 标点信息
					$(this).find(".inputItComparison").each(function(){
						searCompar = $(this).val();
						_searchValue.val(_searchValue.val() + " " + $(this).val());
					});
					var _errSpan = $(this).find("span");
					_errSpan.html("");
					$(this).find(".inputItValue").each(function(){
							if($(this).val() == ""){
								_errSpan.html("不能为空！");
							}else if($(this).attr("myType") == 1 && !isNumber($(this).val())){
								_errSpan.html("请填写数字");
							}else if($(this).attr("myType") == 2){
								if(searCompar == "like"){
									_searchValue.val(_searchValue.val() + " '%" + $(this).val() + "%' ");
								}else{
									_searchValue.val(_searchValue.val() + " '" + $(this).val() + "' ");								
								}

							}else{
								_searchValue.val(_searchValue.val() + " " + $(this).val() );
							}
					});
				});
				
				// 判断 如果存在错误，则不进行跳转
				var isErrHas = true;
				$("#showTable span").each(function(){
					// console.info("html ==  " + ($(this).html() == ""));
					if($(this).html() != ""){
						isErrHas = false;
					}
				});
				if(isErrHas){
					// alert(_searchValue.val());
					var _form=$("#leftTopFrameForm");
			        _form.attr("action","${base}/manage/queries/queriesAction.${actionExt}?tableCode=${tableCode}");
			        _form.submit();
				}
			}
			// 导出数据			
			function clickExportSubmit(){
			// 判断 如果存在错误，则不进行跳转
				var isErrHas = true;
				$("#showTable span").each(function(){
					console.info("html ==  " + ($(this).html() == ""));
					if($(this).html() != ""){
						isErrHas = false;
					}
				});
				if(isErrHas){
					var _form=$("#leftTopFrameForm");
				    _form.attr("action","${base}/manage/queries/exportAction.${actionExt}?tableCode=${tableCode}");
				    _form.submit();
				}
			
			}
		</script>

		<style type="text/css">
.Per_sel_ul{padding:0px;border: 1px solid #b2bac5;}
.Per_sel_ul li{width:100%; display:block; height:25px; cursor:pointer; *margin-bottom:-2px; text-overflow: ellipsis; -moz-text-overflow: ellipsis;
overflow:hidden; line-height:25px;}
/*选中状态 */
 .Per_sel_ul li.bg{background:rgb(255,153,0);}

.Per_sel_ul label{padding:0 0 0 16px; cursor:pointer; display:block; width:100%;white-space: nowrap; }
/*部门选择框样式*/
.Per_sel_ul label.optionDept{background:url(../images/xzyg_liicon_b.gif) no-repeat left;}
/*人员选择框样式*/
.Per_sel_ul label.optionEmp{background:url(../images/xzyg_liicon_p.gif) no-repeat left;}

.ul_search{padding:0px;border:1px solid #b2bac5;}
.ul_search li{width:480px;height:27px;*margin-bottom:2px; text-overflow: ellipsis; -moz-text-overflow: ellipsis;
overflow:hidden; line-height:25px;border-bottom:1px solid #b2bac5;padding:3px;}

.ul_search label{valign:middle;padding:0 0 0 16px; cursor:pointer; display:block; width:100%;white-space: nowrap; font:12px/ 120% Arial, Helvetica, sans-serif; }


.mainright_tdtop{
	color:#004685;
	line-height:25px;
	font-weight:bold;
	background-image:url(../../images/barbk.png);
	background-position:100% 100%;
	background-repeat:repeat;
	height:25px;
	clear:both;
}
.topbar{
	color:#004685;
	line-height:25px;
	font-weight:bold;
	background-position:100% 100%;
	background-repeat:repeat;
	height:25px;
	border:1px;
	clear:both;
}

	</style>
	</head>
	<body>
		<div style="border:0px solid #000;margin:2px;margin-top:1px;" >
				<form id="leftTopFrameForm" action="" method="post"
					target="queriesRightBottom">
					<input type="hidden" value="" name="searchValue" id="searchValue" />
				</form>
			<div style="height:25px;width:100%;border-left:1px solid #b2bac5;border-right:1px solid #b2bac5;" class="mainright_tdtop" >
				<label style="padding-left:10px;">${tableName}</label>
			</div>
			<div style="background-image:url(../../images/menubg2.jpg);clear:both;width:100%;margin:0;text-align:left;border-left:1px solid #b2bac5;border-right:1px solid #b2bac5;">
				<input type="button" value="导出数据" id="exportSubmit" name="exportSubmit" class="bot1other"
					onclick="clickExportSubmit();" style="display:none;" />
				<input type="button" value="查询" name="searchSubmit" class="bot1other"
					onclick="clickSubmit();" />
			</div>
			<div style="width:100%;height:270px;margin:0;border-left:1px solid #b2bac5;border-right:1px solid #b2bac5;border-bottom:1px solid #b2bac5;">
				<div
					style="top:39px;width:150px;height:250px;float:left;margin:0;margin-left:15px;">
					<div style="height:25px;width:160px;margin-top:0px;;background:url(../../images/datainc.png) no-repeat left;">&nbsp;</div>
					<ul class="Per_sel_ul" style="OVERFLOW:auto;height:240;">
						<c:forEach var="vo" items="${itemList}">
							<li id="_perLi_${vo.itemCode }" onclick="perUlLiClick($(this));" itemName="${vo.itemName}"
								itemType="${vo.itemType }" itemCode="${vo.itemCode }"> 
								<label>${vo.itemName} </label>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div style="height:250px;margin-left:185px;">
					<div style="height:25px;width:480px;margin-top:3px;;background:url(../../images/datacondation.png) no-repeat left;">&nbsp;</div>
					<ul id="showTable" class="ul_search"
						style="OVERFLOW-y:auto;OVERFLOW-x:hidden;height:240;width:481px;">
					</ul>
				</div>
			</div>
			
		</div>
	</body>
</html>
