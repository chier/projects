/*
 * Sub class that adds a check box in front of the tree item icon
 * 
 * Created by Erik Arvidsson (http://webfx.eae.net/contact.html#erik)
 * 
 * Disclaimer: This is not any official WebFX component. It was created due to
 * demand and is just a quick and dirty implementation. If you are interested in
 * this functionality the contact us http://webfx.eae.net/contact.html
 * 
 * Notice that you'll need to add a css rule the sets the size of the input box.
 * Something like this will do fairly good in both Moz and IE
 * 
 * input.tree-check-box { width: auto; margin: 0; padding: 0; height: 14px;
 * vertical-align: middle; }
 * 
 */

var disableColor = webFXTreeConfig.disableColor;

function WebFXCheckBoxTreeItem(sText, sValue, isItem, eParent, sIcon,
		sOpenIcon, bChecked, disabled) {
	this.base = WebFXTreeItem;
	this.base(sText, null, eParent, sIcon, sOpenIcon);
	this._checked = bChecked;
	this._disabled = false;
	this.isItem = isItem;
	if (disabled)
		this._disabled = disabled;

	// luohc 2004-7-30.
	this.value = sValue == null ? "" : sValue;
	if (bChecked != null && bChecked == true) {
		checkedValues.put(this.id, new CheckedObject(this.id, sText, sValue));
		checkedNodes.put(this.id, this);
	}
}

WebFXCheckBoxTreeItem.prototype = new WebFXTreeItem;

