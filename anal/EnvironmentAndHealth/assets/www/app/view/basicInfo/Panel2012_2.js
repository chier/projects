/**
 * 试点基本情况2012年的第一页
 * 
 */
Ext.define("Sencha.view.basicInfo.Panel2012_2", {
	extend : 'Ext.Panel',
    xtype : 'panel2012_2',
	config : {
//		padding:'0 0 0 110',
		yearType:2012,
		items:[{
			cls:'map_l',
			html:
			      '<div class="top_h1">2012年污染源强特征污染物一览表</div><div class="l_con"><table width="100%"border="0"cellspacing="0"><tr bgcolor="#83b819"><th colspan="4">有色金属冶炼采选</td></tr><tr bgcolor="#ccc"><th>排污类型</td><th>排放源</td><th>必测项目</td><th>收集资料或选测项目</td></tr><tr><td rowspan="3"class="l_contd01">铅污染</td><td class="l_contd01">废气</td><td rowspan="3"class="l_contd01">铅及主要伴生的毒害重金属</td><td class="l_contd02">烟尘、二氧化硫、氮氧化物、废气流量 、氯化氢、硫酸雾、汞、砷</td></tr><tr><td class="l_contd01">废水</td><td class="l_contd02">pH、化学需氧量、氨氮、石油类、挥发酚、氰化物、废水流量、汞、砷、铬</td></tr><tr><td class="l_contd01">废渣</td><td class="l_contd02">pH、挥发酚、氰化物、汞、砷、铬</td></tr><tr><td rowspan="3"class="l_contd01">镉污染</td><td class="l_contd01">废气</td><td rowspan="3"class="l_contd01">镉及主要伴生的毒害重金属</td><td class="l_contd02">烟尘、二氧化硫、氮氧化物、废气流量 、氯化氢、铬酸雾、硫酸雾、汞、砷</td></tr><tr><td class="l_contd01">废水</td><td class="l_contd02">pH、化学需氧量、氨氮、石油类、挥发酚、氰化物、废水流量、锌、铜、汞、铬、砷</td></tr><tr><td class="l_contd01">废渣</td><td class="l_contd02">pH、锌、铜、汞、铬、砷</td></tr><tr><td rowspan="3"class="l_contd01">砷污染</td><td class="l_contd01">废气</td><td rowspan="3"class="l_contd01">砷及主要伴生的毒害重金属</td><td class="l_contd02">烟尘、二氧化硫、氮氧化物、废气流量 、氯化氢、铬酸雾、硫酸雾、镉</td></tr><tr><td class="l_contd01">废水</td><td class="l_contd02">pH、悬浮物、石油类、挥发性酚、总氰化物、硫化物、磷酸盐 锌、铜、镉、铬</td></tr><tr><td class="l_contd01">废渣</td><td class="l_contd02">pH、硫化物、磷酸盐、 锌、铜、镉、铬</td></tr></table><p>注1：多环芳烃包括16种，萘、苊烯、苊、芴、菲、蒽、荧蒽、芘、屈、苯并[a]蒽、苯并[b]荧蒽、苯并[k]荧蒽、苯并[a]芘、茚并[1,2,3-cd]芘、二苯并[a,h]蒽、苯并[g,h,i]芘。注2：VOCs至少包括苯系物、氯代烃、典型恶臭物质等。注3：多溴联苯醚指五溴、六溴、七溴和八溴联苯醚。</p></div><div class="clear"></div></div>'
			     
		}]
	}
});