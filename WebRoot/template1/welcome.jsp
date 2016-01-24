<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/lib/base.jsp"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>G.net综合信息服务管理平台</title>
<link rel="stylesheet" href="css/global.css" type="text/css"/>
<link rel="stylesheet" href="css/index.css" type="text/css"/>
<link rel="stylesheet" href="css/menu.css" type="text/css"/>
<script src="js/fixPNG.js" type="text/javascript"></script>
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->
<!--[if lte IE 6]>
<style type="text/css">
table{border-collapse:collapse; margin:0; padding:0;}
.menu ul li a.hide, .menu ul li a:visited.hide{display:none;}
.menu ul li a:hover ul li a.hide{display:none;}
.menu ul li a:hover{display:block; width:151px; clear:both; }
.menu ul li a:hover ul{display:block; position:absolute; bottom:26px; left:0;}
.menu ul li a:hover ul li{display:block; width:151px;}
.menu ul li a:hover ul li a:hover{}
.menu ul li a:hover ul li a{display:block; width:100%; padding-left:10px;}
.menu ul li a:hover ul li a ul{visibility:hidden;}
.menu ul li a:hover ul li a:hover{}
.menu ul li a:hover ul li a:hover ul{visibility:visible; position:absolute; left:210px; bottom:0;}
.menu ul li a:hover ul li a:hover ul li a{background:url(../images/icon_sanjiao.png) no-repeat left center;color:#000;}
.menu ul li a:hover ul li a:hover ul li a:hover{background:#c9c9a7;}
.menu ul li a:hover ul.left{left:-105px;}
.menu ul li a:hover ul li a:hover ul.left{left:-210px;}
.category li{width:151px; display:block; float:left; margin-left:2px;}
.category li a:hover{background:url(../images/category_tab.jpg) no-repeat}
</style>
<![endif]-->
</head>

<body>
<!-- header -->
<div class="header">
<div class="logo fleft">
 <!--<img src="images/logo.png" width="344" height="49" />-->
  <div class="title">欢迎使用OA管理系统</div>
</div>
<div class="header_right">
<ul>
  <li><a href="#" title="首页" id="home"></a></li>
  <li><a href="#" title="更换皮肤" id="theme"></a></li>
  <li><a href="#" title="设置" id="Setup"></a></li>
  <li><a href="#" title="刷新" id="refresh"></a></li>
  <li><a href="<%=basePath%>loginOut" title="注销登录" id="logout"></a></li>
</ul>
</div>
</div>
<!-- nav -->
<div class="nav">
<div class="search fright">
<input type="text" value="搜索" onfocus="this.value=''" onblur="this.value='搜索'" class="box" />
<input type="button" value=" " name=" " class="search_btn" />
</div>
</div>
<!-- content -->
<div id="content">
<div class="content">
<!-- 第二屏开始 -->
<div class="second_screen">
<ul>
<li><a href="#" title="客户管理"><span><img src="images/icon_19.png" width="52" height="63" /></span><p>客户管理</p></a></li>
<li><a href="#" title="组织机构"><span><img src="images/icon_1.png" width="56" height="61" /></span><p>组织机构</p></a></li>
<li><a href="#" title="机构人员"><span><img src="images/icon_2.png" width="56" height="58" /></span><p>机构人员</p></a></li>
<li><a href="#" title="工作日志"><span><img src="images/icon_3.png" width="64" height="57" /></span><p>工作日志</p></a></li>
<li><a href="#" title="考勤管理"><span><img src="images/icon_4.png" width="64" height="62" /></span><p>考勤管理</p></a></li>
<li><a href="#" title="指标设置"><span><img src="images/icon_5.png" width="60" height="58" /></span><p>指标设置</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_6.png" width="61" height="63" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_7.png" width="64" height="48" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_8.png" width="45" height="60" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_9.png" width="59" height="60" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_10.png" width="63" height="63" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_11.png" width="52" height="60" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_12.png" width="60" height="60" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_13.png" width="64" height="63" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_14.png" width="60" height="63" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_15.png" width="59" height="61" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_16.png" width="54" height="52" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_17.png" width="51" height="57" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_18.png" width="61" height="61" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_19.png" width="52" height="63" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_20.png" width="61" height="60" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_21.png" width="64" height="64" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_22.png" width="60" height="62" /></span><p>考核审批</p></a></li>
<li><a href="#" title="考核审批"><span><img src="images/icon_23.png" width="50" height="60" /></span><p>考核审批</p></a></li>
<li><a href="#" title="客户管理"><span><img src="images/icon_24.png" width="50" height="63" /></span><p>客户管理</p></a></li>
</ul>
</div>
<!-- 第二屏结束 -->
<div class="clear"></div>
<div class="switch">
<ul>
<li class="switch_tab2"></li>
<li class="switch_tab1"></li>
<li class="switch_tab1"></li>
</ul>
</div>
  <!--
  <div class="main_desktop">
	<ul class="desktop_wrap">
	<li><p>通讯录</p>
	<a href="#"><img src="images/icon_2.png" width="56" height="58" /></a></li>
	<li><p>工作任务</p>
	<a href="#"><img src="images/icon_3.png" width="64" height="57" /></a></li>
	<li><p>项目管理</p>
	<a href="#"><img src="images/icon_4.png" width="64" height="62" /></a></li>
	<li><p>控制面板</p>
	<a href="#"><img src="images/icon_5.png" width="60" height="58" /></a></li>
	<li><p>即时提醒</p>
	<a href="#"><img src="images/icon_6.png" width="61" height="63" /></a></li>
	</ul>
	</div>

  -->
</div>
</div>
<!-- footer -->
<div id="footer">
<!-- 菜单 -->
<div class="menu">
<ul>
<li><a class="hide" href="#"><img src="images/menu.jpg" width="66" height="31" /></a>
<!--[if lte IE 6]>
<a href="#"><img src="images/menu.jpg" width="66" height="31" />
<table><tr><td>
<![endif]-->
    <ul  class="category" style="width:321px; background:url(images/menu_bg.jpg) repeat-y; padding-top:10px; border:2px solid #0059a5; border-bottom:none;">
    <div class="people blue"><span><img src="images/people.jpg" width="29" height="29" /></span><strong>李某</strong></div>
    <li><a href="#" class="icon_a">新闻中心</a>
      <!--[if lte IE 6]>
  <a class="sub" href="#" title="新闻中心">新闻中心
  <table><tr><td>
  <![endif]-->
        <ul style="position:relative;bottom:30px;">
            <li><a href="#" title="企业刊物">企业刊物</a></li>
            <li><a href="#" title="项目签约">项目签约</a></li>
            <li><a href="#" title="投票管理">投票管理</a></li>
        </ul>
    <!--[if lte IE 6]>
    </td></tr></table>
  </a>
  <![endif]-->
    </li>
    <li><a href="#" class="icon_b">企业概况</a>
          <!--[if lte IE 6]>
  <a class="sub" href="#" title="企业概况">企业概况
  <table><tr><td>
  <![endif]-->
        <ul style="position:relative; bottom:30px;">
            <li><a href="#" title="企业刊物">企业刊物</a></li>
            <li><a href="#" title="项目签约">项目签约</a></li>
            <li><a href="#" title="投票管理">投票管理</a></li>
            <li><a href="#" title="企业刊物">企业刊物</a></li>
            <li><a href="#" title="项目签约">项目签约</a></li>
            <li><a href="#" title="投票管理">投票管理</a></li>
            <li><a href="#" title="企业刊物">企业刊物</a></li>
            <li><a href="#" title="项目签约">项目签约</a></li>
            <li><a href="#" title="投票管理">投票管理</a></li>
        </ul>
    <!--[if lte IE 6]>
    </td></tr></table>
  </a>
  <![endif]-->
    </li>
    <li><a href="#" class="icon_c">企业文化</a></li>
    <li><a href="#" class="icon_d">行政办公</a></li>
    <li><a class="icon_e hide" href="#" title="运营管理">运营管理</a>
  <!--[if lte IE 6]>
  <a class="sub" href="#" title="运营管理">运营管理
  <table><tr><td>
  <![endif]-->
        <ul>
            <li><a href="#" title="通知公告">通知公告</a></li>
            <li><a href="#" title="企业动态">企业动态</a></li>
            <li><a href="#" title="媒体报道">媒体报道</a></li>
        </ul>
    <!--[if lte IE 6]>
    </td></tr></table>
  </a>
  <![endif]-->
    </li>
    <li><a href="#" class="icon_f">个人事务</a></li>
    <li><a href="#" class="icon_g">人力资源</a></li>
    <li><a href="#" class="icon_h">知识管理</a></li>
    <li><a href="#" class="icon_i">CRM系统</a></li>
    <li><a href="#" class="icon_j">项目管理</a></li>
    <li><a href="#" class="icon_k">报表统计</a></li>
    <li><a href="#" class="icon_l">交流园地</a>
                  <!--[if lte IE 6]>
  <a class="sub" href="#" title="系统维护">系统维护
  <table><tr><td>
  <![endif]-->
        <ul>
            <li><a href="#" title="企业刊物">企业刊物</a></li>
            <li><a href="#" title="项目签约">项目签约</a></li>
            <li><a href="#" title="投票管理">投票管理</a></li>
            <li><a href="#" title="企业刊物">企业刊物</a></li>
            <li><a href="#" title="项目签约">项目签约</a></li>
            <li><a href="#" title="投票管理">投票管理</a></li>
            <li><a href="#" title="企业刊物">企业刊物</a></li>
            <li><a href="#" title="项目签约">项目签约</a></li>
            <li><a href="#" title="投票管理">投票管理</a></li>
        </ul>
    <!--[if lte IE 6]>
    </td></tr></table>
  </a>
  <![endif]-->
    </li>
    <li><a href="#" class="icon_m">系统维护</a>
	  <!--[if lte IE 6]>
	  <a class="sub" href="#" title="系统维护">系统维护
	  <table><tr><td>
	  <![endif]-->
        <ul>
            <li><a href="#" title="创建用户">创建用户</a></li>
            <li><a href="#" title="密码修改">密码修改</a></li>
        </ul>
    <!--[if lte IE 6]>
    </td></tr></table>
  </a>
  <![endif]-->
    </li>
		<div class="out_quit">
			<a href="<%=basePath%>loginOut" title="注销" class="logout">注销</a>
			<a href="<%=basePath%>loginOut" title="退出" class="quit">退出</a>
		</div>
    </ul>
<!--[if lte IE 6]>
</td></tr></table>
</a>
<![endif]-->
</li>
</ul>
</div>
    <!-- -->
    <div class="footer_right"><span class="number">在线人数<strong>1</strong>人</span>
     <a href="#" class="tixing"><img src="images/tixing.png" width="16" height="16" /></a>
     <a href="#" class="xiaoxi"><img src="images/youjian.png" width="20" height="13" /></a>
     <a href="#" class="liaotian"><img src="images/liaotian.png" width="27" height="19" /></a>
    </div>
    <div class="clear"></div>
  </div>
</body>
</html>