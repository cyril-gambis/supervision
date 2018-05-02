import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsageLogListComponent } from './usage-log-list.component';

describe('UsageLogListComponent', () => {
  let component: UsageLogListComponent;
  let fixture: ComponentFixture<UsageLogListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsageLogListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsageLogListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
