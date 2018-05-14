import { Injectable } from "@angular/core";
import { RecentQuery } from "./recent-query";
import { Logger } from "../logger";

@Injectable()
export class RecentQueryService {

    log = new Logger(Logger.DEBUG);

    QUERIES_KEY = 'queries';

    getRecentQueries(): RecentQuery[] {
        let queries = JSON.parse(localStorage.getItem(this.QUERIES_KEY)) || [];
        return queries;
    }

    addRecentQuery(query: RecentQuery) {
        let recentQueries: RecentQuery[] = this.getRecentQueries();
        this.log.l('RecentQueries before:', recentQueries);

        let index = recentQueries.findIndex(q => +q.id === query.id && q.name === query.name);
        if (index > -1) {
            recentQueries.splice(index, 1);
        }

        recentQueries.unshift(query);

        localStorage.setItem(this.QUERIES_KEY, JSON.stringify(recentQueries));
    }

    clearRecentQueries() {
        localStorage.removeItem(this.QUERIES_KEY);
    }

/*
    public getToken(): string {
        return localStorage.getItem(TokenService.TOKEN_KEY);
    }

    public saveToken(token: string) {
        localStorage.setItem(TokenService.TOKEN_KEY, token);
    }

    public removeToken() {
        localStorage.removeItem(TokenService.TOKEN_KEY);
    }
*/
}