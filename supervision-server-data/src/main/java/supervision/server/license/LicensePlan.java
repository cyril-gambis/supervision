package supervision.server.license;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;

import lombok.Data;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@Table(name = "license_plan")
public class LicensePlan {

    @NotNull
    @Column(name = "ID", precision = 19)
    @Id
    private Long id;
    @Column(name = "ACCOUNT_TYPE", precision = 10)
    private Integer accountType;
    @Column(name = "TASKS_TO_CREATE", precision = 10)
    private Integer tasksToCreate;
    @Column(name = "ALLOCATIONS_TO_CREATE", precision = 10)
    private Integer allocationsToCreate;
    @Size(max = 4000)
    @Column(name = "BACK_OFFICE_COMMENTS", length = 4000)
    private String backOfficeComments;

    @Size(max = 4000)
    @Column(name = "CLOSING_SURVEY_LINK", length = 4000)
    private String closingSurveyLink;
    @Size(max = 4000)
    @Column(name = "DESCRIPTION", length = 4000)
    private String description;
    @Column(name = "DOCUMENTS_TO_CREATE", precision = 10)
    private Integer documentsToCreate;

    @Column(name = "EDITION", precision = 10)
    private Integer edition;
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
    @Column(name = "FREE_PERIOD", precision = 10)
    private Integer freePeriod;
    @Column(name = "GRACE_PERIOD", precision = 10)
    private Integer gracePeriod;
    @Column(name = "INVOICE_FEE1", precision = 22)
    private Double invoiceFee1;

    @Column(name = "INVOICE_FEE12", precision = 22)
    private Double invoiceFee12;
    @NotNull
    @Column(name = "IS_FREE", nullable = false, precision = 3)
    private Integer isFree;
    @Column(name = "LIFETIME_PERIOD", precision = 10)
    private Integer lifetimePeriod;
    @Column(name = "MAX_CLOSED_PROJECTS", precision = 10)
    private Integer maxClosedProjects;
    @Column(name = "MAX_CUSTOMERS_PER_OWNER", precision = 10)
    private Integer maxPlanzonesPerOwner;
    @Column(name = "MAX_OPEN_PROJECTS", precision = 10)
    private Integer maxOpenProjects;
    @Column(name = "MAX_PROJECT_MANAGERS", precision = 10)
    private Integer maxProjectManagers;
    @Column(name = "MAX_STORAGE", precision = 19)
    private Long maxStorage;
    @Column(name = "MAX_USAGE_COUNT", precision = 10)
    private Integer maxUsageCount;
    @Column(name = "MAX_USER_ACCOUNTS", precision = 10)
    private Integer maxUserAccounts;
    @Column(name = "MESSAGES_TO_CREATE", precision = 10)
    private Integer messagesToCreate;
    @Column(name = "MILESTONES_TO_CREATE", precision = 10)
    private Integer milestonesToCreate;
    @Size(max = 256)
    @Column(name = "NAME", length = 256)
    private String name;
    @Column(name = "PAGES_TO_CREATE", precision = 10)
    private Integer pagesToCreate;
    @Size(max = 256)
    @Column(name = "PROMOTION_CODE", length = 256)
    private String promotionCode;
    @Column(name = "RANK", precision = 5)
    private Integer rank;
    @NotNull
    @Column(name = "SERVICE_MAJOR_VERSION", nullable = false, precision = 10)
    private Integer serviceMajorVersion;

    @NotNull
    @Column(name = "SHOW_ADS", nullable = false, precision = 3)
    private Integer showAds;
    @Column(name = "STATUS", precision = 10)
    private Integer status;
    @Column(name = "TODOS_TO_CREATE", precision = 10)
    private Integer todosToCreate;
    @Column(name = "TRIAL_PERIOD", precision = 10)
    private Integer trialPeriod;
    @Size(max = 256)
    @Column(name = "URL_TERMS_AND_CONDITIONS", length = 256)
    private String urlTermsAndConditions;

    @JoinColumn(name = "UPGRADE_PLAN_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private LicensePlan upgradePlan;
    
    @JoinColumn(name = "BASE_PLAN_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private LicensePlan basePlan;

}