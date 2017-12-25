import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import { LenglishAdminModule } from '../../admin/admin.module';
import {
    CommentService,
    CommentPopupService,
    CommentDialogComponent,
    CommentPopupComponent,
    commentRoute,
    commentPopupRoute,
    CommentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...commentRoute,
    ...commentPopupRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        LenglishAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CommentDialogComponent,
        CommentPopupComponent,
    ],
    entryComponents: [
        CommentDialogComponent,
        CommentPopupComponent,
    ],
    providers: [
        CommentService,
        CommentPopupService,
        CommentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishCommentModule {}
