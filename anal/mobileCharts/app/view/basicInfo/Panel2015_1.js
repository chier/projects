/**
 * 试点基本情况2012年的第一页
 * 
 */
Ext.define("Sencha.view.basicInfo.Panel2015_1", {
	extend : 'Ext.Panel',
    xtype : 'panel2015_1',
	config : {
		yearType:2015,
		items:[{
			cls:'map_2012',
			html:
			      '<h1 style="font-size: 38px;font-family: \'微软雅黑\';">2015年试点调查地区污染及行业类型</h1>'+
			      '<div class="fenl">'+
		        	'<ul style="list-style-type:none;">'+
		            	'<li style="list-style-type:none;"><div class="li_yuan01"></div>重金属污染</li>'+
		                '<li style="list-style-type:none;"><div class="li_yuan02"></div>有机物污染</li>'+
		                '<li style="list-style-type:none;"><div class="li_yuan03"></div>复合污染</li>'+
		            '</ul>'+
		        '</div>'+
		        
//		        '<h1 class=mapl_green style="margin:293px 0px 0px 622px;font-size:12px;"><a href="#">山西 </a></h1>'+
//		        '<h1 class=mapl_orange style="margin: 446px 0px 0px 530px;font-size:12px;"><a href="#">云南 </a></h1>'+
//		        '<h1 class=mapl_orange style="margin: 453px 0px 0px 774px;font-size:12px;"><a href="#">江西</a></h1>'+
//		        '<h1 class=mapl_green style="margin: 399px 0px 0px 751px;font-size:12px;"><a href="#">浙江 </a></h1>'+
//		        '<h1 class=mapl_blue style="margin: 501px 0px 0px 663px;font-size:12px;"><a href="#">广东 </a></h1>'+
//		        '<h1 class=mapl_blue style="margin: 348px 0px 0px 746px;font-size:12px;"><a href="#">江苏 </a></h1>'+
//		        '<h1 class=mapl_blue style="margin: 268px 0px 0px 707px;font-size:12px;"><a href="#">山东 </a></h1>'+
//		        '<h1 class=mapl_orange style="margin: 475px 0px 0px 581px;font-size:12px;"><a href="#">广西 </a></h1>'+
		        '<div class="clear"></div>'+
		        '<div class="map_con">'+
		        	'<p>综合考虑污染类型、特征污染物、调查点的区域代表性、已有工作基础及可行性，2014年选择重金属、有机污染、复合污染三种污染类型在8个试点地区开展调查</p>'+
		        '</div>'
			     
		}]
	}
});