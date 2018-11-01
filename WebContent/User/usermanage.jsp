<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/Master/header.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
 <meta name="description" content="这是一个 index 页面">
  <meta name="keywords" content="index">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <meta name="apple-mobile-web-app-title" content="Amaze UI" />
</head>

<body class="main">
		
             <table class="limit">
				<tr class="clinic_tr  classlist orderbox date_control">
				<td style="width: 30%;">
            		共找到&nbsp;<span id="user_total" style="color: red"></span>&nbsp;条记录  
					</td>
					<td style="text-align: right;width: 40%;">
            			<span	class="ordercontrol"><div class="control-group">开始时间：<input id="StartTime1" class="easyui-datebox" required="required" value=""/></div> <div class="control-group">至</div>
						<div class="control-group">结束时间：<input id="EndTime1" class="easyui-datebox" required="required"/></div></span>
					</td>
					<td style="text-align: left;width: 30%;padding-left: 30px;">
			              <select id="selector">
			              <option value="option1">用户名：</option> 
			                <option value="option2">用户id:</option>
			                <option value="option3">姓名：</option>
			              </select>

                       <input type="text" value=""  id="keys"><button id="searchbtn" class="am-btn am-btn-default" type="button" onclick="getUserList();">搜索</button>
					</td>
					
				</tr>
				
	</table>
	        
	
	        <div class="clear"></div>
	
        <div class="listcontent">
				<table id="dg"></table>
	    </div>
	    
	    
     	    <!--数据展示 -->
