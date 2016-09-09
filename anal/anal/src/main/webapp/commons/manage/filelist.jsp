
<script language="javascript">
function downloadAttach(obj){

	window.location="${base}/public/common/contentmanager/content/downloadAttach.${actionExt}?attachId=" + obj;
	
}
function ow(owurl){
var tmp=window.open("","","");
tmp.location=owurl;
}


function deleteAttach(obj,type){
 var attachlistForm=document.getElementById("attachlistForm");
 if(type=='1')
    attachlistForm.action="${base}/manage/contentmanage/content/contentAttachOperator!removeFile.${actionExt}?attachId=" + obj;
 else{
     attachlistForm.action="${base}/manage/contentmanage/content/contentAttachOperator!removeFile.${actionExt}?attachname=" + obj;
 }
    if(confirm("<fmt:message key="delOrNot.msg2"/>")) 
    	attachlistForm.submit();
  
}
function attachlistcallback(obj){
   var tr = document.getElementById(obj);
 document.getElementById("attachlistbody").removeChild(tr);
}
</script>
<form action="" method="post" name="attachlistForm" id="attachlistForm"
	target="attachlistiframe">
	<table class="area-table">
		<a name="attachlist"><iframe name='attachlistiframe'
				id="attachlistiframe" style="display: none"></iframe>
		<tbody id="attachlistbody">


			<tr>
				<td aligh="left" colspan="2" class="left">
					<fmt:message
						key="efetionmanage.framework.contentManager.attachlist" />
				</td>
			</tr>
			<c:forEach items="${attachList}" var="attach" varStatus="status">
				<tr id="${attach.identifier}">
					<td>
						<a href="#attachlist"
							onclick="javascript:downloadAttach(${attach.identifier });">${attach.attName}</a>
					</td>
					<td>
						<a href="#attachlist"
							onclick="javascript:deleteAttach(${attach.identifier },'1');"><fmt:message
								key="efetionmanage.framework.contentManager.delete" /> </a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>
