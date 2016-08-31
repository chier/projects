/**
 * var config = {container:container1, columns:4};
 * new talent.ui.FlexGridLayout({})
 * @param {} config
 */
talent.ui.FlexGridLayout = function () {
	this.count = 1;
	this.rowContainer = null;
	this.table = null;
	this.row = null;
	this.rowIndex = 0;
	this.cellIndex = 0;
	
	/**
	 * container_type : label/input
	 */
	this.next1 = function (type) {
		var div = document.createElement("div");
		this.container.appendChild(div);
		div.className = "talent_flexgrid_" + type + "_container talent_flexgrid_" + type + "_" + this.columns + "column";
		return div;
	};
	
	this.next2 = function (type) {
		
		if (this.rowContainer == null) {
			this.rowContainer = document.createElement("div");
			this.container.appendChild(this.rowContainer);
			
			this.rowContainer.width = "100%";
			this.rowContainer.height = "26px";
		}
		
		var div = document.createElement("span");
		this.rowContainer.appendChild(div);
		
		this.container.appendChild(div);
		div.className = "talent_flexgrid_" + type + "_container talent_flexgrid_" + type + "_" + this.columns + "column";
		div.style.display = "inline";
		if (this.count % this.columns == 0) {
			this.rowContainer = null;
		}
		this.count++;
		return div;
	};
	
	
	
	
	
	
	this.next = function (type) {
		if (this.table == null) {
			this.table = document.createElement("fieldset");
			this.container.appendChild(this.table);
			this.table.width = "100%";
		}
		if (this.row == null) {
			this.row = this.table.insertRow(this.rowIndex++);
			this.cellIndex = 0;
		}
		
		var cell = this.row.insertCell(this.cellIndex++);
		cell.className = "talent_flexgrid_" + type + "_container talent_flexgrid_" + type + "_" + this.columns + "column";
		
		if (this.count % this.columns == 0) {
			this.row = null;
		}
		this.count++;
		return cell;
	};
	
	this.layout1 = function (){
		$(this.container).layout();
	};
	
	this.layout = function (){
		//$(this.container).layout();
	};
	
	this.setConfig = function (config) {
		this.container = config.container;
		this.columns = config.columns == undefined ? 4 : config.columns;
		this.hgap = config.hgap == undefined ? 1 : config.hgap;
		this.vgap = config.vgap == undefined ? 1 : config.vgap;
		this.theme = config.theme == undefined ? "default" /**green/blue/red*/ : config.theme;   //暂时不支持theme
	
		//$.include(this.theme + ".css", function(){});
		
		//this.container.className += " {layout:{type:'flexGrid', hgap: " + this.hgap + ", vgap: " + this.vgap + ", columns: " + this.columns + "}}";
		return this;
	};
};