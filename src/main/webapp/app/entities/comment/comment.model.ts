import { BaseEntity } from './../../shared';

export class Comment implements BaseEntity {
    constructor(
        public id?: number,
        public createDate?: any,
        public content?: string,
        public postId?: number,
        public userId?: number,
    ) {
    }
}
