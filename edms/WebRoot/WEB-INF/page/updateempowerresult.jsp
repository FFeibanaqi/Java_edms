<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	
	<link rel="stylesheet" type="text/css" href="layui/css/layui.css">
	<link rel="stylesheet" type="text/css" href="CSS/styles.css">
  </head>
  
  <body>
  <center>
    <ul class="layui-nav" lay-filter="">
  	<li class="layui-nav-item"><a href="/edms/publicfile/${uid}">部门文件</a></li>
  	<li class="layui-nav-item"><a href="/edms/loaddocument/${uid}">原文件</a></li>
  	<li class="layui-nav-item layui-this"><a href="/edms/loaddocument2/${uid}">授权文件</a></li>
	</ul>
  	<form method="post" action="/edms/loaddocument2/${uid}">
  	<table>
  	<br><br><br>
  	<tr>
  		<td> 文件名</td>
  		<td>${document.name}</td>
  	</tr>
  	<tr>
  		<td> 后缀名</td>
  		<td>${document.type}</td>
  	</tr>
  	<tr>
  		<td> 打开次数</td>
  		<td>${document.frequency}</td>
  	</tr>
  	<tr>
  		<td> 预览</td>
  		<td><c:if test="${document.dread eq 1}">是</c:if>
  			<c:if test="${document.dread eq 0}">否</c:if>
  		</td>
  	</tr>
  	<tr>
  		<td> 编辑</td>
  		<td><c:if test="${document.dedit eq 1}">是</c:if>
  			<c:if test="${document.dedit eq 0}">否</c:if>
  		</td>
  	</tr>
  	<tr>
  		<td> 打印</td>
  		<td><c:if test="${document.dprint eq 1}">是</c:if>
  			<c:if test="${document.dprint eq 0}">否</c:if>
  		</td>
  	</tr>
  	<tr>
  		<td> 下载</td>
  		<td><c:if test="${document.ddownload eq 1}">是</c:if>
  			<c:if test="${document.ddownload eq 0}">否</c:if>
  		</td>
  	</tr>
  	<tr>
  		<td> 结束授权时间</td>
  		<td>${document.edate}</td>
  	</tr>
  	<tr>
  		<td> 操作结果 </td>
  		<td>${message}</td>
  		<td>${fail}</td>
  	</tr>
  	<tr>
  	<td colspan="2"><input type="submit" id="bt52" value="确定"/></td>
  	<td><input type="hidden" name="uid" value="${uid}"/> </td>
  	</table>
  	</form>
  	</center>
  	<script src="layui/layui.js">
	//注意：导航 依赖 element 模块，否则无法进行功能性操作
  	layui.use('element', function(){
    	var element = layui.element;
    
    	//…
  	});  
  </script>
  </body>
</html>
