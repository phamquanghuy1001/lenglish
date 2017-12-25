import { BaseEntity } from './../../shared';

export class CustomerUser implements BaseEntity {
    constructor(
        public id?: number,
        public iconContentType?: string,
        public icon?: any,
        public point?: number,
        public level?: number,
        public todayPoint?: number,
        public dateGoal?: number,
        public userId?: number,
        public roomId?: number,
    ) {
    }
}
