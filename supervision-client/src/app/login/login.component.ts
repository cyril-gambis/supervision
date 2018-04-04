import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
 
import { AuthenticationService } from '../shared/services/authentication.service';

@Component({
    templateUrl: 'login.component.html'
})
 
export class LoginComponent implements OnInit {
    loginData = { username: 'admin', password: 'admin'};
    loading = false;
    error = '';
 
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService) { }
 
    ngOnInit() {
        // reset login status
        // this.authenticationService.logout();
    }
 
    login() {
        this.authenticationService.obtainAccessToken(this.loginData.username, this.loginData.password);
    }
}