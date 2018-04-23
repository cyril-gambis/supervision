import { SearchCriteria } from "./search-criteria";
import { ReportOperator } from "./report-operator";

export class DateSearchCriteria implements SearchCriteria {
    key: string;
    operator: ReportOperator;
    value: Date;

    criteriaType: string = 'date';

    public constructor(key: string, operator: ReportOperator, value: Date) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    getValue(): Date {
        return this.value;
    }
}