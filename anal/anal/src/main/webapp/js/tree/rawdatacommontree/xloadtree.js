/*----------------------------------------------------------------------------\
|                               XLoadTree 1.11                                |
|-----------------------------------------------------------------------------|
|                         Created by Erik Arvidsson                           |
|                  (http://webfx.eae.net/contact.html#erik)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
| An extension to xTree that allows sub trees to be loaded at runtime by      |
| reading XML files from the server. Works with IE5+ and Mozilla 1.0+         |
|-----------------------------------------------------------------------------|
|                   Copyright (c) 1999 - 2002 Erik Arvidsson                  |
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
| 2001-09-27 | Original Version Posted.                                       |
| 2002-01-19 | Added some simple error handling and string templates for      |
|            | reporting the errors.                                          |
| 2002-01-28 | Fixed loading issues in IE50 and IE55 that made the tree load  |
|            | twice.                                                         |
| 2002-10-10 | (1.1) Added reload method that reloads the XML file from the   |
|            | server.                                                        |
/ 2003-05-06 | Added support for target attribute                             |
|-----------------------------------------------------------------------------|
| Dependencies: xtree.js - original xtree library                             |
|               xtree.css - simple css styling of xtree                       |
|               xmlextras.js - provides xml http objects and xml document     |
|                              objects                                        |
|-----------------------------------------------------------------------------|
| Created 2001-09-27 | All changes are in the log above. | Updated 2003-05-06 |
\----------------------------------------------------------------------------*/


webFXTreeConfig.loadingText = "Loading...";
webFXTreeConfig.loadErrorTextTemplate = "loading \"%1%\"";
webFXTreeConfig.emptyErrorTextTemplate = "";

/*
 * WebFXLoadTree class
 */

function WebFXLoadTree(sText,starget, sXmlSrc, sAction, sBehavior, sIcon, sOpenIcon) {
	// call super
	
	this.WebFXTree = WebFXTree;
	this.WebFXTree(sText, sAction, sBehavior, sIcon, sOpenIcon);

	// setup default property values
	this.src = sXmlSrc;
	this.loading = false;
	this.loaded = false;
	this.errorText = "";
	this.dprtid=0;
	this.isleaf = 0;
	
	this.div = this.createStringDiv();
	
    //TODO
    this.target = starget;
	// check start state and load if open
	if (this.open) {
		_startLoadXmlTree(this.src, this);
		
	} else {
	   
		// and create loading item if not
		this._loadingItem = new WebFXTreeItem(webFXTreeConfig.loadingText);
		this.add(this._loadingItem);
	}
}

WebFXLoadTree.prototype = new WebFXTree;

// override the expand method to load the xml file
WebFXLoadTree.prototype._webfxtree_expand = WebFXTree.prototype.expand;
WebFXLoadTree.prototype.expand = function() {
	if (!this.loaded && !this.loading) {
		// load
		_startLoadXmlTree(this.src, this);
	}
	this._webfxtree_expand();
};

/*
 * WebFXLoadTreeItem class
 */

function WebFXLoadTreeItem(sText, sXmlSrc, sAction, eParent, isleaf, sIcon, sOpenIcon) {
	// call super
	this.WebFXTreeItem = WebFXTreeItem;
	this.WebFXTreeItem(sText, sAction, eParent,isleaf, sIcon, sOpenIcon);

	// setup default property values
	this.src = sXmlSrc;
	this.loading = false;
	this.loaded = false;
	this.errorText = "";

	// check start state and load if open
	
	if (this.open){
	
		_startLoadXmlTree(this.src, this);
				
	}else {
		// and create loading item if not
		this._loadingItem = new WebFXTreeItem(webFXTreeConfig.loadingText);
		this.add(this._loadingItem);
	}
}

WebFXLoadTreeItem.prototype = new WebFXTreeItem;

// override the expand method to load the xml file
WebFXLoadTreeItem.prototype._webfxtreeitem_expand = WebFXTreeItem.prototype.expand;
WebFXLoadTreeItem.prototype.expand = function() {
	if (!this.loaded && !this.loading) {
		// load
		_startLoadXmlTree(this.src, this);
	}
	this._webfxtreeitem_expand();
};

// reloads the src file if already loaded
WebFXLoadTree.prototype.reload =
WebFXLoadTreeItem.prototype.reload = function () {
	// if loading do nothing
	if (this.loaded) {
		var open = this.open;
		// remove
		while (this.childNodes.length > 0)
			this.childNodes[this.childNodes.length - 1].remove();

		this.loaded = false;

		this._loadingItem = new WebFXTreeItem(webFXTreeConfig.loadingText);
		this.add(this._loadingItem);

		if (open)
			this.expand();
	}
	else if (this.open && !this.loading)
		_startLoadXmlTree(this.src, this);
};

/*
 * Helper functions
 */

