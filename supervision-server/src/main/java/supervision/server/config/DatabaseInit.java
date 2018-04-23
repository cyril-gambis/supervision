package supervision.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import supervision.server.supervisor.Role;
import supervision.server.supervisor.Supervisor;
import supervision.server.supervisor.repository.SupervisorRepository;

@Component
public class DatabaseInit {

	@Autowired
	SupervisorRepository supervisorRepository;
	/*
	@Autowired
	AnnouncementRepository announcementRepository;
	@Autowired
	AnnouncementDetailRepository announcementDetailRepository;
	*/
	public void reset() {
		System.out.println("Resetting");
	}
	
	public void init() {
		System.out.println("Initialization starting");
		
		// Create admin user if it doesn't exist
		Supervisor adminAccount = supervisorRepository.findByUsername("admin");
		if (adminAccount != null) {
			System.out.println("Administrator account found in database");
		} else {
			System.out.println("Administrator account not found in database, it will be created");
			supervisorRepository.save(new Supervisor("admin", "admin", "Admin", "", Role.ADMIN));
		}		
				
	}

	/* Example for  launching a script in the classpath at startup
	@Value("classpath:schema.sql")
	private Resource schemaScript;

	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
	    DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(databasePopulator());
	    return initializer;
	}
	 
	private DatabasePopulator databasePopulator() {
	    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	    populator.addScript(schemaScript);
	    return populator;
	}
	*/
	
}