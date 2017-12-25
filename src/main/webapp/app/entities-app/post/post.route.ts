import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PostComponent } from './post.component';
import { PostDetailComponent } from './post-detail.component';
import { PostPopupComponent } from './post-dialog.component';

@Injectable()
export class PostResolvePagingParams implements Resolve<any> {

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

export const postRoute: Routes = [
    {
        path: 'post',
        component: PostComponent,
        resolve: {
            'pagingParams': PostResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.post.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'post/:id',
        component: PostDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.post.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const postPopupRoute: Routes = [
    {
        path: 'post-new',
        component: PostPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.post.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
];
