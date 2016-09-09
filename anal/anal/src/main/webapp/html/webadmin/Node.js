//xieye注：这个方法必须按照顺序加节点！！
//============================================
function Node(name){
	this.name = name ;        //节点在树上的显示
  this.id;
  
  this.parent;       //节点的父节点
	this.previous;     //节点的前一个节点
	this.next;         //节点的下一个节点
  this.child = [];   //节点的子节点

  this.level = 0;	   //节点的级别，根节点为0
	this.tree;         //从节点获得树对象
	this.xuhao = 0;    //节点在树中的序号，根节点为0；
	this.displaychild = false; //子节点正在显示吗？表示当前状态
	
	this.checked = 0;  //0:不中，1：选中，2：部分
	this.zhankaiguo = false;  //表示历史记录，一旦变true，无法改变
}

//显示节点自身
Node.prototype.toString = function(){
	var s = '[' + this.id + ']=[' + this.name + ']';
	return s;
};

//决定点击节点的反应
Node.prototype.action = function (){
	var s = this.toString();
	//s = "javascript:alert('" + s +"');void(0)";
	s = "javascript:void(0)";
	return s;
}

/*
<div>
<img src="images/Lplus.png"  align="absbottom"  />
<img src="images/foldericon.png"  align="absbottom"  style="margin-left:-6px;"  />
<a href="javascript:void(0);">玄武分局</a>
</div>
*/
//xieye20070122：这里的逻辑是最复杂的。这是xieyetree中最重要的方法，插入行
//样式见上
Node.prototype.innerhtml = function (){
  var div = document.createElement('div');
  var qj = this.tree.objectname; //得到全局对象的名称
  //给节点的div添加id，＝树名＋序号；
  div.id = qj + this.xuhao; //做标记
  div.className = 'qiguai'; //做标记,奇怪
  var current = this;
  //xieye(1)：用for循环绘制前面的空格或竖线的图标
  //如：2级节点循环一次。3级节点，循环两次
	var oFragment = document.createDocumentFragment();
  for(var i = this.level - 1; i > 0; i--){
	  var img = document.createElement('img');
	  var qq = this.getparent(i);
	  img.src = this.getimgkongge(qq);
	  img.align = "absbottom";
	  oFragment.appendChild(img);
  }
//  div.appendChild(oFragment);
  
  //xieye(2)：绘制加号和减号或T或L的图标，有单击事件，复杂
  var img = document.createElement('img');
  img.src = this.getimgjiahao();
  img.id = qj + 'imgjiahao' + this.xuhao; //做标记
  img.align = "absbottom";
  img.onclick = function(){ //恍然大悟：这是写死的，用随机函数测试过
  	//xieye：状态在全局变量节点中，
  	//xieye：这里要判断，如果本节点无子节点
  	var fu = this.parentNode;
  	var childnodes = current.child;
  	
  	if(childnodes.length == 0){/*alert('无子节点');*/return;}
		
  	var shimowei = current.next?false:true;
		
		if(!current.displaychild){ //如果节点的子节点未打开
	  	current.displaychild = true;
	  	current.clickbytopimg();
	  	this.src = current.getimgdianji(current.displaychild,shimowei);
	  	//现在已经知道序号，要求他的父节点添加子节点
	  	this.nextSibling.nextSibling.src = TreeConfig.openFolderIcon;
			
			if(!current.zhankaiguo){
		  	//这里新加行	
				//alert('新加行');
				current.zhankaiguo = true;
		  	//加变减，减变加，但要考虑是不是末节点
		  	var oFragmentChildDiv = document.createDocumentFragment();
		  	for(var i=0;i<childnodes.length;i++)
			  	oFragmentChildDiv.appendChild(childnodes[i].innerhtml());
			  fu.appendChild(oFragmentChildDiv);	
		  }else{
		  	//这里不新加行	
		  	for(var i = fu.childNodes.length - 1; 
		  	        fu.childNodes[i].className == 'qiguai' && i >= 0; i--)
		  		fu.childNodes[i].style.display = 'block';
		  }
	  }else{
	  	current.displaychild = false;
	  	current.clickbytopimg();
	  	this.src = current.getimgdianji(current.displaychild,shimowei);
	  	this.nextSibling.nextSibling.src = TreeConfig.folderIcon;
	  	//xieye：把这个节点下面所有的div设为隐藏显示
	  	for(var i = fu.childNodes.length - 1; 
	  	        fu.childNodes[i].className == 'qiguai' && i >= 0; i--)
	  		fu.childNodes[i].style.display = 'none';
	  }
  };
  
  oFragment.appendChild(img);
//  div.appendChild(img);
//  div.appendChild(oFragment);
  
  
  //xieye(4)：绘制复选框的图标，有单击事件，复杂
  img = document.createElement('img');
  //img.src = TreeConfig.buzhongIcon;
  img.id = qj + 'imgfuxuan' + this.xuhao; //做标记
  img.src = this.getfuxuanimg();
  img.align = "top";
  img.style.paddingTop = '3px';
  img.style.marginRight = TreeConfig.jianju1;
  img.onclick = function(){
		//他的影响是双向的，
		//对于底部，全部选中
		//对于上方，要考虑是部分选中还是全部选中，并且立即更改属性并绘制
  	var fu = this.parentNode;
  //	var aa = parseInt(fu.id.substring(qj.length));
  	//alert('aa='+aa);
  	//alert(current+'hhh');
  	if(current.checked == 1 || current.checked == 2){
  	  current.checked =0;
  	  this.src = TreeConfig.buzhongIcon;
  	}else{
  	  current.checked =1;
  		this.src = TreeConfig.quanzhongIcon;
  	}
  		current.clickByDepartmentAndEmployee(current);
		current.upDigui(current);
		current.downDigui(current);
	}
  oFragment.appendChild(img);
  

  //xieye(3)：绘制文件夹或单个文件的图标
  img = document.createElement('img');
  img.src = this.getimgfold();
  img.align = "absbottom";
  img.onclick = function (){
	  this.previousSibling.onclick();
  };
  oFragment.appendChild(img);
  
  //xieye(5)：绘制文字超链接
  var a = document.createElement('a');
 // a.href = this.action();
 // a.href = 'javascript:void(0)';
  var xuhao = this.xuhao;
  a.onclick = function (){
	  //this.previousSibling.onclick();
	  
	  current.tree.divtree.clickNode(xuhao);
  };
  a.onfocus = function(){this.blur();}
  a.appendChild(document.createTextNode(this.name));
  a.style.marginLeft = TreeConfig.jianju2;
  oFragment.appendChild(a);
  
  div.appendChild(oFragment);
  return div;
}

