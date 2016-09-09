
/*
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2009 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 *
 * Scripts related to the Image dialog window (see fck_image.html).
 */
var dialog = window.parent;
var oEditor = dialog.InnerDialogLoaded();
var FCK = oEditor.FCK;
var FCKLang = oEditor.FCKLang;
var FCKConfig = oEditor.FCKConfig;
var FCKDebug = oEditor.FCKDebug;
var FCKTools = oEditor.FCKTools;
var FCKDialog = oEditor.FCKDialog;
var bImageButton = (document.location.search.length > 0 && document.location.search.substr(1) == "ImageButton");
var oImageOriginal;
//#### Dialog Tabs

// Set the dialog tabs.
//dialog.AddTab( 'Info', FCKLang.DlgImgInfoTab ) ;

//if ( !bImageButton && !FCKConfig.ImageDlgHideLink )
//	dialog.AddTab( 'Link', FCKLang.DlgImgLinkTab ) ;

//if ( FCKConfig.ImageUpload )
//	dialog.AddTab( 'Upload', FCKLang.DlgLnkUpload ) ;

//if ( !FCKConfig.ImageDlgHideAdvanced )
//	dialog.AddTab( 'Advanced', FCKLang.DlgAdvancedTag ) ;

// Function called when a dialog tag is selected.
function OnDialogTabChange(tabCode) {
	ShowE("divInfo", (tabCode == "Info"));
	//ShowE('divLink'		, ( tabCode == 'Link' ) ) ;
	ShowE("divUpload", (tabCode == "Upload"));
	//ShowE('divAdvanced'	, ( tabCode == 'Advanced' ) ) ;
}

// Get the selected image (if available).
var oImage = dialog.Selection.GetSelectedElement();
if (oImage && oImage.tagName != "IMG" && !(oImage.tagName == "INPUT" && oImage.type == "image")) {
	oImage = null;
}

// Get the active link.
var oLink = dialog.Selection.GetSelection().MoveToAncestorNode("A");
var oImageOriginal;
var bPreviewInitialized;
window.onload = function () {
	oEditor.FCKLanguageManager.TranslatePage(document);
	if (FCKConfig.ImageUpload) {
		GetE("frmUpload").action = FCKConfig.ImageUploadURL;
	}
	dialog.SetAutoSize(true);
};

//#### The OK button was hit.
function Ok() {
	if (GetE("txtUrl").value.length == 0) {
		dialog.SetSelectedTab("divUpload");
		GetE("txtUrl").focus();
		alert(FCKLang.DlgImgAlertUrl);
		return false;
	}
	oEditor.FCKUndo.SaveUndoStep();
	oImage = FCK.InsertElement("img");
	UpdateImage(oImage);
	Cancel();
	return true;
}
function UpdateImage(e, skipId) {
	e.src = GetE("txtUrl").value;
	SetAttribute(e, "_fcksavedurl", GetE("txtUrl").value);
	if (typeof (oImageOriginal.width) != "undefined" && typeof (oImageOriginal.height) != "undefined") {
		e.width = oImageOriginal.width;
		e.height = oImageOriginal.height;
	}
}
var bLockRatio = true;
function SetUrl(url, width, height, alt) {
	GetE("txtUrl").value = url;
}
function OnUploadCompleted(errorNumber, fileUrl, fileName, customMsg) {
	// Remove animation
	//window.parent.Throbber.Hide();
	//GetE("divUpload").style.display = "";
	switch (errorNumber) {
	  case 0:	// No errors
		break;
	  case 1:	// Custom error
		GetE("uploadMessage").innerHTML = customMsg;
		break;
	  case 202:
		GetE("uploadMessage").innerHTML = "\u9519\u8bef\u7684\u6587\u4ef6\u7c7b\u578b";
		break;
	  case 203:
		GetE("uploadMessage").innerHTML = "\u670d\u52a1\u5668\u9519\u8bef";
		break;
	  default:
		break;
	}
	if (errorNumber != 0) {
		window.parent.Throbber.Hide();
		GetE("divUpload").style.display = "";
		return;
	}
	oImageOriginal = document.createElement("IMG");	// new Image() ;
	oImageOriginal.src = fileUrl;
	oImageOriginal.onload = DrawImage;
	oImageOriginal.onerror = Cancel;
	SetUrl(fileUrl);
	GetE("frmUpload").reset();
}
var oUploadAllowedExtRegex = new RegExp(FCKConfig.ImageUploadAllowedExtensions, "i");
var oUploadDeniedExtRegex = new RegExp(FCKConfig.ImageUploadDeniedExtensions, "i");
function CheckUpload() {
	var sFile = GetE("txtUploadFile").value;
	var filename = sFile.substring(sFile.lastIndexOf("\\") + 1, sFile.length);
	if (sFile.length == 0) {
		return false;
	}
	if ((FCKConfig.ImageUploadAllowedExtensions.length > 0 && !oUploadAllowedExtRegex.test(sFile)) || (FCKConfig.ImageUploadDeniedExtensions.length > 0 && oUploadDeniedExtRegex.test(sFile))) {
		OnUploadCompleted(202);
		return false;
	}

	// Show animation
	window.parent.Throbber.Show(10);
	GetE("divUpload").style.display = "none";
	return true;
}
function DrawImage() {
	var Fwidth = 380;
	var Fheight = 200;
	var tmpwidth = this.width;
	var tmpheight = this.height;
	if (this.width > 0 && this.height > 0) {
		if (this.width / this.height >= Fwidth / Fheight) {
			if (this.width > Fwidth) {
				this.width = Fwidth;
				this.height = (tmpheight * Fwidth) / tmpwidth;
			}
		} else {
			if (this.height > Fheight) {
				this.height = Fheight;
				this.width = (tmpwidth * Fheight) / tmpheight;
			}
		}
	}
	Ok();
}
function Cancel() {
	dialog.CloseDialog();
}

