package hello.sboot.dao;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hello.sboot.entity.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPersonDao {
	
	@Autowired
	private PersonDao dao;
	
	@Test
	public void testCRUD(){
		Person person=new Person();
		person.setName("hou");
		person.setAge("18");
		person=dao.save(person);
		System.out.println("psersonId:"+person.getId());
		assertThat(person.getId(), notNullValue());
	}
}