<link rel="stylesheet" href="/Css/user-manage.css"/>
<script src="/Js/Disculist.js"></script>
<script type="text/javascript">
	var occus = [
	 		    {occuid:'1',name:'医务人员'},
	 		    {occuid:'2',name:'教师'},
	 		    {occuid:'3',name:'研发人员'},
	 		    {occuid:'4',name:'学生'},
	 		    {occuid:'9',name:'其它'}
	 		   
	 		];
	var edus = [
		 		    {eduid:'1',name:'专科'},
		 		    {eduid:'2',name:'本科'},
		 		    {eduid:'3',name:'硕士'},
		 		    {eduid:'4',name:'博士'},
		 		    {eduid:'9',name:'其它'}
		 		   
		 		];
    var $obj;  
    $(function() {  
    	getUserList();
      
    });  
      
        function selectCurRow(obj){  
            var $a = $(obj);  
            var $tr = $a.parent().parent().parent();  
            var tmpId = $tr.find("td:eq(0)").text();  
            $obj.datagrid('selectRecord', tmpId);  
        }  
          
        function getIndexAfterDel(){  
            var selected = $obj.datagrid('getSelected');  
            var index = $obj.datagrid('getRowIndex', selected);  
            return index;  
        }  
          
        function editrow(index,obj){  
            selectCurRow(obj);  
              
            var tmpIndex = getIndexAfterDel();    
            $obj.datagrid('beginEdit', tmpIndex);  
        }  
          
        function deleterow(index,obj){  
            $.messager.confirm('Confirm','确认删除?',function(r){  
                if (r){               
                    selectCurRow(obj);  
                    var index = getIndexAfterDel();  
                    var node = $obj.datagrid('getSelected');  
                    var json = {};  
                    json.DelId = node.id; 
                    
                    $.ajax({  
                        url :'<%=path%>/M_DelMember',  
                        type : 'post',
                        data: json,      
                        timeout : 60000,  
                        success : function(data, textStatus, jqXHR) {     
                            var msg = '删除';  
                            if(data == "success") {  
                                $obj.datagrid('deleteRow', index);  
                                $obj.datagrid('reload');  
                                $.messager.alert('提示', msg + '成功！', 'info', function() {  
                                    //window.location.href = root + 'esbService/initSysConfig.do';  
                                });  
                            } else {  
                                $.messager.alert('提示', msg + '失败！', 'error', function() {  
                                    //window.location.href = root + 'esbService/initSysConfig.do';  
                                });  
                            }  
                        }  
                    });   
                      
                }  
            });  
        }  
        function saverow(index,obj){  
            selectCurRow(obj);  
            var tmpIndex = getIndexAfterDel();    
            $obj.datagrid('endEdit', tmpIndex);  
            var node = $obj.datagrid('getSelected');  
            //var data = JSON.stringify(node);  
            var json = {};  
            json.id = node.id; 
            json.loginid = node.loginid;
            json.email = node.email;
            json.mobile = node.mobile;  
            json.name = node.name;  
            json.occupation = node.occupation;  
            json.education = node.education; 
            json.speciality = node.speciality; 
            json.capacity = node.capacity;
            json.address = node.address;
            $.ajax({  
                url :'<%=path%>/M_UpdateMemberInfo',  
                type : 'POST',  
                data : json,  
                timeout : 60000,  
                success : function(data, textStatus, jqXHR) {     
                    var msg = '';  
                    if (data == "success") {  
                        $.messager.alert('提示', '保存成功！', 'info', function() {  
                            $obj.datagrid('refreshRow', tmpIndex);  
                        });  
                    } else{  
                
                            msg = "保存失败！";  
                          
                        $.messager.alert('提示', msg , 'error', function() {  
                            $obj.datagrid('beginEdit', tmpIndex);  
                        });  
                    }                     
      
                }  
            });  
              
        }  
        function cancelrow(index,obj){  
            selectCurRow(obj);  
            var tmpIndex = getIndexAfterDel();    
            $obj.datagrid('cancelEdit', tmpIndex);  
        }  
          
        function appendRow(){  
            $obj.datagrid('appendRow',{  
                id: new Date().getTime(),  
                configName: '',  
                configNameCn: "",  
                configType: "",  
                configValue:"",  
                opt:""  
            });  
              
            var length = $obj.datagrid("getRows").length;  
            if(length > 0){  
                editIndex = length - 1;  
            }else{  
                editIndex = 0;  
            }         
            //$obj.datagrid("selectRow", editIndex);  
            $obj.datagrid("beginEdit", editIndex);  
        }  

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
	
	 starttime = $("#StartTime1").val();
	 endtime = $("#EndTime1").val();
	
	 
    order =$(".actived").attr("data");
   
      
    $obj = $("#dg");  
    $obj.datagrid({  
        loadMsg : '数据加载中请稍后……',  
        url : '<%=path%>/GetUserList', 
        queryParams:{"UName":uname,"Loginid":loginid,"UID":uid,"StartTime":starttime,"EndTime":endtime}, 
        //url : root + 'js/app/sysManagement/sysConfig.json',
       
        fitColumns : true,
        width: $(window).width() - 10,
       
        striped: true,
        autoRowHeight : true,  
        pagination : true,  
        pagePosition : 'bottom',  
        pageSize : 10,  
        toolbar: '#configTb',  
        pageList : [ 10, 20],  
        border : false,  
        singleSelect:true,
        nowrap:false,
        idField:'id',  
        columns : [ [ 
                      {  
        	field : 'id',title : 'ID'},
        	{  
        	field : 'loginid',title : '用户名',editor:'text'
            },{  
            field : 'email',title : '邮箱'
            },{  
        	field : 'mobile',title : '手机',editor:'text'
            },{
        	field : 'name',title : '姓名',editor:'text'
            },{  
        	field : 'occupation',title : '职位',editor:'text',
        	formatter:function(value){
				for(var i=0; i<occus.length; i++){
					if (occus[i].occuid == value) return occus[i].name;
				}
				return value;
			},
			editor:{
				type:'combobox',
				options:{
					valueField:'occuid',
					textField:'name',
					data:occus,
					required:true
				}
			}
            },{  
        	field : 'education',title : '最高学历',editor:'text',
        	formatter:function(value){
				for(var i=0; i<edus.length; i++){
					if (edus[i].eduid == value) return edus[i].name;
				}
				return value;
			},
			editor:{
				type:'combobox',
				options:{
					valueField:'eduid',
					textField:'name',
					data:edus,
					required:true
				}
			}
            },{  
        	field : 'speciality',title : '专业',editor:'text'
            },{  
        	field : 'capacity',title : 'ID',editor:'text'
            },{  
        	field : 'address',title : '地址',editor:'text',
        	formatter: function (value,row,index) {
        	    return '<div style="width:160px;word-break:break-all;word-wrap:break-word;white-space:pre-wrap;">'+value+'</div>';
        	}  
            },{  
        	field : 'createtime',title : '创建时间', formatter : function(value){
                var date = new Date(value);
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var d = date.getDate();
                var dt= y + '-' +m + '-' + d;
               return "<span  title='"+value+"'>"+dt+"</span>";
        	}
            
            },{  
        	field : 'activetime',title : '激活时间',formatter : function(value){
                var date = new Date(value);
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var d = date.getDate();
                var dt= y + '-' +m + '-' + d;
               return "<span  title='"+value+"'>"+dt+"</span>";
        	}
            },
            {  
                field : 'opt',  
                title : "操作",  
                width : 0.1,  
                align : 'center',  
                formatter:function(value,row,index){  
                    if (row.editing){  
                        var s = '<a href="#" class="ope-save" onclick="saverow('+index+',this)">保存</a> ';  
                        var c = '<a href="#" class="ope-cancel" onclick="cancelrow('+index+',this)">取消</a>';  
                        return s+c;  
                    } else {  
                        var e = '<a href="#" class="ope-edit" onclick="editrow('+index+',this)">修改</a> ';  
                        var d = '<a href="#" class="ope-remove" onclick="deleterow('+index+',this)">删除</a>';  
                        return e+d;  
                    }  
                }  
        } ] ], 
        onDblClickRow: function () {
        	editrow('+index+',this);
        },
        
        onBeforeEdit:function(index,row){  
            row.editing = true;  
            $obj.datagrid('refreshRow', index);  
        },  
        onAfterEdit:function(index,row){  
            row.editing = false;  
            $obj.datagrid('refreshRow', index);  
        },  
        onCancelEdit:function(index,row){  
            row.editing = false;  
            $obj.datagrid('refreshRow', index);  
        },
        onLoadSuccess : function(data) {  
  
        var grid = $('#dg');  
        var options = grid.datagrid('getPager').data("pagination").options;  
        var curr = options.pageNumber;  
        var total = options.total;  

        $('#user_total').html(total);  }
        
    });  
    
   
    
}
</script>


<script type="text/javascript">
	

		$(function() {
			$.fn.datebox.defaults.formatter = function(date){
			    var y = date.getFullYear();
			    var m = date.getMonth()+1;
			    var d = date.getDate();
			    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
			}
			$("#StartTime1").datebox({
			    required:true
			});	
			$("#EndTime1").datebox({
			    required:true
			});	


	
			$.getUrlParam = function(uid)
		{
				var reg = new RegExp("(^|&)" + uid + "=([^&]*)(&|$)", "i");
				var r = window.location.search.substr(1).match(reg);
				if (r != null) return unescape(r[2]); return null; 
			}
		
	});

</script>

</body>
</html>