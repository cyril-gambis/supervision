package supervision.server.usagelog;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import supervision.server.user.User;

@Projection(name= "usagelogfullprojection", types = UsageLog.class)
public interface UsageLogFullProjection {

	Long getId();

	LocalDateTime getDate();

    Long getTargetId();

    @Value("#{target.usageLogPage.url}")
    String getUrl();
    
    @Value("#{target.usageLogPage.description}")
    String getPage();

    @Value("#{target.usageLogPage.categoryDescription}")
    String getCategory();

    @Value("#{target.usageLogPage.entityName}")
    String getEntity();
    
    @Value("#{target.usageLogPage.actionTypeDescription}")
    String getActionType();

    @Value("#{target.usageLogPage.id}")
    Long getPageId();

/*
    @Value("#{@alertFormatService.formatMessage(target, @currentUserService.getUserLanguage())}")
    String getFormattedMessage();
*/
/*
    @Value("#{target.getUser().getFirstName()}")
    String getFirstName();
    
    @Value("#{target.getUser().getLastName()}")
    String getLastName();

    @Value("#{target.getUser().getPrimaryEmail().getEmailAddress()}")
    String getEmailAddress();
*/
    User getUser();
}
