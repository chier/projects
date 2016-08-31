/**
 * 试点基本情况2012年的第一页
 * 
 */
Ext.define("Sencha.view.basicInfo.Panel2013_2", {
	extend : 'Ext.Panel',
    xtype : 'panel2013_2',
	config : {
//		padding:'0 0 0 110',
		yearType:2013,
		items:[{
			cls:'map_l',
			html:
			      '<div class="top_h1">2013年<strong>&ldquo;专项调查&rdquo;预调查点位信息</strong></div><div class="l_con"><table width="100%"border="0"cellspacing="0"><tr bgcolor="#fab27b"><th colspan="4">重金属污染</td></tr><tr bgcolor="#ccc"><th>省份</td><th>调查点</td><th>监测指标</td><th>行业类型</td></tr><tr><td class="l_contd01">辽宁</td><td class="l_contd01">锦州中信锦州金属股份有限公司周边地区</td><td class="l_contd01">总铬、六价铬、锰、镉</td><td class="l_contd02">黑色金属采选、冶炼及压延加工业</td></tr><tr><td class="l_contd01">浙江</td><td class="l_contd01">永嘉万垟头电镀企业周边区域</td><td class="l_contd01">总铬、六价铬、镉</td><td class="l_contd02">金属表面处理及热处理加工</td></tr><tr><td class="l_contd01">福建</td><td class="l_contd01">晋江安海镇皮革鞣制企业周边地区</td><td class="l_contd01">总铬、六价铬</td><td class="l_contd02">皮革、毛皮、羽毛（绒）及其制品业</td></tr><tr><td rowspan="3"class="l_contd01">江西</td><td class="l_contd01">崇义县钨钼矿开采周边区域</td><td class="l_contd01">砷、铅、镉</td><td rowspan="3"class="l_contd02">有色金属采选、冶炼及压延加工业</td></tr><tr><td class="l_contd01">南康市土法炼金区</td><td class="l_contd01">镉、铅、总铬、六价铬</td></tr><tr><td class="l_contd01">定南县稀土开采冶炼区</td><td class="l_contd01">铅、砷、总铬、六价铬、锰</td></tr><tr><td class="l_contd01">湖北</td><td class="l_contd01">大冶铜铁矿冶炼公司周边地区</td><td class="l_contd01">镉、铅、总铬、六价铬</td><td rowspan="2"class="l_contd02">黑色金属采选、冶炼及压延加工业</td></tr><tr><td class="l_contd01">湖南</td><td class="l_contd01">花垣锰三角企业群周边地区</td><td class="l_contd01">锰、镉</td></tr><tr><td class="l_contd01">广西</td><td class="l_contd01">南丹有色金属采选冶炼园区周边地区</td><td class="l_contd01">铅、镉、总汞、砷</td><td rowspan="3"class="l_contd02">有色金属采选、冶炼及压延加工业</td></tr><tr><td rowspan="2"class="l_contd01">贵州</td><td class="l_contd01">万山汞矿周边地区</td><td class="l_contd01">总汞、甲基汞</td></tr><tr><td class="l_contd01">安龙金矿开采冶炼区</td><td class="l_contd01">砷、铅、总汞</td></tr></table><p><strong>调查时间：</strong>&ldquo;专项调查&rdquo;预调查工作自2013年5月开始，至2013年12月结束</p></div><div class="clear"></div>'
			     
		}]
	}
});