<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ include file="/Master/header.jsp"%>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户管理</title>
</head>


<body>

    <table class="limit">
				<tr class="clinic_tr  classlist">
					<td >
					<span style="color: red">*</span>账务类型：
						<select name="selectType" id="selectType" class="easyui-combobox" name="state" style="width:100px;"> 
						    <option value="">全部</option>  
					        <option value="下载文件">下载文件</option>   					       
					        <option value="支付宝购买">支付宝购买</option>
					        <option value="微信购买">微信购买</option> 
					        <option value="手动添加">手动添加</option>     
					        <option value="手动减少">手动减少</option>
					        <option value="活动赠送">活动赠送</option>   
					      </select>   
					</td>
					<td >增减类型：
					 <select id="addsub" class="easyui-combobox" name="state" style="width:80px;">
					 <option value="option0">全部</option> 
		                <option value="+">+</option> 
		                <option value="-">-</option>
		             </select>
            			
					</td>
					 <td>
                        <span>日期：</span>
						<div class="control-group">
							<input id="StartTime2" class="easyui-datebox" required="required" value=""/>
						</div> --
						<div class="control-group">

							<input id="EndTime2" class="easyui-datebox" required="required" />
						</div>
					</td>
					<td >用户名:
            			<input type="text" value=""  id="loginid"  class="easyui-textbox" style="width:120px;height:32px;">
					</td>
					
					<td >备注内容：
            			<input type="text" value=""  id="memo"  class="easyui-textbox" style="width:120px;;height:32px;">
					</td>
                   
					<td>
						<div class="al_search">
			                <input type="button" value="搜索" onclick="getCount();" class="searchbtn" name="search" id="search">
			            </div>
					</td>
				</tr>
				
	</table>
              <div class="listcontent">
				<div class="limitbox">
					共找到&nbsp;<span id="total" style="color: red"></span>&nbsp;条记录
				</div>
				<div id="AccountList" style="height: auto;">
				</div>
				   
				<div id="AccPager" style="width: 1200px;"></div>
			</div>
	<div id="accdlg" class="easyui-draggable easyui-resizable">
	     <br><br>
	     <div class="accitems"> 用户名：<span id='uname'></span></div>
	      <div class="accitems">账户余额：<span id='balance'></span></div>			
		<div id="accdlg-content" class="confirmcenter">
		    <div class="accitems" id="updatetype">类型：
					 <select  class="easyui-combobox" name="state" style="width:80px;">					 
		                <option value="+">增加</option> 
		                <option value="-">减少</option>
		             </select></div>
		    <div class="accitems" id="points">点数：<input type="text" value=""    class="easyui-textbox" style="width:220px;;height:32px;"></div>
		    <div class="accitems" id="changememo">备注：<input type="text" value=""    class="easyui-textbox" style="width:220px;;height:32px;"></div>
		</div>
		
		
	</div>
<script>
$(function() {
	$("#accdlg").hide();
	
});

</script>	  


<script src="/Js/jquery.AccListPagination.js"></script>

