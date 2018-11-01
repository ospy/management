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
		<div class="topinfo">
			<h3>书目上传</h3>
			<div class="info_left">
				<input id="ck_img" type="checkbox" value="" checked="checked"
					onclick="SetImg();" /> <img id="s_img" src="" width="350"
					height="350" />
			</div>
			<div class="info_list">
				<ul>

					<li><span style="color: red">*</span>&nbsp;学&nbsp;&nbsp;&nbsp;科1：<input
						id="s_spec1" disabled="disabled" class="input" type="text"
						value="" /><input id="i_spid1" style="display: none"
						disabled="disabled" class="input" type="text" value="" /><input
						style="line-height: 20px" type="button" data-target="#SpecModal"
						data-toggle="modal" value="选择..." onclick="setMark(1)" /></li>

					<li>&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;科2：<input id="s_spec2"
						disabled="disabled" class="input" type="text" value="" /><input
						id="i_spid2" style="display: none" disabled="disabled"
						class="input" type="text" value="" /><input
						style="line-height: 20px" type="button" data-target="#SpecModal"
						data-toggle="modal" value="选择..." onclick="setMark(2)" />&nbsp;&nbsp;<a
						onclick="clearSpec(2)">清空</a></li>


					<li>&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;科3：<input id="s_spec3"
						disabled="disabled" class="input" type="text" value="" /><input
						id="i_spid3" style="display: none" disabled="disabled"
						class="input" type="text" value="" /><input
						style="line-height: 20px" type="button" data-target="#SpecModal"
						data-toggle="modal" value="选择..." onclick="setMark(3)" />&nbsp;&nbsp;<a
						onclick="clearSpec(3)">清空</a></li>

					<li><span style="color: red">*</span>&nbsp;标&nbsp;&nbsp;&nbsp;题&nbsp;&nbsp;：<input
						id="title" class="title" type="text" value="" /><input
						type="button" onclick="dd();" value="当当" style="line-height: 20px" /><input
						onclick="    jd();" type="button" value="京东"
						style="line-height: 20px" /><input onclick="    ymx();"
						type="button" value="亚马逊" style="line-height: 20px" /></li>



					<li>&nbsp;&nbsp;&nbsp;&nbsp;U&nbsp;R&nbsp;L&nbsp;：<input
						id="url" class="title" type="text" /><input id="DetailSearch"
						style="line-height: 20px" type="button" value="获取书目"
						onclick="CheckButton();" /> <img id="loading"
						src="/Images/icons/onLoad.gif" style="display: none" /><span
						id="DetailTip" style="display: none; color: #cc3300;"> 获取中</span></li>
					<!--                        <a href="/help/upload_help.html" target="_blank" class="helplink">使用帮助</a> -->


				</ul>
			</div>
			<div class="clear"></div>

		</div>

		<div class="clear"></div>
		<div class="u_contents">
			<div class="u_content" id="u_info0">

				<div class="m_l_row" id="m_l_row1">
					<div id="ctl00_ContentPlaceHolder1_DiscuContent1_lbldown"
						class="m_l_r_top">
						<h3>百度云盘</h3>
						<a class="detail" href="javascript:;"> <img
							src="/Images/icons/layer_down.gif"></a>
					</div>
					<div id="filebox" class="m_l_r_text" style="margin-top: 8px">
						<div>
							<span class="red">*</span>云盘地址：<input id="ypAddress"
								class="input" style="width: 360px" type="text" value="" />密码：<input
								id="ypKeys" class="input" style="width: 120px" type="text"
								value="" />
						</div>
						<div>
							<span class="red">*</span>文件名称：<input id="uploadbox" type="text"
								value="" /><label for="upload" class="uploadbtn"> <span>选择文件...</span>
							</label><input id="upload" onchange="getfileinfo();" type="file"
								style="display: none" />
						</div>
						<div style="display: none;">
							<input id="filesize" type="text" value=""><input
								id="filetype" type="text" value="" />
						</div>
					</div>
				</div>
				<script type="text/javascript">  
     function getfileinfo() {  
         var f = document.getElementById("upload").files;  
        
         //名称  
        
         $("#uploadbox").val(f[0].name);
         //大小 字节 
          $("#filesize").val(f[0].size);
          
        //类型  
        $("#filetype").val(f[0].name.substr(f[0].name.indexOf(".")));
         
   }  
     
    
 </script>

				<div class="m_l_row" id="m_l_row2">
					<div class="m_l_r_top">
						<h3>
							<span>作者简介</span>
						</h3>
						<a class="detail"> <img src="/Images/icons/layer_down.gif"></a>
					</div>
					<div class="m_l_r_text longtext">

						<input id="ck_AuthorAbstract" onclick="SetAuthorInfo();"
							type="checkbox" value="" checked="checked" />
						<textarea id="CKE_AuthorAbstract" class="ckeditor"></textarea>

					</div>
				</div>

				<div class="m_l_row" id="m_l_row3">
					<div class="m_l_r_top">
						<h3>
							<span>内容简介</span>
						</h3>
						<a class="detail"> <img src="/Images/icons/layer_down.gif"></a>
					</div>
					<div class="m_l_r_text longtext">

						<input id="ck_contentInfo" onclick="SetContentInfo();"
							type="checkbox" value="" checked="checked" />
						<textarea id="CKE_ContentIntro" class="ckeditor"></textarea>

					</div>
				</div>

				<div class="m_l_row" id="m_l_row4">
					<div class="m_l_r_top">
						<h3>
							<span>目录</span>
						</h3>
						<a class="detail"> <img src="/Images/icons/layer_down.gif"></a>
					</div>
					<div class="m_l_r_text longtext" id="index">

						<input id="ck_index" type="checkbox" name="setCheck"
							onclick="SetIndex();" value="" checked="checked" />
						<textarea id="CKEditorIndex" class="ckeditor"></textarea>
					</div>
				</div>

				<div class="m_l_row" id="m_l_row5">
					<div class="m_l_r_top">
						<h3>出版信息</h3>
						<a class="detail" href="javascript:;"> <img
							src="/Images/icons/layer_down.gif"></a>
					</div>
					<div class="m_l_r_text">

						<ul class="cols">
							<li class="col3"><input id="ck_SeriesID" type="checkbox"
								name="setCheck" onclick="SetSeriesID();" value=""
								checked="checked" /><span class="title">丛书名：</span><input
								id="SeriesID" class="input" type="text" value="" /></li>
							<li class="col3"><input id="ck_bookPrice" type="checkbox"
								name="setCheck" onclick="SetBookPrice();" value=""
								checked="checked" /><span class="title">价格：</span><input
								id="bookPrice" class="input" type="text" value="" /></li>
							<li class="col3"></li>
						</ul>

						<ul class="cols">
							<li class="col3"><input id="ck_AuthorID" type="checkbox"
								value="" name="setCheck" onclick="SetAuthorID();"
								checked="checked" /><span class="title">作 者：</span><input
								id="AuthorID" class="input" type="text" value="" /></li>
							<li class="col3"><input id="ck_PressID" type="checkbox"
								value="" name="setCheck" onclick="SetPressID();"
								checked="checked" /><span class="title">出版社：</span><input
								id="PressID" class="input" type="text" value="" /></li>
							<li class="col3"><input id="ck_PublishTimeID"
								type="checkbox" name="setCheck" onclick="SetPublishTimeID();"
								value="" checked="checked" /><span class="title">出版日期：</span><input
								id="PublishTimeID" class="input" type="text" value="" /></li>
						</ul>
						<div class="PublishInfo">
							<input id="ck_PublishInfo" type="checkbox" name="setCheck"
								onclick="SetPublishInfo();" value="" checked="checked" />&nbsp;&nbsp;&nbsp;&nbsp;出版信息
							<ul class="cols">
								<li class="col3"><span class="title"
									style="padding-left: 12px">版 次：</span><input id="EditionID"
									class="input" type="text" value="" /></li>
								<li class="col3"><span class="title"
									style="padding-left: 12px">页 数：</span><input id="PagesID"
									class="input" type="text" value="" /></li>
								<li class="col3"><span class="title"
									style="padding-left: 12px">字 数：</span><input id="WordNumberID"
									class="input" type="text" value="" /></li>

							</ul>

							<ul class="cols">
								<li class="col3"><span class="title"
									style="padding-left: 12px">印刷时间：</span><input
									id="PrintingTimeID" class="input" type="text" value="" /></li>
								<li class="col3"><span class="title"
									style="padding-left: 12px">开 本：</span><input id="BookSizeID"
									class="input" type="text" value="" /></li>
								<li class="col3"><span class="title"
									style="padding-left: 12px">纸 张：</span><input id="PaperID"
									class="input" type="text" value="" /></li>

							</ul>

							<ul class="cols">
								
								<li class="col3 ws4"><span class="title"
									style="padding-left: 12px">I S B N：</span><input id="ISBNID"
									class="input" type="text" value="" /></li>
								<li class="col3"><span class="title"
									style="padding-left: 12px">包 装：</span><input id="PackageID"
									class="input" type="text" value="" /></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="submit">
			<input class="submit_btn" type="button" value="提交"
				onclick="SubmitBookInfo()" />
		</div>
	</div>
	<div class="modal fade" id="SpecModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h2 class="modal-title" id="myModalLabel">学科分类</h2>
				</div>
				<div class="modal-body confirmcenter">

					<div class="modal-body">
						<div id="spec-content" class="confirmcenter">
							一区
							<div>
								<ul class="spec">
									<li><a onclick="getDept('200','全科医学')" class="200">全科医学</a></li>

								</ul>
							</div>
							<br> <br> 二区
							<div>
								<ul class="spec">
									<li><a onclick="getDept('400','外科综合')" class="400">外科综合</a></li>
									<li><a onclick="getDept('401','普通外科')" class="401">普通外科</a></li>
									<li><a onclick="getDept('402','神经外科')" class="402">神经外科</a></li>
									<li><a onclick="getDept('403','骨科')" class="403">骨科</a></li>
									<li><a onclick="getDept('404','泌尿外科')" class="404">泌尿外科
									</a></li>
									<li><a onclick="getDept('405','胸外科')" class="405">胸外科</a></li>
									<li><a onclick="getDept('406','心血管外科')" class="406">心血管外科</a></li>
									<li><a onclick="getDept('407','烧伤科')" class="407">烧伤科</a></li>
								</ul>
							</div>
							<br> 三区
							<div>
								<ul class="spec">
									<li><a onclick="getDept('500','妇产科')" class="500">妇产科</a></li>
									<li><a onclick="getDept('700','儿科')" class="700">儿科</a></li>
									<li><a onclick="getDept('800','小儿外科')" class="800">小儿外科</a></li>
									<li><a onclick="getDept('1000','眼科')" class="1000">眼科</a></li>
									<li><a onclick="getDept('1100','耳鼻咽喉科')" class="1100">耳鼻咽喉科</a></li>
									<li><a onclick="getDept('1200','口腔科')" class="1200">口腔科</a></li>
									<li><a onclick="getDept('1300','皮肤性病科')" class="1300">皮肤性病科</a></li>
									<li><a onclick="getDept('1400','整形美容科')" class="1400">整形美容科</a></li>
									<li><a onclick="getDept('1600','传染科')" class="1600">传染科</a></li>
									<li><a onclick="getDept('1900','肿瘤科')" class="1900">肿瘤科</a></li>
									<li><a onclick="getDept('2000','急诊医学科')" class="2000">急诊医学科</a></li>
									<li><a onclick="getDept('2800','重症医学科')" class="2800">重症医学科</a></li>
								</ul>
							</div>
							<br> <br> 四区
							<div>
								<ul class="spec">
									<li><a onclick="getDept('2100','康复医学科')" class="2100">康复医学科</a></li>
									<li><a onclick="getDept('2200','运动医学科')" class="2200">运动医学科</a></li>
									<li><a onclick="getDept('2300','职业病科')" class="2300">职业病科</a></li>
									<li><a onclick="getDept('2500','特种医学')" class="2500">特种医学</a></li>
									<li><a onclick="getDept('2600','麻醉科')" class="2600">麻醉科</a></li>
									<li><a onclick="getDept('2700','疼痛科')" class="2700">疼痛科</a></li>
									<li><a onclick="getDept('1500','精神科')" class="1500">精神科</a></li>
									<li><a onclick="getDept('5000','中医科')" class="5000">中医科</a></li>
									<li><a onclick="getDept('5200','中西医结合科')" class="5200">中西医结合科</a></li>
									<li><a onclick="getDept('5300','护理学')" class="5300">护理学</a></li>
								</ul>
							</div>
							<br> 五区
							<div>
								<ul class="spec">
									<li><a onclick="getDept('3000','医学检验科')" class="3000">医学检验科</a></li>
									<li><a onclick="getDept('3100','病理科')" class="3100">病理科</a></li>
									<li><a onclick="getDept('3200','影像科综合')" class="3200">影像科综合</a></li>
									<li><a onclick="getDept('3201','X线诊断')" class="3201">X线诊断</a></li>
									<li><a onclick="getDept('3202','CT诊断')" class="3202">CT诊断</a></li>
									<li><a onclick="getDept('3203','MRI')" class="3203">MRI</a></li>
									<li><a onclick="getDept('3204','核医学')" class="3204">核医学</a></li>
									<li><a onclick="getDept('3205','超声诊断')" class="3205">超声诊断</a></li>
									<li><a onclick="getDept('3206','心电诊断')" class="3206">心电诊断</a></li>
									<li><a onclick="getDept('3207','脑电诊断')" class="3207">脑电诊断</a></li>
									<li><a onclick="getDept('3208','神经肌肉电图')" class="3208">神经肌肉电图</a></li>
									<li><a onclick="getDept('3209','介入放射学')" class="3209">介入放射学</a></li>
									<li><a onclick="getDept('3210','放疗')" class="3210">放疗</a></li>
									<li><a onclick="getDept('7100','营养保健科')" class="7100">营养保健科</a></li>
									<li><a onclick="getDept('8000','药剂科')" class="8000">药剂科</a></li>
								</ul>
							</div>
							<br> <br> 六区
							<div>
								<ul class="spec">
									<li><a onclick="getDept('6000','基础综合')" class="6000">基础综合</a></li>
								</ul>
							</div>
						</div>
					</div>

				</div>
				<div style="padding-left: 20px">
					选择类别：<span id="spec" style="color: black; font-weight: bold"></span><span
						id="dpid" style="display: none"></span><span id="tag"
						style="display: none"></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal"
						onclick="setDept()">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						关闭</button>
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>

	<!-- /.modal -->

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
    
  

    	checklogin();
    	
    	GetSpec();
    	
    	
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
    

      
        //当前选择的学科
        function getDept(dpid, spec) {
            $("#spec").html(spec);
            $("#dpid").html(dpid);
            $(".spec li a").removeClass("dept")
            $("." + dpid).addClass("dept");
        }

        //确定选择的学科
        function setDept() {
            var tag = $("#tag").html();
            if (tag == 1) {
                $("#s_spec1").val($("#spec").html());
                $("#i_spid1").val($("#dpid").html());
            }
            if (tag == 2) {
                $("#s_spec2").val($("#spec").html());
                $("#i_spid2").val($("#dpid").html());
            }
            if (tag == 3) {
                $("#s_spec3").val($("#spec").html());
                $("#i_spid3").val($("#dpid").html());
            }
        }

        //设置当前点击选择学科几
        function setMark(tag) {
            $("#tag").html(tag);
            if (tag == 1) {
                $("#spec").html($("#s_spec1").val());
                $("#dpid").html($("#i_spid1").val());
                $("." + $("#i_spid1").val()).addClass("dept");
            }
            if (tag == 2) {
                $("#spec").html($("#s_spec2").val());
                $("#dpid").html($("#i_spid2").val());
                $("." + $("#i_spid2").val()).addClass("dept");
            }
            if (tag == 3) {
                $("#spec").html($("#s_spec3").val());
                $("#dpid").html($("#i_spid3").val());
                $("." + $("#i_spid3").val()).addClass("dept");
            }
        }

        //清空学科
        function clearSpec(tag) {
            if (tag == 2) {
                $("#s_spec2").val('');
                $("#i_spid2").val('');
            }
            if (tag == 3) {
                $("#s_spec3").val('');
                $("#i_spid3").val('');
            }
        }

        //当当
        function dd() {
            var title = $("#title").val();
            var url="http://search.dangdang.com/?key="+title+"&rd_flag=noresult";
            window.open(url);

        }

        //京东
        function jd() {
            var title = $("#title").val();
            
            	 var url="http://search.jd.com/Search?keyword="+title+"&enc=utf-8";
            window.open(url);
        }
        //亚马逊
        function ymx() {
            var title = $("#title").val();
            var url="http://www.amazon.cn/gp/search/ref=sr_hi_2?&keywords="+title;
            window.open(url);
        }

        //获取书目
        function CheckButton() {
            var url = $("#url").val();
            //$("#DetailSearch").attr("disabled", "disabled");
            $("#loading").removeAttr("style");
            $("#DetailTip").removeAttr("style").attr("style", "color:#cc3300;");
            $.ajax({
                url: "/Getbookinfo",
                type: 'post',
                async: false,
                dataType: 'json',
                data: { url: url },
                success: function (result) {

                	     $("#title").val(result[0].bookTitle);

                        if ($("#ck_AuthorAbstract").attr("checked") == "checked") {
                            CKEDITOR.instances['CKE_AuthorAbstract'].setData(result[0].authorIntro);
                        }

                        if ($("#ck_contentInfo").attr("checked") == "checked") {
                            CKEDITOR.instances['CKE_ContentIntro'].setData(result[0].contentIntro);
                        }
                        if ($("#ck_index").attr("checked") == "checked") {
                            CKEDITOR.instances['CKEditorIndex'].setData(result[0].contentIndex);
                        }

                        if ($("#ck_img").attr("checked") == "checked") {
                            $("#s_img").attr("src", result[0].picUrl);
                        }

                        if ($("#ck_bookPrice").attr("checked") == "checked") {
                            $("#bookPrice").val(result[0].price);
                        }

                        if ($("#ck_SeriesID").attr("checked") == "checked") {
                            $("#SeriesID").val(result[0].seriesName);
                        }

                        if ($("#ck_AuthorID").attr("checked") == "checked") {
                            $("#AuthorID").val(result[0].authorName);
                        }

                        if ($("#ck_PressID").attr("checked") == "checked") {
                            $("#PressID").val(result[0].pressName);
                        }

                        if ($("#ck_PublishTimeID").attr("checked") == "checked") {
                            $("#PublishTimeID").val(result[0].publishTime);
                        }

                        if ($("#ck_PublishInfo").attr("checked") == "checked") {
                            $("#EditionID").val(result[0].edition);
                            $("#PagesID").val(result[0].pages);
                            $("#WordNumberID").val(result[0].wordNumber);
                            $("#PrintingTimeID").val(result[0].printingTime);
                            $("#BookSizeID").val(result[0].bookSize);
                            $("#PaperID").val(result[0].paper);
                            //$("#LanguageID").val(result[0].language);
                            $("#ISBNID").val(result[0].ISBN);
                            $("#PackageID").val(result[0].pack);
                        }
                    
                    $("#loading").css("display", "none");
                    $("#DetailTip").css("display", "none");
                    $("#Picstatus").html("1");
                },
                error: function () {
                    alert("获取书目异常！");
                    return false;
                }
            });
        }
        //上传书目
        function SubmitBookInfo() {
            
            var img_url = $("#s_img").attr("src")//封面图片地址
            var title = $("#title").val();
            
               var spids = $("#i_spid1").val() ;
            
                if ($("#i_spid2").val() != "") {
                     spids+= "," + $("#i_spid2").val();
                }
                if ($("#i_spid3").val() != "") {
                	spids+= "," + $("#i_spid3").val();
                }

                var specs = $("#s_spec1").val();
                if ($("#s_spec2").val() != "") {
                    specs += "," + $("#s_spec2").val();
                }
                if ($("#s_spec3").val() != "") {
                	specs += ","  + $("#s_spec3").val();
                }
                
                var AuthorIntro=CKEDITOR.instances['CKE_AuthorAbstract'].getData();
                var ContentIntro=CKEDITOR.instances['CKE_ContentIntro'].getData();
                var Index=CKEDITOR.instances['CKEditorIndex'].getData();
            
                
                
                
                
                var s_path = $("#ypAddress").val();
                var key=$("#ypKeys").val();
                var filename = $("#uploadbox").val();
                var filesize = $("#filesize").val();
                var filetype = $("#filetype").val();
                if(filename==""||filesize==""||filetype==""){
                	alert("上传文件名不能为空！");
                	return;
                }
                if($("#i_spid1").val()==""){
                	alert("主学科分类不能为空！");
                	return;
                }
                //var s_pwd = $("#ypPwd").val();
                
                var Series = TestNull($("#SeriesID").val());//丛书名
                var Price = TestNull($("#bookPrice").val());//价格
                var Author = TestNull($("#AuthorID").val());//作者
                var Press = TestNull($("#PressID").val());//出版社
                var PublishTime = TestNull($("#PublishTimeID").val());//出版日期
                var Edition = TestNull($("#EditionID").val());//版次
                var Pages = TestNull($("#PagesID").val());//页数
                var WordNumber = TestNull($("#WordNumberID").val());//字数
                var PrintingTime = TestNull($("#PrintingTimeID").val());//印刷时间
                var BookSize = TestNull($("#BookSizeID").val());//开本
                var Paper = TestNull($("#PaperID").val());//纸张
                //var Impression = $("#ImpressionID").val();//印次
                var ISBN= TestNull($("#ISBNID").val());//ISBN
                var Package= TestNull($("#PackageID").val());//包装

                //var catalog = $("#CKEditorIndex").val();//目录
                //var content = $("#CKE_ContentIntro").val();//内容
                var AuthorIntroduce = CKEDITOR.instances['CKE_AuthorAbstract'].getData();//作者简介
                var content = CKEDITOR.instances['CKE_ContentIntro'].getData();
                var catalog = CKEDITOR.instances['CKEditorIndex'].getData();

                content = escape(content);
                catalog = escape(catalog);
                
                

                $.ajax({
                    url: "<%=path%>/AddBook",
                    type: 'post',
                    async: false,
                    dataType: 'text',
                    data: {
                        title: title, spids: spids, specs: specs,price: Price,s_path: s_path,key:key, img_url: img_url,
                        filename:filename,filesize:filesize,filetype:filetype,AuthorIntro:AuthorIntro,ContentIntro:ContentIntro,Index:Index,Series:	Series,Price:Price,Author:Author,Press:	Press,PublishTime:PublishTime,Edition:Edition,Pages:Pages,WordNumber:WordNumber,PrintingTime:PrintingTime,BookSize:BookSize,Paper:Paper,ISBN:ISBN,Package:Package
                    },
                    success: function (result) {
                       
                            alert(result);
                        

                    },
                    error: function () {
                        alert("新增书目数据异常！");
                        return false;
                    }
                });
     
        }
     

        //上传封面
        function SendImg() {
            var discuId = $("#discuId").html();
            $("#form_upload").ajaxForm({
                url: "/EditDiscu/SendImg",
                type: 'post',
                async: false,
                dataType: 'text',
                data: { discuId: discuId },
                success: function (result) {
                    if (result != "") {
                        $("#img_url").html(result);
                        $("#s_img").attr("src", "/Pic/" + result);
                        $("#Picstatus").html("1");
                    }
                },
                error: function () {
                    alert("上传封面异常！");
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

        function SetContentInfo() {
            if ($("#ck_contentInfo").attr("checked") == "checked") {
                $("#ck_contentInfo").removeAttr("checked");
            } else {
                $("#ck_contentInfo").attr("checked", "checked");
            }
        }

        function SetIndex() {
            if ($("#ck_index").attr("checked") == "checked") {
                $("#ck_index").removeAttr("checked");
            } else {
                $("#ck_index").attr("checked", "checked");
            }
        }

        function SetImg() {
            if ($("#ck_img").attr("checked") == "checked") {
                $("#ck_img").removeAttr("checked");
            } else {
                $("#ck_img").attr("checked", "checked");
            }
        }

        function SetBookPrice() {
            if ($("#ck_bookPrice").attr("checked") == "checked") {
                $("#ck_bookPrice").removeAttr("checked");
            } else {
                $("#ck_bookPrice").attr("checked", "checked");
            }
        }

        function SetSeriesID() {
            if ($("#ck_SeriesID").attr("checked") == "checked") {
                $("#ck_SeriesID").removeAttr("checked");
            } else {
                $("#ck_SeriesID").attr("checked", "checked");
            }
        }

        function SetAuthorID() {
            if ($("#ck_AuthorID").attr("checked") == "checked") {
                $("#ck_AuthorID").removeAttr("checked");
            } else {
                $("#ck_AuthorID").attr("checked", "checked");
            }
        }

        function SetPressID() {
            if ($("#ck_PressID").attr("checked") == "checked") {
                $("#ck_PressID").removeAttr("checked");
            } else {
                $("#ck_PressID").attr("checked", "checked");
            }
        }

        function SetPublishTimeID() {
            if ($("#ck_PublishTimeID").attr("checked") == "checked") {
                $("#ck_PublishTimeID").removeAttr("checked");
            } else {
                $("#ck_PublishTimeID").attr("checked", "checked");
            }
        }

        function SetPublishInfo() {
            if ($("#ck_PublishInfo").attr("checked") == "checked") {
                $("#ck_PublishInfo").removeAttr("checked");
            } else {
                $("#ck_PublishInfo").attr("checked", "checked");
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