<%@ page language="java" pageEncoding="UTF-8"%>
<%String referer = (String)request.getAttribute("prevLink");
  String notice = (String)request.getAttribute("notice");
  if(notice==null)notice = "";
%>
<%@include file="/lib/base.jsp"%>  
<html>
<head>
<title>用户登陆</title>
<!-- Custom Theme files -->
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" /> 
<meta name="keywords" content=""/>
<!--Google Fonts-->
<link rel='stylesheet' href="<%=basePath%>login/blueBack/css/rel.css" type='text/css'>
<script type="text/javascript" src="<%=basePath%>lib/jquery-2.0.0.min.js"></script>
<script type="text/javascript">
  var referer = "<%=referer%>";

  $(function(){
     $("#prevLink").hide();
  })
</script>
<!--Google Fonts-->
</head>
<body>
<!--header start here-->
	<div class="login">
		 <div class="login-main">
		 		<form type="post" action="<%=basePath%>login.do" method="post" autocomplete='off'>
				   <div class="login-top">
		 			<img src="<%=basePath%>login/blueBack/images/head-img.png" alt=""/>
		 			<h1>用户登录</h1>
		 			<input type="text" placeholder="用户名" required="" name="userName"/>
		 			<input type="password" placeholder="密码" required="" name="passWord"/>
                    <input type="hidden" id="prevLink" type="password"  name="referer" value="<%=referer%>"/>
		 			<div class="login-bottom">
		 			  <div class="login-check">
			 			<label class="checkbox"><input type="checkbox" name="checkbox"  checked="checked" /><i></i>记 住 我</label>
			 		  </div>
			 			<div class="clear"><%=notice%></div>
		 			</div>
		 			<input type="submit" value="登录" />
					<!--
					  <input type="text" type="hidden" name="referer" value="<%=referer%>">
					-->
		 		 </div>
				</form>
		 	</div>
  </div>
<!--header end here-->
</body>
</html>