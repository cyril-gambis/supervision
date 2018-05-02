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

import { Chart } from 'chart.js';
import 'chart.piecelabel.js';
import { forkJoin } from 'rxjs/observable/forkJoin';
import { MatDialog, MatDialogConfig, MatSnackBar } from '@angular/material';
import { AreYouSureComponent } from '../../dialog/are-you-sure/are-you-sure.component';

@Component({
  selector: 'app-customer-details',
  templateUrl: './customer-details.component.html',
  styleUrls: ['./customer-details.component.css']
})
export class CustomerDetailsComponent implements OnInit, AfterViewInit {

  log = new Logger(Logger.DEBUG);

  id: number;

  customer: Customer;

//  usageLogs: UsageLog[] = [];

  loading = false;

  userAccesses: LastAccess[] = [];

  columnsToDisplay = ['user', 'date'];
  columnsToDisplay2 = ['user'];

  lastRefreshDate: Date = undefined;

  neverConnectedUsers: User[] = []

  license: License = undefined;

  lastRefreshAllDate: Date = undefined;
  info = '';
  countByMonth : CountByMonth[] = [];

  constructor(
    private customerService: CustomerService,
    private usageLogService: UsageLogService,
    private userService: UserService,
    private licenseService: LicenseService,
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
            this.getCountByMonth();
            this.displayActivityAllChart()
          })
        this.getLastAccesses();
        this.licenseService.getLicenseByCustomerId(this.id)
          .subscribe(license => {
            console.log(license);
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
  getLastAccesses() {
    this.usageLogService.getLastAccesses(this.id)
      .subscribe(logs => {
        this.userAccesses = logs;
        if (this.userAccesses.length > 0) {
          this.lastRefreshDate = this.userAccesses[0].lastCalculation;
        }
        this.getNeverConnectedUsers();
        this.loading = false;
      });
  }

  getNeverConnectedUsers() {
    this.userService.getUsersByCustomerId(this.id).pipe(
      // Change array to stream of user
      flatMap(user => user),
      // Filter for only the users that have no log
      filter(user => !this.userAccesses.some(log => log.userId === user.id)),
      // Transform stream into array
      toArray()
      )
      .subscribe(users => {
        this.neverConnectedUsers = users;
      });
  }

  get formattedStatus(): string {
    return this.customerService.formattedStatus(this.customer);
  }

  get statusClass(): string {
    return this.customerService.statusClass(this.customer);
  }

  getUsers() {
    // get the users with blank usage logs
    // put 
  }

  refreshLastAccesses() {
    this.loading = true;
    this.userAccesses = [];
    this.usageLogService.refreshLastAccesses(this.customer.id)
      .subscribe(res => {
        this.getLastAccesses();
        this.log.d('Result of refreshLastAccesses: ' + res);
      });
  }

  refreshAllAccesses() {

    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      message: 'Are you sure you want to compute the data for the overview page (main access)?'
    }

    let dialogRef = this.dialog.open(AreYouSureComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(value => {
      if (value) {
        this.loading = true;
        this.usageLogService.refreshAllAccesses(this.customer.id)
          .subscribe(res => {
            this.info = res;
            this.loading = false;
          });        
      }
    });

  }

  refreshAllAccessesAllPages() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      message: 'Are you sure you want to compute the data for ALL THE PAGES (takes some time)?'
    }

    let dialogRef = this.dialog.open(AreYouSureComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(value => {
      if (value) {
        this.loading = true;
        this.usageLogService.refreshAllAccessesAllPages(this.customer.id)
          .subscribe(res => {
            this.info = res;
            this.loading = false;
            this.snackBar.open(res, 'Close', {
              duration: 2000
            });
          });
        }
      });
  }

  chartDataDateLabels = [];
  chartDataCounts = [];
  chartDataDates = [];

  chart: Chart = undefined;
  getCountByMonth() {
    this.usageLogService.getCountByMonth(this.customer.id)
      .subscribe(res => {
        this.countByMonth = res;
        // Reset chart data
        this.chartDataDateLabels = [];
        this.chartDataCounts = [];
        this.chartDataDates = [];

        this.countByMonth.forEach(count => {
          let date = new Date(count.year, count.month -1, 1);
          this.chartDataDateLabels.push(date.toLocaleDateString('en', { year: 'numeric', month: 'short', day: 'numeric' }));
          this.chartDataDates.push(date);
          this.chartDataCounts.push(count.count);
        });

        this.createChart();

      });
  }

  datePipe = new DatePipe('en-US');
  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'dd/MM/y');
  }

  createChart() {
    this.chart = new Chart('canvas', {
      type: 'line',
      data: {
        labels: this.chartDataDateLabels,
        datasets: [
          {
            data: this.chartDataCounts,
            borderColor: "#3cba9f",
            fill: false
          }
        ]
      },
      options: {
        legend: {
          display: false
        },
        scales: {
          xAxes: [{
            display: true
          }],
          yAxes: [{
            display: true
          }]
        }
      }
    });
    this.log.d('Chart is', this.chart);
  }

  formatUser(user: User): string {
    if (user.firstName && user.lastName) {
      return user.firstName + ' ' + user.lastName;
    } else if (user.firstName) {
      return user.firstName;
    } else if (user.id) {
      return '<? id:' + user.id + '>';
    }
    return '<undefined>';
  }

  PZ_DISCUSSION_ID = 24;
	PR_OVERVIEW_PAGE_ID = 13;
	PR_TASKS_PAGE_ID = 8;
	PR_WORKLOAD_PAGE_ID = 22;
	PR_SCHEDULE_PAGE_ID = 15;
	PR_TIMESHEET_PAGE_ID = 23;
  PR_DISCUSSION_ID = 28;
	CALENDAR_PAGE_ID = 14;
	REPORTS_ID = 19;
	TEMPLATES_ID = 20;
	EXPORTS_ID = 21;
	MESSAGE_ON_TASK_ID = 30;
	MESSAGE_ON_MILESTONE_ID = 31;
	MESSAGE_ON_EVENT_ID = 32;
	MESSAGE_POPUP_ID = 33;

  nbOverview = 0;
  nbProjects = 0;
  nbTasks = 0;
  nbWorkloads = 0;
  nbSchedule = 0;
  nbTimesheet = 0;
  displayActivityAllChart() {
    this.chartActivityAllLabels = [];
    this.chartActivityAllData = [];
    this.chartActivityColors = [];

    forkJoin(
      this.usageLogService.getCountByPageId(this.customer.id, 2),
      this.usageLogService.getCountByPageId(this.customer.id, 3),
      this.usageLogService.getCountByPageId(this.customer.id, 4),
      this.usageLogService.getCountByPageId(this.customer.id, 5),
      this.usageLogService.getCountByPageId(this.customer.id, 6),
      this.usageLogService.getCountByPageId(this.customer.id, 7),
      this.usageLogService.getCountByPageId(this.customer.id, this.PZ_DISCUSSION_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.PR_OVERVIEW_PAGE_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.PR_TASKS_PAGE_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.PR_WORKLOAD_PAGE_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.PR_SCHEDULE_PAGE_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.PR_TIMESHEET_PAGE_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.PR_DISCUSSION_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.CALENDAR_PAGE_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.REPORTS_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.EXPORTS_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.MESSAGE_ON_TASK_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.MESSAGE_ON_MILESTONE_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.MESSAGE_ON_EVENT_ID),
      this.usageLogService.getCountByPageId(this.customer.id, this.MESSAGE_POPUP_ID)
    )
    .subscribe(([countOverview, countProjects, countTasks, countWorkload, countSchedule, countTimesheet,
        countDiscussion, countOverview2, countTasks2, countWorkload2, countSchedule2, countTimesheet2,
        countDiscussion2, countCalendar, countReports, countExports, countMessage1,
        countMessage2, countMessage3, countMessagePopup]) => {
      this.nbOverview = countOverview;
      this.chartActivityAllData.push(countOverview);
      this.chartActivityAllLabels.push('Overview PZ');
      this.chartActivityColors.push('#3598DB'); //2A80B9

      this.chartActivityAllData.push(countOverview2);
      this.chartActivityAllLabels.push('Overview PR');
      this.chartActivityColors.push('#2A80B9');

      this.nbProjects = countProjects;
      this.chartActivityAllData.push(countProjects);
      this.chartActivityAllLabels.push('Pz Projects');
      this.chartActivityColors.push('#2DCC70');

      this.nbTasks = countTasks;
      this.chartActivityAllData.push(countTasks + countTasks2);
      this.chartActivityAllLabels.push('Tasks');
      this.chartActivityColors.push('#E84C3D'); // C1392B
/*
      this.chartActivityAllData.push(countTasks2);
      this.chartActivityAllLabels.push('Pr Tasks');
      this.chartActivityColors.push('#C1392B');
*/
      this.nbWorkloads = countWorkload;
      this.chartActivityAllData.push(countWorkload + countWorkload2);
      this.chartActivityAllLabels.push('Workload');
      this.chartActivityColors.push('#E77E23'); // D25400
/*
      this.chartActivityAllData.push(countWorkload2);
      this.chartActivityAllLabels.push('Pr Workload');
      this.chartActivityColors.push('#D25400');
*/
      this.nbSchedule = countSchedule;
      this.chartActivityAllData.push(countSchedule + countSchedule2);
      this.chartActivityAllLabels.push('Schedule');
      this.chartActivityColors.push('#F1C40F'); // F39C11
/*
      this.chartActivityAllData.push(countSchedule2);
      this.chartActivityAllLabels.push('Pr Schedule');
      this.chartActivityColors.push('#F39C11');
*/
      this.nbTimesheet = countTimesheet;
      this.chartActivityAllData.push(countTimesheet + countTimesheet2);
      this.chartActivityAllLabels.push('Timesheet');
      this.chartActivityColors.push('#34495E'); // 2D3E50
/*
      this.chartActivityAllData.push(countTimesheet2);
      this.chartActivityAllLabels.push('Pr Timesheet');
      this.chartActivityColors.push('#2D3E50');
*/
      this.chartActivityAllData.push(countDiscussion + countDiscussion2);
      this.chartActivityAllLabels.push('Discussions');
      this.chartActivityColors.push('#1BBC9B');
/*
      this.chartActivityAllData.push(countDiscussion2);
      this.chartActivityAllLabels.push('Pr Discussions');
      this.chartActivityColors.push('#16A086');
*/
      this.chartActivityAllData.push(countCalendar);
      this.chartActivityAllLabels.push('Calendar');
      this.chartActivityColors.push('#DB00BE');

      this.chartActivityAllData.push(countReports);
      this.chartActivityAllLabels.push('Reports');
      this.chartActivityColors.push('#ECF0F1');
      this.chartActivityAllData.push(countExports);
      this.chartActivityAllLabels.push('Exports');
      this.chartActivityColors.push('#BEC3C7');

      this.chartActivityAllData.push(countMessage1 + countMessage2 + countMessage3);
      this.chartActivityAllLabels.push('Messages');
      this.chartActivityColors.push('#9B58B5');
      this.chartActivityAllData.push(countMessagePopup);
      this.chartActivityAllLabels.push('Messages popup');
      this.chartActivityColors.push('#8F44AD');

      this.createChartActivityAll();
      this.snackBar.open('Data loaded successfully', 'Close', {
        duration: 2000
      });
    },
    err => {
      this.log.e('Error while retrieving data: ', err);
      this.snackBar.open('Error while trying to load the data. They may need to be computed first.', 'Close', {
        duration: 2000
      });
    },
    () => {

      this.loading = false;
    }
  );
  }

  chartActivityAll: Chart = undefined;
  chartActivityAllLabels = [];
  chartActivityAllData = [];
  chartActivityColors = [];
  createChartActivityAll() {
    this.chartActivityAll = new Chart('canvasActivityAll', {
      type: 'doughnut',
      data: {
        labels: this.chartActivityAllLabels,
        datasets: [
          {
            data: this.chartActivityAllData,
            backgroundColor: this.chartActivityColors
          }
        ]
      },
      options: {
        legend: {
          display: true
        },
        cutoutPercentage: 70,
        responsive: true,
        maintainAspectRatio: false,
        pieceLabel: {
          render: 'percentage',
          fontColor: '#fff'
        }
      }
    });
  }

}
