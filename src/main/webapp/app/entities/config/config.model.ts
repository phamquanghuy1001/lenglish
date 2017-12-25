import { BaseEntity } from './../../shared';

export class Config implements BaseEntity {
    constructor(
        public id?: number,
        public key?: string,
        public value?: string,
    ) {
    }
}
