import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../shared';
import { LenglishCustomerUserModule } from './customer-user/customer-user.module';
import { LenglishPostModule } from './post/post.module';
import { LenglishCommentModule } from './comment/comment.module';
import { LenglishRoomModule } from './room/room.module';
import { LenglishLessonModule } from './lesson/lesson.module';
import { LenglishFeedbackModule } from './feedback/feedback.module';
import { LenglishQuestionModule } from './question/question.module';
import { LenglishAnswerModule } from './answer/answer.module';
import { LenglishExamModule } from './exam/exam.module';
import { LenglishActionModule } from './action/action.module';
import { appState } from './entity-app.route';
import { CommentComponent } from './comment/index';
import { PostDetailComponent } from './post/index';
import { AnswerService } from './answer/index';
import { LenglishLessonLogModule } from './lesson-log/lesson-log.module';
import { LessonComponent } from './lesson/index';
import { LessonLogComponent } from './lesson-log/index';
import { LessonDetailComponent } from '../entities-app/lesson/index';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LenglishLessonLogModule,
        LenglishSharedModule,
        LenglishCustomerUserModule,
        LenglishPostModule,
        LenglishCommentModule,
        LenglishRoomModule,
        LenglishLessonModule,
        LenglishFeedbackModule,
        LenglishQuestionModule,
        LenglishAnswerModule,
        LenglishExamModule,
        LenglishActionModule,
        RouterModule.forRoot(appState, { useHash: true }),
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [
        PostDetailComponent,
        CommentComponent,
        LessonDetailComponent,
        LessonLogComponent,
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishEntityAppModule { }
