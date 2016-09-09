<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>loap查询</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${base }/js/loap/loap.js"></script>
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
			
			var tt = "leftframe<%=System.currentTimeMillis()%>";
			
			/**
			 * 显示 item 信息
			 * 姓名   类型 : 1表示数字 2 表示字符。 3 code  代码信息
			 */
			function showItem(obj){
				var itemName = obj.attr("itemName");
				var itemType = obj.attr("itemType");
				var itemCode = obj.attr("itemCode");
				
				var _itemHtmlLi = $("<li>").attr("id","li_"+itemCode);
				// table 信息
				var _itemHtml = "<table>";
				//tr
				_itemHtml += "<tr>";
				_itemHtml += "<td><img style='cursor:pointer;' width='10' height='10' onclick='removeIdElement(\"li_"+itemCode+"\");' src='"+base+"/images/delete.png' /></td>";
				// 字段名称
				_itemHtml += "<td width='140'>"
				_itemHtml += "<label style='width:134px;word-wrap:break-word;word-break:keep-all;overflow:hidden;'>" + itemName + "</label>";	
				_itemHtml += "<input class='inputItCode' type='hidden' value='"+itemCode+"' />" ;	
				_itemHtml += "</td>"
				// 字段类型  1表示数字 2表示字符
				_itemHtml += "<td width='100'>"
				if(itemType == 1){
					_itemHtml += '<select class="inputItComparison" name="sex_T" style="width:80px">'
						+'<option value="<">&nbsp;&nbsp;&nbsp;&nbsp;&lt;&nbsp;&nbsp;</option>'
						+'<option value=">">&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;</option>'
						+'<option value="=">&nbsp;&nbsp;&nbsp;&nbsp;=&nbsp;&nbsp;</option>'
						+'<option value="<>">&nbsp;&nbsp;&nbsp;&lt;&gt;&nbsp;&nbsp;</option>'
						+'<option value="<=">&nbsp;&nbsp;&nbsp;&nbsp;&lt;=&nbsp;&nbsp;</option>'
						+'<option value=">=">&nbsp;&nbsp;&nbsp;&nbsp;&gt;=&nbsp;&nbsp;</option>'
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
						_itemHtml +='<input class="inputItValue" myType="'+itemType+'" name="inputItyValue" type="text" value="" maxlength="11"/>';
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
				// var _img = $("<img style='cursor:pointer;' width='20' height='20' onclick='removeParentElement($(this));' src='"+base+"/images/delete.png' />" );
				//_itemHtmlLi.prepend(_img);
				_itemHtmlLi.append(_itemHtml);
				$("#showTable").append(_itemHtmlLi);
				$(".ul_search li:even").css({background:"#e8e9ed"})
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
.ul_search li{width:413px;height:25px;*margin-bottom:-2px; text-overflow: ellipsis; -moz-text-overflow: ellipsis;
overflow:hidden; line-height:25px;border-bottom:1px solid #b2bac5;padding:5px;}

.ul_search label{padding:0 0 0 16px; cursor:pointer; display:block; width:100%;white-space: nowrap; font:12px/ 120% Arial, Helvetica, sans-serif; }


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

/* 度量 ul css*/
.ul_Measure{
	height:28px;OVERFLOW-x:auto;OVERFLOW-y:auto; 
}
.ul_Measure li{
float:left;margin-right:10px;line-height:25px;
}

/*  维度 ul css */
.ul_Dimension{
	height:28px;line-height:25px;OVERFLOW-x:auto;OVERFLOW-y:auto; 
}
.ul_Dimension li{
	float:left;margin-left:5px;
} 


	</style>
	</head>
	<body>
		<form id="leftTopLoapFrameForm" action="" method="post">
			<div style="border:0px solid #000;margin:5px;">
				<input type="hidden" value="" name="icId" id="icId" />
				<input type="hidden" value="" name="sqlValue" id="sqlValue" />
				<input type="hidden" value="" name="searchValue" id="searchValue" />
				<input type="hidden" value="${tableCode}" name="tableCode"
					id="tableCode" />
				<input type="hidden" value="${tableName}" name="tableName"
					id="tableName" />

				<div
					style="height:25px;width:100%;border-left:1px solid #b2bac5;border-right:1px solid #b2bac5;"
					class="mainright_tdtop">
					<label style="padding-left:10px;">
						${tableName}
					</label>
				</div>
				<div
					style="background-image:url(../../images/menubg2.jpg);clear:both;">
					<input type="button" onclick="searchStn();" value="查询"
						class="bot1other">
					<input type="button" name="save" id="save" disabled="true"
						onclick="clickSave();" value="保存" style="display:none;"
						class="bot1other" />

				</div>
				<div
					style="border:0px solid red;width:100%;height:260px;border-left:1px solid #b2bac5;border-right:1px solid #b2bac5;">
					<div
						style="width:150px;height:250px;float:left;margin:0;margin-left:5px;">
						<div
							style="height:25px;width:150px;margin-top:3px;;background:url(../../images/datainc.png) no-repeat left;">
							&nbsp;
						</div>
						<ul class="Per_sel_ul" style="OVERFLOW:auto;height:240;">
							<c:forEach var="vo" items="${itemList}">
								<li id="per_sel_li_${vo.itemCode }"
									onclick="perUlLiClick($(this));" itemName="${vo.itemName}"
									itemType="${vo.itemType }" itemCode="${vo.itemCode }">
									<label>
										${vo.itemName}
									</label>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div
						style="border:0px solid red;width:490px;height:270px;float:left;">
						<div
							style="width:70px;height:241px;border:0px solid red;float:left;">
							<ul>
								<li style="margin-top:20px;margin-left:5px;">
									<input type="button" value="度量>>" onclick="clickMeasure();"
										class="bot1other" />
								</li>
								<li style="margin-top:20px;margin-left:5px;">
									<input id="btn_Dimension" type="button" value="维度>>"
										onclick="clickDimension($(this));" class="bot1other" />
								</li>
								<li style="margin-top:65px;margin-left:5px;">
									<input type="button" value="条件>>" onclick="clickCondition();"
										class="bot1other" />
								</li>
							</ul>
						</div>
						<div
							style="width:415px;height:241px;float:left;border:0px solid #faa">
							<ul>
								<li style="margin-top:20px;">
									<div style="border:1px solid #b2bac5">
										<ul id="ul_Measure" class="ul_Measure">
										</ul>
									</div>
								</li>
								<li style="margin-top:20px;">
									<div style="border:1px solid #b2bac5">
										<ul id="ul_Dimension" class="ul_Dimension">
										</ul>
									</div>
								</li>
								<li style="margin-top:20px;">
									<div
										style="height:26px;width:415px;margin-top:1px;background:url(../../images/datacondation.png) no-repeat left;"></div>
									<div style="margin-top:-2px;">
										<ul id="showTable" class="ul_search"
											style="OVERFLOW-y:auto;OVERFLOW-x:hidden;height:121;border-top:0px solid #b2bac5;">
										</ul>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div style="border:0px solid
				red;width:100
					%;height:15px;margin:0;text-align:center;border-left:1px solid
					#b2bac5;border-right:1px solid #b2bac5;border-bottom:1px solid
					#b2bac5;">
				</div>
			</div>
		</form>
	</body>
</html>
