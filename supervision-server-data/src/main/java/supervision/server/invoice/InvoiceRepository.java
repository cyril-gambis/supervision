package supervision.server.invoice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "invoices", path = "invoices")
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	@Query("SELECT i FROM Customer c, Invoice i WHERE "
			+ "c.license.purchase = i.purchase "
			+ "AND c.id = :customerId "
			+ "ORDER BY i.invoiceDate DESC")
	List<Invoice> findByCustomerId(Long customerId);

}
