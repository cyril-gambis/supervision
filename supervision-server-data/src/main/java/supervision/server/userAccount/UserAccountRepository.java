package supervision.server.userAccount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "userAccounts", path = "userAccounts")
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
/*

    @Query("SELECT ua FROM UserAccount ua WHERE ua.user.id = :userId AND ua.customer.id = :customerId "
    		+ "AND ua.deleted=0")
    UserAccount findByUserIdAndCustomerId(Long userId, Long customerId);

    @Query("SELECT ua FROM ProjectTeamMember ptm JOIN ptm.userAccount ua WHERE ptm.project.id = :projectId "
    		+ "AND ptm.deleted = 0 AND ua.deleted=0")
    List<UserAccount> findByProjectId(long projectId);

    @Query("SELECT ua FROM UserAccount ua JOIN ua.user u JOIN u.primaryEmail m WHERE m.emailAddress = :emailAddress "
    		+ "AND u.deleted = 0 AND ua.deleted=0")
    List<UserAccount> findByEmailAddress(String emailAddress);

    UserAccount findById(Long id);
    
    @Query("SELECT ua FROM Discussion d JOIN d.project p JOIN p.teamMembers ptm JOIN ptm.userAccount ua WHERE d.id = :discussionId "
    		+ "AND d.deleted = 0 AND ua.deleted = 0")
    List<UserAccount> findParticipantsByDiscussionId(Long discussionId);
  */  
    List<UserAccount> findByCustomerId(Long customerId);

}