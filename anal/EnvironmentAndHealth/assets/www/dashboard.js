
var firstInit = false;
var firstInit1 = false;
var firstInit2 = false;




//************总销售额仪表盘面板*********
window.MyPanel = function(id, option) {
	 this.canvas = document.getElementById(id);
	 if(!this.canvas){
		 return;
	 }
	 this.cvs = this.canvas.getContext("2d"); //得到绘制上下文
	 this.cvs.font = "12px Courier";
	 this.width = this.canvas.width; //对象宽度
	 this.height = this.canvas.height; //对象高度
	 this.level = option.level;
	 this.outsideStyle = option.outsideStyle;
 };
MyPanel.prototype.save = function(fn) {
	if(!this.cvs){
		return;
	}
	this.cvs.save();
	fn();
	this.cvs.restore();
};

MyPanel.prototype.drawImage = function(steps) {
	var p = this;
	var cvs = this.cvs;
	p.save(function() {		
		var img = new Image();		
		img.onload = function(){
			cvs.clearRect(0, 0, p.width, p.height);
		    cvs.drawImage(img, 0, 0, 212, 212, 18, 0, 180, 180);			
			p.drawLine();	
	        p.drawSpanner(50+steps);
			

		};
		// img.src = "images/ios_biaopan_sma166.png";		biaopan_big.png
		img.src = "images/android_biaopan_big212_212.png";
	});

};
MyPanel.prototype.drawLine = function() {
	var p = this;
	var cvs = this.cvs;
	var j = 0;
	for (var i = -10; i <= 70; i=i+2) {
		p.save(function() {
			cvs.translate(p.width / 2.8, p.height/2+2);
			cvs.rotate(Math.PI / 60 * i);
			p.save(function() {
				cvs.beginPath();
				// 原 是 57  后改成 67
				cvs.lineTo(-(p.height - p.outsideStyle.lineWidth-105) + 10, 0);//设置圆形文本刻度半径大小
				if ((i+10) % 10 === 0) {
					cvs.lineTo(-(p.height - p.outsideStyle.lineWidth-105) + 1, 0);//设置圆形文本刻度半径大小
				}else{
					cvs.lineTo(-(p.height - p.outsideStyle.lineWidth-105) + 3, 0);//设置圆形文本刻度半径大小
				}						
				cvs.strokeStyle = "#FFF";						
				cvs.lineWidth = 1;
				cvs.stroke();				
				if ((i+10) % 10 === 0) {
				  //p.drawText((i+10));
				  p.drawText(parseInt((i+10+j)/4));
				}
				j = j + 8;
				
			});
		});
	}
};
MyPanel.prototype.drawText = function(val) {
	var p = this;
	var cvs = this.cvs;
	p.save(function() {
		cvs.strokeText(val, -(p.height - p.outsideStyle.lineWidth-108) + 12, 3);//设置圆形文本半径大小
	});
};
MyPanel.prototype.drawSpanner = function(value) {
	var p = this;
	var cvs = this.cvs;

	p.save(function() {
		cvs.translate(p.width / 2.8, p.height/2+2);
		cvs.rotate(Math.PI / 60 * -90);
		p.save(function() {
			cvs.rotate(Math.PI / 60 * value);
			cvs.beginPath();
			cvs.moveTo(3, -3);
			cvs.lineWidth = 2;
			cvs.lineTo(0, -p.height * 0.38);//设置指针长短 // 原来是0.45 后改成 0.4
			cvs.lineTo(-3, -3);
			cvs.lineTo(3, -3);
			cvs.fillStyle = "red";
			cvs.fill();
		});
	});
};

window.init = function(value,flag,index) {
	var steps = index;
	if(flag===0){
	   steps = 0;
	   if(typeof(handler)!="undefined"){
		  window.clearTimeout(handler);
		  return;  
		}
	}
	if(steps>100){
		steps = 0;
		window.clearTimeout(handler);
		return;
	}
	var panelOption = {
			interval: 5,
			level: [
				  {start: 0, color: "red" },
				  { start: 40, color: "yellow" },
				  { start: 60, color: "blue" },
				  { start: 100, color: "Lime" }
				  ],
			outsideStyle: { lineWidth: 4, color: "rgba(100,100,100,1)"}
	};

	var myPanel = new MyPanel("myYibiao", panelOption);
	
	
	
	var tmp = Math.round(Math.random()*90);
	var p = myPanel;
	
	var cvs = myPanel.cvs;
	//alert(steps);
	//cvs.clearRect(0, 0, myPanel.width, myPanel.height);
	p.drawImage(steps);	
	//drawPic(cvs,myPanel,value);
	steps += 1;	
	var handler = window.setTimeout('init('+value+',1,'+steps+')',10);
	if(steps==value){
		window.clearTimeout(handler);
		steps = 0;
		return;
	}
	
	if(!firstInit){
		document.getElementById("myYibiao").addEventListener('touchstart', function(event) {
			clickCanvasAll();
			document.getElementById("indexSaleTitle").innerHTML="机票+酒店";
		}, false);
		firstInit = true;
	}
};

