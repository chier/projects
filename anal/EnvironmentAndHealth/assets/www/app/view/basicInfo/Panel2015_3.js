/**
 * 试点基本情况2015年的第一页
 * 
 */
Ext.define("Sencha.view.basicInfo.Panel2015_3", {
	extend : 'Ext.Panel',
    xtype : 'panel2015_3',
	config : {
//		padding:'0 0 0 110',
		yearType:2015,
		items:[{
			cls:'map_l',
			html:
			      '<div class="top_h1">2015年污染源强特征污染物一览表</div><div class="l_con"><table width="100%"border="0"cellspacing="0"><tr bgcolor="#ff9933"><th colspan="4">化工</td></tr><tr bgcolor="#ccc"><th>排污类型</td><th>排放源</td><th>必测项目</td><th>收集资料或选测项目</td></tr><tr><td rowspan="2"class="l_contd01">农药污染</td><td class="l_contd01">废水</td><td rowspan="2"class="l_contd01">毒死蜱</td><td class="l_contd02">pH、色度、悬浮物、化学需氧量、氨氮、三氯吡啶酚、三氯乙酰氯和丙烯腈</td></tr><tr><td class="l_contd01">废气</td><td class="l_contd02">硫醇</td></tr><tr><td rowspan="2"class="l_contd01">石油化工污染</td><td class="l_contd01">废气</td><td class="l_contd01">多环芳烃<strong>（注1）</strong>、VOCs<strong>（注2）</strong></td><td class="l_contd02">烟尘、甲硫醇、二氧化硫、氮氧化物、非甲烷总烃、沥青烟、卤代烃</td></tr><tr><td class="l_contd01">废水</td><td class="l_contd01">多环芳烃<strong>（注1）</strong>、VOCs<strong>（注2）</strong></td><td class="l_contd02">pH、化学需氧量、石油类、挥发酚、废水流量</td></tr><tr><td rowspan="2"class="l_contd01">焦化污染</td><td class="l_contd01">废气</td><td rowspan="2"class="l_contd01">多环芳烃、苯系物</td><td class="l_contd02">烟尘、二氧化硫、氮氧化物、总铅、镉、铬</td></tr><tr><td class="l_contd01">废水</td><td class="l_contd02">pH、色度、化学需氧量、氨氮、石油类、硫化物、总氰化物、铅、镉、铬</td></tr><tr><td rowspan="2"class="l_contd01">复合污染</td><td class="l_contd01">废气</td><td rowspan="2"class="l_contd01">通过预调查确定区域中不少于三类的主要特征污染物</td><td rowspan="2"class="l_contd02">参照同类行业指标</td></tr><tr><td class="l_contd01">废水</td></tr></table><p>注1：多环芳烃包括16种，萘、苊烯、苊、芴、菲、蒽、荧蒽、芘、屈、苯并[a]蒽、苯并[b]荧蒽、苯并[k]荧蒽、苯并[a]芘、茚并[1,2,3-cd]芘、二苯并[a,h]蒽、苯并[g,h,i]芘。注2：VOCs至少包括苯系物、氯代烃、典型恶臭物质等。注3：多溴联苯醚指五溴、六溴、七溴和八溴联苯醚。</p></div><div class="clear"></div></div>'
			     
		}]
	}
});