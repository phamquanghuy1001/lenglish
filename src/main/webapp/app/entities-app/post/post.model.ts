import { BaseEntity } from './../../shared';

export class Post implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public content?: string,
        public createDate?: any,
        public lastModifier?: any,
        public activated?: boolean,
        public userId?: number,
        public comments?: BaseEntity[],
    ) {
        this.activated = false;
    }
}
