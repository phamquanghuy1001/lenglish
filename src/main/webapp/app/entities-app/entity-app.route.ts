import { Routes } from '@angular/router';

import {
    postRoute,
    commentRoute,
    examRoute,
    lessonRoute,
    questionRoute,
    roomRoute,
    answerRoute,
    customerUserRoute,
    lessonLogRoute,
    actionRoute
} from './';
import { JhiMainComponent, JhiMainAppComponent } from '../layouts/index';
import { Action } from 'rxjs/scheduler/Action';

const APP_ROUTES = [
    {
        path: '',
        redirectTo: 'lesson',
        pathMatch: 'full'
    },
    ...postRoute,
    ...commentRoute,
    // ...examRoute,
    ...lessonRoute,
    ...roomRoute,
    ...answerRoute,
    ...customerUserRoute,
    ...lessonLogRoute,
    ...actionRoute,
];

export const appState: Routes = [{
    path: 'app',
    component: JhiMainAppComponent,
    children: APP_ROUTES
},
{
    path: 'app',
    component: JhiMainComponent,
    children: questionRoute,
},
{
    path: 'app',
    component: JhiMainComponent,
    children: examRoute,
}

];
