import { Component, OnInit, Input, Inject } from '@angular/core';
import { Invoice } from '../../../shared/models/invoice';
import { DatePipe } from '@angular/common';
import { MAT_DIALOG_DATA } from '@angular/material';
import { InvoiceService } from '../../../shared/services/invoice.service';

@Component({
  selector: 'app-customer-invoice-list',
  templateUrl: './customer-invoice-list.component.html',
  styleUrls: ['./customer-invoice-list.component.css']
})
export class CustomerInvoiceListComponent implements OnInit {

  columnsToDisplay = ['invoiceNumber', 'title', 'invoiceDate', 'amount', 'invoiceStatus'];

  invoices : Invoice[];

  constructor(
    private invoiceService: InvoiceService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.invoices = data.invoices;
  }

  ngOnInit() {
  }

  datePipe = new DatePipe('en-US');
  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'dd/MM/y');
  }

  statusClass(invoice: Invoice): string {
    return this.invoiceService.statusClass(invoice);
  }
  
  formatStatus(invoice: Invoice): string {
    return this.invoiceService.formatStatus(invoice);
  }

  formatAmount(invoice: Invoice): number {
    return this.invoiceService.formatAmount(invoice);
  }

}
