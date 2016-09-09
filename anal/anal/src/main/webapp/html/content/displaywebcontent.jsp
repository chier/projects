<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/html/epage/epagehead.jsp"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0057)http://news.jlu.edu.cn/new/ -->
<HTML>
	<HEAD>
		<%@ include file="/html/epage/meta.jsp"%>
		<TITLE></TITLE>
<META content="text/html; charset=gb2312" http-equiv=Content-Type>
<LINK rel=stylesheet type=text/css href="${base}/css/2010_style.css" media=all>
<LINK rel=stylesheet type=text/css href="${base}/css/liuyan.css" media=all>

<SCRIPT type=text/javascript src="${base}/js/content/zoom.js"></SCRIPT>


<SCRIPT LANGUAGE="JavaScript">

function f_frameStyleResize(targObj){

		var targWin = targObj.parent.document.all[targObj.name];
		
		if(targWin != null) {
		
			var HeightValue = targObj.document.body.scrollHeight
		
			if(HeightValue < 600){HeightValue = 600} //不小于600
		
			targWin.style.pixelHeight = HeightValue;
		
		}

}

function f_iframeResize(){

		bLoadComplete = true; f_frameStyleResize(self);

}

var bLoadComplete = false;

window.onload = f_iframeResize;

</SCRIPT>

