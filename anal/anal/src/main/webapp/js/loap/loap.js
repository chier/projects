			// $(document).ready(function(){
				// $(".Per_sel_ul li:even").css({background:"#e8e9ed"})
			//	$(".Per_sel_ul li").each(function(){
					// console.info($(this).attr("itemType") + "" + $(this).attr("itemName"));
			//	});
			// });

			function clickSave2(){
	            var txt_iframe ='<iframe frameBorder="0" width="300px" height="250px" '
	            	+ 'allowtransparency="true" id="tt" name="tt" '
	            	+ 'src="'+base+'/manage/loap/loapCustomTreeAction!index.'+actionExt+'?date='+Math.random()+'" style="display:block;border: 1px solid rgb(202, 217, 240);margin:0 auto;"></iframe>';
	      		      
	            var _txt_main = '<div style="width:280px;height:400px;padding-left:50px;border:0px solid red;">'
							+ '<div>选择存放的目录 <br/>'
							+ txt_iframe
							+ '</div>'
							+ '分析标题<input type="text" name="cosName" maxlength="40" id="cosName" value="" style="width:200px;"/><br/>'
							+ '描述信息<textarea name="texmemo" id="texmemo" '
							+ 'style="width:210px;height:90px;overflow-y:hidden;overflow-x:auto;"></textarea>'
							+ '</div>';
				top.$.prompt(_txt_main,{
					buttons:{确定:true, 取消:false},
					submit:function(v,m,f){
						if(v){
							var _v = top.document.getElementById("topCsId").value;
							var _titlename =  top.document.getElementById("cosName").value;
							var _texMemo = top.document.getElementById("texmemo").value;
							if(_titlename=="" || _texMemo=="")
							{
								alert("为了便于后续查看分析思路，请填写标题和描述。");
								return;
							}
							if(_v == ""){
								top.$.prompt("请选取一个目录！",{buttons:{确定:true},alertTyper:'error'});
							}else{
								top.document.getElementById("topCsId").value = "";
								var _form;
								var _lrtf;
								var _lrf;
								var _mf;
								var _lBLf;
								if(document.frames){
									// IE
									_mf   = top.document.frames["mainIframe"];
									_lrf  = _mf.document.frames["loapRight"];
									_lBLf =_lrf.document.frames["leftButtomLoadIframe2"]; 
									_lrtf = _lrf.document.frames["loapLeftTop"];
									//_lrtf = document.frames["mainIframe"].document.frames["loapRight"].document.frames["queriesLeftTop"];
									_form = _lrtf.$("#leftTopLoapFrameForm");
								}else{
									// FF
									 _mf = top.document.getElementById("mainIframe");
									 _lrf = _mf.contentWindow.document.getElementById("loapRight");
									 _lBLf = _lrf.contentWindow.document.getElementById("leftButtomLoadIframe2").contentWindow;
									_lrtf = _lrf.contentWindow.document.getElementById("loapLeftTop").contentWindow;
									_form = _lrtf.$("#leftTopLoapFrameForm");
								}
								if(_form){
									var _imgConfig = _lBLf.getSet();
									 $.ajax({
										type : "POST",
										async : true,
										url   :  base+"/manage/loap/loapAction!saveCostomana."+actionExt,
										data : "cosName=" + _titlename    //_lrtf.$("#cosName").val()
												+"&imgConfig=" + _imgConfig 
												+ "&sqlValue="+_lrtf.$("#sqlValue").val() 
												+ "&texmemo="+ _texMemo  		  //_lrtf.$("#texmemo").val() 
												+ "&pid="+_v,
										// dataType : "json",
										success : function(msg) {
											top.$.prompt("保存成功!",{alertType:'msg'});
										},
										error : function(XMLHttpRequest, textStatus, errorThrown) {
											alert("error:"+textStatus);
										}
									});
									// _form.attr("action",base+"/manage/loap/loapAction!saveCostomana."+actionExt);
									// _form.submit();
									// return true;
								}
								top.jQuery.ImpromptuClose();
							}
						}
						else
						{
	             	top.jQuery.ImpromptuClose();
	          }
	          top.document.getElementById("topCsId").value = "";
					}
				});
			}
			
			// 保存事件
			function clickSave(){
				
				/*
				var _cosName = $("#cosName").val();
				if($.trim(_cosName) == ""){
					top.$.prompt("请填写标题!",{alertType:'msg'});
					return;
				}
				
				if($.trim(_cosName).length > 40){
					top.$.prompt("标题最多可输入40个字符!",{alertType:'msg'});
					return;
				}
				
				var _texmemo = $("#texmemo").val();
				if($.trim(_texmemo) == ""){
					top.$.prompt("请填写描述信息!",{alertType:'msg'});
					return;
				}
				*/
				/*
				var _b = clickSave2();
				alert(_b);
				if(_b){
					var _form=$("#leftTopLoapFrameForm");
					_form.attr("action",base+"/manage/loap/loapAction!saveCostomana."+actionExt);
					_form.submit();
					top.$.prompt("保存成功!",{alertType:'msg'});
				}
				*/
				if(!document.getElementById("customId")){
					clickSave2();
				}else{
					var _id = document.getElementById("customId").value;
					if(_id=="") clickSave2();
					else updateImg();
				}

			}
			function updateImg()
			{
				var _id = document.getElementById("customId").value;
				var strsql = document.getElementById("strsql").value;
				//alert(document.getElementById("customTitle").value);
				var _imgConfig = getSet();

				
				$.ajax({
						type : "POST",
						async : true,
						url   :  base+"/manage/loap/loapAction!saveCostomana."+actionExt,
										data : "imgConfig=" + _imgConfig 
												+"&customId=" +_id,
										success : function(msg) {
											top.$.prompt("保存成功!",{alertType:'msg'});
										},
										error : function(XMLHttpRequest, textStatus, errorThrown) {
											alert("error:"+textStatus);
										}
									});			
			}
			/**
			 * 查询事件
			 */
			function searchStn(){
				var _sql = "select ";
				// 字段值信息
				var _itemCode;
				var _itemName;
				var _tableCode = $("#tableCode").val(); 	//表名称
				var _sv; // select 的值
				var _l = $(".ul_Measure li").size();
				if(_l == 0){
					top.$.prompt("请选择度量!",{alertType:'msg'});
					$("#save").attr("disabled","true"); 
					return null;
				}
				var _dl = $(".ul_Dimension li").size();
				if(_dl == 0){
					top.$.prompt("请选择维度!",{alertType:'msg'});
					$("#save").attr("disabled","true"); 
					return null;
				}
				
				var _sqlG = " , ";
				$(".ul_Measure li").each(function(i,c){
					_itemName = $(this).attr("itemName");
					_itemCode = $(this).attr("itemCode");
					_sv = $(this).find("select").val();
					if(_sv == ""){
						_sqlG += " `" + _itemCode + "` as " + _itemName;
					}else{
						_sqlG += " " + _sv + "(`" + _itemCode + "`) as " + funcAlice(_itemName,_sv);
					}
					if(i != _l-1){
						_sqlG += ",";
					}
				});
				
				// from 和表名
				var _sqlF = " from `" + _tableCode+"_info`";
				
				var _sqlbG = "";
				//where 条件
				var _conditionS = $(".ul_search li").size();;
				var _sqlWh = "";
				if(_conditionS > 0){
					_sqlWh += " where "
					clickSubmit();
					var _searValue = $("#searchValue").val();
					_sqlWh += _searValue;
				}
				// group 条件 判断
				var _sqlGoup = "";
				var _ul = $(".ul_Dimension li").size();
				if(_ul > 0){
					_sqlGoup += " group by ";
					$(".ul_Dimension li").each(function(i,c){
					
						_itemCode = $(this).attr("itemCode");
						_itemName = $(this).attr("itemName");
						_sqlGoup += " `" + _itemCode + "`";
						_sqlbG += " `" + _itemCode + "` as " + _itemName;
						if(i != _ul - 1){
							_sqlGoup += ",";
							_sqlbG += ",";
						}
					});
				}
				_sql += _sqlbG;
				_sql += _sqlG;
				_sql += _sqlF;
				_sql += _sqlWh;
				_sql += _sqlGoup;
				// alert(_sql);
			//	console.info(_sql);
				$("#sqlValue").val(_sql);
				$("#save").removeAttr("disabled"); 
				
				var _form=$("#leftTopLoapFrameForm");
				//_form.attr("action",base+"/manage/custom/customAction!executeFlashTable2."+actionExt);
				//_form.attr("target","leftButtomLoadIframe1");
				//_form.submit();
				
				// _form.attr("action",base+"/manage/custom/customAction!executeTable."+actionExt);
				_form.attr("action",base+"/manage/loap/loapAction!showFlashTable."+actionExt);
				_form.attr("target","leftButtomLoadIframe2");
				_form.submit();
			}
			/**
			* 根据聚合函数，返回字段别名
			**/
			
			function funcAlice(itemname,funcname)
			{
				var itemfunc="";
				switch(funcname)
				{
						case "count":
					 		return itemname+"（计数）";
						case "sum":
						 	return itemname+"（合计值）";
						case "avg":
						 	return itemname+"（平均值）";
						case "max":
						 	return itemname+"（最大值）";
						case "min":
						 	return itemname+"（最小值）";
						default:
							return itemname;
				}
				return itemname;
			}
			
			/**
			 * 表头条件选择事件
			 */
			function perUlLiClick(obj){
				// alert(obj.attr("id"));
				$(".Per_sel_ul li").each(function(){
					if($(this).attr("id") == obj.attr("id")){
						obj.attr("class","bg");
					}else{
						$(this).removeAttr("class");
					}
				});
				
				// 当指标是数字时 维度按钮 不能点击				
				var _itemType = obj.attr("itemType");
				if(_itemType == 1){
					$("#btn_Dimension").attr("disabled", true); 
				}else{
					$("#btn_Dimension").removeAttr("disabled"); 
				}
			}
			
			/**
			 * 返回选中的 object 对象
			 */
			function getPerSelUlLi(){
				var obj;
				$(".Per_sel_ul li").each(function(){
					if($(this).attr("class") == "bg"){
						obj = $(this);
					}
				});
				return obj;
			}
			
			/**
			 * 条件点击事件
			 */
			function clickCondition(){
				var obj = getPerSelUlLi();
				if(obj && obj.attr("id")){
					showItem(obj);
				}else{
					top.$.prompt("请左侧选择指标",{alertType:'error'});
				}
			}
			
			/**
			 * 度量点击事件
			 */
			function clickMeasure(){
				var obj = getPerSelUlLi();
				if(obj){
					var _notInDimen = true;// 初始选择指标没有存在于维度中
					$(".ul_Dimension li").each(function(){
						if($(this).attr("itemCode") == obj.attr("itemCode")){
							_notInDimen = false;
						}
					});
					if(_notInDimen){
						createMeasureli(obj);
					}else{
						top.$.prompt("维度不能做为度量选项!",{alertType:'error'});
					}
				}else{
					top.$.prompt("请左侧选择指标",{alertType:'error'});
				}
			}
			
			/**
			 * 维度 点击事件
			 */
			function clickDimension(_obj){
				var obj = getPerSelUlLi();
				if(obj){
					createDimensionli(obj);
//					_obj.attr("disabled", true);
				}else{
					top.$.prompt("请左侧选择指标",{alertType:'error'});
				}
			}
			
			/**
			 * 度量 li 创建事件
			 */
			function createMeasureli(obj){
					var _itemName = obj.attr("itemName");
					var _itemType = obj.attr("itemType");
					var _itemCode =obj.attr("itemCode");
					var _li = $('<li itemType = "'+_itemCode+'" itemCode="'+_itemCode+'" itemName="'+_itemName+'" ><lable>'+_itemName+'</lable></li>');
					var _s = $("<select id='ms_se_"+_itemCode+"' name='ms_se_name_"+_itemCode+"'></select>");
					var _o = "";
					// var inDim = false;	// 判断创建的值是否存在维度中 
					// var _isDimSize = $(".ul_Dimension li").size();	// 判断维度中是否在值
					/*
					$(".ul_Dimension li").each(function(){
						if($(this).attr("itemCode") == _itemCode){
							inDim = true;
						}
						isDim = false;
					});
					
					if(inDim){
						_o += '<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>';
					}else{
					
						if(_isDimSize == 0){
							_o += '<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>';
						}
					*/
						_o += '<option value="count">&nbsp;计数</option>';
						_o += '<option value="sum">&nbsp;合计值</option>';
						_o += '<option value="avg">&nbsp;平均值</option>';
						if(_itemType == 1){
							_o += '<option value="max">&nbsp;最大值</option>';
							_o += '<option value="min">&nbsp;最小值</option>';
						}
					// }
					var _img = $("<img style='cursor:pointer;' width='10' height='10' onclick='removeMeasureElement($(this));' src='"+base+"/images/delete.png' />" );
					_s.append(_o);
					_li.append(_s);
					_li.append(_img);
					$("#ul_Measure").prepend(_li);
			}
			/**
			 * 删除维度元素判断
			 */
			function removeMeasElem(obj){
				
				var _l = obj.parent();
				var _u = _l.parent();
				var itemCode = _l.attr('itemCode');
				// 当删除的节点表示最后一个节点时
				if(_u.children().size() == 1){
					$(".ul_Measure li").each(function(){
						var _itype = $(this).attr("itemType");
						$(this).find("select").each(function(){
							$(this).empty();
							// $(this).append('<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>');
							$(this).append('<option value="count">&nbsp;&nbsp;count</option>');
							$(this).append('<option value="sum">&nbsp;&nbsp;sum</option>');
							$(this).append('<option value="avg">&nbsp;&nbsp;avg</option>');
							if(_itype == 1){
								$(this).append('<option value="max">&nbsp;&nbsp;max</option>');
								$(this).append('<option value="min">&nbsp;&nbsp;min</option>');
							}
						});
						/*
					$("#ul_Measure select").each(function(){
						$(this).empty();
						$(this).append('<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>');
						$(this).append('<option value="count">&nbsp;&nbsp;count</option>');
						$(this).append('<option value="sum">&nbsp;&nbsp;sum</option>');
						$(this).append('<option value="avg">&nbsp;&nbsp;avg</option>');
						$(this).append('<option value="max">&nbsp;&nbsp;max</option>');
						$(this).append('<option value="min">&nbsp;&nbsp;min</option>');
						*/
					});
				}
				
				/*
				// 判断当前被删除的节点 是否还存在
				
				var _cs = _u.find("li[itemCode='"+_l.attr('itemCode')+"']").size();
				// 当前存在的节点 只剩下最后一个时
				if(_cs == 1){
					$("#ul_Measure li").each(function(){
						if($(this).attr("itemCode") == itemCode){
							$(this).find("select").each(function(){
								$(this).empty();
								$(this).append('<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>');
								$(this).append('<option value="count">&nbsp;&nbsp;count</option>');
								$(this).append('<option value="sum">&nbsp;&nbsp;sum</option>');
								$(this).append('<option value="avg">&nbsp;&nbsp;avg</option>');
								$(this).append('<option value="max">&nbsp;&nbsp;max</option>');
								$(this).append('<option value="min">&nbsp;&nbsp;min</option>');
							});
						}
					});
				}
				*/
				/*
				_u.children().each(function(){
					$(this).attr("itemCode");
				});
				*/
				
//				_obj.attr("disabled", true);
				// $("#btn_Dimension").removeAttr("disabled"); 
				removeParentElement(obj);
			}
			/**
			 * 删除度量节点
			 */
			function removeMeasureElement(obj){
				var _l = obj.parent();
				var _u = _l.parent();
				if(_u.children().size() == 1){
					$("#save").attr("disabled","true"); 
				}
				removeParentElement(obj);
			}
			/**
			 * 删除父级点
			 */
			function removeParentElement(obj){
				obj.parent().remove();
				
			}
			
			function removeIdElement(id){
				$("#"+id).remove();
			}
			/**
			 * 维度 li 创建事件
			 */
			function createDimensionli(obj){
					var _itemName = obj.attr("itemName");
					var _itemType = obj.attr("itemType");
					var _itemCode =obj.attr("itemCode");
					
					var _li = $('<li id="di_li_'+_itemCode+'" itemCode="'+_itemCode+'" itemName="'+_itemName+'"><lable>'+_itemName+'</lable></li>');
					var _img = $("<img style='cursor:pointer;' width='10' height='10' onclick='removeMeasElem($(this));' src='"+base+"/images/delete.png' />" );
					
					_li.append(_img);
					$(".ul_Measure li").each(function(){
						if($(this).attr("itemCode") == _itemCode){
							$(this).remove();
						}
					});
				
					$(".ul_Measure option[value='']").each(function(){
						$(this).remove();
					});
			
					/*
					// 度量的值变成空值
					$("[name='ms_se_name_"+_itemCode+"']").each(function(){
						$(this).html("<option value=''>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>");
					});
					*/
					$("#ul_Dimension").prepend(_li);
			}
			
			/**
			 * 判断 值是否在维度列表中
			 * return true 表示没有在  flase 表示在
			 */
			function isInDim(obj){
				var isDim =  true;
				$(".ul_Dimension li").each(function(){
					console.info($(this).attr("itemCode") +"    " + obj.attr("itemCode"));
					if($(this).attr("itemCode") == obj.attr("itemCode")){
						isDim = false;
					}
				});
				return isDim;
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
						_searchValue.val(_searchValue.val() + " `" + $(this).val() +"` ");
					});
					
					// 标点信息
					$(this).find(".inputItComparison").each(function(){
						searCompar = $(this).val();
						_searchValue.val(_searchValue.val() + " " + $(this).val());
					});
					var _errSpan = $(this).find("span");
					_errSpan.html("");
					$(this).find(".inputItValue").each(function(){
								if(searCompar == "like"){
									_searchValue.val(_searchValue.val() + " '%" + $(this).val() + "%' ");
								}else{
									_searchValue.val(_searchValue.val() + " '" + $(this).val() + "' ");								
								}
					});
				});
			}
			// 导出数据			
			function clickExportSubmit(){
			// 判断 如果存在错误，则不进行跳转
				var isErrHas = true;
				$("#showTable span").each(function(){
					// console.info("html ==  " + ($(this).html() == ""));
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