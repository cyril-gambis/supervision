package supervision.server.usagelog;

import lombok.Data;

@Data
public class UserAction {

    private Long id;
    private ActionCategory actionCategory;
    private ActionType actionType;

}