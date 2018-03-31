<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script>
	
	</script>
<link rel="stylesheet" type="text/css" href="../layui/css/layui.css">
  </head>
  
  <body>
  <center>
  <ul class="layui-nav layui-bg-blue" lay-filter="">
  	<li class="layui-nav-item"><a href="/edms/publicfile/${uid}">部门文件</a></li>
  	<li class="layui-nav-item layui-this"><a href="/edms/loaddocument/${uid}">原文件</a></li>
  	<li class="layui-nav-item"><a href="/edms/loaddocument2/${uid}">授权文件</a></li>
	</ul>
  		<h2>上传结果</h2>
  		<form name="uploadresult" method="post" action="/edms/loaddocument/${uid}">
  		<c:forEach items="${files}" var="file">
  			-${file} <br>
  		</c:forEach>
  		<input type="hidden" name="uid" value="${uid}"/>
  		<input type="button" value="继续" onclick="uploadresult.action='/edms/toaddod/${uid}';uploadresult.submit();"/>
  		<input type="submit" value="返回" />
  		</form>
  </center>
    <script src="../layui/layui.js">
	//注意：导航 依赖 element 模块，否则无法进行功能性操作
  	layui.use('element', function(){
    	var element = layui.element;
    
    //…
  	});  
  </script>  
  </body>
</html>
