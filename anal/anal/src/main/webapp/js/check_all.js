//check main checkbox the other checkboxs save to main checkbox
function checkAll(form,allName){
	for(var i=0;i<form.elements.length;i++)
	{
		var e=form.elements[i];
		if(e.name != allName)
		{
			e.checked=document.getElementById(allName).checked;
		}
	}
}

//click td checkbox checked=true or false
function checkOne(chkName,index) {
	var elms = document.getElementsByName(chkName);
	for(var i=0;i<elms.length;i++)
	{
		if(i==index)
		{
			elms[i].checked=!elms[i].checked;
		}
	}
}

//add data
function ad(urlStr){
	document.forms[0].action = urlStr;
	document.forms[0].target='';
	document.forms[0].submit();
}

//update data
function upd(form,idName,urlStr,msg1,msg2){
	var elms = document.getElementsByName(idName);
	var x = 0;
	for(var i=0;i<elms.length;i++)
	{
		if(elms[i].checked == true)
		{
			x=x+1;
		}
	}
	if(x>1)
	{
		alert(msg1);
		return false;
	}
	if(x<1)
	{
		alert(msg2);
		return false;
	}
	
	form.action=urlStr;
	form.submit();
}

//delete data
function del(form,idName,urlStr,msg1,msg2){
	var elms = document.getElementsByName(idName);
	var x = 0;
	for(var i=0;i<elms.length;i++)
	{
		if(elms[i].checked==true)
		{
			x=x+1;
		}
	}
	if(x<1)
	{
		alert(msg1);
	}else
	{
		if (confirm(msg2)){
		
			form.action=urlStr;
			form.submit();
		
		} else {
			
			return false;
		}
	}
}