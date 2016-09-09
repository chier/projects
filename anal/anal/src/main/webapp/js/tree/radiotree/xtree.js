/*----------------------------------------------------------------------------\
|                       Cross Browser Tree Widget 1.17                        |
|-----------------------------------------------------------------------------|
|                          Created by Emil A Eklund                           |
|                  (http://webfx.eae.net/contact.html#emil)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
| An object based tree widget,  emulating the one found in microsoft windows, |
| with persistence using cookies. Works in IE 5+, Mozilla and konqueror 3.    |
|-----------------------------------------------------------------------------|
|                   Copyright (c) 1999 - 2002 Emil A Eklund                   |
|-----------------------------------------------------------------------------|
| This software is provided "as is", without warranty of any kind, express or |
| implied, including  but not limited  to the warranties of  merchantability, |
| fitness for a particular purpose and noninfringement. In no event shall the |
| authors or  copyright  holders be  liable for any claim,  damages or  other |
| liability, whether  in an  action of  contract, tort  or otherwise, arising |
| from,  out of  or in  connection with  the software or  the  use  or  other |
| dealings in the software.                                                   |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| This  software is  available under the  three different licenses  mentioned |
| below.  To use this software you must chose, and qualify, for one of those. |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Non-Commercial License          http://webfx.eae.net/license.html |
| Permits  anyone the right to use the  software in a  non-commercial context |
| free of charge.                                                             |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Commercial license           http://webfx.eae.net/commercial.html |
| Permits the  license holder the right to use  the software in a  commercial |
| context. Such license must be specifically obtained, however it's valid for |
| any number of  implementations of the licensed software.                    |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| GPL - The GNU General Public License    http://www.gnu.org/licenses/gpl.txt |
| Permits anyone the right to use and modify the software without limitations |
| as long as proper  credits are given  and the original  and modified source |
| code are included. Requires  that the final product, software derivate from |
| the original  source or any  software  utilizing a GPL  component, such  as |
| this, is also licensed under the GPL license.                               |
|-----------------------------------------------------------------------------|
| Dependencies: xtree.css (To set up the CSS of the tree classes)             |
|-----------------------------------------------------------------------------|
| 2001-01-10 | Original Version Posted.                                       |
| 2001-03-18 | Added getSelected and get/setBehavior  that can make it behave |
|            | more like windows explorer, check usage for more information.  |
| 2001-09-23 | Version 1.1 - New features included  keyboard  navigation (ie) |
|            | and the ability  to add and  remove nodes dynamically and some |
|            | other small tweaks and fixes.                                  |
| 2002-01-27 | Version 1.11 - Bug fixes and improved mozilla support.         |
| 2002-06-11 | Version 1.12 - Fixed a bug that prevented the indentation line |
|            | from  updating correctly  under some  circumstances.  This bug |
|            | happened when removing the last item in a subtree and items in |
|            | siblings to the remove subtree where not correctly updated.    |
| 2002-06-13 | Fixed a few minor bugs cased by the 1.12 bug-fix.              |
| 2002-08-20 | Added usePersistence flag to allow disable of cookies.         |
| 2002-10-23 | (1.14) Fixed a plus icon issue                                 |
| 2002-10-29 | (1.15) Last changes broke more than they fixed. This version   |
|            | is based on 1.13 and fixes the bugs 1.14 fixed withou breaking |
|            | lots of other things.                                          |
| 2003-02-15 | The  selected node can now be made visible even when  the tree |
|            | control  loses focus.  It uses a new class  declaration in the |
|            | css file '.webfx-tree-item a.selected-inactive', by default it |
|            | puts a light-gray rectangle around the selected node.          |
| 2003-03-16 | Adding target support after lots of lobbying...                |
|-----------------------------------------------------------------------------|
| Created 2000-12-11 | All changes are in the log above. | Updated 2003-03-16 |
\----------------------------------------------------------------------------*/

