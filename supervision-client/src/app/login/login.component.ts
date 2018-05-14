import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
 
import { AuthenticationService } from '../shared/technical/authentication.service';
import { Logger } from '../shared/technical/logger';

@Component({
    templateUrl: 'login.component.html',
    styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

    log = new Logger(Logger.DEBUG);

    loginData = { username: 'admin', password: 'admin'};
    loading = false;
 
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService) { }
 
    ngOnInit() {
    }
 
    login() {

        this.log.d('Calling login()');

        this.authenticationService.clearError();

        this.authenticationService.login(this.loginData.username, this.loginData.password)
            .subscribe(user => {
                this.authenticationService.currentUser = user;
                // If we tried to access an unauthenticated url, we go back to this url
                if (this.authenticationService.redirectUrl) {
                    let myUrl = this.authenticationService.redirectUrl;
                    this.authenticationService.redirectUrl = undefined;
                    this.router.navigate([myUrl]);
                } else {
                    this.router.navigate(['/supervision']);
                }
            });
    }

    get error(): string {
        return this.authenticationService.error;
    }
}