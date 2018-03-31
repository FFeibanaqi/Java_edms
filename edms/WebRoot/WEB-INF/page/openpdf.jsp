<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@ page import="com.zhuozhengsoft.pageoffice.*, com.zhuozhengsoft.pageoffice.wordwriter.*,java.awt.*"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script>
	function SetBookmarks() {
        document.getElementById("PDFCtrl1").BookmarksVisible = !document.getElementById("PDFCtrl1").BookmarksVisible;
    }
    function Print() {
        document.getElementById("PDFCtrl1").ShowDialog(4);
    }
    function SwitchFullScreen() {
        document.getElementById("PDFCtrl1").FullScreen = !document.getElementById("PDFCtrl1").FullScreen;
    }
    function SetPageReal() {
        document.getElementById("PDFCtrl1").SetPageFit(1);
    }
    function SetPageFit() {
        document.getElementById("PDFCtrl1").SetPageFit(2);
    }
    function SetPageWidth() {
        document.getElementById("PDFCtrl1").SetPageFit(3);
    }
    function ZoomIn() {
        document.getElementById("PDFCtrl1").ZoomIn();
    }
    function ZoomOut() {
        document.getElementById("PDFCtrl1").ZoomOut();
    }
    function FirstPage() {
        document.getElementById("PDFCtrl1").GoToFirstPage();
    }
    function PreviousPage() {
        document.getElementById("PDFCtrl1").GoToPreviousPage();
    }
    function NextPage() {
        document.getElementById("PDFCtrl1").GoToNextPage();
    }
    function LastPage() {
        document.getElementById("PDFCtrl1").GoToLastPage();
    }
    function RotateRight() {
        document.getElementById("PDFCtrl1").RotateRight();
    }
    function RotateLeft() {
        document.getElementById("PDFCtrl1").RotateLeft();
    }
	</script>
  </head>
  
  <body>
    <div style="width:auto; height:600px;">
      <po:PDFCtrl id="PDFCtrl1" />
  </div>
  </body>
</html>
