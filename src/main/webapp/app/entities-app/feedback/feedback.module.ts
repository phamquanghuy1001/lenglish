import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import { LenglishAdminModule } from '../../admin/admin.module';
import {
    FeedbackService,
    FeedbackPopupService,
    FeedbackDialogComponent,
    FeedbackPopupComponent,
    feedbackPopupRoute,
    FeedbackResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...feedbackPopupRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        LenglishAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FeedbackDialogComponent,
        FeedbackPopupComponent,
    ],
    entryComponents: [
        FeedbackDialogComponent,
        FeedbackPopupComponent,
    ],
    providers: [
        FeedbackService,
        FeedbackPopupService,
        FeedbackResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishFeedbackModule {}
