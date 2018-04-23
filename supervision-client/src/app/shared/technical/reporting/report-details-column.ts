export class ReportDetailsColumn {
    field: string;
    header: string;
    date: boolean;
    filter: boolean;
    group: boolean;
    styleClass?: string = '';
    bigString?: boolean = false;
    hiddenByDefault?: boolean = false;
    style: any;
}
