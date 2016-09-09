if(typeof Efetion=="undefined") var Efetion={};
if(!Efetion.Loc) Efetion.Loc=[];
//cerate by oujj
//=======================initialize=====2009-3-6 15:45=============//
//default version for Efetion
Efetion.version=1.0;

Efetion.lang='zh-CN';

Efetion.models=new Object();

Efetion.Companyinfo=new Object();



//模块入口
$(document).ready(function() {
    //显示时间
    setInterval('writeTime()',1000);
    $("#tabs").tabs();
   
});

// tables tr bg
function evenBg(){
	$(".tables tr:even").css({background:"#e8e9ed"})
}

  //显示日期时间
  var writeTime = function ()
    		{
			//--------------- LOCALIZEABLE GLOBALS ---------------
			var d=new Date();
			var monthname=new Array("1","2","3","4","5","6","7","8","9","10","11","12");
			var dayname=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
			var hours = d.getHours()+'';
			var minute = d.getMinutes()+'';
			var seconds = d.getSeconds()+'';
			if(hours.length<=1){
				hours='0' + hours;
			}
			if(minute.length<=1){
				minute='0' + minute;
			}
			if(seconds.length<=1){
				seconds='0' + seconds;
			}
			var TODAY = d.getFullYear() + "年" + monthname[d.getMonth()] + "月" + d.getDate() + "日" + "&nbsp;&nbsp;" + dayname[d.getDay()]+hours+":"+minute+":"+seconds;
			//---------------   END LOCALIZEABLE   ---------------
			$("#nowTime").html(TODAY);
			}

//加载PORTLET数据开始
 var showPortletData = function(settings,elmName) {
	    settings = jQuery.extend({
		tableName: "公司新闻",   // 表格名称 、portlet名称
		className: "toggler",   //表格样式
		showHead:false,
		conType:0,
		moreUrl:'url',          //显示更多URL 
		detailUrl:'durl',       //显示详细URL
		tabcolName:[{name:'col1',width:'20%'},{name:'col1',width:'40%'},{name:'col1',width:'40%'}], //显示列
        tabData:[['a'],['b']] //PORTLET数据
	    }, settings);
	  

	var lnd=settings.tabData.length;
	for(var i=0;i<lnd;i++){
		var url=settings.detailUrl+'?contentId='+settings.tabData[i][0];
		$('#'+elmName).find('tr:last').after('<tr onclick="showDetail(\''+url+'\',\''+settings.tableName+'\')"></tr>');
		for(var j=1;j<settings.tabData[i].length;j++){
			$('#'+elmName).find('tr:last').append('<td width=\"'+settings.tabcolName[j-1].width+'\">'+settings.tabData[i][j]+'</td>');
			}
		} 
	
	$('#'+elmName).find('tr:last').after('<tr><td colspan="'+settings.tabcolName.length+'" align="right"><a href="javascript:void(0)" >more</a></td></tr>');
	$('#'+elmName).find('a:last').click(function(){showMore(settings);});
}


//加载更多数据开始
 var showMoretData = function(settings) {
	    settings = jQuery.extend({
		tableName: "公司新闻",   // 表格名称 、portlet名称
		className: "toggler",   //表格样式
		showHead:false,
		conType:1,
		moreUrl:'url',          //显示更多URL 
		detailUrl:'durl',       //显示详细URL
        pageInfo:{page:'1',resultCount:'1',pageSize:'3',totalPage:'1',previousPage:'1',nextPage:'1'},
        tabcolName:[{name:'col1',width:'20%'},{name:'col1',width:'40%'},{name:'col1',width:'40%'}], //显示列
        tabData:[['a'],['b']] //PORTLET数据
	    }, settings);
    $('#showmorebody').empty()
	$('#showmorebody').append('<table><tr></tr></table>');
	$('#showmorebody').find('table').addClass(settings.className);
	for(var i=0;i<settings.tabcolName.length;i++){
		$('#showmorebody').find('tr:last').append('<th width='+settings.tabcolName[i].width+'>'+settings.tabcolName[i].name+'</th>');
		}
	var lnd=settings.tabData.length;
	for(var i=0;i<lnd;i++){
		var url=settings.detailUrl+'?contentId='+settings.tabData[i][0];
		$('#showmorebody').find('tr:last').after('<tr onclick="showDetail(\''+url+'\',\''+settings.tableName+'\')"></tr>');
		for(var j=0;j<settings.tabData[i].length;j++){
			$('#showmorebody').find('tr:last').append('<td >'+settings.tabData[i][j]+'</td>');
			}
		} 
 
  $('#showmorefoot').html('<span>现在是 '+settings.pageInfo.page+' 页</span><span>共 '+settings.pageInfo.resultCount+' 条</span><span> 每页 '+settings.pageInfo.pageSize+' 条</span><span>共 '+settings.pageInfo.totalPage+' 页</span> <a href="javascript:showmorepage(\''+settings.moreUrl+'\',1,'+settings.conType+')">首页</a> <a href="javascript:showmorepage(\''+settings.moreUrl+'\','+settings.pageInfo.previousPage+','+settings.conType+')">上一页</a> <a href="javascript:showmorepage(\''+settings.moreUrl+'\','+settings.pageInfo.nextPage+','+settings.conType+')">下一页</a><a href="javascript:showmorepage(\''+settings.moreUrl+'\','+settings.pageInfo.totalPage+','+settings.conType+')">尾页</a> ');
}

