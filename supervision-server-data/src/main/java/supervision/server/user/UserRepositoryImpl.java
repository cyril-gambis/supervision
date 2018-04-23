package supervision.server.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import supervision.server.mail.Mail;
import supervision.server.mail.MailRepository;

public class UserRepositoryImpl implements UserRepositoryCustom {

	@Autowired
	private MailRepository mailRepository;

	@Override
	public Optional<User> findByUsername(String username) {
		List<Mail> mails = mailRepository.findByEmailAddress(username);
		return mails.stream()
					.filter(m -> m.getUser() != null)
					.findAny()
					.map(Mail::getUser);
	}

}
