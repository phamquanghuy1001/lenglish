import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../shared';
import { LenglishLessonModule } from './lesson/lesson.module';
import { LenglishQuestionModule } from './question/question.module';
import { LenglishCustomerUserModule } from './customer-user/customer-user.module';
import { LenglishConfigModule } from './config/config.module';
import { LenglishCommentModule } from './comment/comment.module';
import { LenglishRoomModule } from './room/room.module';
import { LenglishFeedbackModule } from './feedback/feedback.module';
import { LenglishAnswerModule } from './answer/answer.module';
import { LenglishExamModule } from './exam/exam.module';
import { LenglishPostModule } from './post/post.module';
import { appState } from './entity.route';
import {
    QuestionDetailComponent,
} from './question/question-detail.component';
import {
    AnswerComponent,
} from './answer/answer.component';

import { LenglishLessonLogModule } from './lesson-log/lesson-log.module';
import { LenglishExamLogModule } from './exam-log/exam-log.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LenglishSharedModule,
        LenglishLessonModule,
        LenglishQuestionModule,
        LenglishCustomerUserModule,
        LenglishConfigModule,
        LenglishCommentModule,
        LenglishRoomModule,
        LenglishFeedbackModule,
        LenglishAnswerModule,
        LenglishExamModule,
        LenglishPostModule,
        RouterModule.forRoot(appState, { useHash: true }),
        LenglishLessonLogModule,
        LenglishExamLogModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [
        QuestionDetailComponent,
        AnswerComponent,
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishEntityModule { }
