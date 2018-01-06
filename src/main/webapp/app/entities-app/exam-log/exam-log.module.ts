import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import { LenglishAdminModule } from '../../admin/admin.module';
import {
    ExamLogService,
    ExamLogComponent,
    examLogRoute,
    ExamLogResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...examLogRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        LenglishAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExamLogComponent,
    ],
    entryComponents: [
        ExamLogComponent,
    ],
    providers: [
        ExamLogService,
        ExamLogResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishExamLogModule {}
