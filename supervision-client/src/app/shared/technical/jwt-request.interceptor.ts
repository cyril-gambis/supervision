import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpClient, HttpHandler, HttpEvent, HttpResponse, HttpRequest, HttpErrorResponse } from "@angular/common/http";
import { Logger } from "../technical/logger";
import { environment } from "../../../environments/environment.prod";

import { Observable } from "rxjs/Observable";
import { TokenService } from "./token.service";

import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class JwtRequestInterceptor implements HttpInterceptor {

    authorizationEndpoint: string;
    resourceEndpoint: string;

    log = new Logger(Logger.DEBUG);

    constructor(private tokenService: TokenService) {
        this.resourceEndpoint = environment['resourceEndpoint'];
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let headers = req.headers;
        if (!req.headers.get('Authorization')) {
            headers = headers
                .set('Authorization', 'Bearer ' + this.tokenService.getToken());
        }

        const clonedRequest = req.clone({
            url: req.url,
            headers: headers
        });

        this.log.l("old headers", req.headers.keys());
        this.log.l("new headers", clonedRequest.headers.keys());
        return next.handle(clonedRequest);
    }

}