/**
 * 组织架构与员工管理联动
 */
Node.prototype.clickByDepartmentAndEmployee  = function (node){
	if(this.name == "组织架构管理" || this.name== "员工管理"){
		var child = this.parent.child;
		for(var i=0,l=child.length;i<l;i++){
			if(child[i].name == "组织架构管理" || child[i].name== "员工管理"){
				child[i].getHtmlElementfuxuanimg().src = 
	   				this.getimgbyyangshi(node.checked);
				child[i].checked = node.checked;
			}
		}
	}
};
	//制定逻辑：用户如果收缩后点击加号，则以前的记忆废除，并记录一个
	//所以用户点击加号，则刷新，
	//用户点击减号，则保留最少一个
Node.prototype.clickbytopimg = function (){
  if(!this.tree.clickbyhand)return;
  if(this.level != 1)return;

 	var arr = this.parent.child;
 //	var qj = window[this.tree.objectname];
  var qj = this.tree.divtree;
  if(this.displaychild){ //如果点击加号,全部引进
  	qj.yijiarr = [];
  	for(var i = 0; i < arr.length; i++)
  	  if(arr[i].displaychild)
  	    qj.yijiarr.push(arr[i]);
  }else{
  	if(qj.yijiarr.length <=1 )return;
  	qj.yijiarr = [];
  	for(var i = 0; i < arr.length; i++)
  	  if(arr[i].displaychild)
  	    qj.yijiarr.push(arr[i]);
  }
}