//****************机票销售额仪表盘
    var panel = function(id, option) {
	     this.canvas = document.getElementById(id); //获取canvas元素
		 this.cvs = this.canvas.getContext("2d"); //得到绘制上下文
		 this.width = this.canvas.width; //对象宽度
		 this.height = this.canvas.height; //对象高度
		 //alert(this.width+"========"+this.height);
		 this.level = option.level;
		 this.outsideStyle = option.outsideStyle;
    };
	var panelOption = {
		maxLength: 30,
		interval: 5,
		level: [//仪表盘需要呈现的数据隔离区域
			  {start: 0, color: "red" },
			  { start: 30, color: "yellow" },
			  { start: 40, color: "blue" },
			  { start: 100, color: "Lime" }
			  ],
		outsideStyle: { lineWidth: 8, color: "rgba(100,100,100,0.5)" }
	};	
	panel.prototype.save = function(fn) {
		this.cvs.save();
		fn();
		this.cvs.restore();
	};
	
	panel.prototype.drawImage = function(steps) {
	var p = this;
	var cvs = this.cvs;
	p.save(function() {		
		var img = new Image();		
		img.onload = function(){
			cvs.clearRect(0, 0, p.width, p.height);
		    cvs.drawImage(img, 0, 0, 132, 132, 15, 0, 140, 140);
			p.drawLine();	
	        p.drawSpanner(50+steps);
		};
		img.src = "images/ios_biaopan_sma132.png";		
	});

};
	
	
	panel.prototype.drawLine = function() {
		var p = this;
		var cvs = this.cvs;
		var j =0;
		for (var i = -10; i <= 70; i=i+2) {
			p.save(function() {
				cvs.translate(p.width / 3.5, p.height/2-7);
				cvs.rotate(Math.PI / 60 * i);
				p.save(function() {
					cvs.beginPath();
					cvs.lineTo(-(p.height - p.outsideStyle.lineWidth-96) + 10, 0);
					if((i+10)%10===0){
					    cvs.lineTo(-(p.height - p.outsideStyle.lineWidth-96) + 1, 0);
					}else{
					    cvs.lineTo(-(p.height - p.outsideStyle.lineWidth-96) + 3, 0);
					}
					
					cvs.strokeStyle = "#FFF";
					cvs.stroke();
					if ((i+10) % 10 == 0) {
						p.drawText(parseInt((i+10+j)/4));
					}
					j = j + 8;
				});
			});
		}
	};
	panel.prototype.drawText = function(val) {
		var p = this;
		var cvs = this.cvs;
		p.save(function() {
			cvs.lineWidth = 1;
			cvs.strokeText(val, -(p.height - p.outsideStyle.lineWidth-95) + 12, 3);
		});
	};
	panel.prototype.drawSpanner = function(value) {
		var p = this;
		var cvs = this.cvs;
		p.save(function() {
			cvs.translate(p.width /3.5, p.height/2-2);
			cvs.rotate(Math.PI / 60 * -90);
			p.save(function() {
				cvs.rotate(Math.PI / 60 * value);
				cvs.beginPath();
				cvs.moveTo(3, -3);
				cvs.lineTo(0, -p.height * 0.3);
				cvs.lineTo(-3, -3);
				cvs.lineTo(3, -3);
				cvs.fillStyle = "red";
				cvs.fill();
			});
		});
	};

	window.init1 = function(value,flag,index) {
		var steps = index;
		if(flag===0){
		   steps = 0;
		   if(typeof(handler)!="undefined"){
			  window.clearTimeout(handler);
			  return;  
			}
		}
		if(steps>100){
			steps = 0;
			window.clearTimeout(handler);
			return;
		}
		var panelOption = {
			maxLength: 30,
			interval: 5,
			level: [//仪表盘需要呈现的数据隔离区域
				  {start: 0, color: "red" },
				  { start: 30, color: "yellow" },
				  { start: 40, color: "blue" },
				  { start: 100, color: "Lime" }
				  ],
			outsideStyle: { lineWidth: 4, color: "rgba(100,100,100,1)" }
		};

		Panel = new panel("canvas1", panelOption);
		
//		Panel.canvas.onclick = function(){
//			globalIndexBtn = 1;
//			document.getElementById("indexSaleTitle").innerHTML="机票";
//			Global.MainController.clickCanvasIndex(globalTimeScope,globalTimeNumber,1);
//			Global.MainController.getSaleTotalDetailInIndex(globalTimeScope,globalTimeNumber,1);
//		};
		
		
		/*
		Panel.canvas.addEventListener('touchstart', function(event) {
			globalIndexBtn = 1;
			document.getElementById("indexSaleTitle").innerHTML="机票";
			Global.MainController.clickCanvasIndex(globalTimeScope,globalTimeNumber,1);
			Global.MainController.getSaleTotalDetailInIndex(globalTimeScope,globalTimeNumber,1);
		}, false);
		*/
		if(!firstInit1){
			firstInit1 = true;
			Panel.canvas.addEventListener('touchstart', function(event) {
				globalIndexBtn = 1;
				document.getElementById("indexSaleTitle").innerHTML="机票";
				Global.MainController.clickCanvasIndex(globalTimeScope,globalTimeNumber,1);
				Global.MainController.getSaleTotalDetailInIndex(globalTimeScope,globalTimeNumber,1);
			}, false);
		}
		
	
		var tmp = Math.round(Math.random()*150);
		var p = Panel;
		
	    var cvs = Panel.cvs;
		//cvs.clearRect(0, 0, Panel.width, Panel.height);
		p.drawImage(steps);	
		steps += 1;	
		var handler = window.setTimeout('init1('+value+',1,'+steps+')',10);			
		if(steps==value){
			window.clearTimeout(handler);
			steps = 0;
			return;
		}
	};
