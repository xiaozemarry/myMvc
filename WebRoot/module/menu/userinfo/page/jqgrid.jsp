<%@ page language="java" contentType="text/html;charset=UTF-8"    pageEncoding="UTF-8"%>
<%@include file="/lib/base.jsp"%>  
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>客户信息</title>
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width,initial-scale=1.0" />
		<!-- basic styles -->
		<link href="<%=basePath%>lib/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/font-awesome.min.css" />
		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=basePath%>lib/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->
		<!-- page specific plugin styles --> 
		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/ui.jqgrid.css" />
		<!-- fonts -->
		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/ace-fonts.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/ace.min.css" /> 
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=basePath%>lib/assets/css/ace-ie.min.css" />
		<![endif]-->
 
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		<script src="<%=basePath%>lib/assets/js/html5shiv.js"></script>
		<script src="<%=basePath%>lib/assets/js/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
			<div class="main-container-inner">
				<div>
					<div class="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="#">主页</a>
							</li>
							<li>
								<a href="#">表格</a>
							</li>
							<li class="active">客户信息表格</li>
						</ul>
					</div>
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<table id="grid-table">222222222</table>
								<div id="grid-pager">11111</div>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
		</div><!-- /.main-container -->
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath%>lib/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='<%=basePath%>lib/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
		<![endif]--> 
		<!-- page specific plugin scripts -->
		<script src="<%=basePath%>lib/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
		<script src="<%=basePath%>lib/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>
		<script type="text/javascript">
		  	 var basePath = "<%=basePath%>";
		</script>
		<!-- inline scripts related to this page -->
		<script src="<%=basePath%>module/menu/userinfo/js/user.js" type="text/javascript"></script>
	</body>
</html>