WebFXCheckBoxTreeItem.prototype.toString = function(nItem, nItemCount) {
	var foo = this.parentNode;
	var indent = '';
	var indentArray = []; // 显示子节点时,父节点的虚线
	if (nItem + 1 == nItemCount) {
		this.parentNode._last = true;
	}
	var i = 0;
	while (foo.parentNode) {
		foo = foo.parentNode;
		/*
		indent = "<img id=\""
				+ this.id
				+ "-indent-"
				+ i
				+ "\" src=\""
				+ ((foo._last)
						? webFXTreeConfig.blankIcon
						: webFXTreeConfig.iIcon) + "\">" + indent;
		*/
		indentArray.push("<img id=\"");
		indentArray.push(this.id);
		indentArray.push("-indent-");
		indentArray.push(i);
		indentArray.push("\" src=\"");
		indentArray.push(((foo._last) 
						?webFXTreeConfig.blankIcon
						:webFXTreeConfig.iIcon));
		indentArray.push("\">");
		i++;
	}
	this._level = i;
	if (this.childNodes.length) {
		this.folder = 1;
	} else {
		this.open = false;
	}
	if ((this.folder) || (webFXTreeHandler.behavior != 'classic')) {
		if (!this.icon) {
			this.icon = webFXTreeConfig.folderIcon;
		}
		if (!this.openIcon) {
			this.openIcon = webFXTreeConfig.openFolderIcon;
		}
	} else if (!this.icon) {
		this.icon = webFXTreeConfig.fileIcon;
	}
	var label = "&nbsp;"
			+ this.text.replace(/</g, '&lt;').replace(/>/g, '&gt;');
	var strArray = [];
	
	strArray.push("<div id=\"");
	strArray.push(this.id);
	strArray.push("\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">");
	strArray.push(indentArray.join(""));
	indentArray = null;
	strArray.push("<img id=\"");
	strArray.push(this.id);
	strArray.push("-plus\" src=\"");
	strArray.push(((this.folder) ? ((this.open) ? ((this.parentNode._last)
					? webFXTreeConfig.lMinusIcon
					: webFXTreeConfig.tMinusIcon) : ((this.parentNode._last)
					? webFXTreeConfig.lPlusIcon
					: webFXTreeConfig.tPlusIcon)) : ((this.parentNode._last)
					? webFXTreeConfig.lIcon
					: webFXTreeConfig.tIcon)));
	strArray.push("\" onclick=\"webFXTreeHandler.toggle(this);\">");
	strArray.push("<input type=\"checkbox\"");
	strArray.push(" class=\"tree-check-box\"");
	strArray.push((this._checked ? " checked=\"checked\"" : ""));
	strArray.push(" onclick=\"webFXTreeHandler.all[this.parentNode.id].setChecked(this.checked)\"");
	strArray.push(" value=\"" + this.value + "\" id=\"");
	strArray.push(this.id);
	strArray.push("-input\" name=\"");
	strArray.push(this.value);
	strArray.push("-input\" ");
	strArray.push((this._disabled ? " disabled " : ""));
	strArray.push(">");
	strArray.push("<img id=\"");
	strArray.push(this.id);
	strArray.push("-icon\" class=\"webfx-tree-icon\" src=\"");
	strArray.push(((webFXTreeHandler.behavior == 'classic' && this.open)
					? this.openIcon
					: this.icon));
	strArray.push("\" onclick=\"webFXTreeHandler.select(this);\"><span  id=\"");
	strArray.push(this.id);
	strArray.push("-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\" style='cursor:hand;color:");
	strArray.push(this.getColor());
	strArray.push("'>");
	strArray.push(label);
	strArray.push("</span></div>");
	strArray.push("<div id=\"");
	strArray.push(this.id);
	strArray.push("-cont\" class=\"webfx-tree-container\" style=\"display: ");
	strArray.push(((this.open) ? 'block' : 'none'));
	strArray.push(";\">");
	for (var i = 0; i < this.childNodes.length; i++) {
		strArray.push(this.childNodes[i].toString(i, this.childNodes.length));
	}
	strArray.push("</div>");
	var str = strArray.join("");
	strArray = null;
	
	/*
	var str = "<div id=\""
			+ this.id
			+ "\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">";
//	str += indent;
	str += indentArray.join("");
    indentArray = null;
	str += "<img id=\""
			+ this.id
			+ "-plus\" src=\""
			+ ((this.folder) ? ((this.open) ? ((this.parentNode._last)
					? webFXTreeConfig.lMinusIcon
					: webFXTreeConfig.tMinusIcon) : ((this.parentNode._last)
					? webFXTreeConfig.lPlusIcon
					: webFXTreeConfig.tPlusIcon)) : ((this.parentNode._last)
					? webFXTreeConfig.lIcon
					: webFXTreeConfig.tIcon))
			+ "\" onclick=\"webFXTreeHandler.toggle(this);\">"

	// insert check box
	var tempStr = "<input type=\"checkbox\""
			+ " class=\"tree-check-box\""
			+ (this._checked ? " checked=\"checked\"" : "")
			+ " onclick=\"webFXTreeHandler.all[this.parentNode.id].setChecked(this.checked)\""
			+ " value=\"" + this.value + "\" id=\"" + this.id
			+ "-input\" name=\"" + this.value + "-input\" "
			+ (this._disabled ? " disabled " : "") + ">";
	str += tempStr;
	// end insert checkbox

	// str += "<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\"
	// src=\"" + ((webFXTreeHandler.behavior == 'classic' &&
	// this.open)?this.openIcon:this.icon) + "\"
	// onclick=\"webFXTreeHandler.select(this);\"><a href=\"" + this.action +
	// "\" id=\"" + this.id + "-anchor\"
	// onfocus=\"webFXTreeHandler.focus(this);\"
	// onblur=\"webFXTreeHandler.blur(this);\">" + label + "</a></div>";
	tempStr = "<img id=\""
			+ this.id
			+ "-icon\" class=\"webfx-tree-icon\" src=\""
			+ ((webFXTreeHandler.behavior == 'classic' && this.open)
					? this.openIcon
					: this.icon)
			+ "\" onclick=\"webFXTreeHandler.select(this);\"><span  id=\""
			+ this.id
			+ "-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\" style='cursor:hand;color:"
			+ this.getColor() + "'>" + label + "</span></div>";
	str += tempStr;

	str += "<div id=\"" + this.id
			+ "-cont\" class=\"webfx-tree-container\" style=\"display: "
			+ ((this.open) ? 'block' : 'none') + ";\">";
	for (var i = 0; i < this.childNodes.length; i++) {
		str += this.childNodes[i].toString(i, this.childNodes.length);
	}
	str += "</div>";
	*/
	
	this.plusIcon = ((this.parentNode._last)
			? webFXTreeConfig.lPlusIcon
			: webFXTreeConfig.tPlusIcon);
	this.minusIcon = ((this.parentNode._last)
			? webFXTreeConfig.lMinusIcon
			: webFXTreeConfig.tMinusIcon);
	return str;
}

WebFXCheckBoxTreeItem.prototype.getChecked = function() {
	// var divEl = document.getElementById(this.id);
	// var inputEl = divEl.getElementsByTagName("INPUT")[0];
	var inputEl = getCheckBox(this.id);
	return this._checked = inputEl.checked;
};

WebFXCheckBoxTreeItem.prototype.setChecked = function(bChecked) {
	// 这个方法被罗洪臣修改 2004-7-30.
	/*
	 * if (bChecked != this.getChecked()) { var divEl =
	 * document.getElementById(this.id); var inputEl =
	 * divEl.getElementsByTagName("INPUT")[0]; this._checked = inputEl.checked =
	 * bChecked;
	 * 
	 * if (typeof this.onchange == "function") this.onchange(); }
	 */
	this._checked = bChecked;
	doCheck(this, bChecked);
	if (typeof this.onchange == "function") {
		this.onchange(this.text, this.value, bChecked);
	} else if (this.onchange != null && this.onchange != "") {
		var str = this.onchange + "('" + this.text + "','" + this.value + "',"
				+ bChecked + ");";
		eval(str);
	}
};

/** *** 以下是递归选择CheckBox的方法 罗洪臣 2004-7-30 ****** */
var checkedValues = new Map();
var checkedNodes = new Map();
function CheckedObject(id, text, value) {
	this.id = id;
	this.text = text;
	this.value = value;
}

CheckedObject.prototype.toString = function() {
	var str = "\nid = " + this.id + "\ntext = " + this.text + "\nvalue = "
			+ this.value + "\n";
	return str;
}

function setCheckedValues(node, bChecked) {
	if (bChecked) {
		checkedValues.put(node.id, new CheckedObject(node.id, node.text,
						node.value));
		checkedNodes.put(node.id, node);
	} else {
		checkedValues.remove(node.id);
		checkedNodes.remove(node.id);
	}
}

function getCheckBox(id) {
	return document.getElementById(id + "-input");
}

function doCheck(item, bChecked) {
	setCheckedValues(item, bChecked);
	// 是否级联选择.

	if (webFXTreeConfig.cascadeCheck) {
		checkChildren(item, bChecked);
		if (!bChecked) {
			unCheckParents(item);
		} else {
			checkParents(item);
		}
	}
}

// 设置父节点为选中状态.如果子节点都被选中的话。
function checkParents(item) {
	var pnode = item.parentNode;
	if (pnode instanceof WebFXCheckBoxTreeItem) {
		for (var i = 0; i < pnode.childNodes.length; i++) {
			var node = pnode.childNodes[i];
			var cbx = getCheckBox(node.id);
			if (cbx == null || !cbx.checked) {
				// pnode.getCheckBox().checked = false;
				// setCheckedValues(pnode, false);
				return;
			}
		} // end for.
		getCheckBox(pnode.id).checked = true;
		setCheckedValues(pnode, true);
		checkParents(pnode);
	}
	if (pnode) {
		if (pnode.id == tree_root.id) {

			for (var i = 0; i < pnode.childNodes.length; i++) {
				var node = pnode.childNodes[i];
				var rootcbx = getCheckBox(node.id);
				if (rootcbx == null || !rootcbx.checked) {
					// pnode.getCheckBox().checked = false;
					// setCheckedValues(pnode, false);
					return;
				}
			} // end for.
			getCheckBox(tree_root.id).checked = true;
		}
	}

}

// 子节点未选中时，其父节点置为未选中状态.
function unCheckParents(item) {
	var pNode = item.parentNode;
	while (pNode instanceof WebFXCheckBoxTreeItem) {
		var cbx = getCheckBox(pNode.id);
		if (cbx.checked && !cbx.disabled) {
			cbx.checked = false;
			setCheckedValues(pNode, false);
			pNode = pNode.parentNode;
		} else {
			return;
		}
	}

	var rootcbx = getCheckBox(tree_root.id);
	if (rootcbx.checked) {
		if (rootcbx.checked) {
			rootcbx.checked = false;
			setCheckedValues(tree_root, false);
			pNode = pNode.parentNode;
		} else {
			return;
		}
	}
}

// 循环当前点击节点的所有子节点.
function checkChildren(item, bChecked) {
	checkNode(item); // 加载、展开子节点.
	for (var i = 0; i < item.childNodes.length; i++) {
		var node = item.childNodes[i];
		if (node instanceof WebFXCheckBoxTreeItem) {
			checkChildren(node, bChecked);
			var cbx = getCheckBox(node.id);
			if (!cbx.disabled) {
				cbx.checked = bChecked;
				setCheckedValues(node, bChecked);
			}
		}
	}
}

// 展开所有节点 sw add
function openAll(item) {

	if (item == null) {
		var item = webFXTreeHandler.all[tree_root.id];
		var bChecked = false;
		checkNode(item); // 加载、展开子节点.
		for (var i = 0; i < item.childNodes.length; i++) {
			var node = item.childNodes[i];
			if (node instanceof WebFXCheckBoxTreeItem) {
				checkNode(node);
			}
			if (node.childNodes.length > 0) {
				openAll(node);
			}
		}
	} else {
		checkNode(item); // 加载、展开子节点.
		for (var i = 0; i < item.childNodes.length; i++) {
			var node = item.childNodes[i];
			if (node instanceof WebFXCheckBoxTreeItem) {
				checkNode(node);
			}
			if (node.childNodes.length > 0) {
				openAll(node);
			}
		}
	}
}

// 加载、展开子节点
function checkNode(item) {
	if (item.loadChildren)
		item.loadChildren();
	if (item.childNodes.length > 0)
		item.expand();
}

// 获取选取的对象.
function getCheckObjects(includeDisabled) {
	var array = new Array();
	var values = checkedValues.getValues();
	for (var i = 0; i < values.length; i++) {
		var obj = values[i];
		if (obj != null) {
			if (includeDisabled) {
				array[array.length] = obj;
			} else {
				var cbx = getCheckBox(obj.id);
				if (!cbx.disabled)
					array[array.length] = obj;
			}
		}
	}
	if (getCheckBox(tree_root.id).checked) {
		array[array.length] = tree_root;
	}
	return array;
}

// 获取所有的选中CheckBox的值.
// includeDisabled 是否包括disabled状态的CheckBox, 默认值是false.
function getCheckValues(includeDisabled) {
	var array = new Array();
	var values = checkedValues.getValues();
	for (var i = 0; i < values.length; i++) {
		var obj = values[i];
		if (obj != null) {
			if (includeDisabled) {
				array[array.length] = obj.value;
			} else {
				var cbx = getCheckBox(obj.id);
				if (!cbx.disabled)
					array[array.length] = obj.value;
			}
		}
	}

	if (getCheckBox(tree_root.id).checked) {
		array[array.length] = 0;
	}
	return array;
}

// 获取所有的选中CheckBox的值. 包括根节点。sw add
// includeDisabled 是否包括disabled状态的CheckBox, 默认值是false.
function getAllCheckValues(includeDisabled) {

	var array = new Array();
	var values = checkedValues.getValues();
	for (var i = 0; i < values.length; i++) {
		var obj = values[i];
		if (obj != null) {
			if (includeDisabled) {
				if (obj.id == tree_root.id) {
					array[array.length] = 0;
				} else {
					array[array.length] = obj.value;
				}
			} else {
				if (obj.id == tree_root.id) {
					array[array.length] = 0;
				} else {
					var cbx = getCheckBox(obj.id);
					if (!cbx.disabled)
						array[array.length] = obj.value;
				}
			}
		}
	}

	return array;
}

function getCheckValuesWithOutRoot(includeDisabled) {

	var array = new Array();
	var values = checkedValues.getValues();
	for (var i = 0; i < values.length; i++) {
		var obj = values[i];
		if (obj != null) {
			if (includeDisabled) {
				if (obj.id != tree_root.id) {
					array[array.length] = obj.value;
				}
			} else {
				if (obj.id != tree_root.id) {
					var cbx = getCheckBox(obj.id);
					if (!cbx.disabled){
						
						array[array.length] = obj.value;
						
						}
				}
			}
		}
	}

	return array;
}

function getCheckIsItems(includeDisabled) {
	var array = new Array();
	var values = checkedNodes.getValues();
	// alert(values[0].id);
	// alert(values[0]._checked);
	for (var i = 0; i < values.length; i++) {
		var obj = values[i];
		if (obj != null) {
			if (includeDisabled) {
				array[array.length] = obj;
			} else {
				var cbx = getCheckBox(obj.id);
				if (!cbx.disabled)
					array[array.length] = obj;
			}
		}
	}
	return array;
}
// 返回选中 checkbox 的文本。
function getCheckTexts(includeDisabled) {
	var array = new Array();
	var values = checkedValues.getValues();
	for (var i = 0; i < values.length; i++) {
		var obj = values[i];
		if (obj != null) {
			if (includeDisabled) {
				array[array.length] = obj.text;
			} else {
				var cbx = getCheckBox(obj.id);
				if (!cbx.disabled)
					array[array.length] = obj.text;
			}
		}
	}
	return array;
}

// 返回选中 checkbox 的文本。不包括根节点 sw add
function getCheckTextsWithOutRoot(includeDisabled) {
	var array = new Array();
	var values = checkedValues.getValues();
	for (var i = 0; i < values.length; i++) {
		var obj = values[i];
		if (obj != null && obj.id != tree_root.id) {
			if (includeDisabled) {
				array[array.length] = obj.text;
			} else {
				var cbx = getCheckBox(obj.id);
				if (!cbx.disabled)
					array[array.length] = obj.text;
			}
		}
	}
	return array;
}

// 设置CheckBox的选中状态.
function setCheckBox(value, checked, recursive) {
	var cbxs = document.getElementsByName(value + "-input");
	if (cbxs && cbxs.length > 0) {
		for (var i = 0; i < cbxs.length; i++) {
			var cbx = cbxs[i];
			// if(!cbx.disabled)
			cbx.checked = checked;
			var id = cbx.id.substring(0, cbx.id.length - 6);
			var node = webFXTreeHandler.all[id];
			setCheckedValues(node, checked);
			if (recursive) {
				doCheck(node, checked);
			}
		}
	}
}
// add by conntsing
// 向上级联，向下级联，选中。
function setCheckBoxUpOrDown(value, checked, recursiveup, recursivedown) {
	var cbxs = document.getElementsByName(value + "-input");
	if (cbxs && cbxs.length > 0) {
		for (var i = 0; i < cbxs.length; i++) {
			var cbx = cbxs[i];
			// if(!cbx.disabled)
			cbx.checked = checked;
			var id = cbx.id.substring(0, cbx.id.length - 6);
			var node = webFXTreeHandler.all[id];
			setCheckedValues(node, checked);
			if (recursiveup) { // 向上
				if (checked) {
					checkParents(node);
				} else {
					unCheckParents(node);
				}
			}
			if (recursivedown) {// 向下
				checkChildren(node, checked)
			}
		}
	}
}

// 设置CheckBox的可用状态.
function disableCheckBox(id, disabled, recursive) {
	var cbxs = document.getElementsByName(id + "-input");
	if (cbxs && cbxs.length > 0) {
		for (var i = 0; i < cbxs.length; i++) {
			var cbx = cbxs[i];
			cbx.disabled = disabled;
			var id = cbx.id.substring(0, cbx.id.length - 6);
			var span = document.getElementById(id + "-anchor");
			if (span) {
				if (disabled)
					span.style.color = disableColor;
				else
					span.style.color = "black";
			}
			if (recursive) {
				var node = webFXTreeHandler.all[id];
				disabledChildren(node, disabled);
			}
		}
	}
}

function disabledChildren(node, disabled) {
	for (var i = 0; i < node.childNodes.length; i++) {
		var item = node.childNodes[i];
		var id = item.id;
		var cbx = document.getElementById(id + "-input");
		if (cbx)
			cbx.disabled = disabled;
		var span = document.getElementById(id + "-anchor");
		if (span) {
			if (disabled)
				span.style.color = disableColor;
			else
				span.style.color = "black";
		}
		disabledChildren(item, disabled);
	}
}

// 隐藏CheckBox.
function visiableCheckBox(visiable) {
	if (visiable) {
		disp = "";
	} else {
		disp = "none";
	}
	var cbxs = document.getElementsByTagName("INPUT");
	if (cbxs && cbxs.length > 0) {
		for (var i = 0; i < cbxs.length; i++) {
			var cbx = cbxs[i];
			if (cbx.type.toUpperCase() == "CHECKBOX") {
				cbx.style.display = disp;
			}
		}
	}
}

function getAllTextName() {

	var input = document.getElementsByTagName("input");

	var arrayObj = new Array()
	for (var i = 0; i < input.length; i++) {

		arrayObj[i] = input[i].nextSibling.nextSibling.innerHTML;
	}
	return arrayObj;
}
function getAllId() {

	var input = document.getElementsByTagName("input");

	var arrayObj = new Array()
	for (var i = 0; i < input.length; i++) {

		arrayObj[i] = input[i].value;

	}
	return arrayObj;

}
