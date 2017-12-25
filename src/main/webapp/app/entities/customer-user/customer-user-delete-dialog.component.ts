import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CustomerUser } from './customer-user.model';
import { CustomerUserPopupService } from './customer-user-popup.service';
import { CustomerUserService } from './customer-user.service';

@Component({
    selector: 'jhi-customer-user-delete-dialog',
    templateUrl: './customer-user-delete-dialog.component.html'
})
export class CustomerUserDeleteDialogComponent {

    customerUser: CustomerUser;

    constructor(
        private customerUserService: CustomerUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerUserListModification',
                content: 'Deleted an customerUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-user-delete-popup',
    template: ''
})
export class CustomerUserDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerUserPopupService: CustomerUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.customerUserPopupService
                .open(CustomerUserDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
