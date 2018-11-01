<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ include file="/Master/header.jsp"%>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的账户</title>

<link type="text/css" rel="stylesheet" href="/Css/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="/Css/myresource.css"/>
<link href="/Css/page.css?v=20180312" rel="stylesheet" />
<script type="text/javascript" src="/Js/jquery-ui.min.js"></script>
<script src="/Js/jquery.MyAccountPagination.js"></script>
</head>
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

		$(function() {

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
		});

	
			$.getUrlParam = function(uid)
		{
				var reg = new RegExp("(^|&)" + uid + "=([^&]*)(&|$)", "i");
				var r = window.location.search.substr(1).match(reg);
				if (r != null) return unescape(r[2]); return null; 
			}
		
	}(jQuery));
	
	
</script>
<div class="main">
<table class="limit">
				<tr class="clinic_tr  classlist">
				    <td style="padding-left:12px;">共找到&nbsp;<span id="total" style="color: red"></span>&nbsp;条记录</td>
					<td style="width:25%;">
					<span style="color: red">*</span>&nbsp;学&nbsp;&nbsp;&nbsp;科：<input
						id="s_spec1" disabled="disabled" class="input" type="text" text="全部"
						value="全部" /><input id="i_spid1" style="display: none"
						 class="input" type="text" value="0" /><input
						style="line-height:20px" type="button" data-target="#SpecModal"
						data-toggle="modal" value="选择..." onclick="clearMark()" />&nbsp;&nbsp;<a onclick="AllSpec()">全部</a>
					</td>
                    <td style="width:25%;">
                        <span>日期：</span>
						<div class="control-group">
							<input id="StartTime"  value=""/>
						</div> --
						<div class="control-group">
							<input id="EndTime">
						</div>
					</td>
					<td style="width:20%;">
					    <span>搜索：</span>
		                <input type="text" value=""  id="keys">
		                <input type="button" value="搜索" onclick="getCount();"  name="search" id="searchbtn">
					</td>
				</tr>
				
			</table>
    <div class="listcontent">
				<div class="limitbox">
					</div>
				</div>
				<div id="AccountList" style="height: auto;">
				</div>
				   
				<div id="Pager" style="width: 800px;margin: 0 auto;"></div>
	</div>
</div>
 <script>
  var uid=$.getUrlParam('uid');       	
	 var totalPage = 0;
	var pageListSize = 10;//每页显示调试
         getCount();
   		//字符串转json
		function strToJson(str) {
			var json = eval('(' + str + ')');
			return json;
		}		

         function getCount(){
        	 
        	 if(uid!=""&&uid!="null"){
	            $.ajax({
	                url: "<%=path%>/AccountCount",
	                type: 'post',
	                async: true,
	                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	                dataType: 'text',
	                data: {Uid:uid },
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
                    	$("#AccountList").html("<div class='no_record'>抱歉！没有找到符合限定条件的记录。</div>");
                    	$("#Pager").hide();
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
         function AccountList(pageIndex, pageListSize){
        	 var uid=$.getUrlParam('uid'); 
        	 
		    
	            $.ajax({
	                url: "<%=path%>/GetAccountList",
	                type: 'post',
	                async: false,
	                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	                dataType: 'json',
	                data: {Uid:uid,pageIndex:pageIndex,pageListSize:pageListSize},
	                success: function (result) {
	                	$("#AccountList").html("");
	                	if (result != 0) {
	                	var str = "<table><tr style='line-height:30px;background: #f6f6f6;'><td style='width:10%;text-align:center;'>类型</td><td style='width:40%;text-align:center;'>备注</td><td style='width:10%;text-align:center;'>使用前</td><td style='width:10%;text-align:center;'>变动</td><td style='width:10%;text-align:center;'>使用后</td><td style='width:20%;text-align:center;'>下载时间</td></tr>";
	                	for(var i=0;i < result.length;i++ ){
	                		str += "<tr><td>"+result[i].s_type+"</td>";
	                		str += "<td><a title='" +result[i].s_desc+ "' href='/Detail/detail.jsp?id=" + result[i].i_discuid + "' target='_blank'>"+ result[i].s_desc +"</a>" +result[i].s_message+"</td>";
	                		str += "<td style='padding-left:24px;'>"+result[i].i_old_value+"</td>";
	                		str += "<td style='padding-left:24px;'>"+result[i].i_value+"</td>";
	                		str += "<td style='padding-left:24px;'>"+result[i].i_new_value+"</td>";
	                		str += "<td style='text-align:center;' class=\"createTime\">"+ result[i].s_create_time+"</td>";
	
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
       //用CSS控制奇偶行的颜色    
         function SetTableRowColor()    
         {    
             
             $("#AccountList table tr:odd").css("background-color", "#f6f6f6");    
             $("#AccountList table tr:even").css("background-color", "#fff");    
         } 
       
 
  </script>
</html>