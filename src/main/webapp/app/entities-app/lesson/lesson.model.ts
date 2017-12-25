import { BaseEntity } from './../../shared';

export class Lesson implements BaseEntity {
    constructor(
        public id?: number,
        public createDate?: any,
        public activated?: boolean,
        public title?: string,
        public level?: number,
        public content?: any,
        public imageContentType?: string,
        public image?: any,
        public customerUsers?: BaseEntity[],
    ) {
        this.activated = false;
    }
}
