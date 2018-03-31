package ct;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import process.documentprocess;
import process.userprocess;

import com.entities.Document;
import com.entities.User;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;


@Controller
public class documentcont2 {
	//load all original or empowered documents which belong to user by user's id
	@RequestMapping(value="/loaddocument2/{uid}")
	public String loaddocu(@PathVariable("uid")int uid,ModelMap model){
		documentprocess dp=new documentprocess();
		List<Document> edocuments=dp.loadempower(uid);
		List<Document> uedocuments=dp.loadunempower(uid);
		model.addAttribute("uid",uid);
		model.addAttribute("edocuments",edocuments);
		model.addAttribute("uedocuments",uedocuments);
		return "empowerdocument";
	}
	
	//search empower or unempower document by document's name partial
	@RequestMapping(value="/searchempower")
	public String searchempower(@RequestParam("uid")int uid,
			@RequestParam("searchdata")String dname,ModelMap model){
		documentprocess dp=new documentprocess();
		List<Document> edocuments=dp.searchempower(uid,dname);
		List<Document> uedocuments=dp.searchunempower(uid,dname);
		model.addAttribute("uid",uid);
		model.addAttribute("edocuments",edocuments);
		model.addAttribute("uedocuments",uedocuments);
		return "searchempowerfile";
	}	
	
	
	//turn to setempower page
	@RequestMapping("/setempower/{docno}/{uid}")
	public ModelAndView empowerdoc(@PathVariable("docno")int docno,@PathVariable("uid")int uid,
			ModelMap model){
		documentprocess dp=new documentprocess();
		Document document=dp.getdocument(docno);
		int rank=dp.getempowerrank(docno);
		model.addAttribute("rank",rank);
		model.addAttribute("uid",uid);
		model.addAttribute("docno",docno);
		return new ModelAndView("setempower","command",document);
	}
	
