import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import { LenglishAdminModule } from '../../admin/admin.module';
import {
    LessonLogService,
    LessonLogComponent,
    lessonLogRoute,
    LessonLogResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...lessonLogRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        LenglishAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
    ],
    entryComponents: [
        LessonLogComponent,
    ],
    providers: [
        LessonLogService,
        LessonLogResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishLessonLogModule {}
