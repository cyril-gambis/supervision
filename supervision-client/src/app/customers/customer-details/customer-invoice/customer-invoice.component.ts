import { Component, OnInit } from '@angular/core';
import { Logger } from '../../../shared/technical/logger';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { Invoice } from '../../../shared/models/invoice';
import { InvoiceService } from '../../../shared/services/invoice.service';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { CustomerInvoiceListComponent } from '../customer-invoice-list/customer-invoice-list.component';

@Component({
  selector: 'app-customer-invoice',
  templateUrl: './customer-invoice.component.html',
  styleUrls: ['./customer-invoice.component.css']
})
export class CustomerInvoiceComponent implements OnInit {

  id: number;

  log = new Logger(Logger.DEBUG);

  invoices: Invoice[] = [];
  totalAmount: number = undefined;

  loading = false;

  constructor(
    private invoiceService: InvoiceService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      if (this.id) {
        this.getInvoices(this.id);
      }
    });
  }

  getInvoices(customerId: number) {
    this.invoiceService.getInvoices(customerId)
      .subscribe(invoices => {
        this.invoices = invoices;
        this.totalAmount = this.calculateTotalAmount();
        this.loading = false;
      });
  }

  datePipe = new DatePipe('en-US');
  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'dd/MM/y');
  }

  get lastInvoice(): Invoice {
    if (this.invoices.length > 0) {
      return this.invoices[0];
    }
    return undefined;
  }

  displayAll() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '1100px';
    dialogConfig.panelClass = 'invoice-list-panel';

    dialogConfig.data = {
      invoices: this.invoices
    }

    let dialogRef = this.dialog.open(CustomerInvoiceListComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(value => {  
    });
  }

  calculateTotalAmount(): number {
    this.log.d('Calculate total amount');
    let result = this.invoices.map(item => (item.invoiceStatus === 2 ? item.amount + item.vatAmount : 0)).reduce((prev, next) => prev + next);
    this.log.d('Result is ', result);
    return result;
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

  formatRawAmount(value: number): number {
    return (Math.round(value*100)/100);
  }

}
