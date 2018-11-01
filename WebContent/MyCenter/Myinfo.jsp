<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ include file="/Master/header.jsp"%>
<%@ include file="/Master/topmenu.jsp"%>


<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<link type="text/css" rel="stylesheet" href="../Css/userinfo.css"/>
</head>
<body>
     <div class="navibar"><a href="#">首页</a> > 个人信息</div>
<div class="reg_content">
     <div class="reg_form">
                <form name="frmEmp" id="frmEmp">
                <h2>个人信息</h2>
                
                    <div class="form_item">
                        <span class="red star"></span><label>姓名：</label> <span id="txtName"></span><label>用户名：</label> <span id="loginid" ></span>  &nbsp; &nbsp;ID:<span id="reg_username" ></span>
                    </div>
                    <div class="form_item">
                        <span class="red star">*</span><label>
                            电子邮箱：</label>
                        <input id="reg_email" class="text-input  typeahead" type="text" onblur="checkEmail()" />&nbsp;&nbsp;<span
                            id="reg_emailTip"></span>
                    </div>
                    <div class="form_item">
                        <span class="red star">*</span><label>手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</label> <input id="txtMobile" class="text-input  typeahead"
                                type="text" onblur="checkMobile();" />&nbsp;&nbsp;<span id="txtMobileTip" class="TipItem"></span>
                    </div>
                <div class="form_item">
					<span class="red star">*</span><label> 学科专业：</label> <input id="txt_Spe"
						class="text-input  typeahead" type="text"
						onblur="checkSpe();" />&nbsp;&nbsp;<span id="txt_SpeTip"></span>
				</div>
                    <div class="form_item">
					<span class="red star">*</span><label> 职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</label> 
					    <input id="occu1"  name="ocu"  type="radio"  value="1"  checked="checked"/>医务人员
						<input id="occu2"   name="ocu"   type="radio"  value="2" />教师
						<input id="occu3"   name="ocu"  type="radio"  value="3" />研发人员
						<input id="occu4"   name="ocu"  type="radio"  value="4" />学生
						<input id="occu5"   name="ocu"  type="radio"  value="5" />其它
				</div>

                    
				<div class="form_item">
					<span class="red star">*</span><label> 工作单位：</label> <input id="txt_unit"
						class="text-input  typeahead" type="text"  value="" onblur="checkUnit();" />&nbsp;&nbsp;<span
						id="txt_unitTip"></span>
				</div>
				<div class="form_item">
					<span class="red star">*</span><label> 职务职称：</label> <input id="txt_Level"
						class="text-input  typeahead" type="text" onblur="checkLevel();" />&nbsp;&nbsp;<span
						id="txt_LevelTip"></span>
				</div>

				<div class="form_item">
					<span class="star red">*</span><label> 最高学历：</label> 
					<input id="edu1" name="edu" type="radio"  value="1" />专科
					<input id="edu2" name="edu"  type="radio"  value="2" />本科
					<input id="edu3" name="edu"  type="radio"  value="3" />硕士
					<input id="edu4" name="edu"  type="radio"   value="4" />博士
					<input id="edu5" name="edu"  type="radio"   value="5"  checked="checked"/>其它
				</div>
                    <div class="form_item">
                        <input value="提  交" type="button" class="submit" onclick="updatememberinfo();" />
                    </div>
                </form>
            </div>
</div>
<script type="text/javascript">
	(function($) {

			$.getUrlParam = function(name)
		{
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
			}
		
	}(jQuery));
	
	
