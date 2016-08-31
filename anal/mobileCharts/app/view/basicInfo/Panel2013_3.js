/**
 * 试点基本情况2012年的第一页
 * 
 */
Ext.define("Sencha.view.basicInfo.Panel2013_3", {
	extend : 'Ext.Panel',
    xtype : 'panel2013_3',
	config : {
//		padding:'0 0 0 110',
		yearType:2013,
		items:[{
			cls:'map_l',
			html:
			      '<div class="top_h1">2013年<strong>&ldquo;专项调查&rdquo;预调查点位信息</strong></div><div class="l_con"><table width="100%"border="0"cellspacing="0"><tr bgcolor="#fab27b"><th colspan="4">重金属污染</td></tr><tr bgcolor="#ccc"><th>省份</td><th>调查点</td><th>监测指标</td><th>行业类型</td></tr><tr><td rowspan="2"class="l_contd01">云南</td><td class="l_contd01">澄江东溪哨片磷化工园区周边地区</td><td class="l_contd01">砷、镉、锌、总磷、氟化物</td><td class="l_contd02">非金属矿采选业</td></tr><tr><td class="l_contd01">新平县大红山片区</td><td class="l_contd01">铜、铅、锌、镉</td><td rowspan="4"class="l_contd02">有色金属采选、冶炼及压延加工业</td></tr><tr><td rowspan="2"class="l_contd01">陕西</td><td class="l_contd01">凤翔东岭冶炼有限公司周边地区</td><td class="l_contd01">铅、镉、总汞</td></tr><tr><td class="l_contd01">潼关县金矿开采冶炼区</td><td class="l_contd01">砷、铅、总汞</td></tr><tr><td class="l_contd01">甘肃</td><td class="l_contd01">白银有色集团公司周边地区</td><td class="l_contd01">铅、镉、总铬、六价铬、总汞、砷</td></tr></table><p><strong>调查时间：</strong>&ldquo;专项调查&rdquo;预调查工作自2013年5月开始，至2013年12月结束</p></div><div class="clear"></div>'
			     
		}]
	}
});