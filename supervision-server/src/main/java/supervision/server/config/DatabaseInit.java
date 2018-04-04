package supervision.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import supervision.server.account.Account;
import supervision.server.account.Role;
import supervision.server.account.repository.AccountRepository;
import supervision.server.user.User;
import supervision.server.user.repository.UserRepository;

@Component
public class DatabaseInit {

	@Autowired
	UserRepository userRepository;
	@Autowired
	AccountRepository accountRepository;
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
		Account adminAccount = accountRepository.findByUsername("admin");
		if (adminAccount != null) {
			System.out.println("Administrator account found in database");
		} else {
			System.out.println("Administrator account not found in database, it will be created");
			accountRepository.save(new Account("admin", "admin", Role.ADMIN));
		}


		User tom = userRepository.save(new User("Tom", "Moloch"));
		userRepository.save(tom);
		
	}
	
}