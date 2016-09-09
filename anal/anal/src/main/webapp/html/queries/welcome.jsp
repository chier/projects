<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html> 
	<head>
		<title>即席查询</title>
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
					<td valign="middle" align="left"><h1 > &nbsp;&nbsp;欢迎使用即席查询模块</h1> <br/>
					<h4>&nbsp;&nbsp;&nbsp;&nbsp;点击左侧数据表，开始功能使用。</h4> </td>
					</tr>
			</table><br/>
			<table cellpadding="0" cellspacing="0" border="0" width=650>
				
			<tr>
				<td width=250>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td width='50' valign="top"><img src="${base}/images/welcome/query1.png" width="215"
							height="96"    />
				</td>
				<td width='50'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td width='100%' style="line-height:150%;font-family:微软雅黑">&nbsp;本单元可由用户根据自己的需求，灵活的选择查询条件，系统能够根据用户的选择生成相应的统计报表。即席查询与SQL脚本查询没有本质区别，都可由用户随意指定查询条件，即席查询只是将条件挑选过程可视化，同时利用程序调用使查询过程被优化。即席查询在使用过后可以形成查询模板存储供定制查询使用。

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
				<td width='50' valign="top"><img src="${base}/images/welcome/query2.png" width="215"
							height="96"    />
				</td>
				<td width='50'>&nbsp;
				</td>
				<td width='100%' style="line-height:150%;font-family:微软雅黑">本单元可对调查区县数据信息、企业调查数据信息、环境采样数据信息、检测化验数据信息及山西、江苏、浙江、江西、山东、广西、云南、广东等八个试点的居民调查数据信息进行查询。即席查询在未选择任何条件下默认在结果输出区域列出所有记录，所有查询均可以在结果输出后进行导出操作，导出文件格式为xls。
				</td>
			</tr>
	  </table>
	</div>
</div>
		
	</body> 
</html>

