import { BaseEntity } from './../../shared';

export class ExamLog implements BaseEntity {
    constructor(
        public id?: number,
        public createDate?: any,
        public complete?: number,
        public point?: number,
        public userId?: number,
        public examId?: number,
    ) {
    }
}
