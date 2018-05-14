package supervision.server.usagelog;

import lombok.Data;

@Data
public class ActionCategory {

    private Long id;
    private String description;
    private String entityName;

}