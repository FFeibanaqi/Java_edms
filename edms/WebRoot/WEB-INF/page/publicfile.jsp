<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script>
	function toconfirm(){
		if(confirm("确定取消?"))
			return true;
		else
			return false;
	}
	function check(form){
  	  if(form.searchdata.value==''){
  		  alert("搜索内容不能为空");
  		  return false;
  	  }
  	  return true;
    }

	</script>
    <link rel="stylesheet" type="text/css" href="../layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../CSS/styles.css">
  </head>
  
  <body>
  <div>
  	<center>
  	<ul class="layui-nav" lay-filter="">
  	<li class="layui-nav-item layui-this"><a href="/edms/publicfile/${uid}">部门文件</a></li>
  	<li class="layui-nav-item"><a href="/edms/loaddocument/${uid}">原文件</a></li>
  	<li class="layui-nav-item"><a href="/edms/loaddocument2/${uid}">授权文件</a></li>
	</ul> 
  	<form name="documentlist" method="post" >
    <table>
    <br><br><br>
    <tr>
    <td><input type="hidden" name="uid" value="${uid}"/></td>
    </tr>
    <tr>
    <td><input type="text" name="searchdata"></td>
    <td><input type="submit" id="bt7" onclick="documentlist.action='/edms/searchpublic';return check(this.form);" value="搜索"/></td>
    <td><input type="submit" id="bt6" value="返回" onclick="documentlist.action='/edms/usermenu';"></td>
    </tr>
   
    </table>
    <table>
    <tr><td>授权文件</td></tr>
    <tr>
    <td>文件名称</td><td>文件类型</td><td>结束授权时间</td>
    <td>打开次数</td><td>预览</td><td>编辑</td><td>打印</td><td>下载</td>
    </tr>
    <c:forEach items="${documents}" var="item">
    <tr>
    <td><c:out value="${item.getName()}"/> </td>
    <td><c:out value="${item.getType()}"/></td>
    <td><c:out value="${item.getEdate()}"/></td>
    
    <td><c:out value="${item.getFrequency()}"/></td>
    <td>
    <c:if test="${item.getDread() eq 1}">
    			<input type="submit" id="bt8" value="查看" onclick="documentlist.action='/edms/openpublicdocument/${item.getNo()}/${uid}';
    			documentlist.target='_blank';"/></c:if>
    <c:if test="${item.getDread() eq 0}">否</c:if>
    </td>
    <td>
    <c:if test="${item.getDedit() eq 1}">
    			<input type="button" id="bt9" value="修改" onclick="documentlist.action='/edms/modifypublicdocument/${item.getNo()}/${uid}';
    			documentlist.target='_blank';documentlist.submit();"/></c:if>
    <c:if test="${item.getDedit() eq 0}">否</c:if>
    </td>
    <td>
    <c:if test="${item.getDprint() eq 1}">是</c:if>
    <c:if test="${item.getDprint() eq 0}">否</c:if>
    </td>
    <td>
    <c:if test="${item.getDdownload() eq 1}">
    			<input type="button" id="bt10" value="下载" onclick="documentlist.action='/edms/downloadpublicdocument/${item.getNo()}/${uid}';
    			documentlist.submit();"/></c:if>
    <c:if test="${item.getDdownload() eq 0}">否</c:if>
    </td>
    
    </tr>
    </c:forEach>
    
    </table>
    
    </form>
    </center>
    </div>
    
  <script src="../layui/layui.js">
//注意：导航 依赖 element 模块，否则无法进行功能性操作
  layui.use('element', function(){
    var element = layui.element;
    
    //…
  });  
  </script>
  </body>
</html>
