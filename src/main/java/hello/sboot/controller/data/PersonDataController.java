package hello.sboot.controller.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.sboot.dao.AddressDao;
import hello.sboot.dao.PersonDao;
import hello.sboot.entity.Address;
import hello.sboot.entity.Person;

@Controller
@RequestMapping("/person")
public class PersonDataController {
	
	@Autowired
	private PersonDao personDao;
	@Autowired
	private AddressDao addressDao;
	
	@RequestMapping("/list")
	@ResponseBody
	public Iterable<Person> list(){
		Address addr=new Address();
		addr.setAdd1("addr1");
		addr.setAdd2("addr2");
		addressDao.save(addr);
		
		Person p=new Person();
		p.setName("hou");
		p.setAge("18");
		p.setAddress(addr);
		
		personDao.save(p);
	
		p=new Person();
		p.setName("wang");
		p.setAge("20");
		personDao.save(p);
		return personDao.findAll();
	}
}
