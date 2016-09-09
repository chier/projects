<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>定制分析</title>
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
			<table cellpadding="0" cellspacing="0" border="0" style="width:650;">
				<tr>
					<td width='10'>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td align="right" valign="middle">
						<img src="${base}/images/welcome/arra.png" width="70" height="70" />
					</td>
					<td valign="middle" align="left">
						<h1>
							&nbsp;&nbsp;欢迎定制分析功能
						</h1>
						<br />
						<h4>
							&nbsp;&nbsp;&nbsp;&nbsp;点击左侧数据表，开始功能使用。
						</h4>
					</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" border="0" width=650>
				<tr>
					<td width=250>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td width='50' valign="top">
						<img src="${base}/images/welcome/custom1.png" width="215"
							height="96" />
					</td>
					<td width='50'>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td width='100%' style="line-height:150%;font-family:微软雅黑">
						&nbsp;在日常繁琐的数据分析事务中有许多是重复性的分析工作，此类工作使用普通分析手段会占用分析人员大量时间，同时也会增加系统开销降低执行效能。这些多次重复使用的分析手段可以定制成为分析模板，首次分析时按照一定的约束条件进行模板设计，如查询条件、规则、需求或特定数据集合等，在第二次分析时即可调用模板直接完成分析。

					</td>
				</tr>
				<tr height=30>
					<td width='250'>
						&nbsp;
					</td>
					<td width='50'>
					</td>
					<td width='50'>
						&nbsp;
					</td>
					<td width='100%'>
						&nbsp;

					</td>
				</tr>
				<tr>
					<td width='50'>
						&nbsp;
					</td>
					<td width='50' valign="top">
						<img src="${base}/images/welcome/custom2.png" width="215"
							height="96" />
					</td>
					<td width='50'>
						&nbsp;
					</td>
					<td width='100%' style="line-height:150%;font-family:微软雅黑">
						本单元可根据分析人员需求进行定制分类并将分类存储于树形结构，模板来源可以是直接存入系统、即席查询保存模板及OLAP分析保存分析模板。定制分析的输出内容为图表和数据集合，除可在系统内展示外也可以直接调出配置或调出数组进行其他用途，还可以导出为excel或输出到报告模板进行报告编制。
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>

