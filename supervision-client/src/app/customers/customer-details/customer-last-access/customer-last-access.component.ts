import { Component, OnInit } from '@angular/core';
import { Logger } from '../../../shared/technical/logger';
import { LastAccess } from '../../../shared/models/last-access';
import { User } from '../../../shared/models/user';
import { UsageLogService } from '../../../shared/services/usage-log.service';
import { UserService } from '../../../shared/services/user.service';
import { ActivatedRoute } from '@angular/router';
import { MatDialog, MatSnackBar } from '@angular/material';
import { DatePipe } from '@angular/common';

import { filter } from 'rxjs/operators';
import { tap, map, flatMap } from 'rxjs/operators';
import { toArray } from 'rxjs/operators/toArray';
import { mergeAll } from 'rxjs/operator/mergeAll';

@Component({
  selector: 'app-customer-last-access',
  templateUrl: './customer-last-access.component.html',
  styleUrls: ['./customer-last-access.component.css']
})
export class CustomerLastAccessComponent implements OnInit {

  log = new Logger(Logger.DEBUG);

  id: number;

  columnsToDisplay = ['user', 'date'];
  columnsToDisplayNeverConnected = ['user'];

  userAccesses: LastAccess[] = [];

  lastRefreshDate: Date = undefined;

  neverConnectedUsers: User[] = []

  loading = false;

  // true if first call has been made to server,
  // to know if a computation has ever been done
  lastAccessesReady = false;

  // true if a computation of last accesses has ever been done
  // for this customer
  lastAccessesInitialized = false;

  constructor(
    private usageLogService: UsageLogService,
    private userService: UserService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      if (this.id) {
        this.getLastAccesses();
      }
    });
  }

  getLastAccesses() {
    this.usageLogService.getLastAccesses(this.id)
      .subscribe(logs => {
        this.userAccesses = logs;
        if (this.userAccesses.length > 0) {
          this.lastAccessesInitialized = true;
          this.lastRefreshDate = this.userAccesses[0].lastCalculation;
          this.getNeverConnectedUsers();
        }
        this.lastAccessesReady = true;
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

  datePipe = new DatePipe('en-US');
  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'dd/MM/y');
  }

  refreshLastAccesses() {
    this.loading = true;
    this.userAccesses = [];
    this.usageLogService.refreshLastAccesses(this.id)
      .subscribe(res => {
        this.lastAccessesInitialized = true;
        this.getLastAccesses();
        this.log.d('Result of refreshLastAccesses: ' + res);
      });
  }

  getUsers() {
    // get the users with blank usage logs
    // put 
  }

  numberOfUsers(): string {
    if (this.lastAccessesInitialized) {
      return '(Active: ' + this.userAccesses.length + ' / Other: ' + this.neverConnectedUsers.length + ')';
    }
    return '';
  }
}