var webFXTreeConfig = {
	rootIcon        : base + '/js/tree/radiotree/images/foldericon.gif',
	openRootIcon    : base + '/js/tree/radiotree/images/openfolder.gif',
	folderIcon      : base + '/js/tree/radiotree/images/foldericon.gif',
	openFolderIcon  : base + '/js/tree/radiotree/images/openfolder.gif',
	fileIcon        : base + '/js/tree/radiotree/images/file.gif',
	iIcon           : base + '/js/tree/radiotree/images/I.gif',
	lIcon           : base + '/js/tree/radiotree/images/L.gif',
	lMinusIcon      : base + '/js/tree/radiotree/images/Lminus.gif',
	lPlusIcon       : base + '/js/tree/radiotree/images/Lplus.gif',
	tIcon           : base + '/js/tree/radiotree/images/T.gif',
	tMinusIcon      : base + '/js/tree/radiotree/images/Tminus.gif',
	tPlusIcon       : base + '/js/tree/radiotree/images/Tplus.gif',
	blankIcon       : base + '/js/tree/radiotree/images/blank.gif',
	defaultText     : 'Tree Item',
	defaultAction   : 'javascript:void(0);',
	defaultBehavior : 'classic',
	defaultRadioValue: 'identifier',
	usePersistence	: false,
	haveInput 		: true,
	rootUrl 		: ''
};

var webFXTreeHandler = {
	idCounter : 0,
	idPrefix  : "webfx-tree-object-",
	all       : {},
	behavior  : null,
	selected  : null,
	onSelect  : null, /* should be part of tree, not handler */
	getId     : function() { return this.idPrefix + this.idCounter++; },
	toggle    : function (oItem) { this.all[oItem.id.replace('-plus','')].toggle(); },
	select    : function (oItem) { this.all[oItem.id.replace('-icon','')].select(); },
//	focus     : function (oItem) { this.all[oItem.id.replace('-anchor','')].focus(); },
	blur      : function (oItem) { this.all[oItem.id.replace('-anchor','')].blur(); },
	keydown   : function (oItem, e) { return this.all[oItem.id].keydown(e.keyCode); },
	cookies   : new WebFXCookie(),
	insertHTMLBeforeEnd	:	function (oElement, sHTML) {
		if (oElement.insertAdjacentHTML != null) {
			oElement.insertAdjacentHTML("BeforeEnd", sHTML)
			return;
		}
		var df;	// DocumentFragment
		var r = oElement.ownerDocument.createRange();
		r.selectNodeContents(oElement);
		r.collapse(false);
		df = r.createContextualFragment(sHTML);
		oElement.appendChild(df);
	}
};

/*
 * WebFXCookie class
 */

function WebFXCookie() {
	if (document.cookie.length) { this.cookies = ' ' + document.cookie; }
}

WebFXCookie.prototype.setCookie = function (key, value) {
	document.cookie = key + "=" + escape(value);
	if (start == -1) this.cookies += ";" + ' ' + key + "=" + escape(value);
}

WebFXCookie.prototype.getCookie = function (key) {
	if (this.cookies) {
		var start = this.cookies.indexOf(' ' + key + '=');
		if (start == -1) { return null; }
		var end = this.cookies.indexOf(";", start);
		if (end == -1) { end = this.cookies.length; }
		end -= start;
		var cookie = this.cookies.substr(start,end);
		return unescape(cookie.substr(cookie.indexOf('=') + 1, cookie.length - cookie.indexOf('=') + 1));
	}
	else { return null; }
}

/*
 * WebFXTreeAbstractNode class
 */

function WebFXTreeAbstractNode(sText, sAction, sRadioValue) {
	this.childNodes  = [];
	this.id     = webFXTreeHandler.getId();
	this.text   = sText || webFXTreeConfig.defaultText;
	this.action = sAction || webFXTreeConfig.defaultAction;
	this.radioValue = sRadioValue || webFXTreeConfig.defaultRadioValue;
	this._last  = false;
	webFXTreeHandler.all[this.id] = this;
}

