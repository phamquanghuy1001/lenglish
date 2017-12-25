import { BaseEntity } from './../../shared';

export class Exam implements BaseEntity {
    constructor(
        public id?: number,
        public createDate?: any,
        public imageContentType?: string,
        public image?: any,
        public title?: string,
        public content?: string,
        public point?: number,
        public questions?: BaseEntity[],
        public customerUsers?: BaseEntity[],
    ) {
    }
}
