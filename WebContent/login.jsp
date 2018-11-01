<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ include file="/Master/loginheader.jsp"%>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/Css/Login.css" />
</head>
<body>

	<div class="main">
		<div class="login_content">
			<div class="left">
				<img src="<%=path%>/Images/login_img.png" />
				<h2>网站简介</h2>
				<ul>
					<li>医学、药学、生物最新图书推送</li>
					<li>关键词+主题词聚类技术，全面准确跟踪</li>
					<li>大数据整合，提供一站式生物医药图书信息帮助</li>
				</ul>
			</div>
			<div class="right">
				<div class=login_box>
					<h2>用户登录</h2>
					<form class="login-form">
						<p class="fieldset">
							<span class=title>用户名：</span> <label
								class="image-replace cd-username" for="signin-username">用户名</label>
							<input class="full-width has-padding has-border"
								id="signin-username" placeholder="输入用户名、手机号、邮箱" type="text"
								onblur="checkUserName();"> <span id="nametip"></span>
						</p>
						<p id="tip-username" class="tip red"></p>
						<p class="fieldset">
							<span class=title>密&nbsp;&nbsp;码：</span> <label
								class="image-replace cd-password" for="signin-password">密码</label>
							<input class="full-width has-padding has-border"
								id="signin-password" placeholder="输入密码" type="password"
								onblur="checkpsw();"> <span id="pwdtip"></span>
						</p>
						<p id="tip-password" class="tip red"></p>
						<p class="fieldset">
							<span class=title>验证码：</span> <input id="txt_ValidateCode"
								class="text-input validatecode" onblur="checkCode();"
								type="text">&nbsp;&nbsp;&nbsp;&nbsp;<img id="validimg" src="../code.do?name=user_reg" alt="看不清?换一张" style="cursor: pointer; vertical-align: top; font-size: 12px;"
                            onclick="this.src='../code.do?name=user_reg&id='+new Date();" /> <span id="codetip"></span>
						</p>
						<p id="tip-validatecode" class="tip red"></p>
						<!-- 					<p class="fieldset remember"> -->
						<!-- 						<input id="remember-me"  checked="checked" type="checkbox"> -->
						<!-- 						<label class="remember-txt">&nbsp;&nbsp;记住登录状态</label> -->
						<!-- 					</p> -->

						<p class="fieldset">
							<input id="loginbtn" class="full-width" value="登 录"
								type="button" onclick="login()">
						</p>
					</form>

				</div>
			</div>
		</div>
	</div>
    <%@ include file="/Master/footer.jsp"%>
</body>
<script type="text/javascript">
	var state1 = false;
	var state2 = false;
	var state3 = false;
	function checkUserName() {
		var name = $("#signin-username").val();
		if (name == "" || name == null) {
			$("#tip-username").removeClass("onCorrect").addClass("onError")
					.html("不可为空！");
		} else {
			$("#tip-username").removeClass("onError").addClass("onCorrect")
					.html("");
			state1 = true;
		}

	}

	function checkpsw() {

		var pwd = $("#signin-password").val();
		if (pwd == "" || pwd == null) {

			$("#tip-password").removeClass("onCorrect").addClass("onError")
					.html("不可为空！");
		} else {
			$("#tip-password").removeClass("onError").addClass("onCorrect")
					.html("");
			state2 = true;
		}

	}

	function checkCode() {
		var v_code = $("#txt_ValidateCode").val();
		if (v_code == "" || v_code == null) {
			$("#tip-validatecode").removeClass("onCorrect").addClass("onError")
					.html("不可为空！");
			check3 = false;
			
		}
		// var ver_code = getCookie("VCode").toLowerCase();
		var ver_code;
		$.ajax({
			url : "../code.do",
			type : 'post',
			async : false,
			dataType : 'text',
			data : {
				name : 'user_reg'
			},
			success : function(data) {
				ver_code = data;
			},
			error : function() {
				alert("获取验证码失败，请联系管理员");
			}
		});

		if (ver_code == v_code.toLowerCase()) {
			$("#tip-validatecode").addClass("onCorrect").html("");
			state3 = true;
		} else {
			$("#tip-validatecode").removeClass("onCorrect").addClass("onError")
					.html("验证码输入有误！");
			state3 = false;
		}
	}
	
	function login(){
		 checkUserName() ;
		 checkpsw();
		 checkCode();
		 if(state1&&state2&&state3){
			 var uName = $("#signin-username").val();
	         var pwd = $("#signin-password").val();
	            $.ajax({
	                url: "<%=path%>/UserLogin",
	                type: 'post',
	                async: false,
	                dataType: 'text',
	                data: { uName: uName, pwd: pwd },
	                success: function (result) {
	                    if (result == 1) {
	                        location.href = "<%=path%>/index.jsp";
	                    } else {
	                    	$("#tip-username").removeClass("onCorrect").addClass("onError").html("用户名或密码错误！");
	                        $("#tip-password").removeClass("onCorrect").addClass("onError").html("用户名或密码错误！");
	                        $('#validimg').attr("src",'../code.do?name=user_reg&id='+new Date());
	                    }
	                },
	                error: function () {
	                    alert("用户登录异常，请联系管理员");
	                    return;
	                }
	            });	 
		 }
		 else{
			 return;
			 
		 }
	
	}
	
	
	
</script>
</html>