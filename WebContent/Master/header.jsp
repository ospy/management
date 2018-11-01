
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
    String path = request.getContextPath(); 
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%> 
 <% String username = (String)session.getAttribute("username"); 
          String uid = (String)session.getAttribute("uid");
          String account = (String)session.getAttribute("account");
		%>
<link href="/Css/header.css" rel="stylesheet" />
<script  type="text/javascript" src="<%=path%>/Js/jquery-1.9.1.js" ></script>
<script type="text/javascript" src="<%=path%>/Js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/Js/easyui-lang-zh_CN.js"></script>


<script type="text/javascript">


       function Search(){
    	  var keys =$("#keys").val();
    	  alert(keys.length);
    	  if(keys==""){
    	      alert("搜索条件不能为空！");
    	  }
    	  else if(keys.length>36){
    		  alert("搜索条件限制在36个中文字符！")
    		  
    	  }
    	  else{
    		  var url ="http://localhost:8080/SearchList/searchlist.jsp?search="+encodeURI(keys);
        	  window.location.href =url;
    	  }
       }

       function checklogin(){
    	   var username = "<%=username%>";
    	   var uid = "<%=uid%>";
    	   var account = "<%=account%>";
           if(username!="null"){
        	   $("#accountHead").html("账户余额：<a href='#'>"+account+"资源点</a>");
        	   $("#mycenter").css("display","block");
             $("#username").html("<a href='/MyCenter/Myinfo.jsp'>"+username+"</a>");
             $("#login").html('<a href="<%=path%>/LoginOut">注&nbsp;&nbsp;销</a>');
           }
           else{
        	   $("#accountHead").html("");
        	   $("#mycenter").css("display","none");
        	   $("#login").html('<a href="/Member/login.jsp">登&nbsp;&nbsp;录</a>');
        	   $("#username").html("游客");
        	   location.href="<%=path%>/login.jsp";
           }
    	   
       }


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

	
		
		 function Trim(str)
         { 
             return str.replace(/(^\s*)|(\s*$)/g, ""); 
     }  
	
</script>