// creates the xmlhttp object and starts the load of the xml document
function _startLoadXmlTree(sSrc, jsNode) {
	if (jsNode.loading || jsNode.loaded)
		return;
	if (webFXTreeConfig.usePersistence) {
		webFXTreeHandler.cookies.setCookie(jsNode.id.substr(18,jsNode.id.length - 18), '1');
	}
//	webFXTreeHandler.cookies.setCookie(jsNode.sSrc,true);
	jsNode.loading = true;
	var xmlHttp = XmlHttp.create();
	xmlHttp.open("POST", sSrc, true);	// async
	xmlHttp.onreadystatechange = function () {
		if (xmlHttp.readyState == 4) {
			if(webFXTreeConfig.isJson){
				_initJsonNodes(xmlHttp.responseText,jsNode);
			}else{
				var childrennodes = _xmlFileLoaded(xmlHttp.responseXML, jsNode);
				if(webFXTreeConfig.isopen){
					if(childrennodes && childrennodes.length> 0){
						for(var i = 0 ; i < childrennodes.length ; i ++){
							_startLoadXmlTree(childrennodes[i].src,childrennodes[i]);
						}
					}
				}
			}
		}
	};
	// call in new thread to allow ui to update
	xmlHttp.send(null);
}

function _startLoadXmlTreeItem(sSrc, jsNode) {
	if (jsNode.loading || jsNode.loaded)
		return;
		
	jsNode.loading = true;
	var xmlHttp = XmlHttp.create();
	
	xmlHttp.open("GET", sSrc, true);	// async
	xmlHttp.onreadystatechange = function () {
		if (xmlHttp.readyState == 4) {		 
			_xmlFileLoaded(xmlHttp.responseXML, jsNode);
		}
	};
	// call in new thread to allow ui to update
	window.setTimeout(function () {
		xmlHttp.send(null);
	}, 10);
}

// Converts an xml tree to a js tree. See article about xml tree format
function _xmlTreeToJsTree(oNode) {
	// retreive attributes
	var text = oNode.getAttribute("text");
	var action = oNode.getAttribute("action");
	var parent = null;
	var icon = oNode.getAttribute("icon");
	var openIcon = oNode.getAttribute("openIcon");
	//majianwei modify
	if(null !=oNode.getAttribute("closeUrl")){
		icon = base+ oNode.getAttribute("closeUrl");
	}
	if(oNode.getAttribute("openUrl")){
		openIcon = base+ oNode.getAttribute("openUrl");
	}
	var _name = oNode.getAttribute("_name");
	var _nameUri = encodeURIComponent(_name);
	
	var _parentUrl = oNode.getAttribute("_parentUrl");
	var _parentUrl1 = encodeURIComponent(_parentUrl);
	
	var src = oNode.getAttribute("src");
	src += "&name="+_nameUri;
	src += "&parentUrl=" + _parentUrl1;
	var target = oNode.getAttribute("target");
	
	
	
	var isleaf = oNode.getAttribute("isleaf");
	// create jsNode
	var jsNode;
	if (src != null && src != "")
		jsNode = new WebFXLoadTreeItem(text, src, action, parent,isleaf, icon, openIcon);
	else
		jsNode = new WebFXTreeItem(text, action, parent,isleaf, icon, openIcon);

	if (target != "")
		jsNode.target = target;

	// go through childNOdes
	var cs = oNode.childNodes;
	var l = cs.length;
	for (var i = 0; i < l; i++) {
		if (cs[i].tagName == "tree")
			jsNode.add( _xmlTreeToJsTree(cs[i]), true );
	}

	return jsNode;
}

// Inserts an xml document as a subtree to the provided node
function _xmlFileLoaded(oXmlDoc, jsParentNode) {
	if (jsParentNode.loaded)
		return null;
    
	var bIndent = false;
	var bAnyChildren = false;
	jsParentNode.loaded = true;
	jsParentNode.loading = false;

	// check that the load of the xml file went well
	
	
	if( oXmlDoc == null || oXmlDoc.documentElement == null) {
		jsParentNode.errorText = parseTemplateString(webFXTreeConfig.loadErrorTextTemplate,
							jsParentNode.src);
				
	}
	else {
		// there is one extra level of tree elements
		var root = oXmlDoc.documentElement;

		// loop through all tree children
		var cs = root.childNodes;
		var l = cs.length;
		
		for (var i = 0; i < l; i++) {
			if (cs[i].tagName == "tree") {
				bAnyChildren = true;
				bIndent = true;
				jsParentNode.add( _xmlTreeToJsTree(cs[i]), true);
			}
		}
		
		// if no children we got an error
		if (!bAnyChildren){
			jsParentNode.errorText = parseTemplateString(webFXTreeConfig.emptyErrorTextTemplate,
										jsParentNode.src);
		}
	}

	// remove dummy
	if (jsParentNode._loadingItem != null) {
		jsParentNode._loadingItem.remove();
		bIndent = true;
	}

	if (bIndent) {
		// indent now that all items are added
		jsParentNode.indent();
	}

	// show error in status bar
	if (jsParentNode.errorText != "")
		window.status = jsParentNode.errorText;
	if(jsParentNode.childNodes){
		return jsParentNode.childNodes;
	}
}

