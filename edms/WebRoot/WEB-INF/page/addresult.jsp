<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<link rel="stylesheet" type="text/css" href="CSS/styles.css">
  </head>
  
  <body>
  <div>
  <center>
  	<form action="/edms/Userpage" method="post" name="result">
  	<table>
  	<br><br><br>
  	<tr>
  		<td> 用户id</td>
  		<td>${user.getId()}</td>
  	</tr>
  	<tr>
  		<td> 姓名</td>
  		<td>${user.getName()}</td>
  	</tr>
  	<tr>
  		<td> 密码</td>
  		<td>${user.getPassword()}</td>
  	</tr>
  	<tr>
  		<td> 等级</td>
  		<td>${rankname}</td>
  	</tr>
  	<tr>
  		<td> 部门</td>
  		<td>${deptname}</td>
  	</tr><tr></tr>
  	<tr>
  		<td> 操作结果 </td>
  		<td>${message}</td>
  	</tr>
  	<tr>
  	<td colspan="2"><input type="submit" id="bt50" value="确定" />
  	</td>
  	<td><input type="hidden" name="aid" value="${aid}"/>
  	</table>
  	</form></center></div>
  </body>
</html>
