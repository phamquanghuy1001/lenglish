import { Routes } from '@angular/router';

import {
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    trackerRoute,
    userMgmtRoute,
    userDialogRoute,
    dashboardRoute
} from './';

import { UserRouteAccessService } from '../shared';
import { JhiMainAdminComponent } from '../layouts/index';

const ADMIN_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    trackerRoute,
    ...userMgmtRoute,
    metricsRoute,
    ...dashboardRoute

];

export const adminState: Routes = [{
    path: 'admin',
    component: JhiMainAdminComponent,
    data: {
        authorities: ['ROLE_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: ADMIN_ROUTES
},
...userDialogRoute
];
