import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import { LenglishAdminModule } from '../../admin/admin.module';
import {
    LessonLogService,
    LessonLogPopupService,
    LessonLogComponent,
    LessonLogDetailComponent,
    LessonLogDialogComponent,
    LessonLogPopupComponent,
    LessonLogDeletePopupComponent,
    LessonLogDeleteDialogComponent,
    lessonLogRoute,
    lessonLogPopupRoute,
    LessonLogResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...lessonLogRoute,
    ...lessonLogPopupRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        LenglishAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LessonLogComponent,
        LessonLogDetailComponent,
        LessonLogDialogComponent,
        LessonLogDeleteDialogComponent,
        LessonLogPopupComponent,
        LessonLogDeletePopupComponent,
    ],
    entryComponents: [
        LessonLogComponent,
        LessonLogDialogComponent,
        LessonLogPopupComponent,
        LessonLogDeleteDialogComponent,
        LessonLogDeletePopupComponent,
    ],
    providers: [
        LessonLogService,
        LessonLogPopupService,
        LessonLogResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishLessonLogModule {}
