import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import {
    ExamService,
    ExamPopupService,
    ExamComponent,
    ExamDetailComponent,
    ExamDialogComponent,
    ExamPopupComponent,
    ExamDeletePopupComponent,
    ExamDeleteDialogComponent,
    examRoute,
    examPopupRoute,
    ExamResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...examRoute,
    ...examPopupRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExamComponent,
        ExamDetailComponent,
        ExamDialogComponent,
        ExamDeleteDialogComponent,
        ExamPopupComponent,
        ExamDeletePopupComponent,
    ],
    entryComponents: [
        ExamComponent,
        ExamDialogComponent,
        ExamPopupComponent,
        ExamDeleteDialogComponent,
        ExamDeletePopupComponent,
    ],
    providers: [
        ExamService,
        ExamPopupService,
        ExamResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishExamModule {}
