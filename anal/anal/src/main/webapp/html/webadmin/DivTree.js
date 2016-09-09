var TreeConfig = {
	rootIcon        : '../../../html/department/images/foldericon.gif',
	openRootIcon    : '../../../html/department/images/openfoldericon.gif',
	folderIcon      : '../../../html/department/images/foldericon.gif',
	openFolderIcon  : '../../../html/department/images/openfoldericon.gif',
	fileIcon        : '../../../html/department/images/file.gif',
	iIcon           : '../../../html/department/images/I.gif',
	lIcon           : '../../../html/department/images/L.gif',
	lMinusIcon      : '../../../html/department/images/Lminus.gif',
	lPlusIcon       : '../../../html/department/images/Lplus.gif',
	tIcon           : '../../../html/department/images/T.gif',
	tMinusIcon      : '../../../html/department/images/Tminus.gif',
	tPlusIcon       : '../../../html/department/images/Tplus.gif',
	blankIcon       : '../../../html/department/images/blank.gif',
	buzhongIcon     : '../../../html/department/images/buzhong.gif',
	quanzhongIcon   : '../../../html/department/images/quanzhong.gif',
	jubuzhongIcon   : '../../../html/department/images/jubuzhong.gif',
	defaultText     : 'Tree Item',
	defaultAction   : 'javascript:void(0);',
	defaultBehavior : 'classic',
	usePersistence	: true,
	jianju1         : '5px',   /*这是复选框到文件夹的距离*/
	jianju3         : '5px',   /*这是头部复选框到第一图标的距离*/
	jianju2         : '5px'   /*这是复选框到文字的距离*/
};


//div树类
//=====================================================================
function DivTree(name){
	/*
	使用条件
	1：html的文档类型必须是XHTML 1.0
	2：给树添加节点的顺序必须固定，必须以深度遍历的方式初始化树，考虑到树的实际用途
	3：没有删除节点方法，考虑到树的实际用途
	4：function huifu()把树恢复到开始显示的样子
	5：
	6：树的构造方法参数有(a,b,c,d)
	   a：有复选框，无复选框
	   b：是只有一个节点展开，还是随意
	   c：是点击减号后再点击加号，他保持点击减号后的形状
	   d：
	7：树的构造方法基本参数，名称
	8：function add()  //添加一级节点
	9: function getNodes() //返回最靠前父节点
	*/
	
	var objectname = this.getName();
//	this.
	this.tree = new Tree(name, objectname);
	this.tree.divtree = this;
	
	this.div = this.creatediv();
	//this.display = true;
	//
	this.yijiarr = [];
}

DivTree.count=0;

DivTree.prototype.getName = function (){
	var s="xytreeid" ;
	s+=(window.DivTree.count++);
	
	//alert(s);
	return s;
}


DivTree.prototype.add = function (node){
  this.tree.add(node);	
}

	//制定逻辑：用户如果收缩后点击加号，则以前的记忆废除，并记录一个
	//所以用户点击加号，则刷新，
	//用户点击减号，则保留最少一个
	//
	//当用户点击顶上图标时，如果有展开，就一定收缩
	//如果收缩，查看历史记录，没有就罢
	//如果有历史记录，就展开历史记录
DivTree.prototype.clicktopimg = function (){
	var boo = false;
	var root = this.tree.root;
	var arr = root.child;
	this.tree.clickbyhand = false;
	for(var i=0;i<arr.length; i++ ){
		if(arr[i].displaychild){
		  arr[i].getHtmlElementjiahaoimg().onclick();
		  boo = true;
		}
	}
	this.tree.clickbyhand = true;
	if(boo)return;

	this.tree.clickbyhand = false;
	for(var i=0;i<this.yijiarr.length;i++)
	  this.yijiarr[i].getHtmlElementjiahaoimg().onclick();
	this.tree.clickbyhand = true;
}

//这个方法给程序转移用，清空选择
DivTree.prototype.init2 = function (){

	var root = this.tree.root;
	
	if(root.checked!=0) {//说明有选择
		root.getHtmlElementfuxuanimg().onclick();
	}
	
	var arr = root.child;
	this.tree.clickbyhand = false;
	for(var i=0;i<arr.length; i++ ){
		if(arr[i].displaychild){
		  arr[i].getHtmlElementjiahaoimg().onclick();
		}
	}
	this.tree.clickbyhand = true;
	
	this.yijiarr=[];
}

//这个方法给程序转移用，清空选择,完全复位
DivTree.prototype.init3 = function (){

}

DivTree.prototype.init = function (){
	var div = this.div.lastChild;
	//把所有的一级节点列出来
	//首先得到所有的一级节点
	var root = this.tree.root;
	//alert(root);
	var arr = root.child;
	var oFragment = document.createDocumentFragment();
	for(var i=0;i<arr.length; i++ ){
	  //this.yijiarr[arr[i].xuhao] = false;//为了根节点
	  oFragment.appendChild(arr[i].innerhtml());
	//alert(arr.length);
	}
	div.appendChild(oFragment);
}



DivTree.prototype.getNodesdigui = function (arr, node){
	//xieye：决定采用树状遍历
	//如果节点的checked = 0,那么退出
	//如果节点的checked = 1,那么记录，退出
	//如果节点的checked = 2,那么跟进去
	if(node.checked == 0)
	  /*空函数体*/ ;
	else if(node.checked == 1 && node.level != 0)
	  arr.push(node);
	else if(node.checked == 1 && node.level == 0)
		for(var i = 0; i < node.child.length ; i++)
		  this.getNodesdigui(arr, node.child[i]);
	else
		for(var i = 0; i < node.child.length ; i++)
		  this.getNodesdigui(arr, node.child[i]);
}