/*
 * To speed thing up if you're adding multiple nodes at once (after load)
 * use the bNoIdent parameter to prevent automatic re-indentation and call
 * the obj.ident() method manually once all nodes has been added.
 */

WebFXTreeAbstractNode.prototype.add = function (node, bNoIdent) {
	node.parentNode = this;
	this.childNodes[this.childNodes.length] = node;
	var root = this;
	if (this.childNodes.length >= 2) {
		this.childNodes[this.childNodes.length - 2]._last = false;
	}
	while (root.parentNode) { root = root.parentNode; }
	if (root.rendered) {
		if (this.childNodes.length >= 2) {
			document.getElementById(this.childNodes[this.childNodes.length - 2].id + '-plus').src = ((this.childNodes[this.childNodes.length -2].folder)?((this.childNodes[this.childNodes.length -2].open)?webFXTreeConfig.tMinusIcon:webFXTreeConfig.tPlusIcon):webFXTreeConfig.tIcon);
			this.childNodes[this.childNodes.length - 2].plusIcon = webFXTreeConfig.tPlusIcon;
			this.childNodes[this.childNodes.length - 2].minusIcon = webFXTreeConfig.tMinusIcon;
			this.childNodes[this.childNodes.length - 2]._last = false;
		}
		this._last = true;
		var foo = this;
		while (foo.parentNode) {
			for (var i = 0; i < foo.parentNode.childNodes.length; i++) {
				if (foo.id == foo.parentNode.childNodes[i].id) { break; }
			}
			if (i == foo.parentNode.childNodes.length - 1) { foo.parentNode._last = true; }
			else { foo.parentNode._last = false; }
			foo = foo.parentNode;
		}
		webFXTreeHandler.insertHTMLBeforeEnd(document.getElementById(this.id + '-cont'), node.toString());
		if ((!this.folder) && (!this.openIcon)) {
			this.icon = webFXTreeConfig.folderIcon;
			this.openIcon = webFXTreeConfig.openFolderIcon;
		}
		if (!this.folder) { this.folder = true; this.collapse(true); }
		if (!bNoIdent) { this.indent(); }
	}
	return node;
}

WebFXTreeAbstractNode.prototype.toggle = function() {
	if (this.folder) {
		if (this.open) { this.collapse();}
		else { this.expand();}
}	}

WebFXTreeAbstractNode.prototype.select = function() {
//	document.getElementById(this.id + '-anchor').focus();
}

WebFXTreeAbstractNode.prototype.deSelect = function() {
	document.getElementById(this.id + '-anchor').className = '';
	webFXTreeHandler.selected = null;
}

//WebFXTreeAbstractNode.prototype.focus = function() {
//	if ((webFXTreeHandler.selected) && (webFXTreeHandler.selected != this)) { webFXTreeHandler.selected.deSelect(); }
//	webFXTreeHandler.selected = this;
//	if ((this.openIcon) && (webFXTreeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.openIcon; }
//	document.getElementById(this.id + '-anchor').className = 'selected';
//	document.getElementById(this.id + '-anchor').focus();
//	if (webFXTreeHandler.onSelect) { webFXTreeHandler.onSelect(this); }
//}

WebFXTreeAbstractNode.prototype.blur = function() {
	if ((this.openIcon) && (webFXTreeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.icon; }
	document.getElementById(this.id + '-anchor').className = 'selected-inactive';
}

WebFXTreeAbstractNode.prototype.doExpand = function() {
	if (webFXTreeHandler.behavior == 'classic') { document.getElementById(this.id + '-icon').src = this.openIcon; }
	if (this.childNodes.length) {  document.getElementById(this.id + '-cont').style.display = 'block'; }
	this.open = true;
	
	if (webFXTreeConfig.usePersistence) {
		webFXTreeHandler.cookies.setCookie(this.id.substr(18,this.id.length - 18), '1');
}	}

WebFXTreeAbstractNode.prototype.doCollapse = function() {
	if (webFXTreeHandler.behavior == 'classic') { document.getElementById(this.id + '-icon').src = this.icon; }
	if (this.childNodes.length) { document.getElementById(this.id + '-cont').style.display = 'none'; }
	this.open = false;	
	if (webFXTreeConfig.usePersistence) {
		webFXTreeHandler.cookies.setCookie(this.id.substr(18,this.id.length - 18), '0');
} 	}

WebFXTreeAbstractNode.prototype.expandAll = function() {
	this.expandChildren();
	if ((this.folder) && (!this.open)) { this.expand(); }
}

WebFXTreeAbstractNode.prototype.expandChildren = function() {
	for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].expandAll();
} }

