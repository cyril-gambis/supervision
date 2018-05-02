import { User } from "./user";

export class UsageLog {
    id: number;
    date: Date;
    firstName: string;
    lastName: string;
    page: string;
    category: string;
    entity: string;
    entityId: number;
    actionType: string;
    actionTypeId: number;
    targetId: number;
    user: User;
}