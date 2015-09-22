<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>应急处置提示引导图</title>
<link rel="stylesheet" type="text/css" href="../css/emergency.css" />
<script type="text/javascript" src="../js/emergency.js" /></script>
<script type="text/javascript" src="../js/jquery-ui-1.10.3.custom.min.draggable.js" /></script>
<script type="text/javascript">
<!--
	var flow_type='<%=Formats.nullto(request.getParameter("flowtype"))%>';
	var r_obj_id='<%=Formats.nullto(request.getParameter("obj_id"))%>';
	var r_x='<%=Formats.nullto(request.getParameter("x"))%>';
	var r_y='<%=Formats.nullto(request.getParameter("y"))%>';
	var r_z='<%=Formats.nullto(request.getParameter("z"))%>';
//-->
</script>
<style type="text/css">
</style>
</head>
<body onunload="onCloseWindow()">
<!-- <div id="page_lite">引导图</div> -->
<div class="content">
	<div class="title">应急处置提示引导图</div>
	<div class="flow">
		<!-- <div class="node">突水灾害</div> -->
		<!-- <div class="line"><div class="end"></div><div class="arrow"></div></div> -->
	</div>
	<div class="reset">重置</div>
	<div class="minimized" style="display: none;">&nbsp;</div>
	<div class="close" style="display: none;">X</div>
	<div class="background"></div>
</div>
<div class="menu">
	<ul>
		<!-- <li>应急电话查询</li> -->
	</ul>
</div>
<div class="page-lite">引导图</div>
</body>
</html>