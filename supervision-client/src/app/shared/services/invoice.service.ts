import { Injectable } from '@angular/core';
import { Customer } from '../models/customer';
import { HttpClient } from '@angular/common/http';
import { ReportCommand } from '../technical/reporting/report-command';

import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { Invoice } from '../models/invoice';

@Injectable()
export class InvoiceService {

  constructor(private http: HttpClient) { }
 
  getInvoices(customerId: number): Observable<Invoice[]> {
    return this.http.get<Invoice[]>('/api-data/invoices/search/findByCustomerId?customerId=' + customerId)
      .map(res => res['_embedded']['invoices']);
  }

  formatAmount(invoice: Invoice): number {
    if (invoice.amount) {
      if (invoice.vatAmount) {
        return (Math.round((invoice.amount + invoice.vatAmount)*100)/100);
      }
      return invoice.amount;
    }
    return undefined;
  }

  formatStatus(invoice: Invoice): string {
    if (!invoice.paymentStatus) {
      let oldDateAsMillis = new Date().getTime() - this.FOURTY_FIVE_DAYS;
      if (invoice.invoiceDate && (oldDateAsMillis - new Date(invoice.invoiceDate).getTime()) > 0) {
        if (invoice.invoiceStatus === 1)
          return 'Late';
      }
      return this.formatInvoiceStatus(invoice.invoiceStatus);
    } else {
      if (invoice.paymentStatus === '5' || invoice.paymentStatus === '9') {
        return this.formatInvoiceStatus(invoice.invoiceStatus);
      } else if (invoice.paymentStatus === '2') {
          return "Payment refused";
      } else {
          return "Canceled";
      }
    }
  }

  formatInvoiceStatus(id: number): string {
    switch(id) {
      case 0: return 'Draft';
      case 1: return "Pending";
      case 2: return "Paid";
      case 3: return "Credit";
      case 4: return "Reimbursed";
      default: { 
         return '<No payment status>';
      } 
    }
  }

  formatPaymentStatus(id: number): string {
    switch(id) {
      case 0: return 'Ordered';
      case 1: return "Paid";
      case 2: return "Payment error";
      case 3: return "Disabled";
      default: { 
         return '<No payment status>';
      } 
    }
  }

  FOURTY_FIVE_DAYS = 60 * 60 * 1000 * 24 * 45;

  statusClass(invoice: Invoice): string {
    if (!invoice.paymentStatus) {
      let oldDateAsMillis = new Date().getTime() - this.FOURTY_FIVE_DAYS;
      if (invoice.invoiceDate && (oldDateAsMillis - new Date(invoice.invoiceDate).getTime()) > 0) {
          return 'status-closed'
      } else {
          return this.invoiceStatusClass(invoice.invoiceStatus);
      }
    } else {
      if (invoice.paymentStatus === '5' || invoice.paymentStatus === '9') {
        return this.invoiceStatusClass(invoice.invoiceStatus);
      } else if (invoice.paymentStatus === '2') {
          return "status-closed";
      } else {
          return "status-inactive";
      }
    }
  }
  invoiceStatusClass(id: number): string {
      switch(id) {
          case 0: return 'status-inactive';
          case 1: return "status-inactive";
          case 2: return "status-active";
          case 3: return "status-inactive";
          case 4: return "status-closed";
          default: { 
             return '';
          } 
        }
    }
}
