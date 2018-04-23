import { ReportOperator } from "./report-operator";

export interface SearchCriteria {
    key: string;
    operator: ReportOperator;
    criteriaType: string;
    getValue(): any;
}