import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs/Observable";

import 'rxjs/add/operator/map';
import { Router } from "@angular/router";
//import { map } from 'rxjs/operators/map';

@Injectable()
export class AuthenticationService {

    public token: string;

    TOKEN_AUTH_USERNAME = 'jwtclientid';
    TOKEN_AUTH_PASSWORD = 'XY7kmzoNzl100';
    TOKEN_NAME = 'access_token';

    AUTH_TOKEN = '/oauth/token';

    httpHeadersJson = new HttpHeaders().set('Content-Type', 'application/json').set('Accept', 'application/json');

    constructor(private router: Router, private http: HttpClient) {
        // set token if saved in local storage
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        this.token = currentUser && currentUser.token;
    }

    obtainAccessToken(username: string, password: string) {
        let params = new URLSearchParams();
        params.append('username', username);
        params.append('password', password);
        params.append('grant_type', 'password');
        params.append('client_id', 'clientIdPassword');

        // The client id for the password grant type
        let clientId = 'clientIdPassword';
        let clientPassword = this.TOKEN_AUTH_PASSWORD;

        let headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8')
            .set('Authorization', 'Basic ' + btoa(clientId + ':' + clientPassword));

        this.http.post('http://localhost:8091/api/v1.0/oauth/token',
            params.toString(),
            { headers }
        ).subscribe(
            data => this.saveToken(data),
            err => alert('Invalid Credentials')
        );
    }

    saveToken(token) {
        var expireDate = new Date().getTime() + (1000 * token.expires_in);
        this.token = token.access_token;
        this.router.navigate(['/home']);
    }

    getResource(resourceUrl): Observable<any> {
        let headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8')
            .set('Authorization', 'Bearer ' + this.token);

        return this.http.get(resourceUrl,
            { headers }
        );
    }

    checkCredentials() {
        if (!this.token) {
            this.router.navigate(['/login']);
        }
    }

    logout() {
        this.token = undefined;
        this.router.navigate(['/login']);
    }

/*


    login(username: string, password: string): Observable<any> {
        const body = `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}&grant_type=password`;

        let headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
            .set('Authorization', 'Basic ' + btoa(this.TOKEN_AUTH_USERNAME + ':' + this.TOKEN_AUTH_PASSWORD));

        return this.http.post(
            "http://localhost:8091/api/v1.0/oauth/authorize",
            body,
            { headers }
        )
        .map((response) => {
            console.log(response);
            let token = response['token'];
            // login successful if there's a jwt token in the response
            if (token) {
                this.token = token;
                // Store username and jwt token in local storage to keep user
                // logged in between page refreshes
                localStorage.setItem('currentUser', JSON.stringify({username: username, token: token}));
                return true;
            } else {
                return false;
            }
        });
    }

    logout(): void {
        // clear token and remove user from local storage to log user out
        this.token = undefined;
        localStorage.removeItem('currentUser');
    }
    */
}