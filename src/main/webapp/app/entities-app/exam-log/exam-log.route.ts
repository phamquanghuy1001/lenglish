import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExamLogComponent } from './exam-log.component';

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
    }
];
