import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';

import { Customer } from './../../shared/models/customer';
import { CustomerService } from '../../shared/services/customer.service';
import { ReportCommand } from '../../shared/technical/reporting/report-command';
import { SortDirection } from '../../shared/technical/reporting/sort-direction';
import { LongSearchCriteria } from '../../shared/technical/reporting/long-search-criteria';
import { ReportOperator } from '../../shared/technical/reporting/report-operator';
import { CustomersDataSource } from './customers-data-source';
import { MatPaginator } from '@angular/material';
import { tap } from 'rxjs/operators';
import { StringSearchCriteria } from '../../shared/technical/reporting/string-search-criteria';
import { Logger } from '../../shared/technical/logger';
import { CustomerListData } from './customer-list.data';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements AfterViewInit, OnInit {

  log = new Logger(Logger.DEBUG);

  columnsToDisplay = ['id', 'name', 'site', 'signupDate', 'status', 'backOfficeComments'];

  customerId = 1;

  nbCustomers = 200;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  searchInput = "";

  constructor(
    private customerService: CustomerService,
    private customersData: CustomerListData
  ) { }

  ngOnInit() {
    this.log.d('ngOnInit de CustomersList');

    this.customersData.init(this.getSearchCommand());
  }

  ngAfterViewInit() {
    this.log.d('ngAfterViewInit de CustomersList');

    this.paginator.page
      .pipe(
        tap(() => this.loadCustomersPage())
      )
      .subscribe();
  }

  resetSearch() {
    this.paginator.pageIndex = 0;
    this.loadCustomersPage();
  }

  loadCustomersPage() {
    this.customersData.dataSource.loadCustomers(this.getSearchCommand());
  }

  getSearchCommand(currentFirstItemIndex?: number, nbRows?: number): ReportCommand {
    let cmd = new ReportCommand();

    if (this.paginator.pageIndex) {
      cmd.page = this.paginator.pageIndex;
    } else {
      cmd.page = 0;
    }

    if (this.paginator.pageSize) {
      cmd.nbItemsPerPage = this.paginator.pageSize;
    } else {
      cmd.nbItemsPerPage = 20;
    }

    cmd.sortFieldName = "id";
    cmd.sortDirection = SortDirection.DESC;

    cmd.criterias = [];

    let notDeletedCriteria = new LongSearchCriteria("deleted", ReportOperator.NEQ, 1);
    cmd.criterias.push(notDeletedCriteria);

    if (this.searchInput) {
      cmd.criterias.push(new StringSearchCriteria("name", ReportOperator.LIKE, this.searchInput));
    }

    return cmd;
  }

  get dataSource(): CustomersDataSource {
    return this.customersData.dataSource;
  }

  formattedStatus(customer: Customer): string {
    return this.customerService.formattedStatus(customer);
  }

  statusClass(customer: Customer): string {
    return this.customerService.statusClass(customer);
  }

  customerStyleFromStatus(customer: any): string {
    if (customer) {
      switch(customer.status) {
          case 0: {
            return 'row-inactive';
          }
          case 1: { 
            return "row-active";
          } 
          case 2: { 
            return "row-expired";
          } 
          case 3: { 
            return "row-closed";
          } 
          default: { 
             return '';
          } 
        }
    }
  }

}
