package hello.sboot.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
//@AutoConfigureAfter({ OobyActivitiConfig.class })
@ImportResource("classpath:activiti.cfg.xml")
public class OobyActivitiServiceConfig {
	
}
