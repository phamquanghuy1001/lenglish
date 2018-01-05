import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { CustomerUser } from './customer-user.model';
import { CustomerUserPopupService } from './customer-user-popup.service';
import { CustomerUserService } from './customer-user.service';
import { User, UserService } from '../../shared';
import { Room, RoomService } from '../room';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-customer-user-dialog',
    templateUrl: './customer-user-dialog.component.html'
})
export class CustomerUserDialogComponent implements OnInit {

    customerUser: CustomerUser;
    isSaving: boolean;

    users: User[];

    rooms: Room[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private customerUserService: CustomerUserService,
        private userService: UserService,
        private roomService: RoomService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.roomService.query()
            .subscribe((res: ResponseWrapper) => { this.rooms = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.customerUser, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.customerUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.customerUserService.update(this.customerUser));
        } else {
            this.subscribeToSaveResponse(
                this.customerUserService.create(this.customerUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<CustomerUser>) {
        result.subscribe((res: CustomerUser) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CustomerUser) {
        this.eventManager.broadcast({ name: 'customerUserListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
        this.customerUserService.updateUser();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackRoomById(index: number, item: Room) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-user-popup',
    template: ''
})
export class CustomerUserPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerUserPopupService: CustomerUserPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.customerUserPopupService
                    .open(CustomerUserDialogComponent as Component, params['id']);
            } else {
                this.customerUserPopupService
                    .open(CustomerUserDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
