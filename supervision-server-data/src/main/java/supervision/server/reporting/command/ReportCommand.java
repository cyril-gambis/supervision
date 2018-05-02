package supervision.server.reporting.command;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import lombok.Data;

@Data
public class ReportCommand {

	public static final String ID_FIELD_NAME = "id";

	public static final int NB_MAX_RESULTS = 200;

	private ReportEntity reportEntity;
	
	private List<SearchCriteria> criterias;
	
	private String sortFieldName;
	
	private Direction sortDirection = Direction.ASC;

	private int page = 0;
	
	private int nbItemsPerPage = NB_MAX_RESULTS;

}
