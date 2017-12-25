import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import {
    LessonService,
    LessonComponent,
    lessonRoute,
    LessonResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...lessonRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LessonComponent,
    ],
    entryComponents: [
        LessonComponent,
    ],
    providers: [
        LessonService,
        LessonResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishLessonModule {}