//****************酒店销售额仪表盘
var panel1 = function(id, option) {
	     this.canvas = document.getElementById(id); //获取canvas元素
		 this.cvs = this.canvas.getContext("2d"); //得到绘制上下文
		 this.width = this.canvas.width; //对象宽度
		 this.height = this.canvas.height; //对象高度
		 //alert(this.width+"========"+this.height);
		 this.level = option.level;
		 this.outsideStyle = option.outsideStyle;
    };
	var panelOption1 = {
		maxLength: 30,
		interval: 5,
		level: [//仪表盘需要呈现的数据隔离区域
			  {start: 0, color: "red" },
			  { start: 30, color: "yellow" },
			  { start: 40, color: "blue" },
			  { start: 100, color: "Lime" }
			  ],
		outsideStyle: { lineWidth: 8, color: "rgba(100,100,100,0.5)" }
	};	
	panel1.prototype.save = function(fn) {
		this.cvs.save();
		fn();
		this.cvs.restore();
	};
	
	panel1.prototype.drawImage = function(steps) {
	var p = this;
	var cvs = this.cvs;
	p.save(function() {		
		var img = new Image();		
		img.onload = function(){
			cvs.clearRect(0, 0, p.width, p.height);
		    cvs.drawImage(img, 0, 0, 132, 132, 15, 0, 140, 140);
			p.drawLine();	
	        p.drawSpanner(50+steps);
		};
		img.src = "images/ios_biaopan_sma132.png";		
	});

};
	
	
	panel1.prototype.drawLine = function() {
		var p = this;
		var cvs = this.cvs;
		var j =0;
		for (var i = -10; i <= 70; i=i+2) {
			p.save(function() {
				cvs.translate(p.width / 3.5, p.height/2-7);
				cvs.rotate(Math.PI / 60 * i);
				p.save(function() {
					cvs.beginPath();
					cvs.lineTo(-(p.height - p.outsideStyle.lineWidth-96) + 10, 0);
					if((i+10)%10===0){
					    cvs.lineTo(-(p.height - p.outsideStyle.lineWidth-96) + 1, 0);
					}else{
					    cvs.lineTo(-(p.height - p.outsideStyle.lineWidth-96) + 3, 0);
					}
					
					cvs.strokeStyle = "#FFF";
					cvs.stroke();
					if ((i+10) % 10 == 0) {
						p.drawText(parseInt((i+10+j)/4));
					}
					j = j + 8;
				});
			});
		}
	};
	panel1.prototype.drawText = function(val) {
		var p = this;
		var cvs = this.cvs;
		p.save(function() {
			cvs.lineWidth = 1;
			cvs.strokeText(val, -(p.height - p.outsideStyle.lineWidth-95) + 12, 3);
		});
	};
	panel1.prototype.drawSpanner = function(value) {
		var p = this;
		var cvs = this.cvs;
		p.save(function() {
			cvs.translate(p.width / 3.5, p.height/2-2);
			cvs.rotate(Math.PI / 60 * -90);
			p.save(function() {
				cvs.rotate(Math.PI / 60 * value);
				cvs.beginPath();
				cvs.moveTo(3, -3);
				cvs.lineTo(0, -p.height * 0.3);
				cvs.lineTo(-3, -3);
				cvs.lineTo(3, -3);
				cvs.fillStyle = "red";
				cvs.fill();
			});
		});
	};

	window.init2 = function(value,flag,index) {
		var steps = index;
		if(flag===0){
		   steps = 0;
		   if(typeof(handler)!="undefined"){
			  window.clearTimeout(handler);
			  return;  
			}
		}
		if(steps>100){
			steps = 0;
			window.clearTimeout(handler);
			return;
		}
		var panelOption1 = {
			maxLength: 30,
			interval: 5,
			level: [//仪表盘需要呈现的数据隔离区域
				  {start: 0, color: "red" },
				  { start: 30, color: "yellow" },
				  { start: 40, color: "blue" },
				  { start: 100, color: "Lime" }
				  ],
			outsideStyle: { lineWidth: 4, color: "rgba(100,100,100,1)" }
		};

		Panel = new panel1("canvas2", panelOption1);
		
//		Panel.canvas.onclick = function(){
//			globalIndexBtn = 2;
//			Global.MainController.clickCanvasIndex(globalTimeScope,globalTimeNumber,2);
//			Global.MainController.getSaleTotalDetailInIndex(globalTimeScope,globalTimeNumber,2);
//			document.getElementById("indexSaleTitle").innerHTML="酒店";
//		};
		
		if(!firstInit2){
			firstInit2 = true;
			Panel.canvas.addEventListener('touchstart', function(event) {
				globalIndexBtn = 2;
				Global.MainController.clickCanvasIndex(globalTimeScope,globalTimeNumber,2);
				Global.MainController.getSaleTotalDetailInIndex(globalTimeScope,globalTimeNumber,2);
				document.getElementById("indexSaleTitle").innerHTML="酒店";
			}, false);
		}
		
		
		var tmp = Math.round(Math.random()*150);
		var p = Panel;
	    var cvs = Panel.cvs;	
	   
		//cvs.clearRect(0, 0, Panel.width, Panel.height);		
		p.drawImage(steps);	
		steps += 1;	
		var handler = window.setTimeout('init2('+value+',1,'+steps+')',10);			
		if(steps==value){
			window.clearTimeout(handler);
			steps = 0;
			return;
		}
	};
//************总销售额仪表盘面板*********
//window.DeptInfoPanel = function(id, option) {
//	//var tpd = $("#"+id); //获取canvas元素
//	// var tpd = document.getElementById(id);
//	// this.canvas = tpd[0];
//	this.canvas = document.getElementById(id);
//	this.cvs = this.canvas.getContext("2d");
//	//得到绘制上下文
//	this.cvs.font = "12px Courier";
//	this.width = this.canvas.width / 1.6;
//	//对象宽度
//	this.height = 2 * this.width / 3;
//	//this.canvas.height; //对象高度
//	this.level = option.level;
//	this.outsideStyle = option.outsideStyle;
//};
//DeptInfoPanel.prototype.save = function(fn) {
//	this.cvs.save();
//	fn();
//	this.cvs.restore();
//};
//
//DeptInfoPanel.prototype.drawImage = function(steps) {
//	var p = this;
//	var cvs = this.cvs;
//	p.save(function() {
//		var img = new Image();
//		img.onload = function() {
//			cvs.clearRect(0, 0, p.width, p.height);
//			cvs.drawImage(img, 0, 0, 166, 166, 18, 0, 150, 150);
//			p.drawLine();
//			p.drawSpanner(50 + steps);
//
//		};
//		img.src = "images/biaopan_big.png";
//	});
//
//};
//DeptInfoPanel.prototype.drawLine = function() {
//	var p = this;
//	var cvs = this.cvs;
//	var j = 0;
//	for(var i = -10; i <= 70; i = i + 2) {
//		p.save(function() {
//			cvs.translate(p.width / 2, p.height / 2 + 10);
//			cvs.rotate(Math.PI / 60 * i);
//			p.save(function() {
//				cvs.beginPath();
//				cvs.lineTo(-(p.height - p.outsideStyle.lineWidth - 57) + 10, 0);
//				//设置圆形文本刻度半径大小
//				if((i + 10) % 10 === 0) {
//					cvs.lineTo(-(p.height - p.outsideStyle.lineWidth - 57) + 1, 0);
//					//设置圆形文本刻度半径大小
//				} else {
//					cvs.lineTo(-(p.height - p.outsideStyle.lineWidth - 57) + 3, 0);
//					//设置圆形文本刻度半径大小
//				}
//				cvs.strokeStyle = "#FFF";
//				cvs.lineWidth = 1;
//				cvs.stroke();
//				if((i + 10) % 10 === 0) {
//					//p.drawText((i+10));
//					p.drawText(parseInt((i + 10 + j) /4));
//				}
//				j = j + 8;
//
//			});
//		});
//	}
//};
//DeptInfoPanel.prototype.drawText = function(val) {
//	var p = this;
//	var cvs = this.cvs;
//	p.save(function() {
//		cvs.strokeText(val, -(p.height - p.outsideStyle.lineWidth - 54) + 12, 3);
//		//设置圆形文本半径大小
//	});
//};
//DeptInfoPanel.prototype.drawSpanner = function(value) {
//	var p = this;
//	var cvs = this.cvs;
//
//	p.save(function() {
//		cvs.translate(p.width / 2, p.height / 2 + 10);
//		cvs.rotate(Math.PI / 60 * -90);
//		p.save(function() {
//			cvs.rotate(Math.PI / 60 * value);
//			cvs.beginPath();
//			cvs.moveTo(3, -3);
//			cvs.lineWidth = 2;
//			cvs.lineTo(0, -p.height * 0.45);
//			//设置指针长短
//			cvs.lineTo(-3, -3);
//			cvs.lineTo(3, -3);
//			cvs.fillStyle = "red";
//			cvs.fill();
//		});
//	});
//};

window.initInstrument = function(value, flag, index) {
	var steps = index;
	if(flag === 0) {
		steps = 0;
		if( typeof (handler) != "undefined") {
			window.clearTimeout(handler);
			return;
		}
	}
	if(steps > 100) {
		steps = 0;
		window.clearTimeout(handler);
		return;
	}
	var panelOption = {
		interval : 5,
		level : [{
			start : 0,
			color : "red"
		}, {
			start : 40,
			color : "yellow"
		}, {
			start : 60,
			color : "blue"
		}, {
			start : 100,
			color : "Lime"
		}],
		outsideStyle : {
			lineWidth : 4,
			color : "rgba(100,100,100,1)"
		}
	};

	var myPanel = new DeptInfoPanel("canvasDeptInfo", panelOption);

//	myPanel.canvas.onclick = function() {
//		clickCanvasAll();
//	};

	var tmp = Math.round(Math.random() * 90);
	var p = myPanel;

	var cvs = myPanel.cvs;
	//alert(steps);
	//cvs.clearRect(0, 0, myPanel.width, myPanel.height);
	p.drawImage(steps);
	//drawPic(cvs,myPanel,value);
	steps += 1;
	var handler = window.setTimeout('initInstrument(' + value + ',1,' + steps + ')', 10);
	if(steps == value) {
		window.clearTimeout(handler);
		steps = 0;
		return;
	}
};

/**
 * 地图初始化
 */
//function initMap() {
//	/* 地图显示功能 */
//	var canvas = document.getElementById('demo');
//	var ctx = canvas.getContext('2d');
//	
//	
//	ctx.font = "normal inherit  13px tahoma,arial,'微软雅黑','黑体'";
//	var myImage = new Image();
//	if(!canvas.getContext)
//		return;
//
//	var _vLeft = (628 - 435) / 2;
//	var _vTop = (228 - 229) / 2;
//
//	if(_vLeft < 0) {
//		_vLeft = 0
//	}
//	if(_vTop < 0) {
//		_vTop = 0;
//	}
//
//	myImage.onload = function() {
//		ctx.drawImage(myImage, _vLeft, _vTop);
//		
//		// Draw image.
//		ctx.strokeText('西北地区', 120, 45);
//		ctx.strokeText('-', 120, 65);
//
//		ctx.strokeText('西南地区', 120, 135);
//		ctx.strokeText('-', 120, 155);
//
//		ctx.strokeText('华北地区', 270, 45);
//		ctx.strokeText('-', 270, 65);
//
//		ctx.strokeText('华南地区', 245, 180);
//		ctx.strokeText('-', 245, 200);
//
//		ctx.strokeText('华中地区', 390, 145);
//		ctx.strokeText('-', 390, 165);
//		
//		ctx.strokeText('东北地区', 450, 30);
//		ctx.strokeText('-', 450, 50);
//		
//	}
//
//	myImage.src = 'images/map.png';
//
//	ctx.drawImage(myImage, _vLeft, _vTop);
//
//};

