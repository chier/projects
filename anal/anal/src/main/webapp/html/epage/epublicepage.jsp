<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/html/epage/epagehead.jsp" %>
<%@ page import="com.cmcc.framework.model.corporation.CompanyInfo"%>
<% CompanyInfo companyInfo=(CompanyInfo) request.getAttribute("COMPANYINFO");%>
<html>
	<head>
		<title>${requestScope.platformTitle}</title>
		<link rel="stylesheet" type="text/css" href="${base}/commons/epages/styles.css?v=24" media="screen" />
<%@ include file="/html/epage/meta.jsp" %>
	</head>
<!-- 公共页面整体布局 -->
<body>
<table width="760px" border="0" cellspacing="1" cellpadding="0"  align="center">
  <tr>
    <td>
      <div width="100%" height="150px" style="background-color:#C96" >
            <div id='top_container'> 公司名称：<%=companyInfo.getCorpName()%> </div>
            <div id='top_container'> 公司LOGO：<%=companyInfo.getLogoCrc()%> </div>
      </div>
   </td>
  </tr>
  <tr>
    <td>
<div class="demo">
      <div class="column">
      
            <div class="portlet" id='img_news'>
                <div class="portlet-header" onclick="sor()"></div>
                <div class="portlet-content" id='show_weather'></div>
            </div>
            
            <div class="portlet" id='img_news'>
                <div class="portlet-header" ></div>
                <div class="portlet-content" id='show_HelpCenter'></div>
            </div>
            
            <div class="portlet" id='img_news'>
                <div class="portlet-header"></div>
                <div class="portlet-content" id='show_ELink'></div>
            </div>
            
      </div>
       <div class="column">
       
            <div class="portlet" id='img_news'>
            <div class="portlet-header"></div>
            <div class="portlet-content" id='enotice'>
                <table width="100%" border="0" cellpadding="2" cellspacing="4">
                    <tr>
                    </tr>
              </table>
              </div>
           </div>
           
            <div class="portlet" id='img_news'>
                <div class="portlet-header" ></div>
                <div class="portlet-content" id='show_tabs'>
                <table width="100%" border="0" cellpadding="2" cellspacing="4">
                  <tr>
                  </tr>
                </table>
                </div>
            </div>
        
        <div class="portlet" id='img_news'>
            <div class="portlet-header" ></div>
            <div class="portlet-content" id='show_EFetionMsg'></div>
        </div>
        
      </div>
      <div class="column">
      
        <div class="portlet" id='img_news'>
            <div class="portlet-header"></div>
            <div class="portlet-content" id='show_download'></div>
        </div>
        
        <div class="portlet" id='img_news'>
            <div class="portlet-header"></div>
            <div class="portlet-content" id='show_DemoCenter'></div>
        </div>
        
        
              <div class="portlet" id='img_news'>
        <div class="portlet-header">外网搜索</div>
        <div class="portlet-content">
            
                <div id="tabs">
                    <ul class="tab_ul">
                        <li><a href="#tabs-1">Google</a></li>
                        <li><a href="#tabs-2">Yahoo</a></li>
                        <li><a href="#tabs-3">百度</a></li>
                    </ul>
                    <div id="tabs-1">
                        <!--google search star-->
                        <table cellpadding="0" cellspacing="0" class="search"><tr>
                        <td class="ipt">
                                    <table CELLPADDING="0" CELLSPACING="0">
                                    <tr><td><img src="${base}/images/efetionpage/webSearch/google.gif" class="searchLogo"/></td></tr>
                                     <tr>
                                      <td>
                                            <input type="text" class="searchInput" id='googlesearch' accesskey="t" maxlength="150"/>
                                            <input type="button"  value="搜索" class="searchButton" onclick="searchclick('google')"/>
                                       </td>
                                       </tr>
                                       </table>

                          </td>
                         </tr></table>
                        <!--google search end-->
                    </div>
                    <div id="tabs-2">
                        <!--yahoo search start-->
                                 <table cellpadding="0" cellspacing="0" class="search"><tr>
                                 <td class="ipt">
                                            <table CELLPADDING="0" CELLSPACING="0">
                                            <tr><td><img src="${base}/images/efetionpage/webSearch/yahoo.gif" class="searchLogo"/></td></tr>
                                            <tr><td>
                                                    <input type="text" class="searchInput" id='yahoosearch' accesskey="t" maxlength="150"/>
                                                    <input type="button"  value="搜索" class="searchButton" onclick="searchclick('yahoo')"/>
                                                    </td></tr></table>
                                       </td>
                                                    </tr></table>
                    <!--yahoo search end-->
                    </div>
                    <!--baidu search star-->
                    <div id="tabs-3">
                        
                    <table cellpadding="0" cellspacing="0" class="search"><tr>
                    <td class="ipt">
                                <table CELLPADDING="0" CELLSPACING="0">
                                <tr><td><img src="${base}/images/efetionpage/webSearch/baidu.gif" class="searchLogo"/></td></tr>
                                <tr><td>
                                        <input type="text" class="searchInput" id='baidusearch' accesskey="t" maxlength="150"/>
                                        <input type="button"  value="搜索" class="searchButton" onclick="searchclick('baidu')"/>
                                  </td></tr></table>
                      </td>
                            </tr></table>
                            <!--baidu search end-->
                    </div>
                </div>
      </div>
    </div>
        
      </div>
  </div>    
    </td>
  </tr>
  <tr>
    <td align="center">
          <div id="footer">
            <div><a href="http://www.fetion.com.cn/law.aspx">法律声明</a><a href="http://www.fetion.com.cn/aboutchinamobile.aspx">关于移动</a><a href="http://www.fetion.com.cn/aboutus.aspx">关于飞信</a><a href="http://www.fetion.com.cn/contactus.aspx">联系我们</a><a href="http://www.fetion.com.cn/sitemap.aspx">站点地图</a><a href="http://www.fetion.com.cn/links.aspx">友情链接</a></div>
            <div>京ICP备05024888号????7×24小时客服电话：10086</div>
          </div>
    </td>
  </tr>
</table>

  <div id='showdetail'>
  <div id='showdetailbody'></div>
  <div id='showdetailfoot'></div>
</div>
  
  <div id='showmore'>
  <div id='showmorebody'></div>
  <div id='showmorefoot'></div>
  </div>
</body>
</html>
