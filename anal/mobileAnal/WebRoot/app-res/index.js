//页面加载后，需要做的
$().ready(function() {
	tt.vf.req.addId("tt_method");

	$('#_tt_request_url').trigger("change");

	
	//tt_setupPutdown(document.getElementById("tt_panelHead"), document.getElementById("tt_queryTable"));

});


function getHtml(formId) {

	document.getElementById("html").value = "";
	 $.ajax({
				url :  contextpath + "/common/httpclient/getHtml.talent",
				data : $("#" + formId).serialize(),
				type : "POST",
				dataType : "json",
				cache : false,
				beforeSend : function() {
					tt_beforeSend();
				},

				complete : function(_data) {

					tt_complete();
					var retObj = JSON.parse(_data.responseText);

					var result = tt_showResult(retObj);
					if (result) {
//						$(document.getElementById('html_container_iframe').contentWindow.document.body).html(retObj.data);
//						
//						$(function($("#html_container_iframe").bind("load",function() {
//						
//						})));
						
						
						document.getElementById("html").value = retObj.data;
						
						document.getElementById("urlForm").target = "html_container_iframe_name";
						document.getElementById("urlForm").method = "POST";
						document.getElementById("urlForm").action = contextpath + "/common/echoHtml";
						document.getElementById("urlForm").submit();
					}
				}
			});
}

function getCookie(formId) {
	document.getElementById("html").value = "";
	$.ajax({
				url : contextpath + "/common/httpclient/getCookie.talent",
				data : $("#" + formId).serialize(),
				type : "POST",
				dataType : "json",
				cache : false,
				beforeSend : function() {
					tt_beforeSend();
				},

				complete : function(_data) {

					tt_complete();
					var retObj = JSON.parse(_data.responseText);

					var result = tt_showResult(retObj);
					if (result) {
						var cookieEs = document.getElementsByName("cookies");
						for (var i = 0; i < cookieEs.length; i++) {
							cookieEs[i].value = "";
						}

						for (var i = 0; i < retObj.data.length; i++) {
							cookieEs[i].value = retObj.data[i];
							cookieEs[i + 1].value = retObj.data[i];
						}

					}
				}
			});
}

function login(formId) {
	document.getElementById("html").value = "";
	$.ajax({
				url : document.getElementById(formId).action,
				data : $("#" + formId).serialize(),
				type : "POST",
				dataType : "json",
				cache : false,
				beforeSend : function() {
					tt_beforeSend();
				},

				complete : function(_data) {

					tt_complete();
					var retObj = JSON.parse(_data.responseText);

					var result = tt_showResult(retObj);
					if (result) {
						var cookieEs = document.getElementsByName("cookies");
						for (var i = 1; i <= cookieEs.length; i++) {
							cookieEs[i].value = "";
						}

						for (var i = 1; i <= retObj.data.length; i++) {
							cookieEs[i].value = retObj.data[i];
						}

					}
				}
			});
}

function addField(tableId, tableBodyId, prefix) {
	var tableBody = document.getElementById(tableBodyId);
	var row = tableBody.insertRow(tableBody.rows.length);
	
	var cell = document.createElement("td");//theadRow.insertCell(theadRow.cells.length);
	row.appendChild(cell);
	var inputEle = document.createElement("input");
	cell.appendChild(inputEle);
	inputEle.setAttribute("name", prefix + "_name");
	inputEle.style.width = "150px";
	
	cell = document.createElement("td");//theadRow.insertCell(theadRow.cells.length);
	row.appendChild(cell);
	var inputEle = document.createElement("input");
	cell.appendChild(inputEle);
	inputEle.setAttribute("name", prefix + "_value");
	inputEle.style.width = "150px";
	
	cell = document.createElement("td");//theadRow.insertCell(theadRow.cells.length);
	row.appendChild(cell);
	var inputEle = document.createElement("input");
	cell.appendChild(inputEle);
	inputEle.setAttribute("type", "checkbox");
}
function removeField(tableId, tableBodyId, prefix) {
	var table = document.getElementById(tableBodyId);

	var rowCount = table.rows.length;

	for (var i = 2; i < rowCount; i++) {
		var row = table.rows[i];
		var chkbox = row.cells[2].childNodes[0];
		if (null != chkbox && true == chkbox.checked) {
			table.deleteRow(i);
			rowCount--;
			i--;
		}

	}

}

