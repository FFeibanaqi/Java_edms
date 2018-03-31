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
	</script>
	<link rel="stylesheet" type="text/css" href="layui/css/layui.css">
	<link rel="stylesheet" type="text/css" href="CSS/styles.css">
	
  </head>
  
  <body>
  <div>
  	<center>
  	<ul class="layui-nav layui-bg-blue" lay-filter="">
  	<li class="layui-nav-item"><a href="/edms/publicfile/${uid}">部门文件</a></li>
  	<li class="layui-nav-item"><a href="/edms/loaddocument/${uid}">原文件</a></li>
  	<li class="layui-nav-item layui-this"><a href="/edms/loaddocument2/${uid}">授权文件</a></li>
	</ul> 
  	<form name="edlist" method="post">
    <table>
    <tr>
    <td><input type="hidden" name="uid" value="${uid}"/></td>
    </tr>
    <tr>
    <td><input type="submit" id="bt20" value="返回" onclick="edlist.action='/edms/loaddocument2/${uid}';"></td>
    </tr>
    </table>
    <table>
    <tr><td>授权文件</td></tr>
    <tr>
    <td>文件名称</td><td>文件类型</td><td>结束授权时间</td>
    <td>打开次数</td><td>预览</td><td>编辑</td><td>打印</td><td>下载</td>
    </tr>
    <c:forEach items="${edocuments}" var="eitem">
    <tr>
    <td><c:out value="${eitem.getName()}"/> </td>
    <td><c:out value="${eitem.getType()}"/></td>
    <td><c:out value="${eitem.getEdate()}"/></td>
    
    <td><c:out value="${eitem.getFrequency()}"/></td>
    <td>
    <c:if test="${eitem.getDread() eq 1}">是</c:if>
    <c:if test="${eitem.getDread() eq 0}">否</c:if>
    </td>
    <td>
    <c:if test="${eitem.getDedit() eq 1}">是</c:if>
    <c:if test="${eitem.getDedit() eq 0}">否</c:if>
    </td>
    <td>
    <c:if test="${eitem.getDprint() eq 1}">是</c:if>
    <c:if test="${eitem.getDprint() eq 0}">否</c:if>
    </td>
    <td>
    <c:if test="${eitem.getDdownload() eq 1}">是</c:if>
    <c:if test="${eitem.getDdownload() eq 0}">否</c:if>
    </td>
    
    <td><input type="button" id="bt22" value="查看" onclick="edlist.action='/edms/openempowerdocument/${eitem.getNo()}/${uid}';
    			edlist.target='_blank';edlist.submit();"/></td>
    <td><input type="button" id="bt26" value="修改" onclick="edlist.action='/edms/modifyempowerdocument/${eitem.getNo()}/${uid}';
    			edlist.target='_blank';edlist.submit();"/></td>
    <td><input type="button" id="bt23" value="下载" onclick="edlist.action='/edms/downloadempowerdocument/${eitem.getNo()}/${uid}';
    			edlist.submit();"/></td>
    <td><input type="button" id="bt24" value="修改授权" onclick="edlist.action='/edms/setempower/${eitem.getNo()}/${uid}';
    			edlist.submit();"></td>
    <td><input type="submit" id="bt25" value="取消授权" onclick="edlist.action='/edms/cancelempower/${eitem.getNo()}/${uid}';
    			return toconfirm();"></td>
    </tr>
    </c:forEach>
    
    <tr><td><br>未授权文件</td></tr>
    <tr>
    <td>文件名称</td><td>文件类型</td><td>文件大小(kb)</td><td>上传时间</td><td>是否授权</td>
    </tr>
    <c:forEach items="${uedocuments}" var="ueitem">
    <tr>
    <td><c:out value="${ueitem.getName()}"/> </td>
    <td><c:out value="${ueitem.getType()}"/></td>
    <td><c:out value="${ueitem.getSize()}"/></td>
    <td><c:out value="${ueitem.getSdate()}"/></td>
    <td>
    <c:if test="${ueitem.getDstate() eq 1}">已授权</c:if>
    <c:if test="${ueitem.getDstate() eq 0}">未授权</c:if>
    </td>
    <td><input type="button" value="授权" onclick="edlist.action='/edms/setempower/${ueitem.getNo()}/${uid}';
    			edlist.submit();"></td>
    </tr>
    </c:forEach>
    </table>
    
    </form></center></div>
    <script src="../layui/layui.js">
	//注意：导航 依赖 element 模块，否则无法进行功能性操作
  	layui.use('element', function(){
    	var element = layui.element;
    
    	//…
  	});  
  </script>
  </body>
</html>
