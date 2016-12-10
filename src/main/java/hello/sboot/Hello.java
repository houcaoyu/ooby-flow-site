package hello.sboot;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.sboot.entity.Person;

@SpringBootApplication
@Controller
public class Hello {
	
	@RequestMapping("/")
	@ResponseBody
	public String index(){
		return "Hello World!";
	}
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		return "Hello once agin";
	}
	
	@RequestMapping("/th")
	public String thymeleaf(Map<String,String> map){
		//map.put("hello","abce");
		return "thy";
	}
	
	@RequestMapping("/test")
	public String test(Person person){
		System.out.println("name:"+person.getName()+",age:"+person.getAge());
		return "thy";
	}
	
	public static void main(String[] args) {
        SpringApplication.run(Hello.class, args);
    }
}
