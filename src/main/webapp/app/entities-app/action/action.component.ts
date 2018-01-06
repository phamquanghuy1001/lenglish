import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Action } from './index';
import { ActionService } from './action.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, Account } from '../../shared';

@Component({
    selector: 'jhi-action',
    templateUrl: './action.component.html',
    providers: [
        ActionService
    ]
})
export class ActionComponent implements OnInit, OnDestroy {
    action: Action;
    // Pie
    public pieChartLabels: String[] = ['Listening', 'Reading', 'Writing'];
    public pieChartData: Number[] = [0, 0, 0];
    public pieChartType: String = 'pie';
    // lineChart
    public lineChartData: Array<any> = [
        [65, 59, 80, 81, 56, 55, 40]
    ];
    public lineChartLabels: Array<any> = ['1/1/2018', '1/1/2018', '1/1/2018', '1/1/2018', '1/1/2018', '1/1/2018', '1/1/2018'];
    public lineChartType: String = 'line';

    constructor(private actionService: ActionService) {
    }

    ngOnInit() {
        this.actionService.getData().subscribe(
            (res: Action) => {
                this.action = res;
                this.pieChartData = Array(3);
                this.pieChartData[0] = this.action.listening;
                this.pieChartData[1] = this.action.translation;
                this.pieChartData[2] = this.action.selection;
                this.lineChartData = Array(7);
                for (let i = 0; i < 7; i++) {
                    this.lineChartData[i] = this.action.points[i];
                }
                const date = new Date();
                for (let i = 0; i < 7; i++) {
                    this.lineChartLabels[6 - i] = date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear();
                    date.setDate(date.getDate() - 1);
                }
            }
        )
    }

    ngOnDestroy() {
    }

    // events chart
    public chartClicked(e: any): void {
        console.log(e);
    }

    public chartHovered(e: any): void {
        console.log(e);
    }

}
