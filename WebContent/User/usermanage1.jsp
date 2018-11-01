<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="no-js">
<%@ include file="/Master/header.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
 <meta name="description" content="这是一个 index 页面">
  <meta name="keywords" content="index">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  
  
  
  <link rel="icon" type="image/png" href="/assets/i/favicon.png">
  <link rel="apple-touch-icon-precomposed" href="/assets/i/app-icon72x72@2x.png">
  <meta name="apple-mobile-web-app-title" content="Amaze UI" />
  <link rel="stylesheet" href="<%=path %>/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="<%=path %>/assets/css/admin.css">
  
  <link href="<%=path %>/Css/jquery-ui.css" rel="stylesheet" />
<link href="<%=path %>/Css/classlist.css?v=20171223" rel="stylesheet" />
<link href="<%=path %>/Css/user-manage.css" rel="stylesheet" />
<link href="<%=path %>/Css/page.css?v=20171214" rel="stylesheet" />
<link href="<%=path %>/Css/bootstrap.css?v=20161214" rel="stylesheet" />
<link href="<%=path %>/Css/jquery-ui.css" rel="stylesheet" />
<link href="<%=path %>/Css/jquery-ui-timepicker-addon.css" rel="stylesheet" />
<link href="<%=path %>/Css/demo.css" rel="stylesheet" />
<link href="<%=path %>/Css/icon.css" rel="stylesheet" />
<link href="<%=path %>/Css/default/easyui.css"" rel="stylesheet" />

<script  type="text/javascript" src="<%=path %>/Js/jquery-1.9.1.js" ></script>
<script src="<%=path %>/Js/jquery-ui.js"></script>
<script src="<%=path %>/Js/jquery.myPagination.js"></script>
<script src="<%=path %>/Js/Disculist.js"></script>
<script src="<%=path %>/Js/jquery-ui-timepicker-addon.min.js"></script>
<script src="<%=path %>/Js/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="<%=path %>/Js/jquery.easyui.min.js"></script>
</head>
	    <script type="text/javascript">

        	
	
		var occus = [
		    {occuid:'FI-SW-01',name:'Koi'},
		    {occuid:'K9-DL-01',name:'Dalmation'},
		    {occuid:'RP-SN-01',name:'Rattlesnake'},
		    {occuid:'RP-LI-02',name:'Iguana'},
		    {occuid:'FL-DSH-01',name:'Manx'},
		    {occuid:'FL-DLH-02',name:'Persian'},
		    {occuid:'AV-CB-01',name:'Amazon Parrot'}
		];
		$(function(){
			$('#tt').datagrid({
				title:'Editable DataGrid',
				iconCls:'icon-edit',
				width:660,
				height:250,
				singleSelect:true,
				idField:'loginid',
				url: '<%=path%>/GetUserList',
				pagination: true,//表示在datagrid设置分页              
				rownumbers: true,
				singleSelect: true,
				columns:[[
					{field:'loginid',title:'Item ID',width:60},
					{field:'productid',title:'Product',width:100,
						formatter:function(value){
							for(var i=0; i<products.length; i++){
								if (products[i].productid == value) return products[i].name;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'productid',
								textField:'name',
								data:products,
								required:true
							}
						}
					},
					{field:'listprice',title:'List Price',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}},
					{field:'unitcost',title:'Unit Cost',width:80,align:'right',editor:'numberbox'},
					{field:'attr1',title:'Attribute',width:180,editor:'text'},
					{field:'status',title:'Status',width:50,align:'center',
						editor:{
							type:'checkbox',
							options:{
								on: 'P',
								off: ''
							}
						}
					},
					{field:'action',title:'Action',width:80,align:'center',
						formatter:function(value,row,index){
							if (row.editing){
								var s = '<a href="#" onclick="saverow(this)">Save</a> ';
								var c = '<a href="#" onclick="cancelrow(this)">Cancel</a>';
								return s+c;
							} else {
								var e = '<a href="#" onclick="editrow(this)">Edit</a> ';
								var d = '<a href="#" onclick="deleterow(this)">Delete</a>';
								return e+d;
							}
						}
					}
				]],
				onBeforeEdit:function(index,row){
					row.editing = true;
					updateActions(index);
				},
				onAfterEdit:function(index,row){
					row.editing = false;
					updateActions(index);
				},
				onCancelEdit:function(index,row){
					row.editing = false;
					updateActions(index);
				}
			});
		});
		function updateActions(index){
			$('#tt').datagrid('updateRow',{
				index: index,
				row:{}
			});
		}
		function getRowIndex(target){
			var tr = $(target).closest('tr.datagrid-row');
			return parseInt(tr.attr('datagrid-row-index'));
		}
		function editrow(target){
			$('#tt').datagrid('beginEdit', getRowIndex(target));
		}
		function deleterow(target){
			$.messager.confirm('Confirm','Are you sure?',function(r){
				if (r){
					$('#tt').datagrid('deleteRow', getRowIndex(target));
				}
			});
		}
		function saverow(target){
			$('#tt').datagrid('endEdit', getRowIndex(target));
		}
		function cancelrow(target){
			$('#tt').datagrid('cancelEdit', getRowIndex(target));
		}
		function insert(){
			var row = $('#tt').datagrid('getSelected');
			if (row){
				var index = $('#tt').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#tt').datagrid('insertRow', {
				index: index,
				row:{
					status:'P'
				}
			});
			$('#tt').datagrid('selectRow',index);
			$('#tt').datagrid('beginEdit',index);
		}
	</script>
