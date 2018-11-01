<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script  type="text/javascript" src="/Js/jquery-1.9.1.js" ></script>
<script type="text/javascript" src="/Js/jquery.easyui.min.js"></script>
<link href="/Css/default/easyui.css" rel="stylesheet" />
<link href="/Css/demo.css" rel="stylesheet" />
<link href="/Css/icon.css" rel="stylesheet" />
</head>
<body>
	<h2>Toolbar and Buttons</h2>
	<p>The toolbar and buttons can be added to dialog.</p>
	<div style="margin:20px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('open')">Open</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('close')">Close</a>
	</div>
	<div id="dlg" class="easyui-dialog"  title="Toolbar and Buttons" style="width:400px;height:200px;padding:10px"
			data-options="
				iconCls: 'icon-save',
				closed:true,
				buttons: [{
					text:'Ok',
					iconCls:'icon-ok',
					handler:function(){
						alert('ok');
					}
				},{
					text:'Cancel',
					handler:function(){
						alert('cancel');;
					}
				}]
			">
		The dialog content.
	</div>
	
</body>
</html>