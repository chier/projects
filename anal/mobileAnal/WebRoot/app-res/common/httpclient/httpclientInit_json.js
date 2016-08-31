//页面加载后，需要做的
$().ready(function() {

	$('#_tt_request_url').trigger("change");
	
	var host = document.location.protocol + "//" + document.location.host + contextpath;
	$('#tt_request_url').val(host);
	
	
	//tt_setupPutdown(document.getElementById("tt_panelHead"), document.getElementById("tt_queryTable"));

});


function getHtml(formId, showData) {
	document.getElementById("html").value = "";
	 $.ajax({
				url :  contextpath + "/common/httpclient/getHtml.talent",
				data : $("#" + formId).serialize(),
				type : "POST",
				dataType : "json",
				headers: {"JsonAuthenticationFilter": "true"},
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
						
						
//						document.getElementById("html_div").innerHTML = retObj.data;
//						var xxx = $("mydiv > input");//.select(":input");
//						var index = 0;
//						alert(xxx);
						
						var ss = retObj.data;
						try{
							ss = JSON.stringify(retObj.data);
						}catch(e) {
							
						}
						
						if (true){
							document.getElementById("html").value = ss;
						}else{
							document.getElementById("html").value = _data.responseText;
						}
						
						
						document.getElementById("urlForm").target = "html_container_iframe_name";
						document.getElementById("urlForm").method = "POST";
						document.getElementById("urlForm").action = contextpath + "/common/echoHtml.talent";
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
	var inputEle = talent.Util.createInputElement("text");
	cell.appendChild(inputEle);
	inputEle.setAttribute("name", prefix + "_name");
	inputEle.style.width = "150px";
	
	cell = document.createElement("td");//theadRow.insertCell(theadRow.cells.length);
	row.appendChild(cell);
	var inputEle = talent.Util.createInputElement("text");
	cell.appendChild(inputEle);
	inputEle.setAttribute("name", prefix + "_value");
	inputEle.style.width = "150px";
	
	cell = document.createElement("td");
	row.appendChild(cell);
	var inputEle = talent.Util.createInputElement("checkbox");
	cell.appendChild(inputEle);
}
function removeField(tableId, tableBodyId, prefix) {
	var table = document.getElementById(tableBodyId);

	var rowCount = table.rows.length;

	for (var i = 2; i < rowCount; i++) {
		var row = table.rows[i];
		if (row.cells[2]) {
			var chkbox = row.cells[2].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				table.deleteRow(i);
				rowCount--;
				i--;
			} 
		}

	}
}

