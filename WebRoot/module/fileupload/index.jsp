<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/lib/base.jsp"%>  
<html>
    <head>
        <title>Upload a file please</title>
    </head>
    <body>
        <h1>Please upload a file</h1>
		<br/>
        <form method="post" action="<%=basePath%>uploadFiles.do" enctype="multipart/form-data">
            <input type="text" name="name" />
            <input type="file" name="file" multiple="multiple"/>
			<br/>
			<input type="text1" name="name1"/>
            <input type="file" name="file2"/>
            <input type="submit"/>
        </form>
    </body>
</html>