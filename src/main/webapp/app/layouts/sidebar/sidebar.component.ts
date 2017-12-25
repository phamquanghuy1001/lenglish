import { Component, OnInit, AfterViewInit } from '@angular/core';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { CustomerUserService, CustomerUser } from '../../entities-app/customer-user';
import { Subscription } from 'rxjs/Rx';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { registerModuleFactory } from '@angular/core/src/linker/ng_module_factory_loader';
import { OnDestroy } from '@angular/core/src/metadata/lifecycle_hooks';

@Component({
    selector: 'jhi-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: [
        'sidebar.scss'
    ]
})
export class SidebarComponent implements OnInit, OnDestroy {
    customerUsers: CustomerUser[];
    customerUser: CustomerUser;
    eventSubscriber: Subscription;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    doughnutChartLabels: String[] = ['Hoan thanh', 'Chua hoan thanh'];
    doughnutChartData: number[] = [5, 2];
    doughnutChartType: String = 'doughnut';

    constructor(
        private customerUserService: CustomerUserService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;

        this.page = 0;
        this.previousPage = 0;
        this.reverse = 'desc';
        this.predicate = 'point';
    }

    loadAll() {
        this.customerUserService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
            );
        this.customerUserService.findCurrent().subscribe(
            (customerUser) => {
                this.customerUser = customerUser;
                const _doughnutChartData = new Array(2);
                const todayPoint = customerUser.todayPoint;
                let dateGoal = customerUser.dateGoal;
                dateGoal = dateGoal > todayPoint ? dateGoal - todayPoint : 0;
                _doughnutChartData[0] = todayPoint;
                _doughnutChartData[1] = dateGoal;
                this.doughnutChartData = _doughnutChartData;
            },
            (res: ResponseWrapper) => console.log('can not load customer user!')
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
        }
    }

    ngOnInit() {
        this.loadAll();
        this.registerChangeInCustomerUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CustomerUser) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInCustomerUsers() {
        this.eventSubscriber = this.eventManager.subscribe('customerUserListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + 'desc'];
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.customerUsers = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    // events
    public chartClicked(e: any): void {
        console.log(e);
    }

    public chartHovered(e: any): void {
        console.log(e);
    }
}
