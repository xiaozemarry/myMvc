<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/lib/base.jsp"%>  
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>添加用户</title>

		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width,initial-scale=1.0" />

		<!-- basic styles -->

		<link href="<%=basePath%>lib/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=basePath%>lib/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/jquery-ui-1.10.3.full.min.css" />
		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/datepicker.css" />
		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/ui.jqgrid.css" />

		<!-- fonts -->

		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/ace-fonts.css" />

		<!-- ace styles -->

		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="<%=basePath%>lib/assets/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=basePath%>lib/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="<%=basePath%>lib/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=basePath%>lib/assets/js/html5shiv.js"></script>
		<script src="<%=basePath%>lib/assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
			<div class="main-container-inner">
				<div _class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="#">主页</a>
							</li>
							<li>
								<a href="#">表格</a>
							</li>
							<li class="active">用户信息表</li>
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
<!--
  						<div class="page-header">
							<h1>
								表格
								<small>
									<i class="icon-double-angle-right"></i>
									客户信息详细表格
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<table id="grid-table"></table>

								<div id="grid-pager"></div>

								<script type="text/javascript">
									var $path_base = "/";//this will be used in gritter alerts containing images
								</script>

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

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

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath%>lib/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="<%=basePath%>lib/assets/js/bootstrap.min.js"></script>
		<script src="<%=basePath%>lib/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="<%=basePath%>lib/assets/js/date-time/bootstrap-datepicker.min.js"></script>
		<script src="<%=basePath%>lib/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
		<script src="<%=basePath%>lib/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>

		<!-- ace scripts -->

		<script src="<%=basePath%>lib/assets/js/ace-elements.min.js"></script>
		<script src="<%=basePath%>lib/assets/js/ace.min.js"></script>
		<script type="text/javascript">
		  	 var basePath = "<%=basePath%>";
		</script>
		<!-- inline scripts related to this page -->
		<script src="<%=basePath%>module/menu/userinfo/js/adduser.js" type="text/javascript"></script>
	</body>
</html>
