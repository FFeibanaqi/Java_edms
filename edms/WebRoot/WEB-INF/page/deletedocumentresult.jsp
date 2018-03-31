<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
<link rel="stylesheet" type="text/css" href="../layui/css/layui.css">
  </head>
  
  <body><center>
  <ul class="layui-nav layui-bg-blue" lay-filter="">
  	<li class="layui-nav-item"><a href="/edms/publicfile/${uid}">部门文件</a></li>
  	<li class="layui-nav-item layui-this"><a href="/edms/loaddocument/${uid}">原文件</a></li>
  	<li class="layui-nav-item"><a href="/edms/loaddocument2/${uid}">授权文件</a></li>
	</ul>
  	<form method="post" action="/edms/loaddocument/${uid}">
    	结果：${message}<br>
    	<input type="hidden" name="uid" value="${uid}"/>
    	<input type="submit" value="确定" />
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
