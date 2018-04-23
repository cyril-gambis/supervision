import { SearchCriteria } from "./search-criteria";
import { ReportOperator } from "./report-operator";

export class IntegerSearchCriteria implements SearchCriteria {
    key: string;
    operator: ReportOperator;
    value: number;

    criteriaType: string = 'integer';

    public constructor(key: string, operator: ReportOperator, value: number) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    getValue(): number {
        return this.value;
    }
}