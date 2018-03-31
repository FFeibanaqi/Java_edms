<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script type="text/javascript">
      function check(form){
    	  if(form.searchdata.value==''){
    		  alert("搜索内容不能为空");
    		  return false;
    	  }
    	  return true;
      }
    	function toconfirm(){
    		if(confirm('确定删除？')){
    			return true;
    		}else
    			return false;
    	}
    </script>
    <link rel="stylesheet" type="text/css" href="CSS/styles.css">
  </head>
  
  <body>
  <div>
  <center>
  	<form name="userlist" method="post">
    <table>
    <br><br><br>
    <tr>
    <td><input type="button" id="bt28" onclick="userlist.action='toadduser';userlist.submit();" value="添加用户"/></td>
    <td><input type="button" id="bt29" onclick="userlist.action='Userpage';userlist.submit();"value="用户列表"/></td>
    <td><input type="submit" id="bt30" value="返回主页面" onclick="userlist.action='adminmenu';"/>
    <td><input type="hidden" value="${aid}" name="aid"></td>
    </tr>
    <tr>
    <td><input type="text" name="searchdata"></td>
    <td><input type="submit" id="bt31" onclick="userlist.action='searchid';return check(this.form);" value="根据id搜索"/></td>
    <td><input type="submit" id="bt32" onclick="userlist.action='searchname';return check(this.form);" value="根据姓名搜索"/></td>
    </tr>
    <tr>
    <td>用户id</td><td>姓名</td>
    </tr>
    <c:forEach items="${users}" var="item">
    <tr>
    <td><c:out value="${item.getId()}"/> </td>
    <td><c:out value="${item.getName()}"/></td>
    <td><input type="button" id="bt33" value="查看" onclick="userlist.action='userinf/${item.getId()}';userlist.submit();"/></td>
    <td><input type="button" id="bt34" value="修改" onclick="userlist.action='userinf/${item.getId()}';userlist.submit();"/></td>
    <td><input type="submit" id="bt35" value="删除" onclick="userlist.action='deleteuser/${item.getId()}';return toconfirm();"></td>
    </tr>
    </c:forEach>
    </table>
    </form>
    </center></div>
  </body>
</html>
