package com.model2.mvc.web.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> 회원관리 RestController
@RestController
@RequestMapping("/user/*")
public class UserRestController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	public UserRestController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping( value="json/addUser", method=RequestMethod.POST )
	public User addUser( @RequestBody User user ) throws Exception {

		System.out.println("/user/addUser : POST");
		//Business Logic
		userService.addUser(user);
		
		return userService.getUser(user.getUserId());
	}
	
	@RequestMapping( value="json/updateUser", method=RequestMethod.POST )
	public User updateUser( @RequestBody User user ) throws Exception{

		System.out.println("/user/updateUser : POST");
		//Business Logic
		userService.updateUser(user);
		
		/*
		 * String sessionId=((User)session.getAttribute("user")).getUserId();
		 * if(sessionId.equals(user.getUserId())){ session.setAttribute("user", user); }
		 */
		
		//return "redirect:/getUser.do?userId="+user.getUserId();
		return userService.getUser(user.getUserId());
	}
	
	@RequestMapping( value="json/getUser/{userId}", method=RequestMethod.GET )
	public User getUser( @PathVariable String userId ) throws Exception{
		
		System.out.println("/user/json/getUser : GET");
		
		//Business Logic
		return userService.getUser(userId);
	}
	@RequestMapping( value="json/getUserList" )
	public Map getUserList( @RequestParam(value = "searchCondition",required = false) String searchCondition,
			@RequestParam(value = "searchKeyword" ,required = false) String searchKeyword,
			@RequestBody(required = false ) Search search) throws Exception{
		
		System.out.println("/user/json/getUserList : GET ,POST");
		
		System.out.println("searchKeyword : ? "+searchKeyword);
		System.out.println("searchCondition : ?"+searchCondition);
		if(search==null) {
			search = new Search();
			search.setCurrentPage(1);
			
			if(searchCondition.equals("null") || searchCondition==null) {
				search.setSearchCondition("");
			}else {
				search.setSearchCondition(searchCondition);
			}
			if (searchKeyword.equals("null") || searchKeyword==null) {
				search.setSearchKeyword("");
			}else {
				search.setSearchKeyword(searchKeyword);
			}
			search.setPageSize(10);
		}
		
		
		
		Map map = new HashMap<String, Object>();
		
		map =userService.getUserList(search); //map으로 담겨온다 List<User> user =(User)map.get("list") user[0],user[1],user[2]
		 List list = new ArrayList();
		 list = (List<User>) map.get("list");
		 System.out.println("리스트는 ? :"+list.toString());
		 Map returnMap = new HashMap<String, Object>();
		 
		 returnMap.put("list", map.get("list"));
		 System.out.println("리스트트트"+returnMap.get("list"));
		//Business Logic
		return returnMap;
	}
	
	@RequestMapping( value="json/checkDuplication", method=RequestMethod.POST )
	public Map checkDuplication( @RequestBody User user ) throws Exception{
		
		System.out.println("/user/checkDuplication : POST");
		//Business Logic
		boolean result=userService.checkDuplication(user.getUserId());
		
		System.out.println(JSONObject.toString("message", user.getUserId()+"는 사용하실 수 있는 아이디 입니다."));
		// Model 과 View 연결
		
		Map<String, String> map = new HashMap<String, String>();
		
		
		if(result) {
			map.put("message",user.getUserId()+"는 사용하실 수 있는 아이디 입니다.");
			
			return map;
		}else {
			map.put("message",user.getUserId()+"는 사용하실 수 없는 아이디 입니다.");
			
			return map;
		}
		
	}

	@RequestMapping( value="json/login", method=RequestMethod.POST )
	public User login(	@RequestBody User user,
									HttpSession session ) throws Exception{
	
		System.out.println("/user/json/login : POST");
		//Business Logic
		System.out.println("::"+user);
		User dbUser=userService.getUser(user.getUserId());
		
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		}
		
		return dbUser;
	}
}