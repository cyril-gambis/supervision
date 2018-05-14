import { Component, OnInit } from '@angular/core';
import { RecentQueryService } from '../shared/technical/recent-query/recent-query.service';
import { RecentQuery } from '../shared/technical/recent-query/recent-query';
import { Logger } from '../shared/technical/logger';

@Component({
  selector: 'app-home-dashboard',
  templateUrl: './home-dashboard.component.html',
  styleUrls: ['./home-dashboard.component.css']
})
export class HomeDashboardComponent implements OnInit {

  queries: RecentQuery[] = [];

  log = new Logger(Logger.DEBUG);

  constructor(
    private recentQueryService: RecentQueryService
  ) { }

  ngOnInit() {
    this.queries = this.recentQueryService.getRecentQueries();
  }

}