DivTree.prototype.getNodesdiguiAll = function (arr, node){
	//xieye：决定采用树状遍历
	//如果节点的checked = 0,那么退出
	//如果节点的checked = 1,那么记录，退出
	//如果节点的checked = 2,那么跟进去
	if(node.checked == 0)
	  /*空函数体*/ ;
	else if(node.checked == 1 ){
	  arr.push(node);
		for(var i = 0; i < node.child.length ; i++)
		  this.getNodesdiguiAllSelected(arr, node.child[i]);
	}
	else
		for(var i = 0; i < node.child.length ; i++)
		  this.getNodesdiguiAll(arr, node.child[i]);
}

DivTree.prototype.getNodesdiguiAllSelected = function (arr, node){
//	  alert('进入');
	  arr.push(node);
//alert(arr.toString());
		for(var i = 0; i < node.child.length ; i++)
		  this.getNodesdiguiAllSelected(arr, node.child[i]);
}


DivTree.prototype.getNodesdiguiMoji = function (arr, node){
	//xieye：决定采用树状遍历
	//如果节点的checked = 0,那么退出
	//如果节点的checked = 1,那么记录，退出
	//如果节点的checked = 2,那么跟进去
	if(node.checked == 0)
	  /*空函数体*/ ;
	else if(node.checked == 1 ){
	  if( node.child.length == 0)
		  arr.push(node);
		else
			for(var i = 0; i < node.child.length ; i++)
			  this.getNodesdiguiMojiSelected(arr, node.child[i]);
	}
	else
		for(var i = 0; i < node.child.length ; i++)
		  this.getNodesdiguiMoji(arr, node.child[i]);
}

DivTree.prototype.getNodesdiguiMojiSelected = function (arr, node){
//	  alert('进入');
	  if( node.child.length == 0)
	  	arr.push(node);
//alert(arr.toString());
		else
			for(var i = 0; i < node.child.length ; i++)
		  	this.getNodesdiguiMojiSelected(arr, node.child[i]);
}






DivTree.prototype.creatediv = function (){
  var div = document.createElement('div');
  div.className = 'treeyangshi';
  var divhead = document.createElement('div');
  
  var divtree = this;
  var qj = this.tree.objectname; //得到全局对象的名称
  divhead.id = qj + 'divhead';
  
  //(2)绘制复选框的图标，有单击事件，复杂
  img = document.createElement('img');
  //img.src = TreeConfig.buzhongIcon;
  img.id = qj + 'imgfuxuan0'; //做标记
  img.src = TreeConfig.buzhongIcon;
  img.align = "middle";
  img.style.marginTop= "-8px";
  img.style.marginRight= TreeConfig.jianju3;
  img.onclick = function(){
		//他的影响是双向的，
		//对于底部，全部选中
		//对于上方，要考虑是部分选中还是全部选中，并且立即更改属性并绘制
  	var current = divtree.tree.treeArray[0]; //current是全局节点
  	if(current.checked == 1 || current.checked == 2){
  	  current.checked =0;
  	  this.src = TreeConfig.buzhongIcon;
  	}else{
  	  current.checked =1;
  		this.src = TreeConfig.quanzhongIcon;
  	}
		current.downDigui(current);
  }
  divhead.appendChild(img);
  
  //（1）绘制文件夹图标
  var img = document.createElement('img');
  img.src = TreeConfig.openRootIcon;
  img.align = "absbottom";
  img.onclick = function (){
//  	window[qj].clicktopimg();

   //alert(555);	
   var divbody = this.parentNode.parentNode.lastChild;
   if(divbody.style.display=='block'){
   	divbody.style.display='none';
	  this.src = TreeConfig.rootIcon;
   }
   else{
   	divbody.style.display='block';
  	this.src = TreeConfig.openRootIcon;
   }

  }
  divhead.appendChild(img);
  
  
  //(3)绘制根节点文字链接
  var a = document.createElement('a');
 // a.href = 'javascript:void(0);';
  a.appendChild(document.createTextNode(this.tree.treename));
  a.style.marginLeft= TreeConfig.jianju2;
  a.onclick = function (){
	  this.previousSibling.onclick();
	  
	  divtree.clickRootNode();
	  
  };
  a.onfocus = function(){this.blur();}
  divhead.appendChild(a);
  div.appendChild(divhead);

  var divbody = document.createElement('div');
  divbody.style.display = 'block';
  div.appendChild(divbody);
  
  return div;
}

DivTree.prototype.isSelectAll = function (){
	return (this.tree.root.checked == 1)?true:false;
}

DivTree.prototype.getNodes = function (){
	var arr = [];
	this.getNodesdigui(arr, this.tree.root);
	return arr;
}
DivTree.prototype.getNodesAll = function (){
	var arr = [];
	this.getNodesdiguiAll(arr, this.tree.root);
	return arr;
}
DivTree.prototype.getNodesMoji = function (){
	var arr = [];
	this.getNodesdiguiMoji(arr, this.tree.root);
	return arr;
}



//新的补充：20070418：在这里可以自己写单击节点的反应
DivTree.prototype.clickNode = function (xuhao){
	var node = this.tree.treeArray[xuhao]; //这句话是固定的，勿改，目的：获得节点
//	var s = '[' + node.id + ']=[' + node.name + ']';
	//alert(s);
}

//新的补充：20070418：在这里可以自己写单击根节点文字的反应
DivTree.prototype.clickRootNode = function (){
//	var s='';
//	s+='当前树：';
//	s+='\n节点总数(不含根节点) = '+(this.tree.treeArray.length-1);
//	s+='\n层数(不含根节点的层) = '+this.tree.maxlevel;
//	s+='\n根节点名称 = '+this.tree.treeArray[0].name;
//	alert(s);
}




