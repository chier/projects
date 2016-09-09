// iframe Height
function iframeHeight(){
	$(".main-iframe td").height($("body").height() - 108);
	$("#close-nav").click(function(){
		$("#left").parent().toggle();
	})	
}
// Tree menu
function treeMenu(){
  $(".tree-nav  li em").toggle(
  function(){
	  $(this).addClass("close");
	  $(this).next().next().show();
  },
  function(){
	  $(this).removeClass("close");
	  $(this).next().next().hide();
  });	
}
// tables tr bg
function evenBg(){
	$(".tables tr:even").css({background:"#e8e9ed"});
	
	$(".tables tr").each(function(){
		// 点击事件
		var _b = $(this).css("background");
		/*
		$(this).click(function(){
			if("rgb(255, 153, 0)" == $(this).css("background")){
				$(this).css({background:_b});
			}else{
				$(this).css({background:"rgb(255,153,0)"});
			}
		}).mouseover(function(){
			if("rgb(255, 153, 0)" != $(this).css("background")){
				$(this).css({background:"#ffe582"});
			}
		}).mouseout(function(){
			if("rgb(255, 153, 0)" != $(this).css("background")){
				$(this).css({background:_b});
			}
             //$('#div1').css('display','block').attr('innerHTML','鼠标离开了!'); 
        });
		*/
		$(this).mouseover(function(){
			if("rgb(255, 153, 0)" != $(this).css("background")){
				$(this).css({background:" rgb(229, 236, 247)"});
			}
		}).mouseout(function(){
			if("rgb(255, 153, 0)" != $(this).css("background")){
				$(this).css({background:_b});
			}
             //$('#div1').css('display','block').attr('innerHTML','鼠标离开了!'); 
        });
	});
}

$(function(){
	iframeHeight();
	treeMenu();
	evenBg();
});

/**
 * 鼠标放上去，变成小手状态
 */
function changeHand(id){
	$("#"+id).css("cursor", "hand");
}