WebFXTreeAbstractNode.prototype.collapseAll = function() {
	this.collapseChildren();
	if ((this.folder) && (this.open)) { this.collapse(true); }
}

WebFXTreeAbstractNode.prototype.collapseChildren = function() {
	for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].collapseAll();
} }

WebFXTreeAbstractNode.prototype.indent = function(lvl, del, last, level, nodesLeft) {
	/*
	 * Since we only want to modify items one level below ourself,
	 * and since the rightmost indentation position is occupied by
	 * the plus icon we set this to -2
	 
	 if (last){
	    document.getElementById(this.id + '-plus').src =webFXTreeConfig.openFolderIcon;
	}
	alert(lvl + ":" + del + ":" +last +  ":" +level +  ":" +last );*/
	if (lvl == null) { lvl = -2; }
	var state = 0;
	for (var i = this.childNodes.length - 1; i >= 0 ; i--) {
		state = this.childNodes[i].indent(lvl + 1, del, last, level);
		if (state) { return; }
	}
	if (del) {
		if ((level >= this._level) && (document.getElementById(this.id + '-plus'))) {
			if (this.folder) {
				document.getElementById(this.id + '-plus').src = (this.open)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.lPlusIcon;
				this.plusIcon = webFXTreeConfig.lPlusIcon;
				this.minusIcon = webFXTreeConfig.lMinusIcon;
			}
			else if (nodesLeft) { document.getElementById(this.id + '-plus').src = webFXTreeConfig.lIcon; }
			if (last) {
			    document.getElementById(this.id + '-icon').src =webFXTreeConfig.openFolderIcon;
			}
			return 1;
	}	}
	var foo = document.getElementById(this.id + '-indent-' + lvl);
	if (foo) {
		if ((foo._last) || ((del) && (last))) { foo.src =  webFXTreeConfig.blankIcon; }
		else { foo.src =  webFXTreeConfig.iIcon; }
	}
	
	/** 此处添加 用于 plus 图标 */
	if(this.action != "folder" && webFXTreeConfig.haveInput){
		if(this.isleaf && this.isleaf == 1 ){
			if(this.parentNode.childNodes[this.parentNode.childNodes.length -1]== this ){
				document.getElementById(this.id + '-plus').src = webFXTreeConfig.lIcon;
			}else{
				document.getElementById(this.id + '-plus').src = webFXTreeConfig.tIcon;
			}
		}
	}
	return 0;
}

/*
 * WebFXTree class
 */

function WebFXTree(sText, sIdentifier, sIsRootRadio, sAction, sRadioValue, sBehavior, sIcon, sOpenIcon) {
	this.base = WebFXTreeAbstractNode;
	this.base(sText, sAction, sRadioValue);
	this.sIdentifier = sIdentifier;
	this.sIsRootRadio = sIsRootRadio;
	this.icon      = sIcon || webFXTreeConfig.rootIcon;
	this.openIcon  = sOpenIcon || webFXTreeConfig.openRootIcon;
	/* Defaults to open */
	if (webFXTreeConfig.usePersistence) {
		this.open  = (webFXTreeHandler.cookies.getCookie(this.id.substr(18,this.id.length - 18)) == '0')?false:true;
	} else { this.open  = true; }
	this.folder    = true;
	this.rendered  = false;
	this.onSelect  = null;
	if (!webFXTreeHandler.behavior) {  webFXTreeHandler.behavior = sBehavior || webFXTreeConfig.defaultBehavior; }
}

