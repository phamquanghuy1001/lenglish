import { Routes } from '@angular/router';

import {
    activateRoute,
    passwordRoute,
    passwordResetFinishRoute,
    passwordResetInitRoute,
    registerRoute,
    settingsRoute
} from './';
import { JhiMainAppComponent, JhiMainComponent } from '../layouts/index';

const ACCOUNT_ROUTES = [
    activateRoute,
    passwordRoute,
    passwordResetFinishRoute,
    passwordResetInitRoute,
    settingsRoute
];

export const accountState: Routes = [{
    component: JhiMainAppComponent,
    path: '',
    children: ACCOUNT_ROUTES
}];

export const registerState: Routes = [{
    component: JhiMainComponent,
    path: '',
    children: [registerRoute]
}];
