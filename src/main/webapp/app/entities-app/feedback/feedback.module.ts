import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import { LenglishAdminModule } from '../../admin/admin.module';
import {
    FeedbackService,
    FeedbackComponent,
    feedbackRoute,
    FeedbackResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...feedbackRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        LenglishAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FeedbackComponent,
    ],
    entryComponents: [
        FeedbackComponent,
    ],
    providers: [
        FeedbackService,
        FeedbackResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishFeedbackModule {}
