import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsageLogDetailsComponent } from './usage-log-details.component';

describe('UsageLogDetailsComponent', () => {
  let component: UsageLogDetailsComponent;
  let fixture: ComponentFixture<UsageLogDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsageLogDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsageLogDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
