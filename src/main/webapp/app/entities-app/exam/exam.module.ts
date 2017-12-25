import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import {
    ExamService,
    ExamComponent,
    ExamDetailComponent,
    examRoute,
    ExamResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...examRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExamComponent,
        ExamDetailComponent,
    ],
    entryComponents: [
        ExamComponent,
    ],
    providers: [
        ExamService,
        ExamResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishExamModule {}
