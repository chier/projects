<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<!--部门操作成功页面-->
<html>
  <head>
    <title></title>
	<%@ include file="/commons/meta.jsp"%>
    
     <script language="Javascript">
		
			function goBack()
		 	{
		 		if('${action}' == 'modifyPwd'){
		 			alert('success!');
		 			
		 		}else{
		 			module.action="${base}/manage/safemanage/webadmin/listWebAdminAction.${actionExt}";
		 			module.submit() ;
		 		}
		 	}
		
	</script>
  </head>
<body leftmargin="0" topmargin="0" scroll="no" style="overflow-y:hidden">
<form action="" name="module" method="post">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="bodybg" align="center">
	<table width="350" height="182" border="1" cellpadding="0" cellspacing="0">
            <tr>
              <td class="table_topl"></td>
              <td class="table_topm table_title"><fmt:message key="efetionmanage.common.prosityouops"/></td>
              <td class="table_topr"></td>
            </tr>
            <tr>
              <td class="table_middlel"></td>
              <td class="table_middlem">
			     <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     
                     <td valign="top" class=" ht_mainpadding"><p><span class="colorange"><fmt:message key="efetionmanage.common.operator_success"/></span><br>
                       <br>
                     <br>
					 
                     </p>
                       <p>&nbsp;</p>
                       <p>
                       
                       <label>
                       <input type="button" name="Submit2" value="<fmt:message key="back"/>" onclick="javascript:goBack();" class="button2">
                       </label>
                     </p></td>
                   </tr>
                 </table></td>
              <td class="table_middler"></td>
            </tr>
            <tr>
              <td class="table_bottoml"></td>
              <td class="table_bottomm"></td>
              <td class="table_bottomr"></td>
            </tr>
          </table>	
    </td>
  </tr>
</table>	
</form>	  
 </body>
</html>