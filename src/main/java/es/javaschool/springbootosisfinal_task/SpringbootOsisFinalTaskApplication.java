package es.javaschool.springbootosisfinal_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class SpringbootOsisFinalTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootOsisFinalTaskApplication.class, args);
	}

}
