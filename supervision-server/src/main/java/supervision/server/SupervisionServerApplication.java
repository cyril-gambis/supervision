package supervision.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import supervision.server.config.DatabaseInit;

@SpringBootApplication

// Use this to disable datasource
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

@EnableJpaRepositories

// Find other microservices through Netflix Eureka registry and 
// subscribe itself to the registry
@EnableEurekaClient

// Enable usage of Feign Rest Client for other microservices calls 
// through HTTP REST
@EnableFeignClients
public class SupervisionServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupervisionServerApplication.class, args);
	}
	
	/**
	 * Création du Rest Template
	 * L'annotation @LoadBalanced va permettre à Eureka de remplacer le nom des services
	 * dans les url par les adresses réelles des serveurs
	 * 
	 * @author Cyril Gambis
	 * @param builder
	 * @return
	 */
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate t = builder.build();
		return t;
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate2() {
		return new RestTemplate();
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
