import { BaseEntity } from './../../shared';

export class Room implements BaseEntity {
    constructor(
        public id?: number,
        public createDate?: any,
        public activated?: boolean,
        public title?: string,
    ) {
        this.activated = false;
    }
}
