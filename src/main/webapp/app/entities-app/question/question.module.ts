import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import {
    QuestionService,
    QuestionComponent,
    questionRoute,
    QuestionResolvePagingParams,
} from './';
import { AnswerService } from '../';

const ENTITY_STATES = [
    ...questionRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        QuestionComponent,
    ],
    entryComponents: [
        QuestionComponent,
    ],
    providers: [
        QuestionService,
        QuestionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishQuestionModule {}
