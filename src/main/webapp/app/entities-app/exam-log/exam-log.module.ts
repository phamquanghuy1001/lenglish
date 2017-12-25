import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import { LenglishAdminModule } from '../../admin/admin.module';
import {
    ExamLogService,
    ExamLogPopupService,
    ExamLogComponent,
    ExamLogDetailComponent,
    ExamLogDialogComponent,
    ExamLogPopupComponent,
    ExamLogDeletePopupComponent,
    ExamLogDeleteDialogComponent,
    examLogRoute,
    examLogPopupRoute,
    ExamLogResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...examLogRoute,
    ...examLogPopupRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        LenglishAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExamLogComponent,
        ExamLogDetailComponent,
        ExamLogDialogComponent,
        ExamLogDeleteDialogComponent,
        ExamLogPopupComponent,
        ExamLogDeletePopupComponent,
    ],
    entryComponents: [
        ExamLogComponent,
        ExamLogDialogComponent,
        ExamLogPopupComponent,
        ExamLogDeleteDialogComponent,
        ExamLogDeletePopupComponent,
    ],
    providers: [
        ExamLogService,
        ExamLogPopupService,
        ExamLogResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishExamLogModule {}
