import { Mail } from "./mail";

export class User {
    id: number;
    firstName: string;
    lastName: string;

    primaryEmail: Mail;
}