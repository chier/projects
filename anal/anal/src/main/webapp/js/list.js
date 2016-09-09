function checkAll(obj) {
	if (obj.checked)
		$("input[name=ids]").attr("checked", true);
	else
		$("input[name=ids]").attr("checked", false);
}
function checkOn(id) {
	$("#ids" + id).attr("checked",
			!$("#ids" + id).attr("checked"));
	deCheck(id);
}
function deCheck(id) {
	if (!$("#ids" + id).attr("checked"))
		$("#all").attr("checked", false);
	else if (isCheckAll())
		$("#all").attr("checked", true);
}
function isCheckAll() {
	var sub = document.getElementsByName("ids");
	for (i = 0; i < sub.length; i++) {
		if (!sub[i].checked)
			return false;
	}
	return true;
}
function isCheckOne() {
	var chk = document.getElementsByName("ids");
	var flag = false;
	for ( var i = 0; i < chk.length; i++) {
		if (chk[i].checked) {
			flag = true;
			break;
		}
	}
	return flag;
}
function editCheck() {
	var num = 0;
	var id = 0;
	var sub = document.getElementsByName("ids");
	for (i = 0; i < sub.length; i++) {
		if (sub[i].checked) {
			num++;
			id = sub[i].value;
		}
	}
	if (num != 1) {
		$.prompt("只能选择一项进行修改!", {
			alertType : 'msg'
		});
		return;
	}
	edit(id);
}