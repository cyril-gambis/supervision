import { Injectable } from "@angular/core";
import { Router, CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot } from "@angular/router";
import { AuthenticationService } from "../technical/authentication.service";
import { Logger } from "../technical/logger";
import { TokenService } from "../technical/token.service";

import { Observable } from "rxjs/Observable";
import { of } from 'rxjs/observable/of';
import 'rxjs/add/operator/mergeMap';

@Injectable()
export class AuthGuard implements CanActivate {

    log = new Logger(Logger.DEBUG);

    constructor (
        private authenticationService: AuthenticationService,
        private tokenService: TokenService,
        private router:Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {

        this.log.d('AuthGuard called');

        if (this.authenticationService.currentUser) {
            // logged in, so return true
            return of(true);
        }

        // if not logged in, look for a token in localStorage for login
        let token = this.tokenService.getToken();
        if (token) {
            this.log.d('No current user but token in localStorage');
            return this.authenticationService.loginWithToken(token)
                .map(user => {
                    this.authenticationService.currentUser = user;
                    return true;
                },
                error => {
                    return false;
                });
        }

        // not logged in, so redirect to login page
        this.log.d('AuthGuard redirects to login page');
        this.authenticationService.redirectUrl = state.url;
        this.router.navigate(['/login']);
        return of(false);
    }
}