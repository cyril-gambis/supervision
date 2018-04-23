import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { UsageLog } from "../models/usage-log";
import { ReportCommand } from "../technical/reporting/report-command";
 
@Injectable()
export class UsageLogService {

    constructor(private http: HttpClient) { }
 
    getUsageLogs(reportCommand: ReportCommand): Observable<UsageLog[]> { 
        return this.http.post<UsageLog[]>('/api-data/searchUsageLogs', reportCommand);
    }

    getOverviewLogs(customerId: number): Observable<UsageLog[]> {
        /*
        return this.http.get<UsageLog[]>('/api-data/usageLogs/search/findTop100OverviewLogsOrderByDateDesc?projection=usagelogfullprojection')
            .map(res => res['_embedded']['usageLogs']);
        */
       
       return this.http.get<UsageLog[]>('/api-data/findRecentOverviewLogs?customerId=' + customerId)
       ;

    }

}