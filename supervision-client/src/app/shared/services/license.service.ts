import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
 
import { AuthenticationService } from './../technical/authentication.service';
import { User } from '../models/user';
import { License } from '../models/license';
 
@Injectable()
export class LicenseService {
    constructor(private http: HttpClient) { }
 
    getLicenseByCustomerId(customerId: number): Observable<License> {
        return this.http.get<License>('/api-data/licenses/search/findByCustomerId?customerId=' + customerId);
    }
}