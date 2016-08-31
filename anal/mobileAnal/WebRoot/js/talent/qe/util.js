/**
 * 实例化Render
 * 
 * @param {}
 *            renderClass
 * @param {}
 *            config
 * @return {}
 */
talent.qe.instanceRender = function(renderClass, config) {
	var str = "var render = new " + renderClass + "();";
	eval (str);
	render.setConfig(config);
	return render;
}

/**
 * invoke render
 * 
 * @param {}
 *            renderClass
 * @param {}
 *            config
 */
talent.qe.invokeRender = function(renderClass, config) {
	try  {
		var render = talent.qe.instanceRender(renderClass, config);
		render.render();
	}catch (e) {
		alert("error message from talent.qe.invokeRender: render class=" + renderClass + "\r\n" + e);
	}
}

/**
 * 基础渲染者
 */
talent.qe.BaseRender = talent.Class.extend({
	/**
	 * 所有的渲染都有一个setConfig方法
	 */
	setConfig:function (config) {
		this.config = config;
	},
	init : function (){
		
	}
});

/**
 * 基础创建者
 */
talent.qe.BaseCreator = talent.Class.extend({
	/**
	 * 所有的渲染都有一个setConfig方法
	 */
	setConfig:function (config) {
		this.config = config;
	},
	getConfig:function() {
		return this.config;
	},
	init : function (){
		
	}
});