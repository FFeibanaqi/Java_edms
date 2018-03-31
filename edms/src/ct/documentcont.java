package ct;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import process.documentprocess;
import process.userprocess;

import com.entities.Document;
import com.entities.User;
import com.zhuozhengsoft.pageoffice.*;


@Controller
public class documentcont {
	//load all original documents which belong to user by user's id
	@RequestMapping(value="/loaddocument/{uid}")
	public String loaddocu(@PathVariable("uid")int uid,ModelMap model){
		documentprocess dp=new documentprocess();
		List<Document> documents=dp.loaddocument(uid);
		model.addAttribute("uid",uid);
		model.addAttribute("documents",documents);
		return "originaldocument";
	}
	
	//turn to upload document page
	@RequestMapping(value="/toaddod/{variable}")
	public String toaddoriginaldocument(@PathVariable("variable")int uid,ModelMap model){
		model.addAttribute("uid",uid);
		return "uploaddocument";
	}
	
	//upload file and get message
	@RequestMapping("/upload/{variable}")  
	public String threeFileUpload(  
	        @RequestParam("file") CommonsMultipartFile files[], 
	        @PathVariable("variable")int uid,
	        HttpServletRequest request, ModelMap model) {  
	    documentprocess dp=new documentprocess();
	    List<String> list = new ArrayList<String>();  
	    ServletContext sc = request.getSession().getServletContext();  
	    String path = sc.getRealPath("/original") + "/"+uid+"/";   
	    File f = new File(path);  
	    if (!f.exists())  
	        f.mkdirs();  
	  
	    for (int i = 0; i < files.length; i++) {  
	    	if (!files[i].isEmpty()) {  
	            try {
	        String fileName = files[i].getOriginalFilename();  
	        //get the filename and suffix name
	        String fName = "";  
	        String suffix = ""; 
	        String spath="";
	        if(fileName.indexOf(".")>=0){  
	            int  indexdot =  fileName.indexOf(".");  
	            suffix =  fileName.substring(indexdot);  
	            fName = fileName.substring(0,fileName.lastIndexOf("."));
	            
	            //to judge the file name 
	            int num=1;
	            while(true){
	            	spath=path+fileName;
	            	File testFile=new File(spath);
	            if(testFile.exists()){
	            	fName=fName+"("+num+")";
	            	fileName=fName+suffix;
	            	num++;
	            }
	            else {
	            	break;
	            }
	            }
	            
	        }
	        String newFileName =fileName; 
	                FileOutputStream fos = new FileOutputStream(path  
	                        + newFileName);  
	                InputStream in =  files[i].getInputStream();  
	                int b = 0;  
	                while ((b = in.read()) != -1) {  
	                    fos.write(b);  
	                }  
	                fos.flush();
	                fos.close();  
	                in.close();  
	                //insert the message into database
	                File realfile=new File(spath);
	                Double fsize=(double) (realfile.length()/1024);
	                dp.useruploadfile(uid, fName, suffix, fsize);
	    	        list.add(newFileName); 
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	    model.addAttribute("files", list);
	    model.addAttribute("uid",uid);
	    return "uploadresult";  
	  
	}  
	
	//find file by uid and download document by document's no
	@RequestMapping(value="/downloaddocument/{variable}/{var2}")
	public String downloadod(@PathVariable("variable")int docno,ModelMap model,
			@PathVariable("var2")int uid,HttpServletRequest request,  
	        HttpServletResponse response){
	    // get the file name which you want to download  
	    documentprocess dp=new documentprocess();
		String fileName = dp.getfilename(docno);  
	    try {  
	        //fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");  
	        ServletContext sc = request.getSession().getServletContext();  
	        String fileSaveRootPath = sc.getRealPath("/original")+"/"+uid;   
	        System.out.println(fileSaveRootPath + "\\" + fileName);  
	        File file = new File(fileSaveRootPath + "\\" + fileName);  
	        if (!file.exists()) {  
	            request.setAttribute("message", "文件不存在");  
	        }  
	        String realname = fileName.substring(fileName.indexOf("_") + 1);  
	        response.setHeader("content-disposition", "attachment;filename="  
	                + URLEncoder.encode(realname, "UTF-8"));  
	        FileInputStream in = new FileInputStream(fileSaveRootPath + "\\" + fileName);  
	        OutputStream out = response.getOutputStream();  
	        byte buffer[] = new byte[1024];  
	        int len = 0;  
	        while ((len = in.read(buffer)) > 0) {  
	            out.write(buffer, 0, len);  
	        }  
	        in.close();  
	        out.close();
	        
	    } catch (Exception e) {  
	    	e.printStackTrace();
	    }
	    //can't not return to a jsp
	    return null;
	}
	
	
	//preview document
	@RequestMapping(value="/opendocument/{variable}/{v2}")
	public String opendocument(@PathVariable("variable")int docno,ModelMap model,
			@PathVariable("v2")int uid,HttpServletRequest request,HttpSession session){
		documentprocess dp=new documentprocess();
		String fileName = dp.getfilename(docno); 
		String suffix=dp.getsuffix(docno);
		String realfilepath=null;
	    try {  
	        //fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");  
	        ServletContext sc = request.getSession().getServletContext();  
	        String fileSaveRootPath = sc.getRealPath("/original")+"/"+uid;   
	        System.out.println(fileSaveRootPath + "\\" + fileName);  
	        File file = new File(fileSaveRootPath + "\\" + fileName);  
	        if (!file.exists()) {  
	            model.addAttribute("message", "文件不存在");  
	        }
	        realfilepath=fileSaveRootPath+"\\"+fileName;
	    
	    if(suffix.equals(".doc")||suffix.equals(".docx")){
	    	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	    	poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
	    	poCtrl1.setOfficeToolbars(false);//隐藏Office工具条
	    	poCtrl1.setCustomToolbar(false);//隐藏自定义工具栏
	    	poCtrl1.setAllowCopy(false);//禁止拷贝
	    	poCtrl1.setMenubar(false);//隐藏菜单栏
	    	poCtrl1.setCaption(fileName);//设置页面的显示标题
	    	//打开文件
	    	poCtrl1.webOpen(realfilepath, OpenModeType.docReadOnly, "用户");
	    	poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
	    	return "opendocument";
	    }
	    else if(suffix.equals(".xls")||suffix.equals(".xlsx")){
	    	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	    	poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); 
	    	poCtrl1.setOfficeToolbars(false);
	    	poCtrl1.setCustomToolbar(false);
	    	poCtrl1.setAllowCopy(false);
	    	poCtrl1.setMenubar(false);
	    	poCtrl1.setCaption(fileName);
	    	poCtrl1.webOpen(realfilepath, OpenModeType.xlsReadOnly, "用户");
	    	poCtrl1.setTagId("PageOfficeCtrl1");
	    	return "opendocument";
	    }
	    else if(suffix.equals(".ppt")){
	    	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	    	poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); 
	    	poCtrl1.setOfficeToolbars(false);
	    	poCtrl1.setCustomToolbar(false);
	    	poCtrl1.setAllowCopy(false);
	    	poCtrl1.setMenubar(false);
	    	poCtrl1.setCaption(fileName);
	    	poCtrl1.webOpen(realfilepath, OpenModeType.pptReadOnly, "用户");
	    	poCtrl1.setTagId("PageOfficeCtrl1"); 
	    	return "opendocument";
	    }
	    else if(suffix.equals(".pdf")){
	    	PDFCtrl poCtrl1 = new PDFCtrl(request);
	    	poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须

	    	// Create custom toolbar
	    	poCtrl1.addCustomToolButton("打印", "Print()", 6);
	    	poCtrl1.addCustomToolButton("隐藏/显示书签", "SetBookmarks()", 0);
	    	poCtrl1.addCustomToolButton("-", "", 0);
	    	poCtrl1.addCustomToolButton("实际大小", "SetPageReal()", 16);
	    	poCtrl1.addCustomToolButton("适合页面", "SetPageFit()", 17);
	    	poCtrl1.addCustomToolButton("适合宽度", "SetPageWidth()", 18);
	    	poCtrl1.addCustomToolButton("-", "", 0);
	    	poCtrl1.addCustomToolButton("首页", "FirstPage()", 8);
	    	poCtrl1.addCustomToolButton("上一页", "PreviousPage()", 9);
	    	poCtrl1.addCustomToolButton("下一页", "NextPage()", 10);
	    	poCtrl1.addCustomToolButton("尾页", "LastPage()", 11);
	    	poCtrl1.addCustomToolButton("-", "", 0);

	    	poCtrl1.webOpen(realfilepath);
	    	poCtrl1.setTagId("PDFCtrl1"); //此行必须
	    	return "openpdf";
	    }
	    
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    model.addAttribute("suffix",suffix);
	    return "openfail";
	}
	
	//edit document
	@RequestMapping(value="/modifydocument/{variable}/{v2}")
	public String modifydocument(@PathVariable("variable")int docno,ModelMap model,
			@PathVariable("v2")int uid,HttpServletRequest request,HttpServletResponse response){
		documentprocess dp=new documentprocess();
		String fileName = dp.getfilename(docno); 
		String suffix=dp.getsuffix(docno);
		String realfilepath=null;
	    try {  
	        //fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");  
	        ServletContext sc = request.getSession().getServletContext();  
	        String fileSaveRootPath = sc.getRealPath("/original")+"/"+uid;   
	        System.out.println(fileSaveRootPath + "\\" + fileName);  
	        File file = new File(fileSaveRootPath + "\\" + fileName);  
	        if (!file.exists()) {  
	            model.addAttribute("message", "文件不存在");  
	        }
	        realfilepath=fileSaveRootPath+"\\"+fileName;
	    
	    if(suffix.equals(".doc")||suffix.equals(".docx")){
	    	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	    	poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
	    	poCtrl1.setCaption(fileName);//设置页面的显示标题
	    	//打开文件
	    	poCtrl1.webOpen(realfilepath, OpenModeType.docReadOnly, "用户");
	    	poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
	    	model.addAttribute("uid",uid);
	    	return "editdocument";
	    }
	    else if(suffix.equals(".xls")||suffix.equals(".xlsx")){
	    	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	    	poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); 
	    	//poCtrl1.addCustomToolButton("保存","Save",1);
	    	//poCtrl1.setSaveFilePage("savefile.jsp");
	    	poCtrl1.setCaption(fileName);
	    	poCtrl1.webOpen(realfilepath, OpenModeType.xlsReadOnly, "用户");
	    	poCtrl1.setTagId("PageOfficeCtrl1"); 
	    	model.addAttribute("uid",uid);
	    	return "editdocument";
	    }
	    else if(suffix.equals(".ppt")){
	    	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	    	poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); 
	    	//poCtrl1.addCustomToolButton("保存","Save",1);
	    	//poCtrl1.setSaveFilePage("savefile.jsp");
	    	poCtrl1.setCaption(fileName);
	    	poCtrl1.webOpen(realfilepath, OpenModeType.pptReadOnly, "用户");
	    	poCtrl1.setTagId("PageOfficeCtrl1"); 
	    	model.addAttribute("uid",uid);
	    	return "editdocument";
	    }
	    
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    model.addAttribute("suffix",suffix);
	    return "openfail";
	}
	
	
	//delete document
	@RequestMapping(value="/deletedocument/{variable}/{v2}")
	public String deletedocument(@PathVariable("variable")int docno,ModelMap model,
			@PathVariable("v2")int uid,HttpServletRequest request,HttpServletResponse response){
		documentprocess dp=new documentprocess();
		String fileName = dp.getfilename(docno); 
	    try {  
	        //fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");  
	        ServletContext sc = request.getSession().getServletContext();  
	        String fileSaveRootPath = sc.getRealPath("/original")+"/"+uid;   
	        System.out.println(fileSaveRootPath + "\\" + fileName);  
	        File file = new File(fileSaveRootPath + "\\" + fileName);  
	        if (file.exists()&&file.isFile()) {  
	            file.delete();
	            dp.userdeletefile(uid, docno);
	            model.addAttribute("message", "删除成功");
	        }else{
	        	model.addAttribute("message", "文件不存在");
	        }		        
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    model.addAttribute("uid", uid);
	    return "deletedocumentresult";
	}	
		
	//search document by document's name
	@RequestMapping(value="/searchdocname")
	public String searchusername(@RequestParam("searchdata")String docname,
			@RequestParam("uid")int uid,ModelMap model){
		documentprocess dp=new documentprocess();
		List<Document> documents=dp.searchdocname(uid,docname);
		model.addAttribute("uid",uid);
		model.addAttribute("documents",documents);
		return "searchoriginalfile";
	}	
	

	
	
}
