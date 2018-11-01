<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/Master/header.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="<%=path%>/Css/default/easyui.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=path%>/Css/wu.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/Css/icon.css" />
<link href="<%=path%>/Css/header.css" rel="stylesheet" />

</head>
<body class="easyui-layout">
	<!-- begin of header -->
	<div class="wu-header" data-options="region:'north',border:false,split:true">
    	<div class="wu-header-left">
        	<a target="_self" href='/index.jsp'><h1>医知网后台管理</h1></a>
        </div>
        <div class="wu-header-right">
			<div class="sitetop">

			
					        <div class="loginleft">
					            <span id="username"></span>,欢迎来到MedPdf网站！
					            <label style="display: none" id="tips"></label>
					             <span class="accountbox"><span id="accountHead"></span></span>
					        </div>
					        <div class="loginright">			                    
					                <div id="mycenter" class="easyui-panel"><a class="easyui-menubutton" data-options="menu:'#dropdown_menu',iconCls:'icon-users'" class="active" href="/MyCenter/Myinfo.jsp">个人中心 </a> | <a id='login' class="easyui-linkbutton" data-options="plain:true" href="<%=path %>/Member/login.jsp">登&nbsp;&nbsp;录</a>
					                </div>
					                    <div id="dropdown_menu" class="menu">
					                        <div class="alt"><a href="<%=path%>/MyCenter/MyResourse.jsp">资源管理</a></div>
					                        <div class="alt"><a href="<%=path%>/MyCenter/MyAccount.jsp">账务中心</a></div>
					                        <div class="alt"><a href="<%=path%>/MyCenter/ChangePassword.jsp">修改密码</a></div>
					                    </div>
					        </div>
			            
			      </div>
			</div>
        
    </div>
    <!-- end of header -->
    <!-- begin of sidebar -->
	<div class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'"> 
    	<div class="easyui-accordion" data-options="border:false,fit:true"> 
        	<div title="用户管理" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-users" data-link="/User/usermanage.jsp" iframe="0">用户管理</a></li>

                </ul>
            </div>           
            <div title="账户管理" data-options="iconCls:'icon-creditcards'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                	<li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="/Account/acclist.jsp" iframe="0">账户管理</a></li>
                   
                </ul>
            </div>
            <div title="资源管理" data-options="iconCls:'icon-application-form-edit'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                	<li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="/ResManage/reslist.jsp" iframe="0">资源列表</a></li>

                </ul>
            </div>
        </div>
    </div>	
    <!-- end of sidebar -->    
    <!-- begin of main -->
    <div class="wu-main" data-options="region:'center'">
        <div id="wu-tabs" class="easyui-tabs" data-options="border:false,fit:true">  
            <div title="首页" data-options="closable:false,iconCls:'icon-tip',cls:'pd3'"></div>
        </div>
    </div>
    <!-- end of main --> 
    <!-- begin of footer -->
	<div class="wu-footer" data-options="region:'south',border:true,split:true">
    	&copy; 2013 Wu All Rights Reserved
    </div>
    <!-- end of footer --> 

   <script type="text/javascript">
		$(function(){
			$('.wu-side-tree a').bind("click",function(){
				var title = $(this).text();
				var url = $(this).attr('data-link');
				var iconCls = $(this).attr('data-icon');
				var iframe = $(this).attr('iframe')==1?true:false;
				addTab(title,url,iconCls,iframe);
			});	
			checklogin();	
		})
		
		/**
		* Name 载入树形菜单 
		*/
		$('#wu-side-tree').tree({
			url:'temp/menu.php',
			cache:false,
			onClick:function(node){
				var url = node.attributes['url'];
				if(url==null || url == ""){
					return false;
				}
				else{
					addTab(node.text, url, '', node.attributes['iframe']);
				}
			}
		});
		
		/**
		* Name 选项卡初始化
		*/
		$('#wu-tabs').tabs({
			tools:[{
				iconCls:'icon-reload',
				border:false,
				handler:function(){
					$(".panel-body").panel('refresh'); 
				}
			}]
		});
			
		/**
		* Name 添加菜单选项
		* Param title 名称
		* Param href 链接
		* Param iconCls 图标样式
		* Param iframe 链接跳转方式（true为iframe，false为href）
		*/	
		function addTab(title, href, iconCls, iframe){
			var tabPanel = $('#wu-tabs');
			if(!tabPanel.tabs('exists',title)){
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
				if(iframe){
					tabPanel.tabs('add',{
						title:title,
						content:content,
						iconCls:iconCls,
						fit:true,
						cls:'pd3',
						closable:true
					});
				}
				else{
					tabPanel.tabs('add',{
						title:title,
						href:href,
						iconCls:iconCls,
						fit:true,
						cls:'pd3',
						closable:true
					});
				}
			}
			else
			{
				tabPanel.tabs('select',title);
			}
		}
		/**
		* Name 移除菜单选项
		*/
		function removeTab(){
			var tabPanel = $('#wu-tabs');
			var tab = tabPanel.tabs('getSelected');
			if (tab){
				var index = tabPanel.tabs('getTabIndex', tab);
				tabPanel.tabs('close', index);
			}
		}
	</script>
	
	
	
</body>

</html>