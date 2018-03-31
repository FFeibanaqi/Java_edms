<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
      function check(form){
    	  if(form.id.value==''){
    		  alert("用户id不能为空");
    		  return false;
    	  }
    	  if(form.password.value==''){
    		  alert("密码不能为空");
    		  return false;
    	  }
    	  return true;
      }
  	function indexpage(){
  		if(confirm("确定退出登录？"))
			window.location.href="http://localhost:8080/edms/";
	}
   
    </script>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="CSS/styles.css">
    <link rel="stylesheet" href="layui/css/layui.css">
  </head>
  
  <body>
  <div id="mhead">
  <br><br><br>
  <p>电子文档安全管理系统</p>
  <center>
    <form name="menu" method="post" action="loginjudge">
    <table>
    
    <c:if test="${flag eq 2}">
    <tr>
    	<td><p>欢迎您，${username}用户</p></td>
    	<td><input type="button" id="bt2" value="文件管理" onclick="menu.action='publicfile/${uid}';menu.submit();"/></td>
    	<td><input type="button" id="bt3" value="退出登录" onclick="indexpage()"/></td>
    	<td><input type="hidden" value="${uid}" name="uid" /></td>
    </tr>
    <br>
    <tr>
    	
    </tr>
    </c:if>
    <c:if test="${flag eq 1}">
    <br><br><br>
    <tr>
    	<td><h4>欢迎您，管理员</h4></td>
    	<td><input type="button" id="bt4" value="用户管理" onclick="menu.action='Userpage';menu.submit();"/></td>
    	<td><input type="button" id="bt36" value="退出登录" onclick="indexpage()"/></td>
    	<td><input type="hidden" value="${aid}" name="aid" /></td>
    </tr>
    <tr>
   
    </tr>
    </c:if>
    
    <c:if test="${flag eq 0}">
    <tr>
    	<td>id:</td>
    	<td><input type="text" name="id" value="${id}"/></td>
    </tr>
    <tr>
    	<td>密码:</td>
    	<td><input type="password" name="password" value="${password}"/></td>
    </tr>
    <tr>
    <td><input type="submit" value="登陆" onclick="return check(this.form)"/></td>
    </tr>
    <tr><td>账号或密码错误，请重新输入</td></tr>
    </c:if>
    </table>
    </form>
    </center>
    </div>
  </body>
</html>
