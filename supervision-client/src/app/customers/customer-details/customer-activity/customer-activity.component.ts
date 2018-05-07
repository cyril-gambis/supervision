import { Component, OnInit } from '@angular/core';

import { Chart } from 'chart.js';
import 'chart.piecelabel.js';
import { Logger } from '../../../shared/technical/logger';
import { CountByMonth } from '../../../shared/models/count-by-month';
import { ActivatedRoute } from '@angular/router';
import { MatDialog, MatSnackBar, MatDialogConfig } from '@angular/material';
import { UsageLogService } from '../../../shared/services/usage-log.service';
import { AreYouSureComponent } from '../../../dialog/are-you-sure/are-you-sure.component';
import { forkJoin } from 'rxjs/observable/forkJoin';

import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';

@Component({
  selector: 'app-customer-activity',
  templateUrl: './customer-activity.component.html',
  styleUrls: ['./customer-activity.component.css']
})
export class CustomerActivityComponent implements OnInit {

  log = new Logger(Logger.DEBUG);

  id: number = undefined;

  loading: boolean = false;

  chartActivityAll: Chart = undefined;
  chartActivityAllLabels = [];
  chartActivityAllData = [];
  chartActivityColors = [];

  lastRefreshAllDate: Date = undefined;
  countByMonth : CountByMonth[] = [];

  chart: Chart = undefined;

  chartDataDateLabels = [];
  chartDataCounts = [];
  chartDataDates = [];

  PZ_OVERVIEW = 2;
  PZ_PROJECTS = 3;
  PZ_TASKS = 4;
  PZ_WORKLOAD = 5;
  PZ_SCHEDULE = 6;
  PZ_TIMESHEET = 7;
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

  constructor(
    private usageLogService: UsageLogService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      if (this.id) {
        this.usageLogService.getActivityLastRefreshDate(this.id)
          .subscribe(date => {
            if (date) {
              this.lastRefreshAllDate = date;
              this.getCountByMonth();
              this.displayActivityAllChart()
            }            
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
        this.usageLogService.refreshAllAccessesAllPages(this.id)
          .subscribe(res => {
            this.loading = false;
            this.snackBar.open(res, 'Close', {
              duration: 4000
            });
            this.usageLogService.getActivityLastRefreshDate(this.id)
              .subscribe(date => {
                if (date) {
                  this.lastRefreshAllDate = date;
                  this.getCountByMonth();
                  this.displayActivityAllChart()
                }            
              });
          });
        }
      });
  }