	//try to update empower
	@RequestMapping("/updateempower")
	public String updateempower(@ModelAttribute("springmvc")Document document,HttpServletRequest request,
			@RequestParam("empowerrank")int newutype,@RequestParam("docno")int docno,
			@RequestParam("uid")int uid,ModelMap model,@RequestParam("originalrank")int oldutype){
		documentprocess dp=new documentprocess();
		userprocess up=new userprocess();
		String fileName = dp.getfilename(docno);
		//get department's name by user's id
		User u=up.getUserbyid(uid);
		String depname=up.getDepartmentName(u.getDid());
		String newrankname=up.getRankName(newutype-60);
		String oldrankname=up.getRankName(oldutype-60);
		try{
	        //judge if document is empowered
			if(document.getDstate()==0){
			    ServletContext fromsc = request.getSession().getServletContext();  
			    String fromfileRootPath = fromsc.getRealPath("/original")+"/"+uid;   
			    File fromfile = new File(fromfileRootPath + "\\" + fileName);  
			    if (fromfile.exists()) {
					//new empower file
					//fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");  
					ServletContext tosc = request.getSession().getServletContext();  
					String tofileRootPath = tosc.getRealPath("/empower")+"/"+depname+"/"+newrankname;   
					File rootpath = new File(tofileRootPath);  
					if (!rootpath.exists()) {  
						rootpath.mkdirs();  
						}
					File tofile=new File(tofileRootPath + "\\" + fileName);
					FileUtils.copyFile(fromfile, tofile); 
					if(tofile.exists()){
						model.addAttribute("message","成功授权");
						//update document's information in database
						document.setDstate(1);
						dp.updatedocument(uid,document,newutype);
					}else if(!tofile.exists()){
						model.addAttribute("message","授权失败");
					}
			    }else{
			    	model.addAttribute("fail","原文件不存在");
			    }
			}else if(document.getDstate()==1){
				if(newutype!=oldutype){
					ServletContext fromsc = request.getSession().getServletContext();  
				    String fromfileRootPath = fromsc.getRealPath("/empower")+"/"+depname+"/"+oldrankname;   
				    File fromfile = new File(fromfileRootPath + "\\" + fileName);  
				    if (fromfile.exists()) {
				    	ServletContext tosc = request.getSession().getServletContext();  
						String tofileRootPath = tosc.getRealPath("/empower")+"/"+depname+"/"+newrankname;   
						File rootpath = new File(tofileRootPath);  
						if (!rootpath.exists()) {  
							rootpath.mkdirs();  
							}
						File tofile=new File(tofileRootPath + "\\" + fileName);
						FileUtils.copyFile(fromfile, tofile);
						if(tofile.exists()){
							model.addAttribute("message","成功授权");
							dp.updatedocument(uid,document,newutype);
						}else if(!tofile.exists()){
							model.addAttribute("message","授权失败");
						}
				    }else{
				    	model.addAttribute("fail","原文件不存在");
				    }
				}
			}
	        
		}catch(Exception e){
			model.addAttribute("fail","授权失败");
			e.printStackTrace();
		}
		model.addAttribute("document",document);
		model.addAttribute("rankname",newrankname);
		model.addAttribute("uid",uid);
		return "updateempowerresult";
	}
	
	
	//cancel empower
	@RequestMapping(value="/cancelempower/{docno}/{uid}")
	public String cancelempower(@PathVariable("docno")int docno,HttpServletRequest request,
			@PathVariable("uid")int uid,ModelMap model){
		documentprocess dp=new documentprocess();
		Document document=dp.getdocument(docno);
		userprocess up=new userprocess();
		String fileName = dp.getfilename(docno);
		//get department's name by user's id
		User u=up.getUserbyid(uid);
		String depname=up.getDepartmentName(u.getDid());
		int rank=dp.getempowerrank(docno);
		String rankname=up.getRankName(rank-60);
		try{
			ServletContext sc = request.getSession().getServletContext();  
		    String fileRootPath = sc.getRealPath("/empower")+"/"+depname+"/"+rankname;   
		    File file = new File(fileRootPath + "\\" + fileName);  
		    if (file.exists()&&file.isFile()) {  
	            file.delete();
	            document.setDstate(1);
	            dp.cancelempower(uid, document);
	            model.addAttribute("message", "取消授权成功");
	        }else{
	        	model.addAttribute("message", "文件不存在");
	        }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		model.addAttribute("uid",uid);
		model.addAttribute("document",document);
		return "updateempowerresult";
	}
	
	//preview document
	@RequestMapping(value="/openempowerdocument/{v1}/{v2}")
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
	@RequestMapping(value="/modifyempowerdocument/{v1}/{v2}")
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
	
	//download document by document's no
	@RequestMapping(value="/downloadempowerdocument/{var1}/{var2}")
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
	        
	    } catch (Exception e) {  
	    	e.printStackTrace();
	    }
	    //can't not return to a jsp
	    return null;
	}	
	
	
	
	
	
	@ModelAttribute("readlist")
	public Map<Integer, String> getReadlist(){
		Map<Integer, String> readlist=new HashMap<Integer, String>();
		readlist.put(1, "是");
		readlist.put(0, "否");
		return readlist;
	}
	
	@ModelAttribute("editlist")
	public Map<Integer, String> getEditlist(){
		Map<Integer, String> editlist=new HashMap<Integer, String>();
		editlist.put(1, "是");
		editlist.put(0, "否");
		return editlist;
	}
	
	@ModelAttribute("printlist")
	public Map<Integer, String> getPrintlist(){
		Map<Integer, String> printlist=new HashMap<Integer, String>();
		printlist.put(1, "是");
		printlist.put(0, "否");
		return printlist;
	}
	
	@ModelAttribute("downloadlist")
	public Map<Integer, String> getDownloadlist(){
		Map<Integer, String> downloadlist=new HashMap<Integer, String>();
		downloadlist.put(1, "是");
		downloadlist.put(0, "否");
		return downloadlist;
	}
	
	@ModelAttribute("list")
	public Map<Integer, String> getRanklist(){
		Map<Integer, String> ranklist=new HashMap<Integer, String>();
		ranklist.put(61, "A");
		ranklist.put(62, "B");
		ranklist.put(63, "C");
		ranklist.put(64, "内部");
		ranklist.put(65, "普通");
		return ranklist;
	}
	
}
