/*
 * 验证方法。
 */
Validator = {
	Require : /.+/,// 必填项
	Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,// email
	Phone : /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,// 电话
	Mobile : /^((\(\d{2,3}\))|(\d{3}\-))?1[3,5]\d{9}$/,// 手机
	Url : /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,// http地址。
	IdCard : /^\d{15}(\d{2}[A-Za-z0-9])?$/,// 身份证
	Currency : /^\d+(\.\d+)?$/,// 数字,包含小数，不包含负数
	Number : /^\d+$/,// 数字不包含小数，不包含负数
	Zip : /^[1-9]\d{5}$/,// 邮编
	QQ : /^[1-9]\d{4,12}$/,// qq号，4-12位的数字。
	Integer : /^[-\+]?\d+$/,// 整数
	Double : /^[-\+]?\d+(\.\d+)?$/,// 小数
	English : /^[A-Za-z]+$/,// 字母
	Chinese : /^[\u0391-\uFFE5]+$/,// 中文
	Username : /^[a-z]\w{3,}$/i,// 以小写字母开头的至少4位的，只包含数字，大小写字母的字符串。
	// add 2008.12.8
	NoSign : /^[0-9A-Za-z\u0391-\uFFE5]+$/,// 值包含数字，字母，中文
	// add 2008.12.15
	IsHTML : /^<(S*?)[^>]*>.*?|<.*?\/>/,
	UnHTML : function(str) {// 不包含html标签。
		return !this.IsHTML.test(str);
	},
	UnSafe : /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,// 少于6位字符，或者包含特殊字符的字符串。
	IsSafe : function(str) {// 大于等于6位的不包含特殊字符的字符串。
		return !this.UnSafe.test(str);
	},
	UnHTMLString : "this.UnHTML(value)",
	SafeString : "this.IsSafe(value)",
	Filter : "this.DoFilter(value, getAttribute('accept'))",
	Limit : "this.limit(value.length,getAttribute('min'),  getAttribute('max'))",
	LimitB : "this.limit(this.LenB(value), getAttribute('min'), getAttribute('max'))",
	Date : "this.IsDate(value, getAttribute('min'), getAttribute('format'))",
	Time : "this.IsTime(value)",
	OTime : "this.OnTime(value)",
	Repeat : "value == document.getElementsByName(getAttribute('to'))[0].value",
	Range : "getAttribute('min') < (value|0) && (value|0) < getAttribute('max')",
	Compare : "this.compare(value,getAttribute('operator'),getAttribute('to'))",
	Custom : "this.Exec(value, getAttribute('regexp'))",
	Group : "this.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))",
	// add 2008.11.28
	ByteLength : "this.CheckLength(value,getAttribute('min'), getAttribute('max'))",
	ErrorItem : [document.forms[0]],
	ErrorMessage : ["以下原因导致失败:\t\t"],
	Validate : function(theForm, mode) {
		var obj = theForm || event.srcElement;
		var count = obj.elements.length;
		this.ErrorMessage.length = 1;
		this.ErrorItem.length = 1;
		this.ErrorItem[0] = obj;
		for (var i = 0; i < count; i++) {
			with (obj.elements[i]) {
				var _dataType = getAttribute("dataType");
				if (typeof(_dataType) == "object"
						|| typeof(this[_dataType]) == "undefined")
					continue;
				this.ClearState(obj.elements[i]);
				if (value == "") {
					if (getAttribute("require") == "true"||getAttribute("require")=="require"||_dataType=="Require")
						this.AddError(i, getAttribute("msg"));
					continue;
				}
				switch (_dataType) {
					case "Date" :
					case "Time" :
					case "OTime" :
					case "Repeat" :
					case "Range" :
					case "Compare" :
					case "Custom" :
					case "Group" :
					case "Limit" :
					case "LimitB" :
					case "SafeString" :
						// add 2008.11.28
					case "ByteLength" :
						// add 2008.12.15
					case "UnHTMLString" :
					case "Filter" :
						if (!eval(this[_dataType])) {
							this.AddError(i, getAttribute("msg"));
						}
						break;
					default :
						if (!this[_dataType].test(value)) {
							this.AddError(i, getAttribute("msg"));
						}
						break;
				}
			}
		}
		if (this.ErrorMessage.length > 1) {
			mode = mode || 1;
			var errCount = this.ErrorItem.length;
			switch (mode) {
				case 2 :
					for (var i = 1; i < errCount; i++) {
						this.ErrorItem[i].style.color = "red";
					}
				case 1 :
					alert(this.ErrorMessage.join("\n"));
					this.ErrorItem[1].focus();
					break;
				case 3 :
					for (var i = 1; i < errCount; i++) {
						try {
							var span = document.createElement("SPAN");
							span.id = "__ErrorMessagePanel";
							span.style.color = "red";
							this.ErrorItem[i].parentNode.appendChild(span);
							span.innerHTML = this.ErrorMessage[i].replace(
									/\d+:/, "<br>*");
						} catch (e) {
							alert(e.description);
						}
					}
					this.ErrorItem[1].focus();
					break;
				default :
					alert(this.ErrorMessage.join("\n"));
					break;
			}
			return false;
		}
		return true;
	},
	limit : function(len, min, max) {
		min = min || 0;
		max = max || Number.MAX_VALUE;
		return min <= len && len <= max;
	},
	LenB : function(str) {
		return str.replace(/[^\x00-\xff]/g, "**").length;
	},
	ClearState : function(elem) {
		with (elem) {
			if (style.color == "red")
				style.color = "";
			var lastNode = parentNode.childNodes[parentNode.childNodes.length
					- 1];
			if (lastNode.id == "__ErrorMessagePanel")
				parentNode.removeChild(lastNode);
		}
	},
	compare : function(op1, operator, op2) {
		switch (operator) {
			case "NotEqual" :
				return (op1 != op2);
			case "GreaterThan" :
				return (op1 > op2);
			case "GreaterThanEqual" :
				return (op1 >= op2);
			case "LessThan" :
				return (op1 < op2);
			case "LessThanEqual" :
				return (op1 <= op2);
			default :
				return (op1 == op2);
		}
	},
	MustChecked : function(name, min, max) {
		var groups = document.getElementsByName(name);
		var hasChecked = 0;
		min = min || 1;
		max = max || groups.length;
		for (var i = groups.length - 1; i >= 0; i--)
			if (groups[i].checked)
				hasChecked++;
		return min <= hasChecked && hasChecked <= max;
	},
	DoFilter : function(input, filter) {
		return new RegExp("^.+\.(?=EXT)(EXT)$".replace(/EXT/g, filter
								.split(/\s*,\s*/).join("|")), "gi").test(input);
	},
	AddError : function(index, str) {
		this.ErrorItem[this.ErrorItem.length] = this.ErrorItem[0].elements[index];
		this.ErrorMessage[this.ErrorMessage.length] = this.ErrorMessage.length
				+ ":" + str;
	},
	Exec : function(op, reg) {
		var b;
		if (typeof reg == "string") {
			b = new RegExp(reg, "g").test(op);
		} else {
			b = reg.test(op);
		}
		return b;
	},
	IsDate : function(op, formatString) {
		formatString = formatString || "ymd";
		var m, year, month, day;
		switch (formatString) {
			case "ymd" :
				m = op
						.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
				if (m == null) {
					return false;
				}
				day = m[6];
				month = m[5] * 1;
				year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3],
						10));
				break;
			case "dmy" :
				m = op
						.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
				if (m == null) {
					return false;
				}
				day = m[1];
				month = m[3] * 1;
				year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6],
						10));
				break;
			default :
				break;
		}
		if (!parseInt(month))
			return false;
		month = month == 0 ? 12 : month;
		var date = new Date(year, month - 1, day);
		return (typeof(date) == "object" && year == date.getFullYear()
				&& month == (date.getMonth() + 1) && day == date.getDate());
		function GetFullYear(y) {
			return ((y < 30 ? "20" : "19") + y) | 0;
		}
	},
	IsTime : function(str) {
		var r = str
				.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
		if (r == null)
			return false;
		var d = new Date(r[1], r[3] - 1, r[4], r[5], r[6], r[7]);
		return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3]
				&& d.getDate() == r[4] && d.getHours() == r[5]
				&& d.getMinutes() == r[6] && d.getSeconds() == r[7]);
	},
	OnTime : function(str) {
		var r = str.match(/^(\d{1,2}):(\d{1,2}):(\d{1,2})$/);
		if (r == null)
			return false;
		return (0 <= r[1] && r[1] < 24 && 0 <= r[2] && r[2] < 60 && 0 <= r[3] && r[3] < 60);
	},
	// 2008.11.28
	CheckLength : function(str, min, max, msg) {
		var len = str.replace(/[^\x00-\xff]/g, "**").length;
		var b = len >= min && len <= max;
		if (!b && typeof msg != "undefined" && msg.length > 0) {
			alert(msg);
		}
		return b;
	}
}
