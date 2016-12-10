package hello.sboot.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.activiti.bpmn.model.Resource;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


public class OobyActivitiConfig {

	public DataSource datasource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setUrl("jdbc:h2:mem:ooby;DB_CLOSE_DELAY=1000");
		ds.setUsername("sa");
		ds.setPassword("");
		ds.setDriverClass(org.h2.Driver.class);

		return ds;
	}

	public DataSourceTransactionManager ts(DataSource ds) {
		DataSourceTransactionManager ts = new DataSourceTransactionManager();
		ts.setDataSource(ds);
		return ts;
	}

	@Bean
	public SpringProcessEngineConfiguration pec() {
		DataSource ds = datasource();

		SpringProcessEngineConfiguration pec = new SpringProcessEngineConfiguration();
		pec.setDataSource(ds);
		pec.setTransactionManager(ts(ds));
		pec.setDatabaseSchemaUpdate("create-drop");
		pec.setJobExecutorActivate(false);
		String[] a = { "ooby/workflow/activiti/db/mapping/OobyGroup.xml",
				"ooby/workflow/activiti/db/mapping/OobyUser.xml", "ooby/workflow/activiti/db/mapping/OobyPost.xml" };
		Set<String> set = new HashSet<String>(Arrays.asList(a));
		pec.setCustomMybatisXMLMappers(set);
		
		
		return pec;
	}
	
	@Bean
	public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration pec){
		ProcessEngineFactoryBean engine=new ProcessEngineFactoryBean();
		engine.setProcessEngineConfiguration(pec);
		return engine;
	}

}
