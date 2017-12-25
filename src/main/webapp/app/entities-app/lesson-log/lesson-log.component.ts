import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { LessonLog } from './lesson-log.model';
import { LessonLogService } from './lesson-log.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lesson-log',
    templateUrl: './lesson-log.component.html'
})
export class LessonLogComponent implements OnInit, OnDestroy {

    currentAccount: any;
    lessonLogs: LessonLog[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    @Input() lessonId: number;
    // chart
    public lineChartData: Array<any> = [
        { data: [], label: 'Point' },
    ];
    public lineChartLabels: Array<any> = [];
    public lineChartOptions: any = {
        responsive: true
    };
    public lineChartColors: Array<any> = [
        { // grey
            backgroundColor: 'rgba(0, 102, 255, 0.2)',
            borderColor: 'rgba(0, 102, 255,1)',
            pointBackgroundColor: 'rgba(148,159,177,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(148,159,177,0.8)'
        }
    ];
    lineChartLegend: Boolean = true;
    lineChartType: String = 'line';

    constructor(
        private lessonLogService: LessonLogService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = 0;
            this.previousPage = 0;
            this.reverse = 'asc';
            this.predicate = 'id';
        });
    }

    loadAll() {
        this.lessonLogService.queryByLesson(this.lessonId, {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/lesson-log'], {
            queryParams:
                {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/lesson-log', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLessonLogs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LessonLog) {
        return item.id;
    }
    registerChangeInLessonLogs() {
        this.eventSubscriber = this.eventManager.subscribe('lessonLogListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.lessonLogs = data;
        const _lineChartLabel: Array<any> = new Array(this.lessonLogs.length);
        const _lineChartData: Array<any> = new Array(1);
        _lineChartData[0] = { data: new Array(this.lessonLogs.length), label: 'Point' };
        for (let j = 0; j < this.lessonLogs.length; j++) {
            _lineChartData[0].data[j] = this.lessonLogs[j].complete;
            _lineChartLabel[j] = this.lessonLogs[j].createDate.toLocaleDateString();
        }
        this.lineChartData = _lineChartData;
        setTimeout(() => {
            this.lineChartLabels = _lineChartLabel;
        }, 10);
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    // events chart
    public chartClicked(e: any): void {
        console.log(e);
    }

    public chartHovered(e: any): void {
        console.log(e);
    }
}
