<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp" %>
<html>
  <head>
   <link href="${base }/css/main.css" rel="stylesheet" type="text/css" media="all" />
   </head>
  <body>
  <form name="form1" id="form1" action="contentInfoOperator!updateCatalog.${actionExt}" method="post">
  <div id="main">
	<div class="title"><h4>栏目列表</h4></div>
    <div class="msg"></div>
    <div class="area-warp">
    	<h5>修改栏目名称</h5>
    	<div class="area-content">
		    <table class="area-table">
		      <tr>
		        <td align="right">栏目名称：</td>
		        <td align="left"><label>
		          <input type="text" name="title" class="f-text" id="title" maxlength="20" value="<c:out value="${catalog.title}" escapeXml="true"/>">
                  <input type="hidden" name="aiid" value="${catalog.identifier}"/>
		        </label></td>
		      </tr>
		      <tr>
		        <td align="right">栏目描述：</td>
		        <td align="left"><label>
		          <textarea name="desc" class="f-textarea" id="desc" cols="45" rows="5" onkeyup="bodylength();" onfocus="bodylength()"><c:out value="${catalog.desc}" escapeXml="true"/></textarea>
		        </label>
		        </td>
		      </tr>
		    </table>
		  </div>
		  <div class="f-bot">
        	<input type="button" name="button" id="button" class="bot2" value="保存" onclick="checkUpdate()">
		    <input type="button" name="b" class="bot2" value="取消" onclick="goBack();">
        </div>
    </div>
 </div>
</form>
<script type="text/javascript">
		function bodylength()
		{
			var maxlength=100;
			var conlength=document.getElementById("desc").value.length;
			if(conlength>maxlength)
			{
				var txt=document.getElementById("desc").value;
				document.getElementById("desc").value=txt.substring(0,maxlength);
			}
		}
		
		function goBack()
      	{
			window.location.href="${base}/manage/contentmanage/content/ColumnTree!getCatalogList.${actionExt}";
      	}

		function checkUpdate() {
			var resRslt = "";
			var title = $("#title").val();
			var txt = "请输入栏目名称";
		    if ($.trim(title) == "") {
				$.prompt(txt,{ 
					alertType:'msg'
				});
		    } else {
		    	$('#form1').ajaxSubmit({
					cache:false,
				    async:false,
				    type:'POST',
				    data: {submit:'1'},
				    url:'contentInfoOperator!updateCatalog.${actionExt}',
					success : function(data) {
						//alert(parent.url);
						parent.location.reload();
					}
				});
		    }
		}
</script>
</body>
</html>








