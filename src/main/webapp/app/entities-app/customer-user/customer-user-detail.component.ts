import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { CustomerUser } from './customer-user.model';
import { CustomerUserService } from './customer-user.service';

@Component({
    selector: 'jhi-customer-user-detail',
    templateUrl: './customer-user-detail.component.html'
})
export class CustomerUserDetailComponent implements OnInit, OnDestroy {

    customerUser: CustomerUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private customerUserService: CustomerUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerUsers();
    }

    load(id) {
        this.customerUserService.find(id).subscribe((customerUser) => {
            this.customerUser = customerUser;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCustomerUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'customerUserListModification',
            (response) => this.load(this.customerUser.id)
        );
    }
}
