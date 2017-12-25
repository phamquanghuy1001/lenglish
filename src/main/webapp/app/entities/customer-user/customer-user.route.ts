import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CustomerUserComponent } from './customer-user.component';
import { CustomerUserDetailComponent } from './customer-user-detail.component';
import { CustomerUserPopupComponent } from './customer-user-dialog.component';
import { CustomerUserDeletePopupComponent } from './customer-user-delete-dialog.component';

@Injectable()
export class CustomerUserResolvePagingParams implements Resolve<any> {

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

export const customerUserRoute: Routes = [
    {
        path: 'customer-user',
        component: CustomerUserComponent,
        resolve: {
            'pagingParams': CustomerUserResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.customerUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'customer-user/:id',
        component: CustomerUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.customerUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const customerUserPopupRoute: Routes = [
    {
        path: 'customer-user-new',
        component: CustomerUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.customerUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-user/:id/edit',
        component: CustomerUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.customerUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-user/:id/delete',
        component: CustomerUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lenglishApp.customerUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