WebFXTree.prototype = new WebFXTreeAbstractNode;

WebFXTree.prototype.setBehavior = function (sBehavior) {
	webFXTreeHandler.behavior =  sBehavior;
};

WebFXTree.prototype.getBehavior = function (sBehavior) {
	return webFXTreeHandler.behavior;
};

WebFXTree.prototype.getSelected = function() {
	if (webFXTreeHandler.selected) { return webFXTreeHandler.selected; }
	else { return null; }
}

WebFXTree.prototype.remove = function() { }

WebFXTree.prototype.expand = function() {
	this.doExpand();
}

WebFXTree.prototype.collapse = function(b) {
//	if (!b) { this.focus(); }
	this.doCollapse();
}

WebFXTree.prototype.getFirst = function() {
	return null;
}

WebFXTree.prototype.getLast = function() {
	return null;
}

WebFXTree.prototype.getNextSibling = function() {
	return null;
}

WebFXTree.prototype.getPreviousSibling = function() {
	return null;
}

WebFXTree.prototype.keydown = function(key) {
	if (key == 39) {
		if (!this.open) { this.expand(); }
		else if (this.childNodes.length) { this.childNodes[0].select(); }
		return false;
	}
	if (key == 37) { this.collapse(); return false; }
	if ((key == 40) && (this.open) && (this.childNodes.length)) { this.childNodes[0].select(); return false; }
	return true;
}

WebFXTree.prototype.toString = function() {
	
	var temp_href="";
	if(webFXTreeConfig.rootUrl != ""){
		if (webFXTreeConfig.rootUrl.indexOf("/manage/usercommu/userCommuAction!listMessage.portal?groupId=0") != -1) {
			this.target = "listMessageFrame";
		} else {
			this.target = "frameEmp";
		}
		temp_href = " href=\""+ webFXTreeConfig.rootUrl +"\" ";
		
	}
	
	
	var str = "";
	var strList = [];
	strList[0] = "<div id=\"";
	strList[1] = this.id
	strList[2] = "\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\">";
	
		// insert check box
		if (this.action != "folder" && this.sIsRootRadio == "true"){	
			str += "<input type=\"radio\"" +
				" name=\"ids\" class=\"tree-check-box\"" +
				(this._checked ? " checked=\"checked\"" : "") +
				" onclick=\"handleCheck( this ,'" + this.sIdentifier + "' ,'"+ this.text + "'); \"" +
				" />" ;
		}
		// end insert radio
	strList[3] = str;
	strList[4] = "<img id=\"";
	strList[5] = this.id;
	strList[6] = "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" >"; 
	strList[7] = "<a id=\"";
	strList[8] = this.id;
	strList[9] = "-anchor\" " + (this.target ? " target=\"" + this.target + "\"" : "");
	strList[10]= temp_href;
	strList[11]= " onclick=\"processDeptId('0')\">"; 
	strList[12]= this.text;
	strList[13]= "</a></div>"; 
	strList[14]= "<div id=\""; 
	strList[15]= this.id;
	strList[16]= "-cont\" class=\"webfx-tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	
	var sb = [];
	for (var i = 0; i < this.childNodes.length; i++) {
		sb[i] = this.childNodes[i].toString(i, this.childNodes.length);
	}
	this.rendered = true;
	strList[17]= sb.join("");
	strList[18]= "</div>";
	return strList.join("");
};

/*
 * WebFXTreeItem class
 */

function WebFXTreeItem(sText, sAction, sRadioValue,isleaf, eParent, sIcon, sOpenIcon,actionDepart) {
	this.base = WebFXTreeAbstractNode;
	this.base(sText, sAction, sRadioValue);
	/* Defaults to close */
	if (webFXTreeConfig.usePersistence) {
		this.open = (webFXTreeHandler.cookies.getCookie(this.id.substr(18,this.id.length - 18)) == '1')?true:false;
	} else { this.open = false; }
	if (sIcon) { this.icon = sIcon; }
	if (sOpenIcon) { this.openIcon = sOpenIcon; }
	if (isleaf) { this.isleaf = isleaf;}
	if (eParent) { eParent.add(this); }
	this.actionDepart = actionDepart;
}

