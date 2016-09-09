<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%@ page import="java.util.List"%>
<%@ page import="com.cmcc.framework.model.system.model.ModelInfo"%>

<!--带checkbox的部门树型结构页面-->
<html>


	<head>
	    <title></title>
	    <link type="text/css" rel="stylesheet" href="xtree.css" />
		<%@ include file="/commons/meta.jsp"%>
		
		<style type="text/css">
			#testTree *{font-size:12px;white-space:nowrap;}
		</style>
		<script src="${base}/html/webadmin/Tree.js"></script>
		<script src="${base}/html/webadmin/Node.js"></script>
		<script src="${base}/html/webadmin/DivTree.js"></script>
		<script language="JavaScript">
		
       	Array.prototype.baoremove = function(dx){
		    if(isNaN(dx)||dx>this.length){
		    	return false;
		    }
		    for(var i=0,n=0;i<this.length;i++){
		        if(this[i]!=this[dx]){
		            this[n++]=this[i]
		        }
		    }
		    this.length-=1
	 	 }
		var treeXiaqu = new DivTree('${compName}');
        treeXiaqu.id = '0';				
      
		
		
		
	  function initNodes(node){
	  
	  var nodeArray = $("#testnode").val();
	  var arr = nodeArray.split("|");
	  
       		for(var i = 0 ; i < arr.length ; i ++){
       		   var jsonNodeObj  =  eval("(" + arr[i] + ")");
       				if(jsonNodeObj.parentId == node.id){
	       				var tmp = new Node(jsonNodeObj.name) ;
	       				tmp.id = jsonNodeObj.deptId;
	       				tmp.isLeaf= jsonNodeObj.isLeaf;
	       				tmp.parentId= jsonNodeObj.parentId;
	       				tmp.state= jsonNodeObj.state;
	       				jsonNodeObj=null;
	       				node.add(tmp);
	       				initNodes(tmp);
       				}       			
       		 }
       	}
		
		
		
        function init(){
        	
        	initNodes(treeXiaqu);
        	document.getElementById("testTree").appendChild(window.treeXiaqu.div)
			treeXiaqu.init();
        }
        
        window.onload = init;
        
        function test2(){
		   var a =treeXiaqu.getNodesAll();
		   if(a.length>0) alert(a.toString());
		  else alert('请选中复选框'); 
		}

		function test3(){
		   var a =treeXiaqu.getNodesMoji();
		   var arr = new Array();
		   for(var i = 0 ; i < a.length; i ++){
		   	if(a[i].parent.checked == 2){
		   		arr.push(a[i].parent);
		   	} 
		   }
		   for(var i = 0 ; i < arr.length ;i++){
		   	for(var j = 0 ; j < arr.length ; j++){
		   		if(arr[j].id == arr[i].id){
		   			arr.baoremove(j);
		   		}
		   	}
		   }
		   alert(arr);
		   if(a.length>0) alert(a.toString());
		  else alert('请选中复选框'); 
		}
		function test(){
		   var a =treeXiaqu.getNodes();
		   if(a.length>0) alert(a.toString());
		  else alert('请选中复选框'); 
		}
		function testzhong(){
			 for(i = 0 ; i < treeXiaqu.tree.treeArray.length ; i++){
			 	if(treeXiaqu.tree.treeArray[i].id == '32'){
			 		treeXiaqu.tree.treeArray[i].checked =1;
			 		treeXiaqu.tree.treeArray[i].getHtmlElementfuxuanimg().src = TreeConfig.quanzhongIcon;
			 	}
			 }
		}
		function displaybutton(){
			parent.textIsNull();
		}
	</script> 
</head>
<body onclick="displaybutton();">

	<div id="testTree" name ="testTreename"><input id = "testnode" name="testnode" type="hidden" value="${requestScope.jsonObj}"/></div> 
</body>
	
</html>