</script>
  <%@ include file="/Master/footer.jsp"%>
	<script type="text/javascript">

	  getMemberInfo();
	  var state1=false;
 	  var state2=false;
      var state3=false;
      var state4=false;
	  var state5=false;
	  
      //检查电子邮件
      function checkEmail() {
    	  var uid=$.getUrlParam('uid');
    	  var email = $("#reg_email").val();
          if (email == "" || email == null) {
              $("#reg_emailTip").removeClass("onCorrect");
              $("#reg_emailTip").addClass("onError").html("不可为空！");
              state1 = false;
          } else {
              var reg = /^([a-zA-Z0-9_-])+@@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
             // if (!reg.test(email)) {
              if (1==2) {
                  $("#reg_emailTip").removeClass("onCorrect");
                  $("#reg_emailTip").addClass("onError").html("电子邮箱输入有误！");
                  state1 = false;
              } else {
                  if (email.length > 5 && email.length < 31) {
                      $.ajax({
                          url: "<%=path%>/CheckSelfEmail",
                          type: 'post',
                          async: false,
                          dataType: 'text',
                          data: { Email: email,Uid:uid },
                          success: function (data) {
                        		if (data == "1") {
                                    $("#reg_emailTip").removeClass("onError").html("");
                                    $("#reg_emailTip").addClass("onCorrect").html("当前绑定邮箱！");
                                    state1 = true;
                                }
                            	else if (data == "2") {
                                    $("#reg_emailTip").removeClass("onCorrect").html("");
                                    $("#reg_emailTip").addClass("onError").html("该邮箱已使用，请更换！");
                                } else {
                                	$("#reg_emailTip").removeClass("onError").html("");
                                	$("#reg_emailTip").addClass("onCorrect").html("该邮箱可以使用！");
                                    state1 = true;               
                                }
                          },
                          error: function (err) {
                              alert("检查邮箱失败，请联系管理员");
                              state1 = false;
                          }
                      });
                  } else {
                      $("#reg_emailTip").removeClass("onCorrect");
                      $("#reg_emailTip").addClass("onError").html("长度限制为6-30位！");
                      state1 = false;
                  }
              }

          }
      }
