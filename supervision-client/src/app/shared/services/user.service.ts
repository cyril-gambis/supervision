import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
 
import { AuthenticationService } from './authentication.service';
import { User } from '../models/user';
 
@Injectable()
export class UserService {
    constructor(
        private http: HttpClient,
        private authenticationService: AuthenticationService) {
    }
 
    getUsers(): Observable<string> {
        // add authorization header with jwt token
        let headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
            .set('Authorization', 'Bearer ' + this.authenticationService.token);
 
        // get users from api
        return this.http.get<string>(
                'http://localhost:8091/api/v1.0/users-custom/all',
                { headers });
    }
}