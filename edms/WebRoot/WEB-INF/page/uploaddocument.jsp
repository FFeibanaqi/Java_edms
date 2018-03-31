<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<title>用户上传页面</title>  
 <script type="text/javascript">
 function check(form){
	 if(form.file.value==''){
		  alert("第一个文件不能为空");
		  return false;
	  }
	  return true;
	 
 }
 </script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  

<link rel="stylesheet" type="text/css" href="../layui/css/layui.css">
<link rel="stylesheet" type="text/css" href="../CSS/styles.css">
</head> 
<div>
<body> 
 <center>
    <ul class="layui-nav " lay-filter="">
  	<li class="layui-nav-item"><a href="/edms/publicfile/${uid}">部门文件</a></li>
  	<li class="layui-nav-item layui-this"><a href="/edms/loaddocument/${uid}">原文件</a></li>
  	<li class="layui-nav-item"><a href="/edms/loaddocument2/${uid}">授权文件</a></li>
    </ul> 
    <br><br><br> 
        <form action="/edms/upload/${uid}" method="post"  
            enctype="multipart/form-data" name="uploadlist">  
            <input type="file"  name="file" /><br /> 
            <br><br><br> 
            <input type="file" name="file" /><br /> 
            <br><br><br> 
            <input type="file" name="file" /><br /> 
            <br><br><br>
            <input type="submit" id="bt43" value="上 传" />
            <input type="button" id="bt44" value="返回" onclick="uploadlist.action='/edms/loaddocument/${uid}';uploadlist.submit()"/>
        </form>  
    </center>  
    <script src="../layui/layui.js">
	//注意：导航 依赖 element 模块，否则无法进行功能性操作
  	layui.use('element', function(){
    	var element = layui.element;
    
    //…
  	});  
  </script></div> 
</body>  
</html>