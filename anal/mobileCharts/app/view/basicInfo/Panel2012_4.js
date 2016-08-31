/**
 * 试点基本情况2012年的第一页
 * 
 */
Ext.define("Sencha.view.basicInfo.Panel2012_4", {
	extend : 'Ext.Panel',
    xtype : 'panel2012_4',
	config : {
//		padding:'0 0 0 110',
		yearType:2012,
		items:[{
			cls:'map_l',
			html:
			      '<div class="top_h1">2012年污染源强特征污染物一览表</div><div class="l_con"><table width="100%"border="0"cellspacing="0"><tr bgcolor="#05435f"><th colspan="4">再生资源回收利用</td></tr><tr bgcolor="#ccc"><th>排污类型</td><th>排放源</td><th>必测项目</td><th>收集资料或选测项目</td></tr><tr><td rowspan="3"class="l_contd01">电子垃圾拆解污染</td><td class="l_contd01">废弃</td><td rowspan="3"class="l_contd01">多氯联苯、多溴联苯醚<strong>（注3）</strong>、多环芳烃、铅、汞、镉、铬</td><td class="l_contd02">粉尘、二氧化硫、氮氧化物</td></tr><tr><td class="l_contd01">废水</td><td class="l_contd02">pH、色度、悬浮物、化学需氧量、氨氮及废水流量、酞酸酯、镍、铜、锌</td></tr><tr><td class="l_contd01">废渣</td><td class="l_contd02">酞酸酯、镍、铜、锌</td></tr></table><p>注1：多环芳烃包括16种，萘、苊烯、苊、芴、菲、蒽、荧蒽、芘、屈、苯并[a]蒽、苯并[b]荧蒽、苯并[k]荧蒽、苯并[a]芘、茚并[1,2,3-cd]芘、二苯并[a,h]蒽、苯并[g,h,i]芘。注2：VOCs至少包括苯系物、氯代烃、典型恶臭物质等。注3：多溴联苯醚指五溴、六溴、七溴和八溴联苯醚。</p></div><div class="clear"></div></div>'
			     
		}]
	}
});