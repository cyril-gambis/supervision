import { ReportEntity } from "./report-entity";
import { SearchCriteria } from "./search-criteria";
import { SortDirection } from "./sort-direction";

export class ReportCommand {
    criterias: SearchCriteria[] = [];
    sortFieldName: string = "id";
    sortDirection: SortDirection = SortDirection.ASC;
    page: number = 0;
    nbItemsPerPage: number = 100;
}