<link href="/Css/page.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/Css/account.css"/>

 <script>
    var selectType = "";
    var addsub="";
    var loginid="";
    var memo="";
    var starttime = "";
	var endtime = "";	 
   
    
    $('#loginid').val(loginid);
	var totalPage = 0;
	var pageListSize = 10;//每页显示调试
         getCount();
   		//字符串转json
		function strToJson(str) {
			var json = eval('(' + str + ')');
			return json;
		}		

         function getCount(){
	        	var selectType=$("#selectType").val();
	        var	 addsub=$("#addsub").val();
	        	var loginid=$("#loginid").val();
	        	var memo= $("#memo").val();
        	var	starttime = $("#StartTime2").val();
        	var	endtime = $("#EndTime2").val();	 
        	   
        	    
        	 if(1==1){
	            $.ajax({
	                url: "<%=path%>/AccTotalCount",
	                type: 'post',
	                async: true,
	                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	                dataType: 'text',
	                data: {selectType:selectType,addsub:addsub,loginid:loginid,memo:memo,starttime:starttime,endtime:endtime},
	                success : function(result) {
					 
		                    if(result !=0 ){       
							var	pageTotalCount = parseInt(result);//总记录数
								$("#total").html(pageTotalCount);
								totalPage = parseInt(pageTotalCount
										/ pageListSize)
										+ ((pageTotalCount % pageListSize) > 0 ? 1
												: 0);
		
								//mypagination
								$("#AccPager").show();
								$("#AccPager").myPagination({
									currPage : 1,
									pageCount : totalPage,
									pageSize : 10
								});
                              }
                    else{
                    	$("#total").html("0");
                    	$("#AccountList").html("<div class='no_record'>抱歉！没有找到符合限定条件的记录。</div>");
                    	$("#AccPager").hide();
                          }	                		                	
						
						},
	                error: function () {
	                    alert("获取记录总数异常！");
	                    return false;
	                }
	            });	 
        	 }
        	 else{
        		 
        		 alert("请登录！");
        	 } 
         }
		//获取已下载列表
         function AccTotalList(pageIndex, pageListSize){
        	var selectType=$("#selectType").val();
        	var addsub=$("#addsub").val();
        	var loginid=$("#loginid").val();
        	var memo= $("#memo").val();
    		starttime = $("#StartTime2").val();
    		endtime = $("#EndTime2").val();	 
    	    
        	 
		    
	            $.ajax({
	                url: "<%=path%>/GetAccTotalList",
	                type: 'post',
	                async: false,
	                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	                dataType: 'json',
	                data: {selectType:selectType,addsub:addsub,loginid:loginid,memo:memo,starttime:starttime,endtime:endtime,pageIndex:pageIndex,pageListSize:pageListSize},
	                success: function (result) {
	                	$("#AccountList").html("");
	                	if (result !="0") {
	                	var str = "<table><tr style='line-height:30px;background: #f6f6f6;font-weight:bold;'><td style='width:5%;text-align:center;'>用户名</td><td style='width:10%;text-align:center;'>类型</td><td style='width:30%;text-align:center;'>备注</td><td style='width:10%;text-align:center;'>使用前</td><td style='width:5%;text-align:center;'>变动</td><td style='width:10%;text-align:center;'>使用后</td><td style='width:20%;text-align:center;'>下载时间</td><td style='width:10%;text-align:center;'>操作</td></tr>";
	                	for(var i=0;i < result.length;i++ ){
	                		str += "<tr><td><a class='loginid' target=_blank href='/MyCenter/Myinfo.jsp?uid="+result[i].i_uid+"'>"+result[i].s_loginid+"</a></td><td>"+result[i].s_type+"</td>";
	                		str += "<td><a title='" +result[i].s_desc+ "' href='/Detail/detail.jsp?id=" + result[i].i_discuid + "' target='_blank'>"+ result[i].s_desc +"</a>" +result[i].s_message+"</td>";
	                		str += "<td style='padding-left:24px;'>"+result[i].i_old_value+"</td>";
	                		str += "<td style='padding-left:24px;'>"+result[i].i_value+"</td>";
	                		str += "<td style='padding-left:24px;'>"+result[i].i_new_value+"</td>";
	                		str += "<td style='text-align:center;' class=\"createTime\">"+ result[i].s_create_time+"</td>";
	                		str += "<td style='text-align:center;'><a href=\"javascript:void(0)\" onclick=\"showaccdlg("+result[i].i_uid+",this)\">增减</a></td>";
						}
	                	str += "</tr></table>";
						$("#AccountList").html(str);
						SetTableRowColor(); 
	                	} else {
	                        $("#AccountList").html("<div class='no_record'>抱歉！没有找到符合限定条件的记录。</div>");
	                    }
	                },
	                error: function () {
	                    alert("获取书目列表异常！");
	                    return false;
	                }
	            });	 

	}
		
         function showaccdlg(uid,which) {
        	  
        	    var uname=$(which).parents("tr").find(".loginid").html();
        	    
        	    $('#uname').html(uname);
        	    accedit(uid);
        		$('#accdlg').dialog({
        		title : '选择学科：',
        		top:100,
        		width:700,
        		height:420,
        		modal : true,
        		// 是否显示可折叠按钮
        		collapsible : false,
        		// 是否显示最小化按钮
        		minimizable : true,
        		// 是否显示最大化按钮
        		maximizable : true,
        		// 是否可以改变对话框窗口大小
        		resizable : true,
        		// 设置对话框窗口顶部工具栏
        		//buttons : '#tt',
        		// 对话框窗口底部按钮
        		buttons : [{
        		 text : '保存',
        		 iconCls : 'icon-ok',
        		 handler : function () {
        			 adjust(uid);
        			 $('#accdlg').dialog('close');
        			 getCount();
        			
        		 },
        		},{text:'Cancel',
        		   handler:function(){
        			   $('#accdlg').dialog('close');}
        		  }
        		] 
        		});
        }
		
         function accedit(uid)    
         {           	    
              	var content="";
                  $.ajax({
                  	
                      url: "/GetAccount",
                      type: "post",
                      async: false,
                      contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                      dataType: "text",
                      data: {uid:uid},
                      success: function (result) {
                          if (result != "") {
                          	    $('#balance').html(result); 
                               }  
                          else{                          	    
                          	 alert("获取账户余额为空！") ;                          		
                          	} 

                      },
                      error: function () {
                          alert("获取学科分类异常！");
                          return false;
                      }
                  });    
         }   
         
         function adjust(uid)    
         {   
        	 var updatetype=$("#updatetype select").val();
        	 var points=$("#points input").val();
        	 var accmemo=$("#changememo input").val();
        	 
                  $.ajax({
                      url: "/AccAdjust",
                      type: "post",
                      async: false,
                      contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                      dataType: "text",
                      data: {uid:uid,updatetype:updatetype,points:points,accmemo:accmemo},
                      success: function (result) {
                          if (result != "") {
                          	    $('#balance').html(result); 
                               }  
                          else{ 
                          	    
                          	 alert("获取账户余额为空！") ;  
                          		
                          	} 

                      },
                      error: function () {
                          alert("获取学科分类异常！");
                          return false;
                      }
                  });
              
            
         }  
       //用CSS控制奇偶行的颜色    
         function SetTableRowColor()    
         {    
             
             $("#AccountList table tr:odd").css("background-color", "#f6f6f6");    
             $("#AccountList table tr:even").css("background-color", "#fff");    
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
			$("#StartTime2").datebox({
			    required:true
			});	
			$("#EndTime2").datebox({
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