WebFXTreeItem.prototype = new WebFXTreeAbstractNode;

WebFXTreeItem.prototype.remove = function() {
    
	var iconSrc = document.getElementById(this.id + '-plus').src;
	var parentNode = this.parentNode;
	var prevSibling = this.getPreviousSibling(true);
	var nextSibling = this.getNextSibling(true);
	var folder = this.parentNode.folder;
	var last = ((nextSibling) && (nextSibling.parentNode) && (nextSibling.parentNode.id == parentNode.id))?false:true;
//	this.getPreviousSibling().focus();
	this._remove();
	if (parentNode.childNodes.length == 0) {
		document.getElementById(parentNode.id + '-cont').style.display = 'none';
		parentNode.doCollapse();
		parentNode.folder = true;
		parentNode.open = true;
	}
	if (!nextSibling || last) { parentNode.indent(null, true, last, this._level, parentNode.childNodes.length); }
	/*if ((prevSibling == parentNode) && !(parentNode.childNodes.length)) {
		prevSibling.folder = false;
		prevSibling.open = false;
		iconSrc = document.getElementById(prevSibling.id + '-plus').src;
		iconSrc = iconSrc.replace('minus', '').replace('plus', '');
		document.getElementById(prevSibling.id + '-plus').src = iconSrc;
		//document.getElementById(prevSibling.id + '-icon').src = webFXTreeConfig.fileIcon;
		document.getElementById(prevSibling.id + '-icon').src = webFXTreeConfig.openFolderIcon;
	}*/	
	if (document.getElementById(prevSibling.id + '-plus')) {
		if (parentNode == prevSibling.parentNode) {
			iconSrc = iconSrc.replace('minus', '').replace('plus', '');
			document.getElementById(prevSibling.id + '-plus').src = iconSrc;
        }	
    }
}

WebFXTreeItem.prototype._remove = function() {
	for (var i = this.childNodes.length - 1; i >= 0; i--) {
		this.childNodes[i]._remove();
 	}
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) {
			for (var j = i; j < this.parentNode.childNodes.length; j++) {
				this.parentNode.childNodes[j] = this.parentNode.childNodes[j+1];
			}
			this.parentNode.childNodes.length -= 1;
			if (i + 1 == this.parentNode.childNodes.length) { this.parentNode._last = true; }
			break;
	}	}
	webFXTreeHandler.all[this.id] = null;
	var tmp = document.getElementById(this.id);
	if (tmp) { tmp.parentNode.removeChild(tmp); }
	tmp = document.getElementById(this.id + '-cont');
	if (tmp) { tmp.parentNode.removeChild(tmp); }
}

WebFXTreeItem.prototype.expand = function() {
	this.doExpand();
	document.getElementById(this.id + '-plus').src = this.minusIcon;
}

WebFXTreeItem.prototype.collapse = function(b) {
//	if (!b) { this.focus(); }
	this.doCollapse();
	document.getElementById(this.id + '-plus').src = this.plusIcon;
}

WebFXTreeItem.prototype.getFirst = function() {
	return this.childNodes[0];
}

WebFXTreeItem.prototype.getLast = function() {
	if (this.childNodes[this.childNodes.length - 1].open) { return this.childNodes[this.childNodes.length - 1].getLast(); }
	else { return this.childNodes[this.childNodes.length - 1]; }
}

WebFXTreeItem.prototype.getNextSibling = function() {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	if (++i == this.parentNode.childNodes.length) { return this.parentNode.getNextSibling(); }
	else { return this.parentNode.childNodes[i]; }
}

WebFXTreeItem.prototype.getPreviousSibling = function(b) {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	if (i == 0) { return this.parentNode; }
	else {
		if ((this.parentNode.childNodes[--i].open) || (b && this.parentNode.childNodes[i].folder)) { return this.parentNode.childNodes[i].getLast(); }
		else { return this.parentNode.childNodes[i]; }
} }

