package supervision.server;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import supervision.server.user.User;
import supervision.server.user.UserRepository;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories
public class SupervisionServerDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupervisionServerDataApplication.class, args);
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
	
	@Bean
	public CommandLineRunner demo(UserRepository userRepository) {
		return (args) -> {
/*
			accountRepository.save(new Account("admin", "admin", Role.ADMIN));
			accountRepository.save(new Account("user", "", Role.USER));
			
			User tom = userRepository.save(new User("Tom", "Moloch"));
			User lucie = userRepository.save(new User("Lucie", "Courtois"));
*/
			Optional<User> findByPrimaryEmailAddress = userRepository.findByPrimaryEmailAddress("patricka@test.com");
			if (findByPrimaryEmailAddress.isPresent()) {
				System.out.println("User found: " + findByPrimaryEmailAddress.get().getFirstName());
				System.out.println("His email: " + findByPrimaryEmailAddress.get().getPrimaryEmail().getEmailAddress());
			} else {
				System.out.println("User not found");
			}
		};
	}
}
