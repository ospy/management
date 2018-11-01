<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ include file="/Master/header.jsp"%>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源管理</title>

</head>

<body>	
<link href="/Css/default/easyui.css" rel="stylesheet" />
<link href="/Css/demo.css" rel="stylesheet" />
<link href="/Css/icon.css" rel="stylesheet" />	
<link href="/Css/reslist.css" rel="stylesheet" />	
			<table class="limit">
				<tr class="clinic_tr  classlist">
					<td class="top">
					<span style="color: red">*</span>&nbsp;学&nbsp;&nbsp;&nbsp;科：<input
						id="s_spec1"   readonly class="select_txt" type="text" text="全部"
						value="全部" /><input id="i_spid1" style="display: none"
						 class="input" type="text" value="0" /><a id="spe_btn" class="easyui-linkbutton" iconCls="icon-search" onclick="showdlg();">选择...</a>&nbsp;&nbsp;<a onclick="AllSpec()">全部</a>
					</td>
                    <td>
                        <span>日期：</span>
						<div class="control-group">
							<input id="StartTime3" class="easyui-datebox" required="required" value=""/>
						</div> --
						<div class="control-group">

							<input id="EndTime3" class="easyui-datebox" required="required"/>
						</div>
					</td>
					<td>
				<div class="rm_search">
				    <span>搜索：</span>
	                <input type="text" value="" class="searchbox" id="keys">
	                <input type="button" value="搜索" onclick="getCount();" class="searchbtn" name="search" id="search">
	            </div>
					</td>
				</tr>
				
			</table>


			<div class="listcontent">
				<div class="orderbox">
					共找到&nbsp;<span id="res_total" style="color: red"></span>&nbsp;条记录 <span
						class="ordercontrol">排序方式：发布时间 <a id="down_time"
						data="order by a.s_create_time desc"
						class="down_arrow down_arrow_actived order  actived"></a>&nbsp;<a
						id="up_time" data="order by a.s_create_time asc"
						class="up_arrow  order"></a>&nbsp;&nbsp;下载次数 <a id="down_download"
						data="order by a.i_download_times desc" class="down_arrow  order"></a>&nbsp;<a
						id="up_download" data="order by a.i_download_times asc"
						class="up_arrow  order"></a>&nbsp;&nbsp;点击次数 <a id="down_click"
						data="order by a.i_click_times desc" class="down_arrow  order"></a>&nbsp;<a
						id="up_click" data="order by a.i_click_times asc"
						class="up_arrow  order"></a></span>
				</div>
				<div id="discuList" style="height: auto;"></div>
				<div id="foot" style="height: 40px; text-align: center;"></div>

				<div id="ResPager" style="width: 800px;"></div>
			</div>
		<div class="clear"></div>
		
	<div id="dlg" class="easyui-draggable easyui-resizable">			
		<div id="spec-content" class="confirmcenter"></div>
		<div style="padding-left: 20px">
					选择类别：<span id="spec" style="color: black; font-weight: bold"></span><span
						id="dpid" style="display: none"></span>
		</div>
		
	</div>
<script>
$(function() {
	$("#dlg").hide();
});
function showdlg() {
	 $('#spec').html("");
	 $('#dpid').html("");
	 $('#s_spec1').val("");
		$('#dlg').dialog({
		title : '选择学科：',
		top:100,
		width:800,
		height:560,
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
			 setDept();
			 
			
		 },
		},{text:'Cancel',
		   handler:function(){
			   $('#dlg').dialog('close');}
		  }
		] 
		});
}
</script>	

<script src="/Js/jquery.myPagination.js"></script>
<script src="/Js/Disculist.js"></script>

<link href="/Css/page.css" rel="stylesheet" />