<script type="text/javascript">
	(function($) {

		$(function() {
			$.datepicker.regional['zh-CN'] = {
				changeMonth : true,
				changeYear : true,
				clearText : '清除',
				clearStatus : '清除已选日期',
				closeText : '关闭',
				closeStatus : '不改变当前选择',
				prevText : '<上月',
				prevStatus : '显示上月',
				prevBigText : '<<',
						prevBigStatus: '显示上一年',
						nextText: '下月>',
				nextStatus : '显示下月',
				nextBigText : '>>',
				nextBigStatus : '显示下一年',
				currentText : '今天',
				currentStatus : '显示本月',
				monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月',
						'九月', '十月', '十一月', '十二月' ],
				monthNamesShort : [ '1', '2', '3', '4', '5', '6', '7', '8',
						'9', '10', '11', '12' ],
				monthStatus : '选择月份',
				yearStatus : '选择年份',
				weekHeader : '周',
				weekStatus : '年内周次',
				dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
				dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五', '周六' ],
				dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
				dayStatus : '设置 DD 为一周起始',
				dateStatus : '选择 m月 d日, DD',
				dateFormat : 'yy-mm-dd',
				firstDay : 1,
				initStatus : '请选择日期',
				isRTL : false
			};

		});

		
	}(jQuery));

</script>


<body>			


        <div class="listcontent">
				<div class="orderbox date_control">
					共找到&nbsp;<span id="total" style="color: red"></span>&nbsp;条记录               <span
						class="ordercontrol"><div class="am-form-group am-margin-left am-fl">
              <select id="selector">
              <option value="option1">用户名：</option> 
                <option value="option2">用户id:</option>
                <option value="option3">姓名：</option>
              </select>
            </div><input type="text" value=""  id="keys"><button id="searchbtn" class="am-btn am-btn-default" type="button" onclick="getCount();">搜索</button><div class="control-group">
							<input id="StartTime"  value=""/>
						</div> <div class="control-group">至</div>
						<div class="control-group">

							<input id="EndTime"/>
						</div></span>
				</div>
				<div id="discuList" style="height: auto;"></div>
				<div id="foot" style="height: 40px; text-align: center;"></div>

				<div id="Pager" style="width: 800px;"></div>
	    </div>
	    
	    
     	    <!--数据展示 -->

	<table id="tt"></table>

	
</body>
<script>

		//初始化日期时间控件
		$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
		
		$("#StartTime").prop("readonly", false).datepicker({
			showMonthAfterYear : true, // 月在年之后显示      
			changeMonth : true, // 允许选择月份     
			changeYear : true, // 允许选择年份     
			dateFormat : 'yy-mm-dd', // 设置日期格式   
			onClose : function(selectedDate) {
			
			}
		
		});
		
		$("#EndTime").prop("readonly", false).datepicker({
			showMonthAfterYear : true, // 月在年之后显示      
			changeMonth : true, // 允许选择月份     
			changeYear : true, // 允许选择年份     
			dateFormat : 'yy-mm-dd', // 设置日期格式   
			defaultDate:  new Date(),
			setDate:new Date(),
			onClose : function(selectedDate) {
				
			}
		
		}); 


		var totalPage = 0;
		var pageListSize = 10;//每页显示调试
		//var uType = 0;//uType=2管理员
		var startTime = "";
		var endTime = "";
		
		var loginid="";
		var uname="";
		var uid="";
		var where ="";
		var order ="";

		//getCount();
		
		