<META name=GENERATOR content="MSHTML 8.00.7601.18094">
</HEAD>
<BODY>
<CENTER>
<TABLE border=0 cellSpacing=0 cellPadding=0 width=990 style="BORDER: #A3D1F0 1px solid;" >
  <TBODY>
  <TR>
    <TD bgColor=#ffffff align=middle>
    
      <TABLE border=0 cellSpacing=0 cellPadding=0 width=990>
        <TBODY>
        
        <TR>
          <TD>
            <TABLE border=0 cellSpacing=0 cellPadding=0 width=100% 
              align=center><TBODY>
              <TR>
                <TD vAlign=top width=100%>
                  <TABLE border=0 cellSpacing=0 cellPadding=0 width=100%>
                    <TBODY>
                    <TR>
                      <TD colspan=2 
                      style="PADDING-BOTTOM: 0px; PADDING-LEFT: 28px; PADDING-RIGHT: 0px; PADDING-TOP: 11px;BORDER-BOTTOM: #cccccc 1px solid;" 
                      
                      class=position vAlign=top><B>当前位置： </B>
                      <A href="${base}//manage/contentmanage/content/contentInfoOperator!indexContent.${actionExt }">首页</A> 
                        &gt;&gt; <A href="${base}/manage/contentmanage/content/contentInfoOperator!moreContent.${actionExt }?ctId=${CONTENTINFO.ctId }"><c:forEach var="t" items="${typeList}"><c:if test="${CONTENTINFO.ctId == t.ctId}">${t.ctName}</c:if></c:forEach></A>&gt;&gt; <c:out value="${CONTENTINFO.title }" escapeXml="false"></c:out></TD></TR>
                    <TR>
                    	<TD style="PADDING-BOTTOM: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 7px" class=position_1>
                        <TABLE style="BORDER: #cccccc 1px solid;" 
                        id=change_color cellSpacing=0 cellPadding=0 width=100% >
                        <TBODY>
                          <TR>
                            <TD id=show_title class=show_title><c:out value="${CONTENTINFO.title }" escapeXml="false"></c:out><BR>
                              <DIV style="DISPLAY: none" id=Loading></DIV></TD></TR>
                          <TR>
                            <TD class=show_author>
                            	<fmt:message	key='efetionmanage.framework.contentManager.contentReleaseDate' />:
															<fmt:formatDate value="${CONTENTINFO.releasedate}" type="date"	pattern="yyyy-MM-dd" />
                            	
                            	<c:if test="${not empty CONTENTINFO.author }">
																作者:  <fmt:message key='efetionmanage.framework.contentManager.contentAuthor' />:${CONTENTINFO.author }&nbsp;&nbsp;&nbsp;
								              </c:if>
                              浏览：<SPAN id=hits>${CONTENTINFO.viewNumber}</SPAN>&nbsp;&nbsp;&nbsp;&nbsp;附件下载次数:<SPAN id=hits>${CONTENTINFO.attachNumber}</SPAN>
                              </TD></TR>
                          <TR>
                            <TD class=show_author bgColor=#e7e7e7>【字体大小: <A 
                              href="javascript:doZoom(14)">小</A> <A 
                              href="javascript:doZoom(16)">中</A> <A 
                              href="javascript:doZoom(18)">大</A>】【背景颜色: <A 
                              href="javascript:change_color.style.backgroundColor='fafbe6';setColor('FAFBE6')"><IMG 
                              border=0 alt=杏仁黄 src="${base}/images/content/color1.gif" 
                              width=10 height=10></A> <A 
                              href="javascript:change_color.style.backgroundColor='fff2e2';setColor('FFF2E2')"><IMG 
                              border=0 alt=秋叶褐 src="${base}/images/content/color2.gif" 
                              width=10 height=10></A> <A 
                              href="javascript:change_color.style.backgroundColor='fde6e0';setColor('FDE6E0')"><IMG 
                              border=0 alt=胭脂红 src="${base}/images/content/color3.gif" 
                              width=10 height=10></A> <A 
                              href="javascript:change_color.style.backgroundColor='f3ffe1';setColor('F3FFE1')"><IMG 
                              border=0 alt=芥末绿 src="${base}/images/content/color4.gif" 
                              width=10 height=10></A> <A 
                              href="javascript:change_color.style.backgroundColor='dafafe';setColor('DAFAFE')"><IMG 
                              border=0 alt=天蓝 src="${base}/images/content/color5.gif" 
                              width=10 height=10></A> <A 
                              href="javascript:change_color.style.backgroundColor='e9ebfe';setColor('E9EBFE')"><IMG 
                              border=0 alt=雪青 src="${base}/images/content/color6.gif" 
                              width=10 height=10></A> <A 
                              href="javascript:change_color.style.backgroundColor='eaeaef';setColor('EAEAEF')"><IMG 
                              border=0 alt=淡灰 src="${base}/images/content/color7.gif" 
                              width=10 height=10></A> <A 
                              href="javascript:change_color.style.backgroundColor='ffffff';setColor('FFFFFF')"><IMG 
                              border=0 alt=银河白(默认色) src="${base}/images/content/color8.gif" 
                              width=10 height=10></A>】【阅读(${CONTENTINFO.viewNumber})】
                            </TD></TR>
                          <TR>
                            <TD class=show_text>
                              <DIV id=Zoom>
                              <c:out value="${CONTENTINFO.content}" escapeXml="false"></c:out>
                              <BR>
                              <c:if test="${not empty attachList}">
																<div style="margin-top:100px;">
																	<lable>附件信息</lable>
																	<c:forEach var="attach" items="${attachList}">
																		<ol><a href="javascript:void(0)" onclick="opUrl(${attach.identifier});" >${attach.attName}</a></ol>
																	</c:forEach>
																</div>
															</c:if>
                              </DIV><BR>
                              <DIV id=keyword align=left></DIV></TD></TR>
                          <TR>
                            <TD 
                            style="BORDER-BOTTOM: #bcbcbc 1px solid; BORDER-LEFT: #bcbcbc 1px solid; PADDING-BOTTOM: 2px; PADDING-LEFT: 15px; PADDING-RIGHT: 15px; BORDER-TOP: #bcbcbc 1px solid; BORDER-RIGHT: #bcbcbc 1px solid; PADDING-TOP: 2px" 
                            class=show_comment_class>
                              <A href="javascript:window.print();">[打印]</A> <A 
                              href="javascript:window.close();">[关闭]</A> <A 
                              href="#top">[返回顶部]</A>&nbsp;&nbsp;
                              <FONT style="FONT-SIZE: 14px" 
                              color=#cc3300>转载本网站文章，请注明出处及作者！</FONT></TD></TR>
                          
                            <TD 
                            style="PADDING-BOTTOM: 10px; LINE-HEIGHT: 150%; PADDING-LEFT: 25px; PADDING-RIGHT: 25px; COLOR: #333333; FONT-SIZE: 14px; PADDING-TOP: 1px" 
                            id=01 align=left name="01">
                              
                              </TD></TR></TBODY></TABLE></TD></TR>
                    
                    </TBODY>
                  </TABLE>
                </TD>
                <TD width=10></TD>
              </TR>
              </TBODY>
              </TABLE>
              </TD>
            </TR>
        <TR>
          <TD height=30>&nbsp;</TD></TR>
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>
        
        </CENTER>

</BODY>
<script language="javascript">
		function opUrl(ctid){
			$.ajax({
				type : "POST",
				async : true,
				url   :  base+"/manage/contentmanage/content/contentAttachOperator!isPermission."+actionExt,
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				data : "ctId="+ctid,
//				dataType : "json",
				success : function(msg) {
				// 	console.info("msg = " + msg);
					if(msg == 1){
						window.open("${base}/manage/contentmanage/content/contentAttachOperator!download.${actionExt}?ctId=" + ctid);
					}else{
						 top.$.prompt("你没有权限下载附件！",{buttons:{确定:true},alertType:'error'});
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.top.location=base + "/commons/beforetimeout.jsp";
				}
			});
		}
	</script>
</HTML>
