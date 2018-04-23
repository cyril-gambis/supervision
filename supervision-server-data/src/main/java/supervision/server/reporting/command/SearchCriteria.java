package supervision.server.reporting.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo (use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "criteriaType")
@JsonSubTypes ({@Type (value = StringSearchCriteria.class, name = "string"),
		@Type (value = DateSearchCriteria.class, name = "date"),
		@Type (value = LongSearchCriteria.class, name = "long"),
		@Type (value = IntegerSearchCriteria.class, name = "integer")})

public interface SearchCriteria {

	String getKey();
	
	ReportOperator getOperator();
	
	Object getValue();
	
}