WebFXTreeItem.prototype.keydown = function(key) {
	if ((key == 39) && (this.folder)) {
		if (!this.open) { this.expand(); }
		else { this.getFirst().select(); }
		return false;
	}
	else if (key == 37) {
		if (this.open) { this.collapse(); }
		else { this.parentNode.select(); }
		return false;
	}
	else if (key == 40) {
		if (this.open) { this.getFirst().select(); }
		else {
			var sib = this.getNextSibling();
			if (sib) { sib.select(); }
		}
		return false;
	}
	else if (key == 38) { this.getPreviousSibling().select(); return false; }
	return true;
}

WebFXTreeItem.prototype.toString = function (nItem, nItemCount) {
		var foo = this.parentNode;
		
	var indent = '';
	if (nItem + 1 == nItemCount) { this.parentNode._last = true; }
	var i = 0;
	while (foo.parentNode) {
		foo = foo.parentNode;
		indent = "<img id=\"" + this.id + "-indent-" + i + "\" src=\"" + ((foo._last)?webFXTreeConfig.blankIcon:webFXTreeConfig.iIcon) + "\">" + indent;
		i++;
	}
	this._level = i;
	if (this.childNodes.length) { this.folder = 1; }
	else { this.open = false; }
	
	
	if ((this.folder) || (webFXTreeHandler.behavior != 'classic')) {
		if (!this.icon) { 
			this.icon = webFXTreeConfig.folderIcon; 
		}
		if (!this.openIcon) { 
			this.openIcon = webFXTreeConfig.openFolderIcon; 
		}
	}
	else if (!this.icon) { 
		this.icon = webFXTreeConfig.fileIcon; 
	}
	
	var temp_href="";
	if(!webFXTreeConfig.haveInput){
		temp_href = " href=\""+ this.actionDepart +"\" ";
		var tempHref = "";
		var indexValue = temp_href.indexOf("groupId=");
		tempHref = temp_href.substring(indexValue+8);
		var indexValueChar = tempHref.indexOf("&");
		tempHref = tempHref.substring(0,indexValueChar);
		temp_href += " onclick=\"processDeptId("+tempHref+")\" ";
	}
	
	/** 设置 plus 图片的路径对象 */
	var img_plus = "";
	if(this.folder){
		if(this.open){
			if(this.parentNodex._last){
					img_plus = webFXTreeConfig.lMinusIcon;
			}else{
					img_plus = webFXTreeConfig.tMinusIcon;
			}
		}else{
			if(this.parentNode._last){
					img_plus = webFXTreeConfig.lPlusIcon;
			}else{
					img_plus = webFXTreeConfig.tPlusIcon;
			}
		}
	}else{
		if(this.parentNode._last){
			img_plus = webFXTreeConfig.lIcon;
		}else{
			img_plus = webFXTreeConfig.tIcon;
		}
	}
	
	
	/** 设置 icon 图片的路径对象 */
	var img_icon = "";
	if(this.action != "folder" && webFXTreeConfig.haveInput){
		if(webFXTreeConfig.isopen == true ||(webFXTreeHandler.behavior == 'classic' && this.isleaf == 0)) {
			img_icon = webFXTreeConfig.folderIcon
		}else{
			img_icon = webFXTreeConfig.fileIcon;
		}
	}else{
		if(webFXTreeHandler.behavior == 'classic' && this.open){
			img_icon = this.openIcon;
		}else{
			img_icon = this.icon;
		}
	}
	
	var divonClickStr = " ondblclick=\"webFXTreeHandler.toggle(this);\"";
	var plusclickStr = " onclick=\"webFXTreeHandler.toggle(this);\"";
	if(this.action != "folder" && webFXTreeConfig.haveInput && this.isleaf==1){
		divonClickStr = "";
		plusclickStr = "";
	}
	
	var label = this.text.replace(/</g, '&lt;').replace(/>/g, '&gt;');
	var strList = [];
	var str = "";
	strList[0] = "<div id=\"";
	strList[1] = this.id;
	strList[2] = "\" ";
	strList[3] = divonClickStr;
	strList[4] = " class=\"webfx-tree-item\" >";
	strList[5] = indent;
	strList[6] = "<img id=\"";
	strList[7] = this.id;
	strList[8] = "-plus\" ";
	strList[9] = plusclickStr;
	strList[10]= " src=\"";
	strList[11]= img_plus;
	strList[12]= "\">";
	
	// insert check box
	if (this.action != "folder" && webFXTreeConfig.haveInput){	
		str += "<input type=\"radio\"" +
				" name=\"ids\" class=\"tree-check-box\"" +
				(this._checked ? " checked=\"checked\"" : "") +
				" onclick=\"webFXTreeHandler.all[this.parentNode.id].setChecked(this.checked);handleCheck( this ,'" + this.radioValue + "' ,'"+ this.text + "') \"" +
				" />";
	}
	// end insert radio
	strList[13] = str;
	strList[14]	= "<img id=\"";
	strList[15] = this.id;
	strList[16] = "-icon\" class=\"webfx-tree-icon\" src=\"";
	strList[17] = img_icon;
	strList[18] = "\" onclick=\"webFXTreeHandler.select(this);\">";
	strList[19] = "<a id=\"";
	strList[20] = this.id;
	strList[21] = "-anchor\" "; 
	strList[22] = (this.target ? " target=\"" + this.target + "\"" : "");
	strList[23] = temp_href;
	strList[24] = ">";
	strList[25] = label;
	strList[26] =  "</a></div>";
	strList[27] = "<div id=\"";
	strList[28] = this.id;
	strList[29] = "-cont\" class=\"webfx-tree-container\" style=\"display: ";
	strList[30] = ((this.open)?'block':'none');
	strList[31] = ";\">";
	var sb = [];
	for (var i = 0; i < this.childNodes.length; i++) {
		sb[i] = this.childNodes[i].toString(i,this.childNodes.length);
	}
	this.plusIcon = ((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon);
	this.minusIcon = ((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon);
	strList[32] = sb.join("");
	strList[33] = "</div>";
	
	return strList.join("");
}



WebFXTreeItem.prototype.getChecked = function () {
	var divEl = document.getElementById(this.id);
	var inputEl = divEl.getElementsByTagName("INPUT")[0];
	return this._checked = inputEl.checked;
};

WebFXTreeItem.prototype.setChecked = function (bChecked) {
	if (bChecked != this.getChecked()) {
		var divEl = document.getElementById(this.id);
		var inputEl = divEl.getElementsByTagName("INPUT")[0];
		this._checked = inputEl.checked = bChecked;
		
		if (typeof this.onchange == "function")
			this.onchange();
	}
}

function Element(id, name, type){
  this.id=id;
  this.name=name;
  this.type=type;
}

function handleCheck(obj, id, name){
	if (obj.checked) {
	    addSelected(id, name);
	} else {
	    delSelected(id, name);
	}
 	
}
   
function addSelected(id, name) {
    var element = new Element(id, name, 0);
    /*
    bselected[bselected.length] = element;
    */
    bselected[0] = element;
}

function delSelected(id, name) {
    /*
    var element = new Element(id, name, 0);
   
    var size = bselected.length;
    for(var ele = 0; ele <size; ele ++  ){
	    if ( bselected[ele] != null && id == bselected[ele].id){
		    //bselected[ele] = null;
		    bselected = del(bselected, ele);
		    return;
		  }
	  }
		*/
		return;
}

function getSelected() {
    ids ="";
	name = "";
    for(ele in bselected){
	    if (bselected[ele] != null) {
		   ids = ids + bselected[ele].id;
	       names = names + bselected[ele].name;
	    }
	}   
}

function del( obj,n) {
  if(n<0)  
    return obj;
  else
    return obj.slice(0,n).concat(obj.slice(n+1,obj.length));
}

