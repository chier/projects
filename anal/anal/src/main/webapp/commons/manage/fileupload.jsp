
<form action="" encType="multipart/form-data" target="upiframe"
	method="post" id="upform" name="upform">
	<ss:token name="uploadfile.token" />
	<a name="uploadframe"></a>
	<iframe name='upiframe' id="upiframe" style="display: none"></iframe>
	<table class="area-table">
		<tbody id="uploadtable">
			<tr>
				<td class="left">
					<a href="#uploadframe" onclick="javascript:addAttach();"><fmt:message
							key="efetionmanage.framework.contentManager.addAttach" /> </a>
				</td>
				<td>
					<span id="messagewarn" class="erro"> <fmt:message
							key="efetionmanage.framework.contentManager.warnAttachMaxSize">
							<fmt:param>${attachMaxSize}</fmt:param>
						</fmt:message> </span>

				</td>
				<td>
					<span id="message" class="erro"> &nbsp;</span>
				</td>

			</tr>
		</tbody>
	</table>

</form>

<script language="javascript">
var attmaxsize = ${attachMaxSize};
function upload(){
   var upform=document.getElementById("upform");
   var up = document.getElementById("up");
    upform.action="${base}/servlet/uploadattach";
    upform.submit();
}
 function addAttach(){
document.getElementById("message").innerHTML="";
 var up = document.getElementsByName("up");
  if(up.length==0){
     var table = document.getElementById("uploadtable");
       var tr= document.createElement("tr");
      tr.id="attach";
       var td = document.createElement("td");
       var td1=document.createElement("td");
       td1.width="10%";
       td1.align="left";
       td.width="20%";
       td.align="left";
       var td2=document.createElement("td");
       td1.innerHTML="<fmt:message key="efetionmanage.framework.contentManager.addAttach" />:";
       td.innerHTML="<input type='file' size='50' name='up' id='up' />";
       td2.innerHTML="<input type='button' class='bot1' value='<fmt:message key="efetionmanage.framework.contentManager.uploadAttach" />' onclick='javascript:upload();'/>";
       tr.appendChild(td1);
       tr.appendChild(td);
       tr.appendChild(td2);
        table.appendChild(tr);
 }

 }
 
 function callback(obj){
  var attach = document.getElementById("attach");
 	document.getElementById("uploadtable").removeChild(attach);
		var tt = obj.split("&");
		var type = tt[0];
		var filename=decodeURI(tt[1]);
		var message;
		switch(type){
			case '1':
				var tm = filename.split("~");
				
				message = "<fmt:message key="efetionmanage.framework.contentManager.uploadAttachSuccess" />";
				var attachlist = document.getElementById("attachlistbody");
 				if(attachlist!=null	){
 		     		 var tr= document.createElement("tr");
 		      		 tr.id=tm[0];
 		       		 var td = document.createElement("td");
 		        	 td.width="60%";
	 		       	 td.innerHTML=tm[1];
	       			 var td1=document.createElement("td");
	       			 td1.innerHTML="<a href='#attachlist' onclick=javascript:deleteAttach('" +tm[0]+"','0');><fmt:message key="efetionmanage.framework.contentManager.delete" /></a>";
	       			 tr.appendChild(td);
	       			 tr.appendChild(td1);
	       			 attachlist.appendChild(tr);
       		
 				}
 				break;
			case '0':
				message ="<fmt:message key="efetionmanage.framework.contentManager.uploadAttachError" />";
				break;
			case '-1':
				message ="<fmt:message key="efetionmanage.framework.contentManager.uploadAttachError2" />";
		
		}
		
 		document.getElementById("message").innerHTML=message; 
 }

</script>