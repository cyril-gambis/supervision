import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'

import { UsageLog } from "../models/usage-log";
import { ReportCommand } from "../technical/reporting/report-command";
import { LastAccess } from "../models/last-access";
import { CountByMonth } from "../models/count-by-month";
 
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
            .map((logs) => {
                logs.sort((l1: UsageLog, l2: UsageLog) => {
                    return l1.date > l2.date ? -1 : 1;
                });
                return logs;
            });
    }

    getLastOverviewLogs(customerId: number): Observable<UsageLog[]> {
        return this.http.get<UsageLog[]>('/api-data/findLastOverviewLogsByCustomerId?customerId=' + customerId)
            .map((logs) => {
                logs.sort((l1: UsageLog, l2: UsageLog) => {
                    return l1.date > l2.date ? -1 : 1;
                });
                return logs;
            });
    }
 
    refreshLastAccesses(customerId: number): Observable<string> {
        return this.http.get<string>('/api/usageLogOpt-custom/computeLastAccesses?customerId=' + customerId);
    }

    getLastAccesses(customerId: number): Observable<LastAccess[]> {
        return this.http.get<LastAccess[]>('/api/lastAccesses/search/findByCustomerId?customerId=' + customerId)
            .map(res => res['_embedded']['lastAccesses'])
            .map((logs) => {
                logs.sort((l1: LastAccess, l2: LastAccess) => {
                    if (!l1.lastAccessDate) {
                        return 1;
                    }
                    if (!l2.lastAccessDate) {
                        return -1;
                    }
                    return l1.lastAccessDate > l2.lastAccessDate ? -1 : 1;
                });
                return logs;
            });
    }

    refreshAllAccesses(customerId: number): Observable<string> {
        return this.http.get<string>('/api/usageLogOpt-custom/computeAllAccesses?customerId=' + customerId);
    }

    refreshAllAccessesAllPages(customerId: number): Observable<string> {
        return this.http.get<string>('/api/usageLogOpt-custom/computeAllAccessesAllPages?customerId=' + customerId);
    }

    getCountByMonth(customerId: number): Observable<CountByMonth[]> {
        return this.http.get<CountByMonth[]>('/api/usageLogOpt-custom/getCountByMonth?customerId=' + customerId)
            .map((counts) => {
                counts.sort((c1: CountByMonth, c2: CountByMonth) => {
                    if (c1.year > c2.year) {
                        return 1;
                    } else if (c1.year === c2.year) {
                        return c1.month > c2.month ? 1 : -1;
                    } else {
                        return -1;
                    }
                });
                return counts;
        });;
    }

    getCountByPageId(customerId: number, pageId: number): Observable<number> {
        return this.http.get<number>('/api/nbHitsPerMonths/search/countByCustomerIdAndPageId?customerId=' + customerId
            + '&pageId=' + pageId);
    }
    
}