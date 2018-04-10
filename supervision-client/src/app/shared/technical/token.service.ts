import { Injectable } from "@angular/core";
import { Token } from "@angular/compiler";

@Injectable()
export class TokenService {

    public static TOKEN_KEY = 'token';

    public getToken(): string {
        return localStorage.getItem(TokenService.TOKEN_KEY);
    }

    public saveToken(token: string) {
        localStorage.setItem(TokenService.TOKEN_KEY, token);
    }

    public removeToken() {
        localStorage.removeItem(TokenService.TOKEN_KEY);
    }
}