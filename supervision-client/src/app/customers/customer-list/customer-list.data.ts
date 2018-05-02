import { Injectable } from "@angular/core";
import { CustomersDataSource } from "./customers-data-source";
import { CustomerService } from "../../shared/services/customer.service";
import { ReportCommand } from "../../shared/technical/reporting/report-command";

@Injectable()
export class CustomerListData {

    public dataSource: CustomersDataSource;

    initialized = false;

    constructor(private customerService: CustomerService) {
        this.dataSource = new CustomersDataSource(this.customerService);
    }

    init(command: ReportCommand) {
        if (!this.initialized) {
            this.dataSource.loadCustomers(command);
            this.initialized = true;
        }
    }
}