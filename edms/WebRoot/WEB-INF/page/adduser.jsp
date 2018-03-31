<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script>
  function pageback(){
	  window.history.back();
  }
  function toconfirm(form){
	  if(form.id.value==''){
		  alert("请输入名字");
		  form.id.focus();
		  return false;
	  }
	  if(form.name.value==''){
		  alert("请输入名字");
		  form.name.focus();
		  return false;
	  }
	  if(form.password.value==''){
		  alert("请输入密码");
		  form.password.focus();
		  return false;
	  }
	  if(form.rank.value==''){
		  alert("请选择用户等级");
		  form.rank.focus();
		  return false;
	  }
	  if(form.did.value==''){
		  alert("请选择部门");
		  form.did.focus();
		  return false;
	  }
	  if(confirm("确定添加?"))
		  return true;
	  else
		  return false;
  }  
  
  </script>
  <link rel="stylesheet" type="text/css" href="CSS/styles.css">
  </head>
  
  <body>
  <div>
  <center>
  <form:form method="post" action="judgeuid" onsubmit="return toconfirm(this)">
  	<table>
  	<br><br><br>
  	<tr>
  		<td> 用户id</td>
  		<td><form:input path="id"/></td>
  	</tr>
  	<tr>
  		<td> 姓名</td>
  		<td><form:input path="name"/></td>
  	</tr>
  	<tr>
  		<td> 密码</td>
  		<td><form:input path="password"/></td>
  	</tr>
  	<tr>
  		<td> 等级</td>
  		<td><form:select path="rank">
  		    <form:options items="${ranklist}"/>
  		</form:select></td>
  	</tr>
  	<tr>
  		<td> 部门</td>
  		<td><form:select path="did">
  		    <form:options items="${departmentlist}"/>
  		</form:select></td>
  	</tr>
  	<tr>
  	<td colspan="2"><input type="submit" id="bt45" value="提交" /></td>
  	<td colspan="2"><button id="bt46"  onclick="pageback()">返回</button></td>
  	<td><input type="hidden" value="${aid}" name="aid"></td>
  	</table>
  </form:form></center></div>
  </body>
</html>