<script type="text/javascript">

		var spid = 0;//spid = 0 学科编码
		var totalPage = 0;
		var pageListSize = 10;//每页显示调试
		//var uType = 0;//uType=2管理员
		var startTime = "";
		var endTime = "";
        var classid ="";
        var urlinfo = window.location.href;//获取url
        var search = "";//拆分url得到”=”后面的参数      
       
        var where ="";
        var order ="";
		$(function() {
			 if(classid==0){
				 $(".classlist").show();
				 spid=0;
             }
			
			 else  if(classid==1){
            	  $(".clinic_tr").show().siblings(".classlist").hide();
            	  spid=1;
              }
              else if(classid==2){
            	  $(".basic_tr").show().siblings(".classlist").hide();
            	  spid=2;
              }
              else if(classid==3){
            	  $(".pharmacy_tr").show().siblings(".classlist").hide();
            	  spid=3;
              }
              else{
            	  spid=classid;
            	  $(".classlist").show();
            	 
              }

			//排序显示样式控制
			 $(".down_arrow").click(function(){				 
				   $(this).addClass("down_arrow_actived actived").siblings().removeClass("down_arrow_actived up_arrow_actived actived");
				   
				   $("#ResPager").myPagination({
						currPage : 1,
						pageCount : totalPage,
						pageSize : 10
					});
			 })
			  $(".up_arrow").click(function(){								 
				  $(this).addClass("up_arrow_actived actived").siblings().removeClass("down_arrow_actived up_arrow_actived actived");			  
				  $("#ResPager").myPagination({
						currPage : 1,
						pageCount : totalPage,
						pageSize : 10
					});
			 })
   
			           
            
			 getCount();
			
			 GetSpec();
			 
			 
			 
		});


		//清除时间限定
		function ClearTime() {
			$("#startTime").val('');
			$("#endTime").val('');
			$("#sTime").html("");
			$("#eTime").html("");
			$("#between").html("");
			$("#time_limit").removeClass("limit_info");
			$("#time_limit .crumbDelete").remove();

			startTime = "2007-01-01";
			var curr_time = new Date();
			endTime = curr_time.getFullYear() + "-";
			endTime += curr_time.getMonth() + 1 + "-";
			endTime += curr_time.getDate();

			$("#foot").html("<img src='/Images/icons/loading.gif'/>");
			getCount(dpid);
			//GetDiscuByPage(0, pageListSize, dpid, startTime, endTime);

			$("#foot").html("");
		}
		//清除学科限定
		function ClearSpec() {
			startTime = "2007-01-01";

			var curr_time = new Date();
			endTime = curr_time.getFullYear() + "-";
			endTime += curr_time.getMonth() + 1 + "-";
			endTime += curr_time.getDate();

			if ($("#startTime").val() != "" && $("#endTime").val() == "") {
				startTime = $("#startTime").val();
				$("#sTime").html(startTime);
				$("#between").html("至今");
			}

			if ($("#endTime").val() != "") {
				endTime = $("#endTime").val();
				$("#between").html("至");
				$("#eTime").html(endTime);
			}
			$(".secondclass li a").removeClass("spec");
			$("#select").html("全部学科");
			$("#foot").html("<img src='/Images/icons/loading.gif'/>");
			GetDiscuCount(0);
			//GetDiscuByPage(0, pageListSize, 0, startTime, endTime);

			$("#foot").html("");
		}

		//根据学科编号获取学科名称
		function GetDepartment(dpid) {
			$.ajax({
						url : "/DiscuList/GetDepartment",
						type : 'post',
						async : false,
						dataType : 'text',
						data : {
							dpid : dpid
						},
						success : function(result) {
							if (result != "") {
								$("#select").html(result+ '<a class="crumbDelete" onclick="ClearSpec();"></a>');
							}
						},
						error : function(err) {
							alert("获取学科异常，请联系管理员");
							return false;
						}
					});

		}

		
		
		

		//字符串转json
		function strToJson(str) {
			var json = eval('(' + str + ')');
			return json;
		}		

         function getCount(){ 
        	 search=$("#keys").val();
        	 spid=$("#i_spid1").val();
        	 startTime = $("#StartTime3").val();
    		 endTime = $("#EndTime3").val();
    		
	            $.ajax({
	                url: "<%=path%>/GetCount",
	                type: 'post',
	                async: false,
	                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	                dataType: 'text',
	                data: {search:search, spid:spid,startTime:startTime,endTime:endTime },
	                success : function(result) {
					 
		                    if(result !=0 ){       
							var	pageTotalCount = parseInt(result);//总记录数
								$("#res_total").html(pageTotalCount);
								totalPage = parseInt(pageTotalCount
										/ pageListSize)
										+ ((pageTotalCount % pageListSize) > 0 ? 1
												: 0);
		
								//mypagination
								$("#ResPager").show();
								$("#ResPager").myPagination({
									currPage : 1,
									pageCount : totalPage,
									pageSize : 10
								});
                              }
                    else{
                    	$("#res_total").html("0");
                    	$("#discuList").html("<div class='no_record'>抱歉！没有找到符合限定条件的记录。</div>");
                    	$("#ResPager").hide();
                          }	                		                	
						
						},
	                error: function () {
	                    alert("获取记录总数异常！");
	                    return false;
	                }
	            });	 
        	 
         }
		//获取图书列表
         function getClassList(currIndex,pageSize){
			 
        	 search=$("#keys").val();
        	 spid=$("#i_spid1").val();
        	 startTime = $("#StartTime3").val();
    		 endTime = $("#EndTime3").val(); 
 			 
		     order =$(".actived").attr("data");
		    
	            $.ajax({
	                url: "<%=path%>/SearchList",
	                type: 'post',
	                async: false,
	                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	                dataType: 'json',
	                data: {search:search,spid:spid,startTime:startTime,endTime:endTime,pageIndex:currIndex,pageListSize:pageSize,order:order },
	                success: function (result) {
	                	$("#discuList").html("");
	                	if (result != "0") {
	                	var str = "";
	                	for(var i=0;i < result.length;i++ ){	                		
	                		str += "<div class=\"listtable\"><a title=\"" + result[i].s_desc + "\" class=\"pic\" href=\"/Detail/detail.jsp?id=/" + result[i].i_discuid + "\" target=\"_blank\">";
							str += "<img title=\"" + result[i].s_desc + "\" src=\"" + result[i].s_imgurl + "\" style=\"height: 60px; width: 60px;\">";
							str += "<span class=\"createTime\" style=\"display: none;\">"+ result[i].s_create_time+ "</span></a>";
							str += "<p class=\"firsttd\"><span class=\"icon pdf\"></span>";
							
						    str += "<a class=\"title\" title=\"" + result[i].s_desc + "\" href=\"/Detail/detail.jsp?id=" + result[i].i_discuid + "\" target=\"_blank\">"+ result[i].s_desc + "</a>";

							str += "<span class=\"listtime\">&nbsp; "+ result[i].s_create_time+ "</span><span class='oper'><a class='edit' target='_blank' href='/ResManage/edit.jsp?id="+result[i].i_discuid+"'>编辑</a> | <a class='del'  onclick='delete("+result[i].i_discuid+");'>删除</a></span></p>";
						
							str += "<span class=\"others\"><span class=\"subtitle\">资源类型："+ result[i].s_filetypes+"</span>&nbsp;&nbsp;&nbsp;&nbsp;";
							str += "<span class=\"others\"><span class=\"subtitle\">发&nbsp;&nbsp;布&nbsp;&nbsp;者："+ result[i].s_loginid+"</span>&nbsp;&nbsp;&nbsp;&nbsp;";
							str += "<span class=\"others\"><span class=\"subtitle\">点击次数："+ result[i].i_click_times+ "</span>&nbsp;&nbsp;&nbsp;<span class=\"subtitle\">下载次数："+ result[i].i_download_times + "</span></span>&nbsp;&nbsp;&nbsp;&nbsp;";

							str += "<span class=\"others\">学科分类：<span class=\"subtitle\">"+ result[i].s_spec+"</span></span>&nbsp;&nbsp;&nbsp;&nbsp;";
							str += "<span><span class=\"price\">￥"+ result[i].i_discuPrice +"点</span>&nbsp;&nbsp;&nbsp;&nbsp;</span></span></div>";
						}
						    $("#discuList").html(str);
	                	}
	                	else {
	                        $("#discuList").html("<div class='no_record'>抱歉！没有找到符合限定条件的记录。</div>");
	                    }
	                },
	                error: function () {
	                    alert("获取书目列表异常！");
	                    return false;
	                }
	            });	 

	}

       //获取全部分类
         function GetSpec() {
         	var content="";
             $.ajax({
             	
                 url: "/GetSpec",
                 type: "post",
                 async: false,
                 contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                 dataType: "text",
                 data: {},
                 success: function (result) {
                     if (result != "") {
                     	
                     	var Obj=eval("("+result+")");//转换为json对象 
                     	 
                        for(var i=1;i<6;i++){
                     	content+="<div class='scitem'><h2>"+i+"区</h2><ul class='spec'>";
                     	var root="区"+i;
                     	               	 
     		               	  //遍历json数组 
     		               	  $.each(Obj[root], function(j, item) { 
     		               	  content+="<li><a class=\'"+item.i_spid+"\' onclick=getDept(\'"+item.i_spid+"\',\'"+item.s_spec+"\')>"+item.s_spec+"</a></li>"; 
                    
                    	  }); 
                      	content+="</ul></div>";
                        }
                     		
                     		$("#spec-content").html(content); //分割后的字符输出 
                          }  
                     else{ 
                     	    
                     	 alert("获取学科分类为空！") ;  
                     		
                     	} 

                 },
                 error: function () {
                     alert("获取学科分类异常！");
                     return false;
                 }
             });
         }
         //当前选择的学科
         function getDept(dpid, spec) {
            $("#spec").append(spec+" or ");
            $("#dpid").append(dpid+",");           
             $(".spec li a").removeClass("dept");
             $("." + dpid).addClass("dept");
         }
         //确定选择的学科
         function setDept() {
                 
                 $("#s_spec1").val($("#spec").html());
                 $("#i_spid1").val($("#dpid").html());
            
            
         }

         //设置当前点击选择学科几
         function clearMark() {

                 $("#spec").html("");
                 $("#dpid").html("");
                 $(".spec li a").removeClass("dept");
             
             
         }
       //全部学科
         function AllSpec() {
            
                 $("#s_spec1").val("全部");
                 $("#i_spid1").val(0);
            
            
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
			$("#StartTime3").datebox({
			    required:true
			});	
			$("#EndTime3").datebox({
			    required:true
			});	


		
		$.getUrlParam = function(name)
		{
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
				var r = window.location.search.substr(1).match(reg);
				if (r != null) return unescape(r[2]); return null; 
			}
		
	});
	
</script>


   </body>
</html>