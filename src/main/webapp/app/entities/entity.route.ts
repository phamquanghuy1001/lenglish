import { Routes } from '@angular/router';

import {
    postRoute,
    commentRoute,
    examRoute,
    feedbackRoute,
    lessonRoute,
    questionRoute,
    roomRoute,
    answerRoute,
    customerUserRoute,
    examLogRoute,
    lessonLogRoute
} from './';
import { JhiMainAdminComponent } from '../layouts/index';

const APP_ROUTES = [
   ...postRoute,
   ...commentRoute,
   ...examRoute,
   ...feedbackRoute,
   ...lessonRoute,
   ...questionRoute,
   ...roomRoute,
   ...answerRoute,
   ...customerUserRoute,
   ...examLogRoute,
   ...lessonLogRoute
];

export const appState: Routes = [{
    path: 'admin',
    component: JhiMainAdminComponent,
    children: APP_ROUTES
}];
