
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <link href="/Css/default/easyui.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/Css/wu.css" />
<link rel="stylesheet" type="text/css" href="/Css/icon.css" />
<link href="/Css/header.css" rel="stylesheet" />
	<!-- begin of header -->
	<div class="wu-header" data-options="region:'north',border:false,split:true">
    	<div class="wu-header-left">
        	<a target="_self" href='/default.jsp'><h1>医知网后台管理</h1></a>
        </div>
        <div class="wu-header-right">
			<div class="sitetop">

			
					        <div class="loginleft">
					            <span id="username"></span>,欢迎来到MedPdf网站！
					            <label style="display: none" id="tips"></label>
					             <span class="accountbox"><span id="accountHead"></span></span>
					        </div>
					        <div class="loginright">			                    
					                <div id="mycenter" class="easyui-panel"><a class="easyui-menubutton" data-options="menu:'#dropdown_menu',iconCls:'icon-users'" class="active" href="/MyCenter/Myinfo.jsp">个人中心 </a> | <a id='login' class="easyui-linkbutton" data-options="plain:true" href="/Member/login.jsp">登&nbsp;&nbsp;录</a>
					                </div>
					                    <div id="dropdown_menu" class="menu">
					                        <div class="alt"><a href="/MyCenter/MyResourse.jsp">资源管理</a></div>
					                        <div class="alt"><a href="/MyCenter/MyAccount.jsp">账务中心</a></div>
					                        <div class="alt"><a href="/MyCenter/ChangePassword.jsp">修改密码</a></div>
					                    </div>
					        </div>
			            
			      </div>
			</div>
        
    </div>
    <script type="text/javascript">
    		$(function(){
			checklogin();	
		})
    </script>
    <!-- end of header -->