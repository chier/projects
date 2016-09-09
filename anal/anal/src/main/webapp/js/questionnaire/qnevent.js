function addOption4Qtn(questionId,tableId,inputName,delEleName,basePath){
	var qType = $("#qnDeployType").val();
	var sltTable = tableId + ">tbody>tr";
	var trLength = $("#"+sltTable).length;
	if(0==qType){
		if( trLength > 4 ){
			$.prompt("  短信方式电子调查最多只能设置5个选项！",{buttons:{确定:true},alertType:'msg'});
			return false;
		}
	}else{
		if( trLength > 15 ){
			$.prompt("  web发布方式的电子调查每个问题最多可建15个选项！",{buttons:{确定:true},alertType:'msg'});
			return false;
		}
	}
	var optionT = $("#"+tableId);
	var optionIndex = String.fromCharCode(65+trLength);
	var showCss;
	if(trLength%2==0){
		showCss = "class='tablestr1'";
	}else{
		showCss = "";
	}
	var trHTML = "<tr " + showCss + "><td align='right'>" + optionIndex + "<span style='color:#9d0c0c'>&nbsp;&nbsp;</span></td>" ;
	trHTML += "<td align='left'><input type='text' name='"+inputName+"' style='width:250px;' maxlength='40'/></td>";
	trHTML += "<td><img onclick='javascript:deleteOption(\""+delEleName+"\");' src='" + basePath + "/images/icon_tdclose.gif' width='9' height='9' /></td></tr>";
	optionT.append(trHTML);
}
function deleteOption(tableId){
	var sltTable = tableId + ">tbody>tr";
	var sltTr = sltTable + ":last";
	var trLength = $("#"+sltTable).length;
	if(trLength<=2){
		$.prompt("  不允许继续删除，每个问题至少要有两个选项",{buttons:{确定:true},alertType:'msg'});
	}else{
		$("#"+sltTr).remove();
	}
}