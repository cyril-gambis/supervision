package supervision.server.license;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;

import lombok.Data;
import supervision.server.user.User;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@Table(name = "license")
public class License {

    @NotNull
    @Column(name = "ID", precision = 19)
    @Id
    private Long id;

    @Column(name = "CLOSING_DATE", length = 19)
    private LocalDateTime closingDate;
    @NotNull
    @Column(name = "ENABLE_ADVANCED_LOGIN_SECURITY", nullable = false, precision = 3)
    private Integer enableAdvancedLoginSecurity;
    @NotNull
    @Column(name = "ENABLE_COURSEWARE", nullable = false, precision = 3)
    private Integer enableCourseware;
    @NotNull
    @Column(name = "ENABLE_CUSTOM_REPORTS", nullable = false, precision = 3)
    private Integer enableCustomReports;
    @NotNull
    @Column(name = "ENABLE_CUSTOM_WORK_WEEK", nullable = false, precision = 3)
    private Integer enableCustomWorkWeek;
    @NotNull
    @Column(name = "ENABLE_DASHBOARD", nullable = false, precision = 3)
    private Integer enableDashboard;
    @NotNull
    @Column(name = "ENABLE_EXCEL_EXPORT", nullable = false, precision = 3)
    private Integer enableExcelExport;
    @NotNull
    @Column(name = "ENABLE_FEEDBACK", nullable = false, precision = 3)
    private Integer enableFeedback;
    @NotNull
    @Column(name = "ENABLE_GOOGLE_APPS", nullable = false, precision = 3)
    private Integer enableGoogleApps;
    @NotNull
    @Column(name = "ENABLE_GOOGLE_CALENDAR", nullable = false, precision = 3)
    private Integer enableGoogleCalendar;
    @NotNull
    @Column(name = "ENABLE_GOOGLE_DOCS", nullable = false, precision = 3)
    private Integer enableGoogleDocs;
    @NotNull
    @Column(name = "ENABLE_MULTI_PROJECT_REPORTS", nullable = false, precision = 3)
    private Integer enableMultiProjectReports;
    @NotNull
    @Column(name = "ENABLE_MY_REPORTS", nullable = false, precision = 3)
    private Integer enableMyReports;
    @NotNull
    @Column(name = "ENABLE_PLANNING_CONSTRAINTS", nullable = false, precision = 3)
    private Integer enablePlanningConstraints;
    @NotNull
    @Column(name = "ENABLE_PRINT_PDF", nullable = false, precision = 3)
    private Integer enablePrintPdf;
    @NotNull
    @Column(name = "ENABLE_PROJECT_REPORTS", nullable = false, precision = 3)
    private Integer enableProjectReports;
    @NotNull
    @Column(name = "ENABLE_SCHEDULE", nullable = false, precision = 3)
    private Integer enableSchedule;
    @NotNull
    @Column(name = "ENABLE_SECURED_PUBLICATION", nullable = false, precision = 3)
    private Integer enableSecuredPublication;
    @NotNull
    @Column(name = "ENABLE_TIMESHEET", nullable = false, precision = 3)
    private Integer enableTimesheet;
    @Column(name = "EXPIRATION_DATE", length = 19)
    private LocalDateTime expirationDate;
    @Column(name = "NEXT_REMINDER_DATE", length = 19)
    private LocalDateTime nextReminderDate;
    @NotNull
    @Column(name = "PAYMENT_ERROR", nullable = false, precision = 3)
    private Integer paymentError;
    @NotNull
    @Column(name = "SERVICE_MAJOR_VERSION", nullable = false, precision = 10)
    private Integer serviceMajorVersion;
    @Column(name = "STATUS", precision = 10)
    private Integer status;
    @Column(name = "TERMS_AND_CONDITIONS_ACCEPTED_DATE", length = 19)
    private LocalDateTime termsAndConditionsAcceptedDate;

    // Many to one
    @JoinColumn(name = "TERMS_AND_CONDITIONS_ACCEPTED_BY_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private User termsAndConditionsAcceptedBy;
    @NotNull
    @JoinColumn(name = "PLAN_FK", nullable = false)
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private LicensePlan plan;
    
//    @JoinColumn(name = "ACCOUNT_FK")
  //  @ManyToOne
    //@NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)

//    private Account account;

    
//    @JoinColumn(name = "PURCHASE_FK")
  //  @ManyToOne
   // @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)

    //    private Purchase purchase;

}