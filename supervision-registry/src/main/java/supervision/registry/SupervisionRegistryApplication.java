package supervision.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SupervisionRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupervisionRegistryApplication.class, args);
	}
}
