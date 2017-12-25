import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Config } from './config.model';
import { ConfigPopupService } from './config-popup.service';
import { ConfigService } from './config.service';

@Component({
    selector: 'jhi-config-dialog',
    templateUrl: './config-dialog.component.html'
})
export class ConfigDialogComponent implements OnInit {

    config: Config;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private configService: ConfigService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.config.id !== undefined) {
            this.subscribeToSaveResponse(
                this.configService.update(this.config));
        } else {
            this.subscribeToSaveResponse(
                this.configService.create(this.config));
        }
    }

    private subscribeToSaveResponse(result: Observable<Config>) {
        result.subscribe((res: Config) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Config) {
        this.eventManager.broadcast({ name: 'configListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-config-popup',
    template: ''
})
export class ConfigPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private configPopupService: ConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.configPopupService
                    .open(ConfigDialogComponent as Component, params['id']);
            } else {
                this.configPopupService
                    .open(ConfigDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
