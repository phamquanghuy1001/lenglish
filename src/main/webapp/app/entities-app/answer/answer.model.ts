import { BaseEntity } from './../../shared';

export class Answer implements BaseEntity {
    constructor(
        public id?: number,
        public createDate?: any,
        public imageContentType?: string,
        public image?: any,
        public content?: string,
        public result?: boolean,
        public questionId?: number,
    ) {
        this.result = false;
    }
}