var showmorepage=function(url,page,contype){
  	  $.getJSON(base+url,{page:page,contype:contype},function(obj){
  		    showMoretData(obj);
			});
	}


	




	
var showMore=function(setings){
	$("#showmore").bind('dialogclose', function(event, ui) {$('#showmorebody').empty();$(this).dialog('destroy');});
       $("#showmore").dialog({
								   bgiframe: true,
								   title:setings.tableName+'列表',
								   buttons: { "关闭": function() { $(this).dialog("close");} },
								   width:900,
								   top:10,
								   height:500,
								   minHeight: 250,
								   minWidth: 450,
								   modal: true,
								   overlay:{opacity:15,background:'red'}, 
								   closeOnEscape:true
								  });

  	  $.getJSON(base+setings.moreUrl,{page:1,contype:setings.conType},function(obj){
							
				showMoretData(obj);
			});
	  
	  
	}
	
var showDetail=function(url,showtitle){

	   $("#showdetail").bind('dialogclose', function(event, ui) {$('#showdetailbody').empty();$(this).dialog('destroy');});
       $("#showdetail").dialog({
								   bgiframe: true,
								   title:showtitle,
								   buttons: { "关闭": function() { $("#showdetail").dialog("close");} },
								   width:900,
								   top:10,
								   height:500,
								   minHeight: 250,
								   minWidth: 450,
								   modal: true,
								   overlay:{opacity:15,background:'red'}, 
								   closeOnEscape:true
								  });

  	  $.get(base+url,{},function(obj){
								
				$('#showdetailbody').html(obj); 
			});

	}
	
	
var searchclick= function (flg) {
			var query='';
		switch(flg){
			case "google":{
				switch(Efetion.lang) {
					case "fr":
						url = "http://www.google.fr/search?q=";
						break;
					case "ru":
						url = "http://www.google.ru/search?q=";
						break;
					case "es":
						url = "http://www.google.es/search?q=";
						break;
					case "pt-br":
						url = "http://www.google.pt/search?q=";
						break;
					case "zh-tw":
						url = "http://www.google.com/search?hl=zh-TW&q=";
						break;
					case "hr":
						url = "http://www.google.com/search?hl=hr&q=";
						break;
					case "hi":
						url = "http://www.google.com/search?hl=hi&q=";
						break;
					case "zh-CN" :	//==2007-1-16 14:39==new create==//
						url = "http://www.google.com/search?hl=zh-CN&q=";
						break;
					default:
						url = "http://www.google.com/search?q=";
						break;
		
				}
				query=$('#googlesearch').val();
					break;
		        }
					case "yahoo":
					    query=$('#yahoosearch').val();
						url = "http://www.yahoo.com.cn/search?ei=UTF-8&fr=fp-tab-web-ycn&p=";
						break;

				  
					case "baidu":
					    query=$('#baidusearch').val();
						url = "http://www.baidu.com/s?ie=UTF-8&cl=3&wd=";
						break;
				    default:
					    return false;
					    break;
		}
		window.open(url+encodeURIComponent(query));
		return false;
	}
	