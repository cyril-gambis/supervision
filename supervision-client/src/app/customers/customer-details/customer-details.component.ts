import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CustomerService } from '../../shared/services/customer.service';
import { ActivatedRoute } from '@angular/router';
import { Customer } from '../../shared/models/customer';
import { UsageLogService } from '../../shared/services/usage-log.service';
import { UsageLog } from '../../shared/models/usage-log';
import { Logger } from '../../shared/technical/logger';
import { LastAccess } from '../../shared/models/last-access';
import { UserService } from '../../shared/services/user.service';
import { User } from '../../shared/models/user';

import { filter } from 'rxjs/operators';
import { tap, map, flatMap } from 'rxjs/operators';
import { toArray } from 'rxjs/operators/toArray';
import { mergeAll } from 'rxjs/operator/mergeAll';
import { License } from '../../shared/models/license';
import { LicenseService } from '../../shared/services/license.service';
import { DatePipe } from '@angular/common';
import { CountByMonth } from '../../shared/models/count-by-month';


import { forkJoin } from 'rxjs/observable/forkJoin';
import { MatDialog, MatDialogConfig, MatSnackBar } from '@angular/material';
import { AreYouSureComponent } from '../../dialog/are-you-sure/are-you-sure.component';
import { RecentQueryService } from '../../shared/technical/recent-query/recent-query.service';
import { RecentQuery } from '../../shared/technical/recent-query/recent-query';

@Component({
  selector: 'app-customer-details',
  templateUrl: './customer-details.component.html',
  styleUrls: ['./customer-details.component.css']
})
export class CustomerDetailsComponent implements OnInit, AfterViewInit {

  log = new Logger(Logger.DEBUG);

  id: number;

  customer: Customer;

  loading = false;

  license: License = undefined;

  constructor(
    private customerService: CustomerService,
    private usageLogService: UsageLogService,
    private userService: UserService,
    private licenseService: LicenseService,
    private recentQueryService: RecentQueryService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      if (this.id) {
        this.customerService.getCustomer(this.id)
          .subscribe(customer => {
            this.customer = customer;
            this.recentQueryService.addRecentQuery(new RecentQuery(this.id, this.customer.name));
          })
        this.licenseService.getLicenseByCustomerId(this.id)
          .subscribe(license => {
            this.license = license;
          });
      }
    });
  }

  ngAfterViewInit() {
  }

/*
  getLastOverviewLogs() {
    this.usageLogService.getLastOverviewLogs(this.id)
      .subscribe(logs => this.u = logs);
  }
*/


  get formattedStatus(): string {
    return this.customerService.formattedStatus(this.customer);
  }

  get statusClass(): string {
    return this.customerService.statusClass(this.customer);
  }

  datePipe = new DatePipe('en-US');
  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'dd/MM/y');
  }


}
