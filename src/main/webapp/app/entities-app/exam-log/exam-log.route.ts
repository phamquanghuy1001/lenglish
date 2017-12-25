import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExamLogComponent } from './exam-log.component';
import { ExamLogDetailComponent } from './exam-log-detail.component';
import { ExamLogPopupComponent } from './exam-log-dialog.component';
import { ExamLogDeletePopupComponent } from './exam-log-delete-dialog.component';

@Injectable()
export class ExamLogResolvePagingParams implements Resolve<any> {

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

export const examLogRoute: Routes = [
    {
        path: 'exam-log',
        component: ExamLogComponent,
        resolve: {
            'pagingParams': ExamLogResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.examLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'exam-log/:id',
        component: ExamLogDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.examLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const examLogPopupRoute: Routes = [
    {
        path: 'exam-log-new',
        component: ExamLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.examLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exam-log/:id/edit',
        component: ExamLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.examLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exam-log/:id/delete',
        component: ExamLogDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.examLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
