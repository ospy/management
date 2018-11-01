<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ include file="/Master/header.jsp"%>
<%@ include file="/Master/topmenu.jsp"%>

<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的资源</title>

<link type="text/css" rel="stylesheet" href="../Css/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="../Css/myresource.css"/>
<link href="/Css/page.css?v=20171214" rel="stylesheet" />
<script type="text/javascript" src="/Js/jquery-ui.min.js"></script>
<script src="/Js/jquery.MyDownPagination.js"></script>
</head>
  <script>
  $( function() {
    $( "#tabs" ).tabs();
  } );
  </script>
<body>
   <div id="tabs">
  <div id="tab_btn_box">
	  <ul id="tab_btn">
	    <li><a href="#tabs-1">已下载</a></li>
	    <li><a href="#tabs-2">已收藏</a></li>
	  </ul>
   </div>
  <div id="tabs-1">
     		<div class="listcontent">
				<div class="limitbox">
					共找到&nbsp;<span id="total" style="color: red"></span>&nbsp;条记录</div>
				</div>
				<div id="downloadList" style="height: auto;"></div>
				

				<div id="Pager" style="width: 800px;"></div>
			</div>
  </div>
  <div id="tabs-2">
     
  </div>

</div>
  <script>
  var uid="<%=uid%>";       	
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
	                url: "<%=path%>/DownloadCount",
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
                    	$("#downloadList").html("<div class='no_record'>抱歉！没有找到符合限定条件的记录。</div>");
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
         function getMyDownList(pageIndex, pageListSize){
        	 var uid="<%=uid%>"; 
        	 
		    
	            $.ajax({
	                url: "<%=path%>/GetDownloadList",
	                type: 'post',
	                async: false,
	                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	                dataType: 'json',
	                data: {Uid:uid,pageIndex:pageIndex,pageListSize:pageListSize},
	                success: function (result) {
	                	$("#downloadList").html("");
	                	if (result != 0) {
	                	var str = "<table><tr style='line-height:30px;background: #f6f6f6;'><td style='width:80%;text-align:center;'>标&nbsp;&nbsp;&nbsp;&nbsp;题</td><td style='width:20%;text-align:center;'>下载时间</td></tr>";
	                	for(var i=0;i < result.length;i++ ){
	                		
	                		str += "<tr><td style='width:70%;padding-left:24px;'><a title=\"" + result[i].s_desc + "\" class=\"pic\" href=\"/Detail/detail.jsp?id=" + result[i].i_discuid + "\" target=\"_blank\">"+ result[i].s_desc + "</td>";
	                		str += "<td style='width:30%;text-align:center;' class=\"createTime\">"+ result[i].s_create_time+"</td></tr>";
	
						}
	                	str += "</table>";
						$("#downloadList").html(str);
						 SetTableRowColor();  
	                	} else {
	                        $("#downloadList").html("<div class='no_record'>抱歉！没有找到符合限定条件的记录。</div>");
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
             
             $("#downloadList table tr:odd").css("background-color", "#f6f6f6");    
             $("#downloadList table tr:even").css("background-color", "#fff");    
         }   
  </script>
</body>
</html>