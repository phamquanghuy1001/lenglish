import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import { LenglishAdminModule } from '../../admin/admin.module';
import {
    CustomerUserService,
    CustomerUserPopupService,
    CustomerUserDetailComponent,
    CustomerUserDialogComponent,
    CustomerUserPopupComponent,
    customerUserRoute,
    customerUserPopupRoute,
    CustomerUserResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerUserRoute,
    ...customerUserPopupRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        LenglishAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerUserDetailComponent,
        CustomerUserDialogComponent,
        CustomerUserPopupComponent,
    ],
    entryComponents: [
        CustomerUserDialogComponent,
        CustomerUserPopupComponent,
    ],
    providers: [
        CustomerUserService,
        CustomerUserPopupService,
        CustomerUserResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishCustomerUserModule {}
