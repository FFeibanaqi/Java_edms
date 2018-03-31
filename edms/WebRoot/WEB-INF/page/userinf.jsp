<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script>
  
  function toconfirm(form){
	  
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
	  if(confirm("确定修改用户信息?"))
		  return true;
	  else
		  return false;
  }
  </script>
   <link rel="stylesheet" type="text/css" href="../CSS/styles.css">
  </head>
  
  <body>
  <div>
  <center>
  <form:form method="get" >
  	<table>
  	<br><br><br>
  	<tr>
  		<td><form:label path="id"> 用户id</form:label></td>
  		<td><form:input path="id" disabled="ture" readonly="true"/></td>
  	</tr>
  	<tr>
  		<td><form:label path="name"> 姓名</form:label></td>
  		<td><form:input path="name"/></td>
  	</tr>
  	<tr>
  		<td><form:label path="password"> 密码</form:label></td>
  		<td><form:input path="password"/></td>
  	</tr>
  	<tr>
  		<td><form:label path="rank"> 等级</form:label></td>
  		<td><form:select path="rank">
  		    <form:options items="${ranklist}"/>
  		</form:select></td>
  	</tr>
  	<tr>
  		<td><form:label path="did"> 部门</form:label></td>
  		<td><form:select path="did">
  		    <form:options items="${departmentlist}"/>
  		</form:select></td>
  	</tr>
  	<tr>
  	<td colspan="2"><input type="submit" id="bt47" value="修改" onclick="command.action='/edms/updateuser';return toconfirm(this);"/></td>
  	<td colspan="2"><input type="reset"  id="bt48" value="重置"/></td>
  	<td colspan="2"><input type="submit"  id="bt49" value="返回" onclick="command.action='/edms/Userpage';"/></td>
  	<td><input type="hidden" name="aid" value="${aid}"/></td>
  	</tr>
  	</table>
  </form:form></center></div>
  </body>
</html>
