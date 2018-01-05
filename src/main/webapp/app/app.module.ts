import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { LenglishSharedModule, UserRouteAccessService } from './shared';
import { LenglishHomeModule } from './home/home.module';
import { LenglishAdminModule } from './admin/admin.module';
import { LenglishAccountModule } from './account/account.module';
import { LenglishEntityModule } from './entities/entity.module';
import { LenglishEntityAppModule } from './entities-app/entity-app.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    JhiMainAppComponent,
    JhiMainAdminComponent,
    LayoutRoutingModule,
    NavbarComponent,
    SidebarComponent,
    SidebarAdminComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        LenglishSharedModule,
        LenglishHomeModule,
        LenglishAdminModule,
        LenglishAccountModule,
        LenglishEntityModule,
        LenglishEntityAppModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        JhiMainAppComponent,
        JhiMainAdminComponent,
        NavbarComponent,
        SidebarComponent,
        SidebarAdminComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [JhiMainComponent]
})
export class LenglishAppModule { }
