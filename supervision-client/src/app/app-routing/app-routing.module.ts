import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { UserListComponent } from '../user-list/user-list.component';
import { LoginComponent } from '../login/login.component';
import { NotFoundComponent } from '../not-found/not-found.component';
import { HomeComponent } from '../home/home.component';
import { AuthGuard } from '../shared/guards/auth-guard';
import { UsageLogListComponent } from '../usage-log-list/usage-log-list.component';

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
      { path: '', redirectTo: 'user-list', pathMatch: 'full' },
      { path: 'user-list', component: UserListComponent },
      { path: 'usage-log-list', component: UsageLogListComponent }
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
