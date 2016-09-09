/**
 author:zhongfei 2009 04 28

 **/

;
(function($) {
    //multi table object
    $.multitable = {

        //properties
        //Data format: {column1, column2, column3}
        theadData:null,
        tableClassname:'',
        theadClassname:'',
        //Data format:
        //      [{column1:td11,column2:td12,column3:td13},
        //      {column1:td21,column2:td22,column3:td23},
        //      {column1:td31,column2:td32,column3:td33}[
        tbodyData:null,
        tbodyClassname:'',
        tdClickedClassname:'clicked',
        trHoverClassname:'',
        backable:false,
        dbClickable:false,
        backCallback:null,
        clickCallback:null,
        dbClickCallback:null,
        ROW_PROFIX:'rowid-',
        

        create:function(theadData, tbyData, opts) {
            var mtable = $.extend({}, this, opts);
            mtable.theadData = theadData;
            mtable.tbodyData = tbyData;

            var $table = $('<table border="0"/>');
            var $thead = $('<thead/>');
            var $tbody = $('<tbody/>');
            var thDataArr = null;
            var columnCount = 0;
            var three = new Array("65%", "25%", "10%"); 
            var four = new Array("50%", "15%", "25%", "10%"); 

            if (mtable.tableClassname != null && mtable.tableClassname != '') {
                $table.addClass(mtable.tableClassname);
            }

            if (null != mtable.theadData) {
                //console.log("got a thead");
                thDataArr = mtable.theadData;
                var $tmptr = $('<tr/>');
                for (var i = 0; i < thDataArr.length; i++) {
                    var $tmptd = '';
                    if (thDataArr.length == 3) {
                    	$tmptd = $('<td align="center" width="' + three[i] + '"/>');
                    } else if (thDataArr.length == 4){
                    	$tmptd = $('<td align="center" width="' + four[i] + '"/>');
                    } else {
                    	$tmptd = $('<td align="center" />');
                    }
                    
                    $tmptd.append(thDataArr[i]);
                    $tmptr.append($tmptd);
                }
                columnCount = thDataArr.length;

                //TODO:add class

                $thead.append($tmptr);
                $thead.appendTo($table);
                //console.log("thead:" + $table.html());
            }

            var tbodyData = mtable.tbodyData;
            if (null == tbodyData) {
                //console.log("got a naked tbody");
                return mtable;
            }

            var tdData = tbodyData.data;
            if (mtable.backable) {
                var $backtr = $('<tr/>');
                for (var col = 0; col < thDataArr.length; col++) {
                    $backtr.append($('<td>back</td>'));
                }
                if (mtable.backCallback != null) {
                    $backtr.dblclick(mtable.backCallback);
                }
                $tbody.append($backtr);
            }
            if (tdData.length > 0) {
	            for (var row = 0; row < tdData.length; row++) {
	                //create a row
	                var $tmptr1 = $('<tr/>');
	                $tmptr1.attr('id', mtable.ROW_PROFIX + $(tdData[row]).attr('id'));
	                for (var col1 = 0; col1 < thDataArr.length; col1++) {
	                    //create a cell
	                	var $tmptd1;
	                	if (thDataArr[col1] == '创建时间' || thDataArr[col1] == '上传时间') {
	                		$tmptd1 = $('<td align=\"center\" style="padding-left:2px"/>');
	                	} else if (thDataArr[col1] == '大小（KB）') {
	                		$tmptd1 = $('<td align=\"right\" style="padding-left:2px;padding-right:2px"/>');
	                	} else {
	                		$tmptd1 = $('<td style="padding-left:2px"/>');
	                	}
	                    $tmptd1.append($(tdData[row]).attr(thDataArr[col1]));
	                    $tmptr1.append($tmptd1);
	                }
	                //change class when click tbody->tr
	                if (mtable.clickCallback != null) {
	                    $tmptr1.click(mtable.clickCallback);
	                }
	
	                //bind dblclick to tbody->tr
	                if (mtable.dbClickable && mtable.dbClickCallback != null) {
	                    $tmptr1.dblclick(mtable.dbClickCallback);
	                }
	
	                //hover class
	                if (mtable.trHoverClassname != null && mtable.trHoverClassname != '') {
	                    $tmptr1.hover(
	                            function() {
	                                $(this).addClass(mtable.trHoverClassname);
	                            },
	                            function() {
	                                $(this).removeClass(mtable.trHoverClassname);
	                            })
	                }
	
	                $tbody.append($tmptr1);
	            }
            } else {
            	if (theadData.length == 4) {
            		 var $tmptr = $('<tr/>');
                     var $tmptd = $('<td colspan="4" align="center"/>');
                     $tmptd.append('<font color="blue">暂无文件。</font>');
                     $tmptr.append($tmptd);
                     $tbody.append($tmptr);
            	} else if (theadData.length == 3) {
            		var $tmptr = $('<tr/>');
                    var $tmptd = $('<td colspan="3" align="center"/>');
                    $tmptd.append('<font color="blue">暂无文件夹。</font>');
                    $tmptr.append($tmptd);
                    $tbody.append($tmptr);
            	}
            }

            $tbody.find('tr').click(function() {
                if ($(this).hasClass(mtable.tdClickedClassname)) {
                    $(this).removeClass(mtable.tdClickedClassname);
                } else {
                    $(this).siblings().removeClass(mtable.tdClickedClassname);
                    $(this).addClass(mtable.tdClickedClassname);
                }
            });

            $tbody.appendTo($table);
            //console.log($table.html());
            return $table;
        }
    }

    $.fn.multitable = function(theadData, tbodyData, opts) {
        var $multitable = $.multitable.create(theadData, tbodyData, opts);
        //console.log("rs:" + $multitable.html());
        $(this).html($multitable);
    }
})(jQuery);