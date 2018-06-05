import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs/Observable";

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import { Router } from "@angular/router";
import { TokenService } from "./token.service";
import { User } from "../models/user";
import { Logger } from "./logger";
//import { map } from 'rxjs/operators/map';

@Injectable()
export class AuthenticationService {

    log = new Logger(Logger.DEBUG);

    currentUser: User = undefined;
    redirectUrl: string = undefined;

    error = '';

    TOKEN_AUTH_USERNAME = 'jwtclientid';
    TOKEN_AUTH_PASSWORD = 'XY7kmzoNzl100';
    TOKEN_NAME = 'access_token';

    AUTH_TOKEN = '/oauth/token';

    CURRENT_SUPERVISOR = '/api/supervisors-custom/currentSupervisor';

    httpHeadersJson = new HttpHeaders().set('Content-Type', 'application/json').set('Accept', 'application/json');

    constructor(private router: Router, private http: HttpClient, private tokenService: TokenService) {
    }


    /*
        To obtain a new token with a refresh token, send a request
        to the /oauth/token endpoint, with parameter grant_type=refresh_token
        and the refresh token instead of username and password.
        Client id and client secret must also be added.
        (Client secret is not really relevant for a javascript SPA)
    */
    login(username: string, password: string): Observable<User> {
        let params = new HttpParams();
        params.append('username', username);
        params.append('password', password);
        params.append('grant_type', 'password');
        params.append('client_id', 'clientIdPassword');

        // The client id for the password grant type
        let clientId = 'clientIdPassword';
        let clientPassword = this.TOKEN_AUTH_PASSWORD;

        let headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8')
            .set('Authorization', 'Basic ' + btoa(clientId + ':' + clientPassword));

        return this.http.post('/api' + this.AUTH_TOKEN,
            params.toString(),
            { headers }
        ).mergeMap(
            data => {
                this.tokenService.saveToken(data[this.TOKEN_NAME]);
                return this.http.get<User>(this.CURRENT_SUPERVISOR);
            }
        );
    }

    loginWithToken(token: string): Observable<User> {
        return this.http.get<User>(this.CURRENT_SUPERVISOR);
    }

    logout() {
        this.log.d('Calling logout() of AuthenticationService');
        this.currentUser = undefined;
        this.tokenService.removeToken();
        this.router.navigate(['/login']);
    }

    loginPage(msg: string) {
        this.error = msg;
        this.router.navigate(['/login']);
    }
    clearError(): void {
        this.error = '';
    }
}