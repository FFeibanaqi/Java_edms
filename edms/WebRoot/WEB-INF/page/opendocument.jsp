<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
  <script type="text/javascript">
  
  </script>
    <title>����ļ�</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script>
	function back(){
		window.history.back();
	}
	function AfterDocumentOpened() {
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(4, false); //��ֹ���
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(5, false); //��ֹ��ӡ
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(6, false); //��ֹҳ������
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(8, false); //��ֹ��ӡԤ��
    }
	
	</script>
  </head>
  
  <body onload="AfterDocumentOpened()">
    <div style=" width:auto; height:700px;">
    <po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
    <input type="button" value="����" onclick="back()"/>
    </div>
  </body>
</html>
