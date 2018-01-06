import { BaseEntity } from './../../shared';

export const enum QuestionType {
    'TRANSLATION',
    'LISTENING',
    'SELECTION',
    'SPEECH'
}

export class Question implements BaseEntity {
    constructor(
        public id?: number,
        public createDate?: any,
        public questionType?: QuestionType,
        public content?: string,
        public imageContentType?: string,
        public image?: any,
        public resourceContentType?: string,
        public resource?: any,
        public lessonId?: number,
        public exams?: BaseEntity[],
    ) {
    }
}
