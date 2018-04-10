import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpClient, HttpHandler, HttpEvent, HttpRequest } from "@angular/common/http";
import { Logger } from "../technical/logger";
import { environment } from "../../../environments/environment";

import { Observable } from "rxjs/Observable";

@Injectable()
export class ApiEndpointInterceptor implements HttpInterceptor {

    authorizationEndpoint: string;
    resourceEndpoint: string;

    log = new Logger(Logger.DEBUG);

    constructor(private http: HttpClient) {
        this.log.l('Constructor of ApiEndpointInterceptor Environment name: ' + environment['environmentName']);

        this.authorizationEndpoint = environment['authorizationEndpoint'];
        this.resourceEndpoint = environment['resourceEndpoint'];

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.log.i("Interceptor - The Request url: " + req.url);
        this.log.l("Interceptor - The Request method: " + req.method);

        let contentTypeHeaderValue = req.headers.get('Content-Type');
        if (!contentTypeHeaderValue) {
            if (req.method === 'GET') {
                contentTypeHeaderValue = 'application/x-www-form-urlencoded';
            } else {
                contentTypeHeaderValue = 'application/json';
            }
        }

        let acceptHeaderValue = req.headers.get('Accept');
        if (!acceptHeaderValue) {
            acceptHeaderValue = 'application/json';
        }

        let theUrl = req.url;
        // If url begins with /api, we replace with resourceEndpoint/authorizationEnpoint
        if (theUrl.indexOf("/api") === 0) {
            theUrl = theUrl.replace("/api", this.resourceEndpoint);
            this.log.l("Url replaced to point to api endpoint", theUrl);
        }

        const clonedRequest = req.clone({
            url: theUrl,
            headers: req.headers
                .set('Accept', acceptHeaderValue)
                .set('Content-Type', contentTypeHeaderValue)
        });

        this.log.l("old headers", req.headers.keys());
        this.log.l("new headers", clonedRequest.headers.keys());
        this.log.l('Initial request',req);
        this.log.l('New request', clonedRequest);
        return next.handle(clonedRequest);
    }

}