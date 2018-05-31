package supervision.server.invoice;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "invoice")
@Data
public class Invoice {

    @NotNull
    @Column(name = "ID", precision = 19)
    @Id
    private Long id;

    @Column(name = "INVOICE_DATE", length = 19)
    private LocalDateTime invoiceDate;

    @Column(name = "INVOICE_NUMBER", precision = 10)
    private Integer invoiceNumber;

    @Column(name = "PAYMENT_DATE", length = 19)
    private LocalDateTime paymentDate;

    @Column(name = "PAYMENT_ID", length = 256)
    private String paymentId;

    @Column(name = "PAYMENT_STATUS", length = 256)
    private String paymentStatus;

    @Size(max = 4000)
    @Column(name = "TITLE", length = 4000)
    private String title;

    @Size(max = 4000)
    @Column(name = "TITLE1", length = 4000)
    private String title1;

    @Size(max = 4000)
    @Column(name = "TITLE2", length = 4000)
    private String title2;

    @Column(name = "AMOUNT", precision = 22)
    private Double amount;

    @Column(name = "AMOUNT1", precision = 22)
    private Double amount1;

    @Column(name = "AMOUNT2", precision = 22)
    private Double amount2;

    @Column(name = "VAT_AMOUNT", precision = 22)
    private Double vatAmount;
    
    @Column(name = "`TYPE`", precision = 10)
    private Integer type;

    @Column(name = "NEXT_INVOICE_DATE", length = 19)
    private LocalDateTime nextInvoiceDate;

    @Column(name = "PAYMENT_METHOD", precision = 10)
    private Integer paymentMethod;

    @Column(name = "SUBSCRIPTION_END_DATE", length = 19)
    private LocalDateTime subscriptionEndDate;

    @Column(name = "SUBSCRIPTION_START_DATE", length = 19)
    private LocalDateTime subscriptionStartDate;

    @Column(name = "INVOICE_STATUS", precision = 10)
    private Integer invoiceStatus;

    @JoinColumn(name = "PURCHASE_FK")
    @ManyToOne
    private Purchase purchase;

}
