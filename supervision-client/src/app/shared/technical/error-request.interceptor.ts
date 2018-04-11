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
                    }
                }
//                return Observable.throw(err);
            });
    }

}