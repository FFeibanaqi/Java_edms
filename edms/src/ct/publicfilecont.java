package ct;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
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

import com.entities.Document;
import com.entities.User;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

import process.documentprocess;
import process.userprocess;

@Controller
public class publicfilecont {
	//load department's available document
	@RequestMapping(value="/publicfile/{uid}")
	public String loadpublicfile(@PathVariable("uid")int uid,ModelMap model,
			HttpServletRequest request){
		documentprocess dp=new documentprocess();
		userprocess up=new userprocess();
		
		List<Document> tempDocuments=dp.getdepartmentdocument(uid);
		List<Document> documents=new ArrayList<Document>();
		try{
			for(Document document:tempDocuments){
				String fileName = dp.getfilename(document.getNo());
				//get department's name by user's id
				User u=dp.getuserbydocno(document.getNo());
				String depname=up.getDepartmentName(u.getDid());
				int rank=dp.getempowerrank(document.getNo());
				String rankname=up.getRankName(rank-60);
				ServletContext sc = request.getSession().getServletContext();  
			    String fileRootPath = sc.getRealPath("/empower")+"/"+depname+"/"+rankname;   
			    File file = new File(fileRootPath + "\\" + fileName);  
			    if (file.exists()&&file.isFile()) {  
		            documents.add(document);
		        }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("documents",documents);
		model.addAttribute("uid",uid);
		return "publicfile";
	}
	
	//search department's document partially
	@RequestMapping(value="/searchpublic")
	public String searchpublicfile(@RequestParam("uid")int uid,ModelMap model,
			@RequestParam("searchdata")String dname,HttpServletRequest request){
		documentprocess dp=new documentprocess();
		userprocess up=new userprocess();
		
		List<Document> tempDocuments=dp.searchdepartmentdocument(uid, dname);
		List<Document> documents=new ArrayList<Document>();
		try{
			for(Document document:tempDocuments){
				String fileName = dp.getfilename(document.getNo());
				//get department's name by user's id
				User u=dp.getuserbydocno(document.getNo());
				String depname=up.getDepartmentName(u.getDid());
				int rank=dp.getempowerrank(document.getNo());
				String rankname=up.getRankName(rank-60);
				ServletContext sc = request.getSession().getServletContext();  
			    String fileRootPath = sc.getRealPath("/empower")+"/"+depname+"/"+rankname;   
			    File file = new File(fileRootPath + "\\" + fileName);  
			    if (file.exists()&&file.isFile()) {  
		            documents.add(document);
		        }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("documents",documents);
		model.addAttribute("uid",uid);
		return "searchpublicfile";
	}	
	
	//preview document
	@RequestMapping(value="/openpublicdocument/{v1}/{v2}")
	public String opendocument(@PathVariable("v1")int docno,ModelMap model,
			@PathVariable("v2")int uid,HttpServletRequest request,HttpSession session){
		documentprocess dp=new documentprocess();
		userprocess up=new userprocess();
		String fileName = dp.getfilename(docno);
		//get department's name by user's id
		User u=up.getUserbyid(uid);
		String depname=up.getDepartmentName(u.getDid());
		int rank=dp.getempowerrank(docno);
		String rankname=up.getRankName(rank-60);
		String suffix=dp.getsuffix(docno);
		String realfilepath=null;
		//compare time and judge frequency
		Document d=dp.getdocument(docno);
		Timestamp curtime = new Timestamp(System.currentTimeMillis());
		Timestamp deadline=d.getEdate();
		if(d.getFrequency()>0 && deadline.after(curtime)){
			try {  
		        //fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");  
		        ServletContext sc = request.getSession().getServletContext();  
		        String fileSaveRootPath = sc.getRealPath("/empower")+"/"+depname+"/"+rankname;  
		        File file = new File(fileSaveRootPath + "\\" + fileName);  
		        if (!file.exists()) {  
		            model.addAttribute("message", "文件不存在");  
		        }
		        realfilepath=fileSaveRootPath+"\\"+fileName;
		        if(suffix.equals(".doc")||suffix.equals(".docx")){
			    	dp.useropenpublicfile(docno);
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
			    	dp.useropenpublicfile(docno);
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
			    	dp.useropenpublicfile(docno);
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
			    	dp.useropenpublicfile(docno);
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
		}else{
			ServletContext sc = request.getSession().getServletContext();  
	        String fileSaveRootPath = sc.getRealPath("/empower")+"/"+depname+"/"+rankname;  
	        File file = new File(fileSaveRootPath + "\\" + fileName);  
	        if (file.exists()&&file.isFile()) {  
	        	file.delete();
	        	dp.timerunsout(uid, docno);
	            model.addAttribute("message", "打开次数已尽或授权时间已到");  
	        }
			
			model.addAttribute("suffix",suffix);
		    return "openfail";
		}
	}	
	
	//edit document
	@RequestMapping(value="/modifypublicdocument/{v1}/{v2}")
	public String modifydocument(@PathVariable("v1")int docno,ModelMap model,
			@PathVariable("v2")int uid,HttpServletRequest request,HttpServletResponse response){
		documentprocess dp=new documentprocess();
		userprocess up=new userprocess();
		String fileName = dp.getfilename(docno);
		//get department's name by user's id
		User u=up.getUserbyid(uid);
		String depname=up.getDepartmentName(u.getDid());
		int rank=dp.getempowerrank(docno);
		String rankname=up.getRankName(rank-60); 
		String suffix=dp.getsuffix(docno);
		String realfilepath=null;
		Document d=dp.getdocument(docno);
		Timestamp curtime = new Timestamp(System.currentTimeMillis());
		Timestamp deadline=d.getEdate();
		if(d.getFrequency()>0 && deadline.after(curtime)){
			try {  
		        //fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");  
		        ServletContext sc = request.getSession().getServletContext();  
		        String fileSaveRootPath = sc.getRealPath("/empower")+"/"+depname+"/"+rankname;   
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
			    	dp.usereditpublicfile(uid, docno);
			    	model.addAttribute("uid",uid);
			    	return "editdocument";
			    }
			    else if(suffix.equals(".xls")||suffix.equals(".xlsx")){
			    	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
			    	poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); 
			    	poCtrl1.setCaption(fileName);
			    	poCtrl1.webOpen(realfilepath, OpenModeType.xlsReadOnly, "用户");
			    	poCtrl1.setTagId("PageOfficeCtrl1"); 
			    	dp.usereditpublicfile(uid, docno);
			    	model.addAttribute("uid",uid);
			    	return "editdocument";
			    }
			    else if(suffix.equals(".ppt")){
			    	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
			    	poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); 
			    	poCtrl1.setCaption(fileName);
			    	poCtrl1.webOpen(realfilepath, OpenModeType.pptReadOnly, "用户");
			    	poCtrl1.setTagId("PageOfficeCtrl1");
			    	dp.usereditpublicfile(uid, docno);
			    	model.addAttribute("uid",uid);
			    	return "editdocument";
			    }
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		    model.addAttribute("suffix",suffix);
		    return "openfail";
		}else{
			ServletContext sc = request.getSession().getServletContext();  
	        String fileSaveRootPath = sc.getRealPath("/empower")+"/"+depname+"/"+rankname;  
	        File file = new File(fileSaveRootPath + "\\" + fileName);  
	        if (file.exists()&&file.isFile()) {  
	        	file.delete();
	        	dp.timerunsout(uid, docno);
	            model.addAttribute("message", "打开次数已尽或授权时间已到");  
	        }
			
			model.addAttribute("suffix",suffix);
		    return "openfail";
		}	
	}
	
	//download document by document's no
	@RequestMapping(value="/downloadpublicdocument/{var1}/{var2}")
	public String downloadod(@PathVariable("var1")int docno,ModelMap model,
			@PathVariable("var2")int uid,HttpServletRequest request,  
	        HttpServletResponse response){
		documentprocess dp=new documentprocess();
		userprocess up=new userprocess();
		String fileName = dp.getfilename(docno);
		//get department's name by user's id
		User u=up.getUserbyid(uid);
		String depname=up.getDepartmentName(u.getDid());
		int rank=dp.getempowerrank(docno);
		String rankname=up.getRankName(rank-60);  
	    try {  
	        //fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");  
	        ServletContext sc = request.getSession().getServletContext();  
	        String fileSaveRootPath = sc.getRealPath("/empower")+"/"+depname+"/"+rankname;   
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
	        dp.userdownloadpublicfile(uid, docno);
	    } catch (Exception e) {  
	    	e.printStackTrace();
	    }
	    //can't not return to a jsp
	    return null;
	}	
	
	
}
