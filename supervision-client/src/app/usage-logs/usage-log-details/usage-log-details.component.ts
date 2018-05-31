import { Component, OnInit } from '@angular/core';
import { UsageLog } from '../../shared/models/usage-log';
import { UsageLogService } from '../../shared/services/usage-log.service';
import { ReportCommand } from '../../shared/technical/reporting/report-command';
import { SortDirection } from '../../shared/technical/reporting/sort-direction';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from '../../shared/services/customer.service';
import { Customer } from '../../shared/models/customer';
import { LongSearchCriteria } from '../../shared/technical/reporting/long-search-criteria';
import { Logger } from '../../shared/technical/logger';

@Component({
  selector: 'app-usage-log-details',
  templateUrl: './usage-log-details.component.html',
  styleUrls: ['./usage-log-details.component.css']
})
export class UsageLogDetailsComponent implements OnInit {

  log = new Logger(Logger.DEBUG);

  id: number;

  usageLogs: UsageLog[] = [];

  columnsToDisplay = ['date', 'user', 'category', 'entity', 'actionType', 'targetId'];

  customer: Customer = undefined;

  constructor(
    private usageLogService: UsageLogService,
    private customerService: CustomerService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.log.l('Inside ngOnInit, id: ', params);
      if (this.id) {
        this.customerService.getCustomer(this.id)
          .subscribe(customer => {
            this.customer = customer
            this.getLastUsageLogs();
          });
      }
    });      
  }

  getLastUsageLogs() {
    this.usageLogService.getLastOverviewLogs(this.customer.id)
      .subscribe(data => this.usageLogs = data);
  }
 
}
