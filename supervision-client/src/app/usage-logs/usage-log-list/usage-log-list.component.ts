import { Component, OnInit } from '@angular/core';
import { UsageLog } from '../../shared/models/usage-log';
import { UsageLogService } from '../../shared/services/usage-log.service';
import { ReportCommand } from '../../shared/technical/reporting/report-command';
import { ReportEntity } from '../../shared/technical/reporting/report-entity';
import { SortDirection } from '../../shared/technical/reporting/sort-direction';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-usage-log-list',
  templateUrl: './usage-log-list.component.html',
  styleUrls: ['./usage-log-list.component.css']
})
export class UsageLogListComponent implements OnInit {

  usageLogs: UsageLog[] = [];

  columnsToDisplay = ['date', 'user', 'category', 'entity', 'actionType', 'targetId'];

  customerId = 1;

  constructor(
    private usageLogService: UsageLogService,
    private activatedRoute: ActivatedRoute
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      let id = params['id'];
      if (id) {
        this.customerId = id;
        this.getLastUsageLogs();
      }
    });
  }

  ngOnInit() {
  }

  loadUsageLogs() {
    this.usageLogService.getUsageLogs(this.getSearchCommand())
      .subscribe(data => this.usageLogs = data);
  }

  getSearchCommand(currentFirstItemIndex?: number, nbRows?: number): ReportCommand {
    let cmd = new ReportCommand();
    cmd.page = 0;
    cmd.nbItemsPerPage = 20;
    cmd.sortFieldName = "date";
    cmd.sortDirection = SortDirection.DESC;

    cmd.criterias = [];

    return cmd;
  }

  test(o: Object): string {
    console.log(o);
    return typeof o;
  }

  getLastUsageLogs() {
    this.usageLogService.getLastUsageLogs(this.customerId)
      .subscribe(data => this.usageLogs = data);
  }
}
