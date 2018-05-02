import { Injectable } from '@angular/core';
import { Customer } from '../models/customer';
import { HttpClient } from '@angular/common/http';
import { ReportCommand } from '../technical/reporting/report-command';

import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'

@Injectable()
export class CustomerService {

  constructor(private http: HttpClient) { }
 
  getCustomers(reportCommand: ReportCommand): Observable<Customer[]> { 
      return this.http.post<Customer[]>('/api-data/searchCustomers', reportCommand);
  }

  getCustomer(id: number): Observable<Customer> {
    return this.http.get<Customer>('/api-data/customers/' + id);
  }

  formattedStatus(customer: Customer): string {
    switch(customer.status) {
      case 0: {
        return 'Inactive';
      }
      case 1: { 
        return "Open";
      } 
      case 2: { 
        return "Expired";
      } 
      case 3: { 
        return "Closed";
      } 
      default: { 
         return '?' + status;
      } 
    }
  }

  statusClass(customer: Customer): string {
    if (customer) {
      switch(customer.status) {
          case 0: {
            return 'status-inactive';
          }
          case 1: { 
            return "status-active";
          } 
          case 2: { 
            return "status-inactive";
          } 
          case 3: { 
            return "status-closed";
          } 
          default: { 
             return '';
          } 
        }
    }
  }
}
