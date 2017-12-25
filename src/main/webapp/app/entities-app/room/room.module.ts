import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LenglishSharedModule } from '../../shared';
import {
    RoomService,
    RoomComponent,
    RoomDetailComponent,
    roomRoute,
    RoomResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...roomRoute,
];

@NgModule({
    imports: [
        LenglishSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RoomComponent,
        RoomDetailComponent,
    ],
    entryComponents: [
        RoomComponent,
    ],
    providers: [
        RoomService,
        RoomResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LenglishRoomModule {}
