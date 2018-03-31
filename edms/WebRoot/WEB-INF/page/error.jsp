<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script>
	function indexpage(){
		window.history.back();
	}
	
	</script>

  </head>
  
  <body>
  		
  		错误信息:<br>
  		${message}
  		<input type="button" value="返回" onclick="page()"/>
  </body>
</html>
