import { DataSource } from "@angular/cdk/table";
import { BehaviorSubject } from "rxjs";
import { Customer } from "../../shared/models/customer";
import { CustomerService } from "./../../shared/services/customer.service";
import { CollectionViewer } from "@angular/cdk/collections";

import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { ReportCommand } from "../../shared/technical/reporting/report-command";
import { catchError, finalize } from "rxjs/operators";
import {of} from "rxjs/observable/of";

export class CustomersDataSource implements DataSource<Customer> {

    private customersSubject = new BehaviorSubject<Customer[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private customerService: CustomerService) {}

    connect(collectionViewer: CollectionViewer): Observable<Customer[]> {
        return this.customersSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.customersSubject.complete();
        this.loadingSubject.complete();
    }

    loadCustomers(command: ReportCommand) {

        this.loadingSubject.next(true);

        this.customerService.getCustomers(command).pipe(
            catchError(() => of([])),
            finalize(() => this.loadingSubject.next(false))
        )
        .subscribe(customers => this.customersSubject.next(customers));
    }
}