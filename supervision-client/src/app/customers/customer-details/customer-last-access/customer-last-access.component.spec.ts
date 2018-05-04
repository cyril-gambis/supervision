import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerLastAccessComponent } from './customer-last-access.component';

describe('CustomerLastAccessComponent', () => {
  let component: CustomerLastAccessComponent;
  let fixture: ComponentFixture<CustomerLastAccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerLastAccessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerLastAccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
