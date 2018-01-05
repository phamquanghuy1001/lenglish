import { Component, OnInit, OnDestroy } from '@angular/core';
import { Dashboard } from './dashboard.model';
import { DashboardService } from './dashboard.service';

import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: [
        'dashboard.scss'
    ],
    providers: [
        DashboardService,
    ]
})
export class DashboardComponent implements OnInit {
    dashboard: Dashboard;

    constructor(private dashboardService: DashboardService) {
    }

    ngOnInit() {
        this.dashboardService.getData().subscribe(
            (res: Dashboard) => {
                this.dashboard = res;
            }
        )
    }

}
