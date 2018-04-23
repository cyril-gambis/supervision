package supervision.server.reporting.specification;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.google.common.base.Strings;

import supervision.server.reporting.command.ReportCommand;
import supervision.server.reporting.command.SearchCriteria;

public class ReportSpecificationHelper {
	
	public static <T> List<T> searchItems(ReportCommand command, SpecificationBuilder<T> builder, JpaSpecificationExecutor<T> repository,
			boolean usePaging) {
		for (SearchCriteria crit : command.getCriterias()) {
			builder.with(crit);	
		}
		Specification<T> spec = builder.build();
		
		String sortFieldName = command.getSortFieldName();
		if (Strings.isNullOrEmpty(sortFieldName)) {
			sortFieldName = ReportCommand.ID_FIELD_NAME;
		}
		Sort sorting = new Sort(new Order(command.getSortDirection(), sortFieldName));

		int pageIndex = 0;
		int size = ReportCommand.NB_MAX_RESULTS;
		if (usePaging) {
			pageIndex = command.getPage();
			size = command.getNbItemsPerPage();
		}
		
		Pageable pageRequest = new PageRequest(pageIndex, size, sorting);
		
		Page<T> items = repository.findAll(spec, pageRequest);

		return items.getContent();
	}

	public static <T> List<T> searchPagedItems(ReportCommand command, SpecificationBuilder<T> builder, JpaSpecificationExecutor<T> repository) {
		return searchItems(command, builder, repository, true);
	}
	
	public static <T> List<T> searchAllItems(ReportCommand command, SpecificationBuilder<T> builder, JpaSpecificationExecutor<T> repository) {
		return searchItems(command, builder, repository, false);
	}

	public static <T> long count(ReportCommand command, SpecificationBuilder<T> builder, JpaSpecificationExecutor<T> repository) {
		for (SearchCriteria crit : command.getCriterias()) {
			builder.with(crit);	
		}
		Specification<T> spec = builder.build();
		
		return repository.count(spec);
	}
	
}
