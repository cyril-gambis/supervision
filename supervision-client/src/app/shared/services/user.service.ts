import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
 
import { AuthenticationService } from './../technical/authentication.service';
import { User } from '../models/user';
 
@Injectable()
export class UserService {
    constructor(
        private http: HttpClient,
        private authenticationService: AuthenticationService) {
    }
 
    getUsers(): Observable<string> { 
        // get users from api
        return this.http.get<string>('/api/users/all');
    }
}