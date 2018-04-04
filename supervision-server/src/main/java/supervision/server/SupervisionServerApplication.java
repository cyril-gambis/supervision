package supervision.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import supervision.server.config.DatabaseInit;

@SpringBootApplication

// Use this to disable datasource
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

@EnableJpaRepositories

public class SupervisionServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupervisionServerApplication.class, args);
	}
	

	@Bean
	public CommandLineRunner demo(DatabaseInit databaseInit) {
		return (args) -> {
/*
			accountRepository.save(new Account("admin", "admin", Role.ADMIN));
			accountRepository.save(new Account("user", "", Role.USER));
			
			User tom = userRepository.save(new User("Tom", "Moloch"));
			User lucie = userRepository.save(new User("Lucie", "Courtois"));
*/
			databaseInit.init();
			
		};
	}

	
}
