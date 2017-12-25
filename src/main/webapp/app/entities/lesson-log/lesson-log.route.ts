import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LessonLogComponent } from './lesson-log.component';
import { LessonLogDetailComponent } from './lesson-log-detail.component';
import { LessonLogPopupComponent } from './lesson-log-dialog.component';
import { LessonLogDeletePopupComponent } from './lesson-log-delete-dialog.component';

@Injectable()
export class LessonLogResolvePagingParams implements Resolve<any> {

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

export const lessonLogRoute: Routes = [
    {
        path: 'lesson-log',
        component: LessonLogComponent,
        resolve: {
            'pagingParams': LessonLogResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.lessonLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lesson-log/:id',
        component: LessonLogDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.lessonLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lessonLogPopupRoute: Routes = [
    {
        path: 'lesson-log-new',
        component: LessonLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.lessonLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lesson-log/:id/edit',
        component: LessonLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.lessonLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lesson-log/:id/delete',
        component: LessonLogDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.lessonLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
