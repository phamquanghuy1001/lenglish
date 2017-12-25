import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ActionComponent } from './action.component';

export const actionRoute: Routes = [
    {
        path: 'action',
        component: ActionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.lessonLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
