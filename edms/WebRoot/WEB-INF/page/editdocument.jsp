<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
<script>
function Save() {
    document.getElementById("PageOfficeCtrl1").WebSave();
}
document.createElement("section");
document.createElement("article");
document.createElement("footer");
document.createElement("header");
document.createElement("hgroup");
document.createElement("nav");
document.createElement("menu");
function AfterDocumentOpened() {
    document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(4, true); //¡Ì¥Ê
    document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(5, true); //¥Ú”°
    document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(6, true); //“≥√Ê…Ë÷√
    document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(8, true); //¥Ú”°‘§¿¿
}
</script>
  </head>
  
  <body onload="AfterDocumentOpened()">
    <div style=" width:auto; height:700px;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1">
        </po:PageOfficeCtrl>
    </div>
  </body>
</html>
