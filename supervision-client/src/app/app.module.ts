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

import { MatButtonModule, MatCheckboxModule, MatIconModule, MatFormFieldModule, MatTableModule } from '@angular/material';
import { MatInputModule, MatToolbarModule, MatDividerModule, MatSidenavModule } from '@angular/material';
import { ErrorRequestInterceptor } from './shared/technical/error-request.interceptor';
import { SideMenuComponent } from './side-menu/side-menu.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { UsageLogListComponent } from './usage-log-list/usage-log-list.component';
import { UsageLogService } from './shared/services/usage-log.service';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    LoginComponent,
    NotFoundComponent,
    HomeComponent,
    SideMenuComponent,
    TopBarComponent,
    UsageLogListComponent
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
    MatTableModule
  ],
  providers: [
    AuthGuard,
    AuthenticationService,
    UserService,
    TokenService,

    UsageLogService,

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
  bootstrap: [AppComponent]
})
export class AppModule { }
