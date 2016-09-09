<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html> 
	<head>
		<title>预警分析</title>
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
					<td valign="middle" align="left"><h1 > &nbsp;&nbsp;欢迎使用预警分析功能</h1> <br/>
					<h4>&nbsp;&nbsp;&nbsp;&nbsp;点击左侧数据表，开始功能使用。</h4> </td>
					</tr>
			</table><br/>
			<table cellpadding="0" cellspacing="0" border="0" width=650>
				
			<tr>
				<td width=250>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td width='50' valign="top"><img src="${base}/images/welcome/olap1.png" width="215"
							height="96"    />
				</td>
				<td width='50'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td width='100%' style="line-height:150%;font-family:微软雅黑">&nbsp;
在全国环境与健康试点调查数据中存在着大量多维数据，传统的关系型数据体系分析不能满足涉及此类多维数据的分析要求，OLAP分析通过对信息的多种可能的观察形式进行快速、稳定一致和交互性的存取来实现多维数据的分析和归纳，并且允许允许高级分析人员对数据尤其是复杂数据或特定的数据集合进行深入观察。

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
				<td width='50' valign="top"><img src="${base}/images/welcome/olap2.png" width="215"
							height="96"    />
				</td>
				<td width='50'>&nbsp;
				</td>
				<td width='100%' style="line-height:150%;font-family:微软雅黑">本单元专门设计用于支持复杂的分析操作，侧重对高级分析人员、决策人员和高层管理人员的决策支持，可以根据分析人员的要求快速、灵活地进行大数据量的复杂查询处理，并且以一种直观而易懂的形式将查询结果提供给决策人员，以便他们准确掌握全国环境与健康试点调查数据仓库的情况，并形成分析报告。本单元所有分析均可以保存为定制分析。
				</td>
			</tr>
	  </table>
	</div>
</div>
		
	</body> 
</html>

