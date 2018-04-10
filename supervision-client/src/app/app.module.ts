import { BrowserModule } from '@angular/platform-browser';
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

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    LoginComponent,
    NotFoundComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    AuthGuard,
    AuthenticationService,
    UserService,
    TokenService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiEndpointInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtRequestInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
