import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CustomerService } from '../../shared/services/customer.service';
import { ActivatedRoute } from '@angular/router';
import { Customer } from '../../shared/models/customer';

@Component({
    selector: 'app-field',
    template: `
        <div class="field" [ngClass]="fieldClass">
            <div class="field-title" [ngStyle]="titleStyle()">{{ title }}</div><div class="field-value" [ngClass]="valueClass">{{ value }}</div>
        </div>
    `,
    styleUrls: ['./field.component.css']
})
export class FieldComponent {

    @Input() title: string;
    @Input() value: string;

    @Input() fieldClass: string = '';
    @Input() valueClass: string = '';

    @Input() titleWidth: number;

    constructor() { }

    titleStyle(): any {
        if (this.titleWidth) {
            return { 'min-width': this.titleWidth + 'px', 'max-width': this.titleWidth + 'px'};
        }
        return {};
    }

}
