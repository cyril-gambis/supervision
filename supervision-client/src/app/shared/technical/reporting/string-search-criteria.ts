import { SearchCriteria } from "./search-criteria";
import { ReportOperator } from "./report-operator";

export class StringSearchCriteria implements SearchCriteria {
    key: string;
    operator: ReportOperator;
    value: string;
    criteriaType: string = 'string';

    public constructor(key: string, operator: ReportOperator, value: string) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    getValue(): string {
        return this.value;
    }
}