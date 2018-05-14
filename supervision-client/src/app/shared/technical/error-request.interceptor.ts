import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpClient, HttpHandler, HttpEvent, HttpResponse, HttpRequest, HttpErrorResponse } from "@angular/common/http";
import { Logger } from "../technical/logger";
import { environment } from "../../../environments/environment.prod";

import { Observable } from "rxjs/Observable";
import { AuthenticationService } from "./authentication.service";

import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class ErrorRequestInterceptor implements HttpInterceptor {

    log = new Logger(Logger.DEBUG);

    constructor(private authenticationService: AuthenticationService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req)
            .do((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    // We don't do anything when there is no problem
                }
            },
            (err: any) => {
                if (err instanceof HttpErrorResponse) {
                    if (err.status === 401) {
                        if (err.error.error && (err.error.error === 'invalid_token'))
                        console.log('Invalid token, try to refresh token if possible, but for now we logout');
                        this.authenticationService.logout();
                    } else if (err.status === 0) {
                        console.log('Unknown error - Server probably not running');
                        this.authenticationService.loginPage('Unknown error - Server probably not running');
                    } else if (err.status === 400) {
                        this.authenticationService.loginPage('Bad credentials');
                    }
                }
    //            console.log("ERROR!");
    //            console.log(err);
                return Observable.throw(err);
            });
    }

}

/*

Another way

intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpSentEvent | HttpHeaderResponse | HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>> {
        return next.handle(this.addToken(req, this.authService.getAuthToken()))
            .catch(error => {
                if (error instanceof HttpErrorResponse) {
                    switch ((<HttpErrorResponse>error).status) {
                        case 400:
                            return this.handle400Error(error);
                        case 401:
                            return this.handle401Error(req, next);
                    }
                } else {
                    return Observable.throw(error);
                }
            });
    }
*/