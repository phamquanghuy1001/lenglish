import { BaseEntity } from './../../shared';

export class LessonLog implements BaseEntity {
    constructor(
        public id?: number,
        public createDate?: any,
        public complete?: number,
        public translation?: number,
        public listening?: number,
        public selection?: number,
        public speech?: number,
        public point?: number,
        public userId?: number,
        public lessonId?: number,
    ) {
    }
}
