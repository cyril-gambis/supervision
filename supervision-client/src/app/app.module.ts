import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { UserListComponent } from './user-list/user-list.component';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { HomeComponent } from './home/home.component';
import { AuthenticationService } from './shared/technical/authentication.service';
import { UserService } from './shared/services/user.service';
import { ApiEndpointInterceptor } from './shared/technical/api-endpoint.interceptor';
import { JwtRequestInterceptor } from './shared/technical/jwt-request.interceptor';
import { TokenService } from './shared/technical/token.service';
import { AuthGuard } from './shared/guards/auth-guard';

import { MatButtonModule, MatCheckboxModule, MatIconModule, MatFormFieldModule, MatTableModule, MatProgressSpinnerModule, MatPaginatorModule, MatDialogModule, MatSnackBarModule } from '@angular/material';
import { MatInputModule, MatToolbarModule, MatDividerModule, MatSidenavModule, MatListModule } from '@angular/material';
import { ErrorRequestInterceptor } from './shared/technical/error-request.interceptor';
import { SideMenuComponent } from './side-menu/side-menu.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { UsageLogListComponent } from './usage-logs/usage-log-list/usage-log-list.component';
import { UsageLogService } from './shared/services/usage-log.service';
import { CustomerListComponent } from './customers/customer-list/customer-list.component';
import { CustomerService } from './shared/services/customer.service';
import { CustomersComponent } from './customers/customers.component';
import { CustomerDetailsComponent } from './customers/customer-details/customer-details.component';
import { HomeDashboardComponent } from './home-dashboard/home-dashboard.component';
import { FieldComponent } from './customers/customer-details/field.component';
import { CustomerListData } from './customers/customer-list/customer-list.data';
import { UsageLogsComponent } from './usage-logs/usage-logs.component';
import { UsageLogDetailsComponent } from './usage-logs/usage-log-details/usage-log-details.component';
import { LicenseService } from './shared/services/license.service';
import { AdminComponent } from './admin/admin.component';
import { AreYouSureComponent } from './dialog/are-you-sure/are-you-sure.component';
import { CustomerActivityComponent } from './customers/customer-details/customer-activity/customer-activity.component';
import { CustomerLastAccessComponent } from './customers/customer-details/customer-last-access/customer-last-access.component';
import { RecentQueryService } from './shared/technical/recent-query/recent-query.service';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    LoginComponent,
    NotFoundComponent,
    HomeComponent,
    SideMenuComponent,
    TopBarComponent,
    UsageLogListComponent,
    CustomerListComponent,
    CustomersComponent,
    CustomerDetailsComponent,
    HomeDashboardComponent,
    FieldComponent,
    UsageLogsComponent,
    UsageLogDetailsComponent,
    AdminComponent,
    AreYouSureComponent,
    CustomerActivityComponent,
    CustomerLastAccessComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule, MatCheckboxModule, MatIconModule,
    MatFormFieldModule, MatInputModule, MatToolbarModule,
    MatDividerModule, MatSidenavModule,
    MatTableModule, MatListModule, MatProgressSpinnerModule,
    MatPaginatorModule, MatDialogModule, MatSnackBarModule
  ],
  providers: [
    AuthGuard,
    AuthenticationService,
    UserService,
    TokenService,
    RecentQueryService,

    UsageLogService,
    CustomerService,
    LicenseService,

    CustomerListData,

    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiEndpointInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtRequestInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorRequestInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  entryComponents:  [AreYouSureComponent]
})
export class AppModule { }
