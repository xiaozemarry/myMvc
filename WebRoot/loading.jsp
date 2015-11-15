<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%> 
<html> 
<head>	
</head>
<body style="background:rgba(255,255,255,0)" allowtransparency="true" >
<table border="0" align="center" cellpadding="0" cellspacing="20" height="90%">
  <tr>
    <td align="center" height="45%" valign="bottom"></td>
  </tr>
  <tr>
    <td height="45%" valign="top"><img src="<%=basePath%>lib\loading\a1.gif"/></td>
  </tr>
</table>	
</body>
</html>
