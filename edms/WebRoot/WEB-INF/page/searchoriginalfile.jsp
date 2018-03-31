<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script type="text/javascript">

    	function toconfirm(){
    		if(confirm('确定删除？')){
    			return true;
    		}else
    			return false;
    	}
    </script>
    <link rel="stylesheet" type="text/css" href="layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="CSS/styles.css">
  </head>
  
  <body>
  <div>
  <center>
  	<ul class="layui-nav layui-bg-blue" lay-filter="">
  	<li class="layui-nav-item"><a href="/edms/publicfile/${uid}">部门文件</a></li>
  	<li class="layui-nav-item layui-this"><a href="/edms/loaddocument/${uid}">原文件</a></li>
  	<li class="layui-nav-item"><a href="/edms/loaddocument2/${uid}">授权文件</a></li>
	</ul> 
  	<form name="odlist" method="post">
    <table>
    <tr>
    <td><input type="hidden" name="uid" value="${uid}"/></td>
    </tr>
    <tr>
    <td><input type="submit" id="bt13" value="返回" onclick="odlist.action='/edms/loaddocument/${uid}';"></td>
    </tr>
    </table>
    <table>
    <tr>
    <td>文件名称</td><td>文件类型</td><td>文件大小(kb)</td><td>上传时间</td><td>是否授权</td>
    </tr>
    <c:forEach items="${documents}" var="item">
    <tr>
    <td><c:out value="${item.getName()}"/> </td>
    <td><c:out value="${item.getType()}"/></td>
    <td><c:out value="${item.getSize()}"/></td>
    <td><c:out value="${item.getSdate()}"/></td>
    <td>
    <c:if test="${item.getDstate() eq 1}">已授权</c:if>
    <c:if test="${item.getDstate() eq 0}">未授权</c:if>
    </td>
    <td><input type="button" id="bt15" value="查看" onclick="odlist.action='/edms/opendocument/${item.getNo()}/${uid}';
    			odlist.target='_blank';odlist.submit();"/></td>
    <td><input type="button" id="bt16" value="修改" onclick="odlist.action='/edms/modifydocument/${item.getNo()}/${uid}';
    			odlist.target='_blank';odlist.submit();"/></td>
    <td><input type="button" id="bt17" value="下载" onclick="odlist.action='/edms/downloaddocument/${item.getNo()}/${uid}';
    			odlist.submit();"/></td>
    <td><input type="submit" id="bt18" value="删除" onclick="odlist.action='/edms/deletedocument/${item.getNo()}/${uid}';
    			return toconfirm();"></td>
    </tr>
    </c:forEach>
    </table>
    </form>
    </center></div>
    <script src="../layui/layui.js">
	//注意：导航 依赖 element 模块，否则无法进行功能性操作
  	layui.use('element', function(){
    	var element = layui.element;
    
    //…
  	});  
  </script>
  </body>
</html>