//通过全局节点得到div对象
Node.prototype.getHtmlElement = function (){
  var dd = this.tree.objectname + this.xuhao;
  return document.getElementById(dd); //这是获得行的div
}

//通过全局节点得到复选框img对象
Node.prototype.getHtmlElementfuxuanimg = function (){
  var dd = this.tree.objectname + 'imgfuxuan' + this.xuhao;
  return document.getElementById(dd); //这是获得行的div
}

//通过全局节点得到加号img对象
Node.prototype.getHtmlElementjiahaoimg = function (){
  var dd = this.tree.objectname + 'imgjiahao' + this.xuhao;
  return document.getElementById(dd); //这是获得行的div
}


//确定复选框的状态，向下递归(与下面方法连用)
Node.prototype.downDigui = function (node){
	//alert('进入downdigui方法');
	//alert(node);
	var jibie = node.level;
 	if(jibie == node.tree.maxlevel) return;
 	//如果子节点不在显示并且 子节点不存在内存 就返回
 	if (!node.displaychild && !node.zhankaiguo ) return;//注意这句话！
 	for(var i = 0; i < node.child.length; i++)
 	  this.downDigui2(node.child[i]);
}

//确定复选框的状态，向下递归(与上面方法连用)
Node.prototype.downDigui2 = function (node){
	node.checked = node.parent.checked;
	node.getHtmlElementfuxuanimg().src = 
	   this.getimgbyyangshi(node.parent.checked);
 	if(node.level == node.tree.maxlevel) return;
 	
 	if (!node.displaychild  && !node.zhankaiguo ) return;//xieye：注意到这里和上面不同的地方！
 	for(var i = 0; i < node.child.length; i++)
 	  this.downDigui2(node.child[i]);
}

//确定复选框的状态，向上递归
Node.prototype.upDigui = function (node){
 	//alert('digui');
 	var jibie = node.level;
 	if(jibie == 0) return;
 	
  	var boo = true;  //先假定节点状态全部一样
  	var boo2 = false;
  	var parentyangshi = node.parent.checked;            //父节点样式，可能是0，1，2
  	var yangshi = node.checked;                         //获得当前节点的样式,记住不可能是局部，只会是0或1
  	
  	var arr = node.parent.child;
  	for(var i = 0;i<arr.length; i++){
  	  var tempyangshi = arr[i].checked;
  	  if(tempyangshi != yangshi){
  	    boo = false;
  	    break;	
  	  }	
  	}
  	//如果boo=false，说明有不同，父节点选2，看一样不一样，来决定是否递归
  	//如果boo=true,说明是相同，父节点选当前样式，看一样不一样，来决定是否递归
  	if(!boo){
  	  node.parent.checked = 2;
  	 // node.parent.getHtmlElementfuxuanimg().src = TreeConfig.jubuzhongIcon;
  	}else{
  		node.parent.checked = yangshi;
  	}
  	
  	node.parent.getHtmlElementfuxuanimg().src = 
  	  this.getimgbyyangshi(node.parent.checked); //这是改html样式
  	//node.parent.chuliguo = true;  
  	if(node.parent.checked != parentyangshi)	//判断是否递归
  	  this.upDigui(node.parent);
}

//根据样式返回图片（复选框）
Node.prototype.getimgbyyangshi = function (checked){
	if(checked == 0)return TreeConfig.buzhongIcon;
	if(checked == 1)return TreeConfig.quanzhongIcon;
	if(checked == 2)return TreeConfig.jubuzhongIcon;
}

//返回父节点（与下个方法连用）
Node.prototype.getparent = function (i){
	return this.getparentdigui(i,this);
}

//返回父节点（与上个方法连用）
Node.prototype.getparentdigui = function (i,node){
	var node = node.parent;
	i--;
	if (i == 0)
		return node;
	return this.getparentdigui(i,node);
}