  getCountByMonth() {
    this.usageLogService.getCountByMonth(this.id)
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

        if (!this.chart) {
          this.createChart();
        }
      });
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


  nbOverview = 0;
  nbProjects = 0;
  nbTasks = 0;
  nbWorkloads = 0;
  nbSchedule = 0;
  nbTimesheet = 0;

  progress = 0;
  total = 20;
  displayActivityAllChart() {
    this.chartActivityAllLabels = [];
    this.chartActivityAllData = [];
    this.chartActivityColors = [];

    this.loading = true;
    this.progress = 0;

    this.usageLogService.getCountByPageId(this.id, this.PZ_OVERVIEW)
      .flatMap(countOverview => {
        this.nbOverview = countOverview;
        this.progress++;
        this.chartActivityAllData.push(countOverview);
        this.chartActivityAllLabels.push('Overview PZ');
        this.chartActivityColors.push('#3598DB'); //2A80B9
        return this.usageLogService.getCountByPageId(this.id, this.PZ_PROJECTS);
      })
      .flatMap(countOverview2 => {
        this.progress++;
        this.chartActivityAllData.push(countOverview2);
        this.chartActivityAllLabels.push('Overview PR');
        this.chartActivityColors.push('#2A80B9');
        return this.count(this.CALENDAR_PAGE_ID);
      })
      .flatMap(countProjects => {
        this.nbProjects = countProjects;
        this.progress++;
        this.chartActivityAllData.push(countProjects);
        this.chartActivityAllLabels.push('Pz Projects');
        this.chartActivityColors.push('#2DCC70');
        return this.forkJoinCount(this.PZ_TASKS, this.PR_TASKS_PAGE_ID);
      })
      .flatMap(([countTasks, countTasks2]) => {
        this.nbTasks = countTasks;
        this.progress += 2;
        this.chartActivityAllData.push(countTasks + countTasks2);
        this.chartActivityAllLabels.push('Tasks');
        this.chartActivityColors.push('#E84C3D'); // C1392B
        return this.forkJoinCount(this.PZ_WORKLOAD, this.PR_WORKLOAD_PAGE_ID);
      })
      .flatMap(([countWorkload, countWorkload2]) => {
        this.nbWorkloads = countWorkload;
        this.progress += 2;
        this.chartActivityAllData.push(countWorkload + countWorkload2);
        this.chartActivityAllLabels.push('Workload');
        this.chartActivityColors.push('#E77E23'); // D25400
        return this.forkJoinCount(this.PZ_SCHEDULE, this.PR_SCHEDULE_PAGE_ID)
      })
      .flatMap(([countSchedule, countSchedule2]) => {
        this.nbSchedule = countSchedule;
        this.progress += 2;
        this.chartActivityAllData.push(countSchedule + countSchedule2);
        this.chartActivityAllLabels.push('Schedule');
        this.chartActivityColors.push('#F1C40F'); // F39C11
        return this.forkJoinCount(this.PZ_TIMESHEET, this.PR_TIMESHEET_PAGE_ID);
      })
      .flatMap(([countTimesheet, countTimesheet2]) => {
        this.nbTimesheet = countTimesheet;
        this.progress += 2;
        this.chartActivityAllData.push(countTimesheet + countTimesheet2);
        this.chartActivityAllLabels.push('Timesheet');
        this.chartActivityColors.push('#34495E'); // 2D3E50
        return this.forkJoinCount(this.PZ_DISCUSSION_ID, this.PR_DISCUSSION_ID);
      })
      .flatMap(([countDiscussion, countDiscussion2]) => {
        this.progress += 2;
        this.chartActivityAllData.push(countDiscussion + countDiscussion2);
        this.chartActivityAllLabels.push('Discussions');
        this.chartActivityColors.push('#1BBC9B');
        return this.usageLogService.getCountByPageId(this.id, this.PR_OVERVIEW_PAGE_ID)
      })
      .flatMap(countCalendar => {
        this.progress++;
        this.chartActivityAllData.push(countCalendar);
        this.chartActivityAllLabels.push('Calendar');
        this.chartActivityColors.push('#DB00BE');
        return this.count(this.REPORTS_ID);
      })
      .flatMap(countReports => {
        this.progress++;
        this.chartActivityAllData.push(countReports);
        this.chartActivityAllLabels.push('Reports');
        this.chartActivityColors.push('#ECF0F1');
        return this.count(this.EXPORTS_ID);
      })
      .flatMap(countExports => {
        this.progress++;
        this.chartActivityAllData.push(countExports);
        this.chartActivityAllLabels.push('Exports');
        this.chartActivityColors.push('#BEC3C7');
        return forkJoin(
          this.count(this.MESSAGE_ON_TASK_ID),
          this.count(this.MESSAGE_ON_MILESTONE_ID),
          this.count(this.MESSAGE_ON_EVENT_ID)
        )
      })
      .flatMap(([countMessage1, countMessage2, countMessage3]) => {
        this.progress += 3;
        this.chartActivityAllData.push(countMessage1 + countMessage2 + countMessage3);
        this.chartActivityAllLabels.push('Messages');
        this.chartActivityColors.push('#9B58B5');
        return this.count(this.MESSAGE_POPUP_ID);
      })
      .subscribe(countMessagePopup => {
        this.chartActivityAllData.push(countMessagePopup);
        this.chartActivityAllLabels.push('Messages popup');
        this.chartActivityColors.push('#8F44AD');

        if (!this.chartActivityAll) {
          this.createChartActivityAll();
        }
        this.snackBar.open('Data loaded successfully', 'Close', {
          duration: 4000
        });  
      },
      err => {
        this.log.e('Error while retrieving data: ', err);
        this.snackBar.open('Error while trying to load the data. They may need to be computed first.', 'Close', {
          duration: 4000
        });
      },
      () => {
        this.loading = false;
      }  
    );
  }

  count(id: number): Observable<number> {
    return this.usageLogService.getCountByPageId(this.id, id);
  }

  forkJoinCount(id1: number, id2: number): Observable<number[]> {
    return forkJoin(
      this.usageLogService.getCountByPageId(this.id, id1),
      this.usageLogService.getCountByPageId(this.id, id2)
    );  
  }


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