// parses a string and replaces %n% with argument nr n
function parseTemplateString(sTemplate) {
	var args = arguments;
	var s = sTemplate;

	s = s.replace(/\%\%/g, "%");

	for (var i = 1; i < args.length; i++)
		s = s.replace( new RegExp("\%" + i + "\%", "g"), args[i] )

	return s;
}
//
//function _xmlFileLoaded(elements, jsParentNode) {
//	if (jsParentNode.loaded)
//		return ;
//    
//	var bIndent = false;
//	var bAnyChildren = false;
//	jsParentNode.loaded = true;
//	jsParentNode.loading = false;
//
//	var l = elements.length;
//	
//	for (var i = 0; i < l; i++) {
//		if (elements[i].tagName == "tree") {
//			bAnyChildren = true;
//			bIndent = true;
//			jsParentNode.add( _xmlTreeToJsTree(elements[i]), true);
//			var cs = jsParentNode.childNodes;
//			var cx = elements[i].childNodes;
//			if(cs.length > 0){
//				for(var cxi = 0 ; cxi < cx.length; cxi++){
//					_xmlFileLoaded(cs, cx[cxi])
//				}
//			}
//		}
//	}
//	
//	// if no children we got an error
//	if (!bAnyChildren){
//		jsParentNode.errorText = parseTemplateString(webFXTreeConfig.emptyErrorTextTemplate,
//									jsParentNode.src);
//	}
//
//	// remove dummy
//	if (jsParentNode._loadingItem != null) {
//		jsParentNode._loadingItem.remove();
//		bIndent = true;
//	}
//
//	if (bIndent) {
//		// indent now that all items are added
//		jsParentNode.indent();
//	}
//
//	// show error in status bar
//	if (jsParentNode.errorText != "")
//		window.status = jsParentNode.errorText;
//}
//function _startLoadXmlTree(sSrc, jsNode) {
//	if (jsNode.loading || jsNode.loaded)
//		return;
//	if (webFXTreeConfig.usePersistence) {
//		webFXTreeHandler.cookies.setCookie(jsNode.id.substr(18,jsNode.id.length - 18), '1');
//	}
////	webFXTreeHandler.cookies.setCookie(jsNode.sSrc,true);
//	jsNode.loading = true;
//	var xmlHttp = XmlHttp.create();
//	
//	xmlHttp.open("POST", sSrc, true);	// async
//	xmlHttp.onreadystatechange = function () {
//		if (xmlHttp.readyState == 4) {
//		 	var oXmlDoc = xmlHttp.responseXML;
//		 	if( oXmlDoc == null || oXmlDoc.documentElement == null) {
//				jsParentNode.errorText = parseTemplateString(webFXTreeConfig.loadErrorTextTemplate,
//									jsParentNode.src);
//						
//			}
//			else {
//				// there is one extra level of tree elements
//				var root = oXmlDoc.documentElement;
//		
//				// loop through all tree children
//				var cs = root.childNodes;
//				_xmlFileLoaded(cs, jsNode);
//				
//			}
//		}
//	};
//	// call in new thread to allow ui to update
//	xmlHttp.send(null);
//	
//}

function _initJsonNodes(testnode,node){
	var startDate = new Date();
	// alert(testnode);
	var jsNode;
	var jsonNodeObj  =  eval(testnode);
	// alert(jsonNodeObj);
	// alert(jsonNodeObj.length);
	
	var startEval = new Date() -  startDate;
	
	var divArray = new Array();
	var i = 0;
    while(i<jsonNodeObj.length){
    	if(jsonNodeObj[i].parentId == node.dprtid){
			var dprtid= jsonNodeObj[i].dprtid;
	      	var text  = jsonNodeObj[i].name;
	      	var isleaf= jsonNodeObj[i].isLeaf;
	      	var parent= jsonNodeObj[i].parentId;
	      	var state = jsonNodeObj[i].state;
	      	
			var icon = webFXTreeConfig.fileIcon;
			var action = null;
			var openIcon = null;
			var src = null;
			var target = null;
			jsNode = new WebFXTreeItem(text,null, action, parent,isleaf, icon, openIcon);
		    /*
			if(isleaf != 1){
		    	alert(jsonNodeObj[i].list.length);
		    	// _initJsonNodes(testnode,jsNode);
		    }
			*/
	       node.add(jsNode);
	       divArray.put(jsNode.toString());
	     //  node.div += jsNode.toString();
       	}
       	i++;
    }
    node.div += divArray.toString();
    //alert(node.div);
    var endDate  = new Date()-startDate;
     // alert("循环时间: " + endDate + " eval  时间: " + startEval);
}
