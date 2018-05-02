import { LicensePlan } from "./license-plan";

export class License {
    id: number;
    closingDate: Date;
    enableAdvancedLoginSecurity: number;
    enableCourseware: number;
    enableCustomReports: number;
    enableCustomWorkWeek: number;
    enableDashboard: number;
    enableExcelExport: number;
    enableFeedback: number;
    enableGoogleApps: number;
    enableGoogleCalendar: number;
    enableGoogleDocs: number;
    enableMultiProjectReports: number;
    enableMyReports: number;
    enablePlanningConstraints: number;
    enablePrintPdf: number;
    enableProjectReports: number;
    enableSchedule: number;
    enableSecuredPublication: number;
    enableTimesheet: number;
    expirationDate: Date;
    nextReminderDate: Date;
    paymentError: number;
    serviceMajorVersion: number;
    status: number;
    termsAndConditionsAcceptedDate: Date;

    plan: LicensePlan;
}