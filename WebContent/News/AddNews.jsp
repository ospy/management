<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/Master/header.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title id="bookname"></title>

<link href="/Css/page.css" rel="stylesheet" />
<link href="/Css/bootstrap.css" rel="stylesheet" />
<link href="/Css/Login.css" rel="stylesheet" />
<link href="/Css/DiscuEdit.css" rel="stylesheet" />

<script src="/Js/bootstrap.js"></script>
<script src="/Js/jquery-ui.js"></script>
<script src="/Js/ckeditor/ckeditor.js"></script>

</head>

<script type="text/javascript">
	(function($) {

			$.getUrlParam = function(name)
		{
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
			}
		
	}(jQuery));
	//回车登录
	$(document).ready(function () {

		$(document).keyup(function (evnet) {
		if (evnet.keyCode == '13') {
			login();
		}
		});

		});
</script>
<body>

	<div class="main">

	
				<div class="m_l_row" id="m_l_row2">
				   <div class="m_l_r_top">
						<h3>
							<span>标题：</span>
						</h3>
				   
				   </div>
				   <div class="m_l_r_text longtext">
				   <input id="title" class="title" type="text" value="" style="width:260px;"/>
				   </div>
				   
					<div class="m_l_r_top">
						<h3>
							<span>作者简介</span>
						</h3>
						
					</div>
					<div class="m_l_r_text longtext">

						<textarea id="CKE_NewsAbstract" class="ckeditor"></textarea>

					</div>
				</div>

		</div>

		<div class="submit">
			<input class="submit_btn" type="button" value="提交"
				onclick="addNews()" />
		</div>
	
	<div id="loginModal" class="modal  fade" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document" style="width: 460px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h2 class="modal-title">
						用户登录<span id="currdate" class="currdate"></span>
						</h4>
				</div>
				<div id="loginmodal-body" class="modal-body">

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
								type="text">&nbsp;&nbsp;&nbsp;&nbsp;<img id="validimg"
								src="../code.do?name=user_reg" alt="看不清?换一张"
								style="cursor: pointer; vertical-align: top; font-size: 12px;"
								onclick="this.src='../code.do?name=user_reg&id='+new Date();" />
							<span id="codetip"></span>
						</p>
						<p id="tip-validatecode" class="tip red"></p>
						<!-- 					<p class="fieldset remember"> -->
						<!-- 						<input id="remember-me"  checked="checked" type="checkbox"> -->
						<!-- 						<label class="remember-txt">&nbsp;&nbsp;记住登录状态</label> -->
						<!-- 					</p> -->

						<p class="fieldset">
							<input id="loginbtn" class="full-width" value="登 录" type="button"
								onclick="login()">
						</p>
					</form>


				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</div>
</body>
<script type="text/javascript">
    
    function checklogin() {
   	 var username = '<%=username%>';
        if(username!='null'){
 
        }
        else{
       	 
       	 $("#loginModal").draggable({
                cursor: "move",
                handle: '.modal-header',
            });                         
      	  //modal居中  
      	  $("#loginModal").modal("show");  

        }
    }
    
        //上传书目
        function addNews() {
            var title = $("#title").val();         
            var content=CKEDITOR.instances['CKE_NewsAbstract'].getData();
                $.ajax({
                    url: "<%=path%>/AddNews",
                    type: 'post',
                    async: false,
                    dataType: 'text',
                    data: {title: title,content:content},
                    success: function (result) {
                       
                            alert(result);
                        
                    },
                    error: function () {
                        alert("新增新闻数据异常！");
                        return false;
                    }
                });
     
        }
     

       

        function SetAuthorInfo() {
            if ($("#ck_AuthorAbstract").attr("checked") == "checked") {
                $("#ck_AuthorAbstract").removeAttr("checked");
            } else {
                $("#ck_AuthorAbstract").attr("checked", "checked");
            }
        }

    

       function TestNull(str){
    	   
    	var temp =str.trim();
    	 if(temp.length==0){
    	     return null;
    	 }
    	 else{
    		 return str;
    	 }
    		
       }
      

        //字符串转json
        function strToJson(str) {
            var json = eval('(' + str + ')');
            return json;
        }
    </script>
<script type="text/javascript">
	//用户登录
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
				type : 'post',
				async : false,
				dataType : 'text',
				data : {
					uName : uName,
					pwd : pwd
				},
				success : function(result) {
					if (result == 1) {

						window.location.reload();
					} else {
						$("#tip-username").removeClass("onCorrect").addClass(
								"onError").html("用户名或密码错误！");
						$("#tip-password").removeClass("onCorrect").addClass(
								"onError").html("用户名或密码错误！");
						$('#validimg').attr("src",
								'../code.do?name=user_reg&id=' + new Date());
					}
				},
				error : function() {
					alert("用户登录异常，请联系管理员");
					return;
				}
			});
		} else {
			return;

		}
		
		 function Trim(str)
         { 
             return str.replace(/(^\s*)|(\s*$)/g, ""); 
     }  
	}
	
</script>
</html>