import { SearchCriteria } from "./search-criteria";
import { ReportOperator } from "./report-operator";

export class LongSearchCriteria implements SearchCriteria {
    key: string;
    operator: ReportOperator;
    value: number;

    criteriaType: string = 'long';

    public constructor(key: string, operator: ReportOperator, value: number) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    getValue(): number {
        return this.value;
    }
}