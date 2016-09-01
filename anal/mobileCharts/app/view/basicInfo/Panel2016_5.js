/**
 * 试点基本情况2012年的第一页
 * 
 */
Ext.define("Sencha.view.basicInfo.Panel2016_5", {
	extend : 'Ext.Panel',
    xtype : 'panel2016_5',
	config : {
//		padding:'0 0 0 110',
		yearType:2016,
		items:[{
			cls:'map_l',
			html:
			      '<div class="top_h1">2016年<strong>&ldquo;专项调查&rdquo;预调查点位信息</strong></div><div class="l_con"><table width="100%"border="0"cellspacing="0"><tr bgcolor="#81c531"><th colspan="4">复合污染</td></tr><tr bgcolor="#ccc"><th>省份</td><th>调查点</td><th>监测指标</td><th>行业类型</td></tr><tr><td class="l_contd01">广东</td><td class="l_contd01">贵屿电子固废拆解企业周边地区</td><td rowspan="2"class="l_contd01">多氯联苯、多溴联苯醚、铅、总汞、镉、总铬、六价铬</td><td rowspan="2"class="l_contd02">废弃资源和废旧材料回收加工业</td></tr><tr><td class="l_contd01">山西</td><td class="l_contd01">清远电子固废拆解企业周边地区</td></tr></table><p><strong>调查时间：</strong>&ldquo;专项调查&rdquo;预调查工作自2016年5月开始，至2016年12月结束</p></div><div class="clear"></div>'
			     
		}]
	}
});