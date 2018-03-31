package ct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

import com.entities.User;

import process.userprocess;;

@Controller
public class usercont {
	//login
	@RequestMapping(value="/loginjudge")
	public String loginjudge(@RequestParam("id")int id,@RequestParam("password")String password,
			ModelMap model){
		userprocess up=new userprocess();
		int flag=up.loginjudge(id, password);
		if(flag==0){
			model.addAttribute("id",id);
			model.addAttribute("password",password);
			model.addAttribute("flag",0);
		}else if(flag==1){
			model.addAttribute("flag",1);
			model.addAttribute("aid",id);
		}else if(flag==2){
			User user=up.getUserbyid(id);
			model.addAttribute("flag",2);
			model.addAttribute("username",user.getName());
			model.addAttribute("uid",id);
		}
		return "menu";
			
	}
	
	//user turns to menu page(already login)
	@RequestMapping(value="/usermenu")
	public String turntomainpage(@RequestParam("uid")int uid,ModelMap model){
		userprocess up=new userprocess();
		User user=up.getUserbyid(uid);
		model.addAttribute("flag",2);
		model.addAttribute("username",user.getName());
		model.addAttribute("uid",uid);
		return "menu";
	}
	
	//admin turns to menu page(already login)
	@RequestMapping(value="/adminmenu")
	public String mainpage(@RequestParam("aid")int aid,ModelMap model){
		model.addAttribute("flag",1);
		model.addAttribute("aid",aid);
		return "menu";
	}
	
	//load users
	@RequestMapping(value="/Userpage")
	public String loaduser(@RequestParam("aid")int aid,ModelMap model){
		userprocess up=new userprocess();
		List<User> users=up.loaduser();
		model.addAttribute("users",users);
		model.addAttribute("aid",aid);
		return "user";
	}
	
	//load user's information
	@RequestMapping(value="/userinf/{variable}")
	public ModelAndView showuserinf(@PathVariable("variable") int userid,@RequestParam("aid")int aid,
			ModelMap model){
		userprocess up=new userprocess();
		User userx=up.getUserbyid(userid);
		model.addAttribute("aid",aid);
		return new ModelAndView("userinf","command",userx);
	}
	
	//delete user by user's id
	@RequestMapping(value="/deleteuser/{variable}")
	public String deleteuser(@PathVariable("variable") int userid,
			@RequestParam("aid")int aid,ModelMap model){
		userprocess up=new userprocess();
		int result=3;
		User user=up.getUserbyid(userid);
		String deptname=up.getDepartmentName(user.getDid());
		String rankname=up.getRankName(user.getRank());
		model.addAttribute("deptname",deptname);
		model.addAttribute("rankname",rankname);
		model.addAttribute("user",user);
		result=up.deleteuser(userid);
		String realresult="未知错误";
		if(result==0)
			realresult="删除成功";
		else if(result==1)
			realresult="删除失败";
		model.addAttribute("message", realresult);
		model.addAttribute("aid",aid);
		return "deleteresult";
	}
	
	//turn to add user page
	@RequestMapping(value="/toadduser")
	public ModelAndView turntoadduser(@RequestParam("aid")int aid,ModelMap model){
		User newuser =new User();
		model.addAttribute("aid",aid);
		return new ModelAndView("adduser","command",newuser);
	}
	
	//judge user's id and try to add user
	@RequestMapping(value="/judgeuid")
	public String judgeuid(@ModelAttribute("springMVC")User user,
			@RequestParam("aid")int aid,ModelMap model){
		userprocess up=new userprocess();
		String judgemsg="fail";
		if(!up.judgeUserbyid(user.getId()))
			judgemsg="user's id already exists";
		else{
			boolean aa=up.adduser(user);
			if(aa)
				judgemsg="success";
			else if(aa==false)
				judgemsg="fail to add user";
		}
		
		String deptname=up.getDepartmentName(user.getDid());
		String rankname=up.getRankName(user.getRank());
		model.addAttribute("deptname",deptname);
		model.addAttribute("rankname",rankname);
		
		model.addAttribute(user);
		model.addAttribute("message",judgemsg);
		model.addAttribute("aid",aid);
		return "addresult";
	}
	
	//update user
	@RequestMapping(value="/updateuser")
	public String updateuser(ModelMap model,@RequestParam("id")int id,
			@RequestParam("name")String name,@RequestParam("password")String pw,
			@RequestParam("rank")int rank,@RequestParam("did")int did,@RequestParam("aid")int aid){
		String judgemsg="成功修改";
		userprocess up=new userprocess();
		User userw=new User();
		userw.setId(id);
		userw.setName(name);
		userw.setPassword(pw);
		userw.setRank(rank);
		userw.setDid(did);
		if(up.updateuser(userw))
			judgemsg="success to modify";
		else 
			judgemsg="fail to modify";
		String deptname=up.getDepartmentName(userw.getDid());
		String rankname=up.getRankName(userw.getRank());
		model.addAttribute("deptname",deptname);
		model.addAttribute("rankname",rankname);
		model.addAttribute("user",userw);
		model.addAttribute("message",judgemsg);
		model.addAttribute("aid",aid);
		return "addresult";
	}
	
	//search user by id
	@RequestMapping(value="searchid")
	public String searchuserid(@RequestParam("searchdata")int partid,
			@RequestParam("aid")int aid,ModelMap model){
		userprocess up=new userprocess();
		List<User> users=up.searchid(partid);
		model.addAttribute("users",users);
		model.addAttribute("aid",aid);
		return "user";
	}
	
	//search user by name 
	@RequestMapping(value="searchname")
	public String searchusername(@RequestParam("searchdata")String partname,
			@RequestParam("aid")int aid,ModelMap model){
		userprocess up=new userprocess();
		List<User> users=up.searchname(partname);
		model.addAttribute("users",users);
		model.addAttribute("aid",aid);
		return "user";
	}
	
	
	@ModelAttribute("ranklist")
	public Map<Integer, String> getRanklist(){
		Map<Integer, String> ranklist=new HashMap<Integer, String>();
		ranklist.put(1, "A");
		ranklist.put(2, "B");
		ranklist.put(3, "C");
		ranklist.put(4, "内部");
		ranklist.put(5, "普通");
		return ranklist;
	}
	
	@ModelAttribute("departmentlist")
	public Map<Integer, String> getDeptlist(){
		Map<Integer, String> deptlist=new HashMap<Integer, String>();
		userprocess up=new userprocess();
		List<Integer> list=up.getalldid();
		for(int did:list){
			deptlist.put(did, up.getDepartmentName(did));
		}
		return deptlist;
	}
	
}
