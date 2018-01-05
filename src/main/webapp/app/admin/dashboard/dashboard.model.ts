import { User } from './../../shared';

export class Dashboard {
    constructor(
        public totalUser?: number,
        public totalLesson?: number,
        public totalExam?: number,
        public totalPost?: number,
        public lastedUser?: User[]
    ) {
    }
}