function getCount(classid){
	
	if($("#selector").val()=="option1"){
		
		loginid = $("#keys").val();
		uname = "";
		uid ="";
	}
	else if($("#selector").val()=="option2"){
		uid = $("#keys").val();
		loginid = "";
		uname="";
		
	}
	else{
		uname = $("#keys").val();
		loginid = "";
		uname="";
	}
	
	 starttime = $("#StartTime").val();
	 endtime = $("#EndTime").val();
	
       $.ajax({
           url: "<%=path%>/GetUserListCount",
           type: 'post',
           async: true,
           contentType: "application/x-www-form-urlencoded; charset=UTF-8",
           dataType: 'text',
           data: {Loginid:loginid,UName:uname,UID:uid,StartTime:starttime,EndTime:endtime},
           success : function(result) {
			 
                   if(result !=0 ){       
					var	pageTotalCount = parseInt(result);//总记录数
						$("#total").html(pageTotalCount);
						totalPage = parseInt(pageTotalCount
								/ pageListSize)
								+ ((pageTotalCount % pageListSize) > 0 ? 1
										: 0);

						//mypagination
						$("#Pager").show();
						$("#Pager").myPagination({
							currPage : 1,
							pageCount : totalPage,
							pageSize : 10
						});
                     }
           else{
           	$("#total").html("0");
           	$("#discuList").html("<div class='no_record'>抱歉！没有找到符合限定条件的记录。</div>");
           	$("#Pager").hide();
                 }	                		                	
				
				},
           error: function () {
               alert("获取记录总数异常！");
               return false;
           }
       });	 
	 
}
//获取用户列表
function getUserList(pageIndex, pageListSize){
	
	if($("#selector").val()=="option1"){
		
		loginid = $("#keys").val();
		uname = "";
		uid ="";
	}
	else if($("#selector").val()=="option2"){
		uid = $("#keys").val();
		loginid = "";
		uname="";
		
	}
	else{
		uname = $("#keys").val();
		loginid = "";
		uid="";
	};
	
	 starttime = $("#StartTime").val();
	 endtime = $("#EndTime").val();
	
	 
    order =$(".actived").attr("data");
   
       $.ajax({
           url: "<%=path%>/GetUserList",
           type: 'post',
           async: false,
           contentType: "application/x-www-form-urlencoded; charset=UTF-8",
           dataType: 'json',
           data: {Loginid:loginid,UName:uname,UID:uid,StartTime:starttime,EndTime:endtime,pageIndex:pageIndex,pageListSize:pageListSize,order:order },
           success: function (result) {
           	$("#discuList").html("");
           	if (result != 0) {
           	var str="<table class='userlist datagrid-btable'>";
           	str+="<tr class='first'><th class='name'>用户名</th><th class='name'>姓名</th><th class='tel'>电话号码</th><th class='email'>email</th><th class='addr'>地址</th><th class='capa'>职务</th><th class='userspec'>专业</th><th class='edu'>学历</th><th class='regtime'>注册时间</th><th class='comptime'>完善个人信息</th></tr>";
           	for(var i=0;i < result.length;i++ ){
           		str+="<tr class='datagrid-row'>";
				    str += "<td>&nbsp; <a class=\"title\" title=\"" + result[i].id + "\" href=\"/Detail/detail.jsp?id=" + result[i].id + "\" target=\"_blank\">"
								+ result[i].loginid + "</a></td>";
					str += "<td >"+ result[i].name+ "</td>";
					str += "<td >"+ result[i].mobile+ "</td>";
					str += "<td >"+ result[i].email+ "</td>";
					str += "<td >"+ result[i].address+ "</td>";
					str += "<td >"+ result[i].capacity+ "</td>";
					str += "<td >"+ result[i].speciality+ "</td>";
					str += "<td >"+ result[i].education+ "</td>";
					str += "<td title='"+result[i].createtime+"'>"+ result[i].createtime.substring(0,10)+ "</td>";
					str += "<td  title='"+result[i].activetime+"'>"+ result[i].activetime.substring(0,10)+ "</td>";
					str +="</tr>";
				}
           	    str+="</div>";
				$("#discuList").html(str);
           	} else {
                   $("#discuList").html("<div class='no_record'>抱歉！没有找到符合限定条件的记录。</div>");
               }
           },
           error: function () {
               alert("获取书目列表异常！");
               return false;
           }
       });	 



}
</script>
</html>