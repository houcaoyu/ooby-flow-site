package hello.sboot.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {
	
	@RequestMapping(path="/loginform")
	public String loginForm(){
		return "login_form";
	}
	
	@RequestMapping("/logout")
	public String logout(){
		return null;
	}
}
