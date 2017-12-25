import { BaseEntity } from './../../shared';

export class LessonLog implements BaseEntity {
    constructor(
        public id?: number,
        public createDate?: any,
        public complete?: number,
        public userId?: number,
        public lessonId?: number,
    ) {
    }
}
