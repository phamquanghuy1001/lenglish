import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import {
    AnswerService,
    AnswerComponent,
    answerRoute,
    AnswerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...answerRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AnswerComponent,
    ],
    entryComponents: [
        AnswerComponent,
    ],
    providers: [
        AnswerService,
        AnswerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishAnswerModule {}
