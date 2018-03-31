<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	 
     <script type="text/javascript">
     function IsNum(num){
    	 var reNum=/^\d*$/;
    	 return(reNum.test(num));
    	}
  function toconfirm(form){
	  if(!IsNum(form.frequency.value)){
		  alert("请输入数字!")
		  form.frequency.focus();
		  return false;
		  }
	  if(form.frequency.value==''){
		  alert("请输入打开次数");
		  form.frequency.focus();
		  return false;
	  }
	  if(form.edate.value==''){
		  alert("时间输入为空");
		  form.edate.focus();
		  return false;
	  }
	  
	  
	  if(form.empowerrank.value=='60'){
		  alert("请选择授权等级");
		  form.empowerrank.focus();
		  return false;
	  }
	  if(confirm("确定?"))
		  return true;
	  else
		  return false;
  }
  
  </script> 
  <link rel="stylesheet" type="text/css" href="../../layui/css/layui.css" media="all">
  <link rel="stylesheet" type="text/css" href="../../CSS/styles.css">
  </head>
  
  <body>
  <div>
  <center>
  <ul class="layui-nav " lay-filter="">
  	<li class="layui-nav-item"><a href="/edms/publicfile/${uid}">部门文件</a></li>
  	<li class="layui-nav-item"><a href="/edms/loaddocument/${uid}">原文件</a></li>
  	<li class="layui-nav-item layui-this"><a href="/edms/loaddocument2/${uid}">授权文件</a></li>
	</ul> 
  <form:form method="post">
  	<table>
  	<br><br><br>
  	<tr>
  		<td><input type="hidden" name="uid" value="${uid}"/></td>
  		<td><form:input path="no" type="hidden"/></td>
  	</tr>
  	<tr>
  		<td><form:label path="name"> 文件名字</form:label></td>
  		<td><form:input path="name" disabled="ture" readonly="true"/></td>
  	</tr>
  	<tr>
  		<td><form:label path="type"> 文件类型</form:label></td>
  		<td><form:input path="type" disabled="ture" readonly="true"/></td>
  	</tr>
   	<tr>
  		<td><form:label path="size"> 文件大小</form:label></td>
  		<td><form:input path="size" disabled="ture" readonly="true"/></td>
  	</tr> 	
  	<tr>
  		<td><form:label path="frequency"> 打开次数</form:label></td>
  		<td><form:input path="frequency"/></td>
  	</tr>
  	<tr>
  		<td><form:label path="dread"> 预览</form:label></td>
  		<td><form:select path="dread">
  		    <form:options items="${readlist}"/>
  		</form:select></td>
  	</tr>
  	  	<tr>
  		<td><form:label path="dedit"> 编辑</form:label></td>
  		<td><form:select path="dedit">
  		    <form:options items="${editlist}"/>
  		</form:select></td>
  	</tr>
  	  	<tr>
  		<td><form:label path="dprint"> 打印</form:label></td>
  		<td><form:select path="dprint">
  		    <form:options items="${printlist}"/>
  		</form:select></td>
  	</tr>
  	  	<tr>
  		<td><form:label path="ddownload"> 下载</form:label></td>
  		<td><form:select path="ddownload">
  		    <form:options items="${downloadlist}"/>
  		</form:select></td>
  	</tr>
  	<tr>
  		<td><form:label path="sdate"> 文件上传时间</form:label></td>
  		<td><form:input path="sdate" disabled="ture" readonly="true"/></td>
  	</tr>  	
  	<tr>
  		<td><form:label path="edate"> 结束授权时间</form:label></td>
  		<td><form:input path="edate" class="layui-input" id="test1" disable="true"/></td>
  	</tr>
  	<tr>
  		<td> 授权等级</td>
  		<td><select name="empowerrank" >
  			<option value="60">请选择</option>
  		    <option value="61" <c:if test="${rank==61}">selected</c:if>>A</option>
  		    <option value="62" <c:if test="${rank==62}">selected</c:if>>B</option>
  		    <option value="63" <c:if test="${rank==63}">selected</c:if>>C</option>
  		    <option value="64" <c:if test="${rank==64}">selected</c:if>>内部</option>
  		    <option value="65" <c:if test="${rank==65}">selected</c:if>>普通</option>  			
  		</select></td>
  	<tr>
  	<tr>
  		<td><input type="hidden" name="originalrank" value="${rank}"/></td>
  		<td><input type="hidden" name="docno" value="${docno}"/></td>
  		<td><form:input path="dstate" type="hidden"/></td>
  	</tr>
  	
  	<tr>
  	<td colspan="2"><input type="submit" id="bt37" value="修改" onclick="command.action='/edms/updateempower';return toconfirm(this.form)"/></td>
  	<td colspan="2"><input type="reset"  id="bt38" value="重置"/></td>
  	<td colspan="2"><input type="submit" id="bt39" value="返回" onclick="command.action='/edms/loaddocument2/${uid}'"/> </td>
  	
  	</tr>
  	</table>
  </form:form>
  </center> 
  <script src="../../layui/layui.js"></script>
  <script type="text/javascript">
	//注意：导航 依赖 element 模块，否则无法进行功能性操作
  	layui.use('element', function(){
    	var element = layui.element;
    
    //…
  	}); 
  	layui.use('laydate', function(){
  	  var laydate = layui.laydate;
  	  
  	  //执行一个laydate实例
  	  laydate.render({
  	    elem: '#test1' //指定元素
  	    ,type: 'datetime'
  	  });
  	});
  </script> </div>
  </body>
</html>