/* 	   function checkName(){
	       var name = $("#txtName").val();
            if (name == "" || name == null) {
                $("#txtNameTip").removeClass("onCorrect").addClass("onError").html("输入不能小于2个字符！");
            }
            else if(name.length<2){
              $("#txtNameTip").removeClass("onCorrect").addClass("onError").html("输入不能小于2个字符！");    
            }
	         else {
              $("#txtNameTip").removeClass("onError").addClass("onCorrect").html("");
             state1 = true;
            }
	   
	   } */
	
	  	   function checkUnit(){
	  	   
	        var unit = $("#txt_unit").val();
            if (unit == "" || unit == null) {
                
                $("#txt_unitTip").removeClass("onCorrect").addClass("onError").html("输入不能小于6个字符！");
            }
            else if(unit.length<6){
              $("#txt_unitTip").removeClass("onCorrect").addClass("onError").html("输入不能小于6个字符！");    
            }
	         else {
              $("#txt_unitTip").removeClass("onError").addClass("onCorrect").html("");
              state2 = true;
            }
	   
	   }
	
	 function checkLevel(){
	        var level = $("#txt_Level").val();
            if (level == "" || level == null) {
                $("#txt_LevelTip").removeClass("onCorrect").addClass("onError").html("输入不能小于2个字符！");
            }
            else if(level.length<2){
              $("#txt_LevelTip").removeClass("onCorrect").addClass("onError").html("输入不能小于2个字符！");    
            }
            else{
              $("#txt_LevelTip").removeClass("onError").addClass("onCorrect").html("");
               state3 = true;
            }
	   
	   }
	
	   function checkSpe(){
	        var spe = $("#txt_Spe").val();
            if (spe == "" || spe == null) {
                $("#txt_SpeTip").removeClass("onCorrect").addClass("onError").html("输入不能为空！");
            }
            else if(spe.length<2){
              $("#txt_SpeTip").removeClass("onCorrect").addClass("onError").html("输入不能小于2个字符！");    
            }
	         else {
              $("#txt_SpeTip").removeClass("onError").addClass("onCorrect").html("");
               state4 = true;
            }
	   
	   }
	
        function checkMobile() {
        	var uid=$.getUrlParam('uid');
        	var mobile = $("#txtMobile").val();
            if (mobile == "" || mobile == null) {
                $("#txtMobileTip").addClass("onError").html("手机号不能为空！");
                $("#btnSendVerCode").attr("disabled", "disabled");
            } else {
                var reg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
                if (!reg.test(mobile)) {
                    $("#txtMobileTip").removeClass("onCorrect").html("");
                    $("#txtMobileTip").addClass("onError").html("手机号输入错误！");
                    $("#btnSendVerCode").attr("disabled", "disabled");
                   }
                 else {
                    $.ajax({
                        url: "<%=path%>/CheckSelfMobile",
                        type: 'post',
                        async: false,
                        dataType: 'text',
                        data: { Mobile: mobile,Uid:uid },
                        success: function (data) {
                        	
                        	if (data == "1") {
                                $("#txtMobileTip").removeClass("onError").html("");
                                $("#txtMobileTip").addClass("onCorrect").html("当前绑定号码！");
                                state5 = true;
                            }
                        	else if (data == "2") {
                                $("#txtMobileTip").removeClass("onCorrect").html("");
                                $("#txtMobileTip").addClass("onError").html("该号码已使用，请更换！");
                            } else {
                            	$("#txtMobileTip").removeClass("onError").html("");
                            	$("#txtMobileTip").addClass("onCorrect").html("该号码可以使用！");
                                state5 = true;               
                            }
                        },
                        error: function (err) {
                        
                             alert("检查手机号失败，请联系管理员");
                        }
                    });
               }
            }
        }
	
      //获取个人信息
        function getMemberInfo(){
        	var uid=$.getUrlParam('uid');
        	 if(uid!=""&&uid!="null"){
	            $.ajax({
	                url: "<%=path%>/GetMemberInfo",
	                type: 'post',
	                async: false,
	                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	                dataType: 'json',
	                data: {uid:uid},
	                success: function (result) {
	                //address,capacity,education,member,mobile,name,occupation,speciality	
	                	if (result != 0) {
	                		$("#reg_username").html(result[0].id);
	                		$("#loginid").html(result[0].loginid);
	                		$("#txtName").html(result[0].name);
	                		$("#reg_email").val(result[0].email);
	                		$("#txtMobile").val(result[0].mobile);
	                		$("#txt_Spe").val(result[0].speciality);
	                		loginid
	                		$("#txt_unit").val(result[0].address);
	                		switch(result[0].occupation)
	                		{
	                		case "1":
	                			$("#occu1").attr('checked','true');
	                		  break;
	                		case "2":
	                			$("#occu2").attr('checked','true');
	                		  break;
	                		case "3":
	                			$("#occu3").attr('checked','true');
	                		  break;
	                		case "4":
	                			$("#occu4").attr('checked','true');
	                		  break;
	                		case "5":
	                			$("#occu5").attr('checked','true');
	                		  break;
	                		default:
	                			$("#occu5").attr('checked','true');
	                		}
	                    
	                		$("#txt_Level").val(result[0].capacity);
	                		switch(result[0].education)
	                		{
	                		case "1":
	                			$("#edu1").attr('checked','true');
	                		  break;
	                		case "2":
	                			$("#edu2").attr('checked','true');
	                		  break;
	                		case "3":
	                			$("#edu3").attr('checked','true');
	                		  break;
	                		case "4":
	                			$("#edu4").attr('checked','true');
	                		  break;
	                		case "5":
	                			$("#edu5").attr('checked','true');
	                		  break;
	                		default:
	                			$("#edu5").attr('checked','true');
	                		}
				
	                	} 
	                },
	                error: function () {
	                    alert("读取个人信息异常！");
	                    return false;
	                }
	            });	 
        	 }
        	 else{
        		 
        		 alert("请登录！");
        	 }
           
	
	}
	
	           function updatememberinfo() {
	        	   var uid=$.getUrlParam('uid'); 
	        	   checkEmail();
	        	   checkMobile();
	        	   checkSpe();
	        	   checkLevel();
	        	   checkUnit();
	        	   

	               if( state1&&state2&& state3&&state4&&state5){
                     $.ajax({
                        url: "<%=path%>/M_UpdateMemberInfo",
							type : 'post',
							async : false,
							dataType : 'text',
							data : {
								id: uid,
								occupation : $("input[name=ocu]:checked").val(),
								name : $("#txtName").html(),
								email : $("#reg_email").val(),
								mobile : $("#txtMobile").val(),
								address : $("#txt_unit").val(),
								capacity : $("#txt_Level").val(),
								speciality : $("#txt_Spe").val(),
								education : $("input[name=edu]:checked").val()
							},
							success : function(data) {
								if (data == "true") {
									var $copysuc = $("<div class='alert-tips'><div class='alert-tips-wrap'>恭喜您！提交成功<span id='second'>6</span>秒钟后将自动跳转到登录页！</div></div>");
									$("body").find(".alert-tips").remove()
											.end().append($copysuc);
									timedCount();
									$(".alert-tips").fadeOut(6000);
									
								} else {
									$copysuc = $("<div class='alert-tips'><div class='alert-tips-wrap'>提交失败！请发送问题至客服邮箱：****@163.com</div></div>");
									$("body").find(".alert-tips").remove()
											.end().append($copysuc);
									$(".alert-tips").fadeOut(6000);
								}
							},
							error : function(err) {

							}
						});

			} else {
				return;
			}
		}
		function timedCount() {
		    var c = $('#second').html(); 
		    c--;
		    
		    if(c > 0){	
		        $('#second').html(c);			
				setTimeout("timedCount()", 1000);
				
			}
			else{
			 
			}
			}
	</script>
<body>

</body>
</html>