import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExamComponent } from './exam.component';
import { ExamDetailComponent } from './exam-detail.component';
import { ExamPopupComponent } from './exam-dialog.component';
import { ExamDeletePopupComponent } from './exam-delete-dialog.component';

@Injectable()
export class ExamResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const examRoute: Routes = [
    {
        path: 'exam',
        component: ExamComponent,
        resolve: {
            'pagingParams': ExamResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.exam.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'exam/:id',
        component: ExamDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.exam.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const examPopupRoute: Routes = [
    {
        path: 'exam-new',
        component: ExamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.exam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exam/:id/edit',
        component: ExamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.exam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exam/:id/delete',
        component: ExamDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.exam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
