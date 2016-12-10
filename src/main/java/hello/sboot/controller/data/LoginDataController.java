package hello.sboot.controller.data;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.client.RequestExpectationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginDataController {
	
	@Autowired
	private IdentityService identityService;
	
	@RequestMapping(path="/initlogin")
	@ResponseBody
	public List<User> initlogin(){
		User user=identityService.newUser("hou");
		user.setPassword("123");
		identityService.saveUser(user);
		
		Group group=identityService.newGroup("dep1");
		group.setName("dep1");
		group.setType("dep");
		identityService.saveGroup(group);
		return identityService.createUserQuery().list(); 
	}

	@RequestMapping(path="/login",method=RequestMethod.POST)
	@ResponseBody
	public boolean login(String userId,String password,HttpSession session){
		boolean result=identityService.checkPassword(userId, password);
		if(result){
			session.setAttribute("logined", true);
			session.setAttribute("userid", userId);
		}
		return result;
	}

}
