<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html> 
	<head>
		<title>原始数据</title>
		<link href="${base }/css/main1.css" rel="stylesheet" type="text/css" />
		<%@ include file="/commons/meta.jsp"%>
		<SCRIPT LANGUAGE="JavaScript">

function f_frameStyleResize(targObj){

		var targWin = targObj.parent.parent.document.all[targObj.parent.name];
		
		if(targWin != null) {
		
			var HeightValue = targObj.document.body.scrollHeight
		
			if(HeightValue < 400){HeightValue = 400} //不小于600
			
			var defah = 510+(window.screen.height - 768);
			targWin.style.pixelHeight = defah;
			
		
		}

}

function f_iframeResize(){

		bLoadComplete = true; f_frameStyleResize(self);

}

var bLoadComplete = false;

window.onload = f_iframeResize;

</SCRIPT>
	</head>
	<body> 
<div>
<h1>&nbsp;</h1>

			
			
				
			<table cellpadding="0" cellspacing="0" border="0" style="width:650;">
				<tr><td width='10'>&nbsp;&nbsp;&nbsp;&nbsp;
				</td><td align="right" valign="middle"><img src="${base}/images/welcome/arra.png" width="70" height="70" ></td>
					<td valign="middle" align="left"><h1 > &nbsp;&nbsp;欢迎查阅原始调查数据信息</h1> <br/>
					<h4>&nbsp;&nbsp;&nbsp;&nbsp;点击左侧数据表，开始功能使用。</h4> </td>
					</tr>
			</table><br/>
			<table cellpadding="0" cellspacing="0" border="0" width=650>
				
			<tr>
				<td width=250>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td width='50' valign="top"><img src="${base}/images/welcome/data1.png" width="215"
							height="96"    />
				</td>
				<td width='50'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td width='100%' style="line-height:150%;font-family:微软雅黑">&nbsp;全国环境与健康试点调查数据分析平台提供原始数据的查阅、检索及导出功能，该功能将原始数据表单扫描件数字化，并根据环境区域和试点信息进行树形排列，所有最终节点均为该类调查原始表单集合，可通过页面按钮进行切换及重新导入。每调查表均可以作为图片保存，同时可以被Office软件引用。

				</td>
			</tr>
			<tr height=30>
				<td width='250'>&nbsp;
				</td>
				<td width='50'>
				</td>
				<td width='50'>&nbsp;
				</td>
				<td width='100%'>&nbsp;

				</td>
			</tr>
			<tr>
				<td width='50'>&nbsp;
				</td>
				<td width='50' valign="top"><img src="${base}/images/welcome/data2.png" width="215"
							height="96"    />
				</td>
				<td width='50'>&nbsp;
				</td>
				<td width='100%' style="line-height:150%;font-family:微软雅黑">本单元包含调查区县数据信息、企业调查数据信息、环境采样数据信息、检测化验数据信息及山西、江苏、浙江、江西、山东、广西、云南、广东等八个试点的居民调查数据信息。本单元数据信息以原始表格及列表信息两种形式提供，其中列表信息提供多种文件格式数据导出下载功能，可用于归档及形成报告。
				</td>
			</tr>
	  </table>
	</div>
</div>
		
	</body> 
</html>

