<%@ page language="java" pageEncoding="UTF-8"%>
<%String referer = (String)request.getAttribute("prevLink");
  String notice = (String)request.getAttribute("notice");
  if(notice==null)notice = "";
%>
<%@include file="/lib/base.jsp"%>  
<html lang="en"><head>
		<meta charset="utf-8">
		<title>404页面</title>

		<meta name="description" content="404 Error Page">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<!-- basic styles -->

		<link rel="stylesheet" href="<%=basePath%>page404/assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=basePath%>page404/assets/css/font-awesome.min.css">

		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=basePath%>page404/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<link rel="stylesheet" href="<%=basePath%>page404/assets/css/ace-fonts.css">

		<!-- ace styles -->

		<link rel="stylesheet" href="<%=basePath%>page404/assets/css/ace.min.css">
		<link rel="stylesheet" href="<%=basePath%>page404/assets/css/ace-rtl.min.css">
		<link rel="stylesheet" href="<%=basePath%>page404/assets/css/ace-skins.min.css">

        <style>
		 body{
		      background-color: #FFFFFF;
		  }
		</style>

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=basePath%>page404/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="<%=basePath%>page404/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=basePath%>page404/assets/js/html5shiv.js"></script>
		<script src="<%=basePath%>page404/assets/js/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
	<div class="page-content">
		<div class="row">
			<div class="col-xs-12">
				<div class="error-container">
					<div class="well">
						<h1 class="grey lighter smaller">
							<span class="blue bigger-125">
								<i class="icon-sitemap"></i>
								404
							</span>
							Page Not Found！
						</h1>

						<hr>
						<h3 class="lighter smaller">我们找了整个网站但是找不到它!</h3>

						<div>
							<form class="form-search">
								<span class="input-icon align-middle">
									<i class="icon-search"></i>

									<input type="text" class="search-query" placeholder="请输入关键字...">
								</span>
								<button class="btn btn-sm" onclick="return false;">Go!</button>
							</form>

							<div class="space"></div>
							<h4 class="smaller">试着做做下面的操作:</h4>

							<ul class="list-unstyled spaced inline bigger-110 margin-15">
								<li>
									<i class="icon-hand-right blue"></i>
									从新检查当前url
								</li>

								<li>
									<i class="icon-hand-right blue"></i>
									仔细阅读常见问题
								</li>

								<li>
									<i class="icon-hand-right blue"></i>
									告诉站长
								</li>
							</ul>
						</div>

						<hr>
						<div class="space"></div>

						<div class="center">
							<a href="javascript:history.go(-1)" class="btn btn-grey">
								<i class="icon-arrow-left"></i>
								返回
							</a>

							<a href="javascript:location.href='<%=basePath%>/module/menu/page/menu.jsp'" class="btn btn-primary">
								<i class="icon-dashboard"></i>
								首页
							</a>
							<!--Dashboard-->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>