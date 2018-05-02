import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { UserListComponent } from '../user-list/user-list.component';
import { LoginComponent } from '../login/login.component';
import { NotFoundComponent } from '../not-found/not-found.component';
import { HomeComponent } from '../home/home.component';
import { AuthGuard } from '../shared/guards/auth-guard';
import { CustomerListComponent } from '../customers/customer-list/customer-list.component';
import { CustomersComponent } from '../customers/customers.component';
import { CustomerDetailsComponent } from '../customers/customer-details/customer-details.component';
import { HomeDashboardComponent } from '../home-dashboard/home-dashboard.component';
import { UsageLogsComponent } from '../usage-logs/usage-logs.component';
import { UsageLogDetailsComponent } from '../usage-logs/usage-log-details/usage-log-details.component';
import { UsageLogListComponent } from '../usage-logs/usage-log-list/usage-log-list.component';
import { AdminComponent } from '../admin/admin.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'supervision',
    pathMatch: 'full'
  },
  {
    path: 'supervision',
    component: HomeComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'home-dashboard', pathMatch: 'full' },
      { path: 'home-dashboard', component: HomeDashboardComponent },
      { path: 'user-list', component: UserListComponent },
      {
        path: 'customers',
        component: CustomersComponent,
        children: [
          { path: '', redirectTo: 'customer-list', pathMatch: 'full' },
          { path: 'customer-list', component: CustomerListComponent },
          { path: 'customer-details/:id', component: CustomerDetailsComponent }
        ]
      },
      {
        path: 'usage-logs',
        component: UsageLogsComponent,
        children: [
          { path: '', redirectTo: 'usage-log-list', pathMatch: 'full' },
          { path: 'usage-log-list', component: UsageLogListComponent },
          { path: 'overview/:id', component: UsageLogDetailsComponent }
        ]
      },
      {
        path: 'admin',
        component: AdminComponent
      }
    ]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  { path: '**', component: NotFoundComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