//返回复选框图片
Node.prototype.getfuxuanimg = function (){
	if(this.parent.checked == 2 )
	  return this.getimgbyyangshi(this.checked);
	var s;
	if(this.parent.checked == 1){
		this.checked = 1;
		s = TreeConfig.quanzhongIcon;
	}else{
		this.checked = 0;
		s = TreeConfig.buzhongIcon;
	}
	return s;
}

//根据父节点返回竖线还是空格，是一行div中最前面的图片
Node.prototype.getimgkongge = function (node){
	//(1)父节点不是末尾
	//(2)父节点是末尾
	var s;
	if (node.next)   //(1)父节点不是末尾
	  s = TreeConfig.iIcon;
	else                      //(2)父节点是末尾
		s = TreeConfig.blankIcon;
	return s;
}

//返回加号位置的图片，不过是点击后判断
Node.prototype.getimgdianji = function (img1,mowei){
	//xieye：
	var s;
	if (mowei)                      //在末尾
	  if (img1)                     //变为加
	  	s = TreeConfig.lMinusIcon;
	  else                          //变为减    
	  	s = TreeConfig.lPlusIcon;  
	else                            //不在末尾
	  if (img1)                     //变为加
	  	s = TreeConfig.tMinusIcon;
	  else                          //变为减    
	  	s = TreeConfig.tPlusIcon;
	return s;
}


//返回文件夹图片
Node.prototype.getimgfold = function (){
	//xieye：有3种，开文件夹，闭文件夹，一张纸
	var s;
	if (this.child.length > 0) //有子节点
	  if (this.displaychild)//子节点在显示
	  	s = TreeConfig.openFolderIcon;
	  else                  //子节点不在显示 
	  	s = TreeConfig.folderIcon;
	else                       //无子节点
		s = TreeConfig.fileIcon;  
	return s;
}

//返回加号位置的图片
Node.prototype.getimgjiahao = function (){
	//xieye：有4种，
	//(1)有子节点，不是末尾
	//(2)有子节点，是末尾
	//(3)无子节点，不是末尾
	//(4)无子节点，是末尾
	var s;
	if (this.child.length > 0){ //有子节点
		if(this.next)  //不是末尾
		  s = TreeConfig.tPlusIcon;
		else           //是末尾   
		  s = TreeConfig.lPlusIcon;
	}else{                      //无子节点
		if(this.next)  //不是末尾
		  s = TreeConfig.tIcon;
		else           //是末尾   
		  s = TreeConfig.lIcon;
	}
	return s;
}



Node.prototype.add = function (node){
	//这是子节点的添加方法,分(1)(2)(3)(4)(5)
	
	//(1)设置自身的： child子节点，
	var chang = this.child.length;
	this.child[chang] = node;
	
	//(2)要设置node的：parent，level，tree，不设置下一个节点，子节点
	node.parent = this;
	node.level = this.level + 1;
	node.tree = this.tree;
	
	//(3)设置node的前一个节点的 ：next,     node的previous，
	if(chang > 0){
	  node.previous = this.child[chang - 1];
	  node.previous.next = node;
	} 
	
	//(4)设置树的treeArray[],     node的xuhao，
	chang =  node.tree.treeArray.length;
	//alert(chang);
	node.tree.treeArray[chang] = node;
	//alert('进入初始化方法');
	node.xuhao = chang;
	
	//(5)设置树的maxlevel;
	if(node.level > this.tree.maxlevel)
	  this.tree.maxlevel = node.level;
	
};

	//这个节点的方法可以返回 所有子节点的个数，注：
Node.prototype.getAllChildsNum = function (){
	var num = this.xuhao;
	var j = 0;
	var level = this.level;
	num++;
	while(num < this.tree.nodeArray.length){
	  if	(this.tree.treeArray[num++].level <= level  )
	    break;
	  j++;
	}
	return j;
}

//这个方法显示树用，是被调用的方法
Node.prototype.display = function (){
	for (var i = 0; i <this.level; i++)
		this.tree.tempString += ' ';
	this.tree.tempString += '[' + this.id + ']=[' + this.name + ']' + '\n';
	for (var i = 0; i < this.child.length; i++)
		this.child[i].display();
};


