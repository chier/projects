<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
    <head>
        <title>登录超时</title>
        <link href="${base}/css/main.css" rel="stylesheet" type="text/css" media="all" />
	<script type='text/javascript' src='${base}/dwr/util.js'></script>
	<script type="text/javascript">
            ;
	function closeWindow(){
			top.opener=null;
			top.open('','_self');
			window.top.close();
	}
	function gotoIndex(){
            if(top!=window)
	    {
		top.location.href="${base}/";
	    }else{
		document.location.href="${base}/";
	    }
	}
	window.onload=function(){
		var remain=5;
		go=function(){
			if(remain>0){
				document.getElementById("second").innerHTML=remain;
				remain--;
				setTimeout(go,1000);
			}
			else{
				gotoIndex();
			}
		};
		go();
	}
    </script>
    <style type="text/css">

#outtime {
	background-image: url(../images/mess.gif);
	background-repeat: no-repeat;
	background-position: 0px 0px;
	padding-left:80px;
	padding-top:10px;
	padding-bottom:10px;
	text-align:left;
}
    </style>
    <head>
        <title>登录超时</title>
    </head>
<body style="text-align:center;">
    <div style="margin:auto; height:120px; width:320px; margin-top:100px; border: 2px solid #EBEADE; padding:40px; font-size: 14px; color: #F98F02">
      <div id="outtime">对不起，您的登录已超时。如需继续操作，请重新登录</div>
            <p style="margin-top:60px;"><span id="second"></span>秒后自动跳到首页</p>
</div>
        <p style="margin-top:10px;"><input type="button" value="返回首页" onclick="gotoIndex();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="关闭" onclick="closeWindow();"/></p>
</body>
</html>
